package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.service.AddressService;
import com.android.yl.phonemanager.utils.ServiceStatusUtils;
import com.android.yl.phonemanager.view.SettingItemView;

public class SettingActivity extends Activity {

    private SettingItemView sivUpdate;//设置升级
    private SettingItemView sivAddress;//设置升级
    private SharedPreferences cfgsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //sharedperference保存数据更新
        cfgsPreferences = getSharedPreferences("cfg", MODE_PRIVATE);

        initUpdate();
        initAddressView();


    }

    /**
     * 升级方法
     */
    public void initUpdate() {
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        sivUpdate.setTitle("自动更新设置");
        boolean autoUpdate = cfgsPreferences.getBoolean("auto_update", true);
        if (autoUpdate) {
            sivUpdate.setChecked(true);
            sivUpdate.setDesc("自动更新已经开启");
        } else {
            sivUpdate.setChecked(false);
            sivUpdate.setDesc("自动更新已经关闭");
        }

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前的勾选状态
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
                    sivUpdate.setDesc("自动更新已经关闭");
                    //sp保存的字段叫autoupdate，值为false
                    cfgsPreferences.edit().putBoolean("auto_update", false).commit();
                } else {
                    sivUpdate.setChecked(true);
                    sivUpdate.setDesc("自动更新已经打开");
                    // cfgsPreferences.edit().putBoolean("auto_update", false).commit();
                    cfgsPreferences.edit().putBoolean("auto_update", true).commit();
                }
            }
        });

    }

    /**
     * 开启归属地服务的方法
     */
    public void initAddressView() {
        sivAddress = (SettingItemView) findViewById(R.id.siv_address);

        //先findviewbyid，否则会空指针异常
        //判断服务是否在后台运行，如果运行，则设置勾选框为true，为了防止第三方软件结束进程而出现扔勾选运行的情况
        boolean running = ServiceStatusUtils.isServiceRunning(SettingActivity.this,
                "com.android.yl.phonemanager.service.AddressService");
        //System.out.println(running);
        if (running) {
            sivAddress.setChecked(true);
        } else {
            sivAddress.setChecked(false);
        }

        sivAddress.setTitle("来电归属地设置");
        sivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivAddress.isChecked()) {
                    sivAddress.setChecked(false);
                    sivAddress.setDesc("来电归属地关闭");
                    stopService(new Intent(SettingActivity.this, AddressService.class));
                } else {
                    sivAddress.setChecked(true);
                    sivAddress.setDesc("来电归属地开启");
                    startService(new Intent(SettingActivity.this, AddressService.class));
                }
            }
        });
    }

}
