package org.gupao.server;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:46
 **/
@RpcService(value = IHelloService.class,version = "v1.0")
public class IHelloServiceImpl implements IHelloService{
    @Override
    public String sayHello(String content) {
        System.out.println("request in 【v1.0】:"+content);
        return "【v1.0】 Say hello  : "+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in 【v1.0】 :"+user);
        return "【v1.0】 SUCCESS";
    }
}
