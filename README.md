# liMq
netty+blockingQueue实现的简单mq

### 说明：
  * 请求分发：基于spi机制查找对应的类及方法，通过反射调用
  * 序列化协议：自定义JSON格式序列化
  * 基于TCP长连接的
   
### 待解决：
   1. 如何整合spring？
   2. 客户端请求调用封装 
    
   
###其他：
    * 消息的投递的可靠不可靠模式
    * 测试高效和非高效模式的性能差异


 