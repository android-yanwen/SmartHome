package com.gta.smart.entrywindow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gta.smart.smarthome.MainActivity;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/7/1.
 */
public class RegisterSettingPasswordWin extends AppCompatActivity {
    private Context context;
    private Button register_confirm_password_btn;
    private EditText register_input_password_et;
    private EditText register_again_input_password_et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_setting_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = RegisterSettingPasswordWin.this;
        initView();
    }

    private void initView() {
        register_input_password_et = (EditText) findViewById(R.id.register_input_password_et);
        register_again_input_password_et = (EditText) findViewById(R.id.register_again_input_password_et);
        register_confirm_password_btn = (Button) findViewById(R.id.register_confirm_password_btn);
        register_confirm_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = register_input_password_et.getText().toString();
                String password2 = register_again_input_password_et.getText().toString();
                if (!password.isEmpty()) {
                    if (password.equals(password2)) {
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(context, "两次输入密码不一样，请重新输入。", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
