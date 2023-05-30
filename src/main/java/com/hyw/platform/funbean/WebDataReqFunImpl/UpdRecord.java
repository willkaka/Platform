package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.TableFieldInfo;
import com.hyw.platform.dbservice.utils.QueryUtil;
import com.hyw.platform.dbservice.utils.SqlUtil;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.req.WebValueDto;
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

@Service("updRecord")
@Slf4j
public class UpdRecord implements RequestFun {

    @Autowired
    private DataService dataService;

    @Override
    public PublicResp execute(PublicReq requestDto){
        PublicResp returnDto = new PublicResp();

        Map<String,Object> paramMap = requestDto.getEventInfo().getParamMap();
        String tableName = paramMap.get("tableName").toString();
        String refreshPage = paramMap.get("refreshPage").toString();
        String refreshEle = paramMap.get("refreshEle").toString();
        String closeSW = paramMap.get("closeSW").toString();

        Map<String, ValueObject> inputValue = requestDto.getWebValueDto().getWebInputValueMap();
        int updCount = dataService.executeSql(getUpdateSql(tableName,inputValue));
        if(updCount>0){
            returnDto.setRtnMsg("已成功更新"+updCount+"条记录！");
        }else{
            returnDto.setRtnCode("9998");
            returnDto.setRtnMsg("更新记录失败！");
        }
        //后台执行完请求后，前台下一操作:刷新菜单表格
        List<EventInfo> eventInfoList = new ArrayList<>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(closeSW)) {
            eventInfoList.add(new EventInfo()
                    .setEvent("confirmSw")  //refreshElement
                    .setElement(closeSW));
        }
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
        if(org.apache.commons.lang3.StringUtils.isNotBlank(closeSW)) {
            eventInfoList.add(new EventInfo()
                    .setEvent("closeSw")  //refreshElement
                    .setElement(closeSW));
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
    public String getUpdateSql(String tableName,Map<String, ValueObject> fieldValue){
        if(tableName==null) return null;
        List<String> keyList = dataService.getTablePrimaryKeys(tableName);
        List<TableFieldInfo> keyFieldList = new ArrayList<>();

        List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldList(tableName);

        StringBuilder sql = new StringBuilder().append("UPDATE ").append(tableName).append(" SET ");
        int index=0;
        Field keyField = null;
        for(TableFieldInfo fieldInfo:tableFieldInfoList) {
            String fieldName = fieldInfo.getFieldName();
            if(keyList.contains(fieldName)) keyFieldList.add(fieldInfo);
            String fieldType = fieldInfo.getFieldType();
            ValueObject valueObject = fieldValue.get(fieldName);
            if(StringUtils.isBlank(valueObject.getValue().toString()) && StringUtils.isBlank(valueObject.getDefValue().toString()) ||
                    valueObject.getValue().equals(valueObject.getDefValue())) continue;
            if(index>0) sql.append(QueryUtil.isBlankStr(sql.toString())?"":", ");
            //拼接字段值
            sql.append(fieldName).append("=").append(SqlUtil.getFieldValue(fieldType, valueObject.getValue()));
            index++;
        }

        index=0;
        sql.append(" WHERE ");
        for(TableFieldInfo keyFieldName:keyFieldList) {
            if(index>0) sql.append(QueryUtil.isBlankStr(sql.toString())?"":" AND ");
            ValueObject valueObject = fieldValue.get(keyFieldName.getFieldName());
            sql.append(keyFieldName.getFieldName()).append("=")
                    .append(SqlUtil.getFieldValue(keyFieldName.getFieldType(), valueObject.getDefValue()));
            index++;
        }
        return sql.toString();
    }
}
