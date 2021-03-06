package com.yumf.jince.statusbarcompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yumf.jince.statusbar.StatusBarCompat;

public class SetStatusBarHideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set_status_bar_hide);
        setContentView(R.layout.activity_set_status_bar_transparent);

//        StatusBarCompat compat = new StatusBarCompat(this);
//        compat.setFullScreen(true);

        StatusBarCompat.newBuilder()
                .fullScreen(true)
                .build(this)
                .apply();
    }
}
