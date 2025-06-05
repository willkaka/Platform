package com.hyw.platform.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.gdata.dto.TableFieldInfo;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.funbean.WebTableDataReqFun;
import com.hyw.platform.web.model.*;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.resp.webElement.TableNormal;
import com.hyw.platform.web.util.HttpUtil;
import com.hyw.platform.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class WebElementService {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataService dataService;

    private static final String FORMATTER_DATE = "yyyy-MM-dd";
    private static final String FORMATTER_DATETIME = "yyyy-MM-dd HH:mm:ss";

    @SuppressWarnings("unchecked")
    public List<WebElementDto> getPageElementsByParentEle(String menu, String page, String parentElement,PublicReq publicReq){
        List<WebElementDto> webElementDtoDtoList = new ArrayList<>();
        List<WebElement> webElementList = 
                dataService.list(new NQueryWrapper<WebElement>()
                .eq(WebElement::getMenu,menu)
                .eq(WebElement::getPage,page)
                .eq(StringUtils.isNotBlank(parentElement),WebElement::getElementParent,parentElement));
        for(WebElement webElement:webElementList){
            //检查是否已存在该元素，存在则不再加载。
            if(existEleInSub(webElementDtoDtoList,webElement)) continue;
            List<WebElementDto> webElementDtos = convert2Dto(webElement,publicReq);
            if(CollectionUtils.isNotEmpty(webElementDtos)) webElementDtoDtoList.addAll(webElementDtos);
        }
        return webElementDtoDtoList;
    }

    public NextOprDto getCallAfterOpr(String menu, String page, String reqMapping){
        List<WebCallAfter> webCallAfterList = dataService.list(new NQueryWrapper<WebCallAfter>()
                .eq(WebCallAfter::getMenu, menu)
                .eq(WebCallAfter::getPage, page)
                .eq(WebCallAfter::getProcessStatus, "success")
                .eq(WebCallAfter::getProcessBean, reqMapping));
        List<EventInfo> eventInfoList = new ArrayList<>();
        for(WebCallAfter webCallAfter:webCallAfterList){
            EventInfo eventInfo = new EventInfo();
            eventInfo.setMenu(webCallAfter.getMenu());
            eventInfo.setPage(webCallAfter.getPage());
            eventInfo.setEvent(webCallAfter.getOprType());
            eventInfo.setReqType(webCallAfter.getRequestType());
            eventInfo.setReqMapping(webCallAfter.getRequestBean());
            if(org.apache.commons.lang3.StringUtils.isNotBlank(webCallAfter.getParam())) {
                eventInfo.setParamMap(JSON.parseObject(webCallAfter.getParam()));
            }
            eventInfoList.add(eventInfo);
        }
        NextOprDto nextOprDto = new NextOprDto();
        nextOprDto.setEventInfoList(eventInfoList);
        return nextOprDto;
    }

    @SuppressWarnings("unchecked")
    public List<WebElementDto> getPageElementsById(String id, PublicReq publicReq){
        List<WebElementDto> webElementDtoDtoList = new ArrayList<>();
        WebElement webElement = dataService.getOne(new NQueryWrapper<WebElement>()
                    .eq(WebElement::getElementNo, id));
        List<WebElementDto> webElementDtos = convert2Dto(webElement,publicReq);
        if(CollectionUtils.isNotEmpty(webElementDtos)) webElementDtoDtoList.addAll(webElementDtos);
        return webElementDtoDtoList;
    }

    public boolean existEleInSub(List<WebElementDto> webElementDtoList,WebElement webElement){
        if(CollectionUtils.isEmpty(webElementDtoList)) return false;
        for(WebElementDto dto:webElementDtoList){
            if(dto.getId().equals(webElement.getElement())) {
                return true;
            }
            if(existEleInSub(dto.getSubElementList(),webElement)){
                return true;
            }
        }
        return false;
    }

    public List<WebElementDto> getPageElements(String menu, String page, String element,PublicReq publicReq){
        List<WebElementDto> webElementDtoDtoList = new ArrayList<>();
        List<WebElement> webElementList =
                dataService.list(new NQueryWrapper<WebElement>()
                        .eq(WebElement::getMenu,menu)
                        .eq(WebElement::getPage,page)
                        .eq(StringUtils.isNotBlank(element),WebElement::getElement,element));
        for(WebElement webElement:webElementList){
            List<WebElementDto> webElementDtos = convert2Dto(webElement,publicReq);
            if(CollectionUtils.isNotEmpty(webElementDtos)) webElementDtoDtoList.addAll(webElementDtos);
        }
        return webElementDtoDtoList;
    }


    @SuppressWarnings("unchecked")
    public List<WebElementDto> getSubElements(String menu, String page, String parentElement,PublicReq publicReq){
        List<WebElementDto> webElementDtoDtoList = new ArrayList<>();
        List<WebElement> webElementList =
                dataService.list(new NQueryWrapper<WebElement>()
                        .eq(WebElement::getMenu,menu)
                        .eq(WebElement::getPage,page)
                        .eq(WebElement::getElementParent,parentElement));
        for(WebElement webElement:webElementList){
            List<WebElementDto> webElementDtos = convert2Dto(webElement,publicReq);
            if(CollectionUtils.isNotEmpty(webElementDtos)) webElementDtoDtoList.addAll(webElementDtos);
        }
        return webElementDtoDtoList;
    }

    private List<WebElementDto> convert2Dto(WebElement webElement,PublicReq publicReq){
        if(null == webElement) return null;
        List<WebElementDto> webElementDtos = new ArrayList<>();
        WebElementDto webElementDto = new WebElementDto(webElement);
        webElementDto.setEventInfoList(getEventInfoList(webElement.getMenu(),webElement.getPage(),webElement.getElement()));

        if(MapUtils.isNotEmpty(webElementDto.getAttrMap())){
            for (Map.Entry<String,String> entry : webElementDto.getAttrMap().entrySet()) {
                String attrKey = entry.getKey();
                String attrValue = entry.getValue();
                attrValue = parseValue(attrValue);
                webElementDto.getAttrMap().put(attrKey, attrValue);
            }
        }

        if("table".equalsIgnoreCase(webElement.getElementType())){
            createTable(webElementDto,webElement,publicReq);
        }else if("inputList".equalsIgnoreCase(webElement.getElementType())){
            webElementDto.setData(getDataValue(webElement.getMenu(),webElement.getPage(),webElement.getElement(),publicReq));
            webElementDtos.addAll(getSwInputList(webElement));
        }else{
            webElementDto.setData(getDataValue(webElement.getMenu(),webElement.getPage(),webElement.getElement(),publicReq));
        }
        webElementDto.setSubElementList(getSubElements(webElement.getMenu(), webElement.getPage(), webElement.getElement(),publicReq));
        webElementDtos.add(webElementDto);
        return webElementDtos;
    }

    /**
     * 取非触发的下拉数据选项
     * @param menu 菜单
     * @param element 元素
     * @return Map<String, String>
     */
    private Map<String, String> getDataValue(String menu,String page,String element,PublicReq publicReq){
        Map<String, String> dataMap = new HashMap<>();
        //取元素配置值
        WebData webData = dataService.getOne(new NQueryWrapper<WebData>()
                .eq(WebData::getMenu,menu)
                .eq(WebData::getPage,page)
                .eq(WebData::getElement,element));
        if(webData==null || WebUtil.isBlank(webData.getExpress())) return dataMap;

        if("optionList".equals(webData.getDataAttr())) {
            JSONArray jsonArray = JSON.parseArray(webData.getExpress());
            for (int i = 0; i < jsonArray.size(); i++) {
                dataMap.put((String) jsonArray.getJSONObject(i).get("value"), (String) jsonArray.getJSONObject(i).get("text"));
            }
        }else if("sql".equals(webData.getDataAttr())) {
            String sql= webData.getExpress();
            sql = sql.replaceAll("#menu#","'"+menu+"'");
            sql = sql.replaceAll("#page#","'"+page+"'");
            List<Map<String, Object>> dataMaps = dataService.mapList(new NQueryWrapper<>()
                    .setSql(sql));
            dataMap = WebUtil.getValueMap(dataMaps);
        }else if("fun".equals(webData.getDataAttr())) {
            String funBean = webData.getExpress();
            Map<String,Object> dataMapObject = ((WebDataReqFun) context.getBean(funBean)).execute(publicReq);
            for(String key:dataMapObject.keySet()){
                dataMap.put(key,(String)dataMapObject.get(key));
            }
        }else if("callInterface".equals(webData.getDataType())) {
            dataMap = callInterface(webData.getDataAttr(), webData.getExpress(), publicReq);
        }
        return dataMap;
    }

    private Map<String, String> callInterface(String interfaceName, String interfaceParam, PublicReq publicReq){
        ServiceInterface serviceInterface = dataService.getOne(new NQueryWrapper<ServiceInterface>()
               .eq(ServiceInterface::getInterfaceName,interfaceName));
        if(serviceInterface==null) return null;

        ServiceHost serviceHost = dataService.getOne(new NQueryWrapper<ServiceHost>()
              .eq(ServiceHost::getHostName,serviceInterface.getHostName()));
        if(serviceHost==null) return null;

        List<ServiceInterfaceData> dataList = dataService.list(new NQueryWrapper<ServiceInterfaceData>()
              .eq(ServiceInterfaceData::getInterfaceName,interfaceName)
                .eq(ServiceInterfaceData::getDataType,"input"));
        if(CollectionUtils.isEmpty(dataList)) return null;
        Map<String,Object> param = getAllInputData(publicReq);
        JSONObject dataJson = JSON.parseObject(interfaceParam);
        if(dataJson.containsKey("inputParam")) {
            Map<String, Object> paramMap = dataJson.getJSONObject("inputParam");
            param.putAll(paramMap);
        }
        //组装url(接口地址+接口路径)
        String url = serviceHost.getHostUrl() + serviceInterface.getInterfacePath();
        JSONObject respJson = HttpUtil.getHttpRequestData(url, serviceInterface.getRequestMethod(), serviceInterface.getCharset(),
                getDefinedParam(dataList,param));

        String dataPosition = dataJson.getString("dataPosition");
        String dataType = dataJson.getString("dataType");
        String keyField = dataJson.getString("keyField");
        String valueField = dataJson.getString("valueField");
        Map<String,String> rtnMap = new HashMap<>();
        if("list".equalsIgnoreCase(dataType)){
            assert respJson != null;
            JSONArray jsonArray = respJson.getJSONArray(dataPosition);
            for(Object object:jsonArray){
                JSONObject jsonObject = (JSONObject) object;
                String fieldName = jsonObject.getString(keyField);
                String fieldValue = jsonObject.getString(valueField);
                rtnMap.put(fieldName,fieldValue);
            }
        }
        return rtnMap;
    }

    private JSONObject getDefinedParam(List<ServiceInterfaceData> dataList, Map<String,Object> allInputData){
        Map<String,Object> param = new HashMap<>();
        for(ServiceInterfaceData data:dataList){
            if(allInputData.containsKey(data.getFieldName())){
                param.put(data.getFieldName(),allInputData.get(data.getFieldName()));
            }
        }
        return JSON.parseObject(JSON.toJSONString(param));
    }

    private Map<String,Object> getAllInputData(PublicReq publicReq){
        Map<String, Object> rtnMap = new HashMap<>();

        Map<String, ValueObject> webInputValueMap = publicReq.getWebValueDto().getWebInputValueMap();
        for (Map.Entry<String, ValueObject> entry : webInputValueMap.entrySet()) {
            String key = entry.getKey();
            ValueObject value = entry.getValue();
            String modifiedKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( key );
            rtnMap.put(modifiedKey, value.getValue());
        }
        if(publicReq.getEventInfo() != null && MapUtils.isNotEmpty(publicReq.getEventInfo().getParamMap())){
            for (Map.Entry<String, Object> entry : publicReq.getEventInfo().getParamMap().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String modifiedKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( key );
                rtnMap.put(modifiedKey, value);
            }
        }
        return rtnMap;
    }
    /**
     * 取事件信息
     * @param menu 菜单
     * @param page 区域
     * @param element 元素
     * @return List<EventInfo>
     */
    private List<EventInfo> getEventInfoList(String menu, String page, String element){
        List<EventInfo> eventInfoList = new ArrayList<>();

        //取配置的事件
        List<WebEvent> webEventInfoList = dataService.list(new NQueryWrapper<WebEvent>()
                .eq(WebEvent::getMenu,menu)
                .eq(WebEvent::getPage,page)
                .eq(WebEvent::getElement,element));
        for(WebEvent webEventInfo:webEventInfoList){
            //取由该事件触发的事件
            List<WebTrigger> webTriggerInfoList = dataService.list(new NQueryWrapper<WebTrigger>()
                    .eq(WebTrigger::getSourceMenu,webEventInfo.getMenu())
                    .eq(WebTrigger::getSourcePage,webEventInfo.getPage())
                    .eq(WebTrigger::getSourceElement,webEventInfo.getElement()));
            if(WebUtil.isEmpty(webTriggerInfoList)) {
                eventInfoList.add(createEventInfo(webEventInfo,null));
            }else{
                webTriggerInfoList.forEach(trigger->eventInfoList.add(createEventInfo(webEventInfo,trigger)));
            }
        }
        return eventInfoList;
    }


    /**
     * 生成事件信息（包含该事件需要触发的事件）
     * @param webEventInfo 事件信息
     * @param webTriggerInfo 触发事件
     * @return EventInfo
     */
    private EventInfo createEventInfo(WebEvent webEventInfo,WebTrigger webTriggerInfo){
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEvent(webEventInfo.getEventType());
        eventInfo.setReqType(webEventInfo.getRequestType());
        eventInfo.setReqMapping(webEventInfo.getRequestBean());
        eventInfo.setMenu(webEventInfo.getMenu());
        eventInfo.setPage(webEventInfo.getPage());
        eventInfo.setElement(webEventInfo.getElement());
        eventInfo.setNextPage(webEventInfo.getNextPage());
        //事件参数
        Map<String, Object> eventParam = JSON.parseObject(webEventInfo.getParam());//json转map
        eventInfo.setParamMap(eventParam);
        if (WebUtil.isNotEmpty(eventParam)) {
            if (eventParam.containsKey("withPage")) {
                boolean isWithPage = (boolean) eventParam.get("withPage");
                eventInfo.setWithPage(isWithPage);
            }
        }
        if(webTriggerInfo == null) return eventInfo;
        eventInfo.setRelEleChgType(webTriggerInfo.getTriggerElementType());
        eventInfo.setRelEleType(webTriggerInfo.getTriggerType());
        eventInfo.setRelEleId(webTriggerInfo.getTriggerElement());
//        eventInfo.setTriggerParamMap(JSON.parseObject(webTriggerInfo.getParam()));
        return eventInfo;
    }

    /**
     * table
     * @param webElement 元素定义信息
     * @return WebElementDto
     */
    private void createTable(WebElementDto webElementDto,WebElement webElement,PublicReq publicReq){
//        if(!"table".equalsIgnoreCase(webElement.getElementType())) return null;

        TableNormal tableNormal = new TableNormal();
        tableNormal.setId(webElement.getElement());
        tableNormal.setWithPage(false);
        //取元素配置值
        WebData webData = dataService.getOne(new NQueryWrapper<WebData>()
                .eq(WebData::getMenu,webElement.getMenu())
                .eq(WebData::getPage,webElement.getPage())
                .eq(WebData::getElement,webElement.getElement()));
        if(webData!=null && WebUtil.isNotBlank(webData.getExpress())) {
            if ("sql".equals(webData.getDataType())) {
                String sql = webData.getExpress();
                if(publicReq.getEventInfo()!=null && publicReq.getEventInfo().getParamMap()!=null) {
                    Map<String, Object> paramMap = publicReq.getEventInfo().getParamMap();
                    for (String key : paramMap.keySet()) {
                        if (StringUtils.isNotBlank(key)) {
                            sql = sql.replaceAll("#" + key + "#", "'" + paramMap.get(key) + "'");
                        }
                    }
                }
                if(publicReq.getWebValueDto()!=null && MapUtils.isNotEmpty(publicReq.getWebValueDto().getWebInputValueMap())){
                    for (String key : publicReq.getWebValueDto().getWebInputValueMap().keySet()) {
                        if (StringUtils.isNotBlank(key)) {
                            sql = sql.replaceAll("#" + key + "#", "'" + publicReq.getWebValueDto().getWebInputValueMap().get(key).getValue() + "'");
                        }
                    }
                }
                //表头
                List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldListBySql(sql);
                Map<String,String> headFieldMap = new LinkedHashMap<>();
                tableFieldInfoList.forEach(t->headFieldMap.put(t.getFieldName(),t.getComment()));
                tableNormal.getHeadMap().putAll(headFieldMap);
                //记录
                List<Map<String, Object>> dataMaps = dataService.mapList(new NQueryWrapper<>()
                        .setSql(sql));
                tableNormal.getRecordList().addAll(dataMaps);  //List<Map<String, Object>>
            } else if ("fun".equals(webData.getDataType())) {
                String funBean = webData.getExpress();
                tableNormal = ((WebTableDataReqFun) context.getBean(funBean)).execute(publicReq);
            }
        }

        webElementDto.setData(tableNormal);
    }

    /**
     * table
     * @param webElement 元素定义信息
     * @return WebElementDto
     */
    private List<WebElementDto> getSwInputList(WebElement webElement){
//        if(!"table".equalsIgnoreCase(webElement.getElementType())) return null;
//        WebElementDto webElementDto = new WebElementDto(webElement);
        List<WebElementDto> webElementDtos = new ArrayList<>();
        //取元素配置值
        WebData webData = dataService.getOne(new NQueryWrapper<WebData>()
                .eq(WebData::getMenu,webElement.getMenu())
                .eq(WebData::getPage,webElement.getPage())
                .eq(WebData::getElement,webElement.getElement()));
        if(webData!=null && WebUtil.isNotBlank(webData.getExpress())) {
            if ("sql".equals(webData.getDataType())) {
                String sql = webData.getExpress();
                sql = sql.replaceAll("#menu#","'"+webElement.getMenu()+"'");
                sql = sql.replaceAll("#page#","'"+webElement.getPage()+"'");
                //表头
                List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldListBySql(sql);
                Map<String,String> headFieldMap = new HashMap<>();
                int seq = 0;
                for(TableFieldInfo t:tableFieldInfoList){
                    headFieldMap.put(t.getFieldName(),t.getComment());
                    WebElementDto webElementDto = new WebElementDto(webElement);
                    webElementDto.setSortNo(seq++);
                    webElementDto.setType("input");
                    webElementDto.setId(t.getFieldName());
                    webElementDto.setDesc(t.getComment());
                    webElementDto.setAttrMap(getAttrMap(webElement.getElementAttr(), ",", "="));
                    webElementDtos.add(webElementDto);
                }
            } else if ("fun".equals(webData.getDataType())) {
//            String funBean = webData.getExpress();
//            Map<String,Object> dataMapObject = ((WebDataReqFun) context.getBean(funBean)).execute(null);
//            for(String key:dataMapObject.keySet()){
//                dataMap.put(key,(String)dataMapObject.get(key));
//            }
            }
        }

        return webElementDtos;
    }

    /**
     * 解析分隔符和连接符组成的字符串
     *
     * @param s         字符串
     * @param separator 分隔符
     * @param connector 连接符
     * @return Map<String, String>
     */
    public Map<String, String> getAttrMap(String s, String separator, String connector) {
        Map<String, String> attrMap = new HashMap<>();
        if (WebUtil.isBlank(s)) return attrMap;
        String[] attrs = s.split(separator);
        for (String attrExpress : attrs) {
            String[] express = attrExpress.split(connector);
            String value = express[1].replace("\"", "");
            value = parseValue(value);
            attrMap.put(express[0], value);
        }
        return attrMap;
    }

    private String parseValue(String value){
        Map<String,Object> variablesMap = new HashMap<>();
        variablesMap.put("curDate", LocalDate.now().format(DateTimeFormatter.ofPattern(FORMATTER_DATE)));
        variablesMap.put("curDateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMATTER_DATETIME)));
        variablesMap.put("preDate", LocalDate.now().minusDays(1L).format(DateTimeFormatter.ofPattern(FORMATTER_DATE)));
        variablesMap.put("preDateTime", LocalDateTime.now().minusDays(1L).format(DateTimeFormatter.ofPattern(FORMATTER_DATETIME)));
        variablesMap.put("curMonthFirstDateTime", LocalDate.now().withDayOfMonth(1).atStartOfDay().format(DateTimeFormatter.ofPattern(FORMATTER_DATETIME)));
        variablesMap.put("lastMonthFirstDate", LocalDate.now().minusMonths(1L).withDayOfMonth(1).format(DateTimeFormatter.ofPattern(FORMATTER_DATE)));
        variablesMap.put("lastMonthFirstDateTime", LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().format(DateTimeFormatter.ofPattern(FORMATTER_DATETIME)));
        variablesMap.put("lastMonthLastDate", LocalDate.now().withDayOfMonth(1).minusMonths(1L).format(DateTimeFormatter.ofPattern(FORMATTER_DATE)));
        // 替换
        String newValue = value;
        for (Map.Entry<String,Object> entry : variablesMap.entrySet()) {
            String key = entry.getKey();
            Object paramValue = entry.getValue();
            if(value.contains("#{"+key+"}")){
                newValue = newValue.replace(("#{"+key+"}"), (CharSequence) paramValue);
            }
        }
        return newValue;
    }
}
