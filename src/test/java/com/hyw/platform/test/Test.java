package com.hyw.platform.test;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private String getStringWithMaxLength(String s, int maxLength){
        if(StringUtils.isBlank(s)) return s;
        if(s.length()<=maxLength) return s;
        return s.substring(0,maxLength);
    }


    public static void main(String[] args){
        Test object = new Test();
        String s = object.getStringWithMaxLength("String loanNo = jsonObject.containsKey(\"loan_no\")?jsonObject.getString(\"loan_no\"):",
                10);
        System.out.println(s);
    }

//    public static void main(String[] args){
//
//        getDate("2019-05-10");
//        getDate("2021-01-01");
//        getDate("2022-05-01");
//        getDate("2022-05-10");
//        getDate("2022-07-10");
//        getDate("2024-05-10");
//
//    }
//
//    public static void getDate(String putoutDateStr){
//        List<String> acctModelPreFixAfterElement = new ArrayList<>();
//        acctModelPreFixAfterElement.add("2021-01-01");
////        acctModelPreFixAfterElement.add("2022-05-01");
////        acctModelPreFixAfterElement.add("2022-06-01");
////        acctModelPreFixAfterElement.add("2022-07-01");
////        acctModelPreFixAfterElement.add("2022-08-01");
////        acctModelPreFixAfterElement.add("2022-09-01");
//
//
//
////        System.out.println(acctModelPreFixAfterElement);
//
//        if(CollectionUtils.isNotEmpty(acctModelPreFixAfterElement)) {
//            //按取到的日期倒序排序，以支持多条区间的分润配置。
//            acctModelPreFixAfterElement.sort( ( date1, date2 ) -> date2.compareTo( date1 ) );
//            for(String targetDate:acctModelPreFixAfterElement) {
//                if(putoutDateStr.compareTo(targetDate) >= 0 ) {
//                    System.out.println("putoutDate:"+putoutDateStr+",area:"+targetDate);
//                    break;
//                }
//            }
//        }
//    }
}
