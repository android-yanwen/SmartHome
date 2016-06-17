package com.gta.smart.household_ctrl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.gta.smart.modbus_model.ModbusParser;
import com.gta.smart.slideswitch.SlideSwitch;
import com.gta.smart.socket_utility.TcpSocketUtility;
import com.gta.smart.httputility.InternetRequest;
import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HouseholdCtrl extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout infrared_ly, led_ly, humidifier_ly, corridor_ly;
    private ProgressDialog mProgressDialog ;
    private Context context;
    private String tag;
    private SlideSwitch id_humidifier_slide;
    private SlideSwitch id_leds_slide, id_corridor_led_slide;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ModbusParser.TYPE_HUMIDIFIER:
                    if ((byte) msg.obj == ModbusParser.SWITCH_ON) {
                        id_humidifier_slide.setOnOrOff(SlideSwitch.ON);// 打开加湿器的SlideSwitch
                    } else {
                        id_humidifier_slide.setOnOrOff(SlideSwitch.OFF);// 关闭加湿器的SlideSwitch
                    }
                    break;
                case ModbusParser.TYPE_LEDS:
                    if ((byte) msg.obj == ModbusParser.SWITCH_ON) {
                        id_leds_slide.setOnOrOff(SlideSwitch.ON);// 打开LED灯带的SlideSwitch
                    } else {
                        id_leds_slide.setOnOrOff(SlideSwitch.OFF);// 关闭LED灯带的SlideSwitch
                    }
                    break;
                case ModbusParser.TYPE_CORRIDOR_LED:
                    if ((byte) msg.obj == ModbusParser.SWITCH_ON) {
                        id_corridor_led_slide.setOnOrOff(SlideSwitch.ON);
                    } else {
                        id_corridor_led_slide.setOnOrOff(SlideSwitch.OFF);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infrared_ly:
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
                break;
            case R.id.led_ly:
                if (id_leds_slide.isOpen()) {
                    id_leds_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    id_leds_slide.setOnOrOff(SlideSwitch.ON);
                }
                break;
            case R.id.humidifier_ly:
                if (id_humidifier_slide.isOpen()) {
                    id_humidifier_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    id_humidifier_slide.setOnOrOff(SlideSwitch.ON);
                }
                break;
            case R.id.corridor_ly:
                if (id_corridor_led_slide.isOpen()) {
                    id_corridor_led_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    id_corridor_led_slide.setOnOrOff(SlideSwitch.ON);
                }
                break;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        tag = getLocalClassName();

        initView();

    }

    private void initView() {
        View view = getLayoutInflater().inflate(R.layout.smart_home_layout, null);
        /*Animation animation = AnimationUtils.loadAnimation(HouseholdCtrl.this, R.anim.translate);
        view.startAnimation(animation);*/
        setContentView(view);
        infrared_ly = (RelativeLayout) view.findViewById(R.id.infrared_ly);
        infrared_ly.setOnClickListener(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        led_ly = (RelativeLayout) findViewById(R.id.led_ly);
        led_ly.setOnClickListener(this);
        humidifier_ly = (RelativeLayout) findViewById(R.id.humidifier_ly);
        humidifier_ly.setOnClickListener(this);
        corridor_ly = (RelativeLayout) findViewById(R.id.corridor_ly);
        corridor_ly.setOnClickListener(this);

        id_humidifier_slide = (SlideSwitch) findViewById(R.id.id_humidifier_slide);
        id_humidifier_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {

            }
        });
        id_leds_slide = (SlideSwitch) findViewById(R.id.id_leds_slide);
        id_leds_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {

            }
        });
        id_corridor_led_slide = (SlideSwitch) findViewById(R.id.id_corridor_led_slide);
        id_corridor_led_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {

            }
        });
    }

    /**
     * 获取被控制的电器的当前状态（开或者关）
     */
    @Override
    protected void onResume() {
        super.onResume();
        judgeOnOrOffTheControl(ModbusParser.TYPE_HUMIDIFIER);
        judgeOnOrOffTheControl(ModbusParser.TYPE_LEDS);
        judgeOnOrOffTheControl(ModbusParser.TYPE_CORRIDOR_LED);

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

    /**
     * 判断控制器的状态 on or off
     * @param ctrlName
     */
    private void judgeOnOrOffTheControl(final byte ctrlName) {
        new TcpSocketUtility().requestData(new ModbusParser().queryStatusOrder(ctrlName),
                new TcpSocketUtility.SocketDataHandle() {
                    @Override
                    public void getData(byte[] data) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(tag, ModbusParser.bytesToHexString(data));
                        Message message = myHandler.obtainMessage();
                        message.what = ctrlName;
                        message.obj = new ModbusParser().getControlStatus(data);
                        myHandler.sendMessage(message);
                    }
                });
    }


}
