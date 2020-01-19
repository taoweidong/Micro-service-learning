package com.taowd.entity;

import lombok.Data;

/**
 * 豆瓣数据实体
 * @author Taoweidong
 */
@Data
public class Douban {

  private String serialNumber;
  private String movieName;
  private String introduce;
  private String star;
  private String evaluate;
  private String describe;
}
