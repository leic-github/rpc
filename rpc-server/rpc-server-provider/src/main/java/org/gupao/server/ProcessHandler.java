package org.gupao.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ProcessHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> handleMap;

    public ProcessHandler(Map<String, Object> HandleMap) {
        this.handleMap = HandleMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        Object invoke = invoke(request);
        ctx.writeAndFlush(invoke);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] params = request.getParams();
        String serviceName = request.getClassName();
        if (!StringUtils.isEmpty(request.getVersion())) {
            serviceName = serviceName + "-" + request.getVersion();
        }
        Object serviceBean = handleMap.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException("service not found " + serviceName);
        }
        Class<?> cls = Class.forName(request.getClassName());
        Class<?>[] clsArray = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            clsArray[i] = params[i].getClass();
        }
        Method method = cls.getMethod(request.getMethodName(), clsArray);
        Object result = method.invoke(serviceBean, params);
        return result;

    }
}
