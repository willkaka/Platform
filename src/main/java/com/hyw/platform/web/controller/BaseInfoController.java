package com.hyw.platform.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.resp.webElement.TableNormal;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.service.CallInterface;
import com.hyw.platform.web.service.WebElementService;
import com.hyw.platform.web.service.WebMenuService;
import com.hyw.platform.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Slf4j
public class BaseInfoController {

    @Autowired
    private WebMenuService webMenuService;
    @Autowired
    private WebElementService webElementService;
    @Autowired
    private CallInterface callInterface;

    @Autowired
    ApplicationContext context;

    /**
     * 初始页面
     *
     * @param model model
     * @return index.html
     */
    @RequestMapping(value = {"/", "", "index"})
    public String startRequest(Model model) {
        model.addAttribute("webSiteName", Constant.WEB_SITE_TITLE);
        return "index_new";
//        return "index";
    }

    /**
     * 页面初始化请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "initPageInfo")
    @ResponseBody
    public PublicResp initPageInfo(@RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp().setRtnCode("0000").setRtnMsg("success");
        //取菜单清单
        Map<String,String> menuIdMap = new HashMap<>();
        List<WebElementDto> menuList = webMenuService.getMenu("root", menuIdMap);
        publicResp.setWebElementDtoList(menuList);
        return publicResp;
    }

    /**
     * 菜单请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/menuReq/{eventId}")
    @ResponseBody
    public PublicResp menuReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), "start_page","contentArea",publicReq);
        publicResp.setWebElementDtoList(inputList);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }

    /**
     * 按钮请求(只改变输出区域数据)
     *
     * @param requestDto 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/buttonReq/{eventId}")
    @ResponseBody
    public PublicResp buttonReq(@PathVariable String eventId, @RequestBody PublicReq requestDto) {
        PublicResp publicResp = new PublicResp();

        if (StringUtils.isBlank(eventId)) {
            publicResp.setRtnCode("9997");
            publicResp.setRtnMsg("未配置该按钮请求(" + eventId + ")的处理方法！");
            return publicResp;
        }
        log.info("开始执行{}", eventId);

        EventInfo eventInfo = requestDto.getEventInfo();
        boolean callInterfaceFlag = false;
        JSONObject jsonObject = new JSONObject();
        if(null != eventInfo) {
            Map<String,Object> paramMap = eventInfo.getParamMap();
            if(paramMap!=null && paramMap.containsKey("host")) {
                String host = paramMap.get("host").toString();
                callInterfaceFlag = true;
                // 取调用服务的接口信息
                jsonObject = callInterface.call(host, eventId, requestDto);
            }
        }

        if(!callInterfaceFlag) {
            publicResp = ((RequestFun) context.getBean(eventId)).execute(requestDto);
        }else {
            String page = eventInfo.getNextPage();
            if (StringUtils.isBlank(page) && MapUtils.isNotEmpty(eventInfo.getParamMap()) && eventInfo.getParamMap().containsKey("nextPage")) {
                page = eventInfo.getParamMap().get("nextPage").toString();
            }
            if (StringUtils.isNotBlank(page)) {
                PublicReq publicReq = new PublicReq();
                List<WebElementDto> inputList = webElementService.getPageElementsById(page, publicReq);
                if (jsonObject != null && !jsonObject.isEmpty()) {
                    setElementValue(inputList, jsonObject, eventInfo);
                }
                publicResp.setWebElementDtoList(inputList);
            }
        }
        if(eventInfo!=null) {
            NextOprDto nextOprDto = webElementService.getCallAfterOpr(eventInfo.getMenu(), eventInfo.getPage(), eventInfo.getElement());
            // 将本次事件的参数传递到后续事件中
            for (EventInfo eventInfo1 : nextOprDto.getEventInfoList()) {
                if(MapUtils.isEmpty(eventInfo1.getParamMap())){
                    eventInfo1.setParamMap(new HashMap<>());
                }
                if(MapUtils.isNotEmpty(eventInfo.getParamMap())) {
                    eventInfo1.getParamMap().putAll(eventInfo.getParamMap());
                }
                if(eventInfo1.getParamMap().containsKey("transferCnt")) {
                    eventInfo1.getParamMap().put("transferCnt", (int)eventInfo1.getParamMap().get("transferCnt")+1);
                }else{
                    eventInfo1.getParamMap().put("transferCnt", 1);
                }
            }
            publicResp.setNextOprDto(nextOprDto);
        }
        return publicResp;
    }

    /**
     * 递归遍历元素，将JSONObject中的对应值填充到元素中
     */
    private void setElementValue(List<WebElementDto> dtoList, JSONObject jsonObject, EventInfo eventInfo) {
        for(WebElementDto dto:dtoList){
            if(CollectionUtils.isNotEmpty(dto.getSubElementList())){
                setElementValue(dto.getSubElementList(),jsonObject, eventInfo);
                if(!"table".equalsIgnoreCase(dto.getType())) {
                    continue;
                }
            }
            if("div".equalsIgnoreCase(dto.getType()) ||
                    "divTitle".equalsIgnoreCase(dto.getType()) ||
                    "table_record_button".equalsIgnoreCase(dto.getType())){
                continue;
            }
            if(eventInfo != null && eventInfo.getParamMap()!=null && "table".equalsIgnoreCase(dto.getType())) {
                // "dataToEle":"loanTable","dataToElePos":"data","dataToEleType":"list"
                String dataToEle = (String) eventInfo.getParamMap().get("dataToEle");
                if(dto.getElementName().equals(dataToEle)) {
                    String dataKey;
                    String dataToElePos = (String) eventInfo.getParamMap().get("dataToElePos");
                    JSONObject subJson = jsonObject;
                    if (StringUtils.isNotBlank(dataToElePos) && dataToElePos.contains(".")) {
                        String[] dataToElePosArr = dataToElePos.split("\\.");
                        for(int i=0;i<dataToElePosArr.length-1;i++){
                            subJson = subJson.getJSONObject(dataToElePosArr[i]);
                        }
                        dataKey = dataToElePosArr[dataToElePosArr.length-1];
                    }else{
                        dataKey = dataToElePos;
                    }
                    String dataToEleType = (String) eventInfo.getParamMap().get("dataToEleType");
                    if ("list".equalsIgnoreCase(dataToEleType) && subJson.containsKey(dataKey)) {
                        TableNormal tableNormal = (TableNormal) dto.getData();
                        //记录
                        List tableDataList = subJson.getJSONArray(dataKey);
                        if(subJson.containsKey("total") && subJson.get("total")!=null){
                            tableNormal.setTotalCount(subJson.getIntValue("total"));
                            tableNormal.setWithPage(true);
                        }
                        if(subJson.containsKey("current") && subJson.get("current")!=null){
                            tableNormal.setPageNow(subJson.getIntValue("current"));
                        }
                        if(subJson.containsKey("size") && subJson.get("size")!=null){
                            tableNormal.setPageSize(subJson.getIntValue("size"));
                        }
                        if(subJson.containsKey("pages") && subJson.get("pages")!=null){
                            tableNormal.setPageTotal(subJson.getIntValue("pages"));
                        }
                        tableNormal.getRecordList().addAll(tableDataList);
                    }
                    continue;
                }
            }
            if(eventInfo != null && eventInfo.getParamMap()!=null && "canvas".equalsIgnoreCase(dto.getType())) {
                dto.setData(jsonObject.get("data"));
                continue;
            }
            if(jsonObject.containsKey(dto.getElementName())){
                Object value = jsonObject.get(dto.getElementName());
                dto.setDefValue(value==null?null:value.toString());
                continue;
            }
            if(jsonObject.containsKey("data")){
                JSONObject dataJson = jsonObject.getJSONObject("data");
                if(MapUtils.isNotEmpty(dto.getParam()) && dto.getParam().containsKey("dataField")){
                    String dataField = (String) dto.getParam().get("dataField");
                    if(dataJson.containsKey(dataField)){
                        Object value = dataJson.get(dataField);
                        dto.setDefValue(value==null?null:value.toString());
                        continue;
                    }
                }
                if(dataJson.containsKey(dto.getElementName())){
                    Object value = dataJson.get(dto.getElementName());
                    dto.setDefValue(value==null?null:value.toString());
                    continue;
                }
            }
        }
    }

