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

@Service("configDatabaseInfoDel")
@Slf4j
public class ConfigDatabaseInfoDel extends RequestFunUnit<String, ConfigDatabaseInfoDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(ConfigDatabaseInfoDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDatabaseName()),"数据库名称不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, ConfigDatabaseInfoDel.QueryVariable dto){
        @SuppressWarnings("unchecked")
        ConfigDatabaseInfo configDatabaseInfo = dataService.getOne(new NQueryWrapper<ConfigDatabaseInfo>()
                       .eq(ConfigDatabaseInfo::getDatabaseName, dto.getDatabaseName()));
        Assert.isTrue(configDatabaseInfo!=null,"查询配置不存在！");
        dataService.delete(configDatabaseInfo,"databaseName");

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
    }
}
