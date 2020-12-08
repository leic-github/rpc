package org.gupao.client;

import org.gupao.server.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 22:12
 **/
public class RemoteInvocationHandler implements InvocationHandler {
    private RpcNetTransport rpcNetTransport;

    public RemoteInvocationHandler(String host, int port) {
        this.rpcNetTransport = new RpcNetTransport(host, port);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setVersion("v2.0");
        return rpcNetTransport.send(request);
    }
}
