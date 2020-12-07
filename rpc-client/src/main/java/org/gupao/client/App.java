package org.gupao.client;

import org.gupao.server.IHelloService;
import org.gupao.server.User;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8080);
        User user = new User();
        user.setAge(18);
        user.setUserName("Mic");
//        String result = iHelloService.sayHello("mic");
        String s = iHelloService.saveUser(user);
        System.out.println(s);
    }
}
