package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.gdata.NQueryWrapper;
import com.hyw.platform.exception.BizException;
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

@Service("delNewMenu")
@Slf4j
public class DelNewMenu extends RequestFunUnit<String, DelNewMenu.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(DelNewMenu.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuParent()),"父级菜单不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getMenuSeq()),"菜单序号,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuDesc()),"菜单名称,不允许为空值!");

    }

    @Override
    public String execLogic(PublicReq publicReq, DelNewMenu.QueryVariable dto){

        WebMenu webMenu = dataService.getOne(new NQueryWrapper<WebMenu>()
                .eq(WebMenu::getMenu, dto.getMenu())
                .eq(WebMenu::getMenuParent, dto.getMenuParent()));
        BizException.trueThrow(webMenu==null,"查无记录!");

        dataService.delete(webMenu,"webMenuId");

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String menuParent;
        private String menuSeq;
        private String menu;
        private String menuDesc;
    }
}
