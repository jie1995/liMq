
package com.weiguofu.limq.exception;

import com.weiguofu.limq.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description: 自定义异常处理
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:12
 * @Version 1.0
 */

@Slf4j
@ControllerAdvice
public class CustomExceptionResolver {

	@ResponseBody
	@ExceptionHandler(value = CustomException.class)
	public Object exceptionResolver(CustomException e) {
		log.info("全局异常捕获:{}", e);
		return ResponseUtil.fail(e.getAnEnum());
	}
}

