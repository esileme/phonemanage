package com.android.yl.phonemanager;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class Setup3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }
    /**
     * 跳转到下一个页面
     *
     * @param v
     */
    public void next(View v) {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
    }

    /**
     * 跳转到上一个页面
     *
     * @param v
     */
    public void prev(View v) {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
    }

}
