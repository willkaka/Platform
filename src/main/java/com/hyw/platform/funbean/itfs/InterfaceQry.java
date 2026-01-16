package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("interfaceQry")
@Slf4j
public class InterfaceQry extends RequestFunUnit<Map<String,Object>, InterfaceQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceQry.QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, InterfaceQry.QueryVariable dto){
        List<ServiceInterface> list = dataService.list(new NQueryWrapper<ServiceInterface>()
                        .setTable(ServiceInterface.class)
                       .eq(StringUtils.isNotBlank(dto.getQueryConditionHost()), ServiceInterface::getHostName, dto.getQueryConditionHost())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionInterface()), ServiceInterface::getInterfaceName, dto.getQueryConditionInterface())
               .eq(StringUtils.isNotBlank(dto.getQueryConditionInterfaceDesc()), ServiceInterface::getInterfaceDesc, dto.getQueryConditionInterfaceDesc())
        );
        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("data",list);
        return rtnMap;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String queryConditionHost;
        private String queryConditionInterface;
        private String queryConditionInterfaceDesc;
    }
}
