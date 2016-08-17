package com.android.yl.phonemanager.fragment;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.bean.AppInfo;
import com.android.yl.phonemanager.db.dao.ApplockDao;
import com.android.yl.phonemanager.engine.AppInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnLockFragment extends Fragment {

    private View view;
    private ListView list_view;
    private TextView tv_unlock;
    private List<AppInfo> appInfos;
    private UnlockAdapter adapter;
    private List<AppInfo> unLockLists;
    private ApplockDao dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_un_lock, null);
        list_view = (ListView) view.findViewById(R.id.list_view);
        tv_unlock = (TextView) view.findViewById(R.id.tv_unlock);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        appInfos = AppInfos.getAppInfos(getActivity());

        dao = new ApplockDao(getActivity());//获取dao实例

        unLockLists = new ArrayList<AppInfo>();//新建未加锁的集合
        for (AppInfo appInfo : appInfos) {
            if (dao.find(appInfo.getPackageName())) {

            } else {
                unLockLists.add(appInfo);
            }
        }

        adapter = new UnlockAdapter();
        list_view.setAdapter(adapter);//忘记给listview添加适配器


    }

    public class UnlockAdapter extends BaseAdapter {

        private ViewHolder holder = null;
        private View view = null;
        AppInfo appInfo;

        @Override
        public int getCount() {
            tv_unlock.setText("未加锁软件(" + unLockLists.size() + ")个");
            return unLockLists.size();
        }

        @Override
        public Object getItem(int position) {
            return unLockLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //给view设置holder对象
            if (convertView == null) {

                view = View.inflate(getActivity(), R.layout.item_unlock, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.iv_unlock = (ImageView) view.findViewById(R.id.iv_unlock);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);


                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            //获取到当前对象
            appInfo = unLockLists.get(position);

            //设置数据
            holder.iv_icon.setImageDrawable(unLockLists.get(position).getIcon());
            holder.tv_name.setText(unLockLists.get(position).getAppName());
            System.out.println("-----" + unLockLists.get(position).getAppName());

            //给holder设置监听事件
            holder.iv_unlock.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 初始化一个位移动画

                    TranslateAnimation translateAnimation = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0);
                    // 设置动画时间
                    translateAnimation.setDuration(1000);
                    // 开始动画
                    view.startAnimation(translateAnimation);

                    new Thread() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 添加到数据库里面
                                    dao.add(appInfo.getPackageName());
                                    // 从当前的页面移除对象
                                    unLockLists.remove(position);
                                    // 刷新界面
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }.start();

                }
            });


            return view;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        ImageView iv_unlock;
        TextView tv_name;

    }


}
