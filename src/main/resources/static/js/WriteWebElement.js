
/**
  * PublicResp
  * eventInfo 原事件信息
  * {"rtnCode":"","nextOprDto":{"eventInfoList":[]},"webElementDtoList":[]}
 **/
function sucFreshAll(PublicResp,eventInfo,requestParam){
    let rtnCode = PublicResp.rtnCode;
    let elementDtoList = PublicResp.webElementDtoList;
    if(null!=elementDtoList){
        // 菜单事件，清空输入输出区域元素
        if(eventInfo!=null && eventInfo.reqType=="menuReq"){
            openTab(eventInfo.menu);
            clearChildren("outputArea");
        }
        loopElementList(elementDtoList,eventInfo);
    }
    let nextOprDto = PublicResp.nextOprDto;
    if(null != nextOprDto){
        nextOpr(PublicResp,nextOprDto,requestParam);
    }
}

function nextOpr(PublicResp,nextOprDto,requestParam){
    let eventInfoList = nextOprDto["eventInfoList"];
    if(null == eventInfoList) return;
    for(let i=0;i<eventInfoList.length;i++){
        let eventInfo = eventInfoList[i];
        if(null != eventInfo && eventInfo.event == "showMessage"){
            let msg = eventInfo.paramMap["msg"];
            if(PublicResp.rtnCode != "0000"){
                alert(msg+PublicResp.rtnCode + "\n失败信息:" + PublicResp.rtnMsg);
            }else{
                alert(msg);
            }
        }
        if(null != eventInfo && eventInfo.event == "request"){
            if(eventInfo.paramMap == null){
                eventInfo.paramMap = {};
            }
        //{"element":"dsp_eve_button","event":"click","menu":"MenuMaintain","nextPage":"54","page":"start_page",
        // "reqMapping":"getWebElement","reqPage":0,"reqType":"webDataReq","withPage":false}

        // {"event":"request","menu":"MenuMaintain","page":"edt_ele_sbw","reqMapping":"getWebElement","reqPage":0,"reqType":"webDataReq","withPage":false}
            executeEventMethod(eventInfo,null);
        }
        if(null != eventInfo && eventInfo.event == "closeSw"){
            let subWindowId = eventInfo.paramMap["subWindowId"];
            hideById(subWindowId); //add_sub_window
        }

        if(null != eventInfo && eventInfo.event == "removeEle"){
            let subWindowId = eventInfo.paramMap["removeEleId"];
            removeElementById(subWindowId);
        }
        if(null != eventInfo && eventInfo.event == "loginSucSetUserInfo"){
            //"{"reqParm":{},"curMenu":"","userName":"",
            //  "webValueDto":{"curMenu":"",
            //    "webInputValueMap":{"userId":{"value":"admin","defValue":""},"password":{"value":"123456","defValue":""}}},
            //  "eventInfo":{"event":"click","reqType":"buttonReq","reqMapping":"userLogin","reqMethod":null,"menu":"root","page":"start_page","element":"login","triggerType":null,"triggerElement":null,"triggerElementType":null,"nextPage":null,"selectedValue":null,"withPage":false,"reqPage":0,"recordMap":null,"paramMap":null,"triggerParamMap":null,"relEleId":null,"relEleType":null,"relEleChgType":null}}"
            let reqJson = JSON.parse(requestParam);
            let reqWebValue = reqJson.webValueDto;
            let userFieldDto = reqWebValue.webInputValueMap["userId"];
            let userId = userFieldDto.value;
            setCookie(userNameKey, userId, 1);
            const userLabel = document.getElementById('userNameLabel'); // 获取标签元素
            if (userId) {
               userLabel.textContent = userId; // 显示用户名
            } else {
               userLabel.textContent = "未登录用户"; // 默认文本
            }
        }
    }
}

function loopElementList(elementDtoList,eventInfo){
    for (let i=0;i<elementDtoList.length;i++){
        let elementDto = elementDtoList[i];
        // 如果 eventInfo.paramMap含有参数onlyUpd且等于"Y"，则只更新元素
        if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap.hasOwnProperty("onlyUpd") && eventInfo.paramMap.onlyUpd=="Y") {
            updateWebElement(elementDto,eventInfo);
            continue;
        }
        writeWebElementRoute(elementDto.pid,elementDto,eventInfo);
        let subElementDtoList = elementDto.subElementList;
        if(subElementDtoList!=null && subElementDtoList.length>0){
            loopElementList(subElementDtoList,eventInfo);
        }
    }
}

function updateWebElement(elementInfo,eventInfo){
    let elementId = elementInfo.id;
    let element = document.getElementById(elementId);
    if(element == null) return;
    if(elementInfo.type == "selectOption"){
        // 清空select的option元素
        element.textContent = "";
        let dataMap = elementInfo.data;
        for(let value in dataMap){
            let option = document.createElement("option");
            option.setAttribute("id",elementInfo.id+"_option_"+value);
            option.setAttribute("value",value);
            option.textContent = dataMap[value];
            element.appendChild(option);
        }
    }
}

/**
 * 生成页面元素路由
 **/
function writeWebElementRoute(parentEleId,elementInfo,eventInfo){
    if(elementInfo.id != "body") removeElementById(elementInfo.id);

    let parentEle;
    parentEle = document.getElementById(parentEleId);
    if(parentEle == null) {
        parentEle = document.getElementById(parentEleId+"_group");
        if(parentEle == null) return;
    }

    if(elementInfo.type == "Group") writeGroup(parentEle,elementInfo);
    if(elementInfo.type == "Menu") writeMenu(parentEle,elementInfo);
    if(elementInfo.type == "div") writeDiv(parentEle,elementInfo);
    if(elementInfo.type == "divTitle") writeDivTitle(parentEle,elementInfo);
    if(elementInfo.type == "button") writeButton(parentEle,elementInfo);

    if(elementInfo.type == "table") writeTableLabel(parentEle,elementInfo);
    if(elementInfo.type == "table_record_button") writeTableButton(parentEle,elementInfo);
    if(elementInfo.type == "table_record_radio") writeTableRadio(parentEle,elementInfo);

    if(elementInfo.type == "textarea") writeTextArea(parentEle,elementInfo,eventInfo);
    if(elementInfo.type == "textareaLabel") writeTextAreaLabel(parentEle,elementInfo,eventInfo);
    if(elementInfo.type == "input") writeInput(parentEle,elementInfo,eventInfo);
    if(elementInfo.type == "inputWithoutLabel") writeInputWithoutLabel(parentEle,elementInfo,eventInfo);
    if(elementInfo.type == "inputFile") writeInputFile(parentEle,elementInfo);
    if(elementInfo.type == "spanGroup") writeSpanGroup(parentEle,elementInfo);
    if(elementInfo.type == "dropDown") writeDropDown(parentEle,elementInfo);
    if(elementInfo.type == "inputDataList") writeInputDataList(parentEle,elementInfo);
    if(elementInfo.type == "selectOption") writeSelectOption(parentEle,elementInfo);
    if(elementInfo.type == "multipleSelect") writeMultipleSelect(parentEle,elementInfo);

    if(elementInfo.type == "span") writeSpan(parentEle,elementInfo);
    if(elementInfo.type == "canvas") writeCanvas(parentEle,elementInfo);

    if(elementInfo.type == "subWindow") writeSubWindow(parentEle,elementInfo);
}

