package com.taowd.feign;

import com.taowd.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Taowd
 * @date 2018/3/18 - 15:46
 * @Description
 */
@Component
public class UserFeignClientFallBack implements UserFeignClient {
    @Override
    public String sayHello() {
        return "这里是sayHello方法调用时返回的 FallBack方法";
    }

    @Override
    public User getUserInfo() {

        User user = new User();
        user.setId(90000);
        user.setName("李fallback");
        user.setAge(24);

        return user;
    }
}
