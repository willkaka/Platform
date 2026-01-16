package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("interfaceDataQry")
@Slf4j
public class InterfaceDataQry extends RequestFunUnit<Map<String,Object>, InterfaceDataQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(InterfaceDataQry.QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, InterfaceDataQry.QueryVariable dto){
        List<ServiceInterfaceData> list = dataService.list(new NQueryWrapper<ServiceInterfaceData>()
                        .setTable(ServiceInterfaceData.class)
                .eq(StringUtils.isNotBlank(dto.getInterfaceName()), ServiceInterfaceData::getInterfaceName, dto.getInterfaceName())
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
        private String interfaceName;
        private String queryConditionInterfaceDesc;
    }
}
