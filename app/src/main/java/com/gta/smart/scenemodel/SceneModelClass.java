package com.gta.smart.scenemodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.gta.smart.smarthome.ImageButtonWithText;
import com.gta.smart.smarthome.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


/**
 * Created by Administrator on 2016/6/12.
 */
<<<<<<< HEAD
public class SceneModelClass extends AppCompatActivity implements View.OnClickListener{
=======
public class SceneModelClass extends AppCompatActivity {
>>>>>>> 88b2e8ee06048ae2a1c5ae91357888376b893436
    private ImageButtonWithText id_leave_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_model_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
<<<<<<< HEAD
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
=======
>>>>>>> 88b2e8ee06048ae2a1c5ae91357888376b893436
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

<<<<<<< HEAD
    public void onClicked(View view) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scene_return_home_btn:

                break;
            case R.id.scene_leave_home_btn:
                break;
        }
    }
=======
>>>>>>> 88b2e8ee06048ae2a1c5ae91357888376b893436
}
