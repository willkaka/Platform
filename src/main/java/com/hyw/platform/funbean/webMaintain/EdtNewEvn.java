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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("edtNewEvn")
@Slf4j
public class EdtNewEvn extends RequestFunUnit<String, EdtNewEvn.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(EdtNewEvn.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElement()),"元素,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getEventType()),"事件类型不允许为空值!");

        BizException.trueThrow(!variable.getWebEventId().equals(variable.getDefaultWebEventId()),"web_event_id不允许修改!");
    }

    @Override
    public String execLogic(PublicReq publicReq, EdtNewEvn.QueryVariable dto){
        WebEvent webEvent = dataService.getOne(new NQueryWrapper<WebEvent>()
                .eq(WebEvent::getWebEventId, dto.getDefaultWebEventId()));
        BizException.trueThrow(webEvent==null,"查无记录!");

        BeanUtils.copyProperties(dto, webEvent);
        dataService.updateById(webEvent,"webEventId");
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        // 修改后的新值
        private String webEventId;
        private String menu;
        private String page;
        private String element;
        private String eventType;
        private String requestType;
        private String requestBean;
        private String nextPage;
        private String param;
        // 原值
        private String defaultWebEventId;
        private String defaultMenu;
        private String defaultPage;
        private String defaultElement;
        private String defaultEventType;
        private String defaultRequestType;
        private String defaultRequestBean;
        private String defaultNextPage;
        private String defaultParam;
    }
}
