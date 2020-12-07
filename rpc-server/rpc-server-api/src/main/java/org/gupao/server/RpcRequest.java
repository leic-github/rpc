package org.gupao.server;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:57
 **/
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -5720663566474365530L;

    private String className;
    private String methodName;
    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
