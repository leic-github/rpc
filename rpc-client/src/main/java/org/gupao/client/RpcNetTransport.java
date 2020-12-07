package org.gupao.client;

import org.gupao.server.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            Socket socket = new Socket(hostName, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return null;

    }
}
