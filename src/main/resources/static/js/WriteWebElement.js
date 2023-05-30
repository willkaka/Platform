
function sucFreshAll(PublicResp){
    let rtnCode = PublicResp.rtnCode;
    let elementDtoList = PublicResp.webElementDtoList;
    if(null!=elementDtoList){
        loopElementList(elementDtoList);
    }
    let nextOprDto = PublicResp.nextOprDto;
    if(null != nextOprDto){
        nextOpr(nextOprDto);
    }
}

function nextOpr(nextOprDto){
    let eventInfoList = nextOprDto["eventInfoList"];
    if(null == eventInfoList) return;
    for(let i=0;i<eventInfoList.length;i++){
        let eventInfo = eventInfoList[i];
        if(null != eventInfo && eventInfo.event == "request"){
            executeEventMethod(eventInfo,null);
        }
        if(null != eventInfo && eventInfo.event == "closeSw"){
            hideById(eventInfo.element+"_subWindowBackGround"); //add_sub_window
        }
    }
}

function loopElementList(elementDtoList){
    for (let i=0;i<elementDtoList.length;i++){
        let elementDto = elementDtoList[i];
        writeWebElementRoute(elementDto.pid,elementDto);
        let subElementDtoList = elementDto.subElementList;
        if(subElementDtoList!=null && subElementDtoList.length>0){
            loopElementList(subElementDtoList);
        }
    }
}

/**
 * 生成页面元素路由
 **/
function writeWebElementRoute(parentEleId,elementInfo){
    if(elementInfo.id != "body") removeElementById(elementInfo.id);

    let parentEle = document.getElementById(parentEleId);
    if(parentEle == null) {
        parentEle = document.getElementById(parentEleId+"_group");
        if(parentEle == null) return;
    }

    if(elementInfo.type == "Group") writeGroup(parentEle,elementInfo);
    if(elementInfo.type == "Menu") writeMenu(parentEle,elementInfo);
    if(elementInfo.type == "div") writeDiv(parentEle,elementInfo);
    if(elementInfo.type == "button") writeButton(parentEle,elementInfo);

    if(elementInfo.type == "table") writeTableLabel(parentEle,elementInfo);
    if(elementInfo.type == "table_record_button") writeTableButton(parentEle,elementInfo);
    if(elementInfo.type == "table_record_radio") writeTableRadio(parentEle,elementInfo);

    if(elementInfo.type == "input") writeInput(parentEle,elementInfo);
    if(elementInfo.type == "inputFile") writeInputFile(parentEle,elementInfo);
    if(elementInfo.type == "dropDown") writeDropDown(parentEle,elementInfo);
    if(elementInfo.type == "inputDataList") writeInputDataList(parentEle,elementInfo);
    if(elementInfo.type == "selectOption") writeSelectOption(parentEle,elementInfo);
    if(elementInfo.type == "multipleSelect") writeMultipleSelect(parentEle,elementInfo);

    if(elementInfo.type == "subWindow") writeSubWindow(parentEle,elementInfo);
}

function writeDiv(parentEle,elementInfo){
    let element_div = document.createElement("div");
    element_div.setAttribute("id",elementInfo.id); //id
    setAttr(element_div,elementInfo.attrMap); // 属性配置
    setEventListener(element_div,elementInfo.eventInfoList); //事件

    parentEle.appendChild(element_div);
}
/**
  *    <div class="menuDropDown">
           <a class="menuDropButton" href="#">下拉菜单</a>
           <div class="menuDropDown-content">
               <a href="#">链接 1</a>
               <a href="#">链接 2</a>
               <a href="#">链接 3</a>
           </div>
       </div>
  **/
