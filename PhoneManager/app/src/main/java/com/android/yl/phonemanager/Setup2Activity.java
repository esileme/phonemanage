package com.android.yl.phonemanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

/**
 * 第二个设置页面
 *
 * @param v
 */
public class Setup2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    /**
     * 跳转到下一个页面
     *
     * @param v
     */
    public void next(View v) {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
    }

    /**
     * 跳转到上一个页面
     *
     * @param v
     */
    public void prev(View v) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }

}
