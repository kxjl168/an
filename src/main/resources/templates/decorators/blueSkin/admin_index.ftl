<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>警务110管理平台</title>
    <link rel="icon" href="${ctx}/img/icon.png" type="image/x-icon"/>
    <link rel="shortcut icon" href="${ctx}/img/icon.png" type="image/x-icon"/>

<#--bootstrap-->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrapValidator/css/bootstrapValidator.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-table/css/bootstrap-table.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-datepicker/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-treeview/css/bootstrap-treeview.min.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-select2/css/bootstrap-select.min.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-buttons/css/buttons.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-datepicker/css/bootstrap-datetimepicker.css">


    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/bootstrap-xeditor/bootstrap-editable.css">

<#--图标码-->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/font-awesome/css/font-awesome.css">

<#--页面整体样式css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/blueSkin/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/blueSkin/style-responsive.css">

<#--地区选择-->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/distpicker/css/main.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/distpicker/css/normalize.css">

<#--select2-->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/select2/css/select2.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/select2/css/select2.bootstrap.css">

<#-- sweetalert -->
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/sweetalert/css/sweetalert2.min.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/css/tooltip.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile.css">

    <script src="${ctx}/vendor/jquery/jquery.js"></script>

    <script src="${ctx}/vendor/jquery/jquery-migrate-3.0.1.min.js"></script>

    <script src="${ctx}/vendor/jquery/jquery.plugin.js"></script>
    
    
     <script src="${ctx}/vendor/tippyjs/popper.min.js"></script>
     <script src="${ctx}/vendor/tippyjs/tippy.js"></script>
     

    <sitemesh:write property="head"/>


<script>

$(function(){
	$("#sidebar").on('mouseleave',' .menu1 ',function(){
		 $("#sidebar .nav-sidebar ul").hide();
	    });

	    
	    $('#sidebar').on('mouseover','.nav-sidebar li a',function(e){//显示隐藏
	    	e.stopPropagation();
	        $('.nav-sidebar a').removeClass('active');
	        $(this).addClass('active');
	        $(this).parents("ul").prev().addClass('active');
	    	
	        $(this).parent().parent().find('ul').hide();
	    	$(this).parent().children('ul').show();
	    	if($(this).parent().find('li').length){
	    		$(this).parent().children('ul').show();
	    	}
	    });


		    


	    // 顶部菜单固定
	    $('.zzbutn').click(function () {


	    	  var fixednavbar = localStorage.getItem("fixednavbar");
	    	
		    
	    	    if (fixednavbar == 'on') {
	    	    	$("body").removeClass('zz');

	    			$(".zzbutn").attr('data-tippy-content','收起菜单');
	    	    	
	    	    	 if (localStorageSupport) {
			                localStorage.setItem("fixednavbar", 'off');
			            }
	    	    }
	    	    else{
	    	    	$("body").addClass('zz');
	    	    	$(".zzbutn").attr('data-tippy-content','展开菜单');
	    	    	
	    	    	 if (localStorageSupport) {
			                localStorage.setItem("fixednavbar", 'on');
			            }
		    	    }

	   
	    });

	        if (localStorageSupport) {
	            
	            var fixednavbar = localStorage.getItem("fixednavbar");
	          
	            if (fixednavbar == 'on') {
	                $('body').addClass("zz");
	                $(".zzbutn").attr('data-tippy-content','收起菜单');
	            }
	            else
		            {
	            	$('body').removeClass("zz");
	            	$(".zzbutn").attr('data-tippy-content','展开菜单');
	            	}
	        }

	    	/* setTimeout(function(){
				tippy(".zzbutn",{
					placement:'right',
						 arrow: true,
						  arrowType: 'round', // or 'sharp' (default)
						  animation: 'perspective',
				}
        				)
			},500); */
        	

})

</script>


</head>
<body style="width: 100%; height:100%;" class="zz">
 <input type="hidden" id="managerType2" value="${(Session["user"].type)?c}">
