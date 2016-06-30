package com.gta.smart.household_ctrl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.gta.smart.httputility.HandlerResult;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/27.
 */
public class TvCtrl extends AppCompatActivity implements View.OnClickListener {
    private ImageButton tv_up_btn, tv_down_btn, tv_right_btn, tv_left_btn, tv_ok_btn, tv_add_button, tv_minu_button, tv_open2_btn;
    private Context context;
    private ProgressBar progressbar;
    private String tag;
    private ProgressDialog mProgressDialog;
    private HandlerResult handlerResult;
    private RelativeLayout infrared_keyboard_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_ctrl_layout);
        context = TvCtrl.this;
        tag = getLocalClassName();
        ActionBar actionBar = TvCtrl.this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
//        ActionBar actionBar = getActionBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        InternetRequest internetRequest = new InternetRequest(context);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setTitle("Up");
                mProgressDialog.setMessage("execute...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            }

            @Override
            public void onTaskFinish(String result) {
                mProgressDialog.dismiss();
                handlerResult = new HandlerResult(result);
//                Log.i(tag, "handlerResult:" + handlerResult.getRemoteType());
            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.userid = InternetRequest.userId;
        String msg = new Gson().toJson(bean);
        internetRequest.new RequestSmallK().execute(new String[]{"/User/getGeneralRemoteList", msg});
    }

    /**
     * 获取控件在视图中的ID，设置点击监听事件
     */
    private void initView() {
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        tv_up_btn = (ImageButton) findViewById(R.id.tv_up_btn);
        tv_up_btn.setOnClickListener(this);
        tv_down_btn = (ImageButton) findViewById(R.id.tv_down_btn);
        tv_down_btn.setOnClickListener(this);
        tv_right_btn = (ImageButton) findViewById(R.id.tv_right_btn);
        tv_right_btn.setOnClickListener(this);
        tv_left_btn = (ImageButton) findViewById(R.id.tv_left_btn);
        tv_left_btn.setOnClickListener(this);
        tv_ok_btn = (ImageButton) findViewById(R.id.tv_ok_btn);
        tv_ok_btn.setOnClickListener(this);
        tv_add_button = (ImageButton) findViewById(R.id.tv_add_button);
        tv_add_button.setOnClickListener(this);
        tv_minu_button = (ImageButton) findViewById(R.id.tv_minu_button);
        tv_minu_button.setOnClickListener(this);
        tv_open2_btn = (ImageButton) findViewById(R.id.tv_open2_btn);
        tv_open2_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_up_btn:
                sendInfrared(HandlerResult.ACTION_UP);
                break;
            case R.id.tv_down_btn:
                sendInfrared(HandlerResult.ACTION_DOWN);
                break;
            case R.id.tv_right_btn:
                sendInfrared(HandlerResult.ACTION_RIGHT);
                break;
            case R.id.tv_left_btn:
                sendInfrared(HandlerResult.ACTION_LEFT);
                break;
            case R.id.tv_ok_btn:
                sendInfrared(HandlerResult.ACTION_OK);
                break;
            case R.id.tv_add_button:
                sendInfrared(HandlerResult.ACTION_VOLUME_ADD);
                break;
            case R.id.tv_minu_button:
                sendInfrared(HandlerResult.ACTION_VOLUME_SUBTRACT);
                break;
            case R.id.tv_open2_btn:
                sendInfrared(HandlerResult.ACTION_TV_ON_OFF);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.activity_from_left_to_right_in, R.anim.activity_from_left_to_right_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            overridePendingTransition(R.anim.activity_from_left_to_right_in, R.anim.activity_from_left_to_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 向目标设备发送红外码
     * @param action
     */
    private void sendInfrared(String action) {

        InternetRequest internetRequest = new InternetRequest(context);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskFinish(String result) {
                progressbar.setVisibility(View.GONE);
                Log.i(tag, "result:" + result);

            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.userid = handlerResult.getUserId();
        bean.kid = handlerResult.getKid();
        bean.remoteType = handlerResult.getRemoteType();
        bean.order = handlerResult.getOrder(action);
        String msg = new Gson().toJson(bean);
        internetRequest.new RequestSmallK().execute(new String[]{"/KControl/sendGeneralRemoteOrder", msg});
    }
}
