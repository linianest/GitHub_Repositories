





# 一、Redis入门

## 1、简介

**Redis是一个开源（BSD许可），内存存储的数据结构服务器，可用作数据库，高速缓存和消息队列代理。==采用单进程线程模型，并发能力强大==，是当前互联网中主流的分布式缓存工具；**

1、单体项目数据存储的瓶颈

- 数据量的总大小，一个机器放不下
- 数据的索引，一个机器放不下
- 访问量（读写混合）一个实例不能承受

2、Memcached(缓存)+MySQL+垂直拆分

3、Mysql主从读写分离

4、分表分库+水平拆分+mysql集群

**NoSQL是什么？**泛指非关系型数据库

3v:

>1. 海量Volume
>2. 多样Variety
>3. 实时Velocity

3高：

>1. 高并发
>2. 高可扩
>3. 高性能

**当下的应用是sql和nosql一起使用**

优点：

>1. 以扩展
>2. 大数据量高性能
>3. 多样灵活的数据模型
>4. 传统RDBMS  VS  NOSQL

### 1.1、NOSQL数据模型

>1. kv键值对：用的最多
>   - 新浪：BerkeleyDB+redis
>   - 美团：redis+tair
>   - 阿里、百度：memcache+redis
>2. 文档型数据库
>   - CouchDB
>   - MongoDB(用的多)，基于分布式文件存储的数据库（c++写的）；介于关系与非关系数据库之间的产品
>3. 列存储数据库
>   - Cassandra,HBase
>4. 图关系数据库
>   - 并不是放图形的，而是存放关系比如：朋友圈社交网络、广告推荐系统等；
>   - 社交网络，推荐系统等

**分布式数据库中CAP原理：CAP+BASE**

传统的ACID分别是什么？

原子性（Atomicity）
原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
一致性（Consistency）
事务前后数据的完整性必须保持一致。
隔离性（Isolation）
事务的隔离性是多个用户并发访问数据库时，数据库为每一个用户开启的事务，不能被其他事务的操作数据所干扰，多个并发事务之间要相互隔离。
持久性（Durability）

持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来即使数据库发生故障也不应该对其有任何影响

**CAP原则又称CAP定理**，指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分区容错性），三者不可兼得   。

**京东、淘宝：AP**

http://redis.io

## 2、Redis安装（linux）

1.下载redis-3.0.4.tar.gz存放在/opt

 wget http://download.redis.io/releases/redis-3.0.4.tar.gz

2.解压文件

tar -zxvf redis-3.0.4.tar.gz 

3.cd  redis-3.0.4

4.运行make命令时出现的错误解析：

- gcc找不到缺失：yum -y install gcc

- 如果不能上外网：从安装系统中安装

  >rmp -ivh cpp-4.1.2-48.el5.i386.rpm回车
  >
  >rmp -ivh kernel-headers-xxx.el5.i386.rpm
  >
  >rmp -ivh glibc-headers-xxxx-48.el5.i386.rpm
  >
  >rmp -ivh glibc-devel-xxxx-48.el5.i386.rpm
  >
  >rmp -ivh libgomp-xxxx-48.el5.i386.rpm
  >
  >rmp -ivh gcc-xxxx-48.el5.i386.rpm
  >
  >gcc -v
  >
  >gcc 版本 4.8.5 20150623 (Red Hat 4.8.5-36) (GCC) 表示安装完成

  

- 再make后，出现文件目录找不到，运行make distclean之后再make 就可以了
- 不要运行make test

2.创建文件夹  mkdir /myredis

cp redis.conf /myredis/

3.修改后台启动

vim redis.conf

将daemonize 设置成yes,也就是后台运行

运行redis-server /myredis/redis.conf 

redis-cli  -p 6379

redis-cli  SHUTDOWN 关闭服务

exit退出

4.Redis启动的杂项知识点

>1. 单进程
>2. 默认16库（0-15），
>3. 切换库：select  7(切换到6号库)
>4. Dbsize查看当前数据库的key的数量，keys  *：查看所有  的键，keys  a?查看以a开头的键
>5. FLUSHDB清空指定库，FLUSHALL清空所有的库
>6. 统一密码管理，16个库的都是同样密码
>7. 默认端口：6379
>8. Redis索引都是从零开始



## 3、Redis数据类型

**Redis的五大数据类型：**

>1. String :字符串
>   - String 是redis基本的类型与memcached一样，String的value最大支持512M
>2. Hash：哈希，类似java里的map
>   - redis hash是一个键值对结构
>3. List: 列表
>   - 简单的链表，可以左右一起添加或者弹出
>4. set：集合
>   - 无序无重复的无序集合
>5. Zset（sorted set）:有序集合
>   - 与set一样，不同的是每个元素都会关联一个double类型的分数，通过分数的大小排序，但是分数（score）可以重复
>
>

**Redis**常见数据类型操作命令：http://redisdoc.com

### 3.1、Redis键（key）

