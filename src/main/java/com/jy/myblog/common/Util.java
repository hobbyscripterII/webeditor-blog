package com.jy.myblog.common;

import static com.jy.myblog.common.Const.SUCCESS;

public class Util {
    public static boolean isNotNull(int val) {
        return val == SUCCESS;
    }

    public static boolean isNotNull(String str) {
        return str != null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }
}
