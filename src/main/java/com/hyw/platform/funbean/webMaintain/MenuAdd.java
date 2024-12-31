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

@Service("menuAdd")
@Slf4j
public class MenuAdd extends RequestFunUnit<String, MenuAdd.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(MenuAdd.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuParent()),"父级菜单不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");

//        BizException.trueThrow(StringUtils.isBlank(variable.getSortNo()),"菜单序号,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuDesc()),"菜单名称,不允许为空值!");

    }

    @Override
    public String execLogic(PublicReq publicReq, MenuAdd.QueryVariable dto){
        @SuppressWarnings("unchecked")
        WebMenu webMenuTemp = dataService.getOne(new NQueryWrapper<WebMenu>().orderByDesc(WebMenu::getMenuNo));
        int maxMenuNo = webMenuTemp==null?0:Integer.parseInt(webMenuTemp.getMenuNo().replace("MN",""));
        String menuNo = String.format("MN%04d",maxMenuNo+1);

        @SuppressWarnings("unchecked")
        WebMenu webMenuTemp2 = dataService.getOne(new NQueryWrapper<WebMenu>().orderByDesc(WebMenu::getSortNo));
        int sortNo = webMenuTemp2==null?0:webMenuTemp2.getSortNo();

        WebMenu webMenu = new WebMenu();
        webMenu.setMenuNo(menuNo);
        webMenu.setMenuParent(dto.getMenuParent());
        webMenu.setSortNo(sortNo+1);
        webMenu.setMenu(dto.getMenu());
        webMenu.setMenuDesc(dto.getMenuDesc());
        dataService.save(webMenu);

        return "";
    }

    /**
     * 输入输出参数
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class QueryVariable extends RequestPubDto {
        private String menu;
        private String sortNo;
        private String menuParent;
        private String menuDesc;
    }
}
