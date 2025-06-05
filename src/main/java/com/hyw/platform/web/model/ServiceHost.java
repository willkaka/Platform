package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
public class ServiceHost {

    private Integer serviceHostId;
    private String hostName;
    private String hostEnv;
    private String hostUrl;
    private String hostDesc;

}