function writeGroup(parentEle,elementInfo){
//    clearChildren(parentEle);

    let element_div = document.createElement("div");
    element_div.setAttribute("class","menuDropDown");

    let element_a = document.createElement("a");
    element_a.setAttribute("class","menuDropButton");

    element_a.innerHTML = elementInfo.desc;//菜单名称
    setAttr(element_a,elementInfo.attrMap); // 属性配置
    element_div.appendChild(element_a);

    let element_sub_div = document.createElement("div");
    element_sub_div.setAttribute("class","menuDropDown-content");
    element_sub_div.setAttribute("id",elementInfo.id);
    element_div.appendChild(element_sub_div);
    parentEle.appendChild(element_div);
}

function writeMenu(parentEle,elementInfo){
    let element_a = document.createElement("a");
    element_a.setAttribute("id",elementInfo.id);
    element_a.innerHTML = elementInfo.desc;//菜单名称
    setAttr(element_a,elementInfo.attrMap); // 属性配置
    setEventListener(element_a,elementInfo.eventInfoList); //事件
    parentEle.appendChild(element_a);
}

/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeInput(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("class","inputArea_div_grp");
    if(null == elementInfo.attrMap){
        groupDiv.setAttribute("class","inputArea_div_grp");
    }else{
        setAttr(groupDiv,elementInfo.attrMap);
    }

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    if(null != elementInfo.desc){
        input.setAttribute("placeholder",elementInfo.desc);
    }
    if(null != elementInfo.defValue){
        input.setAttribute("value",elementInfo.defValue);
    }
    input.setAttribute("class","inputArea_sub_input");
    groupDiv.appendChild(input);
//    parentEle.appendChild(groupDiv);
    appendChildAtSeq(parentEle,groupDiv,elementInfo.seq);
}


/**
 * 在父元素插入生成的输入框 div label/input
 **/
function writeInputFile(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
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
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let input = document.createElement("input");
    input.setAttribute("id",elementInfo.id);
    input.setAttribute("list","datalist"+elementInfo.id);
    input.setAttribute("class","inputArea_sub_input");
    setEventListener(input,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(input,elementInfo.attrMap);
    }
    let dataList = document.createElement("datalist");
    dataList.setAttribute("id","datalist"+elementInfo.id);

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("value",value);
        option.setAttribute("name",dataMap[value]);
        //option.innerHTML = dataMap[value];
        dataList.appendChild(option);
    }
    groupDiv.appendChild(input);
    groupDiv.appendChild(dataList);
    parentEle.appendChild(groupDiv);
}

