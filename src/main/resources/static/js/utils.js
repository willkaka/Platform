
/**
 * 删除传入标签ID下所有子标签
 * @param parentId 父标签ID
 */
function clearChildren(parentId) {
    let element = parentId;
    if((typeof parentId=='string') && parentId.constructor==String ){
        element = document.getElementById(parentId);
    }
    if(element!=null){
        let menuElements = element.childNodes;
        for(let i=menuElements.length-1;i>=0;i--){
            element.removeChild(menuElements[i]);
        }
    }
}

function clearChildrenExcept(element,tagName) {
    if(element!=null){
        let menuElements = element.childNodes;
        for(let i=menuElements.length-1;i>=0;i--){
            if(tagName == menuElements[i].tagName || menuElements[i].tagName == "LABEL"){ continue; }
            element.removeChild(menuElements[i]);
        }
    }
}

/**
 * 指定新增元素在父元素的顺序位置
 **/
function appendChildAtSeq(parentElement,childElement,seq) {
    if(parentElement!=null){
        if(seq == 999) {
            parentElement.appendChild(childElement);
            return;
        }
        let childNodeList = copyList(parentElement.childNodes);
        clearChildren(parentElement);
        let isWrite = false;
        for(let i=0;childNodeList!=null && i<childNodeList.length;i++){
            if(i==seq) {
                parentElement.appendChild(childElement);
                isWrite = true;
            }
            parentElement.appendChild(childNodeList[i]);
        }
        if(!isWrite){
            parentElement.appendChild(childElement);
        }
    }
}

function removeElementById(elementId){
    //检查是否是tempArea下的元素，若是则不删除
    let checkElement = document.getElementById(elementId);
    // 遍历所有父元素，直到找到id为tempArea的元素，或者遍历到根元素
    while (checkElement && checkElement.id !== "tempArea") {
        checkElement = checkElement.parentNode;
    }
    // 如果找到了id为tempArea的元素，说明是tempArea下的元素，不删除
    if (checkElement && checkElement.id === "tempArea") {
        return;
    }

    let element = document.getElementById(elementId+"_group");
    if(element!=null){
        removeElement(element);
    }
    element = document.getElementById(elementId);
    if(element!=null){
        removeElement(element);
    }else{
        const elements = getElementByEleName(elementId);
        for (let i = 0; i < elements.length; i++) {
            removeElement(elements[i]);
        }
    }
}

function removeElementByUid(elementUid){
    //检查是否是tempArea下的元素，若是则不删除
    let checkElement = document.querySelector(`[uid="${elementUid.replace(/"/g, '\\"')}"]`);
//    // 遍历所有父元素，直到找到id为tempArea的元素，或者遍历到根元素
//    while (checkElement && checkElement.id !== "tempArea") {
//        checkElement = checkElement.parentNode;
//    }
//    // 如果找到了id为tempArea的元素，说明是tempArea下的元素，不删除
//    if (checkElement && checkElement.id === "tempArea") {
//        return;
//    }
//
    let uid = elementUid + "_group";
    let element = document.querySelector(`[uid="${uid.replace(/"/g, '\\"')}"]`);
    if(element!=null){
        removeElement(element);
    }
//    element = document.getElementById(elementId);
    if(checkElement!=null){
        removeElement(checkElement);
    }
}

function removeElement(element){
    if(element == null) return;
    element.parentNode.removeChild(element);
}


/**
 * 不显示指定标签
 * @param eleId
 */
function hideById(eleId){
    let ele = document.getElementById(eleId);
    ele.style.display="none";
}

/**
 * 不显示指定标签
 * @param eleId
 */
function hideByName(eleId){
    let ele = document.getElementById(eleId);
    ele.style.display="none";
}

