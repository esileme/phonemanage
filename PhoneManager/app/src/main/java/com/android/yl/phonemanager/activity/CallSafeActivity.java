package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.BlackNumberInfo;
import com.android.yl.phonemanager.db.dao.BlackNumberDao;

import java.util.List;

public class CallSafeActivity extends Activity {

    private ListView listview;
    private List<BlackNumberInfo> blackNumberInfos;
    private LinearLayout ll_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_safe);
        initUI();
        initData();
    }

    //给接受到的信息添加一个适配器
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ll_pb.setVisibility(View.INVISIBLE);
            final CallSafeAdapter adapter = new CallSafeAdapter();
            listview.setAdapter(adapter);//给listview设置接收器
        }
    };

    /**
     * init data
     * 初始化数据要在子线程中
     */
    private void initData() {

        new Thread() {
            @Override
            public void run() {//更新数据所以在线程中
                final BlackNumberDao dao = new BlackNumberDao(CallSafeActivity.this);//要把所有的条目列出来，因此要新建一个dao对象
                blackNumberInfos = dao.findAll();
                handler.sendEmptyMessage(0);
            }
        }.start();


    }

    /**
     * init ui
     */
    private void initUI() {
        listview = (ListView) findViewById(R.id.lv_listview);
        ll_pb = (LinearLayout) findViewById(R.id.ll_pb);
        ll_pb.setVisibility(View.VISIBLE);
    }

    /**
     * init a adapter to adapt the data of blacknumber and put the data to list_view
     */
    private class CallSafeAdapter extends BaseAdapter {

        private viewHolder holder;

        @Override
        public int getCount() {
            return blackNumberInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return blackNumberInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * listview 优化
         * 建立一个viewHolder类，将电话和模式设置进去，通过调用listview里面的属性来节省内存
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(CallSafeActivity.this, R.layout.item_call_safe, null);//将item_call_safe视图传给一个新建的试图
                holder = new viewHolder();
                holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holder.tv_mode = (TextView) convertView.findViewById(R.id.tv_mode);
                convertView.setTag(holder);
            } else {
                holder = (viewHolder) convertView.getTag();
            }

            holder.tv_number.setText(blackNumberInfos.get(position).getNumber());
            //mode里有三个数值1、2、3，判断若数字为1，拦截模式为 全部拦截 2，电话拦截 3.短信拦截
            final String mode = blackNumberInfos.get(position).getMode();
            if (mode.equals("1")) {
                holder.tv_mode.setText("全部拦截");
            } else if (mode.equals("2")) {
                holder.tv_mode.setText("电话拦截");
            } else if (mode.equals("3")) {
                holder.tv_mode.setText("短信拦截");
            }


            return convertView;
        }
    }

    static class viewHolder {
        TextView tv_number;
        TextView tv_mode;
    }
}
