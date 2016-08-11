package com.android.yl.phonemanager.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 服务状态判断工具
 * Created by Administrator on 2016/8/7.
 */
public class SystemInfoUtils {
    //判断服务是否运行的工具
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

    //获取正在运行的程序数量
    public static int runningAppProcessesCount(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();//获取到当前正在运行的程序
        return runningAppProcesses.size();
    }

    //获取运行的服务数量
    public static int runningServicesCount(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);//获取到当前所有运行的服务
        return runningServices.size();
    }

    //获取到可用内存
    public static long availMem(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);//获取到内存基本信息
        return memoryInfo.availMem;//获取到可用内存
    }

    //获取到总内存
    public static long totalMem(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);//获取到内存基本信息
        return memoryInfo.totalMem;//获取到总内存
    }


}
