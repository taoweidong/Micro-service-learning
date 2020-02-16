package com.taowd.config;

import com.taowd.service.CustomNotifier;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.NotifierTriggerConfiguration;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启自定义提醒配置
 * @author Taoweidong
 */
@Configuration
//@ConditionalOnProperty(
//    prefix = "spring.boot.admin.notify.dingtalk",
//    name = {"webhook-token"}
//)
@AutoConfigureBefore({NotifierTriggerConfiguration.class, CompositeNotifierConfiguration.class})
public class CustomNotifierConfig {

  @Bean
  @ConditionalOnMissingBean
  //  @ConfigurationProperties(prefix = "spring.boot.admin.notify.dingtalk")
  public CustomNotifier dingTalkNotifier(InstanceRepository repository) {
    return new CustomNotifier(repository);
  }
}
