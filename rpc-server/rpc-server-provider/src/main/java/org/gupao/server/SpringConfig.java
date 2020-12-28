package org.gupao.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lei.chen@hcit.ai
 * @Description:
 * @CreateTiem: 2020/12/7 22:49
 **/
@Configuration
@ComponentScan(value = "org.gupao.server")
public class SpringConfig {

    @Bean
    public GpRpcServer getGpRpcServer() {
        String port = System.getProperty("server.port");
        return new GpRpcServer(Integer.parseInt(port));
    }
}
