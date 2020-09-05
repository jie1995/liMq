package com.weiguofu.limq;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * @Description: LengthFieldBasedFrameDecoder解决粘包
 * @Author: GuoFuWei
 * @Date: 2020/9/4 22:49
 * @Version 1.0
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int MAX_LENGTH = Integer.MAX_VALUE;

    private static final int LENGTH_FIELD_OFFSET = 0;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        /**
         * 第一个参数为1024*1024，表示数据包的最大长度为1024*1024；
         * 第二个参数0，表示长度域的偏移量为0，也就是长度域放在了最前面，处于包的起始位置；
         * 第三个参数为4，表示长度域占用4个字节；
         * 第四个参数为0，表示长度域保存的值，仅仅为有效数据长度，不包含其他域（如长度域）的长度；
         * 第五个参数为4，表示最终的取到的目标数据包，抛弃最前面的4个字节数据，长度域的值被抛弃。
         */
        super(MAX_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, 0, LENGTH_FIELD_LENGTH);
    }
}
