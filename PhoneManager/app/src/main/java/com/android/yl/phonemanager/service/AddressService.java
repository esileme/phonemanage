package com.android.yl.phonemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.yl.phonemanager.db.dao.AddressDao;

/**
 * 监听来电归属地的服务
 */

public class AddressService extends Service {

    private TelephonyManager tm;
    private myStateListener listener;

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
    }

    class myStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("ring......");
                    String address = AddressDao.getAddress(incomingNumber);
                    Toast.makeText(AddressService.this, address, Toast.LENGTH_SHORT).show();
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
    }
}
