package com.yumf.jince.statusbarcompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yumf.jince.statusbar.StatusBarCompat;

public class SetStatusBarShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_status_bar_hide_show);

        findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarCompat.newBuilder()
                        .fullScreen(true)
                        .build(SetStatusBarShowActivity.this)
                        .apply();
            }
        });

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarCompat.newBuilder()
                        .fullScreen(false)
                        .build(SetStatusBarShowActivity.this)
                        .apply();
            }
        });
    }
}
