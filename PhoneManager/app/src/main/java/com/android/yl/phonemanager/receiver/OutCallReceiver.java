package com.android.yl.phonemanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.yl.phonemanager.db.dao.AddressDao;

/**
 * 监听去电的广播
 * 需要给监听去电的权限
 */
public class OutCallReceiver extends BroadcastReceiver {
    public OutCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = getResultData();//获取电话号码
        String address = AddressDao.getAddress(data);
        Toast.makeText(context, address, Toast.LENGTH_LONG).show();
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
