package com.code.multidexinstall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;

import java.io.File;

public class LoadMultidexActivity extends Activity {
    public static final String MULTIDEX_FLAG_FILE = "MULTIDEX_FLAG_FILE";
    String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_multidex);
        path = getIntent().getStringExtra(MULTIDEX_FLAG_FILE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                MultiDex.install(LoadMultidexActivity.this);
                try {
                    //模拟加载耗时
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("MineApplication", "LoadMultidexActivity const:" + (System.currentTimeMillis() - time));
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    Log.e("MineApplication", "delete flag file");
                }
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        //不响应返回键
    }
}
