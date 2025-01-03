package com.hyw.platform.funbean.abs;

import com.alibaba.fastjson.JSON;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.model.WebCallAfter;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.service.WebElementService;
import com.hyw.platform.web.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 功能请求实现单元基类
 */
@Slf4j
public abstract class RequestFunUnit<D, V extends RequestPubDto> implements RequestFun {

    @Autowired
    private WebElementService webElementService;
    @Autowired
    private DataService dataService;

    /**
     * 执行入口
     * @param requestDto requestDto
     */
    @Override
    public PublicResp execute(PublicReq requestDto){
        // 参数赋值，将请求的参数 RequestDto，
        // 转为V（父类为RequestPubDto）并将requestDto.getReqParm().get("inputValue")中的值赋到对应的字段
        V params = getVariable(requestDto);

        // 必要的输入检查
        checkVariable(params);

        //执行自定义逻辑
        D data = execLogic(requestDto,params);

        PublicResp resp = setPublicResp();

        //返回数据处理
        return returnData(requestDto,data,params,resp);
    }

    /**
     * 自动取定义的参数值
     *   参数赋值，将请求的参数 RequestDto，
     * 转为V（父类为RequestPubDto）并将requestDto.getReqParm().get("inputValue")中的值赋到对应的字段
     * @param requestDto 请求dto
     * @return variable
     */

    private V getVariable(PublicReq requestDto){
        V variable = newInstanceVariable();
        //处理参数
        Map<String, ValueObject> webInputValueMap = requestDto.getWebValueDto().getWebInputValueMap();
        Map<String, ValueObject> camelFieldMap = new HashMap<>();
        for (Map.Entry<String, ValueObject> entry : webInputValueMap.entrySet()) {
            String key = entry.getKey();
            ValueObject value = entry.getValue();
            String modifiedKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( key );
            camelFieldMap.put(modifiedKey, value);
        }
        if(requestDto.getEventInfo() != null && MapUtils.isNotEmpty(requestDto.getEventInfo().getParamMap())){
            for (Map.Entry<String, Object> entry : requestDto.getEventInfo().getParamMap().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String modifiedKey = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( key );
                camelFieldMap.put(modifiedKey, new ValueObject().setValue(value));
            }
        }
        List<Field> fields = ObjectUtil.getAllFieldList(variable.getClass());
        for(Field field:fields){
            String fieldName = com.hyw.platform.web.util.StringUtils.camelCaseToUnderline( field.getName() );
            if(camelFieldMap.containsKey(fieldName)){
                ValueObject valueObject = camelFieldMap.get(fieldName);
                try {
                    if (!field.isAccessible()) { field.setAccessible(true); }
                    field.set(variable, valueConvert(field,valueObject.getValue()));
                } catch (Exception e) {
                    throw new BizException("给对象(" + variable.getClass().getName() + ")属性(" + fieldName + ")赋值(" + valueObject.getValue() + ")失败!");
                }
            }
            // 将对象中的字段 去掉default再将首字母改为小写，与map中的字段匹配
            if(fieldName.startsWith("default_") &&
                    camelFieldMap.containsKey(fieldName.replace("default_",""))){
                ValueObject valueObject = camelFieldMap.get(fieldName.replace("default_",""));
                try {
                    if (!field.isAccessible()) { field.setAccessible(true); }
                    field.set(variable, valueConvert(field,valueObject.getDefValue()));
                } catch (Exception e) {
                    throw new BizException("给对象(" + variable.getClass().getName() + ")属性(" + fieldName + ")赋值(" + valueObject.getDefValue() + ")失败!");
                }
            }
        }
        return variable;
    }

    private Object valueConvert(Field field,Object value){
        if(field.getType() == Integer.class) {
            return Integer.parseInt(value.toString());
        }else if(field.getType() == Double.class) {
            return Double.parseDouble(value.toString());
        }else if(field.getType() == Long.class) {
            return Long.parseLong(value.toString());
        }else if(field.getType() == BigDecimal.class) {
            return new BigDecimal(value.toString());
        }else if(field.getType() == LocalDate.class) {
            return LocalDate.parse(value.toString());
        }else if(field.getType() == LocalTime.class) {
            return LocalTime.parse(value.toString());
        }else if(field.getType() == LocalDateTime.class) {
            return LocalDateTime.parse(value.toString());
        }else{
            return value;
        }
    }

    /**
     * 输入参数检查
     */
    public PublicResp setPublicResp(){ return null;}

    /**
     * 输入参数检查
     * @param variable 参数
     */
    public void checkVariable(V variable){ }

