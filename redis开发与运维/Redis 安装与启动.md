## 安装

```text
$ wget http://download.redis.io/releases/redis-5.0.5.tar.gz
$ tar xzf redis-5.0.5.tar.gz
$ cd redis-5.0.5
$ make
$ make install
```

## 启停

### 启动
启动分三种方式：

1. 默认启动 `redis-server`
2. config key 启动 `redis-server --key1 k1value --key2 k2value`
3. 配置文件启动 `redis-server /path/to/config/file`


redis 目录下有一个 `redis.conf` 放置了默认配置，可以以此为模板进行定制修改

redis 配置项：
| 名称 | 含义 | 
|----|----|
| port | 监听端口|
| daemonize | 守护方式启动，yes or no|
|logfile | 日志文件|
| dir | 工作目录（持久化文件，日志文件存放点） |

## 停止

停止redis 服务

```text
// 使用 shutdown 命令停止
redis-cli shutdown
redis-cli -h (host|ip) -p port shutdown
```


## redis 访问

客户端：

```text
// 交互方式
redis-cli -h host -p port

// 一次命令执行
redis-cli -h host -p port {command}


如果没有指定 port 默认为6379
```
