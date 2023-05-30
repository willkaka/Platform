package com.hyw.platform.web.resp.webElement;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors( chain = true )
public class TableNormal extends WebElementDto {

    private boolean isWithPage;//是否分页
    private int totalCount;//总记录数
    private int pageNow;//当前页码
    private int pageSize;//每页记录数

    private Map<String,String> headMap = new LinkedHashMap<>(); // [(loanNo,贷款编号),...]
    private List<Map<String, Object>> recordList = new ArrayList<>();//页面表格显示的数据记录 [(loanNo,"LN0001"),...]
//    private List<BaseEle> recordButtonList;//记录中的按钮
}
