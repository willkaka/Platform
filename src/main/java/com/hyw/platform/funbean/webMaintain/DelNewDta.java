package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebData;
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

@Service("delNewDta")
@Slf4j
public class DelNewDta extends RequestFunUnit<String, DelNewDta.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(DelNewDta.QueryVariable variable){
        //输入检查

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getPage()),"page不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getElement()),"元素,不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, DelNewDta.QueryVariable dto){
        WebData webData = dataService.getOne(new NQueryWrapper<WebData>()
                .eq(WebData::getMenu, dto.getMenu())
                .eq(WebData::getPage, dto.getPage())
                .eq(WebData::getElement, dto.getElement())
                .eq(WebData::getDataType, dto.getDataType())
        );
        BizException.trueThrow(webData==null,"查无记录!");

        dataService.delete(webData,"webDataId");

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
        private String dataType;
        private String dataAttr;
        private String express;
    }
}
