package com.weiguofu.limq.codeh;


import com.weiguofu.limqcommon.Constants;
import com.weiguofu.limqcommon.MessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ResponseMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        log.info("响应消息decode");
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
