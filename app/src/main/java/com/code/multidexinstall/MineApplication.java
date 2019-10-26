package com.code.multidexinstall;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.multidex.MultiDex;

public class MineApplication extends Application {

    private final String TAG = getClass().getSimpleName();
    public static boolean muldexInstallFinish;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //主进程并且vm不支持多dex的情况下才使用 Multidex
        if (ProcessUtil.isMainAppProcess(base) && MultidexUtils.supportMultidex()) {
            loadMultiDex(base);
        }
    }

    private void loadMultiDex(final Context context) {
        //启动多进程去加载MultiDex,此处一定要通过线程去启动
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, LoadMultidexActivity.class);
                context.startActivity(intent);

            }
        }).start();
        while (!muldexInstallFinish){
            //阻塞当前线程，在Application中是不会出现anr的
        }
        long startTime = System.currentTimeMillis();
        MultiDex.install(context);
        Log.e(TAG, "second time const:" + (System.currentTimeMillis() - startTime));
    }

}
