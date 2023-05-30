
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
    let element = document.getElementById(elementId+"_group");
    if(element!=null){
        removeElement(element);
    }
    element = document.getElementById(elementId);
    if(element!=null){
        removeElement(element);
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
function getCurPageInfo(){
    let pageInfoMap = {"curMenu":curMenuId,"webInputValueMap":getInputValueMap()};
    return pageInfoMap;
}

/**
 * 取输入区域取值
 * @return Map
 **/
function getInputValueMap(){
    let nodeValueMap = {};

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
    return nodeValueMap;
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
                nodeValueMap[nodeEle.id] = valueObject;
            }else{
                let index = nodeEle.selectedIndex; // 选中索引
                if(index >= 0){
                    let text = nodeEle.options[index].text; // 选中文本
                    let valueObject = {};
                    valueObject["value"] = nodeEle.options[index].value; // 选中值
                    valueObject["defValue"] = "";
                    nodeValueMap[nodeEle.id] = valueObject;
                }else{
                    let valueObject = {};
                    valueObject["value"] = "";
                    valueObject["defValue"] = "";
                    nodeValueMap[nodeEle.id] = valueObject;
                }
            }
        }else if("input"===nodeTag){
            if("file"===nodeEle.type){
                nodeValueMap[nodeEle.id] = nodeEle.files;//支持多文件上传
            }else{
                let valueObject = {};
                valueObject["value"] = nodeEle.value;
                valueObject["defValue"] = nodeEle.defaultValue;
                nodeValueMap[nodeEle.id] = valueObject;
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