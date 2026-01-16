package com.hyw.platform.web.resp.webElement;

import com.alibaba.fastjson.JSON;
import com.hyw.gdata.DataService;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.util.ConvertEleData;
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
     * 元素名称
     */
    private String elementName;
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
        this.id=webElement.getWebElementId();
        this.elementNo= webElement.getElementNo();
        this.elementName=webElement.getElement();
        this.pId=webElement.getElementParent();
        this.menu=webElement.getMenu();
        this.page=webElement.getPage();
        this.sortNo =webElement.getSortNo();
        this.desc=webElement.getElementDesc();
        this.type=webElement.getElementType();
        this.attrMap= ConvertEleData.getAttrMap(webElement.getElementAttr(), ";", "=");
        this.param = StringUtils.isBlank(webElement.getParam())?new HashMap<>():new HashMap<>(JSON.parseObject(webElement.getParam()));
    }
}
