package com.code.multidexinstall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.os.Bundle;

public class LoadMultidexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_multidex);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MultiDex.install(LoadMultidexActivity.this);
                MineApplication.muldexInstallFinish = true;
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
    }
}