function getElementByEleName(value) {
  try {
    const safeValue = value.replace(/"/g, '\\"');
    return document.querySelectorAll(`[elename="${safeValue}"]`);
  } catch (e) {
    console.error("查询失败:", e);
    return [];
  }

  const elements = document.querySelectorAll(`[elename="${value}"]`);

  // 遍历结果
  elements.forEach(element => {
    console.log("找到元素:", element);
    console.log("elename值:", element.getAttribute('elename'));
  });
  return elements;
}

/**
 * 不显示指定标签
 * @param eleId
 */
function hide(ele){
    ele.style.display="none";
}

/**
 * 显示指定标签
 * @param eleId
 */
function display(eleId){
    let ele = document.getElementById(eleId);
    ele.style.display="";
}

function setUserNameIntoCookie(cvalue){
    setCookie(userNameKey,cvalue,1)
}

function copy(obj){
    return Object.assign({},obj);
}
function copyList(list){
    if(list==null) return null;
    let newList = $.extend(true,[],list);
    return newList;
}

/**
 * 获取表格某一行列数
 * @param  Int id    表格id
 * @param  Int index 行数
 * @return Int
 */
function getTableRowCellsLength(tableId, rowNo){
    var table = document.getElementById(tableId);
    if(rowNo<table.rows.length){
        return table.rows[rowNo].cells.length;
    }else{
        return 0;
    }
}

/**
 * 遍历表格内容返回数组
 * @param  Int   id 表格id
 * @return Array
 */
function getTableContent(tableId){
    var table = document.getElementById(tableId);
    var data = [];
    for(var i=0,rows=table.rows.length; i<rows; i++){
        for(var j=0,cells=table.rows[i].cells.length; j<cells; j++){
            if(!data[i]){
                data[i] = new Array();
            }
            data[i][j] = table.rows[i].cells[j].innerHTML;
        }
    }
    return data;
}

function getTableInfo(tableId){

}

function modalMoveById(id){
    $(id).draggable();//为模态对话框添加拖拽
}


/**
 * 取页面数值
 * @return Map
 **/
function getCurPageInfo(eventInfo){
    let pageInfoMap = {"curMenu":curMenuId,"webInputValueMap":getInputValueMap(eventInfo)};
    return pageInfoMap;
}

/**
 * 取输入区域取值
 * @return Map
 **/
function getInputValueMap(eventInfo){
    // 取标签属性含有out="Y"的页面元素值
    let nodeValueMap = getOutFlagEleValue(eventInfo);
    // 如果nodeValueMap为空，则仍按旧方式获取页面数据
    if(nodeValueMap==null || Object.keys(nodeValueMap).length == 0){
        if(nodeValueMap==null){
            nodeValueMap = {};
        }
        //取标签为 input 的页面元素
        let map = getNodeValueMap("input");
        for(let key in map){
            nodeValueMap[key] = map[key];
        }

        //取标签为 select 的页面元素
        map = getNodeValueMap("select");
        for(let key in map){
            nodeValueMap[key] = map[key];
        }
    }
    return nodeValueMap;
}

function getAllInputValueMap(){
    let nodeValueMap = {};
    // 取标签为 input 的页面元素
    let map = getNodeValueMap("input");
    for(let key in map){
        nodeValueMap[key] = map[key];
    }
    //取标签为 select 的页面元素
    map = getNodeValueMap("select");
    for(let key in map){
        nodeValueMap[key] = map[key];
    }
    return nodeValueMap;
}

/**
 * 取页面所有标签，属性含有out="Y"的值
 **/
function getOutFlagEleValue(eventInfo){
    let keyValueMap = {};
    // eventInfo.sourceElement为空退出
    if(eventInfo!=null && eventInfo.reqType != null && eventInfo.reqType == "menuReq"){
        return keyValueMap;
    }
    // 取eventInfo事件元素的上一层父元素，直到元素id以contentArea或className为subWindowBackGround开始为止
    let parentEle = document.getElementById(eventInfo.elementId);
    while (parentEle &&!parentEle.id.startsWith("contentArea") && !parentEle.className.startsWith("subWindowBackGround")) {
        parentEle = parentEle.parentNode;
    }
    // 遍历页面所有元素，找到属性out="Y"的元素
    nodeAllChildren(parentEle,keyValueMap);

    if(eventInfo != null && eventInfo.paramMap!=null && eventInfo.paramMap["tableRecord"] != null){
        for(let key in eventInfo.paramMap["tableRecord"]){
            keyValueMap[key] = eventInfo.paramMap["tableRecord"][key];
        }
    }
    return keyValueMap;
}

/**
 * 递归遍历所有子元素
 **/
function nodeAllChildren(node, keyValueMap){
    if(node == null) return;
    let childNodes = node.childNodes;
    let children = [];
    for (let i = 0; i < childNodes.length; i++) {
        let childNode = childNodes[i];
        let childNodeChildren = childNode.childNodes;
        if(childNodeChildren.length>0){
            // 如果元素属性list不为空，其子元素需要单独Map存放
            if(childNode.getAttribute("list")!=null){
                let childNodeMap = {};
                nodeAllChildren(childNode,childNodeMap);
                // 获取 list 属性的值
                const listKey = childNode.getAttribute("list");
                // 检查 keyValueMap[listKey] 是否为 undefined，如果是则初始化为空数组
                if (!keyValueMap[listKey]) {
                    keyValueMap[listKey] = [];
                }
                // 将 childNodeMap 推入数组
                keyValueMap[listKey].push(childNodeMap);
            }else{
                nodeAllChildren(childNode,keyValueMap);
            }
        }
        // 如果tag为input,select
        if(childNode.tagName == "INPUT"){
            if("file"===childNode.type){
                keyValueMap[childNode.getAttribute('elename')] = childNode.files;//支持多文件上传
            }else{
                let valueObject = {};
                valueObject["value"] = childNode.value;
                valueObject["defValue"] = childNode.defaultValue;
                keyValueMap[childNode.getAttribute('elename')] = valueObject;
            }
        }
        if(childNode.tagName == "SELECT"){
            //判断select/option是否为多选
            let isMultipleSelect = jQuery("#"+childNode.id).attr("multiple");
            if("multiple" == isMultipleSelect){
                let selectedValueMap;
                for(optionIndex=0;optionIndex<childNode.length;optionIndex++){
                    if(childNode.options[optionIndex].selected){
                        if(selectedValueMap==null) {selectedValueMap =new Array(); }
                        selectedValueMap.push(childNode.options[optionIndex].value);
                    }
                }
                let valueObject = {};
                valueObject["value"] = selectedValueMap;
                valueObject["defValue"] = "";
                keyValueMap[childNode.getAttribute('elename')] = valueObject;
            }else{
                let index = childNode.selectedIndex; // 选中索引
                if(index >= 0){
                    let text = childNode.options[index].text; // 选中文本
                    let valueObject = {};
                    valueObject["value"] = childNode.options[index].value; // 选中值
                    valueObject["defValue"] = "";
                    keyValueMap[childNode.getAttribute('elename')] = valueObject;
                }else{
                    let valueObject = {};
                    valueObject["value"] = "";
                    valueObject["defValue"] = "";
                    keyValueMap[childNode.getAttribute('elename')] = valueObject;
                }
            }
        }
    }
    return children;
}

/**
 * 取页面指定标签的当前值
 **/
function getNodeValueMap(nodeTag){
    let nodeValueMap = {};

    var nodeList = document.querySelectorAll(nodeTag);
    for (let i=0;i<nodeList.length;i++){
        let nodeEle = nodeList[i];
        if("select"===nodeTag){
            //判断select/option是否为多选
            let isMultipleSelect = jQuery("#"+nodeEle.id).attr("multiple");
            if("multiple" == isMultipleSelect){
                let selectedValueMap;
                for(optionIndex=0;optionIndex<nodeEle.length;optionIndex++){
                    if(nodeEle.options[optionIndex].selected){
                        if(selectedValueMap==null) {selectedValueMap =new Array(); }
                        selectedValueMap.push(nodeEle.options[optionIndex].value);
                    }
                }
                let valueObject = {};
                valueObject["value"] = selectedValueMap;
                valueObject["defValue"] = "";
                nodeValueMap[nodeEle.getAttribute('elename')] = valueObject;
            }else{
                let index = nodeEle.selectedIndex; // 选中索引
                if(index >= 0){
                    let text = nodeEle.options[index].text; // 选中文本
                    let valueObject = {};
                    valueObject["value"] = nodeEle.options[index].value; // 选中值
                    valueObject["defValue"] = "";
                    nodeValueMap[nodeEle.getAttribute('elename')] = valueObject;
                }else{
                    let valueObject = {};
                    valueObject["value"] = "";
                    valueObject["defValue"] = "";
                    nodeValueMap[nodeEle.getAttribute('elename')] = valueObject;
                }
            }
        }else if("input"===nodeTag){
            if("file"===nodeEle.type){
                nodeValueMap[nodeEle.id] = nodeEle.files;//支持多文件上传
            }else{
                let valueObject = {};
                valueObject["value"] = nodeEle.value;
                valueObject["defValue"] = nodeEle.defaultValue;
                nodeValueMap[nodeEle.getAttribute('elename')] = valueObject;
            }
        }
    }
    return nodeValueMap;
}

function getStringPX(str){
    let tempLabel = document.createElement("label");
    tempLabel.setAttribute("id","temp_label");
//    tempLabel.style.display="none";
    tempLabel.innerHTML=str;

    let ele = document.getElementById("bottomArea");
    ele.appendChild(tempLabel);
    let strWidth = tempLabel.clientWidth;
    ele.removeChild(tempLabel);

    return strWidth;
}

/**
 * 检查字符串是否为数字
 * @param str 字符串
 * @param decimalNum 小数位数
 * @return boolean
 */
function checkStringIsNumber(str, decimalNum){
    if(str == null || str == ""){
        return false;
    }
    if(decimalNum == null || decimalNum == ""){
        decimalNum = 0;
    }
    let reg = new RegExp("^[0-9]+(.[0-9]{"+decimalNum+"})?$");
    return reg.test(str);
}

/**
 * 检查字符串长度
 * @param str 字符串
 * @param length 长度
 * @return boolean
 */
function checkStringLength(str, length){
    if(str == null || str == ""){
        return true;
    }
    return str.length <= length;
}

/**
 * 检查字符串是否为空
 * @param str 字符串
 */
function checkStringNotEmpty(str){
    if(str == null || str == ""){
        return false;
    }
    return true;
}

// 实现一个简单的对象克隆函数
function cloneObject(obj) {
    if (typeof obj !== 'object' || obj === null) {
        return obj;
    }
    let clone = Array.isArray(obj) ? [] : {};
    for (let key in obj) {
        if (obj.hasOwnProperty(key)) {
            clone[key] = cloneObject(obj[key]);
        }
    }
    return clone;
}
