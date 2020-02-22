package com.taowd.controller;

import com.taowd.entity.ReturnEntity;
import com.taowd.entity.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据提供接口
 * @author Taoweidong
 */
@CrossOrigin
@RestController
@RequestMapping("data")
public class DataController {


  /**
   * 登录功能--鉴权.
   * @return 用户信息
   */
  @GetMapping("/list")
  public ReturnEntity login() {
    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setId(100);
    user1.setUsername("张三");
    user1.setAge(25);
    user1.setStatus("published");
    user1.setBirthday(LocalDate.now().minusYears(25));
    users.add(user1);
    User user2 = new User();
    user2.setId(200);
    user2.setUsername("李四");
    user2.setAge(29);
    user2.setStatus("draft");
    user2.setBirthday(LocalDate.now().minusYears(27));
    users.add(user2);

    return ReturnEntity.success(users);
  }

}
