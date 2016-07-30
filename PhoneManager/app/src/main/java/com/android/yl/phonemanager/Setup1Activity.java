package com.android.yl.phonemanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

/**
 * 第一个设置页面
 */
public class Setup1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    /**
     * 按钮监听事件
     *
     * @param view
     */
    public void next(View view) {
        startActivity(new Intent(Setup1Activity.this, Setup2Activity.class));
        finish();
    }

}
