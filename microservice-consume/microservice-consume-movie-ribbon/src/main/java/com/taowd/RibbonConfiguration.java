package com.taowd;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Taowd
 * @date 2018/3/14 - 11:49
 * @Description     RibbonConfiguration必须是@Configuration，但请注意，它不在主应用程序上下文的@ComponentScan中，
 *                    否则将由所有@RibbonClients共享。如果您使用@ComponentScan（或@SpringBootApplication），
 *                    则需要采取措施避免包含（例如将其放在一个单独的，不重叠的包中，或者指定要在@ComponentScan）。
 */
@Configuration
public class RibbonConfiguration {

//    @Autowired
//    IClientConfig ribbonClientConfig;

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }

//    @Bean
//    public IPing ribbonPing(IClientConfig config) {
//        return new PingUrl();
//    }
//
//    @Bean
//    public IRule ribbonRule(IClientConfig config) {
//        return new AvailabilityFilteringRule();
//    }

}
