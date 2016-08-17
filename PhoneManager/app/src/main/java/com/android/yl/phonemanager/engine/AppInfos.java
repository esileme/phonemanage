package com.android.yl.phonemanager.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.android.yl.phonemanager.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class AppInfos {

    static List<AppInfo> packageAppInfos = new ArrayList<AppInfo>();//初始化appinfo的信息

    //获取包的管理器
    public static List<AppInfo> getAppInfos(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo installedPackage : installedPackages) {
            Drawable drawable = installedPackage.applicationInfo.loadIcon(packageManager);//读取图片
            String apkName = installedPackage.applicationInfo.loadLabel(packageManager).toString();//读取应用程序名字
            String packageName = installedPackage.packageName;//获取包名

            String sourceDir = installedPackage.applicationInfo.sourceDir;//获取资源路径
            File file = new File(sourceDir);
            long apkSize = file.length();

            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(apkName);
            appInfo.setAppSize(apkSize);
            appInfo.setIcon(drawable);
            appInfo.setPackageName(packageName);
            /**
             *获取到应用程序的标记flags
             * 如果应用程序标记与系统程序标记相同则为用户app设为true否则设为false
             *
             */
            int flags = installedPackage.applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                appInfo.setUserApp(false);
            } else {
                appInfo.setUserApp(true);
            }
            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                appInfo.setIsRom(false);
            } else {
                appInfo.setIsRom(true);
            }

            /*System.out.println("=================");
            System.out.println("图片" + drawable);
            System.out.println("名字" + apkName);
            System.out.println("包名" + packageName);
            System.out.println("路径" + sourceDir);
            System.out.println("大小" + apkSize);*/
            packageAppInfos.add(appInfo);
        }


        return packageAppInfos;
    }
}