function writeSpan(parentEle,elementInfo){
    let element_span = document.createElement("span");
    element_span.setAttribute("id",elementInfo.id); //id
    element_span.setAttribute("eleName",elementInfo.elementName); //名称
    element_span.textContent = elementInfo.desc;//名称
    setAttr(element_span,elementInfo.attrMap); // 属性配置
    setEventListener(element_span,elementInfo.eventInfoList); //事件

    parentEle.appendChild(element_span);
}

function writeDiv(parentEle,elementInfo){
    let element_div = document.createElement("div");
    element_div.setAttribute("id",elementInfo.id); //id
    element_div.setAttribute("eleName",elementInfo.elementName); //名称
    setAttr(element_div,elementInfo.attrMap); // 属性配置
    setEventListener(element_div,elementInfo.eventInfoList); //事件

    if(elementInfo.param != null && elementInfo.param["showText"]!=null && elementInfo.param["showText"]){
        element_div.textContent = elementInfo.desc;//名称
    }

    parentEle.appendChild(element_div);
}

function writeDivTitle(parentEle,elementInfo){
    let element_div = document.createElement("div");
    element_div.setAttribute("id",elementInfo.id); //id
    element_div.setAttribute("eleName",elementInfo.elementName); //名称
    setAttr(element_div,elementInfo.attrMap); // 属性配置
    setEventListener(element_div,elementInfo.eventInfoList); //事件
    element_div.textContent = elementInfo.desc;//名称

    parentEle.appendChild(element_div);
}

/**
  * 左测边栏菜单
      <div class="nav-item">
          <div class="nav-link">
              <i class="fas fa-users"></i>
              <span>用户管理</span>
              <i class="fas fa-chevron-right submenu-toggle"></i>
          </div>
  **/
function writeGroup(parentEle,elementInfo){
    if(elementInfo.sortNo==1){
        let temp_div = document.createElement("div");
        temp_div.style.height = "10px";
        parentEle.appendChild(temp_div);
    }
    let item_div = document.createElement("div");
    item_div.setAttribute("class","nav-item");

    let link_div = document.createElement("div");
    link_div.setAttribute("class","nav-link");
    // 增加点击事件，调menuClicked方法，并传递elementInfo.eventInfoList
    link_div.addEventListener('click', function(e) {
        menuClicked(e,elementInfo);
        e.stopPropagation(); // 阻止冒泡，避免触发 link_div 的点击
        // 切换 active 类
        link_div.classList.toggle("active");
    })
    item_div.appendChild(link_div);

    let span = document.createElement("span");
    span.textContent = elementInfo.desc;//名称
    link_div.appendChild(span);

    let i_b = document.createElement("i");
    i_b.classList.add("fas", "fa-chevron-right", "submenu-toggle");
    link_div.appendChild(i_b);

    let sub_menu_div = document.createElement("div");
    sub_menu_div.setAttribute("class","submenu");
    sub_menu_div.setAttribute("id",elementInfo.id);
    sub_menu_div.setAttribute("eleName",elementInfo.elementName); //名称
    item_div.appendChild(sub_menu_div);
    parentEle.appendChild(item_div);
}


function menuClicked(e, elementInfo){
//    console.log("menuClicked");
    // 判断elementInfo.type,为Group时，展开子菜单
    if(elementInfo.type == "Group"){
        let submenu = document.getElementById(elementInfo.id);
        submenu.classList.toggle('open');
        return;
    }
    // 判断elementInfo.type,为Menu时，执行事件
    if(elementInfo.type == "Menu"){
        let eventInfoList = elementInfo.eventInfoList;
        if(null == eventInfoList) return;
        for(let i=0;i<eventInfoList.length;i++){
            let eventInfo = eventInfoList[i];
            executeEventMethod(eventInfo,null);
        }
    }
}

/**
 *  下拉式菜单
*    <div class="menuDropDown">
        <a class="menuDropButton" href="#">下拉菜单</a>
        <div class="menuDropDown-content">
            <a href="#">链接 1</a>
            <a href="#">链接 2</a>
            <a href="#">链接 3</a>
        </div>
    </div>
 **/
function writeGroup_dropDown(parentEle,elementInfo){
    let element_div = document.createElement("div");
    element_div.setAttribute("class","menuDropDown");

    let element_a = document.createElement("a");
    element_a.setAttribute("class","menuDropButton");

    element_a.textContent = elementInfo.desc;//菜单名称
    setAttr(element_a,elementInfo.attrMap); // 属性配置
    element_div.appendChild(element_a);

    let element_sub_div = document.createElement("div");
    element_sub_div.setAttribute("class","menuDropDown-content");
    element_sub_div.setAttribute("id",elementInfo.id);
    element_sub_div.setAttribute("eleName",elementInfo.elementName); //名称
    element_div.appendChild(element_sub_div);
    parentEle.appendChild(element_div);
}

/**
 * 左测边栏菜单
     <div class="submenu">
         <div class="submenu-link" data-tab="user-list">
             <span>用户列表</span>
         </div>
         <div class="submenu-link" data-tab="user-groups">
             <span>用户组管理</span>
         </div>
         <div class="submenu-link" data-tab="permissions">
             <span>权限分配</span>
         </div>
     </div>
 **/
