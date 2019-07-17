/**
 * 
 */



function ajaxLoading(txt) {
	
	txt=txt||"正在处理...";
	
	var iotcss="  ";
	var sys= $("#sys").val();
	if(sys=="iot")
		iotcss=" iot ";
	
	
	var berthDocument = window.document;
	// 创建背景层
	var bgDiv = $("<div id='bgDiv' class='ie "+iotcss+" '></div>");
	// 获取当前文档宽度作为背景层宽度
	var bdWidth = $(berthDocument).width();
	// 获取当前文档高度作为背景层高度
	var bdHeight = $(berthDocument).height();
	// 设置背景层样式
	bgDiv.css({
		'width' : bdWidth,
		'height' : bdHeight,
		'position' : "fixed",
		'top' : 0,
		'left' : 0,
		"z-index" : 100000,
	
		"background-color" : "rgba(117, 117, 117, 0.55)",
		"opacity" : "0.85",
		"-moz-opacity" : "0.85",
		
	
		'position' : "absolute"

	});

	var maskWidth = 200;
	var maskHeight = 90;

	// var loadingDiv = $('<div id="loadingDiv" style="border:1px;"><img
	// src="'+basePath+'/images/loading.gif" /><br/><a
	// style="font-size:14px;">正在加载数据，请稍候...</a></div>');

	// var url=getImUrl()+"szhmpt/android/images/loading.gif";


	
	var loadingDiv = $("<div class=\"datagrid-mask-msg ie  \"></div>")
			.html(
					"<div class='loadimgdiv'><div src='1'  border='0' alt='' style='margin:10px 80px;' class='loadimg'></div>" 
					
					+' <div class="hide loadEffect"> '
					+'  <span></span> '
					+' <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+' <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+' </div> '
					
					+'<div class="hide loading"> '
					+'  <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+'  <span></span> '
					+'	</div> '
					
					
					+ "" 
					+"</div><div style='color:#ddd;text-align:center;'>"+txt+"</div>")
			.css({
				display : "block",

				background : 'rgba(66, 64, 64, 0.74)',
				width : maskWidth,
				height : maskHeight, 
				left : ($(document.body).outerWidth(true) - maskWidth) / 2,
				top : ($(window).height() - maskHeight) / 2
			});

	loadingDiv.css({

		'position' : "absolute",

		"z-index" : 999,
		"border-radius" : "5px",
		"-moz-border-radius" : "5px",
		"-webkit-border-radius" : "5px",
		"-moz-box-shadow" : "0 1px 2px rgba(0,0,0,0.5)",
		"-webkit-box-shadow" : "0 1px 2px rgba(0,0,0,0.5)",
		"text-shadow" : "0 -1px 1px rgba(0,0,0,0.25)",
		"border-bottom" : "1px solid rgba(0,0,0,0.25)"

	});
	// 将确认框添加到背景层中
	bgDiv.append(loadingDiv);
	// 将背景层添加 到页面中
	$(berthDocument).find("body").eq(0).append(bgDiv);
}

$(function(){
	
	ajaxLoading("正在登录...");
	
	//return;
	
	//window.open($("#regCallback").val(),"_blank");
	
	var browser= {
		        versions: function() {
		        var u = window.navigator.userAgent;
		        return {
		            trident: u.indexOf('Trident') > -1, //IE内核
		            presto: u.indexOf('Presto') > -1, //opera内核
		            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		            mobile: !!u.match(/mobile|Mobile|Android|android|iPhone|iphone|ipad|phone/i), //是否为移动终端
		            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
		            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者安卓QQ浏览器
		            iPad: u.indexOf('iPad') > -1, //是否为iPad
		            webApp: u.indexOf('Safari') == -1 ,//是否为web应用程序，没有头部与底部
		            weixin: u.indexOf('MicroMessenger') == -1 //是否为微信浏览器
		            };
		        }()
		    };
	
	
//	alert(window.navigator.userAgent);
	
	if(browser.versions.android)
	{
		//app端
		//调用客户端webview内嵌js
		//ticket-->
		try {
			Native.setCode($("#ticket").val());
			//setCode($("#ticket").val());
		} catch (e) {
			
			alert("code:"+$("#ticket").val()+"页面缺失Native.setCode JS方法");
		}
	}
	else if(browser.versions.iPhone||browser.versions.iPad){
		  window.webkit.messageHandlers.setCode.postMessage($("#ticket").val());
	}
	else
	{
		//alert(1);
		//网页端
		window.location.href=$("#regCallback").val();
	}
	
	
	
})
