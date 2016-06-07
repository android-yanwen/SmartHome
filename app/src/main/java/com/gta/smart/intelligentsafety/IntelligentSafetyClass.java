package com.gta.smart.intelligentsafety;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/6/7.
 */
public class IntelligentSafetyClass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intelligent_safety_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ActionBar上显示返回图标
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

    public void onClicked(View view) {

    }
}
