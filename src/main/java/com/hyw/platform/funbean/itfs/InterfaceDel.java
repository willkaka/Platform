package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
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

@Service("interfaceDel")
@Slf4j
public class InterfaceDel extends RequestFunUnit<String, InterfaceDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceInterfaceId()),"id不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, InterfaceDel.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceInterface serviceInterface = dataService.getOne(new NQueryWrapper<ServiceInterface>()
                       .eq(ServiceInterface::getServiceInterfaceId, dto.getServiceInterfaceId()));
        Assert.isTrue(serviceInterface!=null,"接口不存在！");

        dataService.deleteById(serviceInterface);

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
    }
}
