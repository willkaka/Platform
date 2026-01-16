package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.ServiceInterfaceData;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("interfaceDataDel")
@Slf4j
public class InterfaceDataDel extends RequestFunUnit<String, InterfaceDataDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceDataDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getServiceInterfaceDataId()),"id不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, InterfaceDataDel.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ServiceInterfaceData serviceInterfaceData = dataService.getOne(new NQueryWrapper<ServiceInterfaceData>()
                .eq(ServiceInterfaceData::getServiceInterfaceDataId, dto.getServiceInterfaceDataId()));
        Assert.isTrue(serviceInterfaceData!=null,"接口字段不存在！");
        dataService.deleteById(serviceInterfaceData);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String serviceInterfaceDataId;
    }
}
