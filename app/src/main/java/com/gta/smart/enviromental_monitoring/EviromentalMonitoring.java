package com.gta.smart.enviromental_monitoring;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gta.smart.slideswitch.SlideSwitch;
import com.gta.smart.smarthome.R;
import com.gta.smart.webserver.WebServerThread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/4.
 */
public class EviromentalMonitoring extends AppCompatActivity {
    private static final String tag = "EviromentalMonitoring";
    static final String URL = "http://192.168.187.21:8080/Services.asmx";
    private Timer timer;
    private int updateUi = 1;
    private TextView id_temp_humi_disp;
    private TextView id_is_there_smoke_disp;
    private TextView id_is_there_gas_disp;
    private TextView id_illuminance_disp;
    private TextView id_is_there_people_disp;
    private TextView id_is_there_open_door_disp;
    private SlideSwitch humidifier_on_off;
    private SlideSwitch siren_on_off;
    private SlideSwitch curtain_slide;
    private SlideSwitch corridor_lamp_slide;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 更新UI线程

            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evir_monitor_window);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        id_temp_humi_disp = (TextView) findViewById(R.id.id_temp_humi_disp);
        id_is_there_smoke_disp = (TextView) findViewById(R.id.id_is_there_smoke_disp);
        id_is_there_gas_disp = (TextView) findViewById(R.id.id_is_there_gas_disp);
        id_illuminance_disp = (TextView) findViewById(R.id.id_illuminance_disp);
        id_is_there_people_disp = (TextView) findViewById(R.id.id_is_there_people_disp);
        id_is_there_open_door_disp = (TextView) findViewById(R.id.id_is_there_open_door_disp);

        humidifier_on_off = (SlideSwitch) findViewById(R.id.humidifier_on_off);
        humidifier_on_off.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {

            }
        });
        siren_on_off = (SlideSwitch) findViewById(R.id.siren_on_off);
        siren_on_off.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {

            }
        });
        curtain_slide = (SlideSwitch) findViewById(R.id.curtain_slide);
        curtain_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (state) {
                    findViewById(R.id.drape_img).setBackground(getResources().getDrawable(R.drawable.envir_monitor_curtain_open_icon));
                } else {
                    findViewById(R.id.drape_img).setBackground(getResources().getDrawable(R.drawable.envir_monitor_curtain_close_icon));
                }
            }
        });
        corridor_lamp_slide = (SlideSwitch) findViewById(R.id.corridor_lamp_slide);
        corridor_lamp_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (state) {
                    findViewById(R.id.corridor_img).setBackground(getResources().getDrawable(R.drawable.envir_monitor_corridor_lamp_open_icon));
                } else {
                    findViewById(R.id.corridor_img).setBackground(getResources().getDrawable(R.drawable.envir_monitor_corridor_lamp_close_icon));
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

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new WebServerThread(new WebServerThread.WebServerResult() {
                    @Override
                    public void onResult(String result) {
                        Log.i(tag, "web server result:" + result);
                        // 获取云服务器返回的结果
                        Message message = handler.obtainMessage(updateUi);
                        message.obj = (Object) result;
                        handler.sendMessage(message);
                    }
                }, URL).start();
                Log.i(tag, "timer schedule");
            }
        }, 0, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.id_corridor_lamp_rl:
                if (corridor_lamp_slide.isOpen()) {
                    corridor_lamp_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    corridor_lamp_slide.setOnOrOff(SlideSwitch.ON);
                }
                break;
            case R.id.id_curtain_rl:
                if (curtain_slide.isOpen()) {
                    curtain_slide.setOnOrOff(SlideSwitch.OFF);
                } else {
                    curtain_slide.setOnOrOff(SlideSwitch.ON);
                }
                break;
        }
    }
}