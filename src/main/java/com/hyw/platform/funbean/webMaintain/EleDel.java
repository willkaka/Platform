package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eleDel")
@Slf4j
public class EleDel extends RequestFunUnit<String, EleDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(EleDel.QueryVariable variable){
        //输入检查

        BizException.trueThrow(StringUtils.isBlank(variable.getWebElementId()),"id,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElement()),"元素,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElementParent()),"父级元素不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, EleDel.QueryVariable dto){
        WebElement webElement = dataService.getOne(new NQueryWrapper<WebElement>()
                .eq(WebElement::getWebElementId, dto.getWebElementId())
                .eq(WebElement::getMenu, dto.getMenu())
                .eq(WebElement::getPage, dto.getPage())
                .eq(WebElement::getElementParent, dto.getElementParent())
                .eq(WebElement::getSortNo, dto.getSortNo())
                .eq(WebElement::getElement, dto.getElement())
        );
        BizException.trueThrow(webElement==null,"查无记录!");

        dataService.delete(webElement,"webElementId");

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String webElementId;
        private String elementNo;
        private Integer sortNo;
        private String menu;
        private String page;
        private String elementParent;
        private String element;
        private String elementType;
        private String elementDesc;
        private String elementAttr;
    }
}
