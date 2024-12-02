package com.hyw.platform.test.t2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoanSpecialService {

    public static void main(String[] args){
        List<LoanPlan> LoanPlanList = new ArrayList<>();
        LoanPlanList.add(new LoanPlan().setPaymentNo( 1).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("99")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 2).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("100")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 3).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("201")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 4).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("202")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 5).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("203")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 6).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("204")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 7).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("205")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 9).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("207")));
        LoanPlanList.add(new LoanPlan().setPaymentNo( 8).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("206")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(11).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("209")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(10).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("208")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(12).setPaymentPrincipal(new BigDecimal("1000")).setPaymentInterest(new BigDecimal("210")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(13).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("0")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(14).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("220")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(15).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("221")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(16).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("222")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(17).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("223")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(18).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("224")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(19).setPaymentPrincipal(new BigDecimal("0")).setPaymentInterest(new BigDecimal("225")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(20).setPaymentPrincipal(new BigDecimal("2000")).setPaymentInterest(new BigDecimal("10")));
        LoanPlanList.add(new LoanPlan().setPaymentNo(21).setPaymentPrincipal(new BigDecimal("3000")).setPaymentInterest(new BigDecimal("10")));

        List<LoanSpecialDto> subList = mergePlan(LoanPlanList);
        subList.forEach(s->System.out.println(String.format("%s %s %s %s %s %s",
                s.getPaymentNoBeg(),s.getPaymentNoEnd(),
                s.getPaymentPrincipalSum(),s.getPaymentPrincipalMax(),
                s.getPaymentInterestSum(),s.getPaymentInterestMax())));
    }

    public static List<LoanSpecialDto> mergePlan(List<LoanPlan> LoanPlanList){
        //按还款期次从小到大排序
        LoanPlanList.sort(Comparator.comparing(LoanPlan::getPaymentNo));

        List<LoanSpecialDto> subList = new ArrayList<>();
        BigDecimal principalMax = BigDecimal.ZERO; // 区间内本金最大值
        BigDecimal interestMax = BigDecimal.ZERO; // 区间内利息最大值
        BigDecimal principalSum = BigDecimal.ZERO; // 区间内本金和计
        BigDecimal interestSum = BigDecimal.ZERO; // 区间内利息和计
        String amountType = "";     //当前值
        String amountTypeLast = ""; //前值
        int termBeg = 0,termEnd = 0;
        for(LoanPlan LoanPlan:LoanPlanList){
            //当前期次记录，是否有本金金额
            amountType = (LoanPlan.getPaymentPrincipal().compareTo(BigDecimal.ZERO) > 0)?"principal":"interest";
            // 利息期次 变为 本金时记录；或 本金期次 时记录。
            if(LoanPlan.getPaymentPrincipal().compareTo(BigDecimal.ZERO) > 0 ||
                    "principal".equals(amountTypeLast)) {
                //需要先记录前值
                subList.add(genRtnDto(termBeg, termEnd, principalSum, principalMax, interestSum, interestMax));
                principalSum = BigDecimal.ZERO;
                interestSum = BigDecimal.ZERO;
                interestMax = BigDecimal.ZERO;
                principalMax = BigDecimal.ZERO;
                termBeg = LoanPlan.getPaymentNo();
            }
            amountTypeLast = amountType;
            termBeg = termBeg==0?LoanPlan.getPaymentNo():termBeg;
            //首次进来，赋当前期次；否则取大值
            termEnd = termEnd==0?LoanPlan.getPaymentNo():Math.max(termEnd,LoanPlan.getPaymentNo());
            //累加和计
            principalSum = principalSum.add(LoanPlan.getPaymentPrincipal());
            interestSum = interestSum.add(LoanPlan.getPaymentInterest());
            //记录最大值
            interestMax = interestMax.compareTo(LoanPlan.getPaymentInterest())>0?interestMax:LoanPlan.getPaymentInterest();
            principalMax = principalMax.compareTo(LoanPlan.getPaymentPrincipal())>0?principalMax:LoanPlan.getPaymentPrincipal();
        }
        //记录最后一批数据
        subList.add(genRtnDto(termBeg,termEnd,principalSum,principalMax,interestSum,interestMax));
        return subList;
    }

    private static LoanSpecialDto genRtnDto(int termBeg, int termEnd,
                                            BigDecimal principalSum, BigDecimal principalMax,
                                            BigDecimal interestSum, BigDecimal interestMax){
        return new LoanSpecialDto().setPaymentNoBeg(termBeg)
                .setPaymentNoEnd(termEnd)
                .setPaymentPrincipalSum(principalSum)
                .setPaymentPrincipalMax(principalMax)
                .setPaymentInterestSum(interestSum)
                .setPaymentInterestMax(interestMax);
    }
}

