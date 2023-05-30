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

    private static final long serialVersionUID = 1L;

    /**
     * null
     */
    //web_element_info_id
    private Integer webElementId;

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
    private Integer elementSeq;

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

}
