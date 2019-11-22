package com.seata.common.dto;

import java.io.Serializable;

/**
 * 公共返回数据模型
 * 
 * @author renby
 * @version $Id: ResponseData.java, v 0.1 2018-1-3 下午5:09:14 renby Exp $
 */
@SuppressWarnings("serial")
public class ResponseData<T> implements Serializable {
    //code码
    private String code;
    //信息描述
    private String msg;
    //分页查询记录总数
    private int    count;
    //对象
    private T      obj;

    public ResponseData(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(String code, String msg, T obj) {
        super();
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public ResponseData(String code, String msg, int count, T obj) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.obj = obj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