function writeMenu(parentEle,elementInfo){
    menuNameMap[elementInfo.elementName] = elementInfo.desc;
    let submenu_div = document.createElement("div");
    submenu_div.setAttribute("class","submenu-link");
    // 增加点击事件，调menuClicked方法，并传递elementInfo.eventInfoList
    submenu_div.addEventListener('click', function(e) {
        menuClicked(e, elementInfo);
    });
    submenu_div.setAttribute("data-tab",elementInfo.id);

    let span = document.createElement("span");
    span.textContent = elementInfo.desc;//名称
    submenu_div.appendChild(span);

    parentEle.appendChild(submenu_div);

    // 记录菜单信息
    tabData[elementInfo.id] = {
        title: elementInfo.desc,
        icon: elementInfo.icon==null?"fa-tachometer-alt":elementInfo.icon
    }
}

/**
 * 下拉式菜单
 **/
function writeMenu_dropDown(parentEle,elementInfo){
    let element_a = document.createElement("a");
    element_a.setAttribute("id",elementInfo.id);
    element_a.setAttribute("eleName",elementInfo.elementName); //名称
    element_a.textContent = elementInfo.desc;//菜单名称
    setAttr(element_a,elementInfo.attrMap); // 属性配置
    setEventListener(element_a,elementInfo.eventInfoList); //事件
    parentEle.appendChild(element_a);
}

/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeInputWithoutLabel(parentEle,elementInfo,eventInfo){
    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    if(null != elementInfo.desc){
        input.setAttribute("placeholder",elementInfo.desc);
    }
    if(null != elementInfo.defValue){
        input.setAttribute("value",elementInfo.defValue);
    }
    if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap["valueFromSelectedRecord"]){
        input.setAttribute("value", eventInfo.paramMap[elementInfo.id])
        input.setAttribute("defaultValue", eventInfo.paramMap[elementInfo.id])
    }
    input.setAttribute("class","inputArea_sub_input");
    appendChildAtSeq(parentEle,input,elementInfo.seq);
}

/**
 * 填充输出区域_平铺  textarea
 * @param rtnMap
 */
function writeTextArea(parentEle,elementInfo,eventInfo){
    let textArea = document.createElement("textarea");
    textArea.setAttribute("id",elementInfo.id);
    textArea.setAttribute("eleName",elementInfo.elementName); //名称
    textArea.setAttribute("class","output_textArea");
    setAttr(textArea,elementInfo.attrMap);
    textArea.textContent = elementInfo.data;
    parentEle.appendChild(textArea);
}


/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeTextAreaLabel(parentEle,elementInfo,eventInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("textarea");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    if(null != elementInfo.desc){
        input.setAttribute("placeholder",elementInfo.desc);
    }
    // 不为undefined时，设置默认值
    if(null != elementInfo.defValue && elementInfo.defValue != "undefined"){
        input.textContent = elementInfo.defValue;
    }
    // 从页面中取值
    if(elementInfo.param!=null && elementInfo.param["valueFrmWeb"]){
        let webFieldName = elementInfo.param["valueFrmWeb"];
        let webFieldValueMap = getInputValueMap(eventInfo);
        for(let key in webFieldValueMap){
            if(key == webFieldName){
                input.textContent = webFieldValueMap[key].value;
            }
        }
    }
    if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap["valueFromSelectedRecord"] && eventInfo.paramMap[elementInfo.elementName]!=null){
        input.textContent = eventInfo.paramMap[elementInfo.elementName];
        input.setAttribute("defaultValue", eventInfo.paramMap[elementInfo.elementName]);
    }
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    input.setAttribute("class","inputArea_sub_input");
    groupDiv.appendChild(input);

    if(elementInfo.param != null && elementInfo.param["hide"]!=null && elementInfo.param["hide"]){
        groupDiv.setAttribute("class","display-none");
    }
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}

/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeSpanGroup(parentEle,elementInfo,eventInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("span");
    label.textContent = elementInfo.desc + ":";//名称
    groupDiv.appendChild(label);

    let input = document.createElement("span");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    // 不为undefined时，设置默认值
    if(null != elementInfo.defValue && elementInfo.defValue != "undefined"){
        input.setAttribute("value",elementInfo.defValue);
    }
    // 从页面中取值
    if(elementInfo.param!=null && elementInfo.param["valueFrmWeb"]){
        let webFieldName = elementInfo.param["valueFrmWeb"];
        let webFieldValueMap = getInputValueMap(eventInfo);
        for(let key in webFieldValueMap){
            if(key == webFieldName){
                input.setAttribute("value",webFieldValueMap[key].value);
            }
        }
    }
    if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap["valueFromSelectedRecord"] && eventInfo.paramMap[elementInfo.elementName]!=null){
        input.setAttribute("value", eventInfo.paramMap[elementInfo.elementName])
        input.setAttribute("defaultValue", eventInfo.paramMap[elementInfo.elementName])
    }
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    input.setAttribute("class","inputArea_sub_input");
    groupDiv.appendChild(input);

    if(elementInfo.param != null && elementInfo.param["hide"]!=null && elementInfo.param["hide"]){
        groupDiv.setAttribute("class","display-none");
    }
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}

/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeInput(parentEle,elementInfo,eventInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    if(null != elementInfo.desc){
        input.setAttribute("placeholder",elementInfo.desc);
    }
    // 不为undefined时，设置默认值
    if(null != elementInfo.defValue && elementInfo.defValue != "undefined"){
        input.setAttribute("value",elementInfo.defValue);
    }
    // 从页面中取值
    if(elementInfo.param!=null && elementInfo.param["valueFrmWeb"]){
        let webFieldName = elementInfo.param["valueFrmWeb"];
//        let webFieldValueMap = getAllInputValueMap();
        let webFieldValueMap = getInputValueMap(eventInfo);
        for(let key in webFieldValueMap){
            if(key == webFieldName){
                input.setAttribute("value",webFieldValueMap[key].value);
            }
        }
    }
    if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap["valueFromSelectedRecord"] && eventInfo.paramMap[elementInfo.elementName]!=null){
        input.setAttribute("value", eventInfo.paramMap[elementInfo.elementName])
        input.setAttribute("defaultValue", eventInfo.paramMap[elementInfo.elementName])
    }
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    input.setAttribute("class","inputArea_sub_input");
    groupDiv.appendChild(input);

    if(elementInfo.param != null && elementInfo.param["hide"]!=null && elementInfo.param["hide"]){
        groupDiv.setAttribute("class","display-none");
    }
//    parentEle.appendChild(groupDiv);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}


