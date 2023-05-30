/**
 * 进入index.html自动执行initPageInfo()
 **/

var curMenuId = "";
var userNameKey="userName";

/**
 * 初始化页面内容
 */
function initPageInfo(){
    let reqMapping = "initPageInfo";
    let reqType = "normal";
    let reqMethod = "post";
    sendRequest(reqMapping,reqType,reqMethod,"{}");
}

function showLoginWindow(){
    if(getCookie(userNameKey) != null && getCookie(userNameKey) != ""){
        alert("您 " + getCookie(userNameKey) + " 已登录！");
        return;
    }

//    showLoginModal();
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

