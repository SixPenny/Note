## redis 的机制

1. 全局命令
```text
// 查看全部键 O(N)
keys *

// 键的总数 O(1)
dbsize

// 检查键存在
exists key

// 删除键（返回删除个数，删除一个不存在的键返回0）
del key [key...]

// 设置过期时间
expire key seconds

//获取键的剩余过期时间( -1:无过期时间，-2:键不存在)
ttl key

//键类型(string, hash, list, set, zset)
type key

```

2. 数据结构与内部编码

redis 的每种数据结构都有低层内部实现，而且有多种内部实现，redis 会在合适的场景选择合适的内部编码

`object encoding key` 查询内部编码

3. 单线程架构

redis 使用单线程+IO多路复用来实现高性能的内存服务。

一条命令到达redis 不会被立即执行，而是进入一个队列中被依次执行，所以不存在并发问题

## 字符串

字符串是基础。键都是字符串，值可以是字符串、数字、二进制，值最大不能超过512MB。

### 命令

1. 常用命令
    1. 设置值 `set key value [ex seconds] [px milliseconds] [nx|xx]`
        1. ex,px 设置过期时间，单位不一样
        2. nx 键不存在时才能添加，xx 键存在时才能更新
        3. `setnx key value` 与 `nx` 使用一致
        4. `setex key seconds value` 与 `ex` 用法一致
        5. `setnx` **可以用于实现分布式锁**
    2. 获取值 `get key`
    3. 批量设置值 `mset key value [key value..]`
    4. 批量获取值 `mget key [key..]` , 按顺序返回，不存在值返回nil（减少网络时间）
    5. 计数相关 `incr key` `decr key` `incrby key number` `decrby key number` `incrbyfloat key floatNumber`, 对不存在的键执行计数命令，默认键值为0 然后再做对应操作
2. 不常用命令
    1. 追加 `append key value`
    2. 长度 `strlen key`
    3. 设置并返回原值 `getset key value`
    4. 设置指定位置字符 `setrange key offset value`
    5. 获取部分字符串 `getrange key start end`

### 内部编码

有三种：
1. int  8字节长整型
2. embstr 小于40字节的字符串
3. raw 大于等于40字节的字符串


### 使用场景

1. 缓存功能
    1. 键是全局的，因此需要自己定义namespace，最好使用 `业务:对象名:id` 作为键
2. 计数
3. 共享session
4. 限速

## Hash 哈希

1. 命令
    1. add 值 `hset key field value` `hsetnx key field value` 如果 field 不存在则设置
    2. get 值 `hget key field`
    3. 删除field `hdel key field [field...]`
    4. field 个数 `hlen key`
    5. 批量设置 `hmset key field value [field value...]`
    6. 批量获取 `hmget key field [field ...]`
    7. field 是否存在 `hexists key field`
    8. 获取所有field `hkeys key`
    9. 获取所有value `hvals key`
    10. 获取所有(元素个数过多可能阻塞) `hgetall key`
    11. 增加field (无hincr 命令) `hincrby key field number`
    12. 计算value 字符串长度 `hstrlen key field`
2. 内部编码
    1. `ziplist` 压缩列表，元素个数小于 `hash-max-ziplist-entries`(default 512), 所有值都小于 `hash-max-ziplist-value`(default 64bytes) 时使用，更加紧凑
    2. `hashtable` 无法使用`ziplist` 时的方案，因为 ziplist 读写效率会下降
3. 使用场景
    1. 关系表数据key-value 直接hash 保存

## List 列表

List 用来存储多个元素，是一个有序列表。最多可以保存 `2^32 -1` 个元素。可以从前后压入弹出，可以对比为双端队列

1. 命令
    1. 添加 `lpush key value [value..]` `rpush key value [value...]` `linsert key before|after pivot value`
    2. 查询 `lrange key start end` `lindex key index` `llen key`
    3. 删除 `lpop key` `rpop key` `lrem key count value`(删除value，count>0从左到右，count<0从右到左，count=0 删除所有) `ltrim key start end`(只保留[start,end]的元素，其余删掉)
    4. 修改 `lset key index value`
    5. 阻塞弹出 `blpop key [key...] timeout` `brpop key [key..] timeout` 列表为空，则客户端会阻塞等待 timeout 秒（timeout=0 无限等待），有值加入则会第一个等待的客户端获得，多个 key 会遍历，有一个可以弹出就立即返回
2. 内部编码
    1. `ziplist` 同上
    2. `linkedlist`
3. 使用场景
    1. 消息队列 lpush+brpop 组合
    2. 文章列表

## Set 集合

Set 与列表类似，只是不允许重复的值，还可以取交并补集

