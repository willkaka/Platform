package com.hyw.platform.web.req;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.web.resp.EventInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

@Data
@Accessors( chain = true )
public class PublicReq {
    //报文头部
    private String reqId;//区分请求，避免重复处理
    private String reqMapping;//对应Controller的Mapping
    private String reqName;//请求名称
    private String reqType;//请求类型
    private String curMenu;//当前的菜单

    //报文内容
    private EventInfo eventInfo; //事件信息
    private JSONObject webValueDto; //页面当前输入的值

    public Map<String, ValueObject> getValueMap(){
        Map<String, ValueObject> valueMap = new HashMap<>();
        this.webValueDto.getJSONObject("webInputValueMap").forEach((k,v)->{
            if(v instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) v;
                if(jsonObject.containsKey("value")){
                    ValueObject valueObject = jsonObject.toJavaObject(ValueObject.class);
                    valueMap.put(k,valueObject);
                    return;
                }
            } else if (v instanceof LinkedHashMap){
                Map<String, Object> vMap = (Map<String, Object>) v;
                if(vMap.containsKey("value")) {
                    ValueObject valueObject = new ValueObject().setValue(vMap.get("value")).setDefValue(vMap.get("defValue"));
                    valueMap.put(k, valueObject);
                    return;
                }
            }
        });
        return valueMap;
    }

    public Map<String, Object> getCurValueMap(){
        Map<String, Object> valueMap = new HashMap<>();
        this.webValueDto.getJSONObject("webInputValueMap").forEach((k,v)->{
            if(v instanceof JSONObject){
                valueMap.put(k,((JSONObject) v).get("value"));
            }
            if(v instanceof LinkedHashMap){
                valueMap.put(k,((Map<String,Object>) v).get("value"));
            }
        });
        return valueMap;
    }

    public Map<String, String> getCurValueStrMap(){
        Map<String, String> valueMap = new HashMap<>();
        this.webValueDto.getJSONObject("webInputValueMap").forEach((k,v)->{
            if(v instanceof JSONObject){
                valueMap.put(k,((JSONObject) v).getString("value"));
            }
            if(v instanceof LinkedHashMap){
                valueMap.put(k,((Map<String,Object>) v).getOrDefault("value","").toString());
            }
        });
        return valueMap;
    }

    /**
     * 获取当前值的json
     * @return json
     */
    public JSONObject getCurValueJson(){
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : this.webValueDto.getJSONObject("webInputValueMap").entrySet()) {
//        for (Map.Entry<String, Object> entry : this.webValueDto.getJSONObject("inputValueObjMap").entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value instanceof JSONObject){
                jsonObject.put(key,((JSONObject) value).get("value"));
            }
            if(value instanceof LinkedHashMap){
                jsonObject.put(key,((Map<String,Object>) value).get("value"));
            }
            if(value instanceof JSONArray){
                List<JSONObject> jsonList = new ArrayList<>();
                JSONArray list = (JSONArray) value;
                for(Object subObject:list){
                    JSONObject subJsonObject = new JSONObject();
                    JSONObject subJsonObj = (JSONObject) subObject;
                    for (Map.Entry<String, Object> subEntry : subJsonObj.entrySet()) {
                        String subKey = subEntry.getKey();
                        Object subValue = subEntry.getValue();
                        if (subValue instanceof JSONObject) {
                            subJsonObject.put(subKey, ((JSONObject) subValue).get("value"));
                        }
                    }
                    jsonList.add(subJsonObject);
                }
                jsonObject.put(key,jsonList);
            }
        }
        return jsonObject;
    }

    /**
     * 获取默认值的json
     * @return json
     */
    public JSONObject getDefValueJson(){
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : this.webValueDto.getJSONObject("webInputValueMap").entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value instanceof JSONObject){
                jsonObject.put(key,((JSONObject) value).get("defValue"));
            }
            if(value instanceof LinkedHashMap){
                jsonObject.put(key,((Map<String,Object>) value).get("defValue"));
            }
            if(value instanceof JSONArray){
                List<JSONObject> jsonList = new ArrayList<>();
                JSONArray list = (JSONArray) value;
                for(Object subObject:list){
                    JSONObject subJsonObject = new JSONObject();
                    JSONObject subJsonObj = (JSONObject) subObject;
                    for (Map.Entry<String, Object> subEntry : subJsonObj.entrySet()) {
                        String subKey = subEntry.getKey();
                        Object subValue = subEntry.getValue();
                        if (subValue instanceof JSONObject) {
                            subJsonObject.put(subKey, ((JSONObject) subValue).get("defValue"));
                        }
                    }
                    jsonList.add(subJsonObject);
                }
                jsonObject.put(key,jsonList);
            }
        }
        return jsonObject;
    }

    public Map<String,Object> getInputValueMap(){
        Map<String, Object> valueMap = new HashMap<>();
        getInputValueObjMap().forEach((k,valueObj)->valueMap.put(k,valueObj.getValue()));
        return valueMap;
    }

    public Map<String, ValueObject> getInputValueObjMap(){
        //处理参数
        Map<String, ValueObject> webInputValueMap = this.getValueMap();
        Map<String, ValueObject> camelFieldMap = new HashMap<>();
        for (Map.Entry<String, ValueObject> entry : webInputValueMap.entrySet()) {
            String key = entry.getKey();
            ValueObject value = entry.getValue();
            camelFieldMap.put(key, value);
        }
        if(this.getEventInfo() != null && MapUtils.isNotEmpty(this.getEventInfo().getParamMap())){
            for (Map.Entry<String, Object> entry : this.getEventInfo().getParamMap().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof Map){
                    Map<String, Object> valueMap = (Map<String, Object>) value;
                    for (Map.Entry<String, Object> valueEntry : valueMap.entrySet()) {
                        String valueKey = valueEntry.getKey();
                        Object valueValue = valueEntry.getValue();
                        String modifiedValueKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( valueKey );
                        camelFieldMap.put(modifiedValueKey, new ValueObject().setValue(valueValue));
                    }
                    continue;
                }
                String modifiedKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( key );
                camelFieldMap.put(modifiedKey, new ValueObject().setValue(value));
            }
        }
        return camelFieldMap;
    }
}


