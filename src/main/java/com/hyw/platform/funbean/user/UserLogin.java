package com.hyw.platform.funbean.user;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.UserInfo;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service("userLogin")
@Slf4j
public class UserLogin extends RequestFunUnit<String, UserLogin.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getUserId()),"用户ID不允许为空!");
        BizException.trueThrow(StringUtils.isBlank(variable.getPassword()),"密码不允许为空!");
    }

    @Override
    public String execLogic(PublicReq publicReq, QueryVariable dto){
        UserInfo userInfo = dataService.getOne(new NQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserId,dto.getUserId()));
        Assert.notNull(userInfo,"用户不存在!");
        Assert.isTrue(userInfo.getPassword().equals(dto.getPassword()),"密码错误!");
        return dto.getUserId();
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String userId;
        private String password;
    }
}