/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeInputFile(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    input.setAttribute("type","file");
    input.setAttribute("class","inputArea_sub_input");
    input.setAttribute("placeholder",elementInfo.desc);
    input.setAttribute("multiple",null);
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    groupDiv.appendChild(input);

    parentEle.appendChild(groupDiv);
}

/**
 * 在父元素插入生成的下拉选择框 div label input/option
 **/
function writeInputDataList(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("eleName",elementInfo.elementName); //名称
    input.setAttribute("list","datalist"+elementInfo.id);
    input.setAttribute("class","inputArea_sub_input");
    setEventListener(input,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    let dataList = document.createElement("datalist");
    dataList.setAttribute("id","datalist"+elementInfo.id);
    dataList.setAttribute("eleName","datalist"+elementInfo.elementName);

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("value",value);
        option.setAttribute("name",dataMap[value]);
        dataList.appendChild(option);
    }
    groupDiv.appendChild(input);
    groupDiv.appendChild(dataList);
    parentEle.appendChild(groupDiv);
}

function writeMultipleSelect(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("eleName",elementInfo.elementName+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
    select.setAttribute("eleName",elementInfo.elementName); //名称
    select.setAttribute("multiple","multiple");
    setEventListener(select,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(select,elementInfo.attrMap);
    }
    groupDiv.appendChild(select);
//    parentEle.appendChild(groupDiv);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);

    let selectOptions = [];
    let dataMap = elementInfo.data;
    for(let mapKey in dataMap){
        selectOptions.push({label: dataMap[mapKey], title: dataMap[mapKey], value: mapKey});
    }
    let multipleSelect2 = $("#"+elementInfo.id);
    multipleSelect2.multiselect({
                                enableFiltering: true,
                                includeSelectAllOption: true,
                                nonSelectedText: '请选择',
                                numberDisplayed: 1,
                                nSelectedText: '个已选!',
                                selectAllText: '全选',
                                allSelectedText: '已全选',
                                selectedClass: 'active multiselect-selected',
                                optionClass: function(element) {
                                    var value = $(element).val();
                                    return value%2 == 0?'even':'odd'; }
                           });
    multipleSelect2.multiselect('dataprovider', selectOptions);
}

/**
 * 在父元素插入生成的下拉选择框 div label select/option
 <select>
   <option value="1">Volvo</option>
   <option value="2">Saab</option>
 </select>
 **/
function writeSelectOption(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
    select.setAttribute("eleName",elementInfo.elementName); //名称
    select.setAttribute("class","inputArea_sub_select");
    setEventListener(select,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(select,elementInfo.attrMap);
    }

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("id",elementInfo.id+"_option_"+value);
        option.setAttribute("value",value);
        option.textContent = dataMap[value];
        select.appendChild(option);
    }
    groupDiv.appendChild(select);
//    parentEle.appendChildAtSeq(groupDiv);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}

/**
 * 在父元素插入生成的下拉选择框 div label select/option
 **/
function writeDropDown(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("id",elementInfo.id+"_label");
    label.setAttribute("class","inputArea_sub_label");
    label.textContent = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
    select.setAttribute("eleName",elementInfo.elementName); //名称
    select.setAttribute("class","inputArea_sub_select");
    //setAttr(select,elementInfo.attrMap); // 属性配置
    setEventListener(select,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(select,elementInfo.attrMap);
    }

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("id",elementInfo.id+"_option_"+value);
        option.setAttribute("value",value);
        option.textContent = dataMap[value];
        select.appendChild(option);
    }
    groupDiv.appendChild(select);
//    parentEle.appendChild(groupDiv);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}

/**
 * 在父元素插入生成的按钮
 **/
function writeButton(parentEle,elementInfo){
    let button = document.createElement("button");
    button.setAttribute("id",elementInfo.id);
    button.setAttribute("eleName",elementInfo.elementName); //名称
    setEventListener(button,elementInfo.eventInfoList); //事件
    setAttr(button,elementInfo.attrMap); // 属性配置
    let span = document.createElement("span");
    span.textContent = elementInfo.desc;//名称
    button.appendChild(span);

    appendChildAtSeq(parentEle,button,elementInfo.seq);
}



function writeTableLabel(parentEle,elementInfo){
    let tableNormal = elementInfo.data;//表信息
    let headMap = tableNormal.headMap;//表头
    let recordList = tableNormal.recordList;//记录

    //已存在，则先删除
    let ele = document.getElementById(elementInfo.id+"_group");
    if(null != ele){
        ele.parentNode.removeChild(ele);
    }

    let table_group_div = document.createElement("div");
    table_group_div.setAttribute("id",elementInfo.id+"_group");
    table_group_div.setAttribute("eleName",elementInfo.elementName+"_group");
    table_group_div.setAttribute("class","table_group_div");
    appendChildAtSeq(parentEle,table_group_div,elementInfo.seq);

    //计算表格列宽
    let colSizeMap = {};

    //表头与表体分开两个table
    let thead_div = document.createElement("div");
    thead_div.setAttribute("class","table_head_div");
    thead_div.setAttribute("id",elementInfo.id+"_head_div");
    table_group_div.appendChild(thead_div);

    let head_table = document.createElement("table");
    head_table.setAttribute("id",elementInfo.id+"_head_table");
    setAttr(head_table,elementInfo.attrMap); // 属性配置
    thead_div.appendChild(head_table);

    //表头
    let element_thead = document.createElement("thead");
    element_thead.setAttribute("class","table_head");
    element_thead.setAttribute("id",elementInfo.id+"_thead");
    head_table.appendChild(element_thead);
    let element_thead_tr = document.createElement("tr");
    element_thead_tr.setAttribute("id",elementInfo.id+"_thead_tr");
    element_thead.appendChild(element_thead_tr);

    // 判断是否需要额外增加“序号”字段
    if(elementInfo.param != null && elementInfo.param["showSeq"]!=null && elementInfo.param["showSeq"]){
        let element_thead_th = document.createElement("th");
        element_thead_th.setAttribute("class","output_table_th");
        element_thead_th.setAttribute("colName","序号");
        element_thead_th.textContent = "序号";//名称
        element_thead_tr.appendChild(element_thead_th);
        colSizeMap["序号"] = element_thead_th.clientWidth+7;
    }

    //表头
    let colList;
    let colCnt=0;
    for(let fieldName in headMap){
        let element_thead_th = document.createElement("th");
        element_thead_th.setAttribute("class","output_table_th");
        if( headMap[fieldName] != null && headMap[fieldName] != "" && headMap[fieldName] != "null"){
            element_thead_th.textContent = headMap[fieldName];
        }else{
            element_thead_th.textContent = fieldName;
        }
        element_thead_th.setAttribute("colName",fieldName);
        element_thead_tr.appendChild(element_thead_th);
        // 配置隐藏字段
        if(elementInfo.param != null && elementInfo.param["hideFields"]!=null && elementInfo.param["hideFields"].includes(fieldName)){
            element_thead_th.setAttribute("class","display-none");
        }

        //修正
        let colWidth = element_thead_th.clientWidth;
        if(colSizeMap == null || colSizeMap[fieldName]==null || colSizeMap[fieldName] != null && colSizeMap[fieldName]<colWidth){
            colSizeMap[fieldName]=colWidth+7;
        }
    }

    //表体
    let tbody_div = document.createElement("div");
    tbody_div.setAttribute("class","table_body_div");
    tbody_div.setAttribute("id",elementInfo.id+"_body_div");
    table_group_div.appendChild(tbody_div);

    let body_table = document.createElement("table");
    body_table.setAttribute("id",elementInfo.id+"_body_table");
    setAttr(body_table,elementInfo.attrMap); // 属性配置
    tbody_div.appendChild(body_table);
    //处理表格记录
    let element_tbody = document.createElement("tbody");
    element_tbody.setAttribute("class","output_table_tbody");
    element_tbody.setAttribute("id",elementInfo.id+"_tbody");
    body_table.appendChild(element_tbody);

    let trEventList = [];
    if(elementInfo.eventInfoList != null){
        let events = elementInfo.eventInfoList;
        for(let j=0;j<events.length;j++){
            if(events[j].event == "record_click"){
                let trEvent = events[j];
                trEvent.event="click";
                trEventList.push(trEvent);
            }
        }
    }

    //遍历返回的表记录
    let begSeq = 0;//分页时记录序号累加前页记录数
    if(tableNormal.withPage){
        begSeq = tableNormal.pageSize * (tableNormal.pageNow - 1);
    }

    for (let i=0;i<recordList.length;i++){
        let recordMap = recordList[i];//记录
        let element_table_tr = document.createElement("tr");
        element_table_tr.setAttribute("id",elementInfo.id+"_tbody_tr_"+i);
        if(trEventList !=null && trEventList.length>0){
            for(let j=0;j<trEventList.length;j++){
                let trEvent = trEventList[j];
                //由于bind的特性，需要copy对象绑定。
                element_table_tr.addEventListener(trEvent.event,executeEventMethod.bind(this,copy(trEvent),element_table_tr),false);
            }
        }
        element_tbody.appendChild(element_table_tr);

        //第1列，固定为”序号“
        // 判断是否需要额外增加“序号”字段
        if(elementInfo.param != null && elementInfo.param["showSeq"]!=null && elementInfo.param["showSeq"]){
            let element_table_td = document.createElement("td");
            element_table_td.setAttribute("class","output_table_td_0");
            element_table_td.textContent = begSeq+i+1;//字段名  序号
            element_table_td.width = colSizeMap["序号"];
            element_table_td.setAttribute("colName","序号");
            element_table_tr.appendChild(element_table_td);
        }

        for(let fieldName in headMap){
            let value = recordMap[fieldName];
            let element_table_td = document.createElement("td");
            element_table_td.setAttribute("colName",fieldName);
            if(i%2==0){
                element_table_td.setAttribute("class","output_table_td_0"); //td样式
            }else{
                element_table_td.setAttribute("class","output_table_td_1");
            }
            if(value==null || value==undefined || value=="null"){
                value = "";
            }
            element_table_td.textContent = value;//字段显示值
            element_table_tr.appendChild(element_table_td);
            // 配置隐藏字段
            if(elementInfo.param != null && elementInfo.param["hideFields"]!=null && elementInfo.param["hideFields"].includes(fieldName)){
                element_table_td.setAttribute("class","display-none");
            }

            let colWidth = element_table_td.clientWidth;
            if(colSizeMap == null || colSizeMap[fieldName]==null || colSizeMap[fieldName] != null && colSizeMap[fieldName]<colWidth){
                colSizeMap[fieldName]=colWidth;
            }
        }
    }
    if(recordList==null || recordList.length==0){
        // 没有表记录数据时，在output_table_tbody中写入一个Div/span 暂无数据
        let blankRecordDiv = document.createElement("div");
        blankRecordDiv.setAttribute("class","output_table_empty-block");
        let blankRecordSpan = document.createElement("span");
        blankRecordSpan.textContent = "暂无数据";
        blankRecordDiv.appendChild(blankRecordSpan);
        tbody_div.appendChild(blankRecordDiv);
    }

    //表头和表体列宽
    let trNodeList = element_tbody.childNodes;
    for (let i=0;i<trNodeList.length;i++){
         if(trNodeList[i].tagName == "TR"){
            let tdNodeList = trNodeList[i].childNodes;
            for (let j=0;j<tdNodeList.length;j++){
                let colName = tdNodeList[j].getAttribute("colName");
                tdNodeList[j].width = colSizeMap[colName];
            }
         }
    }

    let totalWidth = 0;
    let thNodeList = element_thead_tr.childNodes;
    for (let j=0;j<thNodeList.length;j++){
        let colName = thNodeList[j].getAttribute("colName");
        thNodeList[j].width = colSizeMap[colName];// 表格单元边框
        totalWidth += colSizeMap[colName];
    }

    thead_div.style.width = totalWidth+"px";
    tbody_div.style.width = totalWidth+"px";

    //分页按钮
    if(tableNormal.withPage){
        let events = elementInfo.eventInfoList;
        writePageButton(table_group_div,events[0],tableNormal);//分页按钮
    }
}

function writeTableRadio(parentEle,elementInfo){
    //表头加“操作”
    let thead_tr = document.getElementById(elementInfo.pid+"_thead_tr");
    let element_thead_th = document.createElement("th");
    element_thead_th.setAttribute("class","output_table_th");
    element_thead_th.textContent = "操作";//字段名
    appendChildAtSeq(thead_tr,element_thead_th,elementInfo.seq);

    row = 0;
    let tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    while(tbody_tr != null){
        let element_table_td = document.createElement("td");

        let button = document.createElement("input");
        button.setAttribute("id",elementInfo.id);
        button.setAttribute("eleName",elementInfo.elementName); //名称
        setEventListener(button,elementInfo.eventInfoList); //事件
        setAttr(button,elementInfo.attrMap); // 属性配置

        element_table_td.appendChild(button);
        appendChildAtSeq(tbody_tr,element_table_td,elementInfo.seq);
        row++;
        tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    }
}

function writeTableButton(parentEle,elementInfo){
    //表头加“操作”
    let thead_tr = document.getElementById(elementInfo.pid+"_thead_tr");
    let thead_td_list = thead_tr.childNodes;
    let existOprTd = false;
    let theadTd;
    let element_thead_th;
    for(let i=thead_td_list.length-1;i>=0;i--){
        theadTd = thead_td_list[i];
        if(theadTd.textContent == "操作"){
            element_thead_th = theadTd;
            existOprTd = true;
            break;
        }
    }

    if(!existOprTd){
        element_thead_th = document.createElement("th");
        element_thead_th.setAttribute("class","output_table_th");
        element_thead_th.textContent = "操作";//字段名
        appendChildAtSeq(thead_tr,element_thead_th,elementInfo.seq);
    }

    row = 0;
    let tdWidth=0;
    let tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    while(tbody_tr != null){
        let element_table_td;
        if(existOprTd){
            let tbody_td_list = tbody_tr.childNodes;
            element_table_td = tbody_td_list[tbody_td_list.length-1];
        }else{
            element_table_td = document.createElement("td");
            if(row%2==0){
                element_table_td.setAttribute("class","output_table_td_0"); //td样式
            }else{
                element_table_td.setAttribute("class","output_table_td_1");
            }
            appendChildAtSeq(tbody_tr,element_table_td,elementInfo.seq);
        }

        let button = document.createElement("label");
        button.setAttribute("id",elementInfo.id);
        button.setAttribute("eleName",elementInfo.elementName); //名称
        setEventListener(button,elementInfo.eventInfoList); //事件
        setAttr(button,elementInfo.attrMap); // 属性配置
        button.textContent = elementInfo.desc;//名称
        appendChildAtSeq(element_table_td,button,elementInfo.seq);

        tdWidth = element_table_td.clientWidth;

        row++;
        tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    }
    if(tdWidth!=0){
        element_thead_th.width = tdWidth;
    }

    let theadTr = document.getElementById(elementInfo.pid+"_thead_tr");
    let theadTdList = theadTr.childNodes;
    let totalWidth = 0;
    for (let j=0;j<theadTdList.length;j++){
        // 取单元格宽度,转为数字
        let width = theadTdList[j].width;
        totalWidth += parseInt(width, 10);
    }

    // 重新设置表头和表体宽度
    let thead_div = document.getElementById(elementInfo.pid+"_head_div");
    if(thead_div != null){
        thead_div.style.width = totalWidth +"px";
    }
    let tbody_div = document.getElementById(elementInfo.pid+"_body_div");
    if(tbody_div != null){
        tbody_div.style.width = totalWidth +"px";
    }
}

//分页按钮
function writePageButton(parentEle,eventInfo,webTableInfo){
    let events =new Array();
    events[0] = eventInfo;
    events[0].pageNow = webTableInfo.pageNow;
    events[0].totalCount = webTableInfo.totalCount;
    events[0].pageSize = webTableInfo.pageSize;
    events[0].pageTotal = webTableInfo.pageTotal;
    let page_div = document.createElement("div");
    page_div.setAttribute("class","page-box");

    let totalPage = parseInt((webTableInfo.totalCount-1)/webTableInfo.pageSize)+1;
    let pageNow = webTableInfo.pageNow;
    let page_pre_a = document.createElement("a");
    page_pre_a.setAttribute("class","page-button");
    page_pre_a.textContent = "上一页";
    setEventListener(page_pre_a,events); //事件
    page_div.appendChild(page_pre_a);

    let totalShowNum = 9;//必须为大于4的整奇数
    let lrShowNum = Math.floor((totalShowNum - 4) / 2); //向下取整数 2

    let pageList = new Array();
    pageList[0] = 1;
    if(totalPage <= totalShowNum){
        for(let i=0,page=1;page <= totalPage;page++,i++){
            pageList[i] = page;
        }
    }else if(pageNow <= totalShowNum-2-lrShowNum){
        for(let i=1,page=2;page<=totalShowNum-2;page++,i++){
            pageList[i] = page;
        }
        pageList[totalShowNum-2]="...";
        pageList[totalShowNum-1]=totalPage;
    }else if(pageNow <= totalPage - (totalShowNum-4)){
        pageList[1]="...";
        for(let i=2,page=pageNow-lrShowNum;page<=pageNow+lrShowNum;page++,i++){
            pageList[i] = page;
        }
        pageList[totalShowNum-2]="...";
        pageList[totalShowNum-1]=totalPage;
    }else if(pageNow > totalPage - (totalShowNum-4)){
        pageList[1]="...";
        for(let i=2,page=totalPage - (totalShowNum-4)-1;page<=totalPage;page++,i++){
            pageList[i] = page;
        }
    }
    for(let i = 0; i < pageList.length; i++) {
        if(pageList[i]=="..."){
            writePageA(page_div,"...",pageNow,null);
        }else{
            writePageA(page_div,pageList[i],pageNow,events);
        }
    }
    let page_next_a = document.createElement("a");
    page_next_a.setAttribute("class","page-button");
    page_next_a.textContent = "下一页";
    setEventListener(page_next_a,events); //事件
    page_div.appendChild(page_next_a);
    parentEle.appendChild(page_div);
}

function writePageA(page_div,pageNum,pageNow,events){
    if(pageNum==pageNow){
        let page_strong = document.createElement("strong");
        page_strong.setAttribute("class","page-button-strong");
        page_strong.textContent = String(pageNum);
        page_div.appendChild(page_strong);
    }else{
        let page_a = document.createElement("a");
        page_a.setAttribute("class","page-button");
        page_a.textContent = String(pageNum);
        setEventListener(page_a,events); //事件
        page_div.appendChild(page_a);
    }
}

/**
  * 弹窗
  **/
function writeSubWindow(parentEle,elementInfo){
    //已存在，则先删除
    let ele = document.getElementById(elementInfo.id+"_subWindowBackGround");
    if(null != ele){
        ele.parentNode.removeChild(ele);
    }

    let div_sBg = document.createElement("div");
    div_sBg.setAttribute("id",elementInfo.id+"_subWindowBackGround");
    div_sBg.setAttribute("class","subWindowBackGround");
    parentEle.appendChild(div_sBg);
    display(elementInfo.id+"_subWindowBackGround");

    let div_sBorder = document.createElement("div");
    div_sBorder.setAttribute("id",elementInfo.id+"_subWindow");
    div_sBorder.setAttribute("class","subWindow ui-draggable ui-draggable-handle");
    div_sBg.appendChild(div_sBorder);
    modalMoveById("#"+elementInfo.id+"_subWindow");//为模态对话框添加拖拽

    let div_sContent = document.createElement("div");
    div_sContent.setAttribute("id",elementInfo.id+"_subWidowContent");
    div_sContent.setAttribute("class","subWidowContent");
    div_sBorder.appendChild(div_sContent);

    let div_sHeader = document.createElement("div");
    div_sHeader.setAttribute("id",elementInfo.id+"_swHeader");
    div_sHeader.setAttribute("class","subWidowHeader");
    div_sContent.appendChild(div_sHeader);

    let headSpan = document.createElement("span");
    headSpan.textContent = elementInfo.desc;
    div_sHeader.appendChild(headSpan);

    let div_closeButton = document.createElement("div");
    div_closeButton.setAttribute("id",elementInfo.id+"_header-x-div");
    div_closeButton.setAttribute("class","subWidowHeaderCloseBtn");
    div_closeButton.textContent = "x";
    div_closeButton.setAttribute("onclick","hideById(\""+elementInfo.id+"_subWindowBackGround"+"\")");
    div_sHeader.appendChild(div_closeButton);

    let div_sBody = document.createElement("div");
//    div_sBody.setAttribute("id",elementInfo.id+"_swBody");
    div_sBody.setAttribute("id",elementInfo.id);
    div_sBody.setAttribute("class","subWidowBody");
    div_sContent.appendChild(div_sBody);

//    let div_sFooter = document.createElement("div");
//    div_sFooter.setAttribute("id",elementInfo.id+"_swFooter");
//    div_sFooter.setAttribute("class","subWidowFooter");
//    div_sContent.appendChild(div_sFooter);

    // 只允许点击弹窗头部进行拖动
    let subWidowId = elementInfo.id+"_subWindow";
    let subWidowHeaderId = elementInfo.id+"_swHeader";
    // 移除原有拖动初始化
    $(`#${subWidowId}`).draggable("destroy");
    // 重新初始化带有限制的拖动
    $(`#${subWidowId}`).draggable({
        handle: `#${subWidowHeaderId}`,
//        containment: "window", // 限制在窗口内拖动
        scroll: false,
        start: function() {
            $(this).css('cursor', 'move');
        },
        stop: function() {
            $(this).css('cursor', '');
        }
    });
}

/**
 * 设置元素属性
 **/
function setAttr(element,attrMap){
    //属性
    if(attrMap != null){
        for(let attrName in attrMap){ //key:attrName,value:attrMap[attrName]
            element.setAttribute(attrName,attrMap[attrName]);
        }
    }
}

/**
 * 设置元素事件
 **/
function setEventListener(element,eventInfoList){
    //事件
    if(eventInfoList != null && eventInfoList.length > 0 ){
        for (let i=0;i<eventInfoList.length;i++){
            let eventInfo = eventInfoList[i];
            //由于bind的特性，需要copy对象绑定。
            element.addEventListener(eventInfo.event,executeEventMethod.bind(this,copy(eventInfo),element),false);
        }
    }
}

function writeCanvas(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_groupDiv");

    let canvas = document.createElement("canvas");
    canvas.setAttribute("id",elementInfo.id);
    canvas.setAttribute("eleName",elementInfo.elementName); //名称
    setAttr(canvas,elementInfo.attrMap); // 属性配置

    const ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height); // 清空画布
    let canvasList = elementInfo.data;
    // canvasList 结构：[{"lineName":"","lineType":"","lineX":100,
    //    "points":[{"pointName":"","pointType":"","pointSeq":1,"pointX":100,"pointY":50,"pointPre":{}}]},{}]
    for(let i=0;i<canvasList.length;i++){
        let line = canvasList[i];
        let lineName = line.lineName;
        let lineType = line.lineType;
        let lineX = line.lineX;
        let points = line.points;
        drawLabel(ctx, lineX-10, 20, lineName);
        for(let j=0;j<points.length;j++){
            let point = points[j];
            let pointName = point.pointName;
            let pointType = point.pointType;
            let pointSeq = point.pointSeq;
            let pointX = point.pointX;
            let pointY = point.pointY;
            let pointPre = point.pointPre;
            if(pointPre!= null){
                drawLine(ctx, pointPre.pointX,pointPre.pointY, pointX,pointY);
            }
            drawPoint(ctx, pointX,pointY, pointName);
        }
    }

    groupDiv.appendChild(canvas);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);

    // 可选：添加拖拽功能
    let isDragging = false;
    let selectedPoint = null;

    canvas.addEventListener('mousedown', (e) => {
        const rect = canvas.getBoundingClientRect();
        const mouseX = e.clientX - rect.left;
        const mouseY = e.clientY - rect.top;

        // 遍历canvasList中的所有点
        for (let i = 0; i < canvasList.length; i++) {
            const line = canvasList[i];
            const points = line.points;
            for (let j = 0; j < points.length; j++) {
                const point = points[j];
                const pointX = point.pointX;
                const pointY = point.pointY;
                // 检查鼠标点击是否在点的范围内
                if (Math.abs(mouseX - pointX) <= 5 && Math.abs(mouseY - pointY) <= 5) {
                    isDragging = true;
                    selectedPoint = point;
                    break;
                }
            }
            if (isDragging) {
                break;
            }
        }
    });

    canvas.addEventListener('mousemove', (e) => {
        if (isDragging && selectedPoint) {
            const rect = canvas.getBoundingClientRect();
            selectedPoint.x = e.clientX - rect.left;
            selectedPoint.y = e.clientY - rect.top;
            drawAll();
        }
    });

    canvas.addEventListener('mouseup', () => {
        isDragging = false;
        selectedPoint = null;
    });

    canvas.addEventListener('mouseleave', () => {
        isDragging = false;
        selectedPoint = null;
    });
}

