package com.hyw.platform.dbservice.exception;

import org.apache.commons.lang3.StringUtils;

public class DbThrow {

    public static void isTrue(boolean expression, Object... arguments) {
        if (expression) {
            throw new DbException(getMessageText(arguments));
        }
    }

    public static void isNull(Object object, Object... arguments) {
        if (object == null) {
            throw new DbException(getMessageText(arguments));
        }
    }

    public static void isBlank(String s, Object... arguments) {
        if (StringUtils.isBlank(s)) {
            throw new DbException(getMessageText(arguments));
        }
    }

    public static String getMessageText(Object[] arguments){
        if(arguments==null || arguments.length<=0) return null;
        String message = arguments[0].toString();
        for(int i=1;i<arguments.length;i++){
            message = message.replaceFirst("\\{}",arguments[i].toString());
        }
        return message;
    }
}
