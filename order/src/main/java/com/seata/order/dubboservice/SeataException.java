package com.seata.order.dubboservice;

/**
 * Created by maxb on 2019/11/11.
 */
public class SeataException extends RuntimeException {
    private String msg;
    public SeataException(String msg, Throwable cause){
        super(msg,cause);
    }
    public SeataException(String msg){
        super(msg);
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
