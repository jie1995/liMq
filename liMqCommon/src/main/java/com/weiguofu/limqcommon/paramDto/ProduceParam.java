package com.weiguofu.limqcommon.paramDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: 消息投递参数
 * @Author: GuoFuWei
 * @Date: 2020/9/4 9:41
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class ProduceParam {

    public String qName;
    public Boolean reliable;
    public String value;
}
