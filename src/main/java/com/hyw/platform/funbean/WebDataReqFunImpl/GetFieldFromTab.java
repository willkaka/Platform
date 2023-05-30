package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.FieldAttr;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.funbean.abs.RequestTableDataUnit;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.webElement.TableNormal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

@Service("getFieldFromTab")
@Slf4j
public class GetFieldFromTab extends RequestTableDataUnit<GetFieldFromTab.QueryVariable>{

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(GetFieldFromTab.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDbName()),"DB不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getLibName()),"数据库,不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getTableName()),"表名,不允许为空值!");

    }

    @Override
    public TableNormal execLogic(PublicReq publicReq,GetFieldFromTab.QueryVariable variable){
        TableNormal tableNormal = new TableNormal();

        Connection connection = dataService.getDatabaseConnection(variable.getDbName(),variable.getLibName());
        Map<String, FieldAttr> fields = DbUtil.getFieldAttrMap(connection,variable.getDbName(),variable.getLibName(),variable.getTableName());
        dataService.closeConnection(connection);

        tableNormal = convertData(fields);

        return tableNormal;
    }

    private TableNormal convertData(Map<String,FieldAttr> dataMapObject){
        TableNormal tableNormal = new TableNormal();
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        for(String key:dataMapObject.keySet()){
            FieldAttr fieldAttr = dataMapObject.get(key);
            Map<String,Object> fieldInfoMap = new LinkedHashMap<>();
            fieldInfoMap.put("fieldName",fieldAttr.getColumnName());

            String fieldType = fieldAttr.getTypeName();
            if("DECIMAL".equals(fieldAttr.getTypeName())){
                fieldType = fieldType + "("+ fieldAttr.getColumnSize() + ","+ fieldAttr.getDecimalDigits() + ")";
            }else if("DATE".equals(fieldAttr.getTypeName()) || "DATETIME".equals(fieldAttr.getTypeName())){

            }else if("VARCHAR".equals(fieldAttr.getTypeName()) || "CHAR".equals(fieldAttr.getTypeName())) {
                fieldType = fieldType + "("+ fieldAttr.getColumnSize() + ")";
            }else{
                fieldType = fieldType + "("+ fieldAttr.getColumnSize() + ")";
            }
            fieldInfoMap.put("type",fieldType);

            fieldInfoMap.put("comment",fieldAttr.getRemarks());
            dataMaps.add(fieldInfoMap);
        }
        tableNormal.getRecordList().addAll(dataMaps);  //List<Map<String, Object>>

        //表头
        Map<String,String> headFieldMap = new LinkedHashMap<>();
        headFieldMap.put("fieldName","fieldName");
        headFieldMap.put("type","type");
        headFieldMap.put("comment","comment");
        tableNormal.getHeadMap().putAll(headFieldMap);
        return tableNormal;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String dbName;
        private String libName;
        private String tableName;
    }
}
