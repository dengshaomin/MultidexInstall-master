package com.code.multidexinstall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Build.VERSION;
import android.util.Log;

public class MultidexUtils {

    private static boolean isVMMultidexCapable() {
        String versionString = System.getProperty("java.vm.version");
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    int minor = Integer.parseInt(matcher.group(2));
                    isMultidexCapable = major > 2 || major == 2 && minor >= 1;
                } catch (NumberFormatException var5) {
                }
            }
        }

        Log.i("MultiDex", "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    public static boolean supportMultidex(){
        return !isVMMultidexCapable() && !(VERSION.SDK_INT < 4);
    }
}
