/**
 * File Name: ExExcelUtils.java
 * Date: 2018年12月04日 下午16:24:53
 * Copyright (c) 2019, Dashuf, Inc. All Rights Reserved.
 *
 */
package com.hyw.platform.tools.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

/**
 * @Auther: huangweibin
 * @Description: ExcelUtils结合模板系统拓展
 */
public class ExExcelUtils2 {

    /**
     * 单例模式
     * 通过{@link ExExcelUtils2#getInstance()}获取对象实例
     */
    private static ExExcelUtils2 exExcelUtils;

    private ExExcelUtils2() {
    }

    public synchronized static ExExcelUtils2 getInstance() {
        if (exExcelUtils == null) {
            exExcelUtils = new ExExcelUtils2();
        }
        return exExcelUtils;
    }

}
