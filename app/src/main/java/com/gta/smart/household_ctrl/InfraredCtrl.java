package com.gta.smart.household_ctrl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gta.smart.httputility.HttpUtility;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.slideswitch.SlideSwitch;
import com.gta.smart.smarthome.ImageButtonWithText;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/27.
 */
public class InfraredCtrl extends AppCompatActivity {
    private String tag;
    private RelativeLayout smart_on_of_relativelayout;
    private TextView on_off_name;
//    private ImageButtonWithText id_tv;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrared_ctrl_layout);
        context = this;
        tag = getPackageName();
        ActionBar actionBar = InfraredCtrl.this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();

    }

    private void initView() {
        smart_on_of_relativelayout = (RelativeLayout) findViewById(R.id.smart_on_of_relativelayout);
        // 长按布局修改名称
        smart_on_of_relativelayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(tag, "smart_on_off_relativelayout long clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("输入名称");
                View view = getLayoutInflater().inflate(R.layout.edit_name_dialog, null);
                final EditText name_et = (EditText) view.findViewById(R.id.input_name_et);
                name_et.setHint(on_off_name.getText().toString());
                builder.setView(view);
                builder.setCancelable(true);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        on_off_name.setText(name_et.getText().toString());
                    }
                });
                builder.setPositiveButton("Cancel", null);
                builder.show();
                return true; //返回false无震动，true有震动
            }
        });
        on_off_name = (TextView) findViewById(R.id.on_off_name);
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
