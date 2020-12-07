package org.gupao.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:50
 **/
public class RpcProxyServer {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private int port;
    private Object service;

    public RpcProxyServer(int port, Object service) {
        this.port = port;
        this.service = service;
    }

    /**
     * 发布服务
     */
    public void publish() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){//不断接受请求
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
