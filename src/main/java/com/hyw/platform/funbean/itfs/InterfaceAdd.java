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

@Service("interfaceAdd")
@Slf4j
public class InterfaceAdd extends RequestFunUnit<String, InterfaceAdd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceAdd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getHost()),"主机名称hostName不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfaceName()),"接口名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfacePath()),"接口路径不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getRequestMethod()),"接口请求方式不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getInterfaceDesc()),"接口描述不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, InterfaceAdd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceHost serviceHostCheck = dataService.getOne(new NQueryWrapper<ServiceHost>().eq(ServiceHost::getHostName, dto.getHost()));
        Assert.isTrue(serviceHostCheck!=null,"主机名称不存在！");

        @SuppressWarnings("unchecked")
        ServiceInterface serviceInterfaceCheck = dataService.getOne(new NQueryWrapper<ServiceInterface>()
                        .eq(ServiceInterface::getHostName, dto.getHost())
                        .eq(ServiceInterface::getInterfaceName, dto.getInterfaceName()));
        Assert.isTrue(serviceInterfaceCheck==null,"接口名称已存在！");

        ServiceInterface serviceInterface = new ServiceInterface();
        serviceInterface.setHostName(dto.getHost());
        serviceInterface.setInterfaceName(dto.getInterfaceName());
        serviceInterface.setInterfacePath(dto.getInterfacePath());
        serviceInterface.setRequestMethod(dto.getRequestMethod());
        serviceInterface.setInterfaceDesc(dto.getInterfaceDesc());
        serviceInterface.setCharset(dto.getCharset());

        dataService.save(serviceInterface);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String host;
        private String interfaceName;
        private String interfacePath;
        private String requestMethod;
        private String interfaceDesc;
        private String charset;
    }
}
