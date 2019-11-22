package com.seata.account.entity;

import java.io.Serializable;

/**
 * 账户信息表(TAccount)实体类
 *
 * @author hexc
 * @since 2019-09-16 09:28:02
 */
public class TAccount implements Serializable {
    private static final long serialVersionUID = 168153031919797884L;
    //ID
    private Integer id;
    //用户ID
    private String userId;
    //余额
    private Object amount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

}