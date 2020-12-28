package org.gupao.client;

import org.gupao.client.discovery.ServiceDiscovery;
import org.gupao.client.discovery.ServiceDiscoveryWithZk;
import org.gupao.server.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 22:12
 **/
public class RemoteInvocationHandler implements InvocationHandler {

    private ServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setVersion("v1.0");
//        初始化输出层
        String seviceNamePath = request.getClassName() + "-" + request.getVersion();
        String addr = serviceDiscovery.discovery(seviceNamePath);
        String[] split = addr.split(":");
        RpcNetTransport rpcNetTransport = new RpcNetTransport(split[0], Integer.parseInt(split[1]));
        return rpcNetTransport.send(request);
    }
}