>1. keys *：查看所有的键；
>2. exists key的名字 ，判断某个key是否存在；
>3. move key db:移动某个key到指定库；
>4. expire key:为某个key设置过期时间；
>5. TTL key:查看还有多少秒过期，-1表示永不过期，-2表示已经过期；
>6. type key:查看你的key是什么类型；
>7. DEL key:删除某个key;
>8. set key value:设置键和值，如果存在会覆盖原有的key的值
>
>

### 3.2、Redis字符串（String）

>1. set/get/del/append/strlen:设置/获取/删除/追加/长度；
>2. incr/decr/incrby/decrby(key的值一定要是数字才能进行加减):增加/减少/多路自增/多路自减；
>3. getrang/setrang:获取指定范围的值，0到-1表示全部
>4. setex/setnx:设置过期时间；setnx如果不存在，值才能起效果；
>5. mset/mget/msetnx:多个设置/多个获取/多个不存在起效(一起起效或者一起失败)；如：mset k1 v1 k2 v2 k3 v3；

### 3.3、Redis列表(List)

>1. lpush/rpush/lrang:左添加/右添加*/左范围获取；
>2. lpop/rpop:左弹出/右弹出（获取）
>3. lindex:按照索引获取list的元素（从上到下）
>4. llen: 获取list的长度
>5. lrem key: 删除多个value;eg: lrem list02  2 4 ->删除list02中两个4；
>6. ltrim key 开始index 结束index，截取指定范围的值后重新再赋值给key;
>7. rpoplpush: 源列表  到 目的列表；
>8. lset key index value:指定list的某个下表设置值；
>9.  linsert key before/after :把某个值插入到指定位置前/后;
>
>

###3.4、Redis集合(set)--去除重复值

>1. sadd/smembers/sismember:添加/获取/某个值是否存在；
>2. scard:获取集合里面的元素个数
>3. srem key value: 删除集合中指定的元素
>4. srandmember  key 某个整数:集合中随机出几个数；
>5. spop key :随机出栈
>6. smove key1 key2:将key1中值移动到key2中
>7. (差集)sdiff key1 key2:查看key1不在key2中的值
>8. （交集）sinter key1 key2:获取key1与key2的交集（去重）
>9. （并集）sunion key1 key2:获取key1与key2的并集（去重）
>
>

### 3.5、Redis哈希（Hash）

**kv模式不变，但v是一个键值对**：hset user id 11;

>1. hset/hget/hmset/hmget/hgetall/hdel:添加/获取/不存在添加/多个获取/获取所有/删除key
>2. hlen:长度
>3. hexists key   value:判断某个value是否存在key中；
>4. hkeys/hvals:获取所有的key集合/获取所有的value的集合
>5. hincrby/hincrbyfloat:多个递增/小数递增
>6. hsetnx: 不存在添加；
>
>



### 3.6、Redis有序集合Zset(sorted set)

 在set基础上，添加了score值。之前的set是k1 v1 k2 v2

现在是k1 score1 v1 k2 score2 v2

