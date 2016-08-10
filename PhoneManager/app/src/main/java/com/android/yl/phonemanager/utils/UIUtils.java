package com.android.yl.phonemanager.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * =================================
 * <p/>
 * Created by Administrator on 2016/8/10.
 * <p/>
 * 描述:封装一个Thread.run类，可以在子线程中刷新UI
 */

public class UIUtils {
    public static void showToast(final Activity context, final String msg, final int duration) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, msg, duration).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, duration).show();
                }
            });
        }
    }
}
