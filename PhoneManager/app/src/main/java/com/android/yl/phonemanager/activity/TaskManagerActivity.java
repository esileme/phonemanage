package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.TaskInfo;
import com.android.yl.phonemanager.engine.TaskInfoParse;
import com.android.yl.phonemanager.utils.SystemInfoUtils;
import com.android.yl.phonemanager.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActivity extends Activity {

    private ListView list_view;
    private List<TaskInfo> tasksInfo;
    private TaskManagerAdapter adapter;

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
                        adapter = new TaskManagerAdapter();
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

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object obj = list_view.getItemAtPosition(position);

                ViewHolder holder = (ViewHolder) view.getTag();

                if (obj != null && obj instanceof TaskInfo) {//对条目进行设置点击操作
                    TaskInfo taskInfo = (TaskInfo) obj;
                    if (taskInfo.isChecked()) {
                        taskInfo.setChecked(false);
                        holder.cb_task_status.setChecked(false);
                    } else {
                        taskInfo.setChecked(true);
                        holder.cb_task_status.setChecked(true);
                    }
                }
            }
        });
    }

    //全选按钮
    public void selectAll(View v) {
        /**
         * 遍历所有进程，然后给进程设置全选
         */
        for (TaskInfo taskInfo : tasksInfo) {
            taskInfo.setChecked(true);
        }
        adapter.notifyDataSetChanged();//通知界面刷新

    }

    //反选按钮
    public void selectOppsite(View v) {
        /**
         * 遍历所有进程，然后给进程设置反选
         */
        for (TaskInfo taskInfo : tasksInfo) {
            taskInfo.setChecked(!taskInfo.isChecked());
        }
        adapter.notifyDataSetChanged();//通知界面刷新

    }

    //杀死进程
    public void killProcess(View v) {

        int totalCount = 0;
        int avaliMem = 0;

        List<TaskInfo> killLists = new ArrayList<>();//在遍历中，不能直接对遍历中的数据进行删除，所以，可以新建一个数组，在完成遍历后，对数组中的数据进行删除
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (TaskInfo taskInfo : tasksInfo) {
            if (taskInfo.isChecked()) {
                killLists.add(taskInfo);
                totalCount++;
                avaliMem += taskInfo.getMemorySize();
                //获取到杀死进程的包名
                /*activityManager.killBackgroundProcesses(taskInfo.getPackageName());
                tasksInfo.remove(taskInfo);*/
            }
        }
        for (TaskInfo taskInfo : killLists) {

            tasksInfo.remove(taskInfo);
            activityManager.killBackgroundProcesses(taskInfo.getPackageName());//获取到杀死进程的包名
        }

        UIUtils.showToast(TaskManagerActivity.this, "杀死了" + totalCount + "个内存," + "释放" + Formatter.formatFileSize(TaskManagerActivity.this, avaliMem) + "内存", 1);
        adapter.notifyDataSetChanged();

    }

    //打开设置
    public void openSetting(View v) {

    }

    private class TaskManagerAdapter extends BaseAdapter {

        private ViewHolder holder;
        private View view;

        @Override
        public int getCount() {
            return tasksInfo.size();
        }

        @Override
        public Object getItem(int position) {
            TaskInfo taskInfo = tasksInfo.get(position);
            return taskInfo;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView != null && convertView instanceof LinearLayout) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                holder = new ViewHolder();

                //把资源找出来
                view = View.inflate(TaskManagerActivity.this, R.layout.item_task_manager, null);

                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
                holder.tv_memorySize = (TextView) view.findViewById(R.id.tv_memorySize);
                holder.cb_task_status = (CheckBox) view.findViewById(R.id.cb_task_status);

                view.setTag(holder);
            }
            //给资源设置相应的属性
            TaskInfo taskInfo = tasksInfo.get(position);
            holder.iv_icon.setImageDrawable(taskInfo.getIcon());
            holder.tv_name1.setText(taskInfo.getAppName());
            holder.tv_memorySize.setText("内存占用:" + Formatter.formatFileSize(TaskManagerActivity.this, taskInfo.getMemorySize()));
            if (taskInfo.isChecked()) {
                holder.cb_task_status.setChecked(true);
            } else {
                holder.cb_task_status.setChecked(false);
            }


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
