package com.gta.smart.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Administrator on 2016/6/12.
 */
public class SettingClass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            case R.id.personal_set_rl:
                startActivity(new Intent(this, PersonalInfoSettingClass.class));
                overridePendingTransition(R.anim.translate, R.anim.windowout);
                break;
        }
    }
}
