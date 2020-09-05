package com.weiguofu.limq;

import com.weiguofu.limq.annotations.LiMqConsumer;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Description: 暂存反射信息，得到响应后继续调用
 * @Author: GuoFuWei
 * @Date: 2020/9/5 16:54
 * @Version 1.0
 */
@Data
public class ReflectDto {

   private Method method;

   private Class<? extends LiMqConsumer> clazz;

   private String resultValue;
}