1. 集合内操作命令
    1. 添加 `sadd key element [ele...]`
    2. 删除 `srem key ele [ele..]`
    3. 计算元素个数 `scard key`
    4. 判断是否在集合中 `sismember key ele`
    5. 随机返回n 个元素 `srandmember key [count]`
    6. 随机弹出元素 `spop key`
    7. 所有元素 `smembers key`
2. 集合间操作(生产环境不能使用)
    1. 交集 `sinter key [key...]`
    2. 并集 `sunion key [key...]`
    3. 差集 `sdiff key [key..]`
    4. 交集保存 `sinterstore des key [key...]`
    5. 并集保存 `sunionstore des key [key ...]`
    6. 差集保存 `sdiffstore des key [key..]`
3. 内部编码
    1. intset ： 元素全部为整数且数量小于 `set-max-intset-entries` 时
    2. hashtable
4. 使用场景
    1. 标签

## 有序集合（zset）

集合中的元素可以排序，每个元素有一个score， 有序集合使用 score 作为排序依据

1. 集合内命令
    1. 添加 `zadd key score member [score member...]`
    2. 成员数 `zcard key`
    3. 成员的分数 `zscore key member`
    4. 成员的排名 `zrank key member` `zrevrank key member`
    5. 删除成员 `zrem key mem`
    6. 增加分数 `zincrby key increment member`
    7. 返回指定排名范围的成员 `zrange key start end [withscore]` `zrevrange key start end [withscore]`
    8. 返回指定分数范围成员 `zrangebyscore key min max [withscore] [limit offset count]` zrevrangebyscore 相同
        1. `[limit offset count]` 从 offset 开始输出count 个
        2. -inf +inf 代表无限小和无限大
    9. 返回指定分数范围个数 `zcount key min max`
    10. 删除指定排名内的升序元素 `zremrangebyrank key start end`
    11. 删除指定分数内的成员 `zremrangebyscore key min max`
2. 集合间命令（不能用在生产环境）
    1. 交集 `zinterstore des numkeys key [key...] [weights weight [weight...]] [aggregate sum|min|max]`
        1. des 计算结果保存到的键
        2. numkeys 需要做交集计算键的个数
        3. key [key...] 计算交集的键
        4. weights weight[weight..] 每个键的权重，做交集运算时，键中的每个member 会将自己的分数乘以这个权重，默认为1
        5. aggregate 计算交集后，分值可以按照 sum， min，max 做汇总，默认值 sum
    2. 并集 `zunionstore des numkeys key [key...] [weights weight [weight]] [aggregate sum|min|max]` 含义同上
3. 内部实现
    1. ziplist 满足个数小于 zset-max-ziplist-entries（default 128） 配置，且每个元素的值都小于 zset-max-ziplist-value（default 64 bytes）时使用，可以减少内存使用
    2. skiplist 
4. 使用场景
    1. 排行榜系统


## 键管理

1. 单个键管理
    1. 键重命名 `rename key newkey` 新键如果存在的话会被覆盖 `renamenx key newkey` 可以确保只有 newkey 不存在时才会重命名
    2. 随机返回一个键 `randomkey`
    3. 键过期
        1. `expireat key timestamp` 单位为秒级
        2. `pttl key` 精度比ttl 更精确，单位为毫秒
        3. `pexpire key milliseconds`
        4. `pexpireat key millsec-timestamp`
        5. `persist key` 删除键的过期时间
        6. 注：如果过期时间为负值，则键会被立即删除
        7. 注：字符串类型键，执行set 命令后过期时间会被删除
    4. 迁移键
        1. `move key db` 用于在redis 内部做迁移，redis 里可以包含多个数据库，多数据库是数据隔离的，但多数据库功能不建议在生产环境使用
        2. `dump + restore` 在不同redis 实例间迁移 `dump key` 输出 RDB 格式，`restore key ttl value` value 为dump出来的结果，这两个操作是非原子的
        3. `migrate host port key|"" destination-db timeout [copy] [replace] [keys key [key...]]`
            1. `key|""` 迁移单个键可以指定key， 迁移多个键使用 空字符串，在后面的 keys 中指定要迁移的键
            2. copy 命令表示不删除源redis 里的键
            3. replace 不管目标redis 是否存在该键都会进行正常迁移覆盖
2. 遍历键
    1. 全量遍历 `keys pattern`
    2. 渐进式遍历`scan cursor [match pattern] [count number]`
3. 数据库管理
    1. 切换数据库 `select dbIndex` 不建议使用（单线程，调试运维困难，有些客户端不支持）
    2. 清除数据库 `flushdb` `flushall`
