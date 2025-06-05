package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
public class ServiceInterface {

    private Integer serviceInterfaceId;
    private String hostName;
    private String interfaceName;
    private String interfacePath;
    private String requestMethod;
    private String interfaceDesc;
    private String charset;

}
