package com.weiguofu.limq.codeh;


import com.weiguofu.limq.Constants;
import com.weiguofu.limq.MessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageEncoder extends MessageToByteEncoder<MessageWrapper> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageWrapper messageWrapper, ByteBuf out) {
        log.info("响应消息encode");
        int len = calcLengthOf(messageWrapper);
        out.writeInt(len);
        out.writeBytes(messageWrapper.getMessageId().getBytes(Constants.CHARSET));
        out.writeBytes(messageWrapper.getMessage().getBytes(Constants.CHARSET));
        out.writeLong(messageWrapper.getTimestamp());
    }

    private int calcLengthOf(MessageWrapper messageWrapper) {
        return Constants.LENGTH_OF_MESSAGE_ID + messageWrapper.getMessage().length() + Constants.LENGTH_OF_TIMESTAMP;
    }
}
