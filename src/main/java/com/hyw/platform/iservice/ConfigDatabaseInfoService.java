package com.hyw.platform.iservice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.gdata.constant.DbConstant;
import com.hyw.gdata.exception.DbException;
import com.hyw.gdata.utils.QueryUtil;
import com.hyw.platform.constant.Constant;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.hyw.platform.mapper.ConfigDatabaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@Service
public class ConfigDatabaseInfoService extends ServiceImpl<ConfigDatabaseInfoMapper, ConfigDatabaseInfo> implements IService<ConfigDatabaseInfo> {

    @Autowired
    private DataService dataService;

    public Connection getConnection(String dbName,String libName){
        Connection connection;
        if(DbConstant.DB_SOURCE_SYS.equals(dbName)) {
            connection = dataService.getDatabaseConnection();
        }else {
            ConfigDatabaseInfo configDatabaseInfo = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
                    .eq(ConfigDatabaseInfo::getDatabaseName, dbName));
            configDatabaseInfo.setDatabaseLabel(libName);
            return getConnection(configDatabaseInfo);
        }
        return connection;
    }

    /**
     * 取DB数据库连接
     * @param configDatabaseInfo
     * @return Connection
     */
    public Connection getConnection(ConfigDatabaseInfo configDatabaseInfo) {
        String dbType = configDatabaseInfo.getDatabaseType();
        String driver = configDatabaseInfo.getDatabaseDriver();
        String url = configDatabaseInfo.getDatabaseAddr();
        String attr = configDatabaseInfo.getDatabaseAttr();
        String lib = configDatabaseInfo.getDatabaseLabel();
        String user = configDatabaseInfo.getLoginName();
        String password = configDatabaseInfo.getLoginPassword();

        return getConnection(dbType,driver,url,attr,lib,user,password);
    }

    /**
     * 取DB数据库连接
     * @param driver 驱动
     * @param url 地址
     * @param user 用户名
     * @param password 密码
     * @return Connection
     */
    public Connection getConnection(String dbType,String driver,String url,String attr,String lib,String user,String password) {
        String dbUrl ="";
        if(dbType.equalsIgnoreCase(Constant.DB_TYPE_MYSQL)) {
            dbUrl = url
                    + (QueryUtil.isNotBlankStr(lib) ? "/" + lib : "")
                    + (QueryUtil.isNotBlankStr(attr) ? "?" + attr : "");
        }else if(dbType.equalsIgnoreCase(Constant.DB_TYPE_ORACLE)) {
            dbUrl = url + (QueryUtil.isNotBlankStr(lib) ? ":" + lib : "");
        }if(dbType.equalsIgnoreCase(Constant.DB_TYPE_SQLITE)){
            if("main".equals(lib)) {
                dbUrl = url;
            }else{
                dbUrl = url
                        + (QueryUtil.isNotBlankStr(lib) ? ":" + lib : "");
            }
        }

        try {
            Class.forName(driver);
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (Exception e) {
            log.error("数据库连接出错！driver({}),url({}),user({}),password({})",driver,dbUrl,user,password,e);
            throw new DbException("数据库连接出错！");
        }
    }
}
