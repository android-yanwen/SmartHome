package com.gta.smart.scenemodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.gta.smart.smarthome.ImageButtonWithText;
import com.gta.smart.smarthome.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


/**
 * Created by Administrator on 2016/6/12.
 */
public class SceneModelClass extends AppCompatActivity implements View.OnClickListener {
    private ImageButtonWithText id_leave_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_model_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_leave_home_btn = (ImageButtonWithText) findViewById(R.id.id_leave_home_btn);
        id_leave_home_btn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_leave_home_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("打开离家模式");
                builder.setMessage("点击“是”按钮。所有照明灯、电器设备（空调，窗帘等）处于关闭状态");
                builder.setCancelable(false);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
                break;
            default:
                break;
        }
    }
}
