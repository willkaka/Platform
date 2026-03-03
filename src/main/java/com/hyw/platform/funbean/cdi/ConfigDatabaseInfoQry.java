package com.hyw.platform.funbean.cdi;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.model.ConfigDatabaseInfo;
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

@Service("configDatabaseInfoQry")
@Slf4j
public class ConfigDatabaseInfoQry extends RequestFunUnit<Map<String,Object>, ConfigDatabaseInfoQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(ConfigDatabaseInfoQry.QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, ConfigDatabaseInfoQry.QueryVariable dto){
        NQueryWrapper<ConfigDatabaseInfo> nq = new NQueryWrapper<ConfigDatabaseInfo>().setTable(ConfigDatabaseInfo.class);
        if(StringUtils.isNotBlank(dto.getInputDatabaseName())){
            nq.like(ConfigDatabaseInfo::getDatabaseName, dto.getInputDatabaseName());
        }
        if(StringUtils.isNotBlank(dto.getInputDatabaseType())){
            nq.like(ConfigDatabaseInfo::getDatabaseType, dto.getInputDatabaseType());
        }
        List<ConfigDatabaseInfo> list = dataService.list(nq);
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
        private String inputDatabaseName;
        private String inputDatabaseType;
    }
}
