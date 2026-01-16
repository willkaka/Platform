package com.hyw.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // ✅ 直接注入 Spring Boot 自动配置好的 DataSourceProperties
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    @Primary
    public DataSource primaryDataSource(DynamicDataSourceManager dynamicDataSourceManager) {
        // 使用自动配置的 properties 创建默认数据源
        DataSource defaultDataSource = dataSourceProperties.initializeDataSourceBuilder().build();

        dynamicDataSourceManager.setDefaultDataSource(defaultDataSource);

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setTargetDataSources(dynamicDataSourceManager.getDataSourceMap());
        dynamicRoutingDataSource.setDefaultTargetDataSource(defaultDataSource);

        // 把路由数据源也放进管理器（用于后续更新）
        dynamicDataSourceManager.getDataSourceMap().put("dynamic_routing", dynamicRoutingDataSource);

        return dynamicRoutingDataSource;
    }
}