package com.hyw.platform.funbean.cdi;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
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

@Service("configDatabaseInfoAdd")
@Slf4j
public class ConfigDatabaseInfoAdd extends RequestFunUnit<String, ConfigDatabaseInfoAdd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(ConfigDatabaseInfoAdd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseName()),"数据库名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseType()),"数据库类型不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseDriver()),"数据库驱动不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseAddr()),"数据库地址不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, ConfigDatabaseInfoAdd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ConfigDatabaseInfo configDatabaseInfoCheck = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
                        .eq(ConfigDatabaseInfo::getDatabaseName, dto.getDatabaseName()));
        Assert.isTrue(configDatabaseInfoCheck==null,"数据库名称【"+dto.getDatabaseName()+"】已存在！");

        ConfigDatabaseInfo configDatabaseInfo = new ConfigDatabaseInfo();
        configDatabaseInfo.setDatabaseName(dto.getDatabaseName());
        configDatabaseInfo.setDatabaseType(dto.getDatabaseType());
        configDatabaseInfo.setDatabaseDriver(dto.getDatabaseDriver());
        configDatabaseInfo.setDatabaseAddr(dto.getDatabaseAddr());
        configDatabaseInfo.setDatabaseAttr(dto.getDatabaseAttr());
        configDatabaseInfo.setDatabaseLabel(dto.getDatabaseLabel());
        configDatabaseInfo.setLoginName(dto.getLoginName());
        configDatabaseInfo.setLoginPassword(dto.getLoginPassword());

        dataService.save(configDatabaseInfo);

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
    }
}
