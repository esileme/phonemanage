package com.android.yl.phonemanager.bean;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BlackNumberInfo {
    //黑名单电话号码
    private String number;
    //黑名单拦截模式
    private String mode;

    /**
     * 黑名单的拦截模式
     * 1、全部拦截
     * 2、短信拦截
     * 3、电话拦截
     *
     * @return
     */
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
