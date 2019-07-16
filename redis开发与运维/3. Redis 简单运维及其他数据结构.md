## 慢查询分析

1. 配置参数
    1. `slowlog-log-slower-than` 单位为微秒，默认为10000微秒，命令执行时间大于这个的才会被记录
    2. `slowlow-max-len` 慢日志是放在一个队列中的，这个配置指定了队列长度，队列满后第一条会出队。
2. 慢日志的查询和管理
    1. `slowlog get [n]` 获取n条慢日志，不指定n获取全部
    2. 慢日志当前列表长度 `slowlog len`
    3. 慢日志查询重置 `slowlog reset`
    4. 由于redis 慢日志是在内存中且有上限的，因此可以每隔一段时间将慢日志导出到数据库中，以备查询

## redis shell

1. redis-cli
    1. `-r repeat` 多次执行某一个命令
    2. `-i internal` 间隔internal秒执行一次，必须与 `-r` 配合使用，单位是秒，可以使用小数来指定毫秒
    3. `-x` 代表从标准输入读取数据作为 redis-cli 的最后一个参数，例：`echo 'world' | redis-clis -x set hello`
    4. `-a` redis 配置了密码使用，也可以进入后使用 auth 命令
    5. `--scan` `--pattern` 用于扫描指定模式的键
    6. `--slave` 用于把当前客户端模拟成当前 redis 节点的 slave 节点，可以用来获取redis 的更新操作
    7. `--rdb` 会请求redis 生成rdb 持久文件，保存到本地，可以用来做本地的持久化备份
    8. `--pipe` 用于将命令封装成 redis 通信协议定义的格式，批量发送给redis 执行
    9. `--bigkeys` 使用scan 对redis进行采样，找到内存占比较大的键值
    10. `--eval` 执行 lua 脚本
    11. `--latency` 用于检测网络延时
    12. `--stat` 实时获取redis 的统计信息，可以看到增量信息
    13. `--no-raw` 要求命令的返回结果必须是原始格式，`--raw` 要求返回格式化后的结果（中文字符使用`--no-raw` 返回unicode 编码， `--raw` 返回中文）

2. redis-benchmark 做基准性能测试
    1. `-c` (clients) 代表客户端的并发数量
    2. `-n requests` 代表客户端请求总量
    3. `-q` （quiet）仅展示reqeusts per second 信息
    4. `-r` （random） 插入更多随机键
    5. `-k boolean` 是否保持keep alive
    6. `-t command,command` 对指定命令做测试
    7. `--csv` 按照csv 格式输出

## Bitmaps

bitmap 在redis 中实质还是字符串，只是提供了命令来操作位，redis 实现的 bitmap 是连续的，如果只设置第1位和第10000位，中间的空间都是浪费的。

1. 相关命令
    1. `setbit key offset value` 设置某一位
    2. `getbit key offset` 获取某一位值
    3. `bitcount [start] [end]` 获取指定范围内 1 的个数，start end 代表起始结束字节数
    4. `bitop op deskey key [key...]` 复合操作，op 可以是 `and` `or` `not` `xor` 等操作，结果保存到 deskey 中。
    5. `bitpos key targetBit [start] [end]` 计算bitmap 中第一个值为 targetBit 的偏移量，start end 可以指定字节数

## HyperLogLog

HyperLogLog 是一种基数算法，可以利用极小的内存空间完成独立总数的统计，数据集可以是 IP、Email、ID 等。

算法是有 Phillippe Flajolet 提出的，因此 redis 的命令前缀为 pf

算法占用极小内存，但有0.81% 的误差，因此适合于可以容忍误差率的场景

1. 相关命令
    1. `pfadd key element [element...]` 添加
    2. `pfcount key [key...]` 计算一个或多个 HyperLogLog 的独立用户数
    3. `pfmerge deskey sourcekey [sourcekey...]` 合并

## 发布订阅

消息发布者和消费者不进行直接通信，二者通过频道（channel） 来通信。发布者发布消息到一个频道上，订阅该频道的每个客户端都可以收到消息

1. 相关命令
    1. 发布消息 `publish channel message`
    2. 订阅消息，可以订阅多个频道 `subscribe channel [channel...]`
        1. 客户端订阅之后进入订阅状态，只能接收 `subscribe` `psubscribe` `unsubscribe` `punsubscribe` 四个命令
        2. 客户端收不到订阅之前发布的消息，因为消息不会进行持久化
    3. 取消订阅 `unsubscribe [channel [channel...]]`, 不带channel 默认全部取消订阅
    4. 按照模式订阅与取消订阅 `psubscribe [pattern [pattern...]]` `punsubscribe [pattern [pattern..]]`
    5. 查询订阅
        1. 查询活跃的频道 `pubsub channels [pattern]`
        2. 查看频道订阅数   `pubsub numsub [channel...]`
        3. 查看模式订阅数  `pubsub numpat`
