package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.yl.phonemanager.R;

public class CallSafeActivity extends Activity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_safe);
        initUI();
    }

    private void initUI() {
        listview = (ListView) findViewById(R.id.lv_listview);
    }

}
