package com.taowd.feign;

import com.taowd.entity.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Taowd
 * @date 2018/3/18 - 16:18
 * @Description
 */
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    private static final Logger logger = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable cause) {
        logger.error("UserFeignClientFallbackFactory发生异常：", cause);
        return new UserFeignClientWithFallBackFactory() {


            @Override
            public String sayHello() {
                return "这是从FallbackFactory中返回的错误信息，错误原因：" + cause;
            }

            @Override
            public User getUserInfo() {

                User user = new User();
                user.setId(89999);
                user.setName("FallbackFactory 测试 错误原因：" + cause);
                return user;
            }
        };
    }
}
