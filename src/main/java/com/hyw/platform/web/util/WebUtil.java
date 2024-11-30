package com.hyw.platform.web.util;

import com.hyw.platform.exception.BizException;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUtil {

    public static boolean isBlank(String s) {
        return null == s || "".equals(s);
    }

    public static boolean isNotBlank(String s) {
        return !(null == s || "".equals(s));
    }

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !(collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !(map == null || map.isEmpty());
    }


    /**
     * 将List<Map<String,Object>>转为按值保存的Map
     * @param mapList
     * @return
     */
    public static Map<String,String> getValueMap(List<Map<String,Object>> mapList){
        Map<String,String> rtnMap = new HashMap<>();
        for(Map<String,Object> map:mapList){
            BizException.trueThrow(map.size()>2,"DataUtil.getValueMap要求List中的Map只能包含两个元素！");

            int index=0;
            String rtnKey="",rtnValue="";
            for (String key: map.keySet()) {
                if(index==0) rtnKey = (String) map.get(key);
                if(index==1) rtnValue = (String) map.get(key);
                index++;
            }
            rtnMap.put(rtnKey,rtnValue);
        }
        return rtnMap;
    }
}
