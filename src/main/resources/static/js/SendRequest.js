
var RTN_CODE_SUC = '0000'; // 后台主机响应返回的成功码值。

function sendRequest(reqMapping,reqType,reqMethod,requestParam) {
    sendJsonByAjax(reqMapping,reqMethod, requestParam,"application/json;charset=utf-8",null,sucFreshAll);
}

/**
 * 发送json报文到后台
 * @param requestUrl
 * @param requestType  post/get
 * @param requestParam
 * @param contentType
 * @param processData
 * @param sucfn
 */
function sendJsonByAjax(requestUrl, requestType, requestParam, contentType, processData, sucFun) {
    $.ajax({

        // 请求类型 post/get
        type: requestType,

        // 请求路径
        url: requestUrl,

        // 传送文件时 传 {let formData = new FormData(); let files = param.inputValue["file"];ReqJsonDto
        data: requestParam,

        // 传送文件时 传 false
        contentType: contentType,

        // 传送文件时 传 false, 否则不传
        processData: processData,

        // 通讯成功
        success:function (PublicResp) {
            // 后台主机响应正常
            if (PublicResp.rtnCode === RTN_CODE_SUC) {
                sucFun(PublicResp);

            // 后台主机响应异常
            } else {
                alert("请求成功，但后台处理失败!\n返 回 码:"+PublicResp.rtnCode + "\n失败信息:" + PublicResp.rtnMsg);
            }
        },
        // 通讯失败
        error:function (PublicResp) {
            alert("请求失败，返回信息："+PublicResp);
        }
    });
}
