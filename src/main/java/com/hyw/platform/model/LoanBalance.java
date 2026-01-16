package com.hyw.platform.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 贷款余额表
 * </p>
 *
 * @author jobob
 * @since 2018-12-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("loan_balance")
public class LoanBalance{

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("loan_balance_id")
    private String loanBalanceId;

    /**
     * 贷款编号
     */
    @TableField("loan_no")
    private String loanNo;

    /**
     * 客户编号
     */
    @TableField("customer_id")
    private String customerId;

    /**
     * 产品ID
     */
    @TableField("product_id")
    private String productId;

    /**
     * 产品版本号
     */
    @TableField("product_version")
    private String productVersion;

    /**
     * 业务币种
     */
    private String currency;

    /**
     * 入账机构
     */
    @TableField("operate_org_id")
    private String operateOrgId;

    /**
     * 还款方式
     */
    @TableField("return_method")
    private String returnMethod;

    /**
     * 还款周期
     */
    @TableField("return_period")
    private String returnPeriod;

    /**
     * 宽限期单位
     */
    @TableField("grace_term_unit")
    private String graceTermUnit;

    /**
     * 宽限期
     */
    @TableField("grace_term")
    private Integer graceTerm;

    /**
     * 放款日期
     */
    @TableField("putout_date")
    private LocalDate putoutDate;

    /**
     * 到期日
     */
    @TableField("maturity_date")
    private LocalDate maturityDate;

    /**
     * 首次还款日
     */
    @TableField("begin_date")
    private LocalDate beginDate;

    /**
     * 贷款发放期限
     */
    @TableField("loan_term")
    private Integer loanTerm;

    /**
     * 摊还期限（仅气球贷使用）
     */
    @TableField(exist = false)
    private Integer calLoanTerm;

    /**
     * 下次还款日
     */
    @TableField("next_pay_date")
    private LocalDate nextPayDate;

    /**
     * 贷款总期次
     */
    private Integer cterm;

    /**
     * 当前执行期次
     */
    private Integer sterm;

    /**
     * 利率基准天数
     */
    @TableField("base_days")
    private Integer baseDays;

    /**
     * 基准利率
     */
    @TableField("base_rate")
    private BigDecimal baseRate;

    /**
     * 默认03日利率利率模式
     */
    @TableField("rate_code")
    private String rateCode;

    /**
     * 利率模式
     */
    @TableField("rate_mode")
    private String rateMode;

    /**
     * 执行利率
     */
    @TableField("execute_rate")
    private BigDecimal executeRate;

    /**
     * 罚息利率
     */
    @TableField("fine_rate")
    private BigDecimal fineRate;

    /**
     * 复利利率
     */
    @TableField("inte_rate")
    private BigDecimal inteRate;

    /**
     * 上次费用计提入账日
     */
    @TableField("last_inte_date")
    private LocalDate lastInteDate;

    /**
     * 贷款状态
     */
    @TableField("loan_status")
    private String loanStatus;

    /**
     * 期供
     */
    @TableField("month_pay")
    private BigDecimal monthPay;

    /**
     * 放款金额
     */
    @TableField("business_sum")
    private BigDecimal businessSum;

    /**
     * 正常余额
     */
    @TableField("normal_balance")
    private BigDecimal normalBalance;

    /**
     * 逾期余额
     */
    @TableField("overdue_balance")
    private BigDecimal overdueBalance;

    /**
     * 正常利息
     */
    @TableField("normal_inte")
    private BigDecimal normalInte;

    /**
     * 逾期利息
     */
    @TableField("overdue_inte")
    private BigDecimal overdueInte;

    /**
     * 复利
     */
    private BigDecimal compound;

    /**
     * 罚息
     */
    @TableField("fine_inte")
    private BigDecimal fineInte;

    /**
     * 本金罚息标志
     */
    @TableField("fine_flag")
    private String fineFlag;

    /**
     * 利息罚息标志
     */
    @TableField("compound_flag")
    private String compoundFlag;

    /**
     * 滚费用日期
     */
    @TableField("accrued_date")
    private LocalDate accruedDate;

    /**
     * 结清日期
     */
    @TableField(value = "finish_date", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate finishDate;

    /**
     * 本息结清日期
     */
    @TableField(exist = false)
    private LocalDate corpInteFinishDate;

    /**
     * 是否参与批扣
     */
    @TableField("batch_deduct_flag")
    private String batchDeductFlag;

    /**
     * 代偿标识
     */
    @TableField("common_flag")
    private String commonFlag;

    /**
     * 转让标识
     */
    @TableField("transfer_flag")
    private String transferFlag;

    /**
     * 银行合作方编号
     */
    @TableField("line_id")
    private String lineId;

    /**
     * 费用计提金额
     */
    @TableField("period_amount")
    private BigDecimal periodAmount;

    /**
     * 期收费用
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /**
     * 合作模式id
     */
    private String collaborate;

    /**
     * 核算参数编号
     */
    @TableField("acc_product_no")
    private String accProductNo;

    /**
     * 个性化处理标志
     */
    @TableField("spec_flag")
    private String specFlag;

    /**
     * 冻结标志
     */
    @TableField("freeze_flag")
    private String freezeFlag;

    /**
     * 代银行滚罚息复利标志
     */
    @TableField("bank_fine_flag")
    private String bankFineFlag;

    /**
     * 银行罚息利率
     */
    @TableField("bank_fine_rate")
    private BigDecimal bankFineRate;

    /**
     * 银行复利利率
     */
    @TableField("bank_inte_rate")
    private BigDecimal bankInteRate;

    @TableField("account_owner_cd")
    private String accountOwnerCd;

    /**
     * 锁定级别
     */
    @TableField("lock_level")
    private Integer lockLevel;

    /**
     * 第三方代扣标志
     */
    @TableField("third_deduct_flag")
    private String thirdDeductFlag;

    /**
     * 机构代扣标志
     */
    @TableField("org_deduct_flag")
    private String orgDeductFlag;

    /**
     * 资金方
     */
    @TableField("fund_org")
    private String fundOrg;

    /**
     * 产品类型
     */
    @TableField("product_type")
    private String productType;

    /**
     * 一级产品
     */
    @TableField("first_product")
    private String firstProduct;

    /**
     * 扣款渠道
     */
    @TableField("deduct_channel")
    private String deductChannel;

    /**
     * 批量锁
     */
    @TableField("batch_lock_cd")
    private String batchLockCd;
    /**
     * 业务来源
     */
    @TableField("business_source")
    private String businessSource;
    /**
     * 业务渠道
     */
    @TableField("business_channel")
    private String businessChannel;

    /**
     * 上次跑批日期
     */
    @TableField("last_batch_date")
    private LocalDate lastBatchDate;

    /**
     * 跑批任务状态位图
     */
    @TableField("batch_task_status")
    private String batchTaskStatus;
    /**
     * 是否收取个人税收标志
     */
    @TableField("tax_type")
    private String taxType;

    /**
     * 理赔标志
     */
    @TableField("claim_flag")
    private String claimFlag;

}
