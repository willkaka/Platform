package com.hyw.platform.web.resp.util;

import com.hyw.platform.web.util.WebUtil;

import java.util.HashMap;
import java.util.Map;

public class ConvertEleData {

    /**
     * 解析分隔符和连接符组成的字符串
     *
     * @param s         字符串
     * @param separator 分隔符
     * @param connector 连接符
     * @return Map<String, String>
     */
    public static Map<String, String> getAttrMap(String s, String separator, String connector) {
        Map<String, String> attrMap = new HashMap<>();
        if (WebUtil.isBlank(s)) return attrMap;
        String[] attrs = s.split(separator);
        for (String attrExpress : attrs) {
            String[] express = attrExpress.split(connector);
            attrMap.put(express[0], express[1].replace("\"", ""));
        }
        return attrMap;
    }
}
