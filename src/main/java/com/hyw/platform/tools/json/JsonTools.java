package com.hyw.platform.tools.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonTools {

    /**
     * 美化JSON字符串
     * @param jsonStr 输入未格式化的JSON字符串
     * @return 输出美化后的JSON字符串
     */
    public static String convertToPrettyFormat(String jsonStr){
        return JSON.toJSONString(JSONObject.parseObject(jsonStr),
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }
}
