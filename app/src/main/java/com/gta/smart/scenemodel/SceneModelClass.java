package com.gta.smart.scenemodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.gta.smart.smarthome.ImageButtonWithText;
import com.gta.smart.smarthome.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


/**
 * Created by Administrator on 2016/6/12.
 */
public class SceneModelClass extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageButtonWithText id_leave_home_btn;
    private Button return_home_apply_btn;
    private Button leave_home_apply_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_model_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = SceneModelClass.this;
    }

    private void setupView() {
        leave_home_apply_btn = (Button) findViewById(R.id.leave_home_apply_btn);
        leave_home_apply_btn.setOnClickListener(this);
        return_home_apply_btn = (Button) findViewById(R.id.return_home_apply_btn);
        return_home_apply_btn.setOnClickListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scene_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClicked(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_home_apply_btn:
                break;
            case R.id.leave_home_apply_btn:
                break;
        }
    }
}
