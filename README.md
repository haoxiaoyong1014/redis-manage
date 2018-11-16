#### redis-manage 

#### Redis的后台管理

**最终展示效果图**

![image](https://github.com/haoxiaoyong1014/redis-manage/raw/master/redis-manage.gif)

**环境**  

* springboot 2.0.1.RELEASE   

* redis Lettuce版本

* 前端 Vue.js

> 目前java操作redis的客户端有jedis跟Lettuce。在springboot1.x系列中，
  其中使用的是jedis,但是到了springboot2.x其中使用的是Lettuce。
  因为我们的版本是springboot2.x系列，所以使用的是Lettuce
  
**相关依赖:**

```$xslt
       <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
   
        <!--spring-data-redis-->
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
       </dependency>
        
       <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
       </dependency>      
```

**主要配置**

```properties
redis.name=redis
redis.index=0
spring.redis.host=127.0.0.1
spring.redis.password=
spring.redis.port=6379
#连接超时时间（毫秒）
spring.redis.timeout=10000
#Redis默认情况下有16个分片，这里配置具体使用的分片，默认是0
spring.redis.database=0
#连接池最大连接数（使用负值表示没有限制） 默认 8
spring.redis.lettuce.pool.max-active=8
#连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
spring.redis.lettuce.pool.max-wait=-1
#连接池中的最大空闲连接 默认 8
spring.redis.lettuce.pool.max-idle=8
#连接池中的最小空闲连接 默认 0
spring.redis.lettuce.pool.min-idle=0
```
**所具备的功能**

* 管理redis数据
     * 管理所有的 key(字符串，列表，哈希，集合，有序集)
     * 更新redis数据  
     * 按键搜索redis数据   
     * 支持分页查询redis数据
     * 根据指定的key列出其value(集合,哈希,字符串),根据需要可以进行增加 
* 切换服务
    * addServer(填写 host,password,port即可切换另一个redis服务,不用手动更改配置文件)   
       
##### 具体实现逻辑代码就不贴出来了,使用之前先配置默认的 host,password

**项目地址**

* 后端项目地址: https://github.com/haoxiaoyong1014/redis-manage 

* 前端项目地址: https://github.com/haoxiaoyong1014/redis-manage-view

**启动前端项目**

* 安装node

* 进入前端的根目录运行 `npm install`,稍等片刻之后运行`npm run dev`,在浏览器输入控制台打印的地址即可看到视图



