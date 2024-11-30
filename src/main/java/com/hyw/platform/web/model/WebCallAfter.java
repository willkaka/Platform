package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
//@TableName("web_call_after")
public class WebCallAfter {

    /**
     * null
     */
    //web_call_after_id
    private Integer webCallAfterId;

    /**
     * null
     */
    //menu
    private String menu;

    /**
     * null
     */
    //area
    private String page;

    /**
     * null
     */
    //element
    private String processBean;

    /**
     * null
     */
    //event_type
    private String processStatus;

    /**
     * null
     */
    //request_type
    private String oprType;

    /**
     * null
     */
    //request_type
    private String requestType;

    /**
     * null
     */
    //request_no
    private String requestBean;

    /**
     * null
     */
    //param
    private String param;

}