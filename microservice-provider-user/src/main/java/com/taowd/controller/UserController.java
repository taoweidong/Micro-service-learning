package com.taowd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.taowd.entity.User;

/**
 * @author Taoweidong
 */
@CrossOrigin
@RestController
public class UserController {

	@Value("${server.port}")
	private String port;

	@GetMapping("/hello")
	public String sayHello() {

		return "Hello 这里是用户服务提供者。。。 当前端口号为：" + port;
	}

	@GetMapping("/getUser")
	public User getUser() {

		User user = new User();
		user.setId(88999);
		user.setName("李三思");
		user.setAge(34);
		return user;
	}

	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable Integer id) {

		User user = new User();
		user.setId(90000);
		user.setName("李三");
		user.setAge(id);
		return user;
	}

}
