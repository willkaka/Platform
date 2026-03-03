package com.hyw.platform.web.service;

import com.alibaba.fastjson.JSON;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.web.model.WebEvent;
import com.hyw.platform.web.model.WebMenu;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.syswebconfig.UUIDShort;
import com.hyw.platform.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WebMenuService {

    @Autowired
    private DataService dataService;


    public List<WebElementDto> getMenu(String parentMenu, Map<String,String> menuIdMap) {
        List<WebElementDto> webElementDtoDtoList = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<WebMenu> webMenuList = dataService.list(new NQueryWrapper<WebMenu>()
                .eq(WebMenu::getMenuParent, parentMenu)
                .orderByAsc(WebMenu::getSortNo));
        int sortNo = 0;
        for (WebMenu webMenu : webMenuList) {
            WebElementDto webElementDto = new WebElementDto();
            webElementDto.setPId("root".equals(webMenu.getMenuParent()) ? "menuArea" : menuIdMap.getOrDefault(webMenu.getMenuParent(),webMenu.getMenuParent()));
            webElementDto.setId(UUIDShort.generate());
            webElementDto.setElementName(webMenu.getMenu());
            webElementDto.setType("root".equals(webMenu.getMenuParent()) ? "Group" : "Menu");
            webElementDto.setDesc(webMenu.getMenuDesc());
            webElementDto.setEventInfoList(getEventInfoList(webMenu.getMenu(),"menuEvent"));

            menuIdMap.put(webMenu.getMenu(),webElementDto.getId());
            webElementDto.setSubElementList(getMenu(webMenu.getMenu(), menuIdMap));
            if(CollectionUtils.isNotEmpty(webElementDto.getSubElementList())){
                webElementDto.setType("Group");
                webElementDto.setSortNo(++sortNo);
            }
            webElementDtoDtoList.add(webElementDto);
        }
        return webElementDtoDtoList;
    }

    /**
     * 取事件信息
     *
     * @param menu 菜单
     * @return List<EventInfo>
     */
    private List<EventInfo> getEventInfoList(String menu,String type) {
        List<EventInfo> eventInfoList = new ArrayList<>();

        //取配置的事件
        List<WebEvent> webEventInfoList = dataService.list(new NQueryWrapper<WebEvent>()
                .eq(WebEvent::getMenu, menu)
                .eq(WebEvent::getPage, StringUtils.isBlank(type)?"start_page":type));
        for (WebEvent webEventInfo : webEventInfoList) {
            eventInfoList.add(createEventInfo(webEventInfo));
        }
        return eventInfoList;
    }


    /**
     * 生成事件信息（包含该事件需要触发的事件）
     *
     * @param webEventInfo   事件信息
     * @return EventInfo
     */
    private EventInfo createEventInfo(WebEvent webEventInfo) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEvent(webEventInfo.getEventType());
        eventInfo.setReqType(webEventInfo.getRequestType());
        eventInfo.setReqMapping(webEventInfo.getRequestBean());
        eventInfo.setMenu(webEventInfo.getMenu());
        eventInfo.setPage(webEventInfo.getPage());
        eventInfo.setElement(webEventInfo.getElement());
        //事件参数
        Map<String, Object> eventParam = JSON.parseObject(webEventInfo.getParam());//json转map
        eventInfo.setParamMap(eventParam);
        if (WebUtil.isNotEmpty(eventParam)) {
            if (eventParam.containsKey("withPage")) {
                boolean isWithPage = (boolean) eventParam.get("withPage");
                eventInfo.setWithPage(isWithPage);
            }
        }
        return eventInfo;
    }
}
