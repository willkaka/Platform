package com.hyw.platform.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.exception.FastRuntimeException;
import com.hyw.platform.tservice.http.HttpServiceInvoker;
import com.hyw.platform.web.model.ServiceHost;
import com.hyw.platform.web.model.ServiceInterface;
import com.hyw.platform.web.model.ServiceInterfaceData;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.util.ExpressContext;
import com.hyw.platform.web.util.ExpressUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CallInterface {

    @Autowired
    private DataService dataService;

    @Autowired
    private HttpServiceInvoker httpServiceInvoker;

    @Value("${service.host.env}")
    private String serviceHostEnv;

    public <T> T call(String host, String interfaceName, PublicReq req, Class<T> clazz) {
        JSONObject jsonObject = call(host, interfaceName, req);
        T obj;
        try {
            obj = clazz.newInstance();
        }catch (Exception e){
            log.error("实例化对象异常", e);
            throw new FastRuntimeException("实例化对象异常！");
        }
        try {
            obj = JSON.parseObject(jsonObject.toJSONString(), clazz);
        }catch (Exception e){
            log.error("解析对象异常", e);
            throw new FastRuntimeException("解析对象异常！");
        }
        return obj;
    }

    public JSONObject call(String host, String interfaceName, PublicReq req) {
        ServiceInterface serviceInterface = dataService.getOne(new NQueryWrapper<ServiceInterface>()
                .eq(ServiceInterface::getHostName, host)
                .eq(ServiceInterface::getInterfaceName, interfaceName));
        Assert.isTrue(serviceInterface!=null,"接口不存在");

        ServiceHost serviceHost = dataService.getOne(new NQueryWrapper<ServiceHost>()
                .eq(ServiceHost::getHostName, host)
                .eq(ServiceHost::getHostEnv, serviceHostEnv));
        Assert.isTrue(serviceHost!=null,"服务不存在");

        List<ServiceInterfaceData> fieldList = dataService.list(new NQueryWrapper<ServiceInterfaceData>()
                .eq(ServiceInterfaceData::getInterfaceName, interfaceName));

        String url = serviceHost.getHostUrl() + serviceInterface.getInterfacePath();

        // 设置请求参数
        HttpMethod callMethod = "get".equalsIgnoreCase(serviceInterface.getRequestMethod())?HttpMethod.GET:HttpMethod.POST;
        String content = null;
        if(HttpMethod.GET.equals(callMethod)) {
            url = setGetParam(url, fieldList, req);
        }else {
            content = setPostParam(fieldList, req);
        }

        // 调用接口
        String respStr = call(host, interfaceName, url, callMethod, content);
        JSONObject respJson = JSON.parseObject(respStr);
        if(respJson.containsKey("code") && !"200".equals(respJson.getString("code"))){
            throw new BizException(respJson.getString("code"), respJson.getString("message"));
        }

        // 解析返回值
        JSONObject jsonObject = convertResp(respStr, fieldList);
        return jsonObject;
    }


    private String call(String host, String interfaceName, String url, HttpMethod callMethod, String content){
        String respStr = null;
        try {
            log.info("调用接口：{}，请求参数：{}", url, JSON.toJSONString(content));
            respStr = httpServiceInvoker.executeRequest(url, callMethod, content, String.class);
            log.info("调用接口返回数据：{}", respStr);
        }catch (Exception e){
            log.error("调用接口异常", e);
            throw new FastRuntimeException("调用接口"+host+"_"+interfaceName+"异常，"+e.getMessage());
        }
        return respStr;
    }

    private String setGetParam(String url, List<ServiceInterfaceData> fieldList, PublicReq req) {
        Assert.isTrue(StringUtils.isNotBlank(url),"URL不允许为空!");
        if(CollectionUtils.isEmpty(fieldList)) return url;
        List<ServiceInterfaceData> inputFieldList = fieldList.stream().filter(field->field.getDataType().equals("req")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(inputFieldList)) return url;
        url += "?";

        ExpressContext context = new ExpressContext();
        context.put("req", req);
        context.put("inputCurValue", req.getCurValueJson());
        context.put("inputDefValue", req.getDefValueJson());

        int count = 0;
        for (ServiceInterfaceData field : inputFieldList) {
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
            url = url + (count>0?"&":"") + field.getFieldName() + "=" + (fieldValue==null?"":fieldValue);
            count++;
        }
        return url;
    }

    private String setPostParam(List<ServiceInterfaceData> fieldList, PublicReq req) {
        JSONObject jsonObject = new JSONObject();
        if(CollectionUtils.isEmpty(fieldList)) return jsonObject.toJSONString();
        List<ServiceInterfaceData> inputFieldList = fieldList.stream().filter(field->field.getDataType().equals("req")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(inputFieldList)) return jsonObject.toJSONString();

        ExpressContext context = new ExpressContext();
        context.put("req", req);
        context.put("inputCurValue", req.getCurValueJson());
        context.put("inputDefValue", req.getDefValueJson());
        for (ServiceInterfaceData field : fieldList) {
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
            jsonObject.put(field.getFieldName(), fieldValue);
        }//req.webValueDto.webInputValueMap.transSeq.value

        return jsonObject.toJSONString();
    }

    private JSONObject convertResp(String respStr, List<ServiceInterfaceData> fieldList) {
        JSONObject jsonObject = new JSONObject();
        if(CollectionUtils.isEmpty(fieldList)) return JSON.parseObject(respStr);
        List<ServiceInterfaceData> respFieldList = fieldList.stream().filter(field->field.getDataType().equals("resp")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(respFieldList)) return JSON.parseObject(respStr);
        for (ServiceInterfaceData field : respFieldList) {
            ExpressContext context = new ExpressContext();
            context.put("resp", JSON.parseObject(respStr));
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
//            if("list".equalsIgnoreCase(field.getDataType())){
//                List<JSONObject> dataList = (List<JSONObject>) fieldValue;
//                List<ServiceInterfaceData> subFieldList = fieldList.stream().filter(f->f.getDataType().equals(field.getFieldName())).collect(Collectors.toList());
//                if(CollectionUtils.isEmpty(subFieldList)){
//                    jsonObject.put(field.getFieldName(), fieldValue);
//                    continue;
//                }
//                for (JSONObject subObject : dataList) {
//
//                }
//            }else {
//            }
            jsonObject.put(field.getFieldName(), fieldValue);
        }
        return jsonObject;
    }
}
