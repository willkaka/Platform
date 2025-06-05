package com.hyw.platform.web.util;

import com.hyw.platform.exception.BizException;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpressUtil {

    public static Object run(String express, IExpressContext<String,Object> context){
        Object result;
        try {
            ExpressRunner runner = new ExpressRunner(true, false);
            result = runner.execute(express, context, null, true, false);
        } catch (Exception e) {
            log.error("表达式(" + express + ")计算出错!", e);
            throw new BizException("表达式(" + express + ")计算出错!");
        }
        return result;
    }
}
