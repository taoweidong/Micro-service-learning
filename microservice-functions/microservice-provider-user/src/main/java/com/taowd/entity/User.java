package com.taowd.entity;

import java.time.LocalDate;
import lombok.Data;

/**
 * 用户信息
 * @author Taoweidong
 */
@Data
public class User {

  /**
   * 用户标识
   */
  private Integer id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 密码
   */
  private String password;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 状态
   */
  private String status;
  /**
   * 生日
   */
  private LocalDate birthday;

}
