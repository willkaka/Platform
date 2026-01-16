package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebMenu;
import com.hyw.platform.web.req.PublicReq;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("menuQry")
@Slf4j
public class MenuQry extends RequestFunUnit<Map<String,Object>, MenuQry.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(MenuQry.QueryVariable variable){
    }

    @Override
    public Map<String,Object> execLogic(PublicReq publicReq, MenuQry.QueryVariable dto){
        List<WebMenu> list = dataService.list(new NQueryWrapper<WebMenu>()
                        .setTable(WebMenu.class)
                       .eq(StringUtils.isNotBlank(dto.getQueryConditionMenu()), WebMenu::getMenu, dto.getQueryConditionMenu())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionMenuDesc()), WebMenu::getMenuDesc, dto.getQueryConditionMenuDesc())
                .eq(StringUtils.isNotBlank(dto.getQueryConditionMenuParent()), WebMenu::getMenuParent, dto.getQueryConditionMenuParent())
        );
        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("data",list);
        return rtnMap;
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String queryConditionMenu;
        private String queryConditionMenuParent;
        private String queryConditionMenuDesc;
    }
}
