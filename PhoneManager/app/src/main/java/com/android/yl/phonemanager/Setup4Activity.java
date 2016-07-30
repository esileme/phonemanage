package com.android.yl.phonemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class Setup4Activity extends Activity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        preferences = getSharedPreferences("cfg", MODE_PRIVATE);
    }

    /**
     * 跳转到下一个页面
     *
     * @param v
     */
    public void next(View v) {
        preferences.edit().putBoolean("configed", true).commit();
        startActivity(new Intent(this, LostAndFoundActivity.class));
        finish();
    }

    /**
     * 跳转到上一个页面
     *
     * @param v
     */
    public void prev(View v) {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
    }

}
