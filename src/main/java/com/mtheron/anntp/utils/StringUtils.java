package com.mtheron.anntp.utils;

import org.apache.commons.lang3.ClassUtils;

public class StringUtils {

    public static String convertToString(Object obj) {
        if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
            return String.valueOf(obj);
        }
        return obj.toString();
    }
}
