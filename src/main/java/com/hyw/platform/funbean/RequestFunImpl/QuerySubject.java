package com.hyw.platform.funbean.RequestFunImpl;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyw.platform.config.DynamicDataSourceManager;
import com.hyw.platform.constant.WebConstant;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.iservice.ConfigDatabaseInfoService;
import com.hyw.platform.model.ConfigDatabaseInfo;
import com.hyw.platform.web.req.PublicReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("querySubject")
public class QuerySubject extends RequestFunUnit<Map<String,Object>, QuerySubject.QueryVariable> {

    @Autowired
    private ConfigDatabaseInfoService configDatabaseInfoService;
    @Autowired
    private DynamicDataSourceManager dynamicDataSourceManager;
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
    }

    @Override
    public Map<String,Object> execLogic(PublicReq requestDto, QueryVariable variable){
        // 获取对应数据库配置信息
        ConfigDatabaseInfo cdi = configDatabaseInfoService.getOne(new QueryWrapper<ConfigDatabaseInfo>().lambda()
                .eq(ConfigDatabaseInfo::getDatabaseName,variable.getDbName()));

        // 手动添加数据源
        dynamicDataSourceManager.addDataSource(cdi.getDatabaseName(),
                cdi.getDatabaseAddr(),
                cdi.getLoginName(),
                cdi.getLoginPassword(),
                cdi.getDatabaseDriver());
        //取trans_entry
        String teSql = "select * from caes.trans_entry WHERE record_ind='A'";
        if(StringUtils.isNotBlank(variable.getQueryInputModelId())){
            teSql = teSql + " AND model_id='"+variable.getQueryInputModelId()+"'";
        }
        if(StringUtils.isNotBlank(variable.getQueryInputTransType()) && !"all".equals(variable.getQueryInputTransType())){
            teSql = teSql + " AND trans_id='" + variable.getQueryInputTransType() + "'";
        }
        teSql = teSql +  "order by trans_id,group_cd asc,expression,direction desc";
        //取subject_map_eas
        String smeSql = "select * from caes.subject_map_eas WHERE record_ind='A'";

        List<TransEntry> transEntryList;
        List<SubjectMapEas> subjectMapEasList;
        try{
            // 切换到该数据源
            dynamicDataSourceManager.use(cdi.getDatabaseName());
            transEntryList = jdbcTemplate.query(teSql,new BeanPropertyRowMapper<>(TransEntry.class));
            subjectMapEasList = jdbcTemplate.query(smeSql,new BeanPropertyRowMapper<>(SubjectMapEas.class));
        }finally {
            dynamicDataSourceManager.clear();
        }

        // DSF	*DFT	01.01	大数信科总部
        // DSF	100500	01.02	大数信科北京分公司
        // DSRD	*DFT	02	深圳市大数融资担保有限公司
        // RSRD	*DFT	03	武汉融生融资担保有限公司
        String company;
        if("DSRD".equals(variable.getQueryInputAccountOwner())) company = "02";
        else if("RSRD".equals(variable.getQueryInputAccountOwner())) company = "03";
        else if("100500".equals(variable.getQueryInputBranchNo())) company = "01.02";
        else company = "01.01";

        List<Map<String, Object>> dataMaps = new ArrayList<>();
        int index = 1;
        for(TransEntry te:transEntryList){
            // 取匹配的科目映射
            List<SubjectMapEas> subjectMapList2 = new ArrayList<>();
            for(SubjectMapEas sme:subjectMapEasList){
                if(te.getSubjectNo().equals(sme.getSubjectNo())){
                    subjectMapList2.add(sme);
                }
            }
            SubjectMapEas subjectMap = getMatchModel(subjectMapList2,
                    variable.getQueryInputTransChannel(),variable.getQueryInputModelId(),variable.getQueryInputBranchNo(),company);

            // 序号	交易ID	编号	发生方向	科目	EAS科目编号	核算项目编码1	分组	摘要	表达式
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("index",index++);
            dataMap.put("transId",te.getTransId());
            dataMap.put("sortId",te.getSortId());
            dataMap.put("direction",te.getDirection());
            dataMap.put("subjectNo",te.getSubjectNo());
            dataMap.put("accountNum",subjectMap.getAccountNum());
            dataMap.put("asstactFirstNum",subjectMap.getAsstactFirstNum());
            dataMap.put("groupCd",te.getGroupCd());
            dataMap.put("digest",te.getDigest());
            dataMap.put("expression",te.getExpression());
            dataMaps.add(dataMap);
        }

        //参数配置
        variable.setOutputShowType(WebConstant.OUTPUT_SHOW_TYPE_TABLE); //以表格形式显示

        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("data",dataMaps);

        return rtnMap;
    }

    public static final double WEIGH_FIRST = 0.5;
    public static final double WEIGH_SECOND = 0.25;
    public static final double WEIGH_THIRD = 0.125;
    public static final double WEIGH_FOURTH = 0.0625;
    public static final double WEIGH_FIFTH = 0.03125;
    private static SubjectMapEas getMatchModel(List<SubjectMapEas> list,
                                                    String businessChannel, String lineId, String orgId, String companyNum) {
        double max = 0;
        SubjectMapEas result = null;
        for (SubjectMapEas sme:list){
            double temp = 0;
            if(sme.getBusinessChannel().equals(businessChannel)) temp = temp + WEIGH_FIRST;
            if(sme.getLineId().equals(lineId)) temp = temp + WEIGH_SECOND;
            if(sme.getOrgId().equals(orgId)) temp = temp + WEIGH_THIRD;
            if(sme.getCompanyNum().equals(companyNum)) temp = temp + WEIGH_FOURTH;
            if(max<temp) {
                max = temp;
                result = sme;
            }
        }
        return result==null?list.get(0):result;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String dbName;
        private String queryInputModelId;
        private String queryInputBranchNo;
        private String queryInputAccountOwner;
        private String queryInputTransChannel;
        private String queryInputTaxFree;
        private String queryInputTransType;
    }

    @Data
    @TableName( "trans_entry" )
    public static class TransEntry {
        /**
         * 主键id
         */
        @TableId("trans_entry_id")
        private String transEntryId;

        @TableField("model_id")
        private String modelId;

        /**
         * 交易ID
         */
        @TableField("trans_id")
        private String transId;

        /**
         * 编号
         */
        @TableField("sort_id")
        private String sortId;

        /**
         * 发生方向
         */
        @TableField(value="direction")
        private String direction;

        /**
         * 科目
         */
        @TableField("subject_no")
        private String subjectNo;

        /**
         * 表达式
         */
        @TableField(value="expression")
        private String expression;

        @TableField("account_owner_cd")
        private String accountOwnerCd;

        /**
         * 摘要
         */
        @TableField(value="digest")
        private String digest;

        /**
         * 校验规则
         */
        @TableField("valid_rule")
        private String validRule;

        /**
         * 覆盖标识
         */
        @TableField(value="cover")
        private String cover;


        @TableField(value="created_date")
        private LocalDateTime createdDate;

        @TableField(value="created_by")
        private String createdBy;

        @TableField(value="updated_date")
        private LocalDateTime updatedDate;

        @TableField(value="updated_by")
        private String updatedBy;

        @TableField(value="version_val")
        private Integer versionVal;

        @TableField(value="record_ind")
        private String recordInd;

        /**
         * 分组
         */
        @TableField("group_cd")
        private String groupCd;
    }

    @Data
    public static class SubjectMapEas {

        /**
         * 主键流水号
         */
        @TableId("subject_map_eas_id")
        private String subjectMapEasId;

        /**
         * 信贷科目
         */
        @TableField("subject_no")
        private String subjectNo;

        /**
         * 渠道
         */
        @TableField("business_channel")
        private String businessChannel;

        /**
         * 合作方
         */
        @TableField("line_id")
        private String lineId;

        /**
         * 机构
         */
        @TableField("org_id")
        private String orgId;

        /**
         * EAS账套
         */
        @TableField("company_num")
        private String companyNum;

        /**
         * 税收标识
         * 1:收税，0：不收税
         */
        @TableField("tax_type")
        private String taxType;

        /**
         * EAS科目编号
         */
        @TableField("account_num")
        private String accountNum;

        /**
         * 核算项目编码1
         */
        @TableField("asstact_first_num")
        private String asstactFirstNum;

        /**
         * 核算项目编码2
         */
        @TableField("asstact_second_num")
        private String asstactSecondNum;
    }

}
