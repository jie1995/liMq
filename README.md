# liMq
Netty+BlockingQueue实现的简单mq

### 说明：
  * 请求分发：基于spi机制查找对应的类及方法，通过反射调用
  
  * ~~序列化协议：自定义JSON格式序列化~~
  
  * 基于TCP长连接的
  
  * 自定义加码解码特定请求报文，支持对象传输（参考自闪电侠）
     * 请求参数格式不对(含有null值)，传输时加码会失败
  * LengthFieldBasedFrameDecoder处理粘包
     * 响应含有中文会超过处理长度
  * 拉取消费信息后，如何和请求对应起来？
     * LinkedHashMap维护统一的messageId;
   
### 待解决：
   1. 如何整合spring？
   
###其他：
    * 消息的投递的可靠不可靠模式
    * 测试高效和非高效模式的性能差异


 