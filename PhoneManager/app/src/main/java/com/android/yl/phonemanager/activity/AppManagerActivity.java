package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.AppInfo;
import com.android.yl.phonemanager.engine.AppInfos;

import java.util.ArrayList;
import java.util.List;

public class AppManagerActivity extends Activity implements View.OnClickListener {

    private ListView lv_list;
    private List<AppInfo> appInfos;
    private List<AppInfo> sysApp;
    private List<AppInfo> userApp;
    private PopupWindow popupWindow;
    private AppInfo clickAppInfo;

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
                userApp = new ArrayList<AppInfo>();
                sysApp = new ArrayList<AppInfo>();
                for (AppInfo appinfo : appInfos) {
                    if (appinfo.isUserApp()) {
                        userApp.add(appinfo);
                    } else {
                        sysApp.add(appinfo);
                    }
                }
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

        //给listview设置滚动监听事件
        final TextView tvCount = (TextView) findViewById(R.id.tv_count);
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                popupWindowDismiss();//popupwindow消失

                if (userApp != null && sysApp != null) {
                    if (firstVisibleItem > (userApp.size() + 1)) {
                        tvCount.setText("系统程序(" + sysApp.size() + ")");
                    } else {
                        tvCount.setText("用户程序(" + userApp.size() + ")");
                    }
                }
            }
        });

        /*//给listview的item设置长按监听事件
        lv_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Object obj = lv_list.getItemAtPosition(position);


                //在此处new出一个popupWindow，然后设置属性
                if (obj != null && obj instanceof AppInfo) {//先获取一个obj对象，通过判断obj对象是否属于appinfo，防止两个textview被点击
                    View inflate = view.inflate(AppManagerActivity.this, R.layout.dialog_popup, null);


                    clickAppInfo = (AppInfo) obj;//在此处将对象转化为appInfo对象？,在点击启动时调用此处

                    ll_uninstal = (LinearLayout) findViewById(R.id.ll_uninstal);
                    ll_run = (LinearLayout) findViewById(R.id.ll_run);
                    ll_share = (LinearLayout) findViewById(R.id.ll_share);

                    *//*ll_uninstal.setOnClickListener(AppManagerActivity.this);
                    ll_run.setOnClickListener(AppManagerActivity.this);
                    ll_share.setOnClickListener(AppManagerActivity.this);*//*


                    popupWindowDismiss();

                    popupWindow = new PopupWindow(inflate, -2, WindowManager.LayoutParams.WRAP_CONTENT);
                    //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));不设置背景也能显示出动画

                    int[] location = new int[2];
                    view.getLocationInWindow(location);//先获取当前item的y值，通过此方法获取当前item的x和y的值，通过数组的方法将y的值取出来
                    popupWindow.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 90, location[1]);

                    ScaleAnimation sa = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(500);
                    inflate.setAnimation(sa);
                }


                return false;
            }
        });*/
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object obj = lv_list.getItemAtPosition(position);

                //在此处new出一个popupWindow，然后设置属性
                if (obj != null && obj instanceof AppInfo) {//先获取一个obj对象，通过判断obj对象是否属于appinfo，防止两个textview被点击

                    clickAppInfo = (AppInfo) obj;//在此处将对象转化为appInfo对象？,在点击启动时调用此处
                    View inflate = view.inflate(AppManagerActivity.this, R.layout.dialog_popup, null);


                    LinearLayout ll_uninstal = (LinearLayout) findViewById(R.id.ll_uninstal);
                    LinearLayout ll_run = (LinearLayout) findViewById(R.id.ll_run);
                    LinearLayout ll_share = (LinearLayout) findViewById(R.id.ll_share);

                    /*ll_uninstal.setOnClickListener(AppManagerActivity.this);
                    ll_run.setOnClickListener(AppManagerActivity.this);
                    ll_share.setOnClickListener(AppManagerActivity.this);*/


                    popupWindowDismiss();

                    popupWindow = new PopupWindow(inflate, -2, WindowManager.LayoutParams.WRAP_CONTENT);
                    //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));不设置背景也能显示出动画

                    int[] location = new int[2];
                    view.getLocationInWindow(location);//先获取当前item的y值，通过此方法获取当前item的x和y的值，通过数组的方法将y的值取出来
                    popupWindow.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 90, location[1]);

                    ScaleAnimation sa = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(500);
                    inflate.setAnimation(sa);
                }

            }
        });

    }

    private void popupWindowDismiss() {//调用此方法的有:滚动时，按返回键时，当点击其他的条目时
        if (popupWindow != null && popupWindow.isShowing()) {//这个&&两边为何不能写反？？？
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_run:
                Intent intent = this.getPackageManager().getLaunchIntentForPackage(clickAppInfo.getPackageName());
                this.startActivity(intent);
                popupWindowDismiss();
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_uninstal:
                break;
        }

    }


    /**
     * 自定义adapter
     */
    private class AppManagerAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return userApp.size() + 1 + sysApp.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            if (position == 0) {
                return null;
            }
            if (position == userApp.size() + 1) {
                return null;
            }
            //判断位置·用户程序和系统程序的位置，要先把两个名称排除掉，然后，再让用户或者系统程序判断数量
            AppInfo appInfo;
            if (position < userApp.size() + 1) {
                appInfo = appInfos.get(position - 1);

            } else {
                int location = 1 + userApp.size() + 1;
                appInfo = sysApp.get(position - location);

            }
            return appInfo;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (position == 0) {//如果位置为0，表示用户程序
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("用户程序(" + userApp.size() + ")");
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;

            } else if (position == userApp.size() + 1) {//如果位置为用户的位置+1，则表示系统程序
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("系统程序(" + sysApp.size() + ")");
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                return textView;
            }

            //判断位置·用户程序和系统程序的位置，要先把两个名称排除掉，然后，再让用户或者系统程序判断数量
            AppInfo appInfo;
            if (position < userApp.size() + 1) {
                appInfo = appInfos.get(position - 1);

            } else {
                int location = 1 + userApp.size() + 1;
                appInfo = sysApp.get(position - location);

            }


            View view = null;
            ViewHolder holder;
            if (convertView != null && convertView instanceof LinearLayout) {//判断如果coverview不属于linearlayout，则不对它加载

                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {

                view = View.inflate(AppManagerActivity.this, R.layout.item_app_manager, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_apk_size = (TextView) view.findViewById(R.id.tv_apk_size);
                holder.tv_location = (TextView) view.findViewById(R.id.tv_location1);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name1);
                view.setTag(holder);
            }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupWindowDismiss();
    }
}
