package com.jy.myblog.common;

public class Util {
    public static boolean isNotNull(int val) {
        return val > 0;
    }

    public static boolean isNotNull(String str) {
        return str != null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }
}
