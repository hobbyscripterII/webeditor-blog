package com.jy.myblog.common;

import static com.jy.myblog.common.Const.FAIL;

public class Util {
    public static boolean isNotNull(int val) {
        return val != FAIL;
    }

    public static boolean isNotNull(String str) {
        return str != null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }
}
