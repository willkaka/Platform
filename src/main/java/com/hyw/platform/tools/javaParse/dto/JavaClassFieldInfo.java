package com.hyw.platform.tools.javaParse.dto;

import lombok.Data;

import java.util.List;

@Data
public class JavaClassFieldInfo {
    private String argString;//原定义代码字符串
    private String fieldName;//字段名
    private String fieldType;//字段类型
    private String fieldTypeFullName;//类型完整名称
    private List<JavaClassFieldInfo> subFieldList; // 子类型
    private String fieldAttr;// private/public static final
    private String fieldDesc;
    private String fieldInit;
    private String fieldValue;
}
