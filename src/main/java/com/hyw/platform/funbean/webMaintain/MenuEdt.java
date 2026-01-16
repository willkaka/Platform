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
import org.springframework.util.Assert;

@Service("menuEdt")
@Slf4j
public class MenuEdt extends RequestFunUnit<String, MenuEdt.QueryVariable> {

    @Autowired
    DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(MenuEdt.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuParent()),"父级菜单不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getSortNo()),"菜单序号,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuDesc()),"菜单名称,不允许为空值!");

        BizException.trueThrow(!variable.getWebMenuId().equals(variable.getDefaultWebMenuId()),"web_menu_id不允许修改!");
    }

    @Override
    public String execLogic(PublicReq publicReq, MenuEdt.QueryVariable dto){
        WebMenu webMenu = dataService.getOne(new NQueryWrapper<WebMenu>()
                .eq(WebMenu::getWebMenuId, dto.getWebMenuId()));
        Assert.isTrue(webMenu!=null,"查无记录!");

        webMenu.setMenuNo(dto.getMenuNo());
        webMenu.setSortNo(Integer.parseInt(dto.getSortNo()));
        webMenu.setMenu(dto.getMenu());
        webMenu.setMenuParent(dto.getMenuParent());
        webMenu.setMenuDesc(dto.getMenuDesc());
        dataService.updateById(webMenu);
        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        // 修改后的新值
        private String webMenuId;
        private String menuNo;
        private String sortNo;
        private String menu;
        private String menuParent;
        private String menuDesc;
        // 原值
        private String defaultWebMenuId;
        private String defaultMenuNo;
        private String defaultSortNo;
        private String defaultMenu;
        private String defaultMenuParent;
        private String defaultMenuDesc;
    }
}
