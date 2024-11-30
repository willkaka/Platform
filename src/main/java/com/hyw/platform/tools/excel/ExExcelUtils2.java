/**
 * File Name: ExExcelUtils.java
 * Date: 2018年12月04日 下午16:24:53
 * Copyright (c) 2019, Dashuf, Inc. All Rights Reserved.
 *
 */
package com.hyw.platform.tools.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.crab2died.exceptions.Excel4JException;
import com.github.crab2died.handler.ExcelHeader;
import com.github.crab2died.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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

    /**
     * 更具模板解析list类型Excel
     * @param inputStream
     * @param jsonArray
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<JSONObject> readExcel2List(InputStream inputStream, JSONArray jsonArray)
            throws IOException, InvalidFormatException {
        //判断模板参数配置是否有误
        List<Integer> temp = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String location = jsonObject.getString("cellLocation");
            CellReference cr = new CellReference(location);
            if (!temp.contains(cr.getRow())) {
                temp.add(cr.getRow());
            }
        }

        if (temp.size() == 1) {
            try (Workbook workbook = WorkbookFactory.create(inputStream)) {
                return readExcel2ObjectsHandler(workbook, temp.get(0), Integer.MAX_VALUE, 0, jsonArray);
            }
        } else {
            throw new RuntimeException("模板参数有误");
        }
    }

    private List<JSONObject> readExcel2ObjectsHandler(Workbook workbook, int offsetLine,
                                                      int limitLine, int sheetIndex, JSONArray jsonArray) {
        List<JSONObject> list = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        long maxLine = sheet.getLastRowNum() > ((long) offsetLine + limitLine) ?
                ((long) offsetLine + limitLine) : sheet.getLastRowNum();
        for (int i = offsetLine; i <= maxLine; i++) {
            JSONObject jobj = new JSONObject();
            Row row = sheet.getRow(i);
            if (null == row)
                continue;
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String location = jsonObject.getString("cellLocation");
                String key = jsonObject.getString("cellContent");
                CellReference cr = new CellReference(location);
                Cell cell = row.getCell(cr.getCol());
                if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
                    break;
                }
                String val = Utils.getCellValue(cell);
                jobj.put(key, val.trim());
            }
            if (!jobj.isEmpty()) {
                list.add(jobj);
            } else {
                continue;
            }
        }
        return list;
    }

    /**
     * 生成sheet数据
     * @param workbook workbook
     * @param data 待导出数据
     * @param clazz {@link .annotation.ExcelField}映射对象Class
     * @param isWriteHeader 是否写入表头
     * @param sheetName 指定导出Excel的sheet名称
     * @throws Excel4JException 异常
     */
    private void generateSheet(Workbook workbook, List<?> data, Class clazz,
                               boolean isWriteHeader, String sheetName)
            throws Excel4JException {

        Sheet sheet;
        if (null != sheetName && !"".equals(sheetName)) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        Row row = sheet.createRow(0);
        List<ExcelHeader> headers = Utils.getHeaderList(clazz);
        if (isWriteHeader) {
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                row.createCell(i).setCellValue(headers.get(i).getTitle());
            }
        }
        // 写数据
        Object objData;
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i + 1);
            objData = data.get(i);
            for (int j = 0; j < headers.size(); j++) {
                row.createCell(j).setCellValue(Utils.getProperty(objData,
                        headers.get(j).getFiled(),
                        headers.get(j).getWriteConverter()));
            }
        }

    }

    /**
     * 无模板、基于注解的数据导出
     *
     * @param data          待导出数据
     * @param isWriteHeader 是否写入表头
     * @param sheetName     指定导出Excel的sheet名称
     * @param isXSSF        导出的Excel是否为Excel2007及以上版本(默认是)
     * @throws IOException      异常
     * @author Crab2Died
     */
    public void exportObjects2Excel(List<Map<String,Object>> data, Map<String,String> headFieldName, boolean isWriteHeader,
                                    String sheetName, boolean isXSSF, OutputStream os)
            throws IOException {

        try (Workbook workbook = exportExcelNoTemplateHandler(data, headFieldName, isWriteHeader, sheetName, isXSSF)) {
            workbook.write(os);
        }

    }

    /**
     * sheet数据导出
     * @param data 待导出数据
     * @param isWriteHeader 是否写入表头
     * @param sheetName 指定导出Excel的sheet名称
     * @param isXSSF 导出的Excel是否为Excel2007及以上版本(默认是)
     * @return Workbook
     */
    private Workbook exportExcelNoTemplateHandler(List<Map<String,Object>> data, Map<String,String> headFieldName,
                                                  boolean isWriteHeader,
                                                  String sheetName, boolean isXSSF){

        Workbook workbook;
        if (isXSSF) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }

        generateSheet(workbook, data, headFieldName, isWriteHeader, sheetName);

        return workbook;
    }

    public void writeDataToExcel(File file,String sheetName,
                                 Map<String,String> headFieldName,List<Map<String,Object>> data,
                                 boolean isWriteHeader, boolean isXSSF) {
        if(file.exists()){
            FileInputStream inputStream = null;
            Workbook workbook = null;
            try {
                inputStream = new FileInputStream(file);
                if(file.getName().endsWith("xls")){     //Excel&nbsp;2003
                    workbook = new HSSFWorkbook(inputStream);
                }else if(file.getName().endsWith("xlsx")){    // Excel 2007/2010
                    workbook = new XSSFWorkbook(inputStream);
//                    workbook = StreamingReader.builder()
//                            .rowCacheSize(100)  //缓存到内存中的行数，默认是10
//                            .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
//                            .open(inputStream);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
                }
                generateSheet(workbook,data,headFieldName,isWriteHeader,sheetName);
                workbook.write(Files.newOutputStream(file.toPath()));
            }catch (Exception e) {
                e.printStackTrace();
                try {
                    if(Objects.nonNull(inputStream)) inputStream.close();
                    if(Objects.nonNull(workbook)) workbook.close();
                } catch (IOException e1) {
                    e.printStackTrace();
                }
            }
        }else{
            Workbook workbook;
            if (isXSSF) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }
            generateSheet(workbook, data, headFieldName, isWriteHeader, sheetName);
            try {
                workbook.write(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 生成sheet数据
     * @param workbook workbook
     * @param data 待导出数据
     * @param headFieldName 表头字段定义
     * @param isWriteHeader 是否写入表头
     * @param sheetName 指定导出Excel的sheet名称
     */
    public void generateSheet(Workbook workbook, List<Map<String,Object>> data, Map<String,String> headFieldName,
                               boolean isWriteHeader, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if(sheet == null) {
            if (null != sheetName && !"".equals(sheetName)) {
                sheet = workbook.createSheet(sheetName);
            } else {
                sheet = workbook.createSheet();
            }
        }
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum>0?sheet.getLastRowNum()+1:0);
        Map<Integer,Integer> headColLengthSumMap = new HashMap<>();
        if (isWriteHeader && lastRowNum<=0) {
            // 写标题
            CellStyle style = workbook.createCellStyle();
//            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());//title单元格背景色
            style.setFillForegroundColor((short) 44);//title单元格背景色 https://www.docin.com/p-1019071433.html
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 12);//设置字体大小
            font.setBold(true);
            //边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            style.setFont(font);
            int i=0;
            for(String key:headFieldName.keySet()){
                row.createCell(i).setCellValue(headFieldName.get(key));
                row.getCell(i).setCellStyle(style);
                headColLengthSumMap.put(i,stringLength(headFieldName.get(key)));
                i++;
            }
        }
        // 写数据
        Map<Integer,Integer> colLengthSumMap = new HashMap<>();
        for (int i = 0; i < data.size(); i++) { // i-行号
            row = sheet.createRow(lastRowNum + i + 1);
            Map<String,Object> objData = data.get(i);

            int j=0;// j-列号
            for(String key:headFieldName.keySet()){
                Object valueObject = objData.get(key);
                String value;

                if(valueObject == null){
                    value = "";
                }else if (valueObject instanceof BigDecimal) {
                    value = ((BigDecimal)valueObject).setScale(6,BigDecimal.ROUND_HALF_UP).toString();
                    Double v = Double.valueOf(value);
                    row.createCell(j).setCellValue(v);
                }else if (valueObject instanceof LocalDate) {
                    value = ((LocalDate)valueObject).toString();
                    row.createCell(j).setCellValue(value);
                } else {
                    value = valueObject.toString();
                    row.createCell(j).setCellValue(value);
                }
                saveCellLength(colLengthSumMap,j,value.length());
                j++;
            }
        }

        //设置列宽为所有记录该列的平均字符长度
//        for(Integer colNum:colLengthSumMap.keySet()){
//            int argWidth = colLengthSumMap.get(colNum)/data.size();
//            int width = headColLengthSumMap.get(colNum)>argWidth?
//                    headColLengthSumMap.get(colNum)+4:argWidth+4;
//            sheet.setColumnWidth(colNum,width*256);
////            System.out.println("设置第"+colNum+"列，宽度为"+width*256);
//        }
    }

    private void saveCellLength(Map<Integer,Integer> colLengthSumMap, int colNum, int length){
        if(colLengthSumMap.containsKey(colNum)){
            colLengthSumMap.put(colNum,colLengthSumMap.get(colNum)+length);
        }else{
            colLengthSumMap.put(colNum,length);
        }
    }

    /**
     * 更具模板解析list类型Excel
     * @param workbook
     * @param jsonArray
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<JSONObject> readExcel2List(Workbook workbook, JSONArray jsonArray, Integer bg, Integer size){
        return readExcel2ObjectsHandler(workbook, bg, size, 0, jsonArray);
    }

    public int stringLength(String s){
        if(s==null) return 0;
        String chineseString = "[\u0391-\uFFE5]";
        int len=0;
        for(int i=0;i<s.length();i++){
            String temp = s.substring(i,i+1);
            if(temp.matches(chineseString)){
                len += 2;
            }else{
                len += 1;
            }
        }
        return len;
    }
}
