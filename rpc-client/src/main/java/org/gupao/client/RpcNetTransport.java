package org.gupao.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.gupao.server.RpcRequest;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 22:20
 **/
public class RpcNetTransport {
    private String hostName;
    private int port;

    public RpcNetTransport(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public Object send(RpcRequest request) {
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        try {
            final ProcessHandler processHandler = new ProcessHandler();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    .group(workGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(processHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(hostName, port).sync();
            ChannelFuture future = channelFuture.channel().writeAndFlush(request);
            future.channel().closeFuture().sync();
            return processHandler.getRequest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
        return null;
    }
}



