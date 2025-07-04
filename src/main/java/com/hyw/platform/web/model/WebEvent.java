package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
//@TableName("web_event_info
public class WebEvent {

    /**
     * null
     */
    //web_event_info_id
    private String webEventId;

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
    private String element;

    /**
     * null
     */
    //event_type
    private String eventType;

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
    //next_page
    private String nextPage;

    /**
     * null
     */
    //param
    private String param;

}