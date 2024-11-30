package com.hyw.platform.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.exception.BizThrow;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class Test001 {

    public static void main(String[] args){
//        log.info("测试1：{},测试2：{},测试3：{}","kkkk",LocalDate.now(),LocalDate.now());
//
//        BizThrow.isTrue(true,"xxxx{}sdfsf{}s","kkkk", LocalDate.now());

        JSONArray jsonArray = JSONObject.parseArray("[{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-05-01\",\"sterm\":1},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-06-01\",\"sterm\":2},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-07-01\",\"sterm\":3},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-08-01\",\"sterm\":4},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-09-01\",\"sterm\":5},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-10-01\",\"sterm\":6},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-11-01\",\"sterm\":7},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2019-12-01\",\"sterm\":8},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2020-01-01\",\"sterm\":9},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2020-02-01\",\"sterm\":10},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2020-03-01\",\"sterm\":11},{\"loanNo\":\"XW20190424000269\",\"payAmount\":669.0000,\"payDate\":\"2020-04-01\",\"sterm\":12}]");
        log.info("测试1：{},测试2：{},测试3：{}","kkkk",LocalDate.now(),jsonArray);
    }

}
