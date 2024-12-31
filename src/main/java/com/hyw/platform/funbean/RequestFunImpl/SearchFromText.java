package com.hyw.platform.funbean.RequestFunImpl;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.funbean.abs.RequestTableDataUnit;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.webElement.TableNormal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Service("searchFromText")
public class SearchFromText extends RequestTableDataUnit<SearchFromText.QueryVariable> {

    /**
     * 输入参数检查
     * @param dto 参数
     */
    @Override
    public void checkVariable(SearchFromText.QueryVariable dto){
        //输入检查

//        BizException.trueThrow(StringUtils.isBlank(dto.getDirPath()),"文件夹路径,不允许为空值!");
//        BizException.trueThrow(StringUtils.isBlank(dto.getSearchText()),"查找关键词，不允许为空值!");
    }

    @Override
    public TableNormal execLogic(PublicReq publicReq, SearchFromText.QueryVariable dto){

        //        File dirFile = new File("D:\\002385\\20_生产维护单");
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        if(StringUtils.isNotBlank(dto.getDirPath()) && StringUtils.isNotBlank(dto.getSearchText())) {
            File dirFile = new File(dto.getDirPath());
            List<String> pathList = readDirFile(dirFile, dto.getSearchText());
            for (String path : pathList) {
                Map<String, Object> record = new HashMap<>();
                record.put("路径", path);
                dataMaps.add(record);
            }
        }

        TableNormal tableNormal = new TableNormal();
        tableNormal.getRecordList().addAll(dataMaps);  //List<Map<String, Object>>

        //表头
        Map<String,String> headFieldMap = new LinkedHashMap<>();
        headFieldMap.put("路径","路径");
        tableNormal.getHeadMap().putAll(headFieldMap);
        return tableNormal;
    }

    private List<String> readDirFile(File dir,String searchText){
        if(!dir.isDirectory()) return new ArrayList<>();

        File [] files = dir.listFiles();
        if(files == null || files.length == 0) return new ArrayList<>();

        List<String> subFilePathList = new ArrayList<>();
        for(File file:files){
            if(file.isDirectory()){
                subFilePathList.addAll( readDirFile(file,searchText) );
            }else{
                if(file.getName().contains(searchText)){
                    subFilePathList.add(file.getPath());
                }else {
                    String filePath = readFile(file, searchText);
                    if (StringUtils.isNotBlank(filePath)) {
                        subFilePathList.add(filePath);
                    }
                }
            }
        }
        return subFilePathList;
    }

    private String readFile(File file,String searchText){
        if(file.getName().endsWith(".txt") || file.getName().endsWith(".text")){
            return readTextFileContent(file,searchText);
//        }else if(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")){
//            return readExcelFileContent(file,searchText);
        }else{
            return null;
        }
    }


    private String readWordDocxFileContent(File file, String searchText){
        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(file))) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String documentText = extractor.getText();

            if (documentText.contains(searchText)) {
                System.out.println(file.getPath());
                return file.getPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String readWordDocFileContent(File file, String searchText){
//        try (FileInputStream fis = new FileInputStream(file);
//             HWPFDocument doc = new HWPFDocument(fis);
//             WordExtractor extractor = new WordExtractor(doc)) {
//
//            String documentText = extractor.getText();
//
//            if (documentText.contains(searchText)) {
//                System.out.println(file.getPath());
//                return file.getPath();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private String readExcelFileContent(File file, String searchText){
        try (Workbook workbook = WorkbookFactory.create(Files.newInputStream(file.toPath()))) {
            int sheetCount = workbook.getNumberOfSheets();
            for(int sheetNo = 0;sheetNo < sheetCount;sheetNo++) {
                Sheet sheet = workbook.getSheetAt(sheetNo); // 获取第一个工作表

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if(cell.getCellType() == CellType.STRING.getCode()) {
                            String cellValue = cell.getStringCellValue();
                            if (cellValue.contains(searchText)) {
                                System.out.println(file.getPath());
                                return file.getPath();
                            }
                        }
                    }
                }
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readTextFileContent(File file, String searchText){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains(searchText)){
                    System.out.println(file.getPath());
                    return file.getPath();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String dirPath;
        private String searchText;
    }
}
