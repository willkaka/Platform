package com.hyw.platform.tools.javaParse.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JavaClassInfo {
    private String name; // 类名
    private String fullName; // 包含包路径类名
    private String packageStr; // 包路径
    private List<String> importList = new ArrayList<>(); // 引入包
    private List<String> argList = new ArrayList<>(); // 类变量
    private List<String> annotationList = new ArrayList<>(); // 注解
    private String comment; // 类注释

    private List<JavaClassFieldInfo> javaClassFieldInfoList = new ArrayList<>(); // 类成员变量

    private List<JavaClassMethodInfo> methodInfoList = new ArrayList<>();// 方法
}