>1. zadd/zrang:添加/获取  zrang  set01 0 -1;
>
>2. zrangebyscore key 开始score  结束score;withscores;  ( 不包含；limit 0 2;
>
>3. zrem key 某score下对应的value值：删除元素；
>
>4. zcard/zcount key score区间/zrank key values值：获取下标值/zscore key对应值，获取分数；
>
>5. zrevrank key values值：逆序获得下标值
>
>6. zrevrang:获取值
>
>7. zrevrangebyscore key  score1 score2:获取集合
>
>   



## 4、解析配置文件redis.conf

### 4.1、它在那里？

默认在redis里面，**一定要先备份**

```properties
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.


```

>1. 配置单位大小，开头定义了一些基本的度量单位，只支持bytes，不支持bit
>2. 对大小写不明感



### 4.2、INCLUDE包含

```properties
# Include one or more other config files here.  This is useful if you
# have a standard template that goes to all Redis servers but also need
# to customize a few per-server settings.  Include files can include
# other files, so use this wisely.
#
# Notice option "include" won't be rewritten by command "CONFIG REWRITE"
# from admin or Redis Sentinel. Since Redis always uses the last processed
# line as value of a configuration directive, you'd better put includes
# at the beginning of this file to avoid overwriting config change at runtime.
#
# If instead you are interested in using includes to override configuration
# options, it is better to use include as the last line.
#
# include /path/to/local.conf
# include /path/to/other.conf

```

>**可以和struts2一样，通过includes包含，redis.conf可以作为总闸，包含其他的redis**



### 4.3、*GENERAL通用配置

```properties
################################ GENERAL  #####################################

# By default Redis does not run as a daemon. Use 'yes' if you need it.
# Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
daemonize yes

# When running daemonized, Redis writes a pid file in /var/run/redis.pid by
# default. You can specify a custom pid file location here.
pidfile /var/run/redis.pid

# Accept connections on the specified port, default is 6379.
# If port 0 is specified Redis will not listen on a TCP socket.
port 6379

# TCP listen() backlog.
#
# In high requests-per-second environments you need an high backlog in order
# to avoid slow clients connections issues. Note that the Linux kernel
# will silently truncate it to the value of /proc/sys/net/core/somaxconn so
# make sure to raise both the value of somaxconn and tcp_max_syn_backlog
# in order to get the desired effect.
tcp-backlog 511

# By default Redis listens for connections from all the network interfaces
# available on the server. It is possible to listen to just one or multiple
# interfaces using the "bind" configuration directive, followed by one or
# more IP addresses.

```

>1. daemonize yes设置成后台启动
>2. pidfile /var/run/redis.pid  默认的pid路径
>3. port  6379  设置端口
>4. tcp-backlog 511  设置连接队列数
>5. timeout  0 ：设置空闲多少秒后关闭连接；0表示不关闭
>6. tcp-keepalive（秒）：建议设置成60秒，心跳设置
>7. loglevel notice:日志级别；debug/verbose/notice/warning
>8. logfile :日志名称
>9. syslog-enabled:是否把日志输出到日志文件中
>10. syslog-ident:指定syslog里的日志标志
>11. syslog-facliity:指定syslog设备，值可以是user或者local0-local7
>12. databases :默认的数据库数量



### 4.5、SNAPSHOTTING快照

1.save  秒钟  写操作次数(触发快照操作):当单位时间内，操作实数达到后，会持久化。

  eg: save 60 10000

 禁用：save  ""

立刻备份：save命令

2.stop-writes-on-bgsave-error yes:默认是yes,如果配置成no，表示不在乎数据的一致性，出错还是会继续备份

3.rdbcompression :是否对存储到磁盘中的快照进行压缩，会消耗cpu，默认就好

4.rdbchecksum:压缩完后会进行crc64算法。默认就好

5.dbfilename:备份后的文件名称

6.dir：备份后存放的位置（当前执行redis命令的位置）







### 4.6、REPLICATION复制

### 4.7、SECURITY安全

默认的密码是空，不需要设置

>1. 设置密码
>
>   ```shell
>   config set requirepass "123456"
>   #下次在访问前必须验证
>   auth 123456
>   
>   ```
>
>​    

### 4.8、*LIMITS限制

>1. maxclients:最大并发连接数（10000）
>
>2. maxmemory:最大内存（bytes）
>
>3. Maxmemory-policy:缓存策略-----LRU最近最少使用原则
>
>   - volatile-lru:使用LRU算法移除key，只对设置了过期时间的键
>   - Allkeys-lru:使用LRU算法移除key
>   - Volatile-random:在过期集合中移除随机的key，只对设置了过期时间的键
>   - Allkeys-random:移除随机的key
>   - Volatile-ttl:移除那些TTL值最小的key,即那些最近要过期的key
>   - Noeviction:不进行移除。针对写操作，只是返回错误信息
>
>4. Maxmemory-samples:
>
>   设置样本的数量，进行测试移除，使用的是LRU和TTL原则
>
>

### 4.9、APPEND ONLY MODE追加

1. appendonly no:默认是关闭的,yes就是打开aof的持久化
2. appendfilename "appendonly.aof" 默认备份aof的文件名称
3. Appendfsync:策略
   - Always：同步持久化，每次发生数据变化都会被立即记录到磁盘，性能较差，但是数据完整性比较好
   - Everysec:出厂默认推荐，异步操作，每秒记录，如果一秒内宕机，有数据丢失。这归功于Redis引入了BIO线程，所有fsync操作都异步交给了BIO线程。
   - No:则write后不会有fsync调用，由操作系统自动调度刷磁盘，性能是最好的
4. No-appendfsync-on-rewrite:重写时是否可以运用Appendfsync,用默认no即可，保证数据安全性；
5. Auto-aof-rewrite-min-size:设置重写的基准值
6. Auto-aof-rewrite-percentage:设置重写的基准值（百分比）



**aof可以与rdb一起共存，启动先加载aof文件**







### 4.10、常见配置redis.conf介绍

参数说明
**redis.conf 配置项说明如下：**

1. Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程
  daemonize no
2. 当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定
  pidfile /var/run/redis.pid
3. 指定Redis监听端口，默认端口为6379，作者在自己的一篇博文中解释了为什么选用6379作为默认端口，因为6379在手机按键上MERZ对应的号码，而MERZ取自意大利歌女Alessia Merz的名字
  port 6379
4. 绑定的主机地址
  bind 127.0.0.1
  5.当 客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能
  timeout 300
5. 指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为verbose
  loglevel verbose
6. 日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则日志将会发送给/dev/null
  logfile stdout
7. 设置数据库的数量，默认数据库为0，可以使用SELECT <dbid>命令在连接上指定数据库id
  databases 16
8. 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
  save <seconds> <changes>
  Redis默认配置文件中提供了三个条件：
  save 900 1
  save 300 10
  save 60 10000
  分别表示900秒（15分钟）内有1个更改，300秒（5分钟）内有10个更改以及60秒内有10000个更改。

9. 指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
  rdbcompression yes
10. 指定本地数据库文件名，默认值为dump.rdb
  dbfilename dump.rdb
11. 指定本地数据库存放目录
   dir ./
12. 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
   slaveof <masterip> <masterport>
13. 当master服务设置了密码保护时，slav服务连接master的密码
   masterauth <master-password>
14. 设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过AUTH <password>命令提供密码，默认关闭
   requirepass foobared
15. 设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息
   maxclients 128
16. 指定Redis最大内存限制，Redis在启动时会把数据加载到内存中，达到最大内存后，Redis会先尝试清除已到期或即将到期的Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis新的vm机制，会把Key存放内存，Value会存放在swap区
   maxmemory <bytes>
17. 指定是否在每次更新操作后进行日志记录，Redis在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为 redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no
   appendonly no
18. 指定更新日志文件名，默认为appendonly.aof
   appendfilename appendonly.aof
19. 指定更新日志条件，共有3个可选值： 
   no：表示等操作系统进行数据缓存同步到磁盘（快） 
   always：表示每次更新操作后手动调用fsync()将数据写到磁盘（慢，安全） 
   everysec：表示每秒同步一次（折衷，默认值）
   appendfsync everysec

20. 指定是否启用虚拟内存机制，默认值为no，简单的介绍一下，VM机制将数据分页存放，由Redis将访问量较少的页即冷数据swap到磁盘上，访问多的页面由磁盘自动换出到内存中（在后面的文章我会仔细分析Redis的VM机制）
   vm-enabled no
21. 虚拟内存文件路径，默认值为/tmp/redis.swap，不可多个Redis实例共享
   vm-swap-file /tmp/redis.swap
22. 将所有大于vm-max-memory的数据存入虚拟内存,无论vm-max-memory设置多小,所有索引数据都是内存存储的(Redis的索引数据 就是keys),也就是说,当vm-max-memory设置为0的时候,其实是所有value都存在于磁盘。默认值为0
   vm-max-memory 0
23. Redis swap文件分成了很多的page，一个对象可以保存在多个page上面，但一个page上不能被多个对象共享，vm-page-size是要根据存储的 数据大小来设定的，作者建议如果存储很多小对象，page大小最好设置为32或者64bytes；如果存储很大大对象，则可以使用更大的page，如果不 确定，就使用默认值
   vm-page-size 32
24. 设置swap文件中的page数量，由于页表（一种表示页面空闲或使用的bitmap）是在放在内存中的，，在磁盘上每8个pages将消耗1byte的内存。
   vm-pages 134217728
25. 设置访问swap文件的线程数,最好不要超过机器的核数,如果设置为0,那么所有对swap文件的操作都是串行的，可能会造成比较长时间的延迟。默认值为4
   vm-max-threads 4
26. 设置在向客户端应答时，是否把较小的包合并为一个包发送，默认为开启
   glueoutputbuf yes
27. 指定在超过一定的数量或者最大的元素超过某一临界值时，采用一种特殊的哈希算法
   hash-max-zipmap-entries 64
   hash-max-zipmap-value 512
28. 指定是否激活重置哈希，默认为开启（后面在介绍Redis的哈希算法时具体介绍）
   activerehashing yes
29. 指定包含其它的配置文件，可以在同一主机上多个Redis实例之间使用同一份配置文件，而同时各个实例又拥有自己的特定配置文件
   include /path/to/local.conf



## 5、Redis 的持久化

### 5.1、RDB（Redis Database）

**RBD：在指定时间间隔内，将内存中的数据集快照写入磁盘**，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接写到内存里；

**RDB是什么**？：Redis会单独创建（fork）一个子进程进行持久化，会将数据写入一个临时性文件，待持久化结束，会将临时文件替换上次持久化号的文件。

整个过程，主进程不会进行任何IO操作，这确保了极高的性能。

如果需要进行大规模的数据恢复，对数据的完整性不是特别敏感，那么RDB比AOF更加的高校.。RBD的缺点是最后一次持久化的数据可能会丢失。

**Fork是什么**？复制一个与当前进程一样的进程。新进程的所有数据（变量、环境变量、程序计数器等）数值都和远进程一致，但是是一个全新的进程，并作为原进程的子进程。

**RBD保存的是dump.rdb文件**

**配置文件的位置**：dir 的位置，当前执行的命令的位置，默认是./

**==备份机器一定要与生产机器分开备份==**

**save/bgsave:**

save：只管保存，线程全部阻塞

bgsave:后台异步操作备份；

**flushall:**也会产生dump.rdb文件，但里面是空的。无意义。

**==如何恢复数据：==**将备份文件（dump.rdb）移动到redis安装目录并启动服务即可；通过**config get dir** 获取目录；

>****
>
>RBD优点：
>
>- 适合大规模的数据恢复
>- 对数据完整性和一致性要求不高
>
>RBD缺点：
>
>- 在一定间隔时间做一次备份，如果redis意外down的话，就会丢失最后一次快照后的所有修改数据
>- Fork的时候，内存中的数据被克隆了一份，大致2倍的膨胀性内存空间需要考虑

**如何停止：动态的停止所有RDB保存规则的方法：redis-cli config set save "";**



### 5.2、AOF（Append Only File）

**AOF是什么？以日志的形式来记录每个写操作**，将redis执行过的所有指令记录下来（读操作不记录），只许追加文件，但不可以改写文件。换而言之**redis会在重启后根据日志文件的内容将指令从前到后依次执行一遍。来进行恢复数据的工作**。

**AOF启动：修复、恢复**：

>1. appendonly no:默认是no，yes就是开启aof持久化，将数据的aof文件复制（config get dir）到对应的目录，重启redis，重新加载数据；
>2. 备份文件坏掉：redis-check-aof  --fix xxxx.aof进行修复，然后重启Redis恢复数据。
>
>

**Rewrite:**AOF采用文件追加方式，文件会越来越大，为了避免这种情况，新增了重写机制。当**AOF**文件的大小达到所设定的阈值时，**Redis**就会启动**AOF**文件的内容压缩，只保留可以恢复数据的最小指令，可以使用命令**bgrewriteaof**;

**重写原理:** AOF文件持续增长而过大时，会fork出一条新进程来将文件重写（也就是先写临时文件，最后再rename）,遍历新进程的内存中数据，每条记录有一条的set语句，重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照优点类似。

**触发机制：Redis会记录上次重写时的aof大小，默认配置是当aof文件大小是上次rewrite后大小的一倍且文件大于64M时触发。**



AOF优劣势：

>1. 优势：
>   - appendfsync Always：同步持久化，每次发生数据变化都会被立即记录到磁盘，性能较差，但是数据完整性比较好
>   - appendfsync  Everysec:出厂默认推荐，异步操作，每秒记录，如果一秒内宕机，有数据丢失。这归功于Redis引入了BIO线程，所有fsync操作都异步交给了BIO线程。
>   - appendfsync  No:从不同步
>2. 劣势：
>   - 相同数据集的数据而言，aof文件要远远大于rdb文件，恢复速度慢与rdb;
>   - Aof运行效率慢与rdb，每秒同步策略较好，不同步策略和rdb相同。
>
>

### 5.3、Redis持久化选择

>1. RDB持久化方式能够在指定时间间隔内，对数据进行快照存储
>2. Aof持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以Redis协议追加保存每次写的操作到文件末尾。Redis还能对AOF文件进行后台重写，使得文件的体积不至于过大。
>3. **只做缓存：如果希望数据在服务器运行的时候存在，亦可以不使用任何持久化方式。**
>4. 同时开启两种持久化方式。
>   - 通常情况下，**当Redis重启的时候会优先加载AOF文件的方式来恢复原始的数据**，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整。
>   - RDB的数据不实时，**同时RDB与AOF两者存在**,因为RDB更适合备份数据库。
>
>

## 6、Redis 的事务

Redis事务是什么？可以一次执行多个命令，本质是一组命令的集合，一个事务中的所有命令都会被序列化，**按顺序的串行化执行，而不会被其他的命令插入**，不许加塞。

命令：DISCARD、EXEC、MULTI、UNWATCH、WATCH

>1. DISCARD:取消事务，放弃执行事务块内的所有命令
>2. EXEC：执行所有事务块的命令
>3. MULTI：开启事务
>4. WATCH:悲观锁、乐观锁、CAS（check and set）
>   - 悲观锁：表示在每次操作数据更新前，都会去加锁。
>   - 乐观锁：表示在每次操作数据前，不会加锁，但是会增加一个版本号等机制；策略：提交的版本必须大于记录当前版本才能执行更新；
>   - CAS：
>5. UNWATCH：取消WATCH命令对所有的key的监控。
>
>

Redis对事务的支持是部分支持，如果是命令异常，则是全体不执行，如果是运行期异常，则是其他的命令执行，错误命令报错。





##7、Redis 的发布订阅

进程间的一种消息通信模式：发送者（pub）发送消息，订阅者（sub）接受消息；

SUBSCRIBE:订阅消息，可以订阅多个

PUBLISH：发布消息

PSUBSCRIBE NEW*：通配符星号订阅多个，通配符星号





##8、Redis 的复制（master/slaver）

### 8.1、简介

>行话：**主从复制，主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，Maste以写为主，Slave以读为主；**

### 8.2、作用

1. 读写分离
2. 容灾备份

### 8.3、如何使用？



>1. 配从（库）不配主（库）
>
>2. 从库配置：slaveof  主库IP 主机端口
>
>   - 每次与master断开之后，都需要重新连接，除非配置进redis.conf文件
>   - Info replication：查看机器信息
>
>3. 修改配置文件细节操作
>
>   - 拷贝多个redis.conf文件
>   - 开启daemonize yes
>   - pid:文件名字
>   - port:指定端口
>   - Log文件名字：指定日志文件名字
>   - Dump.rdb名字：指定dump.rdb名字
>
>4. 常用3招
>
>   - 一主二仆
>
>     - Init：
>
>     - 一个Master两个Slave：
>
>     - 日志查看
>
>     - 主从问题演示
>
>       >1. 通过slaveof  ip  port,设置主机地址和端口，切换成slave角色
>       >2. 从机会把主机的所有命令都执行一遍
>       >3. 主机能进行读写操作，从机只能进行读操作
>       >4. 主机down掉后，slave原地待命，不进行数据修改操作，角色不变,直至master重启恢复
>       >5. 从机down掉后，启动会重新恢复master角色
>       >
>       >
>
>   - 薪火相传（去中心化）
>
>     - 上一个slave可以是下一个slave的master,slave同样可以接受其他slaves的连接和同步请求，这样可以作为链条中下一个slave的master,可以有效减轻master的写压力；
>     - 会清楚之前的数据，重新建立拷贝最新的
>     - slaveof  新主库ip  新主库端口
>
>   - 反客为主
>
>     - slaveof no one:使当前数据库停止与其他数据库的同步，转为主数据库
>
>  
>
>

### 8.4、复制原理

1. slave启动成功连接到master后会发送一个sync命令
2. master接到命令启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，master将传送整个数据文件到slave，以完成一次完全同步
3. 全量复制:而slave服务在接受到数据库文件数据后，将其存盘并加载到内存中。
4. 增量复制：master继续将新的所有收集到的修改命令依次传给slave,完成同步
5. 但是只要是重新连接master，一次完全同步（全量复制）将被自动执行



### 8.5、哨兵模式（ sentinel)

1. 简介

   模式:反客为主的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库。

2. 如何使用？

   步骤：

   1. 一主（master)两从（slave)

   2. 自定义的/myredis目录下新建**==sentinel.conf==**文件，名字绝不能错。

   3. 配置哨兵内容，填写如下内容: vim   centinel.conf

      ```shell
      #sentinel monitor  被监控数据库名字（自己起名字） 127.0.0.1 6379 1
      #后面的1表示：主机挂掉后slave投票看让谁接替成为主机，得票数多少后成为主机
      sentinel monitor host6379 127.0.0.1 6379 1
      ```

   4. 启动哨兵：redis-sentinel /myredis/sentinel.conf

   5. 一组sentinel能同时监控多个master

