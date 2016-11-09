package com.gta.smart.scenemodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/6/30.
 */
public class SceneModelAdd extends AppCompatActivity {
    private Context context;
    private RelativeLayout scene_model_name_rl;
    private TextView scene_model_name_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_model_add);
        context = SceneModelAdd.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scene_model_name_tv = (TextView) findViewById(R.id.scene_model_name_tv);
        scene_model_name_rl = (RelativeLayout) findViewById(R.id.scene_model_name_rl);
        // 长按修改添加的情景名称
        scene_model_name_rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("输入名称");
                final EditText editText = new EditText(context);
                builder.setView(editText);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText().length() > 0) {
                            scene_model_name_tv.setText(editText.getText());
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                return true;
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

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.scene_triggering_condition_rl:
                startActivity(new Intent(context, SceneModelTriggeringCondition.class));
                overridePendingTransition(R.anim.activity_from_right_to_left_in, R.anim.activity_from_right_to_left_out);
                break;
            case R.id.scene_triggering_action_rl:
                startActivity(new Intent(context, SceneModelTriggeingAction.class));
                overridePendingTransition(R.anim.activity_from_right_to_left_in, R.anim.activity_from_right_to_left_out);
                break;
        }
    }
}
