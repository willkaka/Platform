package com.hyw.platform.test.t2;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 本息计划
 */
@Data
@Accessors(chain = true)
public class LoanPlan {

    /**
     * 主键id
     */
    private String planBankId;

    /**
     * 贷款编号
     */
    private String loanNo;

    /**
     * 还款期次
     */
    private Integer paymentNo;

    /**
     * 还款日期
     */
    private LocalDate paymentDate;

    /**
     * 代垫标志
     */
    private String ddFlag;

    /**
     * 代垫日期
     */
    private LocalDate ddDate;

    /**
     * 结清标志
     */
    private String payoff;

    /**
     * 最后记账日期
     */
    private LocalDate lastAccountDate;

    /**
     * 应还本金
     */
    private BigDecimal paymentPrincipal;

    /**
     * 实还本金
     */
    private BigDecimal actualPrincipal;

    /**
     * 应还利息
     */
    private BigDecimal paymentInterest;

    /**
     * 实还利息
     */
    private BigDecimal actualInterest;

    /**
     * 应还罚息
     */
    private BigDecimal paymentPenalty;

    /**
     * 实还罚息
     */
    private BigDecimal actualPenalty;

    /**
     * 应还复利
     */
    private BigDecimal paymentCompound;

    /**
     * 实还复利
     */
    private BigDecimal actualCompound;


    /**
     * 创建用户
     */
    private String createdUser;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 最后更新用户
     */
    private String updatedUser;

    /**
     * 最后更新时间
     */
    private LocalDateTime updatedTime;

}