3. 复制的缺点：

   由于所有的写操作都是先在master操作，然后同步更新到slave上，所以master同步到slave机器有一定的延迟，当系统很忙的时候，延时问题会更加严重，slave机器数量的增加也会使这个问题更加严重。



##9、Redis 的java客户端jedis

1. Jedis所需要的jar包：Commons-pool-1.6.jar、jedis-2.1.0.jar

2. jedis的常规操作：

   - 测试连通性

     ```java
     Jedis jedis=new Jedis("127.0.0.1","6379");
     System.out.println(jedis.Ping());
     //结果
     pong
     ```

     

   - 日常和事务

     ```java
     Jedis jedis=new Jedis("127.0.0.1","6379");
     //开启事务
     Transaction transaction=jedis.multi();
     //设置命令
     transaction.set("k1","v1");
     transaction.set("k2","v2");
     //提交事务
     transaction.exec();
     
     //放弃事务
     transaction.discard();
     ```

     ```java
     public static void mian(String [] args){
          TestTx   tx=new TestTx();
          boolean b=tx.transMethod();
          System.out.println(b);
         
     }
     
     public boolean transMethod(){
         Jedis jedis=new Jedis("127.0.0.1","6379");
         int balance;
         int debt;
         int amtToSubtract=10;
         //添加锁
         jedis.watch("balance");
         //模拟分布式高并发锁
         Thread.sleep(7000);
         balance=Integer.parseInt(jedis,get("balance"));
         if(balance<amtToSubtract){
             jedis.unwatch();
             System.out.println("modify");
             return false
         }else{
             System.out.println("..........transaction");  
             //开启事务
             Transaction transaction=jedis.multi();
             //设置命令
             transaction.decrby("balance",amtToSubtract);
             transaction.set("debt",amtToSubtract);
             //提交事务
             transaction.exec();
             return true;
         }
     }
     ```

     

   - java中jedis池子（单例模式）：jedisPoolUtil

     ```java
     //单例双端解锁,保证线程不会被篡改
     public class JedisPoolUtil{
         
         private final String host="127.0.0.1";
         private final Integer port="6379";
         
         private static volatile JedisPool jedisPool=null;
             
         private JedisPoolUtil(){};
         
         public static JedisPool getJedisPoolInstance(){
             
             if(null == jedisPool){
                 synchronized(JedisPoolUtil.class){
                     if(null == jedisPool){
                         JedisPoolConfig poolConfig=new JedisPoolConfig();
                         //最大连接数
                         poolConfig.setMaxActive(1000);
                         //当剩余多少空闲连接的时候，就需要创建新的连接
                         poolConfig.setMaxIdle(32);
                         poolConfig.setMaxWait(100);
                         //获得一个连接是否需要检测可用性
                         poolConfig.setTestOnBorrow(true);
                         
                         jedisPool=new JedisPool(poolConfig,host,port);                       
                     }
                 }
             }
             
             return jedisPool;
         }
         
         //释放连接
         public static void release(JedisPool jedisPool，Jedis jedis){
             if(null!=jedis){
                 jedisPool.returnResourceObject(jedis);
             }
         }
             
     }
     
     //测试连接
     public class TestPool{
         public static void main(String [] args){
             JedisPool jedisPool=JedisPoolUtil.getJedisPoolInstance();
             
             Jedis jedis=null;
             
             try{
                 jedis=jedisPool.getResource();
                 jedis.set("aa","bb");
             }catch(Exception e){
                 e.printStackTrace();
             }finally{
                 JedisPoolUtil.release(jedisPool,jedis);
             }
         }
     }
     ```

     

     



