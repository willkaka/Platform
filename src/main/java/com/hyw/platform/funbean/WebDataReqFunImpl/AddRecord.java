package com.hyw.platform.funbean.WebDataReqFunImpl;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.dto.TableFieldInfo;
import com.hyw.platform.dbservice.utils.SqlUtil;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("addRecord")
@Slf4j
public class AddRecord implements RequestFun {

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
        Map<String,String> inputValue = requestDto.getWebValueDto().getValue();

        List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldList(tableName);
        String insertSql = SqlUtil.getInsertSql(tableName,tableFieldInfoList,inputValue);
        int count = dataService.executeSql(insertSql);

        if(count>0){
            returnDto.setRtnMsg("已成功写入"+count+"记录！");
        }else{
            returnDto.setRtnCode("9998");
            returnDto.setRtnMsg("写记录失败！");
        }

        //后台执行完请求后，前台下一操作:刷新菜单表格
        List<EventInfo> eventInfoList = new ArrayList<>();
        if(StringUtils.isNotBlank(closeSW)) {
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
        if(StringUtils.isNotBlank(closeSW)) {
            eventInfoList.add(new EventInfo()
                    .setEvent("closeSw")  //refreshElement
                    .setElement(closeSW));
        }

        returnDto.setNextOprDto(new NextOprDto().setShowSw(false).setEventInfoList(eventInfoList));
        return returnDto;
    }
}
