package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.utils.SmsUtils;
import com.android.yl.phonemanager.utils.UIUtils;

public class AtoolsActivity extends Activity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }

    public void numberAddressQuery(View view) {
        startActivity(new Intent(AtoolsActivity.this, AddressActivity.class));
    }

    public void backUpsms(View view) {

        //new一个提示条
        dialog = new ProgressDialog(AtoolsActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("正在备份中。。。。");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();


        new Thread() {
            @Override
            public void run() {

                boolean backUp = SmsUtils.backUp(AtoolsActivity.this,dialog);
                if (backUp) {
                    /*Looper.prepare();
                    Toast.makeText(AtoolsActivity.this, "备份成功", Toast.LENGTH_LONG).show();
                    Looper.loop();*/
                    UIUtils.showToast(AtoolsActivity.this, "备份成功", Toast.LENGTH_LONG);
                } else {
                    Looper.prepare();
                    Toast.makeText(AtoolsActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                dialog.dismiss();
            }
        }.start();

    }
}
