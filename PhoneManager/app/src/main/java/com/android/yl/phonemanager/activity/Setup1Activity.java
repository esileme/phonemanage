package com.android.yl.phonemanager.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.yl.phonemanager.R;

/**
 * 第一个设置页面
 */
public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(Setup1Activity.this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {

    }


}
