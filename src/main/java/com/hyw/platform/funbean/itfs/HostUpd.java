package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.ServiceHost;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("hostUpd")
@Slf4j
public class HostUpd extends RequestFunUnit<String, HostUpd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(HostUpd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceHostId()),"id不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getHostName()),"主机名称hostName不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getHostEnv()),"环境不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getHostUrl()),"URL不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, HostUpd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceHost serviceHostCheck = dataService.getOne(new NQueryWrapper<ServiceHost>()
                .eq(ServiceHost::getHostName, dto.getHostName())
                .ne(ServiceHost::getServiceHostId, dto.getServiceHostId()));
        Assert.isTrue(serviceHostCheck==null,"主机名称已存在！");

        @SuppressWarnings("unchecked")
        ServiceHost serviceHost = dataService.getOne(new NQueryWrapper<ServiceHost>()
                       .eq(ServiceHost::getServiceHostId, dto.getServiceHostId()));
        serviceHost.setHostName(dto.getHostName());
        serviceHost.setHostEnv(dto.getHostEnv());
        serviceHost.setHostUrl(dto.getHostUrl());
        serviceHost.setHostDesc(dto.getHostDesc());

        dataService.updateById(serviceHost);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        // serviceHostId,hostName,hostEnv,hostUrl,hostDesc
        private String serviceHostId;
        private String hostName;
        private String hostEnv;
        private String hostUrl;
        private String hostDesc;

        // 原值
        private String defaultHostName;
        private String defaultHostEnv;
        private String defaultHostUrl;
        private String defaultHostDesc;
    }
}
