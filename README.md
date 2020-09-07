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
  * springboot自动装配
   
### 待解决：
   1. 服务端整合spring？
   2. 引入的包要通过spring.factoies自动装配
   
### 其他：
    * 消息的投递的可靠不可靠模式
    * 测试高效和非高效模式的性能差异
    
### 使用方式：
   #### 1.客户端引用：
   ```
      <dependency>
            <groupId>com.weiguofu</groupId>
            <artifactId>limqClient</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
   ```
   #### 2.基本使用 
```    
   @Component
   public class MyConsumer implements LimqConsumer {
       /**
        * 注入limqClient
        * @return
        */
       @Autowired
       LimqClient limqClient;

   
       /**
        * 声明队列
        * @return
        */
       @Bean
       public Queue queue1() {
           return new Queue("testQueue");
       }
   
       /**
        * 监听队列处理业务逻辑
        * @param val
        */
       @LimqListener(listenQueue = "testQueue")
       @Override
       public void consume(String val) {
           log.info("val:{}", val);
       }
   
       /**
        * 消息投递
        * @param limqClient
        */
       public  void produce(LimqClient limqClient) {
           limqClient.produce("testQueue", false, "hello,world");
       }   
   }
   
   ```


 