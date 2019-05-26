## 以太网链路层协议无长度字段，如何确定帧已结束？

1. 书中显示的以太网帧字段不全，在前面是还有其他字段的，不过并没有长度字段，参考 [以太网帧格式](https://blog.csdn.net/ftxc_blog/article/details/12811235)
2. 底层的物理特性来告诉mac 层帧传送结束 [How does Ethernet know how long a frame is?](https://serverfault.com/questions/87927/how-does-ethernet-know-how-long-a-frame-is)


## 链路层为什么会有最小数据长度与最大数据长度限制？

1. 最小数据长度是由物理电气特性规定的，以太网需要一个 slot time 才能检测冲突，这个时间可传输64字节，除去链路层18字节，剩下的46字节就是最小数据长度。[Why is the ethernet payload fixed between 46 and 1500 bytes?](https://networkengineering.stackexchange.com/questions/5263/why-is-the-ethernet-payload-fixed-between-46-and-1500-bytes)
2. 最大数据长度限制是由于以太网有两个不同的协议导致的，以太网封装在目的地址、源地址之后是一个类型字段(字段值从 0x0600 开始)，而802.3协议定义的是数据包长度字段，由于这两个协议封装的帧都需要被识别出来，因此1500bytes 是上限。[Why was the MTU size for ethernet frames calculated as 1500 bytes?](https://networkengineering.stackexchange.com/questions/2962/why-was-the-mtu-size-for-ethernet-frames-calculated-as-1500-bytes)

## IP层协议中总长度是否是没有必要的？

如果IP 报文传送给链路层的数据少于 46 字节，链路层会在后面填充字节使 payload 满足以太网的最小长度需求，接收方在收到帧后，需要使用总长度来判断IP 报文的边界

## ICMP 报文为什么没有长度字段

ICMP 一次传输一个报文，里面包含了所有的信息，因此依赖IP 层提供的长度能力就可以了。

## IP 层为什么不提供整体 checksum 校验，而只有头部 checksum？

1. 这导致所有使用 IP协议的上层协议都需要有自己的校验字段
2. 应该是一个设计决定

## IP 为什么要含有头部校验

1. 某些物理网络，如 SLIP，链路层本身不含有checksum，需要IP 自己检测包损坏
2. IP 头部信息在经过路由器是会变化的，增加 checksum 可以尽快检测到错误

