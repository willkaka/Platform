package com.hyw.platform.exception;

/**
 * 统一的业务异常处理基类
 * 框架已经做了统一异常处理
 * 所有的业务异常可继承此类通过抛出异常的方式来做业务校验等操作
 */
public class BizException extends FastRuntimeException {

    public BizException(String message) { super("9998", message); }

    public BizException(String code, String message) {
        super(code, message);
    }

    public BizException(String message, Exception e) {
        super(message, e);
    }

    public BizException(String code, String message, Exception e) {
        super(code, message, e);
    }

    public BizException(String code, String message, Object... params) {
        super(code, message);
        String msg = message;
        Exception e = null;
        for(Object param:params){
            if(param instanceof Exception){
                e = (Exception) param;
            }else {
                msg = msg.replaceFirst("\\{}", param.toString());
            }
        }
        if(e == null) {
            new BizException(code, msg);
        }else {
            new BizException(code, msg, e);
        }
    }

    public static void trueThrow(boolean b, String message, Object... params){
        if(!b) return;
        String msg = message;
        for(Object param:params){
            msg = msg.replaceFirst("\\{}",param.toString());
        }
        throw new BizException(msg);
    }
}
