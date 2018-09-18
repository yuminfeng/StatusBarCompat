package com.yumf.jince.statusbarcompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yumf.jince.statusbar.StatusBarCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarCompat compat = new StatusBarCompat();
                Toast.makeText(MainActivity.this,compat.printInfo(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
