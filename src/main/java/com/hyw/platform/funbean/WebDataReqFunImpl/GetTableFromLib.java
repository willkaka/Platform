package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.gdata.DataService;
import com.hyw.gdata.utils.DbUtil;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.req.WebValueDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

@Service("getTableFromLib")
@Slf4j
public class GetTableFromLib implements WebDataReqFun {

    @Autowired
    private DataService dataService;
    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        Map<String, ValueObject> valueMap = publicReq.getValueMap();
        if(valueMap.isEmpty() ||
                !valueMap.containsKey("dbName") ||
                !valueMap.containsKey("libName") ){
            return new HashMap<>();
        }
        String dbName = valueMap.get("dbName").getValue().toString();
        String libName = valueMap.get("libName").getValue().toString();

        Connection connection = configDatabaseInfoService.getConnection(dbName,libName);
        List<String> tables = DbUtil.getTableNames(connection, libName);
        dataService.closeConnection(connection);
        tables.sort((s1,s2) -> s1.compareTo(s2));

        Map<String,Object> map = new HashMap<>();
        for(String table:tables){
            map.put(table,table);
        }

        return map;
    }
}
