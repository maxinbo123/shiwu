package com.example.orderservice.entity;

import java.io.Serializable;

/**
 * 订单信息表(TOrder)实体类
 *
 * @author hexc
 * @since 2019-09-16 16:03:54
 */
public class TOrder implements Serializable {
    private static final long serialVersionUID = -65842327473446872L;
    //ID
    private Integer id;
    //订单号
    private String orderNo;
    //用户ID
    private String userId;
    //商品编码
    private String commodityCode;
    //数量
    private Integer count;
    //金额
    private Object amount;
    private String tccId;

    public String getTccId() {
        return tccId;
    }

    public void setTccId(String tccId) {
        this.tccId = tccId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

}