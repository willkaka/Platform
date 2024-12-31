package com.hyw.platform.funbean.RequestFunImpl;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("prcStringUtil")
@Slf4j
public class PrcStringUtil extends RequestFunUnit<String, PrcStringUtil.QryVariable> {

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(QryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getInputStr()),"输入字符串不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getColNum()),"列数不允许小于0!");
        try {
            int num = Integer.parseInt(variable.colNum);
        }catch (Exception e){
            throw new BizException("列数必须为整数!");
        }
    }

    /**
     * 执行自定义逻辑
     * @param requestDto 请求dto
     * @param variable 参数
     * @return D
     */
    @Override
    public String execLogic(PublicReq requestDto, QryVariable variable) {
        String inputString = variable.getInputStr();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.toCharArray()[i];
            switch (c) {
                case '\n':
                    sb.append("#");
                    break;
                case '\r':
                    sb.append("#");
                    break;
                case 32:
                    sb.append("#");
                    break;
                case '\t':
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        String[] list = sb.toString().split("#");
        StringBuffer outString = new StringBuffer();
        int colNum = 0;
        for (String s : list) {
            colNum ++;
            if ("single".equals(variable.getAddString())) outString.append("'");
            if ("double".equals(variable.getAddString())) outString.append("\"");
            outString.append(s);
            if ("single".equals(variable.getAddString())) outString.append("'");
            if ("double".equals(variable.getAddString())) outString.append("\"");
            if("comma".equals(variable.getSeparator()) && colNum!=list.length) outString.append(",");
            if("semicolon".equals(variable.getSeparator())) outString.append(";");
            if(colNum % Integer.parseInt(variable.getColNum()) == 0) outString.append("\n");
        }

        return outString.toString();
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QryVariable extends RequestPubDto {
        private String inputStr;  //输入字符串
        private String addString; //单双引号
        private String separator; //分隔符
        private String colNum;
    }
}
