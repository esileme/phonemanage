package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.utils.SmsUtils;

public class AtoolsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }

    public void numberAddressQuery(View view) {
        startActivity(new Intent(AtoolsActivity.this, AddressActivity.class));
    }

    public void backUpsms(View view) {
        boolean backUp = SmsUtils.backUp(this);



    }
}
