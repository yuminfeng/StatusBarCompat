package com.yumf.jince.statusbarcompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yumf.jince.statusbar.StatusBarCompat;

public class SetStatusBarColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_status_bar_color);

        StatusBarCompat compat = new StatusBarCompat();
        compat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }
}
