package com.hyw.platform.web.service;

import com.alibaba.fastjson.JSONObject;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.FastRuntimeException;
import com.hyw.platform.web.model.ServiceHost;
import com.hyw.platform.web.model.ServiceInterface;
import com.hyw.platform.web.model.ServiceInterfaceData;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.tservice.http.HttpUtil;
import com.hyw.platform.web.util.ExpressContext;
import com.hyw.platform.web.util.ExpressUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CallInterface {

    @Autowired
    private DataService dataService;

    @Value("${service.host.env}")
    private String serviceHostEnv;

    public PublicResp call(String host, String interfaceName, String req) {
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

        PublicResp publicResp = new PublicResp();
        if("get".equalsIgnoreCase(serviceInterface.getRequestMethod())) {
            setGetParam(url, fieldList, req);
            String respStr = null;
            try {
                respStr = HttpUtil.get(url);
            }catch (Exception e){
                log.error("调用接口异常", e);
                throw new FastRuntimeException("调用接口"+host+"_"+interfaceName+"异常，"+e.getMessage());
            }
            Assert.isTrue(StringUtils.isNotBlank(respStr),"调用接口"+host+"_"+interfaceName+"异常，返回数据为空");
            convertResp(respStr, fieldList);
        }else if("post".equalsIgnoreCase(serviceInterface.getRequestMethod())) {
            String content = setPostParam(fieldList, req);
            String respStr = null;
            try {
                respStr = HttpUtil.post(url, content);
            }catch (Exception e){
                log.error("调用接口异常", e);
                throw new FastRuntimeException("调用接口"+host+"_"+interfaceName+"异常，"+e.getMessage());
            }
            Assert.isTrue(StringUtils.isNotBlank(respStr),"调用接口"+host+"_"+interfaceName+"异常，返回数据为空");
            convertResp(respStr, fieldList);
        }
        return publicResp;
    }

    private void setGetParam(String url, List<ServiceInterfaceData> fieldList, String req) {
        Assert.isTrue(StringUtils.isNotBlank(url),"URL不允许为空!");
        if(CollectionUtils.isEmpty(fieldList)) return;
        List<ServiceInterfaceData> inputFieldList = fieldList.stream().filter(field->field.getDataType().equals("req")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(inputFieldList)) return;
        url += "?";
        int count = 0;
        for (ServiceInterfaceData field : inputFieldList) {
            ExpressContext context = new ExpressContext();
            context.put("req", JSONObject.parseObject(req));
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
            url = url + (count>0?"&":"") + field.getFieldName() + "=" + fieldValue;
            count++;
        }
    }

    private String setPostParam(List<ServiceInterfaceData> fieldList, String req) {
        JSONObject jsonObject = new JSONObject();
        if(CollectionUtils.isEmpty(fieldList)) return jsonObject.toJSONString();
        List<ServiceInterfaceData> inputFieldList = fieldList.stream().filter(field->field.getDataType().equals("req")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(inputFieldList)) return jsonObject.toJSONString();
        for (ServiceInterfaceData field : fieldList) {
            ExpressContext context = new ExpressContext();
            context.put("req", JSONObject.parseObject(req));
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
            jsonObject.put(field.getFieldName(), fieldValue);
        }

        return jsonObject.toJSONString();
    }

    private void convertResp(String respStr, List<ServiceInterfaceData> fieldList) {
        if(CollectionUtils.isEmpty(fieldList)) return;
        List<ServiceInterfaceData> respFieldList = fieldList.stream().filter(field->field.getDataType().equals("resp")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(respFieldList)) return;
        for (ServiceInterfaceData field : respFieldList) {
            ExpressContext context = new ExpressContext();
            context.put("resp", JSONObject.parseObject(respStr));
            Object fieldValue = ExpressUtil.run(field.getFieldSource(), context);
        }
    }
}
