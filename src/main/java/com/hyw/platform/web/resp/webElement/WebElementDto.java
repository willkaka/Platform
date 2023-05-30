package com.hyw.platform.web.resp.webElement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.web.model.WebData;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.model.WebEvent;
import com.hyw.platform.web.model.WebTrigger;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.util.ConvertEleData;
import com.hyw.platform.web.util.WebUtil;
import com.ql.util.express.ExpressRunner;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Accessors( chain = true )
public class WebElementDto {
    private String id; //元素id
    private String menu;
    private String page;
    private String pId; //父标签id
    private Integer seq;
    private String type; // group/menu/div/labelInput/button/
    private String desc;
    private Object data;

    private String defValue; // 初始值
    private Map<String,String> attrMap;
    private List<EventInfo> eventInfoList;
    private List<WebElementDto> subElementList; //子元素

    @Autowired
    private DataService dataService;

    public WebElementDto(){}

    public WebElementDto(WebElement webElement){
        this.id=webElement.getElement();
        this.pId=webElement.getElementParent();
        this.menu=webElement.getMenu();
        this.page=webElement.getPage();
        this.seq=webElement.getElementSeq();
        this.desc=webElement.getElementDesc();
        this.type=webElement.getElementType();
        this.attrMap= ConvertEleData.getAttrMap(webElement.getElementAttr(), ";", "=");
    }

    /**
     * 取默认值
     * @param menu 菜单
     * @param element 元素
     * @return 默认值
     */
    private String getDefaultValue(String menu,String page,String element){
        //取元素配置值
        WebData webData = dataService.getOne(new NQueryWrapper<WebData>()
                .eq(WebData::getMenu,menu)
                .eq(WebData::getPage,page)
                .eq(WebData::getElement,element)
                .eq(WebData::getDataType,"defaultValue"));
        if(webData==null || WebUtil.isBlank(webData.getExpress())) return null;
        if("constant".equalsIgnoreCase(webData.getDataAttr())) {
            return webData.getExpress();
        }else if("QLExpress".equalsIgnoreCase(webData.getDataAttr())) {
            Object result;
            try {
                ExpressRunner runner = new ExpressRunner(true, false);
                result = runner.execute(webData.getExpress(), null, null, true, false);
            } catch (Exception e) {
                log.error("计算表达式(" + webData.getExpress() + ")出错!", e);
                throw new BizException("计算表达式(" + webData.getExpress() + ")出错!");
            }
            return result == null ? null : result.toString();
        }
        return null;
    }
}
