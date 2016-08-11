package com.android.yl.phonemanager.engine;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/8/11.
 * <p/>
 * 描述:
 */

public class TaskInfoParse {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<TaskInfo> getTasksInfo(Context context) {

        List<TaskInfo> taskInfos = new ArrayList<>();//创建个集合,将获取到的所有信息都放进去,然后返回

        PackageManager packageManager = context.getPackageManager();//获取包管理信息
        //获取进程管理器
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        //List<ActivityManager.RunningServiceInfo> runningAppProcesses = activityManager.getRunningServices(0);
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            TaskInfo taskInfo = new TaskInfo();

            String processName = runningAppProcessInfo.processName;//获取到运行的正在运行的进程的名字:包名
            Debug.MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});//获取内存信息

            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(processName, 0);
                Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);//获取到图片信息
                String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();//获取到应用名字

                taskInfo.setPackageName(processName);
                taskInfo.setAppName(appName);
                taskInfo.setIcon(icon);
                taskInfos.add(taskInfo);
                int totalPrivateDirty = memoryInfo[0].getTotalPrivateDirty() * 1024;
                taskInfo.setMemorySize(totalPrivateDirty);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                taskInfo.setIcon(context.getDrawable(R.drawable.ic_launcher));
            }
            return taskInfos;
        }


        return null;
    }
}
