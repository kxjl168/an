
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/mobilehead.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0143)https://api.ztgmwl.com/oauth2/authorize?client_id=1111232509511&response_type=code&redirect_uri=http://passport.sohu.com/openlogin/callback/sina -->
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
  
    <style>
    body{
    	background: #fff!important; background-image: none!important;
    }
    
   .loadEffect{
            width: 100px;
            height: 100px;
            position: relative;
            margin: 0 auto;
            margin-top:10px;
            margin-bottom: 5px;
        }
        .loadEffect span{
            display: inline-block;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            background: lightgreen;
            position: absolute;
            -webkit-animation: load 1.04s ease infinite;
        }
        @-webkit-keyframes load{
            0%{
                opacity: 1;
            }
            100%{
                opacity: 0.2;
            }
        }
        .loadEffect span:nth-child(1){
            left: 0;
            top: 50%;
            margin-top:-8px;
            -webkit-animation-delay:0.13s;
        }
        .loadEffect span:nth-child(2){
            left: 14px;
            top: 14px;
            -webkit-animation-delay:0.26s;
        }
        .loadEffect span:nth-child(3){
            left: 50%;
            top: 0;
            margin-left: -8px;
            -webkit-animation-delay:0.39s;
        }
        .loadEffect span:nth-child(4){
            top: 14px;
            right:14px;
            -webkit-animation-delay:0.52s;
        }
        .loadEffect span:nth-child(5){
            right: 0;
            top: 50%;
            margin-top:-8px;
            -webkit-animation-delay:0.65s;
        }
        .loadEffect span:nth-child(6){
            right: 14px;
            bottom:14px;
            -webkit-animation-delay:0.78s;
        }
        .loadEffect span:nth-child(7){
            bottom: 0;
            left: 50%;
            margin-left: -8px;
            -webkit-animation-delay:0.91s;
        }
        .loadEffect span:nth-child(8){
            bottom: 14px;
            left: 14px;
            -webkit-animation-delay:1.04s;
        }
        
        
      /*   .loading{
            width: 80px;
            height: 40px;
            margin: 0 auto;
             margin-top:100px;
        }
        .loading span{
            display: inline-block;
            width: 8px;
            height: 100%;
            border-radius: 4px;
            background: lightgreen;
            -webkit-animation: load 1s ease infinite;
        }
        @-webkit-keyframes load{
            0%,100%{
                height: 40px;
                background: lightgreen;
            }
            50%{
                height: 70px;
                margin: -15px 0;
                background: lightblue;
            }
        }
        .loading span:nth-child(2){
            -webkit-animation-delay:0.2s;
        }
        .loading span:nth-child(3){
            -webkit-animation-delay:0.4s;
        }
        .loading span:nth-child(4){
            -webkit-animation-delay:0.6s;
        }
        .loading span:nth-child(5){
            -webkit-animation-delay:0.8s;
        } */
        
        
    .loadimgdiv {
    width: 30px;
    height: 30px;
    margin-bottom: 20px;
}
        
      
        
        /*移动适配 zj*/
@media screen and (max-width: 768px) {
	  #bgDiv.ie{
        	 background: url(../image/bk.png) no-repeat!important;
        	   background-size:cover!important;
        }
        
         #bgDiv.ie.iot{
        	 background: url(../image/iotback.png) no-repeat!important;
        	   background-size:cover!important;
        }
	}
	@media screen and (min-width: 768px) {
	  #bgDiv.ie{
        	background-color : #333;
        	
        }
        .datagrid-mask-msg.ie {
        background : #333!important;
        }
        }
        
        
.loadimg {
    background: url(../image/loading.gif);
    width: 100%;
    height: 100%;
    display: block;
    border: 0;
} 


        
    </style>
    
   <meta http-equiv="pragma" content="no-cache">
 <meta http-equiv="cache-control" content="no-cache">
 <meta http-equiv="expires" content="0">   
 
</head>
<body class="WB_widgets">

  <input type="hidden" id="regCallback" name="regCallback" value="${oauthBean.regCallback}">
      <input type="hidden" name="action" id="action" value="${oauthBean.action}">
      <input type="hidden" name="ticket" id="ticket" value="${oauthBean.ticket}">
       <input type="hidden" name="sys" id="sys" value="${oauthBean.sys}">
 
 
 
 
  <script src="${ctx}/web/oauth/close.js?t=1"></script> 
</body></html>