// 绘制点（带标签）
function drawPoint(ctx, x, y, label) {
    // 绘制点
    ctx.beginPath();
    ctx.arc(x, y, 5, 0, Math.PI * 2);
    ctx.fillStyle = '#3cb371';
    ctx.fill();

    // 绘制标签
    ctx.font = '16px Arial';
    ctx.fillStyle = '#2c3e50';
    ctx.fillText(label, x + 10, y + 5);
}

function drawLabel(ctx, x, y, label) {
    // 绘制标签
    ctx.font = '16px Arial';
    ctx.fillStyle = '#2c3e50';
    ctx.fillText(label, x, y);
}

// 绘制连接线
function drawLine(ctx, pointAx,pointAy, pointBx,pointBy) {
    ctx.beginPath();
    ctx.moveTo(pointAx, pointAy);
    ctx.lineTo(pointBx, pointBy);
    ctx.strokeStyle = '#3498db';
    ctx.lineWidth = 2;
    ctx.stroke();
}



// 打开标签页
function openTab(tabId) {
    if (!tabId) return;

    // 如果标签页已经打开，直接切换到该标签
    if (tabs.some(tab => tab.id === tabId)) {
        setActiveTab(tabId);
        return;
    }
    let tabName = menuNameMap[tabId];

    // 添加新标签页
    const tabInfo = tabData[tabId] || { title: tabId, icon: "fa-file" };
    tabs.push({
        id: tabId,
        title: tabName,
//        title: tabInfo.title,
        icon: tabInfo.icon
    });

    setActiveTab(tabId);
    renderTabs();
}

