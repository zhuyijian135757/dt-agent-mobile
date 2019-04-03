package com.dtstack.agent.conf;

import com.dtstack.agent.prop.NewLand;
import com.dtstack.agent.prop.Plats;
import com.dtstack.agent.prop.Proxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: dt-mobile-agent
 * @description: 配置类
 * @author: terry.zhu
 * @create: 2019-04-02 13:55
 **/
@Configuration
public class AgentConf {


    @Bean
    @ConfigurationProperties(prefix="plats")
    public Plats plats(){
        return new Plats();
    }

    @Bean
    @ConfigurationProperties(prefix="newland")
    public NewLand newLand(){
        return new NewLand();
    }

    @Bean
    @ConfigurationProperties(prefix="proxy")
    public Proxy proxy(){
        return new Proxy();
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
