package com.android.yl.phonemanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * 监听手机开机启动广播
 * Created by Administrator on 2016/8/2.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String simSerialNumber = tm.getSimSerialNumber();
        System.out.println(simSerialNumber);
    }
}
