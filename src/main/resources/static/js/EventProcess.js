
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
        let del = confirm(eventInfo.paramMap["confirmCnt"]);
        if(!del){
            return;
        }
        // 因会将事件参数传到后续处理动作中，因此此处已弹窗确认后将标志改为false,不影响后续处理。
        eventInfo.paramMap["showConfirmSW"]=false;
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

/**
 * 取事件源信息，并保存在eventInfo中
 */
function getSourceElementInfo(eventEle, eventInfo){

    if(eventEle.parentElement != undefined && eventEle.parentElement != null && eventEle.parentElement.nodeName == 'TD'){
//        let tableElement = eventEle.parentElement.parentElement.parentElement.parentElement; // table
//        let rowNo = eventEle.parentElement.parentElement.rowIndex; //行号
//        let tableId = eventEle.parentElement.parentElement.parentElement.parentElement.id.replace("_body_table","");
//        let headTdList = document.getElementById(tableId+"_thead_tr").childNodes;
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
            eventInfo.reqPage = outputMap.pageNow - 1 < 0?0:outputMap.pageNow - 1;
        }else if("下一页" == eventEle.innerHTML){
            let totalPage = parseInt((outputMap.totalCount-1)/outputMap.pageSize)+1;
            eventInfo.reqPage = outputMap.pageNow + 1 > totalPage?totalPage:outputMap.pageNow + 1;
        }else{
            eventInfo.reqPage = eventEle.innerHTML;
        }
    }else if(eventEle.tagName == "TR"){
        if(null == eventInfo.paramMap) eventInfo.paramMap = {};
        let childList = eventEle.childNodes;
        for (let i=0;i<childList.length;i++){
            let node = childList[i];
            let colName = node.getAttribute("colName");
            let colValue = node.innerText;
            if(colName != null){
                eventInfo.paramMap[colName] = colValue;
            }
        }
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
