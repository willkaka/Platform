package com.hyw.platform.funbean.RequestFunImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyw.platform.config.DynamicDataSourceManager;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.iservice.CommonQueryConfigService;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.model.CommonQueryConfig;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.resp.webElement.WebElementDto;
import com.hyw.platform.web.service.WebElementService;
import com.hyw.platform.web.syswebconfig.UUIDShort;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("commonQueryCond")
public class CommonQueryCond extends RequestFunUnit<List<WebElementDto>, CommonQueryCond.QueryVariable> {

    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;
    @Autowired
    private DynamicDataSourceManager dynamicDataSourceManager;
    @Autowired
    private CommonQueryConfigService commonQueryConfigService;
    @Autowired
    private WebElementService webElementService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getDbName()),"DB不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getQueryType()),"查询类型不允许为空值!");
    }

    @Override
    public List<WebElementDto> execLogic(PublicReq requestDto, QueryVariable variable){
        // 获取对应数据库配置信息
        ConfigDatabaseInfo cdi = configDatabaseInfoService.getOne(new QueryWrapper<ConfigDatabaseInfo>().lambda()
                .eq(ConfigDatabaseInfo::getDatabaseName,variable.getDbName()));

        // 手动添加数据源
        dynamicDataSourceManager.addDataSource(cdi.getDatabaseName(),
                cdi.getDatabaseAddr(),
                cdi.getLoginName(),
                cdi.getLoginPassword(),
                cdi.getDatabaseDriver());

        CommonQueryConfig cqc = commonQueryConfigService.getOne(new QueryWrapper<CommonQueryConfig>().lambda()
                .eq(CommonQueryConfig::getQueryType,variable.getQueryType()));
        BizException.trueThrow(Objects.isNull(cqc),"查询类型【"+variable.getQueryType()+"】不存在!");
        JSONArray condFieldList = JSON.parseArray(cqc.getQueryCond());
        List<WebElementDto> webElementDtoList = new ArrayList<>();
        try {
            // 切换到该数据源
            dynamicDataSourceManager.use(cdi.getDatabaseName());

            for(int i=0;i<condFieldList.size();i++){
                JSONObject condField = condFieldList.getJSONObject(i);
                String field = condField.getString("field");
                String type = condField.getString("type");
                String fieldName = condField.getString("fieldName");
                String dataSql = condField.getString("dataSql");
                List<Map<String, Object>> dataMaps = new ArrayList<>();
                if(StringUtils.isNotBlank(dataSql)) {
                    dataMaps = jdbcTemplate.queryForList(dataSql);
                }
                Map<String, String> dataMap = new LinkedHashMap<>();
                for(Map<String,Object> dataMapItem:dataMaps){
                    dataMap.put(dataMapItem.get("key").toString(),dataMapItem.get("value").toString());
                }
                WebElementDto webElementDto = new WebElementDto();
                webElementDto.setElementName(field);
                webElementDto.setType(type);
                webElementDto.setDesc(fieldName);
                webElementDto.setData(dataMap);
                webElementDto.setSortNo(i+1);
                webElementDto.setId(variable.getQueryType()+"_"+"USER_DEF"+field+ (i + 1));
                webElementDtoList.add(webElementDto);
            }
        } finally {
            dynamicDataSourceManager.clear();
        }

        return webElementDtoList;
    }

    /**
     * 输出自定义
     */
    @Override
    public PublicResp setPublicResp(List<WebElementDto> webElementDtoList){
        List<WebElementDto> inputList = webElementService.getPageElementsById("CommonQuery#start_page#personCondDiv", new PublicReq());
        String pid = inputList.get(0).getId();
        webElementDtoList.forEach(w->w.setPId(pid));
        inputList.get(0).setSubElementList(webElementDtoList);
        return new PublicResp().setWebElementDtoList(inputList);
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
