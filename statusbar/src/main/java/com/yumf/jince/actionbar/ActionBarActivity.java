package com.yumf.jince.actionbar;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yumf.jince.statusbar.R;
import com.yumf.jince.statusbar.StatusBarCompat;

public abstract class ActionBarActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mActionRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        mToolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.title_center);
        mActionRight = findViewById(R.id.action_right);
        FrameLayout mContentLayout = findViewById(R.id.content_layout);

        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        LayoutInflater.from(this).inflate(getContentView(), mContentLayout);

        initToolBar();
    }

    private void initToolBar() {
//        setSupportActionBar(mToolbar); //这里用不到ActionBar的一些特性，所以无需此操作
        mToolbar.setNavigationIcon(R.drawable.ic_return_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract int getContentView();

    protected void setStatusBarColor(@ColorInt int color) {
        StatusBarCompat.newBuilder()
                .statusColor(color)
                .build(this)
                .apply();

        mToolbar.setBackgroundColor(color);
    }

    protected void setTitleName(String title) {
        mTitle.setText(title);
    }

    protected void setRightMenu(String rightStr, final View.OnClickListener listener) {
        if (!mActionRight.isShown())
            mActionRight.setVisibility(View.VISIBLE);
        mActionRight.setText(rightStr);
        mActionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }
}
