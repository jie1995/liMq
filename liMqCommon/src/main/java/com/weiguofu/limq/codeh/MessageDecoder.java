package com.weiguofu.limq.codeh;


import com.weiguofu.limq.Constants;
import com.weiguofu.limq.MessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * @Description: 请求消息解码
 * @Author: GuoFuWei
 * @Date: 2020/9/5 1:54
 * @Version 1.0
 */
public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    private Object decode(ByteBuf in) {
        MessageWrapper rm = new MessageWrapper();

        byte[] messageIdBytes = new byte[Constants.LENGTH_OF_MESSAGE_ID];
        in.readBytes(messageIdBytes);
        rm.setMessageId(new String(messageIdBytes, Constants.CHARSET));

        byte[] contentBytes = new byte[in.readableBytes() - Constants.LENGTH_OF_TIMESTAMP];
        in.readBytes(contentBytes);
        rm.setMessage(new String(contentBytes, Constants.CHARSET));

        rm.setTimestamp(in.readLong());

        return rm;
    }
}
