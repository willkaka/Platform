package com.hyw.platform.funbean;

import com.hyw.platform.web.req.PublicReq;

import java.util.Map;

public interface WebDataReqFun {

    Map<String,Object> execute(PublicReq publicReq);
}
