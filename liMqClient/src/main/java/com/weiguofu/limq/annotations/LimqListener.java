package com.weiguofu.limq.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 监听队列注解
 * @Author: GuoFuWei
 * @Date: 2020/9/5 13:43
 * @Version 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LimqListener {

   public String listenQueue() default "";
}
