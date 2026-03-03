package com.hyw.platform.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommonQueryConfig {
    private Integer commonQueryConfigId;
    private String queryType;
    private String queryName;
    private String queryDesc;
    private String queryCond;
    private String querySql;
}
