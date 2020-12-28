package org.gupao.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessHandler extends ChannelInboundHandlerAdapter {
    private Object msg;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        this.msg = msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public Object getRequest() {
        return msg;
    }
}
