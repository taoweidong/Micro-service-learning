package com.taowd.entity;

import lombok.Data;

/**
 * 前台数据格式
 */
@Data
public class ReturnEntity {

    /**
     * 处理码
     */
    private long code;
    /**
     * 数据
     */
    private Object data;

    /**
     * 处理成功.
     * 
     * @param data
     *            数据
     * @return 结果集
     */
    public static ReturnEntity success(Object data) {
        ReturnEntity returnEntity = new ReturnEntity();
        returnEntity.setCode(20000);
        returnEntity.setData(data);
        return returnEntity;
    }

}
