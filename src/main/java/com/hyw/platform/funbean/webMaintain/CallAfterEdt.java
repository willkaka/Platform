package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
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
import org.springframework.util.Assert;

@Service("callAfterEdt")
@Slf4j
public class CallAfterEdt extends RequestFunUnit<String, CallAfterEdt.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(CallAfterEdt.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getPage()),"页面,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getProcessBean()),"处理bean 不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getProcessStatus()),"处理状态 不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getOprType()),"操作类型 不允许为空值!");

        BizException.trueThrow(!variable.getWebCallAfterId().equals(variable.getDefaultWebCallAfterId()),"WebCallAfterId 不允许修改!");
    }

    @Override
    public String execLogic(PublicReq publicReq, CallAfterEdt.QueryVariable dto){
        WebCallAfter webCallAfter = dataService.getOne(new NQueryWrapper<WebCallAfter>()
                .eq(WebCallAfter::getWebCallAfterId, dto.getDefaultWebCallAfterId()));
        Assert.isTrue(webCallAfter!=null,"查无记录!");

        BeanUtils.copyProperties(dto, webCallAfter);
        dataService.updateById(webCallAfter);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String webCallAfterId;
        private String menu;
        private String page;
        private String processBean;
        private String processStatus;
        private String oprType;
        private String requestType;
        private String requestBean;
        private String param;

        // 原值
        private String defaultWebCallAfterId;
        private String defaultMenu;
        private String defaultPage;
        private String defaultProcessBean;
        private String defaultProcessStatus;
        private String defaultOprType;
        private String defaultRequestType;
        private String defaultRequestBean;
        private String defaultParam;
    }
}
