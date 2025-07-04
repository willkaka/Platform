package com.hyw.platform.tservice.http;

import com.hyw.platform.web.syswebconfig.MyThreadContext;
import com.hyw.platform.web.syswebconfig.UserContext;
import com.hyw.platform.web.syswebconfig.WebConstants;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Component
public class HttpServiceInvoker {

    private final RestTemplate restTemplate;

    public HttpServiceInvoker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 执行 GET 请求
     * @param url 请求地址
     * @param responseType 响应类型
     * @return 响应结果
     */
    public <T> T get(String url, Class<T> responseType) {
        return executeRequest(url, HttpMethod.GET, null, responseType);
    }

    /**
     * 执行 POST 请求
     * @param url 请求地址
     * @param body 请求体
     * @param responseType 响应类型
     * @return 响应结果
     */
    public <T> T post(String url, Object body, Class<T> responseType) {
        return executeRequest(url, HttpMethod.POST, body, responseType);
    }

    /**
     * 通用请求执行方法
     * @param url 请求地址
     * @param method HTTP 方法
     * @param body 请求体
     * @param responseType 响应类型
     * @return 响应结果
     */
    public <T> T executeRequest(String url, HttpMethod method, Object body, Class<T> responseType) {
        // 1. 创建请求头并添加 X-TRACE-ID
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 指定请求的字符编码
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        // 从线程上下文中获取 traceId
        String traceId = MyThreadContext.getTraceId();
        if (traceId != null && !traceId.isEmpty()) {
            headers.add(WebConstants.HEADER_FOR_TRACE_ID, traceId);
        }
        String userId = UserContext.id();
        if (userId!= null &&!userId.isEmpty()) {
            headers.add(WebConstants.HEADER_FOR_USER_ID, userId);
        }

        // 2. 创建请求实体
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        // 3. 执行请求
        ResponseEntity<T> response = restTemplate.exchange(
                url,
                method,
                requestEntity,
                responseType
        );

        // 4. 返回响应体
        return response.getBody();
    }
}