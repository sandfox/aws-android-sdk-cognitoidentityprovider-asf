// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.datacollection;

import android.telephony.TelephonyManager;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

public class TelephonyDataCollector extends DataCollector
{
    @Override
    public Map<String, String> collect(final Context context) {
        final Map<String, String> contextData = new HashMap<String, String>();
        final TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        if (telephonyManager != null) {
            contextData.put("HasSimCard", String.valueOf(telephonyManager.hasIccCard()));
            contextData.put("IsNetworkRoaming", String.valueOf(telephonyManager.isNetworkRoaming()));
            contextData.put("Carrier", telephonyManager.getNetworkOperatorName());
            contextData.put("NetworkType", String.valueOf(telephonyManager.getNetworkType()));
            contextData.put("PhoneType", String.valueOf(telephonyManager.getPhoneType()));
            if (telephonyManager.getSimState() == 5) {
                contextData.put("SimCountry", String.valueOf(telephonyManager.getSimCountryIso()));
                contextData.put("SimOperator", String.valueOf(telephonyManager.getSimOperatorName()));
            }
        }
        return contextData;
    }
}
