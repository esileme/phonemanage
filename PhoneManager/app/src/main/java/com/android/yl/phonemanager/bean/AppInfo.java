package com.android.yl.phonemanager.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/8.
 */
public class AppInfo {
    private Drawable icon;//定义图片
    private String appName;//程序名称
    private long appSize;//程序大小
    private boolean userApp;//true为用户程序，false为系统程序
    private boolean isRom;//程序放置的位置
    private String packageName;//程序的包名

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    public boolean isRom() {
        return isRom;
    }

    public void setIsRom(boolean isRom) {
        this.isRom = isRom;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", appSize='" + appSize + '\'' +
                ", userApp=" + userApp +
                ", isRom=" + isRom +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
