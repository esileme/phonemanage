package com.android.yl.phonemanager.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.yl.phonemanager.R;

/**
 * 第二个设置页面
 */
public class Setup2Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }
    /**
     * 显示xia一页
     */
    public void showNextPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    /**
     * 显示上一页
     */
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

}
