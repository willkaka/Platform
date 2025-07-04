package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
//@TableName("web_element_info
public class WebElement {

    /**
     * null
     */
    //web_element_info_id
    private String webElementId;

    /**
     * null
     */
    //menu
    private String elementNo;

    /**
     * null
     */
    //menu
    private String menu;

    /**
     * null
     */
    //menu
    private String page;

    /**
     * null
     */
    //element_seq
    private String elementParent;

    /**
     * null
     */
    //element_seq
    private Integer sortNo;

    /**
     * null
     */
    //element
    private String element;

    /**
     * null
     */
    //element_type
    private String elementType;

    /**
     * null
     */
    //element_desc
    private String elementDesc;

    /**
     * null
     */
    //element_attr
    private String elementAttr;

    private String param;

}
