
/**
 * 设置点击事件处理方法
 * @param eventInfo 绑定在页面元素事件的事件定义信息
 * @param sourceElement 发生事件的元素
 */
function executeEventMethod(eventInfo, sourceElement) {
    // 取事件源信息
    var event = window.event || arguments[0];
    var eventEle = event.currentTarget;
    eventInfo.reqPage = 0;

    // 判断该事件需要如何处理：
    // 1. 需要先 弹窗确认，客户取消则退出事件，否则继续后续处理
    if(eventInfo!=null && eventInfo.paramMap!=null && eventInfo.paramMap["showConfirmSW"]){
        let transferCnt = eventInfo.paramMap["transferCnt"];
        if(transferCnt==null || transferCnt<=0){
            let del = confirm(eventInfo.paramMap["confirmCnt"]);
            if(!del){
                return;
            }
        }
        // 因会将事件参数传到后续处理动作中，因此此处已弹窗确认后将标志改为false,不影响后续处理。
//        eventInfo.paramMap["showConfirmSW"]=false;
    }

    // 2. 按钮实现：新增一行
    if(eventInfo.reqType == "addNewRecord"){
        insertIntoNewLine(eventInfo);
        return;
    }
    // 3. 按钮实现：删除一行
    if(eventInfo.reqType == "delRecordLine"){
        delRecordLine(eventInfo);
        return;
    }

    // 取事件源信息，并保存在eventInfo中
    eventInfo = getSourceElementInfo(eventEle, eventInfo);

    let data;        //请求数据
    let contentType;
    let processData;

    let requestParm = '{"eventId":"","reqParm":{}}';
    let requestObj = JSON.parse(requestParm);  //string -> obj
    requestObj.eventId = eventInfo.id;
    requestObj.curMenu = curMenuId;
    requestObj.userName = getCookie(userNameKey);
    let param = getCurPageInfo(eventInfo);
    requestObj.webValueDto = param;
    requestObj.eventInfo = eventInfo; //事件信息
    let requestJsonStr = JSON.stringify(requestObj); // obj -> string

    if(eventInfo.type == "fileReq"){
        let formData = new FormData();
        let files = param.inputValue["file"];
        for(let j=0;j<files.length;j++){
            formData.append('fileList',files[j]);
        }
        formData.append('requestDto', requestJsonStr);
        data = formData;
        contentType = false;
        processData = false;
    }else{
        data = requestJsonStr;
        contentType = "application/json;charset=utf-8";
        processData = null;
    }
    sendJsonByAjax(eventInfo.reqType+'/'+eventInfo.reqMapping,'post', data,contentType,processData,sucFreshAll,eventInfo);
}

function clearUpWebData(){

}

function insertIntoNewLine(eventInfo){
    // 获取事件发生的元素
    var event = window.event || arguments[0];
    var eventEle = event.currentTarget;
    let copyDivId = eventInfo.paramMap["targetDiv"];
    // 获取事件元素的上一层父元素，直到元素id以copyDivId开始为止
    let parentEle = eventEle.parentNode;
    let parentEleName = document.getElementById(eventEle.parentNode.id).getAttribute("elename");
    while (eventEle.parentNode &&
        getElementByEleName(eventEle.parentNode.id)!=null &&
        getElementByEleName(eventEle.parentNode.id).length>0 &&
        !getElementByEleName(eventEle.parentNode.id).startsWith(copyDivId)) {
         parentEle = parentEle.parentNode;
         parentEleName = document.getElementById(eventEle.parentNode.id).getAttribute("elename");
    }
    // 将id的前部分copyDivId替换为空，得到当前list的号码
    let listNum = document.getElementById(parentEle.id).getAttribute("elename").replace(copyDivId,"");

    let divNew = parentEle.cloneNode(true);
    // 删除divNew下的所有元素
    while (divNew.firstChild) {
        divNew.removeChild(divNew.firstChild);
    }
    // 遍历parentEle下的所有元素，拷贝到divNew中
    for (let i = 0; i < parentEle.childNodes.length; i++) {
        let childNode = parentEle.childNodes[i];
        let childEleName = childNode.getAttribute("elename");
        // 属性type值为addRecordLineButton的元素不拷贝
        if (childNode.getAttribute("defType") && childNode.getAttribute("defType") == "addRecordLineButton") {
            divNew.appendChild(childNode);
            // id以delRecordLineButton_开头的元素不拷贝
        }else if(childNode.id && childEleName && childEleName.startsWith("delRecordLineButton_")){
        }else{
            divNew.appendChild(childNode.cloneNode(true));
        }
    }

    // 设置新元素的id
    divNew.id = copyDivId + (parseInt(listNum)+1);
    divNew.elename = copyDivId + (parseInt(listNum)+1);
    // 新增"-"按钮
    // 定义事件信息
    let eventInfoList = [];
    let eventInfoCopy = cloneObject(eventInfo);
    eventInfoCopy.reqType = "delRecordLine";
    eventInfoCopy.element = "delRecordLineButton_"+(parseInt(listNum)+1);
    eventInfoList.push(eventInfoCopy);
    let elementInfo = {
        "elename": "delRecordLineButton_"+(parseInt(listNum)+1),
        "desc":"-",
        "eventInfoList":eventInfoList,
        "attrMap":{
            "class":"inputArea_sub_button"
        },
        "seq":998
    }
    writeButton(divNew,elementInfo);
    parentEle.parentNode.appendChild(divNew);
}

