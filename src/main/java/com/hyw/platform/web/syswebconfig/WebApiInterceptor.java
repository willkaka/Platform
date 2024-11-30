package com.hyw.platform.web.syswebconfig;

import com.hyw.platform.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口拦截器
 */
@Slf4j
@Service
public class WebApiInterceptor implements HandlerInterceptor {

    private final String httpStartTime = "HTTP_START_TIME";
    private final int MAXSIZE = 5000;
    public WebApiInterceptor(){
        log.info("webApiInterceptor");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 异常需要捕获，如果是业务异常（主动抛出的），就往上抛，如果是其他异常，则跳过校验（防止做不了交易）
        try {

        } catch (Exception e) {
            if (e instanceof BizException) {
                throw e;
            } else {
                log.error("非业务异常，跳过校验",e);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        // 异常需要捕获，如果是业务异常（主动抛出的），就往上抛，如果是其他异常，则跳过校验（防止做不了交易）
        try {

        } catch (Exception e) {
            if (e instanceof BizException) {
                throw e;
            } else {
                log.error("非业务异常，跳过校验",e);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                           Exception exception) throws Exception {
        // 异常需要捕获，如果是业务异常（主动抛出的），就往上抛，如果是其他异常，则跳过校验（防止做不了交易）
        try {

        } catch (Exception e) {
            if (e instanceof BizException) {
                throw e;
            } else {
                log.error("非业务异常，跳过校验",e);
            }
        }
    }
}
