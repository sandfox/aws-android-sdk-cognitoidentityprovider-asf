// 
// Decompiled by Procyon v0.5.36
// 

package com.amazonaws.cognito.clientcontext.data;

import java.nio.charset.Charset;

public class ConfigurationConstant
{
    public static final Charset DEFAULT_CHARSET;
    
    static {
        DEFAULT_CHARSET = Charset.forName("UTF-8");
    }
}
