package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.db.dao.AntivirusDao;
import com.android.yl.phonemanager.utils.MD5Utils;

import java.util.List;

public class AntivirusActivity extends Activity {

    private static final int BEGINING = 0;
    private static final int SCANNING = 1;
    private static final int FINISH = 2;
    private ImageView iv_scanning;
    private TextView tv_init_virus;
    private ProgressBar pb;
    private LinearLayout ll_content;
    private ScrollView scrollView;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
    }

    //定义handler，实现子线程中发消息
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BEGINING:
                    tv_init_virus.setText("初始化病毒扫描成功");
                    break;
                case SCANNING:
                    TextView child = new TextView(AntivirusActivity.this);
                    ScanInfo scanInfo = (ScanInfo) msg.obj;//将获取到的对象转换成扫描的信息，在扫描信息中获取应用名字
                    if (scanInfo.desc) {
                        child.setText(scanInfo.appName + "有病毒");
                        child.setTextColor(Color.RED);
                    } else {
                        child.setText(scanInfo.appName + "扫描安全");
                        child.setTextColor(Color.GRAY);

                    }
                    ll_content.addView(child);

                    //给scrollview添加向下自动滑动的样式
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                    System.out.println(scanInfo.appName);
                    break;
                case FINISH:
                    tv_init_virus.setText("扫描成功");
                    iv_scanning.clearAnimation();//扫描完成后关闭动画
                    break;
            }

        }
    };

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                //在此获取消息对象
                message = Message.obtain();
                message.what = BEGINING;
                handler.sendMessage(message);

                PackageManager packageManager = getPackageManager();
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

                int size = installedPackages.size();//获取到安装程序的数量
                //System.out.println("最大数量:" + size);
                pb.setMax(size);//给进度条设置最大数
                int progress = 0;//初始化进度为0

                for (PackageInfo packageInfo : installedPackages) {

                    ScanInfo scanInfo = new ScanInfo();
                    //获取应用名字
                    String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                    scanInfo.appName = appName;
                    //获取包名
                    String packageName = packageInfo.applicationInfo.packageName.toString();
                    scanInfo.packageName = packageName;

                    //获取到应用的资源目录
                    String sourceDir = packageInfo.applicationInfo.sourceDir;
                    String md5 = MD5Utils.getFileMD5(sourceDir);//获取得到的MD5值
                    System.out.println(md5);
                    //判断病毒是否在数据库里
                    String desc = AntivirusDao.checkFileVirus(md5);//把查询后得到的描述取出来

                    if (desc == null) {
                        scanInfo.desc = true;
                    } else {
                        scanInfo.desc = false;
                    }

                    progress++;
                    pb.setProgress(progress);

                    message = Message.obtain();
                    message.what = SCANNING;
                    message.obj = scanInfo;//获取扫描对象，然后发送数据，属于handler中发送数据
                    //handler在此发消息
                    handler.sendMessage(message);
                }

                message = Message.obtain();
                message.what = FINISH;
                handler.sendMessage(message);
            }
        }.start();
    }

    static class ScanInfo {
        boolean desc;
        String appName;
        public String packageName;
    }

    private void initUI() {
        setContentView(R.layout.activity_antivirus);
        iv_scanning = (ImageView) findViewById(R.id.iv_scanning);

        tv_init_virus = (TextView) findViewById(R.id.tv_init_virus);

        pb = (ProgressBar) findViewById(R.id.progressBar1);

        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        /**
         * 第一个参数表示开始的角度 第二个参数表示结束的角度 第三个参数表示参照自己 初始化旋转动画
         */
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 设置动画的时间
        rotateAnimation.setDuration(1000);
        // 设置动画无限循环
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        // 开始动画
        iv_scanning.startAnimation(rotateAnimation);
    }
}
