/**
 * 
 */

var rid = null;
$(function() {
	rid = GetQueryString("id");

	/*$.ajax({
		type : "post",
		url : getRPath() + "/manager/usermessage/managermessageList",
		data : {

		},
		dataType : "json",
		success : function(list) {
			refreshMsg(list);
		},
		error : function(a, b, c) {
			console.log()
			// error("1");
		}
	});*/

	setTimeout(function() {
		connect();
	}, 2000);

});

var ws = null;
var ws_status='close';

function refreshMsg(list) {
	if (null != list && undefined != list) {

	}
}


$(function(){
	
	$("#btnmsg").click(function(){
	
		sendmsg();
	})
	
	$('#txtmsginput').keyup(function (e) {
    if (e.keyCode === 13 ) {
    	sendmsg();
    }
	});

})


function sendmsg(){
	
	 $(".tpdata").html( $("#txtmsginput").val());
	
	if($(".tpdata").text()=="")
	{
	info("请输入消息内容");	
	return;
	}
	
	   if(ws&&ws.readyState==1)
	   {
   }
   else{
	   connect();
	   info("链接断开,正在重新连接...请稍后重试");
	   return;;
   }

	   
	   var allinput= $("#txtmsginput").val();
	   var imgs= $(allinput).find("img");
	   $.each(imgs,function(index,item){
		   var fileurl=$(item).attr("fid");
			send(curAlarmId,"","2",fileurl);
			var msginfo=formatMsg("","2",getRPath() +"/upload/file/"+fileurl)
			appendToList(msginfo,true);
	   });

	  
	   $(".tpdata").html(allinput);
	   $(".tpdata").find("img").remove();
	   
	   var rowdatas= $(".tpdata").text().split(/[\r\n]/g);;
	   $.each(rowdatas,function(index,item){
		   if(item!='')
			   {
			send(curAlarmId,item);
			appendToList(item,true);
			   }
	   });

	
	$("#txtmsginput").val("");
}


function formatMsg(msg,msgtype,fileurl)
{
	if(msgtype=="1")
		return msg;
	else if(msgtype=="2")//img
		return "<span class='talkimg'><img src='"+fileurl+"' class='img-responsive '></span>";
	
	
}

/**
 * 收到app端消息
 * @param jdata
 * @returns
 */
function getmsg(jdata)
{
	
	//uid://alarmid
	if(typeof( jdata.uid)!='undefined')
		{
		if(jdata.uid==curAlarmId) //当前报警聊天
			{
			var msginfo=formatMsg(jdata.msg,jdata.msgtype,jdata.fileurl)
			
			appendToList(msginfo,false);
			}
		else
			{
			//其他报警中的聊天消息，小红点提示.
			refreshOtherAlarmNew(jdata.uid);
			}
		}
}
//日期时间处理
function conver(s) {
return s < 10 ? '0' + s : s;
}
function getDate(){

   var myDate = new Date();

    //获取当前年
   var year = myDate.getFullYear();

   //获取当前月
   var month = myDate.getMonth() + 1;

    //获取当前日
    var date = myDate.getDate();
    var h = myDate.getHours(); //获取当前小时数(0-23)
    var m = myDate.getMinutes(); //获取当前分钟数(0-59)
    var s = myDate.getSeconds();

   //获取当前时间

   var now = year + '-' + conver(month) + "-" + conver(date) + " " + conver(h) + ':' + conver(m) + ":" + conver(s);
 return now;
}

/**
 * 更新消息列表
 * @param msg
 * @param issend
 * @param date
 * @returns
 */
