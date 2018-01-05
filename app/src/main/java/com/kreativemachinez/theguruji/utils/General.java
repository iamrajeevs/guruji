package com.kreativemachinez.theguruji.utils;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kreativemachinez.theguruji.TrackerService;

import java.util.List;

/**
 * Created by regitroy on 8/27/17.
 */

public class General {

    public static boolean isMyServiceRunning(Context context) {
        Class<?> serviceClass = TrackerService.class;
        android.app.ActivityManager manager = (android.app.ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("Service already","running");
                return true;
            }
        }
        context.startService(new Intent(context, TrackerService.class));
        return false;
    }

    public static boolean ServiceRunning(Context context, Class<?> serviceClass) {
        android.app.ActivityManager manager = (android.app.ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("Service already","running");
                return true;
            }
        }
        context.startService(new Intent(context, TrackerService.class));
        return false;
    }

    public static boolean isAppOnForeground(Context context) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}
