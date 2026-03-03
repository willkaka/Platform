package com.hyw.platform.funbean.RequestFunImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyw.platform.config.DynamicDataSourceManager;
import com.hyw.platform.constant.WebConstant;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.iservice.CommonQueryConfigService;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.model.CommonQueryConfig;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("commonQuery")
public class CommonQuery extends RequestFunUnit<Map<String,Object>, CommonQuery.QueryVariable> {

    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;
    @Autowired
    private DynamicDataSourceManager dynamicDataSourceManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommonQueryConfigService commonQueryConfigService;


    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq requestDto, QueryVariable variable){
        CommonQueryConfig cqc = commonQueryConfigService.getOne(new QueryWrapper<CommonQueryConfig>().lambda()
                .eq(CommonQueryConfig::getQueryType,variable.getQueryType()));
        BizException.trueThrow(Objects.isNull(cqc),"查询类型【"+variable.getQueryType()+"】不存在!");
        String querySql = cqc.getQuerySql();

        // 获取对应数据库配置信息
        ConfigDatabaseInfo cdi = configDatabaseInfoService.getOne(new QueryWrapper<ConfigDatabaseInfo>().lambda()
                .eq(ConfigDatabaseInfo::getDatabaseName,variable.getDbName()));
        // 手动添加数据源
        dynamicDataSourceManager.addDataSource(cdi.getDatabaseName(),
                cdi.getDatabaseAddr(),
                cdi.getLoginName(),
                cdi.getLoginPassword(),
                cdi.getDatabaseDriver());
        // 从请求参数中获取参数，替换SQL中的参数
        Map<String,Object> webValueMap = (Map<String,Object>) requestDto.getWebValueDto().get("webInputValueMap");
        for(Map.Entry<String,Object> entry:webValueMap.entrySet()){
            String k = entry.getKey();
            Object value = ((Map<String,Object>)entry.getValue()).get("value");
            querySql = querySql.replace("${"+k+"}","'"+value.toString()+"'");
        }

        List<Map<String, Object>> dataMaps = new ArrayList<>();
        try{
            // 切换到该数据源
            dynamicDataSourceManager.use(cdi.getDatabaseName());
            dataMaps = jdbcTemplate.queryForList(querySql);
        }finally {
            dynamicDataSourceManager.clear();
        }
        Map<String,String> headMap = new LinkedHashMap<>();
        if(CollectionUtils.isNotEmpty(dataMaps)){
            dataMaps.get(0).forEach((k,v)->headMap.put(k,k));
        }

        //参数配置
        variable.setOutputShowType(WebConstant.OUTPUT_SHOW_TYPE_TABLE); //以表格形式显示

        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("data",dataMaps);
        rtnMap.put("headMap",headMap);

        return rtnMap;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String dbName;
        private String queryType;
    }
}
