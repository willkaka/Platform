package com.hyw.platform.dbservice.dao;

import lombok.Data;

@Data
public class ConfigDatabaseInfo {
    private int configDatabaseInfoId;
    private String databaseName;
    private String databaseType;
    private String databaseDriver;
    private String databaseAddr;
    private String databaseAttr;
    private String databaseLabel;
    private String loginName;
    private String loginPassword;
}