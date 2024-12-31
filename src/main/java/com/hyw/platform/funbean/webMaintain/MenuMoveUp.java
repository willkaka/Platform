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

@Service("menuMoveUp")
@Slf4j
public class MenuMoveUp extends RequestFunUnit<String, MenuMoveUp.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(MenuMoveUp.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getWebMenuId()),"菜单主键id不允许为空值!");

    }

    @Override
    public String execLogic(PublicReq publicReq, MenuMoveUp.QueryVariable dto){

        WebMenu webMenu = dataService.getOne(new NQueryWrapper<WebMenu>()
                .eq(WebMenu::getWebMenuId, dto.getWebMenuId()));
        Assert.isTrue(webMenu!=null, "查无记录!");

        // 查询上一排序号记录
        WebMenu webMenuPre = dataService.getOne(new NQueryWrapper<WebMenu>()
                .lt(WebMenu::getSortNo, webMenu.getSortNo())
                .orderByDesc(WebMenu::getSortNo));
        Assert.isTrue(webMenuPre!=null, "已无更小的排序号的记录!");

        webMenu.setSortNo(webMenuPre.getSortNo());
        webMenuPre.setSortNo(webMenuPre.getSortNo()+1);

        dataService.updateById(webMenuPre, "webMenuId");
        dataService.updateById(webMenu, "webMenuId");

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String webMenuId;
        private String menuNo;
        private String sortNo;
        private String menu;
        private String menuParent;
        private String menuDesc;
    }
}
