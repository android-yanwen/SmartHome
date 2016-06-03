package com.gta.smart.smarthome;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gta.smart.household_ctrl.HouseholdCtrl;

/**
 * Created by Administrator on 2016/5/27.
 */
public class OnePagerFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ImageButtonWithText smartHomeBnt;
    private ImageButtonWithText evenMonitorBnt;
    private ImageButtonWithText securityBnt;
    private ImageButtonWithText sceneModelBnt;
    private ImageButtonWithText doorSysBnt;
    private ImageButtonWithText bedroomBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_pager_fragment_1, container, false);
        smartHomeBnt = (ImageButtonWithText) view.findViewById(R.id.homn_ctrl_btn);
        smartHomeBnt.setOnClickListener(this);
        evenMonitorBnt = (ImageButtonWithText) view.findViewById(R.id.even_monitor_btn);
        evenMonitorBnt.setOnClickListener(this);
        securityBnt = (ImageButtonWithText) view.findViewById(R.id.security_btn);
        securityBnt.setOnClickListener(this);
        sceneModelBnt = (ImageButtonWithText) view.findViewById(R.id.scene_model_btn);
        sceneModelBnt.setOnClickListener(this);
        doorSysBnt = (ImageButtonWithText) view.findViewById(R.id.door_sys_btn);
        doorSysBnt.setOnClickListener(this);
        bedroomBtn = (ImageButtonWithText) view.findViewById(R.id.bedroom_btn);
        bedroomBtn.setOnClickListener(this);
        return view/*super.onCreateView(inflater, container, savedInstanceState)*/;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.homn_ctrl_btn:
                startActivity(new Intent(getActivity(), HouseholdCtrl.class));
//                overridePendingTransition(R.anim.translate, R.anim.windowout);
                break;
            case R.id.even_monitor_btn:
                break;
            case R.id.security_btn:
                break;
            case R.id.scene_model_btn:
                break;
            case R.id.door_sys_btn:  //门禁系统:

                break;
            case R.id.bedroom_btn:
                break;
        }
    }

}
