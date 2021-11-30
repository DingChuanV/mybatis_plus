#数据库插入的id的默认值为：全局唯一id
##1.主键的生成策略
    1.1 雪花算法生成 
        分布式系统唯一id生成的方案
        其核心思想就是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit是机器的id），
        12bit作为毫秒内的流水号（意味着每个节点在每毫秒数可以产生4096个id），最后还有一个符号位，永远是0。
##2.自动填充
    1.创建时间、修改时间！这些操作都是自动化完成的，我们不希望手动更新！
    gmt_create gmt_modified 几乎说所有的表都要配置上。而且需要自动化。
    方式一：数据库级别
    在表中新增字段create_time,update_time
##3.乐观锁
    乐观锁：顾名思义十分乐观，他总认为不会出现问题，无论干什么不去上锁！如果出现了问题，在次更新值测试
    悲观锁：顾名思义十分悲观，他总是认为出现问题，无论干什么都会上锁。再去操作
    1.当要更新一条记录的时候，希望这条记录没有被别人更新
        乐观锁实现方式：
        -取出记录时，获取当前version
        -更新时，带上这个version
        -执行更新时， set version = newVersion where version = oldVersion
        -如果version不对，就更新失败
    ```A
        update user set name="uin" ,version=version+1
        where id=2 and version=1;
    ```
    ```B 线程抢先完成，这个时候version=2 ，会导致A修改失败
        update user set name="uin" ,version=version+1
        where id=2 and version=1;
    ```


