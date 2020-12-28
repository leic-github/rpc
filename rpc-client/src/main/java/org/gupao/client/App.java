package org.gupao.client;

import javafx.application.Application;
import org.gupao.server.IHelloService;
import org.gupao.server.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient =  applicationContext.getBean(RpcProxyClient.class);

        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8080);
        User user = new User();
        user.setAge(18);
        user.setUserName("Mic");
        for (int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(2);
            String s = iHelloService.saveUser(user);
            System.out.println(s);
        }
    }
}
