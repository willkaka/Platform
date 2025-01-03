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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("callAfterDel")
@Slf4j
public class CallAfterDel extends RequestFunUnit<String, CallAfterDel.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(CallAfterDel.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getWebCallAfterId()),"WebCallAfterId,不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, CallAfterDel.QueryVariable dto){
        WebCallAfter webCallAfter = dataService.getOne(new NQueryWrapper<WebCallAfter>()
                .eq(WebCallAfter::getWebCallAfterId, dto.getWebCallAfterId()));
        Assert.isTrue(webCallAfter!=null,"查无记录!");

        dataService.delete(webCallAfter, "webCallAfterId");
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
    }
}
