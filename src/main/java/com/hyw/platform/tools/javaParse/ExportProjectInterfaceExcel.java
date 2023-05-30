package com.hyw.platform.tools.javaParse;

import com.hyw.platform.tools.javaParse.dto.JavaClassFieldInfo;
import com.hyw.platform.tools.javaParse.dto.JavaClassTypeInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportProjectInterfaceExcel {

    private static final String filePath = "/temp/ipos/";

    public static void main(String[] args){

        List<InterfaceInfo> interfaceInfoList = JavaUtil.scanProject();

        String fileName = "caes_interface";
        String fileFullPath = filePath + fileName + ".xlsx";
        File file = new File(fileFullPath);
        XSSFWorkbook workbook = new XSSFWorkbook();

        //遍历list,将数据写入Excel中
        generateSheet(workbook, interfaceInfoList, fileName);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 生成sheet数据
     * @param workbook workbook
     * @param data 待导出数据
     * @param sheetName 指定导出Excel的sheet名称
     */
    public static void generateSheet(Workbook workbook, List<InterfaceInfo> data, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if(sheet == null) {
            if (null != sheetName && !"".equals(sheetName)) {
                sheet = workbook.createSheet(sheetName);
            } else {
                sheet = workbook.createSheet();
            }
        }
        List<String> headFieldNameList = new ArrayList<>();
        headFieldNameList.add("classPath");
        headFieldNameList.add("className");
        headFieldNameList.add("methodName");
        headFieldNameList.add("reqMethod");
        headFieldNameList.add("reqPath");

        // 写数据
        int rowNum = 0;
        for(InterfaceInfo interfaceInfo:data){
            Row row = sheet.createRow(rowNum);
            int colNum = -1;
            row.createCell(++colNum).setCellValue(interfaceInfo.getClassPath());
            row.createCell(++colNum).setCellValue(interfaceInfo.getMethodName());
            row.createCell(++colNum).setCellValue(interfaceInfo.getReqMethod());
            row.createCell(++colNum).setCellValue(interfaceInfo.getReqPath());

            int methodCol = colNum;
            rowNum = writeRow(rowNum,methodCol,sheet,interfaceInfo.getInputFields(),"input");
            rowNum = writeRow(rowNum,methodCol,sheet,interfaceInfo.getOutputFields(),"output");
//            rowNum--;
            rowNum++;
        }
    }

    private static int writeRow(int curRowNum, int curColNum,Sheet sheet,List<JavaClassFieldInfo> javaClassFieldInfoList,String type){
        int rowNum = curRowNum;
        int colNum = curColNum;
        for(JavaClassFieldInfo javaClassFieldInfo: javaClassFieldInfoList){
            Row row = sheet.createRow(++rowNum);
            row.createCell(++colNum).setCellValue(type);
            row.createCell(++colNum).setCellValue(javaClassFieldInfo.getFieldName());
            row.createCell(++colNum).setCellValue(javaClassFieldInfo.getFieldType());
            row.createCell(++colNum).setCellValue(javaClassFieldInfo.getFieldDesc());
            row.createCell(++colNum).setCellValue(javaClassFieldInfo.getFieldInit());
            row.createCell(++colNum).setCellValue(javaClassFieldInfo.getFieldValue());

            if(CollectionUtils.isNotEmpty(javaClassFieldInfo.getSubFieldList())){
                rowNum = writeRow(rowNum,curColNum,sheet,javaClassFieldInfo.getSubFieldList(),type);
            }
            colNum = curColNum;
        }
        return rowNum;
    }
}
