package org.gupao.server;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 20:46
 **/
@RpcService(value = IHelloService.class,version = "v2.0")
public class IHelloServiceImplV2 implements IHelloService{
    @Override
    public String sayHello(String content) {
        System.out.println("request in 【v2.0】:"+content);
        return "【v2.0】 Say hello  : "+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in 【v2.0】 :"+user);
        return "【v2.0】 SUCCESS";
    }
}
