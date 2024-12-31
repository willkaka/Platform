package com.hyw.platform.funbean.RequestFunImpl;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebElement;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("updSeq")
public class UpdSeq extends RequestFunUnit<String, UpdSeq.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param dto 参数
     */
    @Override
    public void checkVariable(UpdSeq.QueryVariable dto){
        //输入检查

//        BizException.trueThrow(StringUtils.isBlank(dto.getDirPath()),"文件夹路径,不允许为空值!");
//        BizException.trueThrow(StringUtils.isBlank(dto.getSearchText()),"查找关键词，不允许为空值!");
    }

    @Override
    public String execLogic(PublicReq publicReq, UpdSeq.QueryVariable dto){

        List<WebElement> webElementList = dataService.list(new NQueryWrapper<WebElement>());

        int seq = 0;
        for(WebElement webElement:webElementList){
            seq++;
            webElement.setElementNo(String.format("EM%04d",seq));
            webElement.setSortNo(seq);
        }
        dataService.updateById(webElementList);
        return null;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String dirPath;
        private String searchText;
    }
}
