// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.datacollection;

import android.content.pm.PackageInfo;
import android.util.Log;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

public class ApplicationDataCollector extends DataCollector
{
    private static final String TAG;
    private static final int ALL_FLAGS_OFF = 0;
    
    @Override
    public Map<String, String> collect(final Context context) {
        final Map<String, String> contextData = new HashMap<String, String>();
        contextData.put("ApplicationName", this.getAppName(context));
        contextData.put("ApplicationTargetSdk", this.getAppTargetSdk(context));
        contextData.put("ApplicationVersion", this.getAppVersion(context));
        return contextData;
    }
    
    private String getAppName(final Context context) {
        final ApplicationInfo applicationInfo = context.getApplicationInfo();
        final PackageManager packageManager = context.getPackageManager();
        final String appName = (String)packageManager.getApplicationLabel(applicationInfo);
        return appName;
    }
    
    private String getAppVersion(final Context context) {
        final PackageManager packageManager = context.getPackageManager();
        String appVersion = "";
        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            appVersion = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.i(ApplicationDataCollector.TAG, "Unable to get app version. Provided package name could not be found.");
        }
        return appVersion;
    }
    
    private String getAppTargetSdk(final Context context) {
        final ApplicationInfo applicationInfo = context.getApplicationInfo();
        return String.valueOf(applicationInfo.targetSdkVersion);
    }
    
    static {
        TAG = ApplicationDataCollector.class.getSimpleName();
    }
}
