package com.weiguofu.limq.exception;


import com.weiguofu.limq.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 自定义异常
 * @Author: GuoFuWei
 * @Date: 2020/9/3 13:12
 * @Version 1.0
 */

@Getter
@Setter
public class CustomException extends Exception {

    private ResultEnum anEnum;

    public CustomException(ResultEnum anEnum, String message) {
        super(message);
        this.anEnum = anEnum;
    }

    public CustomException(ResultEnum anEnum) {
        this.anEnum = anEnum;
    }
}
