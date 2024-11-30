package com.hyw.platform.tools.javaParse;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.hyw.platform.tools.javaParse.dto.*;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaUtil {


    public static void main(String[] args){
//        scanOnePgm();
//        scanProject();
//        System.out.println(JSON.toJSONString(getJavaClassInfo("E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java")));

        List<JavaClassInfo> javaClassInfoList = getJavaClassInfo("E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java");

        Map<String,List<JavaClassFieldInfo>> classFieldListMap = new HashMap<>();
        javaClassInfoList.forEach(j->{
            if(StringUtils.isNotBlank(j.getFullName()))
                classFieldListMap.put(j.getFullName(),j.getJavaClassFieldInfoList());
//            System.out.println(j.getFullName());
        });
        System.out.println(JSON.toJSONString(classFieldListMap));
    }

    @Test
    public static void scanOnePgm(){
//        String classPath = "E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java\\com\\dashuf\\caes\\web\\controller\\webapi\\ClaimController.java";
        String classPath = "E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java\\com\\dashuf\\caes\\web\\controller\\sysapi\\DeductController.java";
//        String classPath = "E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java\\com\\dashuf\\caes\\dto\\BackBillDto.java";
//        String classPath = "D:\\Java\\HywSource\\webSite\\src\\test\\java\\com\\hyw\\webSite\\TestDto.java";
//        String classPath = "E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java\\com\\dashuf\\caes\\web\\Urls.java";

        ClassUnit classUnit = new ClassUnit();
        classUnit.visit(classPath,null);
//        JavaClassInfo javaClassInfo = classUnit.getJavaClassInfo();
        List<JavaClassInfo> javaClassInfoList = classUnit.getJavaClassInfoList();

        System.out.println(JSON.toJSONString(javaClassInfoList));
    }

    public static List<InterfaceInfo> scanProject(){
        List<JavaClassInfo> javaClassInfoList = getJavaClassInfo("E:\\ideaSpace\\DashufSource\\caes\\src\\main\\java");

        Map<String,List<JavaClassFieldInfo>> classFieldListMap = new HashMap<>();
        javaClassInfoList.forEach(j->{
            if(StringUtils.isNotBlank(j.getFullName()))
                classFieldListMap.put(j.getFullName(),j.getJavaClassFieldInfoList());
//            System.out.println(j.getFullName());
        });

        List<InterfaceInfo> interfaceInfoList = new ArrayList<>();
        for(JavaClassInfo javaClassInfo:javaClassInfoList){
            if(javaClassInfo.getAnnotationList().contains("@Controller") ||
                    javaClassInfo.getAnnotationList().contains("@RestController") ||
                    javaClassInfo.getAnnotationList().contains("@FeignClient")
            ){
                List<JavaClassMethodInfo> methodInfoList = javaClassInfo.getMethodInfoList();
                for(JavaClassMethodInfo javaClassMethodInfo:methodInfoList){
                    InterfaceInfo interfaceInfo = new InterfaceInfo();
                    interfaceInfo.setClassName(javaClassInfo.getName());
                    interfaceInfo.setClassPath((javaClassInfo.getPackageStr()+"."+javaClassInfo.getName()).replace(".","/"));
                    interfaceInfo.setMethodName(javaClassMethodInfo.getName());

                    //接口路径
                    String path = null;
                    String method = null;
                    for(String annotationStr:javaClassMethodInfo.getAnnotationList()){
                        if(annotationStr.indexOf("@PostMapping")>=0){
                            method = "post";
                            path = getStringFromQuotationMark(annotationStr);
                        }else if(annotationStr.indexOf("@GetMapping")>=0){
                            method = "get";
                            path = getStringFromQuotationMark(annotationStr);
                        }else if(annotationStr.indexOf("@RequestMapping")>=0){
                            method = "";
                            path = getStringFromQuotationMark(annotationStr);
                        }
                    }
                    interfaceInfo.setReqMethod(method);
                    interfaceInfo.setReqPath(path);

                    //接口输入参数
                    interfaceInfo.setInputFields(getFieldList(javaClassMethodInfo.getInputParamList(),classFieldListMap));

                    //接口输出参数
                    interfaceInfo.setOutputFields(getFieldList(javaClassMethodInfo.getOutputParamList(),classFieldListMap));

                    //接口输出参数
                    interfaceInfoList.add(interfaceInfo);
                }
            }
        }

        for(InterfaceInfo interfaceInfo : interfaceInfoList){
            for(JavaClassFieldInfo javaClassFieldInfo: interfaceInfo.getInputFields()){
                javaClassFieldInfo.setArgString(null);
            }
            for(JavaClassFieldInfo javaClassFieldInfo: interfaceInfo.getOutputFields()){
                javaClassFieldInfo.setArgString(null);
            }
        }

//        System.out.println(JSON.toJSONString(interfaceInfo2List));
        return interfaceInfoList;
    }

    public static List<JavaClassInfo> getJavaClassInfo(String projectRootPath){// .../src/main/java
        List<JavaClassInfo> javaClassInfoList = new ArrayList<>();

        //读取目录下所有*.java文件
        File file = new File(projectRootPath);
        List<String> filePathList = FileUtil.getFilePathListWithType(file,"java");
        for(String javaClassPath:filePathList){
            //解析java代码
            javaClassInfoList.addAll(getJavaClassInfo(javaClassPath, projectRootPath, javaClassInfoList));
        }

        //加工fullName
        List<String> classFullNameList = new ArrayList<>();
        javaClassInfoList.forEach(j->{
            classFullNameList.add(j.getFullName());
            System.out.println(j.getFullName());
        });

        for(JavaClassInfo javaClassInfo:javaClassInfoList){
            List<String> importList = includeStarStr(javaClassInfo.getImportList());
            if(CollectionUtils.isNotEmpty(importList)){
                List<JavaClassFieldInfo> javaClassFieldInfoList = javaClassInfo.getJavaClassFieldInfoList();
                for(JavaClassFieldInfo javaClassFieldInfo:javaClassFieldInfoList){
                    javaClassFieldInfo.setFieldTypeFullName(getFullClassName(importList,classFullNameList,
                            javaClassFieldInfo.getFieldType(),javaClassFieldInfo.getFieldTypeFullName()));
                }
                List<JavaClassMethodInfo> methodInfoList = javaClassInfo.getMethodInfoList();
                for(JavaClassMethodInfo javaClassMethodInfo:methodInfoList){
                    List<JavaClassMethodParamInfo> inputParamList = javaClassMethodInfo.getInputParamList();
                    for(JavaClassMethodParamInfo javaClassMethodParamInfo:inputParamList){
                        javaClassMethodParamInfo.setTypeFullName(getFullClassName(importList,classFullNameList,
                                javaClassMethodParamInfo.getType(),javaClassMethodParamInfo.getTypeFullName()));
                        setSubTypeFullName(importList,classFullNameList,javaClassMethodParamInfo.getSubTypeList());
                    }
                    List<JavaClassMethodParamInfo> outputParamList = javaClassMethodInfo.getOutputParamList();
                    for(JavaClassMethodParamInfo javaClassMethodParamInfo:outputParamList){
                        javaClassMethodParamInfo.setTypeFullName(getFullClassName(importList,classFullNameList,
                                javaClassMethodParamInfo.getType(),javaClassMethodParamInfo.getTypeFullName()));
                        setSubTypeFullName(importList,classFullNameList,javaClassMethodParamInfo.getSubTypeList());
                    }
                }
            }
        }

        return javaClassInfoList;
    }

    private static void setSubTypeFullName(List<String> importList,List<String> classFullNameList,
                                           List<JavaClassTypeInfo> typeFieldList){
        for(JavaClassTypeInfo javaClassTypeInfo:typeFieldList){
            javaClassTypeInfo.setTypeFullName(getFullClassName(importList,classFullNameList,
                    javaClassTypeInfo.getTypeName(), javaClassTypeInfo.getTypeFullName()));
            if(CollectionUtils.isNotEmpty(javaClassTypeInfo.getSubTypeList())){
                setSubTypeFullName(importList,classFullNameList,javaClassTypeInfo.getSubTypeList());
            }
        }
    }

    private static String getFullClassName(List<String> importList,List<String> classFullNameList,
                                           String curClassName,String curClassFullName){
        if(curClassName.equals(curClassFullName) && !curClassFullName.contains(".")){
            for(String imp:importList){
                String classFullName = imp.substring(0,imp.length()-2) + curClassName;
                if(classFullNameList.contains(classFullName)){
                    return classFullName;
                }
            }
        }
        return curClassFullName;
    }

    private static List<String> includeStarStr(List<String> importList){
        List<String> list = new ArrayList<>();
        for(String s:importList){
            if(s.endsWith(".*")) list.add(s);
        }
        return list;
    }
    private static boolean includeStar(List<String> importList){
        for(String s:importList){
            if(s.endsWith(".*")) return true;
        }
        return false;
    }


    public static List<JavaClassInfo> getJavaClassInfo(String classPath,String projectRootPath, List<JavaClassInfo> javaClassInfoList){
        ClassUnit classUnit = new ClassUnit();
        classUnit.visit(classPath, projectRootPath);
        return classUnit.getJavaClassInfoList();
    }


    private static List<JavaClassFieldInfo> getFieldList(List<JavaClassMethodParamInfo> paramInfoList,Map<String,List<JavaClassFieldInfo>> classFieldListMap){
        List<JavaClassFieldInfo> javaClassFieldInfoList = new ArrayList<>();
        for(JavaClassMethodParamInfo paramInfo:paramInfoList){
            JavaClassFieldInfo fieldInfo = new JavaClassFieldInfo();
            fieldInfo.setFieldName(paramInfo.getName());
            fieldInfo.setFieldType(paramInfo.getType());
            fieldInfo.setFieldTypeFullName(paramInfo.getTypeFullName());
            fieldInfo.setSubFieldList(classFieldListMap.get(paramInfo.getTypeFullName()));
            if(CollectionUtils.isNotEmpty(paramInfo.getSubTypeList())){
                fieldInfo.setSubFieldList(getTypeFieldList(paramInfo.getSubTypeList(),classFieldListMap));
            }
            javaClassFieldInfoList.add(fieldInfo);
        }
        return javaClassFieldInfoList;
    }

    private static List<JavaClassFieldInfo> getTypeFieldList(List<JavaClassTypeInfo> typeList,Map<String,List<JavaClassFieldInfo>> classFieldListMap){
        List<JavaClassFieldInfo> javaClassFieldInfoList = new ArrayList<>();
        for(JavaClassTypeInfo typeInfo:typeList){
            JavaClassFieldInfo fieldInfo = new JavaClassFieldInfo();
            fieldInfo.setFieldName(typeInfo.getTypeName());
            fieldInfo.setFieldType(typeInfo.getTypeName());
            fieldInfo.setFieldTypeFullName(typeInfo.getTypeFullName());
            fieldInfo.setSubFieldList(classFieldListMap.get(typeInfo.getTypeFullName()));
            if(CollectionUtils.isNotEmpty(typeInfo.getSubTypeList())){
                fieldInfo.setSubFieldList(getTypeFieldList(typeInfo.getSubTypeList(),classFieldListMap));
            }

            javaClassFieldInfoList.add(fieldInfo);
        }
        return javaClassFieldInfoList;
    }

    /**
     * 从双引号中取字符串
     * @param s 输入字符串
     */
    private static String getStringFromQuotationMark(String s){
        int beg = s.indexOf("(");
        int end = s.indexOf(")",beg+1);
        return s.substring(beg+1,end).replaceAll("\"","");
    }
}

@Data
class InterfaceInfo {
    private String className;
    private String classPath;
    private String methodName;
    private String reqMethod;
    private String reqPath;
    private List<JavaClassFieldInfo> inputFields;
    private List<JavaClassFieldInfo> outputFields;
}