# 二、Redis 分布式锁

## 1、简介

为什么需要锁?

>1. 多任务环境中才需要
>2. 任务都需要对同一共享资源进行写操作
>3. 对资源的访问是互斥的

Tips:任务通过竞争获取锁才能对资源进行操作（**==①竞争锁==**），当有一个任务在对资源进行更新时（**==②占有锁==**），其他任务都不可以对这个资源进行操作（**==③任务阻塞==**），直到该任务完成更新（**==④释放锁==**）；

线程不安全的原因：

>1. 共享资源的操作非原子性
>
>2. 共享资源存放在堆中，共享资源的可见性（类成员变量）
>
>   
>
>

线程不安全的特征：

>1. 每次运行结果都不一样
>2. 每次运行结果都不符合预期结果
>
>

代码如下：

```java
package com.ln.demo.lock;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RedisLock {

    //100张票
    private int count=100;


    @Test
    public void ticketTest() throws InterruptedException {
        TicketRunnable tr=new TicketRunnable();

        //四个线程对应四个窗口
        Thread t1=new Thread(tr,"窗台A");
        Thread t2=new Thread(tr,"窗台B");
        Thread t3=new Thread(tr,"窗台C");
        Thread t4=new Thread(tr,"窗台D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        Thread.currentThread().join();

    }

    //线程模拟一个窗口买火车票
    public class TicketRunnable implements Runnable{

        @Override
        public void run() {
            while (count>0){
                if (count>0){
                    System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

```

