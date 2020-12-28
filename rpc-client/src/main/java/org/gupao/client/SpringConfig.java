package org.gupao.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 23:08
 **/
@Configuration
public class SpringConfig {

    @Bean
    public RpcProxyClient getRpcProxyClient(){
        return new RpcProxyClient();
    }
}
