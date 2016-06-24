package com.gta.smart.bedroom;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/6/21.
 */
public class BedroomClass extends AppCompatActivity implements View.OnClickListener {
    Button id_medicine_chest_btn, id_watch_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bedroom_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_medicine_chest_btn = (Button) findViewById(R.id.id_medicine_chest_btn);
        id_medicine_chest_btn.setOnClickListener(this);
        id_watch_btn = (Button) findViewById(R.id.id_watch_btn);
        id_watch_btn.setOnClickListener(this);
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
            case R.id.id_medicine_chest_btn:
                launchAPK("com.mordo.health.familycarev6", "com.mordo.health.familycarev6.activity.LoadActivity");
                break;
            case R.id.id_watch_btn:

                break;
        }
    }

    /**
     * 启动包名为mPackName，主Activity是mClassName的应用程序
     * @param mPackName
     * @param mClassName
     */
    public void launchAPK(String mPackName, String mClassName) {
        ComponentName toLaunch = new ComponentName(mPackName, mClassName);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_MONKEY);
        intent.setComponent(toLaunch);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }
}
