package com.taowd.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.taowd.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * @author Taowd
 * @date 2018/3/29 - 14:02
 * @Description
 */
@Service
public class AggregationService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserById(Integer id) {
        // 创建一个被观察者
        return Observable.create(observer -> {
            // 请求用户微服务的/{id}端点
            User user = restTemplate.getForObject("http://microservice-provider-user/getUserById/{id}", User.class, id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getMovieUserByUserId(Integer id) {
        return Observable.create(observer -> {
            // 请求电影微服务的/user/{id}端点
            User movieUser = restTemplate.getForObject("http://microservice-consume-movie/getUserInfo/{id}", User.class, id);
            observer.onNext(movieUser);
            observer.onCompleted();
        });
    }

    /**
     * 回退方法
     *
     * @param id
     * @return
     */
    public User fallback(Integer id) {
        User user = new User();
        user.setId(900);
        return user;
    }

}
