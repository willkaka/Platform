package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.web.req.PublicReq;
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

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        WebValueDto webValueDto = publicReq.getWebValueDto();
        if(webValueDto==null || webValueDto.getWebInputValueMap()==null || !webValueDto.getWebInputValueMap().containsKey("dbName")
                || !webValueDto.getWebInputValueMap().containsKey("libName")){
            return new HashMap<>();
        }
        String dbName = webValueDto.getWebInputValueMap().get("dbName").getValue().toString();
        String libName = webValueDto.getWebInputValueMap().get("libName").getValue().toString();

        Connection connection = dataService.getDatabaseConnection(dbName,libName);
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
