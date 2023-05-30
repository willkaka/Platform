package com.hyw.platform.tools.javaParse.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JavaClassTypeInfo {
    private String typeName;
    private String typeFullName;
    private List<JavaClassFieldInfo> typeFieldList = new ArrayList<>(); // 方法参数
    private List<JavaClassTypeInfo> subTypeList;
}
