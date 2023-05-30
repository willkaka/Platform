package com.hyw.platform.funbean.abs;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.RequestFun;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.PublicResp;
import com.hyw.platform.web.service.WebElementService;
import com.hyw.platform.web.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 功能请求实现单元基类
 */
@Slf4j
public abstract class RequestFunUnit<D, V extends RequestPubDto> implements RequestFun {

    @Autowired
    private WebElementService webElementService;

    /**
     * 执行入口
     * @param requestDto requestDto
     */
    @Override
    public PublicResp execute(PublicReq requestDto){
        // 参数赋值，将请求的参数 RequestDto，
        // 转为V（父类为RequestPubDto）并将requestDto.getReqParm().get("inputValue")中的值赋到对应的字段
        V var = getVariable(requestDto);

        // 必要的输入检查
        checkVariable(var);

        //执行自定义逻辑
        D data = execLogic(requestDto,var);

        //返回数据处理
        return returnData(requestDto,data,var);
    }

    /**
     * 自动取定义的参数值
     *   参数赋值，将请求的参数 RequestDto，
     * 转为V（父类为RequestPubDto）并将requestDto.getReqParm().get("inputValue")中的值赋到对应的字段
     * @param requestDto 请求dto
     * @return variable
     */
    private V getVariable(PublicReq requestDto){
        V var = newInstanceVariable();
        //处理参数
        Map<String,String> inputValue = requestDto.getWebValueDto().getValue();
        List<Field> fields = ObjectUtil.getAllFieldList(var.getClass());
        for(Field field:fields){
            String fieldName = field.getName();
            Object value = inputValue.get(fieldName);
            if(inputValue.containsKey(fieldName)){
                try {
                    if (!field.isAccessible()) { field.setAccessible(true); }
                    field.set(var, valueConvert(field,value));
                } catch (Exception e) {
                    throw new BizException("给对象(" + var.getClass().getName() + ")属性(" + fieldName + ")赋值(" + value + ")失败!");
                }
            }
        }
        return var;
    }

    private Object valueConvert(Field field,Object value){
        if(field.getType() == Integer.class) {
            return Integer.parseInt(value.toString());
        }else if(field.getType() == Double.class) {
            return Double.parseDouble(value.toString());
        }else if(field.getType() == Long.class) {
            return Long.parseLong(value.toString());
        }else if(field.getType() == BigDecimal.class) {
            return new BigDecimal(value.toString());
        }else if(field.getType() == LocalDate.class) {
            return LocalDate.parse(value.toString());
        }else if(field.getType() == LocalTime.class) {
            return LocalTime.parse(value.toString());
        }else if(field.getType() == LocalDateTime.class) {
            return LocalDateTime.parse(value.toString());
        }else{
            return value;
        }
    }

    /**
     * 输入参数检查
     * @param var 参数
     */
    public void checkVariable(V var){ }

    /**
     * 执行自定义逻辑
     * @param requestDto 请求dto
     * @param var 参数
     * @return D
     */
    public D execLogic(PublicReq requestDto, V var){
        return null;
    }

    /**
     * 返回数据处理
     * @param data 数据
     * @param variable 参数
     * @return ReturnDto
     */
    public PublicResp returnData(PublicReq requestDto, D data, V variable){
        PublicResp returnDto = new PublicResp();

        return returnDto;
    }

    /**
     * 字节流转字符流
     * @param bytes
     * @return
     */
    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    /**
     * 实例化变量
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
}
