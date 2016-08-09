package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.AppInfo;
import com.android.yl.phonemanager.engine.AppInfos;

import java.util.List;

public class AppManagerActivity extends Activity {

    private ListView lv_list;
    private List<AppInfo> appInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread() {
            @Override
            public void run() {
                //获取所有应用
                appInfos = AppInfos.getAppInfos(AppManagerActivity.this);
                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_app_manager);
        TextView tv_rom = (TextView) findViewById(R.id.tv_rom);
        TextView tv_sd = (TextView) findViewById(R.id.tv_sd);
        lv_list = (ListView) findViewById(R.id.lv_list);
        long romfreeSpace = Environment.getDataDirectory().getFreeSpace();
        tv_rom.setText("内存可用:" + Formatter.formatFileSize(this, romfreeSpace));//格式化内存代码
        long sdFreeSpace = Environment.getExternalStorageDirectory().getFreeSpace();
        tv_sd.setText("SD卡可用:" + Formatter.formatFileSize(this, sdFreeSpace));

    }

    /**
     * 自定义adapter
     */
    private class AppManagerAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return appInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(AppManagerActivity.this, R.layout.item_app_manager, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_apk_size = (TextView) view.findViewById(R.id.tv_apk_size);
                holder.tv_location = (TextView) view.findViewById(R.id.tv_location1);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name1);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            AppInfo appInfo = appInfos.get(position);
            holder.iv_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_apk_size.setText(Formatter.formatFileSize(AppManagerActivity.this, appInfo.getAppSize()));//不是settextsize.....
            holder.tv_name.setText(appInfo.getAppName());
            if (appInfo.isRom()) {
                holder.tv_location.setText("手机内存");
            } else {
                holder.tv_location.setText("sd内存");
            }
            return view;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_apk_size;
        TextView tv_location;
        TextView tv_name;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            AppManagerAdapter adapter = new AppManagerAdapter();
            lv_list.setAdapter(adapter);
        }
    };
}
