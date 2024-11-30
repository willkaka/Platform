package com.hyw.platform.funbean;

import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.PublicResp;

public interface RequestFun {

    PublicResp execute(PublicReq requestDto);
}
