package com.hyw.platform.funbean.cqc;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.model.CommonQueryConfig;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("commonQueryConfigAdd")
@Slf4j
public class CommonQueryConfigAdd extends RequestFunUnit<String, CommonQueryConfigAdd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(CommonQueryConfigAdd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getQueryType()),"查询类型不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getQueryName()),"查询名称不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getQueryDesc()),"查询路径不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getQueryCond()),"查询条件不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getQuerySql()),"查询SQL不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, CommonQueryConfigAdd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        CommonQueryConfig commonQueryConfigCheck = dataService.getOne(new NQueryWrapper<CommonQueryConfig>()
                        .eq(CommonQueryConfig::getQueryType, dto.getQueryType())
                        .eq(CommonQueryConfig::getQueryName, dto.getQueryName()));
        Assert.isTrue(commonQueryConfigCheck==null,"查询类型【"+dto.getQueryType()+"】下查询名称【"+dto.getQueryName()+"】已存在！");

        CommonQueryConfig commonQueryConfig = new CommonQueryConfig();
        commonQueryConfig.setQueryType(dto.getQueryType());
        commonQueryConfig.setQueryName(dto.getQueryName());
        commonQueryConfig.setQueryDesc(dto.getQueryDesc());
        commonQueryConfig.setQueryCond(dto.getQueryCond().replace("'","''"));
        commonQueryConfig.setQuerySql(dto.getQuerySql().replace("'","''"));

        dataService.save(commonQueryConfig);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String queryType;
        private String queryName;
        private String queryDesc;
        private String queryCond;
        private String querySql;
    }
}
