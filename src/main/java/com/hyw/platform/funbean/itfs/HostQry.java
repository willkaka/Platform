package com.hyw.platform.funbean.itfs;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hostQry")
@Slf4j
public class HostQry extends RequestFunUnit<Map<String,Object>, HostQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(HostQry.QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, HostQry.QueryVariable dto){
        List<ServiceHost> list = dataService.list(new NQueryWrapper<ServiceHost>()
                        .setTable(ServiceHost.class)
                       .eq(StringUtils.isNotBlank(dto.getQueryConditionHost()), ServiceHost::getHostName, dto.getQueryConditionHost())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionEnv()), ServiceHost::getHostEnv, dto.getQueryConditionEnv())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionHostDesc()), ServiceHost::getHostDesc, dto.getQueryConditionHostDesc())
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
        private String queryConditionHostDesc;
        private String queryConditionEnv;
    }
}
