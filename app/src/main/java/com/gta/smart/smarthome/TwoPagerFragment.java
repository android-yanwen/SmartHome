package com.gta.smart.smarthome;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/5/27.
 */
public class TwoPagerFragment extends Fragment implements View.OnClickListener{
    View view;
    private ImageButtonWithText camera_sys_btn, setting_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_pager_fragment_2, container, false);
        camera_sys_btn = (ImageButtonWithText) view.findViewById(R.id.camera_sys_btn);
        camera_sys_btn.setOnClickListener(this);
        setting_btn = (ImageButtonWithText) view.findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_sys_btn:

                break;
            case R.id.setting_btn:

                break;
            default:
                break;
        }
    }
}
