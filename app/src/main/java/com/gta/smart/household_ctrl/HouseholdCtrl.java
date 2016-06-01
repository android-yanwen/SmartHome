package com.gta.smart.household_ctrl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HouseholdCtrl extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout infrared_ly;
    private ProgressDialog mProgressDialog ;
    private Context context;
    @Override
    public void onClick(View v) {

        InternetRequest internetRequest = new InternetRequest(HouseholdCtrl.this);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setTitle("Request remote data");
                mProgressDialog.setMessage("Loading.....");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void onTaskFinish(String result) {
                mProgressDialog.dismiss();
                startActivity(new Intent(HouseholdCtrl.this, InfraredCtrl.class));
                HouseholdCtrl.this.overridePendingTransition(R.anim.translate, R.anim.windowout);
            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.username = "13040872678";
        String params = new Gson().toJson(bean);
        internetRequest.new RequestUserID().execute(params);  //获取AccessToken和UserId

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.smart_home_layout, null);
        /*Animation animation = AnimationUtils.loadAnimation(HouseholdCtrl.this, R.anim.translate);
        view.startAnimation(animation);*/
        setContentView(view);
        context = this;
        infrared_ly = (RelativeLayout) view.findViewById(R.id.infrared_ly);
        infrared_ly.setOnClickListener(this);

    }
}