function appendToList(msg,issend,date,addtoFront)
{
	var name=" 接警员 ";
	var css=" send ";
	if(!issend)
		{
		css=" get ";
		name=" 报警人 ";
		}
	
	
	
	
	
	
	var existData= $("#txtmsglist").html();
	
	if(typeof(date)=="undefined")
		date=getDate();
	
	if(typeof(addtoFront)!='undefined') //往前加历史记录
		existData="<div class='"+css+"'><div class='talktime'>"+date+name+"</div><div class='msg'>"+msg+"</div></div>"+existData;
	else
	existData+="<div class='"+css+"'><div class='talktime'>"+date+name+"</div><div class='msg'>"+msg+"</div></div>";
	
 $("#txtmsglist").html(existData);
 

   if(typeof(addtoFront)=='undefined') //正常往下加的才滚动，
	   {
	   //滚动到底部
	   var ele = document.getElementById('txtmsglist');
	   ele.scrollTop = ele.scrollHeight;
	   }
 
}
//将字符串转为 Array byte数组
	function stringToByte(str) {  
		    var bytes = new Array();  
		    var len, c;  
		    len = str.length;  
		    for(var i = 0; i < len; i++) {  
		        c = str.charCodeAt(i);  
		        if(c >= 0x010000 && c <= 0x10FFFF) {  
		            bytes.push(((c >> 18) & 0x07) | 0xF0);  
		            bytes.push(((c >> 12) & 0x3F) | 0x80);  
		            bytes.push(((c >> 6) & 0x3F) | 0x80);  
		            bytes.push((c & 0x3F) | 0x80);  
		        } else if(c >= 0x000800 && c <= 0x00FFFF) {  
		            bytes.push(((c >> 12) & 0x0F) | 0xE0);  
		            bytes.push(((c >> 6) & 0x3F) | 0x80);  
		            bytes.push((c & 0x3F) | 0x80);  
		        } else if(c >= 0x000080 && c <= 0x0007FF) {  
		            bytes.push(((c >> 6) & 0x1F) | 0xC0);  
		            bytes.push((c & 0x3F) | 0x80);  
		        } else {  
		            bytes.push(c & 0xFF);  
		        }  
		    }  
		    return bytes;  


		}
		
		//byte数组转字符串
		function byteToString(arr) {
			if(typeof arr === 'string') {
				return arr;
			}
			var str = '',
				_arr = arr;
			for(var i = 0; i < _arr.length; i++) {
				var one = _arr[i].toString(2),
					v = one.match(/^1+?(?=0)/);
				if(v && one.length == 8) {
					var bytesLength = v[0].length;
					var store = _arr[i].toString(2).slice(7 - bytesLength);
					for(var st = 1; st < bytesLength; st++) {
						store += _arr[st + i].toString(2).slice(2);
					}
					str += String.fromCharCode(parseInt(store, 2));
					i += bytesLength - 1;
				} else {
					str += String.fromCharCode(_arr[i]);
				}
			}
		return str;
	}
		
		
function send(uid,msg,msgtype,fileurl)
{
	
	if(typeof(msgtype)=="undefined")
		msgtype="1";
	
	

    var jmsg={
		   "type":'websend',//web端发送给app
		   "tid":encodeURIComponent(uid),
		   "uid": encodeURIComponent(rid),
    		"msg":msg,
    		"msgtype":msgtype,
    		"fileurl":fileurl
    };
   var smsg= JSON.stringify(jmsg);
   
   
	//将字符串转换为byte数组
	var bytesArr= stringToByte(smsg);   
	var bytes =new Uint8Array(bytesArr.length) ;
	for (var i = 0; i < bytes.length; i++) {
		bytes[i]=bytesArr[i];
	}  
	console.log(bytes)
 // websocket.send(bytes);
   
    ws.send(bytes);
	
	
	
}



function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}

// 连接
function connect() {
	if (ws != null) {
		disconnect();
		//return;
	}

	var hostport = document.location.host;

	var websocketServerUrl; // 服务器地址

	var protocolStr = document.location.protocol;
	if (protocolStr.indexOf("https") >= 0) {
		websocketServerUrl = "wss://" + hostport + getContextPath()
				+ "/talkwebsocket/" + rid;
	} else {
		websocketServerUrl = "ws://" + hostport + getContextPath()
				+ "/talkwebsocket/" + rid;
	}

	url = websocketServerUrl;// "${websocketPath}${port}${ctx}/websocket/${principal.user.id}";
	if ('WebSocket' in window) {
		ws = new WebSocket(url);
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket(url);
	} else {
		alert("您的浏览器不支持WebSocket");
		return;
	}
	ws.onopen = function() {
		// 设置发信息送类型为：ArrayBuffer
		ws.binaryType = "arraybuffer";

		// 发送一个字符串和一个二进制信息
		ws.send("thank you for accepting this WebSocket request");
		var a = new Uint8Array([ 8, 6, 7, 5, 3, 0, 9 ]);
		ws.send(a.buffer);
		
		connCount=0;
	}
	ws.onmessage = function(e) {
		
		try {
			var unit8Arr = new Uint8Array(e.data) ; 
			var str=byteToString(unit8Arr); 
	        
	        var list = JSON.parse(str);
	        
	        
	        if(list!=null)
	        	{
	        	getmsg(list);
	        	}
			
		} catch (e) {
			// TODO: handle exception
		}
	    
		
	}
	ws.onclose = function(e) {
		 console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)
		  console.log(e)
		  reconnection();
	}
	ws.onerror = function(e) {
		console.log("error");
		 reconnection();
	}
}

var connCount=0;
var connMax=10;
var connTimeoutHandler=null;
var reconnection = function() {
	connCount = connCount + 1;
    console.log("reconnection...【" + connCount + "】");
    //1与服务器已经建立连接
    if (connCount >= connMax || ws.readyState == 1) {
        clearTimeout(connTimeoutHandler);
    } else {
        //2已经关闭了与服务器的连接
        if (socket.readyState == 3) {
        	connect();
        }
        //0正尝试与服务器建立连接,2正在关闭与服务器的连接
        connTimeoutHandler= setTimeout(function() {reconnection();}, 100);
    }
}

// 断开连接
function disconnect() {
	if (ws != null) {
		ws.close();
		ws = null;
	
	}
}
