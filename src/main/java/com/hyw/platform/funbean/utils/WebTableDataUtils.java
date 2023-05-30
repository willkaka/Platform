package com.hyw.platform.funbean.utils;

import com.hyw.platform.dbservice.dto.FieldAttr;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WebTableDataUtils {

    public static List<Map<String,Object>> convertToMapList(List<Map<String,FieldAttr>> recordList){
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        for(Map<String,FieldAttr> map:recordList) {
            Map<String, Object> fieldInfoMap = new LinkedHashMap<>();
            for (String fieldName : map.keySet()) {
                FieldAttr fieldAttr = map.get(fieldName);
                fieldInfoMap.put(fieldName,fieldAttr.getValue());
            }
            dataMaps.add(fieldInfoMap);
        }
        return dataMaps;
    }

    public static Map<String,String> getHeadMap(List<Map<String,FieldAttr>> recordList){
        Map<String,String> headMap = new LinkedHashMap<>();
        if(CollectionUtils.isEmpty(recordList)) return headMap;

        for (String fieldName : recordList.get(0).keySet()) {
            FieldAttr fieldAttr = recordList.get(0).get(fieldName);
            headMap.put(fieldName,fieldAttr.getRemarks());
        }
        return headMap;
    }
}
