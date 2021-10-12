
引用该模块时， 需要以下配置

引入以下依赖
```xml
 <dependency>
    <groupId>com.jhmarryme</groupId>
    <artifactId>rabbit-producer</artifactId>
    <version>0.0.1</version>
</dependency>
```

rabbitmq 连接相关配置
```yml
spring:
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    default-property-inclusion: NON_NULL
    time-zone: GMT+8
  rabbitmq:
    addresses: 1.14.140.53:30050
    connection-timeout: 15000
    password: 541224
    username: jhmarryme
    virtual-host: /
    publisher-returns: true
    publisher-confirm-type: simple
```