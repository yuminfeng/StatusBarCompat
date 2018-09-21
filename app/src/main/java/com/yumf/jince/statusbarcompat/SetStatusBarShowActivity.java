package com.yumf.jince.statusbarcompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yumf.jince.statusbar.StatusBarCompat;

public class SetStatusBarShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set_status_bar_show);
        setContentView(R.layout.activity_set_status_bar_transparent);

//        StatusBarCompat compat = new StatusBarCompat(this);
//        compat.setFullScreen(false);

        StatusBarCompat.newBuilder()
                .fullScreen(false)
                .build(this)
                .apply();
    }
}
