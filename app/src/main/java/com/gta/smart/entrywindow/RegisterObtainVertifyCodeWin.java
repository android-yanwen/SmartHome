package com.gta.smart.entrywindow;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gta.smart.smarthome.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RegisterObtainVertifyCodeWin extends AppCompatActivity {
    private static final String tag = "Register";
    private Context context;
    private String phoneNumber;
    private int timeLength; //设置倒计时时间长度 s
    private Timer timer;
    private TextView register_again_obtain_verify_code_tv;
    private String SEND = "sms_send";
    private String DELIVERED = "sms_delivered";
    private SmsSendBroadcast smsSend;
    private SmsDeliveredBroadcast smsDelivered;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (timeLength < 0) {
                timer.cancel();
                timer = null;
                register_again_obtain_verify_code_tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                register_again_obtain_verify_code_tv.setText("重新获取验证码");
            } else {
                register_again_obtain_verify_code_tv.setTextColor(getResources().getColor(R.color.colorBlack));
                register_again_obtain_verify_code_tv.setText("重新获取验证码(" + msg.obj + ")");
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_verify_code_win);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = RegisterObtainVertifyCodeWin.this;
        initView();
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phone_number");
        setTimeLength(60);
        CountDown();

        smsSend = new SmsSendBroadcast();
        registerReceiver(smsSend, new IntentFilter(SEND));
        smsDelivered = new SmsDeliveredBroadcast();
        registerReceiver(smsDelivered, new IntentFilter(DELIVERED));
        sendSMS(phoneNumber, "你好");
    }

    private void initView() {
        register_again_obtain_verify_code_tv = (TextView) findViewById(R.id.register_again_obtain_verify_code_tv);
        register_again_obtain_verify_code_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer == null) {
                    setTimeLength(60);
                    CountDown();
                }
            }
        });
    }

    /**
     * 计算倒计时60s
     */
    private void CountDown() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeLength--;
                    Message message = handler.obtainMessage(1, timeLength);
                    handler.sendMessage(message);
                }
            }, 0, 1000);
        }
    }

    /**
     * 设置倒计时时间
     * @param length
     */
    private void setTimeLength(int length) {
        this.timeLength = (length == 0 ? 60 : length);
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

    private void sendSMS(String mobile, String content) {
        SmsManager manager = SmsManager.getDefault();
        PendingIntent sendPI = PendingIntent.getActivity(context, 0, new Intent(SEND), 0);
        PendingIntent deliveredPI = PendingIntent.getActivity(context, 0, new Intent(DELIVERED), 0);
        manager.sendTextMessage(mobile, null, content, sendPI, deliveredPI);
    }

    /**
     * 发送状态监听广播
     */
    private class SmsSendBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SEND)) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i(tag, "----->>Activity.RESULT_OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.i(tag, "RESULT_ERROR_GENERIC_FAILURE");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.i(tag, "RESULT_ERROR_NO_SERVICE");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.i(tag, "RESULT_ERROR_NULL_PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.i(tag, "RESULT_ERROR_RADIO_OFF");
                        break;
                }
            }
        }
    }

    /**
     * 数据是否送达目标监听广播
     */
    private class SmsDeliveredBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DELIVERED)) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i(tag, "RESULT_OK");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(tag, "RESULT_CANCELED");
                        break;
                }
            }
        }
    }

}
