package org.gupao.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:53
 **/
public class ProcessHandler implements Runnable {
    private Socket socket;
    private Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(request);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
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

    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] params = request.getParams();
        Class<?> cls = Class.forName(request.getClassName());
        Class<?>[] clsArray = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            clsArray[i] = params[i].getClass();
        }
        Method method = cls.getMethod(request.getMethodName(), clsArray);
        Object result = method.invoke(service, params);
        return result;

    }

}
