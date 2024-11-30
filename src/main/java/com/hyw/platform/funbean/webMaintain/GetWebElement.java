package com.hyw.platform.funbean.webMaintain;

import com.alibaba.fastjson.JSON;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebMenu;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.service.WebElementService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("getWebElement")
@Slf4j
public class GetWebElement extends RequestFunUnit<PublicResp, GetWebElement.QueryVariable> {

    @Autowired
    private WebElementService webElementService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(GetWebElement.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuParent()),"父级菜单不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuSeq()),"菜单序号,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuDesc()),"菜单名称,不允许为空值!");

        BizException.trueThrow(!variable.getWebMenuId().equals(variable.getDefaultWebMenuId()),"web_menu_id不允许修改!");
    }

    @Override
    public PublicResp execLogic(PublicReq publicReq, GetWebElement.QueryVariable dto){
        PublicResp publicResp = new PublicResp();
        //取输入区域元素清单，固定从body开始
//        List<WebElementDto> inputList = webElementService.getPageElementsByParentEle(eventInfo.getMenu(), "start_page",null,publicReq);
//        publicResp.setWebElementDtoList(inputList);

        publicResp.setRtnCode("0000");
        publicResp.setRtnMsg("success");

        log.info("返回报文内容{}", JSON.toJSONString(publicResp));
        return publicResp;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        // 修改后的新值
        private String webMenuId;
        private String menuParent;
        private String menuSeq;
        private String menu;
        private String menuDesc;
        // 原值
        private String defaultWebMenuId;
        private String defaultMenuParent;
        private String defaultMenuSeq;
        private String defaultMenu;
        private String defaultMenuDesc;
    }
}
