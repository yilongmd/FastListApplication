package com.jeeweel.syl.fastlistapplication.api.core.ylpublic;

/**
 * Created by Administrator on 2015-08-18.
 * Object应用单元
 */
public class OUtils {
    public static boolean IsNotNull(Object o) {
        return o != null;
    }
    public static boolean IsNull(Object o) {
        return !IsNotNull(o);
    }
}
