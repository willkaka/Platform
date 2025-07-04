package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.gdata.DataService;
import com.hyw.gdata.dto.FieldAttr;
import com.hyw.gdata.utils.DbUtil;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.funbean.abs.RequestTableDataUnit;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
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
    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;

    @Override
    public Map<String,Object> execute(PublicReq publicReq){
        Map<String,Object> changedEleMap = new LinkedHashMap<>();

        Map<String, ValueObject> valueMap = publicReq.getValueMap();
        if(valueMap.isEmpty() ||
                !valueMap.containsKey("dbName") ||
                !valueMap.containsKey("libName") ||
                !valueMap.containsKey("tableName") ){
            return changedEleMap;
        }

        String selectedDb = valueMap.get("dbName").getValue().toString();
        String selectedLib = valueMap.get("libName").getValue().toString();
        String tableName = valueMap.get("tableName").getValue().toString();

        if(StringUtils.isBlank(selectedDb) || StringUtils.isBlank(selectedLib) || StringUtils.isBlank(tableName)){
            return changedEleMap;
        }

        Connection connection = configDatabaseInfoService.getConnection(selectedDb,selectedLib);
        Map<String, FieldAttr> fields = DbUtil.getFieldAttrMap(connection,selectedDb,selectedLib,tableName);
        dataService.closeConnection(connection);

        fields.forEach((k,v)->changedEleMap.put(k,v.getRemarks()));

        return changedEleMap;
    }
}