解决方案：

1. 添加synchronized ,同步代码块

2. 添加AtomicInteger，将count变量变成原子性变量

3. 添加锁lock

   ```java
   //引入juc包，是jdk所有线程和并发的包
     private Lock lock=new ReentrantLock();
   
   //添加锁
   lock.lock();
   try {
       if (count>0){
           System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
       }
   } catch (Exception e) {
       e.printStackTrace();
   }finally {
       //释放锁，为什么不在异常代码块，防止异常退出
       lock.unlock();
   }
   ```

**lock与synchronized 的区别:lock比synchronized 更加的灵活，有多种情况下加锁，并不是一定要和线程耗着**。

## 2、分布式锁解决方案

**分布式方案比较：**

| 方案                    | 实现思路                                                     | 优点                                     | 缺点                                                         |
| ----------------------- | ------------------------------------------------------------ | ---------------------------------------- | ------------------------------------------------------------ |
| 利用mysql的实现方案     | 利用数据库自身提供的锁机制实现，要求数据库支持行级锁。       | 实现简单，稳定可靠                       | 性能差，无法适应高并发场景；容易出现死锁的情况；无法优雅的实现阻塞式锁； |
| 利用redis的实现方案     | 基于redis的setnx命令实现，并通过lua脚本保证解锁时对缓存操作序列的原子性 | 性能好                                   | 实现相对较复杂，无法优雅的实现阻塞式锁；                     |
| 利用zookeeper的实现方案 | 基于zk的节点特性以及watch机制实现                            | 性能好，稳定可靠性，能较好的实现阻塞式锁 | 实现相对复杂                                                 |



