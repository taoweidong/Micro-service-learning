package com.taowd.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * 用户信息TOKEN
 */
@Data
@Builder
public class Token {

    /**
     * roles : ["admin"] introduction : 我是超级管理员 avatar :
     * https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif name : Super Admin
     */

    /**
     * 描述
     */
    private String introduction;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 名称
     */
    private String name;
    /**
     * 角色
     */
    private List<String> roles;

}
