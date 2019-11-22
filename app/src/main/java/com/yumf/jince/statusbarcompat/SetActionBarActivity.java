package com.yumf.jince.statusbarcompat;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yumf.jince.actionbar.ActionBarActivity;

public class SetActionBarActivity extends ActionBarActivity {

    private SeekBar seekBar;
    private TextView seek_progress;
    private ArgbEvaluator evaluator;

    @Override
    protected int getContentView() {
        return R.layout.activity_set_action_bar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("yumf", "onCreate");

        setStatusBarColor(ContextCompat.getColor(SetActionBarActivity.this, R.color.colorPrimaryDark));
        setTitleName("BaseActivity");
        setRightMenu("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetActionBarActivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });

        evaluator = new ArgbEvaluator(); //颜色渐变计算器
        seek_progress = findViewById(R.id.seek_progress);
        seekBar = findViewById(R.id.seekBar_color);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float value = progress / 100.0f;
                seek_progress.setText(String.valueOf(value));
                int color = (int) evaluator.evaluate(value, ContextCompat.getColor(SetActionBarActivity.this, R.color.colorPrimaryDark), ContextCompat.getColor(SetActionBarActivity.this, R.color.colorWhite));
                setStatusBarColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e("yumf", "onPostCreate");
    }
}
