package com.hyw.platform.funbean.cdi;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("configDatabaseInfoUpd")
@Slf4j
public class ConfigDatabaseInfoUpd extends RequestFunUnit<String, ConfigDatabaseInfoUpd.QueryVariable> {

    @Autowired
    private DataService dataService;
    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(ConfigDatabaseInfoUpd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseName()),"数据库名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseType()),"数据库类型不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseDriver()),"数据库驱动不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseAddr()),"数据库地址不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getLoginName()),"登录名不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getLoginPassword()),"登录密码不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, ConfigDatabaseInfoUpd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ConfigDatabaseInfo configDatabaseInfo = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
                .eq(ConfigDatabaseInfo::getDatabaseName, dto.getDefaultDatabaseName()));
        Assert.isTrue(configDatabaseInfo!=null,"数据库"+dto.getDefaultDatabaseName()+"不存在！");

        if(!dto.getDefaultDatabaseName().equals(dto.getDatabaseName())) {
            @SuppressWarnings("unchecked")
            ConfigDatabaseInfo configDatabaseInfoCheck = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
                    .eq(ConfigDatabaseInfo::getDatabaseName, dto.getDatabaseName()));
            Assert.isTrue(configDatabaseInfoCheck == null, "查询名称" + dto.getDatabaseName() + "已存在！");
        }

        configDatabaseInfoService.update(new UpdateWrapper<ConfigDatabaseInfo>().lambda()
                .set(ConfigDatabaseInfo::getDatabaseName, dto.getDatabaseName())
                .set(ConfigDatabaseInfo::getDatabaseType, dto.getDatabaseType())
                .set(ConfigDatabaseInfo::getDatabaseDriver, dto.getDatabaseDriver())
                .set(ConfigDatabaseInfo::getDatabaseAddr, dto.getDatabaseAddr())
                .set(ConfigDatabaseInfo::getDatabaseAttr, dto.getDatabaseAttr())
                .set(ConfigDatabaseInfo::getDatabaseLabel, dto.getDatabaseLabel())
                .set(ConfigDatabaseInfo::getLoginName, dto.getLoginName())
                .set(ConfigDatabaseInfo::getLoginPassword, dto.getLoginPassword())
                .eq(ConfigDatabaseInfo::getDatabaseName, dto.getDefaultDatabaseName()));

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String databaseName;
        private String databaseType;
        private String databaseDriver;
        private String databaseAddr;
        private String databaseAttr;
        private String databaseLabel;
        private String loginName;
        private String loginPassword;

        // 原值
        private String defaultDatabaseName;
        private String defaultDatabaseType;
        private String defaultDatabaseDriver;
        private String defaultDatabaseAddr;
        private String defaultDatabaseAttr;
        private String defaultDatabaseLabel;
        private String defaultLoginName;
        private String defaultLoginPassword;

    }
}
