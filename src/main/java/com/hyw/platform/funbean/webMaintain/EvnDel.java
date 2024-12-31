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

@Service("evnDel")
@Slf4j
public class EvnDel extends RequestFunUnit<String, EvnDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(EvnDel.QueryVariable variable){
        //输入检查

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElement()),"元素,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getEventType()),"事件类型不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, EvnDel.QueryVariable dto){
        WebEvent webEvent = dataService.getOne(new NQueryWrapper<WebEvent>()
                .eq(WebEvent::getMenu, dto.getMenu())
                .eq(WebEvent::getPage, dto.getPage())
                .eq(WebEvent::getElement, dto.getElement())
                .eq(WebEvent::getEventType, dto.getEventType())
                .eq(WebEvent::getRequestType, dto.getRequestType())
                .eq(WebEvent::getRequestBean, dto.getRequestBean())
        );
        BizException.trueThrow(webEvent==null,"查无记录!");

        dataService.delete(webEvent,"webEventId");

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String menu;
        private String page;
        private String element;
        private String eventType;
        private String requestType;
        private String requestBean;
        private String nextPage;
        private String param;
    }
}
