// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.datacollection;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.Locale;
import android.content.SharedPreferences;
import java.util.Date;
import java.util.UUID;
import android.view.WindowManager;
import android.view.Display;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

public class DeviceDataCollector extends DataCollector
{
    private static final String PLATFORM = "ANDROID";
    protected static final String LOCAL_STORAGE_PATH = "AWS.Cognito.ContextData";
    protected static final String LOCAL_STORAGE_DEVICE_ID_KEY = "CognitoDeviceId";
    
    @Override
    public Map<String, String> collect(final Context context) {
        final Map<String, String> contextdata = new HashMap<String, String>();
        contextdata.put("ClientTimezone", this.getTimezoneOffset());
        contextdata.put("Platform", "ANDROID");
        contextdata.put("ThirdPartyDeviceId", this.getThirdPartyDeviceAgent());
        contextdata.put("DeviceId", this.getCognitoDeviceAgent(context));
        contextdata.put("DeviceLanguage", this.getLanguage());
        final Display display = this.getDisplay(context);
        contextdata.put("ScreenHeightPixels", String.valueOf(display.getHeight()));
        contextdata.put("ScreenWidthPixels", String.valueOf(display.getWidth()));
        return contextdata;
    }
    
    private Display getDisplay(final Context context) {
        final WindowManager windowManager = (WindowManager)context.getSystemService("window");
        return windowManager.getDefaultDisplay();
    }
    
    protected String getCognitoDeviceAgent(final Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("AWS.Cognito.ContextData", 0);
        if (sharedPreferences == null) {
            return null;
        }
        final String storedId = sharedPreferences.getString("CognitoDeviceId", (String)null);
        if (storedId != null) {
            return storedId;
        }
        final String randomId = UUID.randomUUID().toString();
        final String deviceId = randomId + ":" + new Date().getTime();
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CognitoDeviceId", deviceId);
        editor.apply();
        return deviceId;
    }
    
    protected String getThirdPartyDeviceAgent() {
        return "android_id";
    }
    
    protected String getLanguage() {
        return Locale.getDefault().toString();
    }
    
    private String getTimezoneOffset() {
        final TimeZone timeZone = this.getTimezone();
        final int rawTimezoneOffset = timeZone.getRawOffset();
        final long hours = TimeUnit.MILLISECONDS.toHours(rawTimezoneOffset);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(rawTimezoneOffset) - TimeUnit.HOURS.toMinutes(hours);
        final String offset = ((hours < 0L) ? "-" : "") + String.format(Locale.US, "%02d:%02d", Math.abs(hours), minutes);
        return offset;
    }
    
    protected TimeZone getTimezone() {
        return TimeZone.getDefault();
    }
}
