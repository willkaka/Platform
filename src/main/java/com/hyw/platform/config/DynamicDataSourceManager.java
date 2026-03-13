package com.hyw.platform.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DynamicDataSourceManager {

    @Autowired
    @Lazy
    private ConfigDatabaseInfoService configDatabaseInfoService;

    @Getter
    private final Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    private DataSource defaultDataSource;

    public void setDefaultDataSource(DataSource defaultDataSource) {
        Assert.notNull(defaultDataSource, "Default DataSource must not be null");
        this.defaultDataSource = defaultDataSource;
        dataSourceMap.put("default", defaultDataSource);
    }

    public void addDataSource(String dsKey){
        // 获取对应数据库配置信息
        ConfigDatabaseInfo cdi = configDatabaseInfoService.getOne(new QueryWrapper<ConfigDatabaseInfo>().lambda()
                .eq(ConfigDatabaseInfo::getDatabaseName, dsKey));
        // 手动添加数据源
        addDataSource(cdi.getDatabaseName(),cdi.getDatabaseAddr(),cdi.getLoginName(),
                cdi.getLoginPassword(),cdi.getDatabaseDriver());
    }

    /**
     * 动态添加数据源
     *
     * @param dsKey            数据源唯一标识（如 "tenant_123"）
     * @param url              JDBC URL
     * @param username         用户名
     * @param password         密码
     * @param driverClassName  驱动类名
     */
    public synchronized void addDataSource(
            String dsKey,
            String url,
            String username,
            String password,
            String driverClassName) {

        if (dataSourceMap.containsKey(dsKey)) {
            // 如果已存在，可选择复用或覆盖（这里直接返回复用）
            return;
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        HikariDataSource dataSource = new HikariDataSource(config);
        dataSourceMap.put(dsKey, dataSource);

        // 更新所有 DynamicRoutingDataSource 实例的目标数据源
        for (Object ds : dataSourceMap.values()) {
            if (ds instanceof DynamicRoutingDataSource) {
                ((DynamicRoutingDataSource) ds).setTargetDataSources(dataSourceMap);
                ((DynamicRoutingDataSource) ds).afterPropertiesSet();
            }
        }
    }

    /**
     * 切换到指定数据源（供业务调用）
     */
    public void use(String dsKey) {
        if(!dataSourceMap.containsKey(dsKey)){
            addDataSource(dsKey);
        }
        DataSourceContextHolder.setDataSource(dsKey);
        log.info("切换到数据源: {}", dsKey);
    }

    /**
     * 切回默认数据源
     */
    public void useDefault() {
        DataSourceContextHolder.setDataSource("default");
    }

    /**
     * 清理当前线程数据源上下文
     */
    public void clear() {
        DataSourceContextHolder.clear();
        useDefault();
        log.info("切换回默认数据源");
    }

    /**
     * 关闭并移除指定动态数据源（防止连接池泄漏）
     */
    public synchronized void removeDataSource(String dsKey) {
        if ("default".equals(dsKey) || "dynamic_routing".equals(dsKey)) {
            return; // 保护内置 key
        }
        Object ds = dataSourceMap.remove(dsKey);
        if (ds instanceof HikariDataSource) {
            ((HikariDataSource) ds).close();
        }
    }
}