package com.hyw.platform.web.resp;

import com.hyw.platform.web.resp.webElement.WebElementDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors( chain = true )
public class PublicResp {
    //报文返回信息
    private String rtnCode = "0000";
    private String rtnMsg = "Success";

    private List<WebElementDto> webElementDtoList;  //菜单元素
    private NextOprDto nextOprDto;            //后台执行完请求后，前台的下一操作
}


