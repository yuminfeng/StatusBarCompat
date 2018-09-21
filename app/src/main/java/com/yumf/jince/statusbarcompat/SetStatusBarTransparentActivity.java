package com.yumf.jince.statusbarcompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yumf.jince.statusbar.StatusBarCompat;

public class SetStatusBarTransparentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_status_bar_transparent);

//        StatusBarCompat compat = new StatusBarCompat(this);
//        compat.setStatusBarTransparent(true);

        StatusBarCompat.newBuilder()
                .statusBarTransparent(true)
                .build(this)
                .apply();
    }
}
