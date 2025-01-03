package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebCallAfter;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("callAfterAdd")
@Slf4j
public class CallAfterAdd extends RequestFunUnit<String, CallAfterAdd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(CallAfterAdd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getPage()),"页面,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getProcessBean()),"处理bean 不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getProcessStatus()),"处理状态 不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getOprType()),"操作类型 不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, CallAfterAdd.QueryVariable dto){
        WebCallAfter webCallAfter = new WebCallAfter();
        BeanUtils.copyProperties(dto, webCallAfter);
        dataService.save(webCallAfter);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String menu;
        private String page;
        private String processBean;
        private String processStatus;
        private String oprType;
        private String requestType;
        private String requestBean;
        private String param;
    }
}
