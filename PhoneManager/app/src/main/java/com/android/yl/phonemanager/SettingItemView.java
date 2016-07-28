package com.android.yl.phonemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 在设置中心中，调用此方法，用来加载视图
 *
 * * Created by Administrator on 2016/7/28.
 */
public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }
    public void setChecked(boolean b){
        cbStatus.setChecked(b);
    }
    //返回cb的勾选状态
    public boolean isChecked(){
        return cbStatus.isChecked();
    }
}
