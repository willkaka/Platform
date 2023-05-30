package com.hyw.platform.web.exception;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.web.resp.PublicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 控制器增强处理(返回 JSON 格式数据)，添加了这个注解的类能被 classpath 扫描自动发现
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class) // 捕获 Controller 中抛出的指定类型的异常，也可以指定其他异常
    public PublicResp handler(Exception exception){
        PublicResp publicResp = new PublicResp();

        // 主动抛出的报错信息
        if (exception instanceof BizException){
            BizException bizException = (BizException) exception;
            publicResp.setRtnCode(bizException.getCode());
            publicResp.setRtnMsg(bizException.getMessage());

        // 未按要求发送报文
        } else if(exception instanceof HttpMessageNotReadableException){
            log.error("Required request body is missing!",exception);
            publicResp.setRtnCode("9998");
            publicResp.setRtnMsg("Required request body is missing!");

        // 其它未知异常
        } else {
            log.error("未知异常！",exception);
            publicResp.setRtnCode("9999");
            publicResp.setRtnMsg("未知异常！"+exception.getMessage());
        }
        return publicResp;
    }
}
