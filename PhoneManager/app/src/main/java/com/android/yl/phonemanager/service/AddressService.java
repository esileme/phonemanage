package com.android.yl.phonemanager.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.yl.phonemanager.db.dao.AddressDao;
import com.android.yl.phonemanager.receiver.OutCallReceiver;

/**
 * 监听来电归属地的服务
 */

public class AddressService extends Service {

    private TelephonyManager tm;
    private myStateListener listener;
    private OutCallReceiver receiver;
    private String address;
    private WindowManager wm;
    private TextView view;

    public AddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new myStateListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//设置监听状态

        //把去电广播注册下
        receiver = new OutCallReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receiver, filter);
    }

    class myStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("ring......");
                    address = AddressDao.getAddress(incomingNumber);
                    //Toast.makeText(AddressService.this, address, Toast.LENGTH_LONG).show();
                    showToast(address);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (wm != null && view != null) {
                        wm.removeView(view);
                        view = null;
                    }
                    break;
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);//通过监听此方式来关闭服务
        unregisterReceiver(receiver);
    }

    /**
     * 显示电话归属地界面
     */
    public void showToast(String text) {
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");

        view = new TextView(this);
        view.setText(text);
        view.setTextColor(Color.RED);
        wm.addView(view, params);

    }
}
