package com.seata.common.dto;


import java.io.Serializable;

/**
 * @Author: heshouyou
 * @Description 商品信息
 * @Date Created in 2019/1/13 16:25
 */
public class CommodityDTO implements Serializable {

    private Integer id;

    private String commodityCode;

    private String name;

    private Integer count;

    private String tccId;

    //测试异常标识
    private String flag;

    //预减库存值
    private Integer preCount;

    public Integer getPreCount() {
        return preCount;
    }

    public void setPreCount(Integer preCount) {
        this.preCount = preCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

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
