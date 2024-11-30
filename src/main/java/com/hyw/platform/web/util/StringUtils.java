package com.hyw.platform.web.util;

public class StringUtils {

    /***
     * 下划线命名转为驼峰命名
     * @param para 下划线命名的字符串
     */
    public static String underlineToCamelCase(String para) {
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 功能：驼峰命名转下划线命名
     * 小写和大写紧挨一起的地方,加上分隔符,然后全部转小写
     */
    public static String camelCaseToUnderline(String c) {
        String separator = "_";
        c = c.replaceAll("([a-z])([A-Z])", "$1" + separator + "$2").toLowerCase();
        return c;
    }
    /**
     * 功能：数据脱敏.默认方式
     * 描述：对str的前4和后4位保留，其余用*代替
     */
    public static String desensitizedStringKeep4(String str){
        return desensitizedString(str,4,4) ;
    }

    /**
     * 功能：数据脱敏
     * 描述：对str的欠pre位和后end位保留，其余用*代替
     * 要求：pre，end为 >=0 的整数，且不为空
     */
    public static String desensitizedString(String str,int pre, int end){
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(str)){
            int desensitiLength = str.length()-pre-end;
            if (desensitiLength<=0) return str;
            StringBuffer cha = new StringBuffer();
            for(int i=0;i<desensitiLength;i++) cha.append("*");
            str = str.replaceAll("(\\w{"+pre+"})\\w*(\\w{"+end+"})", "$1"+cha.toString()+"$2");
        }
        return str;
    }
}
