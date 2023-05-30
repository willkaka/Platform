package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
//@TableName("web_trigger_info
public class WebTrigger {

    private static final long serialVersionUID = 1L;

    /**
     * null
     */
    //web_trigger_info_id
    private Integer webTriggerId;

    /**
     * null
     */
    //source_menu
    private String sourceMenu;

    /**
     * null
     */
    //source_area
    private String sourcePage;

    /**
     * null
     */
    //source_element
    private String sourceElement;

    /**
     * null
     */
    //trigger_type
    private String triggerType;

    /**
     * null
     */
    //trigger_element
    private String triggerElement;

    /**
     * null
     */
    //trigger_element_type
    private String triggerElementType;

    /**
     * null
     */
    //param
    private String param;

}
