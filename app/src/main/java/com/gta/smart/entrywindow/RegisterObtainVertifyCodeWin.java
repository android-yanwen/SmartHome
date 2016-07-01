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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gta.smart.smarthome.R;

import java.util.List;
import java.util.Random;
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
    private TextView register_hint_tv;
    private EditText register_input_vertify_code_edittext;
    private Button register_next_btn;
    private String SEND = "sms_send";
    private String DELIVERED = "sms_delivered";
    private SmsSendBroadcast smsSend;
    private SmsDeliveredBroadcast smsDelivered;
    private String verifyCode = "";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (timeLength < 0) {
                timer.cancel();
                timer = null;
                verifyCode = "";
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
        if (phoneNumber != null) {
            register_hint_tv.setText("请输入" + phoneNumber + "收到的短信验证码");
        }
        setTimeLength(60);
        CountDown();

        // 获得4位数的校验码
        verifyCode = generateVerifyCode();
        // 注册发送监听广播，如果发送成功将捕获到系统的广播
        smsSend = new SmsSendBroadcast();
        registerReceiver(smsSend, new IntentFilter(SEND));
        // 注册对方接收广播，如果对方接收成功将捕获到系统的广播
        smsDelivered = new SmsDeliveredBroadcast();
        registerReceiver(smsDelivered, new IntentFilter(DELIVERED));
        // 发送短信校验码
        sendSMS(phoneNumber, smsHintMessage(verifyCode));
    }

    private void initView() {
        register_again_obtain_verify_code_tv = (TextView) findViewById(R.id.register_again_obtain_verify_code_tv);
        register_again_obtain_verify_code_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer == null) {
                    verifyCode = generateVerifyCode();
                    setTimeLength(60);
                    CountDown();
                    sendSMS(phoneNumber, smsHintMessage(verifyCode));
                }
            }
        });
        register_input_vertify_code_edittext = (EditText) findViewById(R.id.register_input_vertify_code_edittext);
        register_hint_tv = (TextView) findViewById(R.id.register_hint_tv);
        register_next_btn = (Button) findViewById(R.id.register_next_btn);
        register_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyCode.equals(register_input_vertify_code_edittext.getText().toString())) {
                    Toast.makeText(context, "校验成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, RegisterSettingPasswordWin.class));
                    finish();
                } else {
                    register_input_vertify_code_edittext.setError("校验码错误");
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

    /**
     * 获得短信提示消息
     * @param verify 加入的校验码
     * @return
     */
    private String smsHintMessage(String verify) {
        return "尊敬的客户：感谢您注册国泰安智能家居客户端账号，您的校验码是" + verify + "，如果非本人操作请勿理会，该校验码将在一分钟之后失效";
    }

    /**
     * 发送一条短信
     * @param mobile
     * @param content
     */
    private void sendSMS(String mobile, String content) {
        // 获取短信管理器
        SmsManager manager = SmsManager.getDefault();
        PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, new Intent(SEND), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
        // 拆分短信内容，因为每一条短信有长度限制
        List<String> list = manager.divideMessage(content);
        for (String msg : list) {
            manager.sendTextMessage(mobile, null, msg, sendPI, deliveredPI);
        }
    }

    /**
     * 产生获取4位数的校验码
     * @return
     */
    private String generateVerifyCode() {
        int verifyCodeBitNum = 4;
        int[] verifyCodeSet = new int[4];
        String verifyCodeStr = "";
        for (int i = 0; i < verifyCodeBitNum; i++) {
            verifyCodeSet[i] = new Random().nextInt(10);
        }
        for (int i = 0; i < verifyCodeBitNum; ++i) {
            verifyCodeStr += Integer.toString(verifyCodeSet[i]);
        }
        return verifyCodeStr;
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
                        Toast.makeText(context, "请插入SIM卡\n测试校验码是" + verifyCode + "", Toast.LENGTH_SHORT).show();
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
