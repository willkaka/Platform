package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.gdata.DataService;
import com.hyw.gdata.utils.DbUtil;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.req.WebValueDto;
import com.hyw.platform.web.resp.EventInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

@Service("getLibFromDb")
@Slf4j
public class GetLibFromDb implements WebDataReqFun {

    @Autowired
    private DataService dataService;
    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        Map<String, ValueObject> valueMap = publicReq.getValueMap();
        if(valueMap.isEmpty() ||
                !valueMap.containsKey("dbName") ){
            return new HashMap<>();
        }
        String dbName = valueMap.get("dbName").getValue().toString();
        Connection connection = configDatabaseInfoService.getConnection(dbName,null);
        List<String> libs = DbUtil.getLibraryNames(connection);
        dataService.closeConnection(connection);

        Map<String,Object> map = new LinkedHashMap<>();
        for(String lib:libs){
            map.put(lib,lib);
        }
        return map;
    }
}
