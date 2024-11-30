package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addNewEle")
@Slf4j
public class AddNewEle extends RequestFunUnit<String, AddNewEle.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(AddNewEle.QueryVariable variable){
        //输入检查

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElement()),"元素,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElementParent()),"父级元素不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, AddNewEle.QueryVariable dto){
        WebElement webElement = new WebElement();
        BeanUtils.copyProperties(dto, webElement);
        dataService.save(webElement);

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
        private String elementParent;
        private Integer elementSeq;
        private String element;
        private String elementType;
        private String elementDesc;
        private String elementAttr;
    }
}