// 设置活动标签
function setActiveTab(tabId) {
    // activeTab不为空且当前已打开且活动的标签页，不做任何操作
    if (activeTab!=null && tabId === activeTab) return;

    // 当前已打开且活动的标签页，将主体div contentArea备份到tempArea
    if (activeTab) {
        moveContentAreaDivToTemp();
    }
    let curTabBody = document.getElementById("contentArea_" + tabId);
    if(curTabBody!=null){
        //将tempArea备份的contentArea恢复到contentArea
        recoverContentAreaDivFromTemp(tabId);
    }
    activeTab = tabId;
    renderTabs();
}

function moveContentAreaDivToTemp(){
    // 移动contentArea到tempArea
    let tempAreaDiv = document.getElementById("tempArea");
    let contentAreaDiv = document.getElementById("contentArea");
    tempAreaDiv.appendChild(contentAreaDiv);
    contentAreaDiv.id = "contentArea_" + activeTab;
    // 创建新的contentArea/inputArea
    let contentAreaParentDiv = document.getElementById("contentAreaParent");
    let newContentAreaDiv = document.createElement("div");
    newContentAreaDiv.id = "contentArea";
    newContentAreaDiv.className = "contentArea";
    contentAreaParentDiv.appendChild(newContentAreaDiv);
    let inputAreaDiv = document.createElement("div");
    inputAreaDiv.id = "inputArea";
    inputAreaDiv.className = "inputArea";
    newContentAreaDiv.appendChild(inputAreaDiv);
}

