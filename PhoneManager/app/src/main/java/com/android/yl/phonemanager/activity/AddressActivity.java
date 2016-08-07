package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.db.dao.AddressDao;

public class AddressActivity extends Activity {

    private TextView tv_result;
    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        et_number = (EditText) findViewById(R.id.et_number);
        tv_result = (TextView) findViewById(R.id.tv_result);
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //在文本框文字改变之前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //在文字改变时调用
                String address = AddressDao.getAddress(s.toString().trim());
                tv_result.setText(address);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //文字改变后调用
            }
        });


    }

    public void query(View view) {
        String number = et_number.getText().toString().trim();
        if (!TextUtils.isEmpty(number)) {
            String address = AddressDao.getAddress(number);
            tv_result.setText(address);
        } else {
            Animation animation = AnimationUtils.loadAnimation(AddressActivity.this, R.anim.shake);
            et_number.startAnimation(animation);
            virbrate();

        }

    }

    public void virbrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{200, 500, 300}, -1);//-1表示不循环，0表示从第0个开始，1表示从第一个开始
    }
}
