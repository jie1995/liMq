# liMq
java实现的简单mq

##下一步：
* 用netty建立服务端和客户端的通信
* 实现队列消息的投递的高效和非高效模式
* 测试高效和非高效模式的性能差异
* 做消费端
* 开始做客户端:
   随着客户端的启动开启主线程
   主线程轮训，通过注解获取监听队列
 