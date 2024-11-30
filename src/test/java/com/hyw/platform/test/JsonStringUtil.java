package com.hyw.platform.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class JsonStringUtil {

    public void toTable(String jsonString, String listKeyName, List<String> colList){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(listKeyName);
//        for(JSONObject object:jsonArray)
    }

    public static void main(String[] args){
        String startDate = "2022-01";
        String endDate = "2022-02";
        LocalDate startAccDate = LocalDate.of(Integer.parseInt(startDate.substring(0, 4)),
                Integer.parseInt(startDate.substring(5, 7)),1);
        LocalDate endAccDate = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)),
                Integer.parseInt(endDate.substring(5, 7)),1);
        endAccDate = endAccDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(startAccDate+"  "+endAccDate);

        LocalDate birthDate = LocalDate.of(2000,12,31);
        LocalDate curBusinessDate = LocalDate.of(2022,1,31);

        int weekDay = curBusinessDate.getDayOfWeek().getValue();

        if(birthDate.getMonthValue()==curBusinessDate.getMonthValue() &&
                birthDate.getDayOfMonth()==curBusinessDate.getDayOfMonth()){
            endDate = "2022-02";
        }
        endDate = "2022-01";

        LocalDate nextMonthFirstDate = LocalDate.of(curBusinessDate.plusMonths(1).getYear(),
                curBusinessDate.plusMonths(1).getMonthValue(),1);
        int diffDays = (int) (nextMonthFirstDate.toEpochDay() - curBusinessDate.toEpochDay());
        if(diffDays == 1){
            endDate = "2022-01";
        }

    }
}
