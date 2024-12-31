package com.hyw.platform.web.controller;

import com.alibaba.fastjson.JSON;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.model.WebCallAfter;
import com.hyw.platform.web.model.WebEvent;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.service.WebElementService;
import com.hyw.platform.web.service.WebMenuService;
import com.hyw.platform.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BaseInfoController {

    @Autowired
    private WebMenuService webMenuService;
    @Autowired
    private WebElementService webElementService;

    @Autowired
    ApplicationContext context;

    /**
     * 初始页面
     *
     * @param model model
     * @return index.html
     */
    @RequestMapping(value = {"/", "", "index"})
    public String startRequest(Model model) {
        model.addAttribute("webSiteName", Constant.WEB_SITE_TITLE);
        return "index";
    }

    /**
     * 页面初始化请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "initPageInfo")
    @ResponseBody
    public PublicResp initPageInfo(@RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp().setRtnCode("0000").setRtnMsg("success");

        //取菜单清单
        List<WebElementDto> menuList = webMenuService.getMenu("root");
        publicResp.setWebElementDtoList(menuList);
        return publicResp;
    }

    /**
     * 菜单请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/menuReq/{eventId}")
    @ResponseBody
    public PublicResp menuReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), "start_page",null,publicReq);
        publicResp.setWebElementDtoList(inputList);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }

    /**
     * 按钮请求(只改变输出区域数据)
     *
     * @param requestDto 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/buttonReq/{eventId}")
    @ResponseBody
    public PublicResp buttonReq(@PathVariable String eventId, @RequestBody PublicReq requestDto) {
        PublicResp publicResp = new PublicResp();

        if (StringUtils.isBlank(eventId)) {
            publicResp.setRtnCode("9997");
            publicResp.setRtnMsg("未配置该按钮请求(" + eventId + ")的处理方法！");
            return publicResp;
        }

        log.info("开始执行{}", eventId);
        publicResp = ((RequestFun) context.getBean(eventId)).execute(requestDto);

        EventInfo eventInfo = requestDto.getEventInfo();
        if(eventInfo!=null) {
            NextOprDto nextOprDto = webElementService.getCallAfterOpr(eventInfo.getMenu(), eventInfo.getPage(), eventInfo.getElement());
            for (EventInfo eventInfo1 : nextOprDto.getEventInfoList()) {
                if(MapUtils.isEmpty(eventInfo1.getParamMap())){
                    eventInfo1.setParamMap(new HashMap<>());
                }
                if(MapUtils.isNotEmpty(eventInfo.getParamMap())) {
                    eventInfo1.getParamMap().putAll(eventInfo.getParamMap());
                }
            }
            publicResp.setNextOprDto(nextOprDto);
        }
        return publicResp;
    }


    /**
     * 菜单请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/refreshEleReq/refresh")
    @ResponseBody
    public PublicResp refreshEleReq(@RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElements(eventInfo.getMenu(), eventInfo.getPage(),eventInfo.getElement(),publicReq);
        publicResp.setWebElementDtoList(inputList);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }


    /**
     * 菜单请求
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/refreshEleReq/{eventId}")
    @ResponseBody
    public PublicResp refreshEleReqTrigger(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElements(eventInfo.getMenu(), eventInfo.getPage(),eventId,publicReq);
        publicResp.setWebElementDtoList(inputList);

        Map<String,Object> paramMap = eventInfo.getParamMap();
        publicResp.setNextOprDto(getNextOpr(paramMap));
        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }

    /**
     * 页面显示数据请求（例：点选下拉选择触发请求改变另一个下拉选择内容）
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/webDataReq/{eventId}")
    @ResponseBody
    public PublicResp webDataReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }
        Map<String,Object> param = eventInfo.getParamMap();
        String page = eventInfo.getNextPage();
        String refreshFlag = (String) param.getOrDefault("refreshFlag","N");
        if("Y".equalsIgnoreCase(refreshFlag)){
            String refreshPage = (String) param.get("refreshPage");
            List<WebElementDto> inputList = webElementService.getPageElementsById(refreshPage, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }else if(StringUtils.isNotBlank(page)){
            List<WebElementDto> inputList = webElementService.getPageElementsById(page, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }else {
            String parentEle = (String) param.getOrDefault("parentEle", null);
            //取输入区域元素清单，固定从body开始
            List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), page, parentEle, publicReq);
            publicResp.setWebElementDtoList(inputList);
        }
        NextOprDto nextOprDto = webElementService.getCallAfterOpr(eventInfo.getMenu(), eventInfo.getPage(), eventInfo.getElement());
        for(EventInfo eventInfo1:nextOprDto.getEventInfoList()){
            eventInfo1.getParamMap().putAll(param);
        }
        publicResp.setNextOprDto(nextOprDto);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }


    /**
     * 页面显示数据请求（例：点选下拉选择触发请求改变另一个下拉选择内容）
     *
     * @param publicReq 前台传入参数
     * @return ReturnDto 后台返回参数
     */
    @RequestMapping(value = "/swDataReq/{eventId}")
    @ResponseBody
    public PublicResp swDataReq(@PathVariable String eventId, @RequestBody PublicReq publicReq) {
        PublicResp publicResp = new PublicResp();

        EventInfo eventInfo = publicReq.getEventInfo();
        if(null == eventInfo) {
            throw new BizException("菜单事件信息不允许为空!");
        }
        Map<String,Object> param = eventInfo.getParamMap();
        String nextPage = eventInfo.getNextPage();

        //取输入区域元素清单，固定从body开始
        List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), nextPage,null,publicReq);
        if(CollectionUtils.isNotEmpty(inputList) && param!=null && !param.isEmpty()) {
            inputList.forEach(in -> param.forEach((k, v) -> {
                if (k.equals(in.getId())) in.setDefValue(v.toString());
            }));
        }
        publicResp.setWebElementDtoList(inputList);
        NextOprDto nextOprDto = new NextOprDto();
        nextOprDto.setShowSw(true);
        publicResp.setNextOprDto(nextOprDto);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");
        return publicResp;
    }

    private NextOprDto getNextOpr(Map<String,Object> param){
        if(param==null) return null;
        String str = JSON.toJSONString(param.get("nextOprDto"));
        if(StringUtils.isBlank(str)) return null;
        return JSON.parseObject(str,NextOprDto.class);
    }
}