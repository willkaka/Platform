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

@Service("hostDel")
@Slf4j
public class HostDel extends RequestFunUnit<String, HostDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(HostDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceHostId()),"id不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, HostDel.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceHost serviceHost = dataService.getOne(new NQueryWrapper<ServiceHost>()
                .eq(ServiceHost::getServiceHostId, dto.getServiceHostId()));
        Assert.isTrue(serviceHost!=null,"接口字段不存在！");
        dataService.deleteById(serviceHost);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String serviceHostId;
    }
}
