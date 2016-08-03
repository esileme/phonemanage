package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.view.SettingItemView;

public class SettingActivity extends Activity {

    private SettingItemView sivUpdate;//设置升级
    private SharedPreferences cfgsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //sharedperference保存数据更新
        cfgsPreferences = getSharedPreferences("cfg", MODE_PRIVATE);
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

}
