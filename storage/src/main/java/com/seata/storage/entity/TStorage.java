package com.seata.storage.entity;

import java.io.Serializable;

/**
 * 库存信息表(TStorage)实体类
 *
 * @author hexc
 * @since 2019-09-16 16:53:52
 */
public class TStorage implements Serializable {
    private static final long serialVersionUID = -81267914116198476L;
    //ID
    private Integer id;
    //商品编码
    private String commodityCode;
    //名称
    private String name;
    //库存
    private Integer count;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}