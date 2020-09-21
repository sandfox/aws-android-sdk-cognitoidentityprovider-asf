// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.data;

import android.util.Base64;
import org.json.JSONException;
import java.util.Map;
import android.util.Log;
import org.json.JSONObject;
import android.content.Context;
import com.amazonaws.cognito.clientcontext.util.SignatureGenerator;
import com.amazonaws.cognito.clientcontext.datacollection.ContextDataAggregator;

public class UserContextDataProvider
{
    private static final String TAG;
    public static final String VERSION = "ANDROID20171114";
    private ContextDataAggregator aggregator;
    private SignatureGenerator signatureGenerator;
    
    private UserContextDataProvider() {
        this(ContextDataAggregator.getInstance(), new SignatureGenerator());
    }
    
    protected UserContextDataProvider(final ContextDataAggregator aggregator, final SignatureGenerator signatureGenerator) {
        this.aggregator = aggregator;
        this.signatureGenerator = signatureGenerator;
    }
    
    public static UserContextDataProvider getInstance() {
        return InstanceHolder.INSTANCE;
    }
    
    public String getEncodedContextData(final Context context, final String username, final String userPoolId, final String signatureSecret) {
        JSONObject jsonResponse = new JSONObject();
        try {
            final Map<String, String> contextData = this.aggregator.getAggregatedData(context);
            final JSONObject payload = this.getJsonPayload(contextData, username, userPoolId);
            final String payloadString = payload.toString();
            final String signature = this.signatureGenerator.getSignature(payloadString, signatureSecret, "ANDROID20171114");
            jsonResponse = this.getJsonResponse(payloadString, signature);
            return this.getEncodedResponse(jsonResponse);
        }
        catch (Exception e) {
            Log.e(UserContextDataProvider.TAG, "Exception in creating JSON from context data");
            return null;
        }
    }
    
    private JSONObject getJsonPayload(final Map<String, String> contextData, final String username, final String userPoolId) throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("contextData", (Object)new JSONObject((Map)contextData));
        payload.put("username", (Object)username);
        payload.put("userPoolId", (Object)userPoolId);
        payload.put("timestamp", (Object)this.getTimestamp());
        return payload;
    }
    
    protected String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    private JSONObject getJsonResponse(final String payload, final String signature) throws JSONException {
        final JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("payload", (Object)payload);
        jsonResponse.put("signature", (Object)signature);
        jsonResponse.put("version", (Object)"ANDROID20171114");
        return jsonResponse;
    }
    
    protected String getEncodedResponse(final JSONObject jsonResponse) {
        final byte[] responseBytes = jsonResponse.toString().getBytes(ConfigurationConstant.DEFAULT_CHARSET);
        return Base64.encodeToString(responseBytes, 0);
    }
    
    static {
        TAG = UserContextDataProvider.class.getSimpleName();
    }
    
    private static class InstanceHolder
    {
        private static final UserContextDataProvider INSTANCE;
        
        static {
            INSTANCE = new UserContextDataProvider(null);
        }
    }
    
    private class ContextDataJsonKeys
    {
        private static final String CONTEXT_DATA = "contextData";
        private static final String USERNAME = "username";
        private static final String USER_POOL_ID = "userPoolId";
        private static final String TIMESTAMP_MILLI_SEC = "timestamp";
        private static final String DATA_PAYLOAD = "payload";
        private static final String VERSION = "version";
        private static final String SIGNATURE = "signature";
    }
}
