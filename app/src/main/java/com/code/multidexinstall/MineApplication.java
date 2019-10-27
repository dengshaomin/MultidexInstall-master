package com.code.multidexinstall;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.multidex.MultiDex;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MineApplication extends Application {
    private final String TAG = getClass().getSimpleName();
    private File multidexFlagFile = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //主进程并且vm不支持多dex的情况下才使用 Multidex
        if (ProcessUtil.isMainAppProcess(base) && MultidexUtils.supportMultidex()) {
            loadMultiDex(base);
        }
    }

    private void loadMultiDex(final Context context) {
        createMultidexFlagFile(context);
        //启动多进程去加载MultiDex
        Intent intent = new Intent(context, LoadMultidexActivity.class);
        intent.putExtra(LoadMultidexActivity.MULTIDEX_FLAG_FILE, multidexFlagFile.getPath());
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //阻塞当前线程，在Application中是不会出现anr的
        while (multidexFlagFile.exists()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long startTime = System.currentTimeMillis();
        MultiDex.install(context);
        Log.e(TAG, TAG + " const:" + (System.currentTimeMillis() - startTime));
    }

    private void createMultidexFlagFile(Context context) {
        try {
            multidexFlagFile = new File(context.getCacheDir().getAbsolutePath(), "multidex_flag.tmp");
            if (!multidexFlagFile.exists()) {
                Log.d(TAG, "crate multidex flag file success");
                multidexFlagFile.createNewFile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
