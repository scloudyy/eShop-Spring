# Mysql主从同步

## Master

1. 配置my.cnf
```
vim /etc/mysql/my.cnf

[mysqld]
server-id = 1
log-bin = master-bin
 log-bin-index = master-bin.index
```

2. 重启mysql
```
service mysqld restart

OR

docker restart mysql_master
```
	
3. 设置slave账户

```
create user 'repl'@'%' identified with mysql_native_password by 'password';

grant replication slave on *.* to 'repl'@'%';

flush privileges;
```
	
4. 获取master信息

```
show master status;  # 表单中file和position在slave中会用到
```

## Slave

1. 配置my.cnf 
```
vim /etc/mysql/my.cnf

[mysqld]
server-id = 2
relay-log = slave-relay-bin
relay-log-index = slave-relay-bin.index
```
2. 重启mysql
3. 设置master
```
change master to master_host='172.17.0.1',master_port=3308,master_user='repl',master_password='password',master_log_file='master-bin.000001',master_log_pos=0;
# 注意master_port不加引号。如果mysql运行在docker中，那么master的host是宿主机的桥接IP即docker0，port是宿主机映射到容器中master mysql 3306的那个端口
```
4. 启动
```
start slave
```
5. 查看状态

```
show slave status\G;  # \G代表竖列显示
```

# Spring读写分离

MySQL配置主从数据库后，需要配置Spring和Mybatis使其向从库请求读操作，向主库请求写操作或者任何事物操作（事物涉及读写）

在之前的配置中只有一个dataSource，指向一个数据库。为了实现读写分离，首先编写DynamicDataSource类，其继承AbstractRoutingDataSource，是一个具有路由功能的抽象DataSource。实现其继承AbstractRoutingDataSource中的determineCurrentLookupKey方法该类就能够在每次需要连接数据库时根据返回的lookupKey连接不同的数据库。

determineCurrentLookupKey方法直接向DynamicDataSourceHolder查询并放回lookupKey。DynamicDataSourceHolder主要维护一个ThreadLocal变量，里面存放了当前的lookupKey，该变量时线程安全的。

DynamicDataSourceHolder里的lookupKey是由DynamicDataSourceInterceptor来更新的，其继承了Mybatis的拦截器Interceptor。当有新的Mybatis请求发起时，拦截器会拦截该请求，并判断该请求时读操作还是写操作，以此更新DynamicDataSourceHolder。

在spring-dao.xml中，首先分别配置主从库的bean，然后添加DynamicDataSource类的bean，并将主从库的bean作为参数注入targetDataSources这一map中。最后将DynamicDataSource的bean注入LazyConnectionDataSourceProxy的bean中作为真正的dataSource，因为只有等到SQL语句正式执行时才能指定dataSource，所以需要懒加载

在mybatis-config.xml中，需要将DynamicDataSourceInterceptor配置为Mybatis的plugin。