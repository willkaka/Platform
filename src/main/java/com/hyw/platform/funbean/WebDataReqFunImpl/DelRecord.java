package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.TableFieldInfo;
import com.hyw.platform.dbservice.utils.QueryUtil;
import com.hyw.platform.dbservice.utils.SqlUtil;
import com.hyw.platform.exception.BizThrow;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.PublicResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("delRecord")
@Slf4j
public class DelRecord implements RequestFun {

    @Autowired
    private DataService dataService;

    @Override
    public PublicResp execute(PublicReq requestDto){
        PublicResp returnDto = new PublicResp();

        Map<String,Object> paramMap = requestDto.getEventInfo().getParamMap();
        BizThrow.isTrue(!paramMap.containsKey("tableName"),"没有tableName参数！");
        BizThrow.isTrue(paramMap.get("tableName")==null,"tableName参数为null！");
        String tableName = paramMap.get("tableName").toString();
        String refreshPage = paramMap.get("refreshPage").toString();
        String refreshEle = paramMap.get("refreshEle").toString();

        int updCount = dataService.executeSql(getDeleteSql(tableName,paramMap));
        if(updCount>0){
            returnDto.setRtnMsg("已成功删除"+updCount+"条记录！");
        }else{
            returnDto.setRtnCode("9998");
            returnDto.setRtnMsg("删除记录失败！");
        }

        //后台执行完请求后，前台下一操作:刷新菜单表格
        List<EventInfo> eventInfoList = new ArrayList<>();
        if(StringUtils.isNotBlank(refreshEle) && StringUtils.isNotBlank(refreshPage)) {
            Map<String,Object> rtnParamMap = new HashMap<>();
            for(String key:requestDto.getWebValueDto().getWebInputValueMap().keySet()){
                ValueObject valueObject = requestDto.getWebValueDto().getWebInputValueMap().get(key);
                rtnParamMap.put(key,valueObject.getValue());
            }
            eventInfoList.add(new EventInfo().setEvent("request")//refreshElement
                    .setReqType("refreshEleReq")
                    .setReqMapping("refresh")
                    .setReqMethod("post")
                    .setMenu(requestDto.getEventInfo().getMenu())
                    .setPage(refreshPage)
                    .setElement(refreshEle)
                    .setParamMap(rtnParamMap));
        }
        returnDto.setNextOprDto(new NextOprDto().setShowSw(false).setEventInfoList(eventInfoList));
        return returnDto;
    }


    /**
     * 拼接单表单记录更新sql语句
     * @param tableName 表名
     * @param fieldValue 字段及值map
     * @return sql字符串
     */
    public String getDeleteSql(String tableName, Map<String,Object> fieldValue){
        if(tableName==null) return null;
        List<String> keyList = dataService.getTablePrimaryKeys(tableName);
        List<TableFieldInfo> keyFieldList = new ArrayList<>();

        List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldList(tableName);

        StringBuilder sql = new StringBuilder().append("DELETE FROM ").append(tableName);
        for(TableFieldInfo fieldInfo:tableFieldInfoList) {
            String fieldName = fieldInfo.getFieldName();
            if(keyList.contains(fieldName)) keyFieldList.add(fieldInfo);
        }

        int index=0;
        sql.append(" WHERE ");
        for(TableFieldInfo keyFieldName:keyFieldList) {
            if(index>0) sql.append(QueryUtil.isBlankStr(sql.toString())?"":" AND ");
            sql.append(keyFieldName.getFieldName()).append("=")
                    .append(SqlUtil.getFieldValue(keyFieldName.getFieldType(), fieldValue.get(keyFieldName.getFieldName())));
            index++;
        }
        return sql.toString();
    }
}
