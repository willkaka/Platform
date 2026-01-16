package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.ServiceHost;
import com.hyw.platform.web.model.ServiceInterface;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("interfaceUpd")
@Slf4j
public class InterfaceUpd extends RequestFunUnit<String, InterfaceUpd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceUpd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceInterfaceId()),"id不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getHostName()),"主机名称hostName不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfaceName()),"接口名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfacePath()),"接口路径不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getRequestMethod()),"接口请求方式不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfaceDesc()),"接口描述不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, InterfaceUpd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceHost serviceHostCheck = dataService.getOne(new NQueryWrapper<ServiceHost>().eq(ServiceHost::getHostName, dto.getHostName()));
        Assert.isTrue(serviceHostCheck!=null,"主机名称不存在！");

        @SuppressWarnings("unchecked")
        ServiceInterface serviceInterfaceCheck = dataService.getOne(new NQueryWrapper<ServiceInterface>()
                        .eq(ServiceInterface::getHostName, dto.getHostName())
                        .eq(ServiceInterface::getInterfaceName, dto.getInterfaceName())
                        .ne(ServiceInterface::getServiceInterfaceId, dto.getServiceInterfaceId()));
        Assert.isTrue(serviceInterfaceCheck==null,"接口名称已存在！");

        @SuppressWarnings("unchecked")
        ServiceInterface serviceInterface = dataService.getOne(new NQueryWrapper<ServiceInterface>()
                       .eq(ServiceInterface::getServiceInterfaceId, dto.getServiceInterfaceId()));
        serviceInterface.setHostName(dto.getHostName());
        serviceInterface.setInterfaceName(dto.getInterfaceName());
        serviceInterface.setInterfacePath(dto.getInterfacePath());
        serviceInterface.setRequestMethod(dto.getRequestMethod());
        serviceInterface.setInterfaceDesc(dto.getInterfaceDesc());
        serviceInterface.setCharset(dto.getCharset());

        dataService.updateById(serviceInterface);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String serviceInterfaceId;
        private String hostName;
        private String interfaceName;
        private String interfacePath;
        private String requestMethod;
        private String interfaceDesc;
        private String charset;

        // 原值
        private String defaultHostName;
        private String defaultInterfaceName;
        private String defaultInterfacePath;
        private String defaultRequestMethod;
        private String defaultInterfaceDesc;
        private String defaultCharset;
    }
}