<section id="container">

    <header class="header black-bg">
        <a href="${ctx}/manager/admin/index.action" class="logo">
        <img src="${ctx}/img/blueSkin/logo-ga.png" 
                                                                      style="height: 50px;margin-top: -10px;"/>
                                                                      
                                                                      <img class="hide" src="${ctx}/img/blueSkin/logo4.png"
                                                                      style="height: 50px;margin-top: -10px;"/>
                                                                      </a>

        <div class="top-menu">
            <ul class="nav pull-right top-menu">
            
             <li class="refreshBtn maxhide">
                       <a  title="菜单" id="menumobile"  style="margin-top: 20px;"><i class=" fa fa-bars"></i>菜单</a>
                       
                        <ul id="mmenu" class="dmenu mmobile  collapse " >
                   
                        <#list principal.userMenus as menu>

			
			<li class="menu1">
			    <a     class="menu-one" oid="${menu.id }"   >
			      <i  title='${menu.name}' class="micon ${menu.icon?default('fa fa-cube')}"></i>
					<text oid="${menu.id }">${menu.name}</text><span class=" "></span>
		       </a>
					
				<ul  id="${menu.id}" class="menu-two collapse " >
					<#assign persissions = menu.permissions>
					 <#list persissions as
					permission>
					<li  class="menu-two-item"><a href="${ctx}${permission.url}">${permission.name}</a></li>
					</#list>
				</ul>
				
			
				 
				
			</li> 
				
			</#list>
                        
                      
                        </ul>
                       
                
		
                </li>
                
                <script>
                $(function() {

                	$('#menumobile').click(function(){

                		if($("#mmenu").hasClass("open"))
                		$("#mmenu").removeClass("open");
                		else
                			$("#mmenu").addClass("open");

                	});


                	$('.menu-one').click(function(e){

                		var tarid=$(e.target).attr('oid');
                		if($("#"+tarid).hasClass("open"))
                		$("#"+tarid).removeClass("open");
                		else
                			$("#"+tarid).addClass("open");

                	});

                })
		</script>

		   
                
            
                <li class="refreshBtn">
                
              
                
                
                    <a id="message" title="消息" data-toggle="dropdown" style="margin-top: 20px;"><i class=" fa fa-bell"></i>消息
                        <span id="hasUnReadMsg" class="badge hide " style="background:red;"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="message">
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="${ctx}/manager/orderInfo/index">待确认
                                <span id="daiqueren" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="${ctx}/manager/torderinfo_todo/manager">待接单
                                <span id="daijiedan" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="${ctx}/manager/torderinfo_huif/manager">待回访
                                <span id="daihuifang" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                        <div class="hide">
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="#">取消工单
                                <span id="cancelOrder" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                        </div>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="${ctx}/manager/torderreview/manager">申请加钱
                                <span id="addMoney" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                          <div class="hide">
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="/manager/userDeposit/index">申请提现
                                <span id="tixian" class="badge pull-right" style="background:red"></span>
                            </a>
                        </li>
                        </div>
                    </ul>
                </li>
                <li class="refreshBtn mobilehide">
                    <a href="javascript:void(0);" onclick="myWorkbench();" title="我的控制台" style="margin-top: 20px;">
                        <i class="fa fa-home">控制台</i></a>
                </li>
                
                 <li class="refreshBtn maxhide">
                    <a href="javascript:void(0);" onclick="myWorkbench();" title="我的控制台" style="margin-top: 20px;">
                        <i class="fa fa-home">控制台</i></a>
                </li>
                <li class="refreshBtn mobilehide">
                    <a href="javascript:void(0);" onclick="refreshMenu();" title="重新加载菜单" style="margin-top: 20px;"><i
                            class="fa fa-refresh">刷新菜单</i></a> 
                </li>
                <li>
                    <a href="${ctx}/logout.action" style="margin-top: 20px;"><i class="fa fa-sign-out">退出</i></a>
                </li>
            </ul>
        </div>
    </header>


<#include "admin_left_navi.ftl"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <sitemesh:write property="body"/>
            </div>
        </section>
    </section>
</section>



<script src="${ctx}/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/vendor/jquery/jquery.dcjqaccordion.2.7.js"></script>
<script src="${ctx}/vendor/jquery/jquery.scrollTo.min.js"></script>
<script src="${ctx}/vendor/jquery/jquery.nicescroll.js"></script>
<script src="${ctx}/vendor/jquery/jquery.sparkline.js"></script>
<script src="${ctx}/vendor/blueSkin/common-scripts.js"></script>
<script src="${ctx}/vendor/bootstrapValidator/js/bootstrapValidator.js"></script>
<script src="${ctx}/vendor/bootstrapValidator/js/bootstrapValidator-zh-cn.js"></script>
<script src="${ctx}/vendor/bootstrap-table/js/bootstrap-table.js"></script>
<script src="${ctx}/vendor/bootstrap-table/js/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/vendor/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/vendor/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script src="${ctx}/vendor/bootstrap-datepicker/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/vendor/bootstrap-datepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="${ctx}/vendor/bootstrap-treeview/js/bootstrap-treeview.min.js"></script>


