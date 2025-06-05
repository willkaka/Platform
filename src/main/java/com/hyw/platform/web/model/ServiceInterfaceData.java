package com.hyw.platform.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *null
 */
@Data
@Accessors(chain = true)
public class ServiceInterfaceData {

    private Integer serviceInterfaceDataId;
    private String interfaceName;
    private String dataType;
    private Integer fieldSeq;
    private String fieldName;
    private String fieldSource;
    private String fieldType;
    private String fieldDesc;

}
