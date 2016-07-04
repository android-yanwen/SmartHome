package com.gta.smart.household_ctrl;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.gta.smart.httputility.HandlerResult;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.slideswitch.SlideSwitch;
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
    private SlideSwitch smart_on_of_slide;
    private String kId;
    private boolean isCtrlK = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrared_ctrl_layout);
        context = this;
        tag = getLocalClassName();
        ActionBar actionBar = InfraredCtrl.this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();

        obtainSmallKId();
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
        smart_on_of_slide = (SlideSwitch) findViewById(R.id.smart_on_of_slide);
        smart_on_of_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (isCtrlK) {
                    if (true == state) {
                        openOrCloseSmallKey("open");
                    } else if (false == state) {
                        openOrCloseSmallKey("close");
                    }
                } else {
                    isCtrlK = true;
                }
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ctrl_layout:
//                Log.i(tag, "R.id.id_tv");
                InternetRequest internetRequest = new InternetRequest(context);
                internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
                    @Override
                    public void onTaskStart() {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.show();
                    }

                    @Override
                    public void onTaskFinish(String result) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(InfraredCtrl.this, TvCtrl.class);
                        intent.putExtra("result", result);
                        startActivity(intent);
                        InfraredCtrl.this.overridePendingTransition(R.anim.activity_from_right_to_left_in, R.anim.activity_from_right_to_left_out);
//                Log.i(tag, "handlerResult:" + handlerResult.getRemoteType());
                    }
                });
                InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
                bean.userid = InternetRequest.userId;
                String msg = new Gson().toJson(bean);
                internetRequest.new RequestSmallK().execute(new String[]{"/User/getGeneralRemoteList", msg});
                break;
            case R.id.air_ctrl_layout:
                Log.i(tag, "R.id.id_ari_conditioner");
                break;
            case R.id.smart_on_of_relativelayout:
                if (smart_on_of_slide.isOpen()) {
                    smart_on_of_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    smart_on_of_slide.setOnOrOff(SlideSwitch.ON);
                }
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
                overridePendingTransition(R.anim.activity_from_left_to_right_in, R.anim.activity_from_left_to_right_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            Log.i(tag, "onKeyDown");
            finish();
            overridePendingTransition(R.anim.activity_from_left_to_right_in, R.anim.activity_from_left_to_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    private ProgressDialog progressDialog;
    /**
     * 打开或者关闭小k开关
     * @param key
     */
    private void openOrCloseSmallKey(final String key) {
        InternetRequest internetRequest = new InternetRequest(context);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {
                progressDialog = new ProgressDialog(context);
                if (key.equals("open")) {
                    progressDialog.setTitle("打开小k");
                } else {
                    progressDialog.setTitle("关闭小k");
                }
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onTaskFinish(String result) {
                progressDialog.dismiss();
            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.userid = InternetRequest.userId;
        bean.kid = kId;
        bean.key = key;
        String params = new Gson().toJson(bean);
        InternetRequest.RequestSmallK smallK = internetRequest.new RequestSmallK();
        smallK.execute(new String[]{"/KControl/doSwitchK", params});
    }

    private void obtainSmallKId() {
        InternetRequest internetRequest = new InternetRequest(context);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {

            }

            @Override
            public void onTaskFinish(String result) {
//                Log.i(tag, "result:" + result);
                HandlerResult handlerResult = new HandlerResult(result);
                kId = handlerResult.getKid();
                obtainSmallKStatus();
            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.userid = InternetRequest.userId;
        String params = new Gson().toJson(bean);
        InternetRequest.RequestSmallK smallK = internetRequest.new RequestSmallK();
        smallK.execute(new String[]{"/User/getKList", params});
    }

    /**
     * 获得小K的开关状态
     */
    private void obtainSmallKStatus() {
        InternetRequest internetRequest = new InternetRequest(context);
        internetRequest.setOnAsyncTaskListener(new InternetRequest.OnAsyncTaskListener() {
            @Override
            public void onTaskStart() {

            }

            @Override
            public void onTaskFinish(String result) {
                Log.i(tag, "result:" + result);
                String status = HandlerResult.getSmallKStatus(result);
                if ("close".equals(status)) {
                    smart_on_of_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    smart_on_of_slide.setOnOrOff(SlideSwitch.ON);
                }
            }
        });
        InternetRequest.RequestParamsBean bean = internetRequest.new RequestParamsBean();
        bean.userid = InternetRequest.userId;
        bean.kid = kId;
        String params = new Gson().toJson(bean);
        InternetRequest.RequestSmallK smallK = internetRequest.new RequestSmallK();
        smallK.execute(new String[]{"/KInfo/getKState", params});
    }
}
