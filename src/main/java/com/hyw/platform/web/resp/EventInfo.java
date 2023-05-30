package com.hyw.platform.web.resp;

import com.hyw.platform.dbservice.dto.FieldAttr;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 事件信息
 *
 * 该数据会在触发事件后的请求中带回
 */
@Data
@Accessors( chain = true )
public class EventInfo {
    // {"eventList":[{"event":"change","type":"webDataReq","id":"getLibFromDb","relEleId":"libName"}]}
    private String event; // click

    private String reqType; // menuReq/buttonReq
    private String reqMapping; //
    private String reqMethod; //post/get...

    private String menu;
    private String page;
    private String element;

    private String RelEleId;
    private String RelEleType;
    private String RelEleChgType;

    private String triggerType;
    private String triggerElement;
    private String triggerElementType; //改变的类型：value-改变值

    private String nextPage;

    private String selectedValue;
    private boolean withPage; //是否为分页按钮的请求事件
    private int reqPage; //请求页码
    private Map<String, FieldAttr> recordMap;
    private Map<String,Object> paramMap; //放置参数
    private Map<String,Object> triggerParamMap; //放置参数
}
