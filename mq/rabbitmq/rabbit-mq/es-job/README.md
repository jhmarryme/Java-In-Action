
引用该模块时，需要以下配置

开启job的相关配置
```yml
spring:
  profiles:
    active: esjob
```

对es-job配置zookeeper地址
```yml
elasticjob:
  reg-center:
    namespace: elastic-job
    server-lists: 1.14.140.53:30061,1.14.140.53:30062,1.14.140.53:30063
```