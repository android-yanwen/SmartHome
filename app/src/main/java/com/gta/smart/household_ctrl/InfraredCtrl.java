package com.gta.smart.household_ctrl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.gta.smart.httputility.HttpUtility;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/27.
 */
public class InfraredCtrl extends AppCompatActivity {
    private String tag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrared_ctrl_layout);
        tag = getPackageName();
        ActionBar actionBar = InfraredCtrl.this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.id_tv:
                Log.i(tag, "R.id.id_tv");
                startActivity(new Intent(InfraredCtrl.this, TvCtrl.class));
                break;
            case R.id.id_ari_conditioner:
                Log.i(tag, "R.id.id_ari_conditioner");
                break;
            default:
                break;
        }
    }

//    class RequestParamsBean{
//        String username;
//    }
    @Override
    protected void onResume() {
        super.onResume();
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
        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            Log.i(tag, "onKeyDown");
            finish();
            overridePendingTransition(R.anim.activity_exit_2, R.anim.activity_exit_1);
        }
        return super.onKeyDown(keyCode, event);
    }
}
