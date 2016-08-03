package com.android.yl.phonemanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private TextView tvVersion;
    private String mVersionName;
    private int mVersionCode;
    private String mDesc;
    private String mDownloadUrl;
    private TextView tvProgress;

    private static final int CODE_UPLOAD_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_IO_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPLOAD_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_IO_ERROR:
                    Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "json解析异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this, "url解析异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    Toast.makeText(SplashActivity.this, "进入了主页面", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
    private HttpURLConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号:" + getVersionName());

        copyDB("address.db");

        tvProgress = (TextView) findViewById(R.id.tv_progress);
        SharedPreferences preferences = getSharedPreferences("cfg", MODE_PRIVATE);

        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root_splash);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1f);
        animation.setDuration(2000);
        rlRoot.startAnimation(animation);

        boolean autoUpdate = preferences.getBoolean("auto_update", true);
        if (autoUpdate) {
            checkVersion();
        } else {
            mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000);
        }
    }

    /**
     * 获取当前程序的版本号
     *
     * @return
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            System.out.println("versionCode" + versionCode + "versionName" + versionName);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 传给获得的返回码
     *
     * @return
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            System.out.println("versionCode" + versionCode + "versionName" + versionName);

            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 检查版本更新
     */
    public void checkVersion() {
        new Thread() {

            Message msg = Message.obtain();//获取一个方法

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL("http://192.168.1.106:8080/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");//设置请求方法
                    connection.setConnectTimeout(5000);//设置连接超时时间
                    connection.setReadTimeout(5000);//设置读取超时时间
                    connection.connect();//连接到服务器

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String reslut = StreamUtils.readFromStream(inputStream);
                        System.out.println("网络返回" + reslut);
                        JSONObject jo = new JSONObject(reslut);
                        mVersionName = jo.getString("versionName");
                        mVersionCode = jo.getInt("versionCode");
                        mDesc = jo.getString("description");
                        mDownloadUrl = jo.getString("downloadUrl");

                        if (mVersionCode > getVersionCode()) {
                            //showUpdateDialog();//在此处不能调用显示对话框的方法，相当于在子线程中刷新ui，所以要写一个handler方法。
                            msg.what = CODE_UPLOAD_DIALOG;
                        } else {
                            msg.what = CODE_ENTER_HOME;
                        }

                    }

                } catch (MalformedURLException e) {
                    msg.what = CODE_URL_ERROR;
                    //url解析异常
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = CODE_IO_ERROR;
                    //网络异常
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = CODE_JSON_ERROR;
                    //json解析异常
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long usedTime = endTime - startTime;
                    if (usedTime < 2000) {
                        try {
                            Thread.sleep(2000 - usedTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
        }.start();

    }

    /**
     * 升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本" + mVersionName);
        builder.setMessage(mDesc);
        builder.setCancelable(false);//禁用返回键功能
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this, "下载地址是:" + mDownloadUrl, Toast.LENGTH_SHORT).show();
                downLoad();//开始下载文件
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this, "下次更新", Toast.LENGTH_SHORT).show();
                enterHome();
            }
        });
        builder.show();

    }

    /**
     * 下载页面的制作与完成
     */
    private void downLoad() {
        if (true) {
            String target = getFilesDir() + "/app.apk";
            //使用utils工具下载文件
            HttpUtils utils = new HttpUtils();
            tvProgress.setVisibility(View.VISIBLE);

            utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    System.out.println("下载进度" + current + "/" + total);
                    tvProgress.setText("下载进度" + current * 100 / total + "%");
                }

                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    //在下载成功后，系统自动跳转到安装界面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(arg0.result),
                            "application/vnd.android.package-archive");
                    //startActivity(intent);
                    startActivityForResult(intent, 0);

                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(SplashActivity.this, "没有sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    //返回结果与startActivityForResult配对
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void enterHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 拷贝数据库
     */
    private void copyDB(String dbName) {
        File filesDir = getFilesDir();
        System.out.println("路径:" + filesDir.getAbsolutePath());
        File destFile = new File(getFilesDir(), dbName);// 要拷贝的目标地址

        if (destFile.exists()) {
            System.out.println("数据库" + dbName + "已存在!");
            return;
        }

        FileOutputStream out = null;
        InputStream in = null;

        try {
            in = getAssets().open(dbName);
            out = new FileOutputStream(destFile);

            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /* public static String copyDB1(String dbName) throws IOException {

        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>

        File dir = new File("data/data/" + MyApplication.getContext().getPackageName() + "/databases");
        LogUtil.i("!dir.exists()=" + !dir.exists());
        LogUtil.i("!dir.isDirectory()=" + !dir.isDirectory());

        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        File file = new File(dir, dbName);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
        if (!file.exists()) {
            try {
                file.createNewFile();

                inputStream = MyApplication.getContext().getClass().getClassLoader().getResourceAsStream("assets/" + dbName);
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;

                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();

            } finally {

                if (outputStream != null) {

                    outputStream.flush();
                    outputStream.close();

                }
                if (inputStream != null) {
                    inputStream.close();
                }

            }

        }
        System.out.println(file.getPath());
        return file.getPath();

    }
*/
}
