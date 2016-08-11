package com.android.yl.phonemanager.db.dao;

import android.graphics.drawable.Drawable;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/8/11.
 * <p/>
 * 描述:
 */

public class TaskInfo {

    private Drawable icon;
    private String packageName;
    private String appName;
    private long memorySize;
    private boolean userApp;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "icon=" + icon +
                ", packageName='" + packageName + '\'' +
                ", appName='" + appName + '\'' +
                ", memorySize=" + memorySize +
                ", userApp=" + userApp +
                '}';
    }
}
