package com.hyw.platform.tservice.multithread.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 线程执行返回结果
 */
@Data
public class ThreadRtnDto {
    private BigDecimal amount;
    private String s;
    private String rtnCode;
    private String rtnMessage;
}
