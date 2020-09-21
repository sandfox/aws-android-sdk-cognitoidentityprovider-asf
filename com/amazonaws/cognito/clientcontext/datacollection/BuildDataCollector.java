// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.datacollection;

import android.os.Build;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

public class BuildDataCollector extends DataCollector
{
    @Override
    public Map<String, String> collect(final Context context) {
        final Map<String, String> contextData = new HashMap<String, String>();
        contextData.put("DeviceBrand", Build.BRAND);
        contextData.put("DeviceFingerprint", Build.FINGERPRINT);
        contextData.put("DeviceHardware", Build.HARDWARE);
        contextData.put("DeviceName", Build.MODEL);
        contextData.put("Product", Build.PRODUCT);
        contextData.put("BuildType", Build.TYPE);
        contextData.put("DeviceOsReleaseVersion", Build.VERSION.RELEASE);
        contextData.put("DeviceSdkVersion", Build.VERSION.SDK);
        return contextData;
    }
}