    /**
     * 页面显示数据请求（例：点选下拉选择触发请求改变另一个下拉选择内容）
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/webDataReq/{eventId}")
    @ResponseBody
    public PublicResp webDataReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }
        Map<String,Object> param = eventInfo.getParamMap();
        String page = eventInfo.getNextPage();
        String refreshFlag = (String) param.getOrDefault("refreshFlag","N");
        if("Y".equalsIgnoreCase(refreshFlag)){
            String refreshPage = (String) param.get("refreshPage");
            List<WebElementDto> inputList = webElementService.getPageElementsById(refreshPage, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }else if(StringUtils.isNotBlank(page)){
            List<WebElementDto> inputList = webElementService.getPageElementsById(page, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }else {
            String parentEle = (String) param.getOrDefault("parentEle", null);
            //取输入区域元素清单，固定从body开始
            List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), page, parentEle, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }
        NextOprDto nextOprDto = webElementService.getCallAfterOpr(eventInfo.getMenu(), eventInfo.getPage(), eventInfo.getElement());
        for(EventInfo eventInfo1:nextOprDto.getEventInfoList()){
            eventInfo1.getParamMap().putAll(param);
        }
        publicResp.setNextOprDto(nextOprDto);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }


    /**
     * 页面显示数据请求（例：点选下拉选择触发请求改变另一个下拉选择内容）
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/swDataReq/{eventId}")
    @ResponseBody
    public PublicResp swDataReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }
        Map<String,Object> param = eventInfo.getParamMap();
        String nextPage = eventInfo.getNextPage();

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), nextPage,"body",publicReq);
        if(CollectionUtils.isNotEmpty(inputList) && param!=null && !param.isEmpty()) {
            inputList.forEach(in -> param.forEach((k, v) -> {
                if (k.equals(in.getId())) in.setDefValue(v.toString());
            }));
        }
        publicResp.setWebElementDtoList(inputList);
        // 取后续操作
        NextOprDto nextOprDto = webElementService.getCallAfterOpr(eventInfo.getMenu(), eventInfo.getPage(), eventInfo.getElement());
        if(MapUtils.isNotEmpty(param)){
            for (EventInfo eventInfo1 : nextOprDto.getEventInfoList()) {
                eventInfo1.getParamMap().putAll(param);
            }
        }
        publicResp.setNextOprDto(nextOprDto);
        nextOprDto.setShowSw(true);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }

    private NextOprDto getNextOpr(Map<String,Object> param){
        if(param==null) return null;
        String str = JSON.toJSONString(param.get("nextOprDto"));
        if(StringUtils.isBlank(str)) return null;
        return JSON.parseObject(str,NextOprDto.class);
    }
}