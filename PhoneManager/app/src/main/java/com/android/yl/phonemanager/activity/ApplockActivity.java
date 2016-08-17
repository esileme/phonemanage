package com.android.yl.phonemanager.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.fragment.LockFragment;
import com.android.yl.phonemanager.fragment.UnLockFragment;

public class ApplockActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_unlock;
    private Button btn_lock;
    private FrameLayout fl_content;
    private FragmentManager fragmentManager;
    private LockFragment lockFragment;
    private UnLockFragment unLockFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_applock);
        btn_unlock = (Button) findViewById(R.id.btn_unlock);
        btn_lock = (Button) findViewById(R.id.btn_lock);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);

        btn_unlock.setOnClickListener(this);
        btn_lock.setOnClickListener(this);

        //创建fragment对象
        fragmentManager = getSupportFragmentManager();
        //开始事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //创建出两个fragment对象
        lockFragment = new LockFragment();
        unLockFragment = new UnLockFragment();
        //将默认显示的界面替换出来，并将事务提交
        transaction.replace(R.id.fl_content,unLockFragment).commit();

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = fragmentManager.beginTransaction();//开始事务
        switch (v.getId()) {
            case R.id.btn_unlock:
                //先改变按钮的样式
                btn_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
                btn_lock.setBackgroundResource(R.drawable.tab_right_default);
                ft.replace(R.id.fl_content, unLockFragment);
                System.out.println("切换到" + unLockFragment);
                break;
            case R.id.btn_lock:
                btn_unlock.setBackgroundResource(R.drawable.tab_left_default);
                btn_lock.setBackgroundResource(R.drawable.tab_right_pressed);
                ft.replace(R.id.fl_content, lockFragment);
                System.out.println("切换到" + lockFragment);
                break;
        }
        ft.commit();
    }
}
