/**
 * 进入index.html自动执行initPageInfo()
 **/

var curMenuId = "";
var userNameKey="userName";
// var htmlMap = {};

/**
 * 初始化页面内容
 */
function initPageInfo(){
    if(getCookie(userNameKey) != null && getCookie(userNameKey)!=""){
        let reqMapping = "initPageInfo";
        let reqType = "normal";
        let reqMethod = "post";
        sendRequest(reqMapping,reqType,reqMethod,"{}");
        setUserInfo();
    }else{
        showLoginWindow();
    }
}

function setUserInfo(){
    const userLabel = document.getElementById('userNameLabel'); // 获取标签元素
    const userId = getCookie(userNameKey); // 获取用户ID
    if (userId) {
       userLabel.textContent = userId; // 显示用户名
    } else {
       userLabel.textContent = "未登录用户"; // 默认文本
    }
}

function showLoginWindow(){
    if(getCookie(userNameKey) != null && getCookie(userNameKey)!=""){
        return;
    }
    let reqMapping = "buttonReq";
    let reqType = "normal";
    let reqMethod = "post";
    let eventInfo = {
        element:"loginWebReq",
        event:"menuReq",
        menu:"root",
        page:"loginPage",
        reqMapping:"post",
        reqType:"swDataReq"
    }
    let requestParm = '{"eventId":"loginWebReq","reqParm":{}}';
    let requestObj = JSON.parse(requestParm);  //string -> obj
    requestObj.eventInfo = eventInfo; //事件信息
//    sendRequest(reqMapping,reqType,reqMethod,JSON.stringify(requestObj));
    sendJsonByAjax('menuReq/loginWebReq','post', JSON.stringify(requestObj),"application/json;charset=utf-8",null,sucFreshAll,eventInfo);
}

function setCookie(cname,cvalue,exdays){
    let d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    let expires = "expires="+d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function delCookie(cname){
    let expires = "expires=Thu, 01 Jan 1970 00:00:00 GMT";
    document.cookie = cname + "=" + getCookie(cname) + "; " + expires;
}

function getCookie(cname){
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i=0; i<ca.length; i++){
        let c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}