    /**
     * 执行自定义逻辑
     * @param requestDto 请求dto
     * @param variable 参数
     * @return D
     */
    public D execLogic(PublicReq requestDto, V variable){
        return null;
    }

    /**
     * 返回数据处理
     * @param data 数据
     * @param variable 参数
     * @return ReturnDto
     */
    public PublicResp returnData(PublicReq requestDto, D data, V variable, PublicResp resp){
        if(resp!=null){
            return resp;
        }

        PublicResp returnDto = new PublicResp();
        if(requestDto.getEventInfo() != null && StringUtils.isNotBlank(requestDto.getEventInfo().getNextPage())){
            List<WebElementDto> inputList = webElementService.getPageElementsById(requestDto.getEventInfo().getNextPage(), requestDto);
            returnDto.setWebElementDtoList(inputList);
            WebElementDto curElement = null;
            if(requestDto.getEventInfo().getParamMap().containsKey("dataFillToEle")) {
                String dataFillToElementName = (String) requestDto.getEventInfo().getParamMap().get("dataFillToEle");
                curElement = getEleByName(inputList, dataFillToElementName);
            }
            if (curElement != null) {
                if ("textarea".equalsIgnoreCase(curElement.getType())) {
                    curElement.setData(data);
                }
            }
        }

        List<WebCallAfter> webCallAfterList = dataService.list(new NQueryWrapper<WebCallAfter>()
                .eq(WebCallAfter::getMenu, requestDto.getEventInfo().getMenu())
                .eq(WebCallAfter::getPage, requestDto.getEventInfo().getPage())
                .eq(WebCallAfter::getProcessStatus, "success")
                .eq(WebCallAfter::getProcessBean, requestDto.getEventInfo().getReqMapping()));
        List<EventInfo> eventInfoList = new ArrayList<>();
        for(WebCallAfter webCallAfter:webCallAfterList){
            EventInfo eventInfo = new EventInfo();
            eventInfo.setMenu(webCallAfter.getMenu());
            eventInfo.setPage(webCallAfter.getPage());
            eventInfo.setEvent(webCallAfter.getOprType());
            eventInfo.setReqType(webCallAfter.getRequestType());
            eventInfo.setReqMapping(webCallAfter.getRequestBean());
            if(StringUtils.isNotBlank(webCallAfter.getParam())) {
                eventInfo.setParamMap(JSON.parseObject(webCallAfter.getParam()));
            }
            eventInfoList.add(eventInfo);
        }
        if(requestDto.getEventInfo()!=null && MapUtils.isNotEmpty(requestDto.getEventInfo().getParamMap())) {
            for (EventInfo eventInfo : eventInfoList) {
                if(MapUtils.isEmpty(eventInfo.getParamMap())){
                    eventInfo.setParamMap(new HashMap<>());
                }
                eventInfo.getParamMap().putAll(requestDto.getEventInfo().getParamMap());
            }
        }
        returnDto.setNextOprDto(new NextOprDto().setEventInfoList(eventInfoList));
        return returnDto;

    }

    private WebElementDto getEleByName(List<WebElementDto> inputList, String dataFillToElementName){
        for (WebElementDto webElementDto : inputList) {
            if (webElementDto.getId().equals(dataFillToElementName)) {
                return webElementDto;
            }
            if(CollectionUtils.isNotEmpty(webElementDto.getSubElementList())){
                WebElementDto dto = getEleByName(webElementDto.getSubElementList(), dataFillToElementName);
                if(dto!=null){
                    return dto;
                }
            }
        }
        return null;
    }

    /**
     * 字节流转字符流
     * @param bytes
     * @return
     */
    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    /**
     * 实例化变量
     * @return 返回实例化的变量信息
     */
    public V newInstanceVariable() {
        Class<V> vClass = null;
        try {
            Type genericSuperclass = null;
            for (Class clazz = getClass();
                 clazz != null && !((genericSuperclass = clazz.getGenericSuperclass()) instanceof ParameterizedType);
                 clazz = getClass().getSuperclass()) {}

            ParameterizedType type = (ParameterizedType) genericSuperclass;
            Type actualTypeArgument = type.getActualTypeArguments()[1];
            //noinspection unchecked
            vClass = (Class<V>) (actualTypeArgument instanceof Class ? actualTypeArgument : ((ParameterizedType) actualTypeArgument).getRawType());
        } catch (Exception e) {
            throw new RuntimeException(getClass() + " 缺少泛型", e);
        }
        try {
            return vClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(vClass + " 实例化失败", e);
        }
    }
}
