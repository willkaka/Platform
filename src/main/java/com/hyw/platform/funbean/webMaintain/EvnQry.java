package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebEvent;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("evnQry")
@Slf4j
public class EvnQry extends RequestFunUnit<Map<String,Object>, EvnQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(EvnQry.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, EvnQry.QueryVariable dto){
        List<WebEvent> list = dataService.list(new NQueryWrapper<WebEvent>()
                        .setTable(WebEvent.class)
                       .eq(StringUtils.isNotBlank(dto.getMenu()), WebEvent::getMenu, dto.getMenu())
                       .eq(StringUtils.isNotBlank(dto.getQueryConditionPage()), WebEvent::getPage, dto.getQueryConditionPage())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionElement()), WebEvent::getElement, dto.getQueryConditionElement())
                .orderByAsc(WebEvent::getWebEventId)
        );
        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("data",list);
        return rtnMap;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String menu;
        private String queryConditionPage;
        private String queryConditionElement;
    }
}
