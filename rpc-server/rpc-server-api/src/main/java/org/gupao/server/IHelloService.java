package org.gupao.server;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:42
 **/
public interface IHelloService {
    String sayHello(String content);

    String saveUser(User user);
}
