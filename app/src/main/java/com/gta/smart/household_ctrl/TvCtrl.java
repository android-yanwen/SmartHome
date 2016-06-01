package com.gta.smart.household_ctrl;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/27.
 */
public class TvCtrl extends AppCompatActivity implements View.OnClickListener {
    private ImageButton tv_up_btn;
    private Context context;
//    private ProgressBar myprogressbar;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_ctrl_layout);
        context = TvCtrl.this;
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
//        myprogressbar = (ProgressBar) findViewById(R.id.myprogressbar);
        tv_up_btn = (ImageButton) findViewById(R.id.tv_up_btn);
        tv_up_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_up_btn:
                break;
            default:
                break;
        }
    }
}
