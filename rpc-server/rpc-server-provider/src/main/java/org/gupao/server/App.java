package org.gupao.server;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        IHelloService iHelloService = new IHelloServiceImpl();
        RpcProxyServer rpcProxyServer = new RpcProxyServer(8080, iHelloService);
        rpcProxyServer.publish();
    }
}
