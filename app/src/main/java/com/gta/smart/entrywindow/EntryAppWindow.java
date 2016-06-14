package com.gta.smart.entrywindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gta.smart.smarthome.R;

/**
 * Created by Administrator on 2016/6/13.
 */
public class EntryAppWindow extends AppCompatActivity {
    private Button register_btn;
    private Button load_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_app_win);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        load_btn = (Button) findViewById(R.id.load_btn);
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryAppWindow.this, LoadingWin.class));
                finish();
            }
        });
    }
}