function writeMultipleSelect(parentEle,elementInfo){
    let groupDiv = document.createElement("div");
    groupDiv.setAttribute("id",elementInfo.id+"_group");
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
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
    groupDiv.setAttribute("id",elementInfo.id);
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
    select.setAttribute("class","inputArea_sub_select");
    setEventListener(select,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(select,elementInfo.attrMap);
    }

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("value",value);
        option.innerHTML = dataMap[value];
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
    groupDiv.setAttribute("class","inputArea_div_grp");

    let label = document.createElement("label");
    label.setAttribute("class","inputArea_sub_label");
    label.innerHTML = elementInfo.desc;//名称
    groupDiv.appendChild(label);

    let select = document.createElement("select");
    select.setAttribute("id",elementInfo.id);
    select.setAttribute("class","inputArea_sub_select");
    //setAttr(select,elementInfo.attrMap); // 属性配置
    setEventListener(select,elementInfo.eventInfoList); //事件
    if(null != elementInfo.attrMap){
        setAttr(select,elementInfo.attrMap);
    }

    let dataMap = elementInfo.data;
    for(let value in dataMap){
        let option = document.createElement("option");
        option.setAttribute("value",value);
        option.innerHTML = dataMap[value];
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
    setEventListener(button,elementInfo.eventInfoList); //事件
    setAttr(button,elementInfo.attrMap); // 属性配置
    let span = document.createElement("span");
    span.innerHTML = elementInfo.desc;//名称
    button.appendChild(span);

//    parentEle.appendChild(button);
    appendChildAtSeq(parentEle,button,elementInfo.seq);
}
//
//function writeTableLabel(parentEle,elementInfo){
//    let tableNormal = elementInfo.data;//表信息
//    let headMap = tableNormal.headMap;//表头
//    let recordList = tableNormal.recordList;//记录
//
//    //分页按钮
//    if(tableNormal.isWithPage){
//        for (let i=0;i<recordList.length;i++){
//            let events = elementInfo.eventInfoList;
//            for(let j=0;j<events.length;j++){
//                if(events[j].withPage){
//                    writePageButton(parentEle,events[j],webTableInfo);//分页按钮
//                }
//            }
//        }
//    }
//
//    //已存在，则先删除
//    let ele = document.getElementById(elementInfo.id);
//    if(null != ele){
//        ele.parentNode.removeChild(ele);
//    }
//
//    let element_table = document.createElement("table");
//    element_table.setAttribute("id",elementInfo.id);
////    element_table.setAttribute("class","output_table");
//    setAttr(element_table,elementInfo.attrMap); // 属性配置
////    parentEle.appendChild(element_table);
//    appendChildAtSeq(parentEle,element_table,elementInfo.seq);
//
//    //表头
//    let element_thead = document.createElement("thead");
//    element_thead.setAttribute("class","output_table_thead");
//    element_thead.setAttribute("id",elementInfo.id+"_thead");
//    element_table.appendChild(element_thead);
//    let element_thead_tr = document.createElement("tr");
//    element_thead_tr.setAttribute("id",elementInfo.id+"_thead_tr");
//    element_thead.appendChild(element_thead_tr);
//
//    let element_thead_th = document.createElement("th");
//    element_thead_th.setAttribute("class","output_table_th");
//    element_thead_th.innerHTML = "序号";//名称
//    element_thead_tr.appendChild(element_thead_th);
//
//    //取第一条记录的字段名称作为表头
//    let colList;
//    let colCnt=0;
//    for(let fieldName in headMap){
//        let element_thead_th = document.createElement("th");
//        element_thead_th.setAttribute("class","output_table_th");
//        if( headMap[fieldName] != null && headMap[fieldName] != "" && headMap[fieldName] != "null"){
//            element_thead_th.innerHTML = headMap[fieldName];
//        }else{
//            element_thead_th.innerHTML = fieldName;
//        }
//        element_thead_tr.appendChild(element_thead_th);
//    }
//
//    //处理表格记录
//    let element_tbody = document.createElement("tbody");
//    element_tbody.setAttribute("class","output_table_tbody");
//    element_tbody.setAttribute("id",elementInfo.id+"_tbody");
//    element_table.appendChild(element_tbody);
//
//    let trEvent = null;
//    if(elementInfo.eventInfoList != null){
//        let events = elementInfo.eventInfoList;
//        for(let j=0;j<events.length;j++){
//            if(events[j].event == "record_click"){
//                trEvent = events[j];
//                trEvent.event="click";
//                break;
//            }
//        }
//    }
//
//    //遍历返回的表记录
//    let begSeq = 0;//分页时记录序号累加前页记录数
//    if(tableNormal.isWithPage){
//        begSeq = tableNormal.pageSize * (tableNormal.pageNow - 1);
//    }
//    for (let i=0;i<recordList.length;i++){
//        let recordMap = recordList[i];//记录
//        let element_table_tr = document.createElement("tr");
//        element_table_tr.setAttribute("id",elementInfo.id+"_tbody_tr_"+i);
//        if(trEvent != null){
//            //由于bind的特性，需要copy对象绑定。
//            element_table_tr.addEventListener(trEvent.event,executeEventMethod.bind(this,copy(trEvent),element_table_tr),false);
//        }
//        element_tbody.appendChild(element_table_tr);
//
//        //第1列，固定为”序号“
//        let element_table_td = document.createElement("td");
//        element_table_td.setAttribute("class","output_table_td_0");
//        element_table_td.innerHTML = begSeq+i+1;//字段名
//        element_table_tr.appendChild(element_table_td);
//        for(let fieldName in headMap){
////        for(let fieldName in recordMap){
//            let value = recordMap[fieldName];
//            let element_table_td = document.createElement("td");
//            element_table_td.setAttribute("colName",fieldName);
//            if(i%2==0){
//                element_table_td.setAttribute("class","output_table_td_0"); //td样式
//            }else{
//                element_table_td.setAttribute("class","output_table_td_1");
//            }
//            element_table_td.innerHTML = value;//字段显示值
//            element_table_tr.appendChild(element_table_td);
//        }
//    }
//}


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
    table_group_div.setAttribute("class","table_group_div");
    appendChildAtSeq(parentEle,table_group_div,elementInfo.seq);

    //分页按钮
    if(tableNormal.isWithPage){
        for (let i=0;i<recordList.length;i++){
            let events = elementInfo.eventInfoList;
            for(let j=0;j<events.length;j++){
                if(events[j].withPage){
                    writePageButton(table_group_div,events[j],webTableInfo);//分页按钮
                }
            }
        }
    }

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

    let element_thead_th = document.createElement("th");
    element_thead_th.setAttribute("class","output_table_th");
    element_thead_th.setAttribute("colName","序号");
    element_thead_th.innerHTML = "序号";//名称
    element_thead_tr.appendChild(element_thead_th);
    colSizeMap["序号"] = element_thead_th.clientWidth+7;

    //取第一条记录的字段名称作为表头
    let colList;
    let colCnt=0;
    for(let fieldName in headMap){
        let element_thead_th = document.createElement("th");
        element_thead_th.setAttribute("class","output_table_th");
        if( headMap[fieldName] != null && headMap[fieldName] != "" && headMap[fieldName] != "null"){
            element_thead_th.innerHTML = headMap[fieldName];
        }else{
            element_thead_th.innerHTML = fieldName;
        }
        element_thead_th.setAttribute("colName",fieldName);
        element_thead_tr.appendChild(element_thead_th);

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

    let trEvent = null;
    if(elementInfo.eventInfoList != null){
        let events = elementInfo.eventInfoList;
        for(let j=0;j<events.length;j++){
            if(events[j].event == "record_click"){
                trEvent = events[j];
                trEvent.event="click";
                break;
            }
        }
    }

    //遍历返回的表记录
    let begSeq = 0;//分页时记录序号累加前页记录数
    if(tableNormal.isWithPage){
        begSeq = tableNormal.pageSize * (tableNormal.pageNow - 1);
    }

    for (let i=0;i<recordList.length;i++){
        let recordMap = recordList[i];//记录
        let element_table_tr = document.createElement("tr");
        element_table_tr.setAttribute("id",elementInfo.id+"_tbody_tr_"+i);
        if(trEvent != null){
            //由于bind的特性，需要copy对象绑定。
            element_table_tr.addEventListener(trEvent.event,executeEventMethod.bind(this,copy(trEvent),element_table_tr),false);
        }
        element_tbody.appendChild(element_table_tr);

        //第1列，固定为”序号“
        let element_table_td = document.createElement("td");
        element_table_td.setAttribute("class","output_table_td_0");
        element_table_td.innerHTML = begSeq+i+1;//字段名  序号
        element_table_td.width = colSizeMap["序号"];
        element_table_td.setAttribute("colName","序号");
        element_table_tr.appendChild(element_table_td);

        for(let fieldName in headMap){
            let value = recordMap[fieldName];
            let element_table_td = document.createElement("td");
            element_table_td.setAttribute("colName",fieldName);
            if(i%2==0){
                element_table_td.setAttribute("class","output_table_td_0"); //td样式
            }else{
                element_table_td.setAttribute("class","output_table_td_1");
            }
            element_table_td.innerHTML = value;//字段显示值
            element_table_tr.appendChild(element_table_td);

            let colWidth = element_table_td.clientWidth;
            if(colSizeMap == null || colSizeMap[fieldName]==null || colSizeMap[fieldName] != null && colSizeMap[fieldName]<colWidth){
                colSizeMap[fieldName]=colWidth;
            }
        }
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

    let thNodeList = element_thead_tr.childNodes;
    for (let j=0;j<thNodeList.length;j++){
        let colName = thNodeList[j].getAttribute("colName");
        thNodeList[j].width = colSizeMap[colName]+1;// 表格单元边框
    }
}

function writeTableRadio(parentEle,elementInfo){
    //表头加“操作”
    let thead_tr = document.getElementById(elementInfo.pid+"_thead_tr");
    let element_thead_th = document.createElement("th");
    element_thead_th.setAttribute("class","output_table_th");
    element_thead_th.innerHTML = "操作";//字段名
    appendChildAtSeq(thead_tr,element_thead_th,elementInfo.seq);

    row = 0;
    let tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    while(tbody_tr != null){
        let element_table_td = document.createElement("td");

        let button = document.createElement("input");
        button.setAttribute("id",elementInfo.id);
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
    for(let i=thead_td_list.length-1;i>=0;i--){
        theadTd = thead_td_list[i];
        if(theadTd.innerHTML == "操作"){
            existOprTd = true;
            break;
        }
    }
    if(!existOprTd){
        let element_thead_th = document.createElement("th");
        element_thead_th.setAttribute("class","output_table_th");
        element_thead_th.innerHTML = "操作";//字段名
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
            element_table_td.setAttribute("class","output_table_td_1");
            appendChildAtSeq(tbody_tr,element_table_td,elementInfo.seq);
        }

        let button = document.createElement("label");
        button.setAttribute("id",elementInfo.id);
        setEventListener(button,elementInfo.eventInfoList); //事件
        setAttr(button,elementInfo.attrMap); // 属性配置
        button.innerHTML = elementInfo.desc;//名称
        appendChildAtSeq(element_table_td,button,elementInfo.seq);

        tdWidth = element_table_td.clientWidth;

        row++;
        tbody_tr = document.getElementById(elementInfo.pid+"_tbody_tr_"+row);
    }

    theadTd.width = tdWidth;
}

//分页按钮
function writePageButton(parentEle,eventInfo,webTableInfo){
    let events =new Array();
    events[0] = eventInfo;
    let page_div = document.createElement("div");
    page_div.setAttribute("class","page-box");

    let totalPage = parseInt((webTableInfo.totalCount-1)/webTableInfo.pageSize)+1;
    let pageNow = webTableInfo.pageNow;
    let page_pre_a = document.createElement("a");
    page_pre_a.setAttribute("class","page-button");
    page_pre_a.innerHTML = "上一页";
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
    page_next_a.innerHTML = "下一页";
    setEventListener(page_next_a,events); //事件
    page_div.appendChild(page_next_a);
    parentEle.appendChild(page_div);
}

function writePageA(page_div,pageNum,pageNow,events){
    if(pageNum==pageNow){
        let page_strong = document.createElement("strong");
        page_strong.setAttribute("class","page-button-strong");
        page_strong.innerHTML = String(pageNum);
        page_div.appendChild(page_strong);
    }else{
        let page_a = document.createElement("a");
        page_a.setAttribute("class","page-button");
        page_a.innerHTML = String(pageNum);
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
    headSpan.innerHTML = elementInfo.desc;
    div_sHeader.appendChild(headSpan);

    let div_closeButton = document.createElement("div");
    div_closeButton.setAttribute("id",elementInfo.id+"_header-x-div");
    div_closeButton.setAttribute("class","subWidowHeaderCloseBtn");
    div_closeButton.innerHTML = "x";
    div_closeButton.setAttribute("onclick","hide("+elementInfo.id+"_subWindowBackGround"+")");
    div_sHeader.appendChild(div_closeButton);

    let div_sBody = document.createElement("div");
    div_sBody.setAttribute("id",elementInfo.id+"_swBody");
    div_sBody.setAttribute("class","subWindowBody");
    div_sContent.appendChild(div_sBody);

    let div_sFooter = document.createElement("div");
    div_sFooter.setAttribute("id",elementInfo.id+"_swFooter");
    div_sFooter.setAttribute("class","subWidowFooter");
    div_sContent.appendChild(div_sFooter);
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
