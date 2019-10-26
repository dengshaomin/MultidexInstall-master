package com.code.multidexinstall;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

public class ProcessUtil {

    /**
     * 在6.0以上的手机将getRunningAppProcesses只能获取当前进程的信息
     */
    public static String getProcessName(Context context) {
        ActivityManager am = null;
        try {
            am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        } catch (Exception e) {

        }
        if (am == null) {
            return null;
        }
        List<RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        if (processes != null) {
            int myPid = Process.myPid();
            for (RunningAppProcessInfo info : processes) {
                if (info.pid == myPid) {
                    return info.processName;
                }
            }
        }
        return null;
    }

    public static boolean isMainAppProcess(Context context) {
        String currentProcessName = getProcessName(context);
        return !TextUtils.isEmpty(currentProcessName)
                && TextUtils.equals(currentProcessName, BuildConfig.APPLICATION_ID)
                ;
    }


}
