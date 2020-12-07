package org.gupao.client;

import java.lang.reflect.Proxy;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 22:10
 **/
public class RpcProxyClient {

    public <T> T clientProxy(Class<?> cls, String host, int port) {
        return (T)Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls}, new RemoteInvocationHandler(host, port));
    }
}
