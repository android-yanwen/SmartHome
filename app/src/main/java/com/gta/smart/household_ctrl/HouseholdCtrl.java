package com.gta.smart.household_ctrl;

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

import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HouseholdCtrl extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout infrared_ly;
    @Override
    public void onClick(View v) {
        startActivity(new Intent(HouseholdCtrl.this, InfraredCtrl.class));
        HouseholdCtrl.this.overridePendingTransition(R.anim.translate, R.anim.windowout);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.smart_home_layout, null);
        /*Animation animation = AnimationUtils.loadAnimation(HouseholdCtrl.this, R.anim.translate);
        view.startAnimation(animation);*/
        setContentView(view);
        infrared_ly = (RelativeLayout) view.findViewById(R.id.infrared_ly);
        infrared_ly.setOnClickListener(this);

    }
}
