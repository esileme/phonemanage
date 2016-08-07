package com.android.yl.phonemanager.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 服务状态判断工具
 * Created by Administrator on 2016/8/7.
 */
public class ServiceStatusUtils {
    public static boolean isServiceRunning(Context ctx, String serviceName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            String className = runningServiceInfo.service.getClassName();
            //System.out.println(className);
            if (className.equals(serviceName)) {//服务存在
                return true;
            }

        }
        return false;
    }
}
