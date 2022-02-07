
注册中心为eureka-server, http://127.0.0.1:20000/eureka

-------------------------------------------------------
需要先启动eureka-client服务

feign-consumer
调用的是eureka-client服务, 并自己维护feign的接口

------------------------------------------------

feign-client
被调用的接口服务

feign-client-intf
声明feign的接口, client去实现该接口, consumer通过引用接口依赖, 就可以直接调用接口在client中的实现

feign-consumer-advanced
调用的是feign-client服务