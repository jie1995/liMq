# liMq
Netty+BlockingQueue实现的简单mq,支持点对点和topic模式。

   
### 使用方式：
   #### 1.客户端引用：
   
   ```
        私有仓库地址：修改setting.xml,比较麻烦，以后deploy到公共仓库
        <mirror>
            <id>nexus-weiguofu</id>
            <mirrorOf>!internal.repo,*</mirrorOf>
            <name>internal nexus repository</name>
            <!--镜像采用配置好的组的地址-->
            <url>http://47.108.178.29:8081/repository/java-group/</url>
        </mirror>

      <dependency>
            <groupId>com.weiguofu</groupId>
            <artifactId>liMqClient</artifactId>
            <version>1.2-SNAPSHOT</version>
        </dependency>
   ```
   #### 2.基本使用 
```    
   @Component
   public class MyConsumer implements LimqConsumer {
       @Autowired
          LimqClient limqClient;
      
          /**
           * 声明队列
           *
           * @return
           */
          @Bean
          public Queue queue1() {
              return new Queue("testQueue").bind("topic1").bind("topic2");
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
           * 消息投递点对点
           */
          public void produce() {
              limqClient.produce("testQueue", false, "hello,world");
          }
      
          /**
           * 消息投递到topic
           */
          public void produceWithTopic() {
              limqClient.produceWithTopic("topic3", "hello,world");
          }
   }
  
   ```
### 说明：
  * 请求分发：基于spi机制查找对应的类及方法，通过反射调用
  
  * ~~序列化协议：自定义JSON格式序列化~~
  
  * 基于TCP长连接的
  
  * 自定义加码解码特定请求报文，支持对象传输（参考自闪电侠）
     * 请求参数格式不对(含有null值)，传输时加码会失败
     
  * LengthFieldBasedFrameDecoder处理粘包
     * 响应含有中文会超过处理长度
     
  * 拉取消费信息后，如何处理异步的请求结果？
     * LinkedHashMap维护统一的messageId;
     
  * springboot自动装配
     * spring.factoies
   

    
 