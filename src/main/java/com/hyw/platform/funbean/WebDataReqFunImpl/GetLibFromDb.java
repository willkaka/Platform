package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.funbean.WebDataReqFun;
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

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        WebValueDto webValueDto = publicReq.getWebValueDto();
        if(webValueDto==null || webValueDto.getWebInputValueMap()==null || !webValueDto.getWebInputValueMap().containsKey("dbName")){
            return new HashMap<>();
        }
        String dbName = webValueDto.getWebInputValueMap().get("dbName").getValue().toString();
        Connection connection = dataService.getDatabaseConnection(dbName,null);
        List<String> libs = DbUtil.getLibraryNames(connection);
        dataService.closeConnection(connection);

        Map<String,Object> map = new LinkedHashMap<>();
        for(String lib:libs){
            map.put(lib,lib);
        }
        return map;
    }
}
