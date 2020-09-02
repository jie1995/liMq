package com.weiguofu.limq;


/**
 * @author GuoFuWei
 */

public enum ResultEnum {

    SUCCESS("200", "消息处理成功"),
    FAIL("500", "消息处理失败");


    private String code;
    private String message;

    /**
     * 枚举构造方法默认是private
     * @param code
     * @param message
     */
    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
