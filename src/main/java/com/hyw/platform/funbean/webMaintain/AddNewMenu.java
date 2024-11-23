package com.hyw.platform.funbean.webMaintain;

import com.hyw.gdata.DataService;
import com.hyw.platform.exception.BizException;
import com.hyw.platform.funbean.abs.RequestFunUnit;
import com.hyw.platform.funbean.abs.RequestPubDto;
import com.hyw.platform.web.model.WebMenu;
import com.hyw.platform.web.req.PublicReq;
import com.hyw.platform.web.resp.EventInfo;
import com.hyw.platform.web.resp.NextOprDto;
import com.hyw.platform.web.resp.PublicResp;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("addNewMenu")
@Slf4j
public class AddNewMenu extends RequestFunUnit<String, AddNewMenu.QueryVariable> {

    @Autowired
    private DataService dataService;

    /**
     * 输入参数检查
     * @param variable 参数
     */
    @Override
    public void checkVariable(AddNewMenu.QueryVariable variable){
        //输入检查
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuParent()),"父级菜单不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getMenu()),"菜单,不允许为空值!");

        BizException.trueThrow(StringUtils.isBlank(variable.getMenuSeq()),"菜单序号,不允许为空值!");
        BizException.trueThrow(StringUtils.isBlank(variable.getMenuDesc()),"菜单名称,不允许为空值!");

    }

    @Override
    public String execLogic(PublicReq publicReq, AddNewMenu.QueryVariable dto){
        WebMenu webMenu = new WebMenu();
        webMenu.setMenuParent(dto.getMenuParent());
        webMenu.setMenuSeq(Integer.parseInt(dto.getMenuSeq()));
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
        private String menuParent;
        private String menuSeq;
        private String menu;
        private String menuDesc;
    }
}
