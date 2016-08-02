package com.android.yl.phonemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Setup4Activity extends BaseSetupActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        preferences = getSharedPreferences("cfg", MODE_PRIVATE);
    }

    @Override
    public void showNextPage() {
        preferences.edit().putBoolean("configed", true).commit();
        startActivity(new Intent(this, LostAndFoundActivity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);

    }
}