方案1:利用mysql:通过对数据库的id锁，来实现分布式锁。mysql的性能峰值是：300-700（机械硬盘-固态硬盘）

1. 首先逆向工程创建mapper

   - generatorConfig.xml

     ```xml
     <?xml version='1.0' encoding='UTF-8'?>
     <!DOCTYPE generatorConfiguration
             PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
             "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
     
     <generatorConfiguration>
         <context id="mybatisGenerator" targetRuntime="MyBatis">
             <commentGenerator>
                 <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
                 <property name="suppressAllComments" value="true" />
             </commentGenerator>
             <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->
             <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                             connectionURL="jdbc:mysql://localhost:3306/mybatisGenerator?
                                     serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true" userId="root"
                             password="12345">
             </jdbcConnection>
     
             <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和
                 NUMERIC 类型解析为java.math.BigDecimal -->
             <javaTypeResolver>
                 <property name="forceBigDecimals" value="false" />
             </javaTypeResolver>
     
             <!-- targetProject:生成PO类的位置 -->
             <javaModelGenerator targetPackage="pojo"
                                 targetProject=".\src">
                 <!-- enableSubPackages:是否让schema作为包的后缀 -->
                 <property name="enableSubPackages" value="false" />
                 <!-- 从数据库返回的值被清理前后的空格 -->
                 <property name="trimStrings" value="true" />
             </javaModelGenerator>
             <!-- targetProject:mapper映射文件生成的位置 -->
             <sqlMapGenerator targetPackage="mapper"
                              targetProject=".\src">
                 <!-- enableSubPackages:是否让schema作为包的后缀 -->
                 <property name="enableSubPackages" value="false" />
             </sqlMapGenerator>
             <!-- targetPackage：mapper接口生成的位置 -->
             <javaClientGenerator type="XMLMAPPER"
                                  targetPackage="mapper"
                                  targetProject=".\src">
                 <!-- enableSubPackages:是否让schema作为包的后缀 -->
                 <property name="enableSubPackages" value="false" />
             </javaClientGenerator>
             <!-- 指定数据库表 -->
             <table tableName="items"></table>
             <table tableName="orders"></table>
             <table tableName="orderdetail"></table>
             <table tableName="user"></table>
     
             <!-- 有些表的字段需要指定java类型
              <table schema="" tableName="">
                 <columnOverride column="" javaType="" />
             </table> -->
         </context>
     </generatorConfiguration>
     ```

     ```java
     //100张票
         private int count=100;
     
         //引入juc包，是jdk所有线程和并发的包
         @Resource(name="mysqlock")
         private Lock lock;
     
         @Test
         public void ticketTest() throws InterruptedException {
             TicketRunnable tr=new TicketRunnable();
     
             //四个线程对应四个窗口
             Thread t1=new Thread(tr,"窗台A");
             Thread t2=new Thread(tr,"窗台B");
             Thread t3=new Thread(tr,"窗台C");
             Thread t4=new Thread(tr,"窗台D");
     
             t1.start();
             t2.start();
             t3.start();
             t4.start();
             Thread.currentThread().join();
     
         }
     
         //线程模拟一个窗口买火车票
         public class TicketRunnable implements Runnable{
     
             @Override
             public void run() {
                 while (count>0){
     
                     lock.lock();
                     try {
                         if (count>0){
                             System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }finally {
                         lock.unlock();
                     }
     
     
                     try {
                         Thread.sleep(50);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
     ```

     代码：

     ```java
     package com.ln.demo.lock;
     
     import com.ln.demo.mapper.LockMapper;
     import org.springframework.stereotype.Service;
     
     import javax.annotation.Resource;
     import java.util.concurrent.TimeUnit;
     import java.util.concurrent.locks.Condition;
     import java.util.concurrent.locks.Lock;
     
     //@Service
     public class MysqlLock implements Lock {
     
         private static final int ID_NUM=1;
     
     
         @Resource
         private LockMapper mapper;
     
         @Override
         //阻塞式加锁
         public void lock() {
     
             //1.尝试加锁
             if (tryLock()){
                 return;
             }
             //2.加锁失败，当前任务休眠一段时间
             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             //3.递归调用，再次从新加锁
             lock();
         }
     
     
         @Override
         //非阻塞式加锁。往数据库写入id为1的数据，能写成功的即为加锁成功
         public boolean tryLock() {
     
             try {
                 mapper.insert(ID_NUM);
             } catch (Exception e) {
                 return false;
             }
             return true;
         }
     
     
         @Override
         public void unlock() {
             mapper.deleteByPrimaryKey(ID_NUM);
         }
     
         @Override
         public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
             return false;
         }
         @Override
         public Condition newCondition() {
             return null;
         }
         @Override
         public void lockInterruptibly() throws InterruptedException {
     
         }
     
     }
     
     ```

     

     

     

     ```java
     //100张票
         private int count=100;
     
         //引入juc包，是jdk所有线程和并发的包
         @Resource(name="mysqlLock")
         private Lock lock;
     
         @Test
         public void ticketTest() throws InterruptedException {
             TicketRunnable tr=new TicketRunnable();
     
             //四个线程对应四个窗口
             Thread t1=new Thread(tr,"窗台A");
             Thread t2=new Thread(tr,"窗台B");
             Thread t3=new Thread(tr,"窗台C");
             Thread t4=new Thread(tr,"窗台D");
     
             t1.start();
             t2.start();
             t3.start();
             t4.start();
             Thread.currentThread().join();
     
         }
     
         //线程模拟一个窗口买火车票
         public class TicketRunnable implements Runnable{
     
             @Override
             public void run() {
                 while (count>0){
     
                     lock.lock();
                     try {
                         if (count>0){
                             System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }finally {
                         lock.unlock();
                     }
     
     
                     try {
                         Thread.sleep(50);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
     ```

     

   

