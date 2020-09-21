// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.datacollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import java.util.List;

public class ContextDataAggregator
{
    private final List<DataCollector> dataCollectors;
    
    private ContextDataAggregator() {
        this.dataCollectors = this.getDataCollectors();
    }
    
    protected ContextDataAggregator(final List<DataCollector> dataCollectors) {
        this.dataCollectors = dataCollectors;
    }
    
    public static ContextDataAggregator getInstance() {
        return InstanceHolder.INSTANCE;
    }
    
    public Map<String, String> getAggregatedData(final Context context) {
        final Map<String, String> userContextData = new HashMap<String, String>();
        for (final DataCollector collector : this.dataCollectors) {
            final Map<String, String> data = collector.collect(context);
            userContextData.putAll(data);
        }
        this.removerNullEntries(userContextData);
        return userContextData;
    }
    
    private void removerNullEntries(final Map<String, String> data) {
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            if (entry.getValue() == null) {
                data.remove(entry.getKey());
            }
        }
    }
    
    private List<DataCollector> getDataCollectors() {
        final List<DataCollector> availableDataCollectors = new ArrayList<DataCollector>();
        availableDataCollectors.add(new ApplicationDataCollector());
        availableDataCollectors.add(new BuildDataCollector());
        availableDataCollectors.add(new DeviceDataCollector());
        availableDataCollectors.add(new TelephonyDataCollector());
        return availableDataCollectors;
    }
    
    private static class InstanceHolder
    {
        private static final ContextDataAggregator INSTANCE;
        
        static {
            INSTANCE = new ContextDataAggregator((ContextDataAggregator$1)null);
        }
    }
}
