package com.hyw.platform.dbservice.utils;

import sun.awt.geom.AreaOp;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 计算返回两个时间差
     * @param begDateTime 开始时间
     * @param endDateTime 结束时间
     * @return 两个时间差：x
     */
    public static String getDifTime(LocalDateTime begDateTime,LocalDateTime endDateTime){
        //耗时(分钟)
        if(begDateTime != null && endDateTime != null){
            long iDifTotalMilSec =  java.time.Duration.between(begDateTime, endDateTime).toMillis();
            long iTimeDay = iDifTotalMilSec / (1000 * 24 * 60 * 60);
            long iTimeHour = iDifTotalMilSec % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);
            long iTimeMinu = iDifTotalMilSec % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) / (1000 * 60);
            long iTimeSec = iDifTotalMilSec % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) % (1000 * 60) / 1000;
            long iTimeMilSec = iDifTotalMilSec % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) % (1000 * 60) % 1000;
            return  (iTimeDay   >0?(iTimeDay + " Day"):"")
                    + (iTimeHour  >0?(String.format("%02d", iTimeHour  ) + ":"):"00:")
                    + (iTimeMinu  >0?(String.format("%02d", iTimeMinu  ) + ":"):"00:")
                    + (iTimeSec   >0?(String.format("%02d", iTimeSec   ) + ","):"00,")
                    + (iTimeMilSec>0?(String.format("%03d", iTimeMilSec)      ):"000");
        }
        return null;
    }

    public static String timeStampToString(long timestamp){
        Date d = new Date(timestamp);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    public static LocalDateTime timeStampToLocalDateTime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static void main(String[] args){
        System.out.println(timeStampToLocalDateTime(1652663207273L));
    }
}
