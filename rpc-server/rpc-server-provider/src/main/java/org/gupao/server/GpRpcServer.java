package org.gupao.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.gupao.server.registry.IRegistryCenter;
import org.gupao.server.registry.RegistryCenterWithZk;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class GpRpcServer implements InitializingBean, ApplicationContextAware {
    private Map<String, Object> handlerMap = new HashMap<>();

    private int port;

    private IRegistryCenter registryCenter = new RegistryCenterWithZk();


    public GpRpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .group(bossGroup, workGroup)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ProcessHandler(handlerMap));
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Object bean : beans.values()) {
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            String serviceName = rpcService.value().getName();
            String version = rpcService.version();
            if (!StringUtils.isEmpty(version)) {
                serviceName = serviceName + "-" + version;
            }
            handlerMap.put(serviceName, bean);
            registryCenter.registry(serviceName, localHostName()+":"+port);
        }
    }

    private String  localHostName()  {
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();
            return host.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;

    }


}
