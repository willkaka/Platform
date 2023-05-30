package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.FieldAttr;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.dbservice.utils.SqlUtil;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.WebDataReqFun;
import com.hyw.platform.funbean.WebTableDataReqFun;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.funbean.abs.RequestTableDataUnit;
import com.hyw.platform.funbean.utils.WebTableDataUtils;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.webElement.TableNormal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Service("queryTableData")
public class QueryTableData extends RequestTableDataUnit<QueryTableData.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(QueryTableData.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDbName()),"DB不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getLibName()),"数据库,不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getTableName()),"表名,不允许为空值!");

    }

    /**
     * 执行自定义逻辑
     * @param publicReq 请求dto
     * @return TableNormal
     */
    @Override
    public TableNormal execLogic(PublicReq publicReq,QueryTableData.QueryVariable variable){
        //取数逻辑
        int pageNow = publicReq.getEventInfo().getReqPage()==0?1:publicReq.getEventInfo().getReqPage();         //当前的页码
        int pageSize = 10;
        int totalCount;      //表中记录的总行数

        String sql = "SELECT ";
        if(CollectionUtils.isNotEmpty(variable.getShowFields())){
            for(String field:variable.getShowFields()) {
                sql = sql.length() >7?sql +","+ field:sql + field;
            }
        }else{
            sql = sql + "*";
        }
        sql = sql + " FROM " + variable.tableName;

        //连接数据库，查询数据，关闭数据库
        Connection connection = dataService.getDatabaseConnection(variable.getDbName(),variable.getLibName());
        String whereCondition = null;
        Map<String, FieldAttr> fieldAttrMap = DbUtil.getFieldAttrMap(connection,variable.getDbName(),variable.getLibName(),variable.getTableName());
        if(StringUtils.isNotBlank(variable.getSelectField()) &&
                StringUtils.isNotBlank(variable.getOperationType()) &&
                StringUtils.isNotBlank(variable.getFieldValue())){
            FieldAttr fieldAttr = fieldAttrMap.get(variable.getSelectField());
            if(null != fieldAttr){
                whereCondition = variable.getSelectField() + " " + variable.getOperationType() + " " +
                        SqlUtil.convertToExpression(variable.getFieldValue(),fieldAttr.getDataType());
            }
        }
        if(StringUtils.isNotBlank(whereCondition)){
            sql = sql + " WHERE " + whereCondition;
        }

        totalCount = DbUtil.getSqlRecordCount(connection,sql);
        if(totalCount >500){
            sql = sql + " LIMIT " + (pageNow==0?1:pageNow-1)*pageSize + "," + pageSize;
            variable.setWithPage(true);//表格内容分页显示
        }else{
            variable.setWithPage(false);//表格内容分页显示
        }

        List<Map<String,FieldAttr>> records = DbUtil.getSqlRecordsWithFieldAttr(connection,sql);
        DbUtil.closeConnection(connection);

        //参数配置
        TableNormal tableNormal = new TableNormal();
        tableNormal.setRecordList(WebTableDataUtils.convertToMapList(records));
        tableNormal.setHeadMap(WebTableDataUtils.getHeadMap(records));

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
        private String selectField;
        private String operationType;
        private String fieldValue;
        private List<String> showFields;
    }
}
