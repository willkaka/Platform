package com.hyw.platform.funbean.abs;

import com.alibaba.fastjson.JSONObject;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.req.ValueObject;
import com.hyw.platform.web.req.WebValueDto;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 页面功能请求单元基类
 */
@Slf4j
public abstract class RequestWebUnit<D, V extends RequestWebUnit.Variable> implements RequestFun {

    /**
     * 执行入口
     * @param requestDto requestDto
     */
    @Override
    public PublicResp execute(PublicReq requestDto){
        //参数赋值
        V param = getVariable(requestDto);

        checkVariable(param);

        //执行自定义逻辑
        D data = execLogic(requestDto,param);

        //返回数据处理
        return returnData(data,param);
    }

    /**
     * 自动取定义的参数值
     * @param requestDto 请求dto
     * @return variable
     */
    private V getVariable(PublicReq requestDto){
        V param = newInstanceVariable();
        //处理参数
        JSONObject inputValue = requestDto.getWebValueDto();
        List<Field> fields = ObjectUtil.getAllFieldList(param.getClass());
        for(Field field:fields){
            String fieldName = field.getName();
            if(inputValue.containsKey(fieldName)){
                Object value = null;
                Object valueObject = inputValue.get(fieldName);
                if(valueObject instanceof ValueObject){
                    ValueObject webValueDto = (ValueObject) valueObject;
                    value = webValueDto.getValue();
                }else if(valueObject instanceof List){
//                    List<ValueObject> list = ((List) valueObject).toArray();
                }
                try {
                    if (!field.isAccessible()) { field.setAccessible(true); }
                    field.set(param,value);
                }catch (Exception e) {
                    throw new BizException("给对象("+param.getClass().getName()+")属性("+fieldName+")赋值("+value+")失败!");
                }
            }
        }
        return param;
    }

    /**
     * 输入参数检查
     * @param param 参数
     */
    public void checkVariable(V param){ }

    /**
     * 执行自定义逻辑
     * @param requestDto 请求dto
     * @param param 参数
     * @return D
     */
    public D execLogic(PublicReq requestDto, V param){
        return null;
    }

    /**
     * 返回数据处理
     * @param data 数据
     * @param param 参数
     * @return ReturnDto
     */
    public PublicResp returnData(D data, V param){
        PublicResp returnDto = new PublicResp();

//        returnDto.getOutputMap().put("showType", param.getOutputShowType());//以表格形式显示
//        returnDto.getOutputMap().put("withPage",param.isWithPage());//表格内容分页显示
//        returnDto.getOutputMap().put("isChanged",true); //标识输出区域已改变需要刷新
//        returnDto.getOutputMap().put("isClear",true);//清除原有输出内容
//
//        boolean isListMapFieldAttr = false;
//        if(data instanceof List){
//            for(Object object:(List) data){
//                if(object instanceof Map){
//                    for(Object key:((Map) object).keySet()){
//                        if(key instanceof String && ((Map) object).get(key) instanceof FieldAttr){
//                            isListMapFieldAttr = true;
//                            break;
//                        }
//                    }
//                }else{
//
//                }
//                if(isListMapFieldAttr) break;
//            }
//        }else if(data instanceof String){
//            returnDto.getOutputMap().put("textAreaValue", data);
//        }
//
//        if(isListMapFieldAttr){
//            returnDto.getOutputMap().put("tableRecordList", data);
//            returnDto.getOutputMap().put("totalCount", param.getTotalCount());
//            returnDto.getOutputMap().put("pageNow", param.getPageNow());
//            returnDto.getOutputMap().put("pageSize", param.getPageSize());
//        }

        return returnDto;
    }


    /**
     * 实例化变量
     *
     * @return 返回实例化的变量信息
     */
    public V newInstanceVariable() {
        Class<V> vClass = null;
        try {
            Type genericSuperclass = null;
            for (Class clazz = getClass();
                 clazz != null && !((genericSuperclass = clazz.getGenericSuperclass()) instanceof ParameterizedType);
                 clazz = getClass().getSuperclass()) {}

            ParameterizedType type = (ParameterizedType) genericSuperclass;
            Type actualTypeArgument = type.getActualTypeArguments()[1];
            //noinspection unchecked
            vClass = (Class<V>) (actualTypeArgument instanceof Class ? actualTypeArgument : ((ParameterizedType) actualTypeArgument).getRawType());
        } catch (Exception e) {
            throw new RuntimeException(getClass() + " 缺少泛型", e);
        }
        try {
            return vClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(vClass + " 实例化失败", e);
        }
    }

    /**
     * 自定义变量
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Variable {
        private String outputShowType; //输出显示格式类型
        private boolean isWithPage; //是否分页
        private int totalCount;
        private int pageNow;
        private int pageSize;
    }
}