function recoverContentAreaDivFromTemp(tabId){
    //将tempArea备份的contentArea恢复到contentArea
    let backUpContentAreaDiv = document.getElementById("contentArea_"+tabId);

    // 删除contentAreaDiv
    let contentAreaParentDiv = document.getElementById("contentAreaParent");
    let contentAreaDiv = document.getElementById("contentArea");
    contentAreaParentDiv.removeChild(contentAreaDiv);

    // 将备份的contentAreaDiv恢复到contentAreaParentDiv
    contentAreaParentDiv.appendChild(backUpContentAreaDiv);
    backUpContentAreaDiv.id = "contentArea";
}

// 关闭标签页
function closeTab(tabId, e) {
    e.stopPropagation();

    // 如果关闭的是当前活动标签，需要切换到另一个标签
    if (tabId === activeTab) {
        const currentIndex = tabs.findIndex(tab => tab.id === tabId);
        const newActiveTab = tabs[currentIndex + 1] || tabs[currentIndex - 1];

        if (newActiveTab) {
            setActiveTab(newActiveTab.id);
            activeTab = newActiveTab.id;
        } else {
            activeTab = null;
            // 删除contentAreaDiv
            let contentAreaParentDiv = document.getElementById("contentAreaParent");
            let contentAreaDiv = document.getElementById("contentArea");
            contentAreaParentDiv.removeChild(contentAreaDiv);
            // 创建新的contentArea/inputArea
            let newContentAreaDiv = document.createElement("div");
            newContentAreaDiv.id = "contentArea";
            newContentAreaDiv.className = "contentArea";
            contentAreaParentDiv.appendChild(newContentAreaDiv);
            let inputAreaDiv = document.createElement("div");
            inputAreaDiv.id = "inputArea";
            inputAreaDiv.className = "inputArea";
            newContentAreaDiv.appendChild(inputAreaDiv);
        }
    }

    // 移除标签
    let curTabBody = document.getElementById("contentArea_" + tabId);
    if(curTabBody!=null){
        // 删除tempArea备份的contentArea
        let tempAreaDiv = document.getElementById("tempArea");
        tempAreaDiv.removeChild(curTabBody);
    }
    tabs = tabs.filter(tab => tab.id !== tabId);


    // 如果没有标签了，打开控制面板
//    if (tabs.length === 0) {
//        tabs.push({ id: "dashboard", title: "控制面板", icon: "fa-tachometer-alt" });
//        activeTab = "dashboard";
//    }

    renderTabs();
}

// 渲染标签页
function renderTabs() {
    const tabsBar = document.getElementById('tabsBar');
    tabsBar.textContent = '';

    tabs.forEach(tab => {
        const tabElement = document.createElement('div');
        tabElement.className = `tab-item ${tab.id === activeTab ? 'active' : ''}`;
        tabElement.setAttribute('data-tab', tab.id);

        tabElement.innerHTML = `
            <div class="tab-title">
                <i class="fas ${tab.icon}" style="margin-right: 8px;"></i>
                ${tab.title}
            </div>
            <div class="tab-close">
                <i class="fas fa-times"></i>
            </div>
        `;

        // 添加点击事件
        tabElement.addEventListener('click', () => setActiveTab(tab.id));

        // 添加关闭事件
        const closeBtn = tabElement.querySelector('.tab-close');
        closeBtn.addEventListener('click', (e) => closeTab(tab.id, e));

        tabsBar.appendChild(tabElement);
    });
}