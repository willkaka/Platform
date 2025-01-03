package com.hyw.platform.funbean.RequestFunImpl;

import com.hyw.platform.exception.BizException;
import com.hyw.platform.exception.FastRuntimeException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service("getTextContent")
public class GetTextContent extends RequestFunUnit<String, GetTextContent.QueryVariable> {

    /**
     * 输入参数检查
     * @param dto 参数
     */
    @Override
    public void checkVariable(GetTextContent.QueryVariable dto){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(dto.getFullPath()),"文件路径,不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, GetTextContent.QueryVariable dto){

        File file = new File(dto.getFullPath());
        if(!file.isFile()){
            throw new FastRuntimeException("文件路径不是文件!");
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("读文件数据异常!",e);
            throw new FastRuntimeException("读文件数据异常!");
        }

        return sb.toString();
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String fullPath;
    }
}
