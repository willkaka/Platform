package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
//@TableName("web_menu_info
public class WebMenu {

    private Integer webMenuId;
    private String menuNo;
    private Integer sortNo;
    private String menu;
    private String menuParent;
    private String menuDesc;

}