2. 关于Redis分布式锁的基础知识

   >1. **缓存有效期**
   >
   >   redis的数据，不一定都是持久化的；给定key设置的生存时间，当key过期时，它会被自动删除；
   >
   >2. SETNX命令
   >
   >   SETNX key value,将key的值设为value,当且仅当key不存在。若key已经存在，则setnx不做任何操作。setnx是【set if not exists】(如果不存在，则set)的简写
   >
   >3. lua脚本
   >
   >   轻量小巧的脚本语言，用于支持redis操作序列的原子性；
   >
   >

**Redis加锁的正确方式**

1. ==**加锁(原子性)：通过setnx向特定的key写入一个随机值，并同时设置失效时间，写值成功即加锁成功**==。set key value nx px 30000;

   - 必须给锁设置一个失效时间。避免死锁。
   - 加锁时，每个节点产生一个随机字符串。避免误删。
   - 写入随机值与设置失效时间必须是同时的；保证加锁时原子的

   

2. 解锁：匹配随机值，删除redis上的特点key数据，**==要保证获取数据、判断一致以及删除数据三个操作是原子==**的；

   执行如下lua脚本：

   ```lua
    if redis.call("get",KEYS[1])==ARGV THEN
       return redis.call("del",KEYS[1])
    else 
       return 0
    end
   ```

   

   

   













