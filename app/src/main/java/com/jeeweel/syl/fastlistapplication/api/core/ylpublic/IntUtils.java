package com.jeeweel.syl.fastlistapplication.api.core.ylpublic;

/**
 * 作者：苏逸龙 on 2015-10-27 14:49
 * QQ：317616660
 */
public class IntUtils {
    public static String toStr(int value, String sDefValue) {
        try {
            return Integer.valueOf(value).toString();
        } catch (Exception e) {
        }
        return sDefValue;
    }

    public static String toStr(int value) {
        return IntUtils.toStr(value,"");
    }
}
