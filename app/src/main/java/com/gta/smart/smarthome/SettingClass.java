package com.gta.smart.smarthome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gta.smart.entrywindow.EntryAppWindow;
import com.gta.smart.entrywindow.LoadingWin;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SettingClass extends AppCompatActivity {
    private Context context;
    private TextView id_real_name_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manager_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = SettingClass.this;

        setupView();
    }

    private void setupView() {
        id_real_name_tv = (TextView) findViewById(R.id.id_real_name_tv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_exit_2, R.anim.activity_exit_1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.activity_exit_2, R.anim.activity_exit_1);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.id_real_name_rl:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("修改真实姓名");
                builder.setCancelable(false);
                final View v = getLayoutInflater().inflate(R.layout.layout_modify_real_name, null);
                builder.setView(v);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tv = (EditText) v.findViewById(R.id.id_real_name_et);
                        if (!tv.getText().toString().isEmpty()) {
                            id_real_name_tv.setText(tv.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
            case R.id.id_modify_pwd_rl:
                final View pwd_v = getLayoutInflater().inflate(R.layout.layout_modify_pwd, null);
                new AlertDialog.Builder(context)
                        .setTitle("修改密码")
                        .setView(pwd_v)
                        .setCancelable(false)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.id_phone_rl:
                final View phone_v = getLayoutInflater().inflate(R.layout.layout_bind_phone, null);
                new AlertDialog.Builder(context)
                        .setTitle("绑定手机号码")
                        .setCancelable(false)
                        .setView(phone_v)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.id_email_rl:
                final View email_v = getLayoutInflater().inflate(R.layout.layout_bind_email, null);
                new AlertDialog.Builder(context)
                        .setTitle("绑定邮箱")
                        .setCancelable(false)
                        .setView(email_v)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.id_exit_account_rl:
                startActivity(new Intent(context, EntryAppWindow.class));
                break;
        }
    }
}
