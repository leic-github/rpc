package org.gupao.server;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:46
 **/
public class IHelloServiceImpl implements IHelloService{
    @Override
    public String sayHello(String content) {
        System.out.println("request in :"+content);
        return "Say hello : "+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in :"+user);
        return "SUCCESS";
    }
}
