package com.android.yl.phonemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主页面
 *
 * @author 11111111111111111
 */
public class HomeActivity extends Activity {

    private GridView gvHome;

    private String[] mItems = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理",
            "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

    private int[] mPics = new int[]{R.drawable.home_safe,
            R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize,
            R.drawable.home_tools, R.drawable.home_settings};
    private AlertDialog dialog;
    private SharedPreferences preferences;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = getSharedPreferences("cfg", MODE_PRIVATE);

        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new HomeAdapter());
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取位置信息，判断点击的位置，如果点击到一个item，就跳转到到当前的item
                //此方法不是在获取position中写，而是在gridview的项目点击监听事件中写
                switch (position) {
                    case 8:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                    case 0:
                        showPassWordDialog();
                        //showEnterPassword();
                        break;
                }
            }
        });
    }


    /**
     * 定义一个适配器，用来接收gridview里面的主程序视图
     */
    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {

            return mItems[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this,
                    R.layout.home_list_item, null);
            ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
            TextView tvItem = (TextView) view.findViewById(R.id.tv_item);

            tvItem.setText(mItems[position]);

            ivItem.setImageResource(mPics[position]);
            return view;
        }

    }

    /**
     * 定义一个类，用来判断是进入输入密码还是设置密码页面
     */
    public void showPassWordDialog() {
        final String savedPassWord = preferences.getString("password", null);
        if (!TextUtils.isEmpty(savedPassWord)) {
            showPassWordEnterDialog();
        } else {
            showEnterPassword();
        }
    }

    /**
     * 设置密码的对话框设置
     */
    private void showEnterPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        dialog.setView(view);

        final EditText etPass = (EditText) view.findViewById(R.id.et_password);
        final EditText etPassConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
        Button okBtn = (Button) view.findViewById(R.id.btn_ok);//此处应为view.findviewbyid而不是findviewbyid
        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passWord = etPass.getText().toString();
                String passWordConfirm = etPassConfirm.getText().toString();
                if (!TextUtils.isEmpty(passWord) && !TextUtils.isEmpty(passWordConfirm)) {
                    if (passWord.equals(passWordConfirm)) {
                        preferences.edit().putString("password", MD5Utils.encode(passWord)).commit();
                        Toast.makeText(HomeActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LostAndFoundActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "输入不匹配", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    /**
     * 输入密码确认进入页面
     */
    private void showPassWordEnterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_show_password, null);
        dialog.setView(view);

        final EditText etPass = (EditText) view.findViewById(R.id.et_password_show);
        Button okBtn = (Button) view.findViewById(R.id.btn_ok_show);//此处应为view.findviewbyid而不是findviewbyid
        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel_show);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String password = etPass.getText().toString().trim();
                if (!TextUtils.isEmpty(password)) {
                    String savedPassword = preferences.getString("password", null);
                    if (MD5Utils.encode(password).equals(savedPassword)) {
                        Toast.makeText(HomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LostAndFoundActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "密码不对", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
