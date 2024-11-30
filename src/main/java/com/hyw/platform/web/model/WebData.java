package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
public class WebData {

    /**
     * null
     */
    //web_element_data_id
    private Integer webDataId;

    /**
     * null
     */
    //menu
    private String menu;

    /**
     * null
     */
    //page
    private String page;

    /**
     * null
     */
    //element
    private String element;

    /**
     * null
     */
    //data_type
    private String dataType;

    /**
     * null
     */
    //data_attr
    private String dataAttr;

    /**
     * null
     */
    //express
    private String express;

}