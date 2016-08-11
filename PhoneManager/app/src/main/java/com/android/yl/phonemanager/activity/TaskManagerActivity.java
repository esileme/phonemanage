package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.db.dao.TaskInfo;
import com.android.yl.phonemanager.engine.TaskInfoParse;
import com.android.yl.phonemanager.utils.SystemInfoUtils;

import java.util.List;

public class TaskManagerActivity extends Activity {

    private ListView list_view;
    private List<TaskInfo> tasksInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initData();
    }

    private void initData() {

        new Thread() {
            @Override
            public void run() {
                tasksInfo = TaskInfoParse.getTasksInfo(TaskManagerActivity.this);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TaskManagerAdapter adapter = new TaskManagerAdapter();
                        list_view.setAdapter(adapter);
                    }
                });
            }
        }.start();


    }

    private void initUI() {
        setContentView(R.layout.activity_task_manager);
        TextView tv_task_process_count = (TextView) findViewById(R.id.tv_task_process_count);
        TextView tv_task_memory = (TextView) findViewById(R.id.tv_task_memory);
        list_view = (ListView) findViewById(R.id.list_view);

        //将获取信息的方法封装成工具类，然后调用。
        long availMem = SystemInfoUtils.availMem(this);
        int runningAppProcessesCount = SystemInfoUtils.runningAppProcessesCount(this);
        int runningServicesCount = SystemInfoUtils.runningServicesCount(this);
        long totalMem = SystemInfoUtils.totalMem(this);

        tv_task_process_count.setText("服务:" + runningServicesCount + "个|" + "进程:" + runningAppProcessesCount + "个");
        tv_task_memory.setText("可用内存/总内存:" + Formatter.formatFileSize(TaskManagerActivity.this, availMem) + "/" + Formatter.formatFileSize(TaskManagerActivity.this, totalMem));

    }

    //全选按钮
    public void selectAll(View v) {

    }

    //反选按钮
    public void selectOppsite(View v) {

    }

    //杀死进程
    public void killProcess(View v) {

    }

    //打开设置
    public void openSetting(View v) {

    }

    private class TaskManagerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tasksInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            //把资源找出来
            View view = View.inflate(TaskManagerActivity.this, R.layout.item_task_manager, null);
            holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            holder.tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
            holder.tv_memorySize = (TextView) view.findViewById(R.id.tv_memorySize);
            holder.cb_task_status = (CheckBox) view.findViewById(R.id.cb_task_status);

            //给资源设置相应的属性
            TaskInfo taskInfo = tasksInfo.get(position);
            holder.iv_icon.setImageDrawable(taskInfo.getIcon());
            holder.tv_name1.setText(taskInfo.getAppName());
            holder.tv_memorySize.setText("内存占用:" + Formatter.formatFileSize(TaskManagerActivity.this, taskInfo.getMemorySize()));

            return view;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name1;
        TextView tv_memorySize;
        CheckBox cb_task_status;
    }
}