function delRecordLine(eventInfo){
    // 获取事件发生的元素
    var event = window.event || arguments[0];
    var eventEle = event.currentTarget;
    let copyDivId = eventInfo.paramMap["targetDiv"];
    // 获取事件元素的上一层父元素，直到元素id以copyDivId开始为止
    let parentEle = eventEle.parentNode;
    while (eventEle.parentNode && !eventEle.parentNode.id.startsWith(copyDivId)) {
        parentEle = parentEle.parentNode;
    }
    // 将id的前部分copyDivId替换为空，得到当前list的号码
    let listNum = parentEle.id.replace(copyDivId,"");
    // 按id号找到前一行元素，如果前一个id不存在则再找前一个id，直到找到为止
    let index = parseInt(listNum) - 1;
    let preRecordLine = document.getElementById(copyDivId + index);
    while (preRecordLine == null && index > 1) {
        index = index - 1;
        preRecordLine = document.getElementById(copyDivId + index);
    }

    // 遍历parentEle下的所有元素，找到属性type值为addRecordLineButton的元素
    let addButEle;
    for (let i = 0; i < parentEle.childNodes.length; i++) {
        let childNode = parentEle.childNodes[i];
        // 属性type值为addRecordLineButton的元素
        if (childNode.getAttribute("defType") && childNode.getAttribute("defType") == "addRecordLineButton") {
            addButEle = childNode;
            break;
        }
    }
    if(addButEle!=null && preRecordLine!=null){
        // 把addButEle移动到preRecordLine后面
        preRecordLine.append(addButEle);
    }
    // 删除元素
    parentEle.parentNode.removeChild(parentEle);
}


/**
 * 取事件源信息，并保存在eventInfo中
 */
function getSourceElementInfo(eventEle, eventInfo){

    if(eventEle.parentElement != undefined && eventEle.parentElement != null && eventEle.parentElement.nodeName == 'TD'){
        let curTDList = eventEle.parentElement.parentElement.childNodes;
        if(null == eventInfo.paramMap) eventInfo.paramMap = {};
        for (let i=0;i<curTDList.length;i++){
            eventInfo.paramMap[curTDList[i].getAttribute("colName")] = curTDList[i].innerText;
        }
    }

    if(eventEle.tagName == "INPUT"){
        // 当没有值时不
        if(eventEle.value == undefined || eventEle.value == "" || eventEle.value == null){
            return;
        }
        // 有值时，返回当前值
        eventInfo.selectedValue = eventEle.value;

    // 分页按钮，需要返回请求的页码 reqPage
    }else if(eventEle.tagName == "A" && eventInfo.withPage){ //分页按钮请求
        if("上一页" == eventEle.innerHTML){
            eventInfo.reqPage = (eventInfo.pageNow==0?1:eventInfo.pageNow) - 1 <= 0?1:eventInfo.pageNow - 1;
        }else if("下一页" == eventEle.innerHTML){
            let totalPage = parseInt((eventInfo.totalCount-1)/eventInfo.pageSize)+1;
            eventInfo.reqPage = (eventInfo.pageNow==0?1:eventInfo.pageNow) + 1 > totalPage?totalPage:(eventInfo.pageNow==0?1:eventInfo.pageNow) + 1;
        }else{
            eventInfo.reqPage = eventEle.innerHTML;
        }
    }else if(eventEle.tagName == "TR"){
        if(null == eventInfo.paramMap) eventInfo.paramMap = {};
        let childList = eventEle.childNodes;
        let colNameValueMap = {};
        for (let i=0;i<childList.length;i++){
            let node = childList[i];
            let colName = node.getAttribute("colName");
            let colValue = node.innerText;
            if(colName != null){
                colNameValueMap[colName] = colValue;
            }
        }
        eventInfo.paramMap["tableRecord"] = colNameValueMap;
    }else{
        if(eventInfo.type == "menuReq"){
            curMenuId = eventInfo.id;
            document.getElementById("navSpan").innerHTML= eventInfo.id;
        } else if(eventInfo.type == "webDataReq"){
            var selectedValue = $('select  option:selected').val();
            eventInfo.selectedValue = selectedValue;
        }
    }
    return eventInfo;
}

/**
 * 把记录的原值保存在 eventInfo.recordMap.curValue
 */
function putChangeValue(param,eventInfo){
    let inputValueMap = param["inputValue"]; //输入区域值
    let recordMap = eventInfo.recordMap;     //recordMap[field].value,原记录值
    if(inputValueMap != null && recordMap != null){
        for(let modalFieldName in inputValueMap){ //key:attrName,value:attrMap[attrName]
            if("modal" === modalFieldName.substring(0,5) ) {
                let fieldName = modalFieldName.substring(5);
                recordMap[fieldName].curValue = inputValueMap[modalFieldName];
            }
        }
    }
    return eventInfo;
}
