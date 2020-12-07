package org.gupao.server;

import java.io.Serializable;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:42
 **/
public class User implements Serializable {

    private static final long serialVersionUID = -2698842311706258959L;

    private String userName;
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
