package com.hyw.platform.tools;

import com.hyw.platform.dbservice.DataService;
import com.hyw.platform.dbservice.NQueryWrapper;
import com.hyw.platform.dbservice.dto.TableFieldInfo;
import com.hyw.platform.dbservice.utils.DateTimeUtil;
import com.hyw.platform.dbservice.utils.DbUtil;
import com.hyw.platform.tools.excel.ExExcelUtils;
import com.hyw.platform.tools.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ExportExcelData2 {

    private static final String filePath = "/temp/ipos/";
    private Connection connection;
    private int deletePageSize = 100000;
    public String sqlDeleteLedgerDetailFin, sqlLoanInfo, sqlPoolFeeRate, sqlPoolPay, sqlPoolAct, sqlOtherRate, sqlOtherPay, sqlOtherAct, sqlLedgerGeneral, sqlLedgerDetail;


    public static void main(String[] args) {

        LocalDateTime begTime = LocalDateTime.now();
        ExportExcelData2 exportExcelData = new ExportExcelData2();
        exportExcelData.setInitVariables("sit3");
        //导出到同一个文件，不同sheet
//        exportExcelData.genExcel2("ipos_sit6_20220422", "资金池_费率", exportExcelData.sqlPoolFeeRate, 0);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "资金池_应收", exportExcelData.sqlPoolPay, 100000);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "资金池_实收", exportExcelData.sqlPoolAct, 100000);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "非资金池_费率", exportExcelData.sqlOtherRate, 0);
//        exportExcelData.genExcel3("ipos_sit6_20220422", "非资金池_应收", exportExcelData.sqlOtherPay);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "非资金池_实收", exportExcelData.sqlOtherAct, 100000);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "科目总账", exportExcelData.sqlLedgerGeneral, 100000);
//        exportExcelData.genExcel2("ipos_sit6_20220422", "分录明细", exportExcelData.sqlLedgerDetail, 100000);

        //导出到不同文件
        exportExcelData.genExcel("资金池_费率", "资金池_费率",  exportExcelData.sqlPoolFeeRate, 0);
        exportExcelData.genExcel("资金池_应收", "资金池_应收",  exportExcelData.sqlPoolPay, 200000);
        exportExcelData.genExcel("资金池_实收", "资金池_实收",  exportExcelData.sqlPoolAct, 200000);
        exportExcelData.genExcel("非资金池_费率", "非资金池_费率", exportExcelData.sqlOtherRate, 0);
        exportExcelData.genExcel("非资金池_应收", "非资金池_应收", exportExcelData.sqlOtherPay, 200000);
        exportExcelData.genExcel("非资金池_实收", "非资金池_实收", exportExcelData.sqlOtherAct, 200000);
//        exportExcelData.genExcel("科目总账",   "科目总账",      sqlLedgerGeneral, 100000);
//        exportExcelData.genExcel("分录明细",   "分录明细",      sqlLedgerDetail, 100000);

//        exportExcelData.genExcel("loanInfo",   "loanInfo",      sqlLoanInfo, 100000);


//        exportExcelData.executeUpdateSql(exportExcelData.sqlDeleteLedgerDetailFin,5000000,30000);
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println(String.format("开始时间：%s,结束时间：%s,耗时：%s",begTime,endTime, DateTimeUtil.getDifTime(begTime,endTime)));
    }

    private void setInitVariables(String env) {

        connection = DbUtil.getConnection(
                "mysql",
                "com.mysql.cj.jdbc.Driver",
                env.equalsIgnoreCase("dev") ? "jdbc:mysql://10.20.16.15:5102" :  //dev
                        env.equalsIgnoreCase("sit1") ? "jdbc:mysql://10.21.16.15:4576" :  // sit1
                                env.equalsIgnoreCase("sit3") ? "jdbc:mysql://10.21.16.31:4588" :  // sit3
                                        env.equalsIgnoreCase("sit6") ? "jdbc:mysql://10.21.16.52:3307" : "",  // sit6
                "useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai",
                "ipos",
                "iposopr", "*nP*!X6ixmSH"  // dev/sit1/sit3/sit6
        );

        sqlDeleteLedgerDetailFin = "delete from ledger_detail where 1=1";

        sqlLoanInfo = "select loan_no 贷款编号 from loan_info order by loan_no";

        sqlPoolFeeRate = "select d.loan_no as '贷款编号',d.discount_rate as '折现率',d.fair_value_rate as '担保公允价值'," +
                "d.match_rate as '撮合比例',d.guarantee_ratio as '担保占比',d.match_ratio as '撮合占比'," +
                "d.loan_after_ratio as '贷后占比',d.path_ratio as '通道占比',d.pool_ration as '资金池占比'," +
                "(select sum(dd.pay_dashuf_discount) from ipos.pool_fee_plan dd where dd.loan_no = d.loan_no) as '应还大数折现值'," +
                "(select sum(dd.pay_pool_discount) from ipos.pool_fee_plan dd where dd.loan_no = d.loan_no) as '应还资金池折现值'," +
                "'资金池' as '模式',d.message as '信息' " +
                " from ipos.loan_info d WHERE data_mode = 'POOL' " +
                " order by d.loan_no";
        sqlPoolPay = "select d.loan_no as '贷款编号', d.sterm as '期次', d.pay_date as '应还日期', d.pay_amount as '原费用金额', " +
                "d.pay_dashuf_amount as '原应还大数费用金额', d.pay_pool_amount as '原应还资金池费用金额', d.pay_dashuf_discount as '应还大数折现值', " +
                "d.pay_pool_discount as '应还资金池折现值', d.pay_dashuf_fee_inte as '应还大数利息收入', d.pay_pool_fee_inte as '应还资金池利息收入', " +
                "d.pay_guarantee as '应还担保收入', d.pay_match as '应还撮合收入', d.pay_loan_after as '贷后收入' " +
                " from ipos.pool_fee_plan d inner join loan_info l on d.loan_no = l.loan_no and data_mode = 'POOL' " +
                " ORDER BY d.loan_no, d.sterm";

        sqlPoolAct = "select d.loan_no as '贷款编号', d.acc_date as '记账日期', d.pay_dashuf_fee_inte as '应还大数利息收入', " +
                "d.actual_dashuf_fee_inte as '实还大数利息收入', d.pay_pool_fee_inte as '应还资金池利息收入', d.actual_pool_fee_inte as '实还资金利息收入', " +
                "d.actual_guarantee as '实还担保收入', d.actual_match as '实还撮合收入', d.actual_loan_after as '贷后收入', d.bill_type as '单据类型', " +
                "d.fee_type as '费用类型', d.origin_bill_no as '原单据', d.trans_channel as '渠道' " +
                " from ipos.pool_back_bill d inner join loan_info l on l.loan_no = d.loan_no and l.data_mode = 'POOL' " +
                " ORDER BY d.loan_no,d.acc_date";

        sqlOtherRate = "select  d.loan_no          as '贷款编号', d.discount_rate    as '折现率', " +
                "      d.fair_value_rate  as '担保公允价值',       d.match_rate       as '撮合比例', " +
                "      d.guarantee_ratio  as '担保占比', " +
                "      d.match_ratio      as '撮合占比', " +
                "      d.loan_after_ratio as '贷后占比', " +
                "      (select sum(dd.pay_discount) from ipos.dsf_fee_plan dd where dd.loan_no = d.loan_no) as '费用折现合计', " +
                "      (case when d.data_mode = 'DSFFEE' then '常规' " +
                "            when d.data_mode = 'SHARE' then '分润' " +
                "            when d.data_mode = 'SHARE_DSFFEE' then '分润+收费' end) as '模式', " +
                "      d.message as '信息' " +
                " from ipos.loan_info d " +
                " where data_mode != 'POOL' " +
                " ORDER BY d.loan_no";

        sqlOtherPay = "select d.loan_no        as '贷款编号', " +
                "     d.sterm          as '期次', " +
                "     d.pay_date       as '应还日期', " +
                "     d.pay_amount     as '原费用金额', " +
                "     d.pay_discount   as '折现值', " +
                "     d.pay_fee_inte   as '应还利息收入', " +
                "     d.pay_guarantee  as '应还担保收入', " +
                "     d.pay_match      as '应还撮合收入', " +
                "     d.pay_loan_after as '贷后收入' " +
                " from ipos.dsf_fee_plan d inner join loan_info l on d.loan_no = l.loan_no and data_mode != 'POOL' " +
                " ORDER BY d.loan_no, d.sterm";

        sqlOtherAct = "select d.loan_no           as '贷款编号', " +
                "     d.acc_date          as '记账日期', " +
                "     d.pay_fee_inte      as '应还利息收入', " +
                "     d.actual_fee_inte   as '实还利息收入', " +
                "     d.actual_guarantee  as '实还担保收入', " +
                "     d.actual_match      as '实还撮合收入', " +
                "     d.actual_loan_after as '贷后收入', " +
                "     d.bill_type         as '单据类型', " +
                "     d.fee_type          as '费用类型', " +
                "     d.origin_bill_no    as '原单据', " +
                "     d.trans_channel     as '渠道' " +
                "from ipos.dashuf_back_bill d inner join loan_info l on l.loan_no = d.loan_no and l.data_mode != 'POOL' " +
                " ORDER BY d.loan_no,d.acc_date";

        sqlLedgerGeneral = "select loan_no          as '贷款编号', " +
                "       subject_alias    as '科目别名', " +
                "       subject_no       as '科目编号', " +
                "       sub_subject_no   as '子科目号', " +
                "       currency         as '币种', " +
                "       debit_balance    as '借方余额', " +
                "       credit_balance   as '贷方余额', " +
                "       line_id          as '合作机构', " +
                "       org_id           as '分公司号', " +
                "       account_owner_cd as '账务归属' " +
                " from ledger_general " +
                " ORDER BY loan_no,subject_no";
        sqlLedgerDetail = "select loan_no          as '贷款编号', bill_no          as '单据编号', " +
                "     trans_id         as '交易ID', " +
                "     sort_id          as '序号', " +
                "     acc_date         as '记账日期', " +
                "     business_date    as '业务日期', " +
                "     currency         as '币种', " +
                "     subject_no       as '科目编号', " +
                "     sub_subject_no   as '子科目号', " +
                "     debit_amount     as '借方发生额', " +
                "     credit_amount    as '贷方发生额', " +
                "     line_id          as '合作机构', " +
                "     org_id           as '分公司号', " +
                "     account_owner_cd as '账务归属', " +
                "     hand_status      as '处理状态' " +
                " from ledger_detail " +
                " ORDER BY loan_no,acc_date,trans_id,sort_id";
    }

    public void genExcel2(String fileName, String sheetName, String sql, int pageSize) {
        DataService dataService = new DataService();

        //取脚本记录的字段名称
        Map<String, String> headFieldName = new LinkedHashMap<>();
        List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldList(sql + " limit 1", connection);
        tableFieldInfoList.forEach(tf -> headFieldName.put(tf.getFieldName(), tf.getComment()));

        int index = 0;
        int limitStartIndex = 0;
        String sqlLimit = pageSize > 0 ? (sql + " LIMIT " + limitStartIndex + "," + pageSize) : sql;
        List<Map<String, Object>> dataListMap = dataService.mapList(new NQueryWrapper<>().setConnection(connection).setSql(sqlLimit));
        while (dataListMap.size() > 0) {
            File file = new File(filePath + fileName + ".xlsx");
            try {
                ExExcelUtils.getInstance().writeDataToExcel(
                        file, sheetName, headFieldName, dataListMap, true, true);
            } catch (Exception e) {
                log.error("异常！", e);
            }
            System.out.println("文件名：" + file.getAbsolutePath() + ",sheetName:" + sheetName);
            index++;

            limitStartIndex = limitStartIndex + pageSize;
            if (pageSize > 0) {
                sqlLimit = sql + " LIMIT " + limitStartIndex + "," + pageSize;
                dataListMap = dataService.mapList(new NQueryWrapper<>().setConnection(connection).setSql(sqlLimit));
                sheetName = sheetName + index;
            } else {
                dataListMap.clear();
            }
        }

    }

    public void genExcel(String fileName, String sheetName, String sql, int pageSize) {
        DataService dataService = new DataService();

        //取脚本记录的字段名称
        Map<String, String> headFieldName = new LinkedHashMap<>();
        List<TableFieldInfo> tableFieldInfoList = dataService.getTableFieldList(sql + " limit 1", connection);
        tableFieldInfoList.forEach(tf -> headFieldName.put(tf.getFieldName(), tf.getComment()));

        int index = 1;
        int limitStartIndex = 0;
        String sqlLimit = pageSize > 0 ? (sql + " LIMIT " + limitStartIndex + "," + pageSize) : sql;
        List<Map<String, Object>> dataListMap = dataService.mapList(new NQueryWrapper<>().setConnection(connection).setSql(sqlLimit));
        while (dataListMap.size() > 0) {
            File file = new File(pageSize > 0 ? (filePath + fileName + "_" + index + ".xls") :
                    (filePath + fileName + ".xls"));
            try {
                ExExcelUtils.getInstance().exportObjects2Excel(dataListMap, headFieldName, true, sheetName, true, new FileOutputStream(file));
            } catch (Exception e) {
                log.error("异常！", e);
            }
            System.out.println(file.getAbsolutePath());
            index++;

            limitStartIndex = limitStartIndex + pageSize;
            if (pageSize > 0) {
                sqlLimit = sql + " LIMIT " + limitStartIndex + "," + pageSize;
                dataListMap = dataService.mapList(new NQueryWrapper<>().setConnection(connection).setSql(sqlLimit));
            } else {
                dataListMap.clear();
            }
        }
    }

    public int executeUpdateSql(String sql,int totalCount,int pageSize){
        DataService dataService = new DataService();

        String exeSql;
        int count = 0;
        while (count<totalCount) {
            exeSql = sql + " limit " + (count+pageSize>totalCount?totalCount-count:pageSize);
            System.out.println(exeSql);
            int curDelCount = dataService.delete(this.connection, exeSql);
            if(curDelCount<=0) break;
            count = count + curDelCount;
        }
        return count;
    }
}
