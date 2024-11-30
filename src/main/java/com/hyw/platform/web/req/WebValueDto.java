package com.hyw.platform.web.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@Accessors( chain = true )
public class WebValueDto {

    private String curMenu;//当前的菜单

    private Map<String,ValueObject> webInputValueMap;//页面输入值

    /**
     * 取当前值
     * @return
     */
    public Map<String,String> getValue(){
        Map<String,String> valueMap = new HashMap<>();
        if(webInputValueMap == null) return valueMap;
        webInputValueMap.forEach((k,v)->{
            valueMap.put(k, Objects.isNull(v)||Objects.isNull(v.getValue())?null:v.getValue().toString());
        });
        return valueMap;
    }
}




