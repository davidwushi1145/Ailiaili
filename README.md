# Ailiaili

**基于 Spring Boot 的仿bilibili全栈项目**

[前端项目链接](https://github.com/davidwushi1145/bilibili_front)

Java+Springboot+Mysql+JWT+FastJson+Mybatis Plus+RocketMQ+elasticSearch+Redis+mahout+kibana

---

## 什么是Ailiaili?

一个方便、实用的在线视频创作、发布、观看平台。该平台是一个以在线视频为主的社区平台，提供多样化的内容，以弹幕评论系统、用户互动为特色

- 📝 **四大核心** - 本项目实现了四大核心功能：普通用户、内容创作者、广告主、平台管理员
- 🎨 **强大的防XSS能力** - 用户模块运用三种算法（RSA，MD5，AES）实现 JWT-Token 登录
- 🧑‍💻 **良好的用户体验** - 将登录模式进一步改造成双令牌登录（RefreshToken），实现自动刷新Token，大大提升了用户体验
- 🤹 **精细化且可拓展的数据库设计** - 使用自定义注解以及数据库表设计（用户表，权限表，角色表，关联表），设计了一套可拓展的权限体系，实现页面，接口，数据等层面的精细化权限控制
- 🎥 **优化的响应速度** -使用Redis缓存动态相关信息，提升了动态列表以及视频流的响应速度与实时性，降低数据库压力
- 📤 **消息异步** - 使用RocketMQ实现动态消息异步推送，视频文件信息异步存储，数据库异步更新等，提升了动态发送，视频上传的速度，大大降低了服务器的压力
- 🛠 **精确且丰富的报表及搜索系统** - 使用ElasticSearch实现全文检索并且高亮搜索字段，让用户拥有良好的搜索体验，使用ElasticSearch+kibana完成报表系统，支持多种数据展示模式

- 📝 **个性化推荐系统** - 使用Mahout推荐算法量化用户行为，根据用户喜好进行个性化推荐
- 🎨 **真—实时系统** - 使用WebSocket实现长连接，发送实时弹幕，进行在线观看人数统计，进行站内信发送
- 🧑‍💻 **良好的用户体验** - 将登录模式进一步改造成双令牌登录（RefreshToken），实现自动刷新Token，大大提升了用户体验
- 🤹 **优秀的适应能力** - 基于B/S架构实现，必须采用响应式设计，适配不同尺寸的移动设备。
- 🎥 **强大的可迁移性** -项目高度模块化，可迁移至spring cloud或k8s

<img src="https://raw.githubusercontent.com/davidwushi1145/photo2/d07bcd4a020cfa5de9b7a52aafa094abcc2254d1/123.png" alt="123"/>

## 项目启动方法

### 本地开发

#### 使用sql文件夹内的脚本创建bilibili数据库

![image-20240102181743140](https://raw.githubusercontent.com/davidwushi1145/photo2/main/image-20240102181743140.png)

#### 安装docker

##### 安装rocketmq

自行拉取最新镜像安装即可:如https://hub.docker.com/r/xuchengen/rocketmq

或者参考：https://wushiqwq.cn/archives/663

##### 安装elasticsearch

自行拉取最新镜像安装即可:项目开发测试使用的为elasticsearch:7.13.1

##### 安装kibana

自行拉取最新镜像安装即可:项目开发测试使用的为kibana:7.13.1

#### 自行配置application.properties

填写腾讯云cos的配置

```
cos.client.accessKey=
cos.client.secretKey=
cos.client.region=
cos.client.bucket=
```

或者选择使用FastDFS

配置好其他参数

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bilibili?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.username=root

spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.max-lifetime=120000

spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=

spring.servlet.multipart.max-file-size=512MB
spring.servlet.multipart.max-request-size=1024MB

spring.application.name=bilibili
#spring.profiles.active=dev
#logging.level.org.springframework.data=DEBUG
logging.level.com.bilibili.api=DEBUG
logging.level.com.bilibili.service=DEBUG

mybatis-plus.mapper-locations=classpath:mapper/*.xml
mybatis-plus.type-aliases-package=mapper

fdfs.http.storage-addr=http://localhost:8080
fdfs.tracker-list=localhost:22122

rocketmq.name.server.address= 127.0.0.1:9876
rocketmq.producer.group=xfg-group
rocketmq.consumer.group=xfg-group


cos.client.accessKey=
cos.client.secretKey=
cos.client.region=
cos.client.bucket=

spring.elasticsearch.url=127.0.0.1

```

## 贡献

如存在bug，欢迎提交issue及pull requst
