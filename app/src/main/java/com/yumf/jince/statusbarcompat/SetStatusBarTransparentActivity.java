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

        /**
         * android:fitsSystemWindows=”true” （触发View的padding属性来给系统窗口留出空间）
         * 这个属性可以给任何view设置,只要设置了这个属性此view的其他所有padding属性失效。
         * 该属性的生效条件是只有在设置了透明状态栏(StatusBar)或者导航栏(NavigationBar)此属性才会生效。
         */
        StatusBarCompat.newBuilder()
                .statusBarTransparent()
                .build(this)
                .apply();
    }
}
