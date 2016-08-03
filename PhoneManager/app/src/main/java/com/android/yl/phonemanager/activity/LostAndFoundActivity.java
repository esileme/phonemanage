package com.android.yl.phonemanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.android.yl.phonemanager.R;

public class LostAndFoundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences("cfg", MODE_PRIVATE);
        final boolean configed = preferences.getBoolean("configed", false);
        if (configed) {
            setContentView(R.layout.activity_lost_and_found);
        } else {
            startActivity(new Intent(LostAndFoundActivity.this, Setup1Activity.class));
            finish();
        }
    }

    /**
     * 重新进入设置页面
     *
     * @param v
     */
    public void reEnter(View v) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }


}
