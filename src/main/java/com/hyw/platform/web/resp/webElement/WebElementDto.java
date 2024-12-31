package com.hyw.platform.web.resp.webElement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.web.model.WebData;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.util.ConvertEleData;
import com.hyw.platform.web.util.WebUtil;
import com.ql.util.express.ExpressRunner;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Accessors( chain = true )
public class WebElementDto {
    /**
     * 页面元素id
     */
    private String id;
    /**
     * 页面元素编号
     */
    private String elementNo;
    /**
     * 页面元素位置-菜单
     */
    private String menu;
    /**
     * 页面元素位置-页面
     */
    private String page;
    /**
     * 页面元素位置-父元素id
     */
    private String pId;
    /**
     * 页面元素序号
     */
    private Integer sortNo;
    /**
     * 页面元素类型
     * group-菜单组
     * menu-菜单
     * div-div
     * labelInput-label显示文字
     * button-页面按钮
     */
    private String type; // group/menu/div/labelInput/button/
    /**
     * 页面元素描述信息
     */
    private String desc;
    /**
     * 页面元素数据，例如下拉数据等。
     */
    private Object data;
    /**
     * 页面元素初始值
     */
    private String defValue; // 初始值
    /**
     * 页面元素显示属性
     */
    private Map<String,String> attrMap;
    /**
     * 控制参数
     */
    private Map<String,Object> param;
    /**
     * 页面元素事件定义
     */
    private List<EventInfo> eventInfoList;
    /**
     * 页面元素的子元素
     */
    private List<WebElementDto> subElementList;

    @Autowired
    private DataService dataService;

    public WebElementDto(){}

    public WebElementDto(WebElement webElement){
        this.id=webElement.getElement();
        this.elementNo= webElement.getElementNo();
        this.pId=webElement.getElementParent();
        this.menu=webElement.getMenu();
        this.page=webElement.getPage();
        this.sortNo =webElement.getSortNo();
        this.desc=webElement.getElementDesc();
        this.type=webElement.getElementType();
        this.attrMap= ConvertEleData.getAttrMap(webElement.getElementAttr(), ";", "=");
        this.param = StringUtils.isBlank(webElement.getParam())?new HashMap<>():new HashMap<>(JSON.parseObject(webElement.getParam()));
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
