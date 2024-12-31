package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.model.WebMenu;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("elementMoveDown")
@Slf4j
public class ElementMoveDown extends RequestFunUnit<String, ElementMoveDown.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(ElementMoveDown.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getWebElementId()),"元素主键id不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单 不允许为空值!");

    }

    @Override
    public String execLogic(PublicReq publicReq, ElementMoveDown.QueryVariable dto){

        WebElement webElement = dataService.getOne(new NQueryWrapper<WebElement>()
                .eq(WebElement::getWebElementId, dto.getWebElementId()));
        Assert.isTrue(webElement!=null, "查无记录!");

        // 查询下一排序号记录
        WebElement webElementNext = dataService.getOne(new NQueryWrapper<WebElement>()
                .eq(WebElement::getMenu, dto.getMenu())
                .gt(WebElement::getSortNo, webElement.getSortNo())
                .orderByAsc(WebElement::getSortNo));
        Assert.isTrue(webElementNext!=null, "已无更小的排序号的记录!");

        webElement.setSortNo(webElementNext.getSortNo());
        webElementNext.setSortNo(webElementNext.getSortNo()-1);

        dataService.updateById(webElementNext);
        dataService.updateById(webElement);

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
        private String menu;
    }
}