<script src="${ctx}/vendor/bootstrap-select2/js/bootstrap-select.js"></script>
<script src="${ctx}/vendor/bootstrap-select2/js/i18n/defaults-zh_CN.min.js"></script>


<script src="${ctx}/vendor/distpicker/js/distpicker.js"></script>


<script src="${ctx}/vendor/sweetalert/js/sweetalert2.min.js"></script>

<script src="${ctx}/vendor/bootstrap-xeditor/bootstrap-editable.js"></script>
<script src="${ctx}/vendor/bootstrap-xeditor/x-editor.js"></script>


<script src="${ctx}/vendor/select2/js/select2.full.js"></script>
<script src="${ctx}/vendor/select2/js/i18n/zh-CN.js"></script>

<script src="${ctx}/js/iot.js"></script>


<script type="text/javascript">
    var ws = null;


    function refreshMsg(list){
    	 if (null != list && undefined != list) {

				
              var total=0;

              for (var i = 0; i < list.length; i++) {
                  switch (list[i].type){
                      case 0+"":

                          break
                      case 1+"":
                          $('#daijiedan').html(list[i].num)
                          total+=list[i].num;
                          break
                      case 2+"":
                          $('#daihuifang').html(list[i].num)
                          total+=list[i].num;
                          break
                      case 3+"":
                          $('#addMoney').html(list[i].num)
                          total+=list[i].num;
                          break
                      case 4+"":
                          $('#cancelOrder').html(list[i].num)
                          total+=list[i].num;
                          break
                      case 5+"":
                          $('#tixian').html(list[i].num)
                          total+=list[i].num;
                          break
                      case 6+"":
                          $('#daiqueren').html(list[i].num)
                          total+=list[i].num;
                          break
                  }

              }


              if(list.length>0){

              	$(".unreadicon").removeClass("red").addClass("red");
	                //	newmsg("收到一条新的系统消息!")
	                   $("#hasUnReadMsg").html( total);
	                    $('#hasUnReadMsg').removeClass('hide');
	                }else{
	                	$("#hasUnReadMsg").html(0);
	                    $('#hasUnReadMsg').addClass('hide');
	                }
		 	

          }}

    $(function(){

    	
    	setTimeout(function(){
    		// connect();},2000);
        
    	
      });
        
        
        
    

    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }
       

    //连接
    function connect() {
        if (ws != null) {
            console.log("websocket连接已连接");
            return;
        }



        var hostport = document.location.host;

        var websocketServerUrl; //服务器地址

        var protocolStr = document.location.protocol;
        if (protocolStr.indexOf("https") >= 0) {
            websocketServerUrl = "wss://" + hostport + getContextPath() + "/websocket/${principal.user.id}";
        } else {
            websocketServerUrl = "ws://" + hostport + getContextPath() + "/websocket/${principal.user.id}";
        }

        url =websocketServerUrl;// "${websocketPath}${port}${ctx}/websocket/${principal.user.id}";
        if ('WebSocket' in window) {
            ws = new WebSocket(url);
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(url);
        } else {
            alert("您的浏览器不支持WebSocket");
            return;
        }
        ws.onopen = function () {
            //设置发信息送类型为：ArrayBuffer
            ws.binaryType = "arraybuffer";

            //发送一个字符串和一个二进制信息
            ws.send("thank you for accepting this WebSocket request");
            var a = new Uint8Array([8, 6, 7, 5, 3, 0, 9]);
            ws.send(a.buffer);
        }
        ws.onmessage = function (e) {
            console.log(e.data.toString());
            var list = JSON.parse(e.data);
            if (null != list && undefined != list) {
                if(list.length>0){

                	newmsg("收到一条新的系统消息!")
                }
                	refreshMsg(list);

            }
        }
        ws.onclose = function (e) {
            console.log("closed");
        }
        ws.onerror = function (e) {
            console.log("error");
        }
    }

    //断开连接
    function disconnect() {
        if (ws != null) {
            ws.close();
            ws = null;
            setConnected("已断开");
        }
    }
</script>
</body>
</html>