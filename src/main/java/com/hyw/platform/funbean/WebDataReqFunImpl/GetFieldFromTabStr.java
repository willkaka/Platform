package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.FieldAttr;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.funbean.abs.RequestTableDataUnit;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.WebValueDto;
import com.hyw.platform.web.resp.webElement.TableNormal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("getFieldFromTabStr")
@Slf4j
public class GetFieldFromTabStr implements WebDataReqFun {

    @Autowired
    private DataService dataService;

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        Map<String,Object> changedEleMap = new LinkedHashMap<>();

        WebValueDto webValueDto = publicReq.getWebValueDto();
        if(webValueDto==null || webValueDto.getWebInputValueMap()==null ||
                !webValueDto.getWebInputValueMap().containsKey("dbName") ||
                !webValueDto.getWebInputValueMap().containsKey("libName") ||
                !webValueDto.getWebInputValueMap().containsKey("tableName") ){
            return changedEleMap;
        }

        String selectedDb = webValueDto.getWebInputValueMap().get("dbName").getValue().toString();
        String selectedLib = webValueDto.getWebInputValueMap().get("libName").getValue().toString();
        String tableName = webValueDto.getWebInputValueMap().get("tableName").getValue().toString();

        if(StringUtils.isBlank(selectedDb) || StringUtils.isBlank(selectedLib) || StringUtils.isBlank(tableName)){
            return changedEleMap;
        }

        Connection connection = dataService.getDatabaseConnection(selectedDb,selectedLib);
        Map<String, FieldAttr> fields = DbUtil.getFieldAttrMap(connection,selectedDb,selectedLib,tableName);
        dataService.closeConnection(connection);

        fields.forEach((k,v)->changedEleMap.put(k,v.getRemarks()));

        return changedEleMap;
    }
}
