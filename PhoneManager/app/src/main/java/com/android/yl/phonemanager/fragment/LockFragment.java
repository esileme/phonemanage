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


public class LockFragment extends Fragment {


    private View view;
    private TextView tv_lock;
    private ListView list_view;
    private List<AppInfo> appInfos;
    private List<AppInfo> lockLists;
    private ApplockDao dao;
    private LockAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lock, null);
        tv_lock = (TextView) view.findViewById(R.id.tv_lock);
        list_view = (ListView) view.findViewById(R.id.list_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        appInfos = AppInfos.getAppInfos(getActivity());

        lockLists = new ArrayList<>();
        dao = new ApplockDao(getActivity());
        for (AppInfo appInfo : appInfos) {
            if (dao.find(appInfo.getPackageName())) {
                lockLists.add(appInfo);
            } else {
            }
        }

        adapter = new LockAdapter();
        list_view.setAdapter(adapter);

    }

    private class LockAdapter extends BaseAdapter {

        private View view;
        private ViewHolder holder;

        @Override
        public int getCount() {
            tv_lock.setText("未加锁软件(" + lockLists.size() + ")个");
            return lockLists.size();
        }

        @Override
        public Object getItem(int position) {
            return lockLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                view = View.inflate(getActivity(), R.layout.item_lock, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.iv_lock = (ImageView) view.findViewById(R.id.iv_lock);
                holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            final AppInfo appInfo = lockLists.get(position);
            holder.iv_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_name.setText(appInfo.getAppName());

            holder.iv_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f,
                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    translateAnimation.setDuration(1000);
                    view.startAnimation(translateAnimation);

                    new Thread() {
                        public void run() {

                            SystemClock.sleep(1000);

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    dao.delete(appInfo.getPackageName());
                                    lockLists.remove(position);
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
        TextView tv_name;
        ImageView iv_lock;
    }
}
