package com.android.yl.phonemanager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.yl.phonemanager.R;
import com.android.yl.phonemanager.utils.SmsUtils;
import com.android.yl.phonemanager.utils.UIUtils;

public class AtoolsActivity extends Activity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);

        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/tencent/QQfile_recv/1.mp4");
        //Uri uri = Uri.parse("http://localhost:8080/tt.mp4");
        //Uri uri = Uri.parse("http://data.vod.itc.cn/?rb=1&prot=1&key=jbZhEJhlqlUN-Wj_HEI8BjaVqKNFvDrn&prod=flash&pt=1&new=/218/73/f7H15r7zTLys0J6z53cmwC.mp4");
        System.out.println("路径===========" + Environment.getExternalStorageDirectory().getPath());
        VideoView videoPlayer = (VideoView) findViewById(R.id.vv_video);
        videoPlayer.setMediaController(new MediaController(this));

        videoPlayer.setVideoURI(uri);
        videoPlayer.start();
        videoPlayer.requestFocus();
    }

    public void numberAddressQuery(View view) {
        startActivity(new Intent(AtoolsActivity.this, AddressActivity.class));
    }

    public void backUpsms(View view) {

        //new一个提示条
        dialog = new ProgressDialog(AtoolsActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("正在备份中。。。。");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();


        new Thread() {
            @Override
            public void run() {

                boolean backUp = SmsUtils.backUp(AtoolsActivity.this, dialog);
                if (backUp) {
                    /*Looper.prepare();
                    Toast.makeText(AtoolsActivity.this, "备份成功", Toast.LENGTH_LONG).show();
                    Looper.loop();*/
                    UIUtils.showToast(AtoolsActivity.this, "备份成功", Toast.LENGTH_LONG);
                } else {
                    Looper.prepare();
                    Toast.makeText(AtoolsActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                dialog.dismiss();
            }
        }.start();

    }
}
