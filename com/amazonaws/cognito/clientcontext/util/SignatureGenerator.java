// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.util;

import android.util.Log;
import android.util.Base64;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import com.amazonaws.cognito.clientcontext.data.ConfigurationConstant;
import javax.crypto.Mac;

public class SignatureGenerator
{
    private static final String TAG = "HMAC_SHA256_Signature";
    private static final String ALGORITHM = "HmacSHA256";
    
    public String getSignature(final String data, final String secret, final String version) {
        String signature = "";
        try {
            final Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(ConfigurationConstant.DEFAULT_CHARSET), "HmacSHA256");
            sha256Hmac.init(secretKey);
            final byte[] versionBytes = version.getBytes(ConfigurationConstant.DEFAULT_CHARSET);
            sha256Hmac.update(versionBytes);
            final byte[] dataBytes = data.getBytes(ConfigurationConstant.DEFAULT_CHARSET);
            signature = Base64.encodeToString(sha256Hmac.doFinal(dataBytes), 0);
        }
        catch (Exception e) {
            this.logWarning(e);
        }
        return signature;
    }
    
    private void logWarning(final Exception e) {
        Log.w("HMAC_SHA256_Signature", "Exception while completing context data signature", (Throwable)e);
    }
}
