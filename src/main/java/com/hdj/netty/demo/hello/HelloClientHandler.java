package com.hdj.netty.demo.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

/**
 * Author: 端吉
 * Date:   2017/4/23.
 */
public class HelloClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes("hello".getBytes());
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(MessageFormat.format("received:{0}", buffer.getCharSequence(0, buffer.readableBytes(), StandardCharsets.UTF_8)));
    }
}
