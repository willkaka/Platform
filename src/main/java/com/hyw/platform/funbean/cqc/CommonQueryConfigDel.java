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

@Service("commonQueryConfigDel")
@Slf4j
public class CommonQueryConfigDel extends RequestFunUnit<String, CommonQueryConfigDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(CommonQueryConfigDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getCommonQueryConfigId()),"id不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, CommonQueryConfigDel.QueryVariable dto){
        @SuppressWarnings("unchecked")
        CommonQueryConfig commonQueryConfig = dataService.getOne(new NQueryWrapper<CommonQueryConfig>()
                       .eq(CommonQueryConfig::getCommonQueryConfigId, dto.getCommonQueryConfigId()));
        Assert.isTrue(commonQueryConfig!=null,"查询配置不存在！");

        dataService.deleteById(commonQueryConfig);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String commonQueryConfigId;
    }
}
