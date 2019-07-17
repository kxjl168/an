<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>锁匠类型变更审核管理</title>

<!-- 锁匠类型变更状态审核 -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

<link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/zfileUpload/FileUploadMuti.css">
</head>

<body>





	<div>

		<div class="row">

			<div class="col-lg-12 wzbj">
				<div style="padding-top: 9px; float: left; padding-right: 4px;">
					<embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
				</div>
				<h1 class="page-header">
					首页&nbsp;>&nbsp;锁匠管理&nbsp;><span>&nbsp;锁匠类型变更审核列表</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
<div class="queryclass collaps">

  <div class='querytitle ' data-tippy-content="展开查询条件" >
                                <h5>查询条件 <i    class="querytitlebtn fa fa-chevron-down"></i></h5>
                               
                                <hr>
                            </div>
		
			


				<form class="form-inline">

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠名称:</label>

						<div class="col-xs-7">
							<input id="q_name" type="text" name="q_name"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠电话:</label>

						<div class="col-xs-7">
							<input id="q_phone" type="text" name="q_phone"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">审核状态:</label>

						<div class="col-xs-7">
						
									 <select name="q_auditStates" id="q_auditStates" class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0" selected="selected">待审核</option>
                    <option value="1">审核通过</option>
                    <option value="2">审核未通过</option>
                      
                   
                </select>
								
						</div>
					</div>



					
					
				</form>
				

					
			



				<form class=" form-inline margin-top-10">
					


					<button type="button" id="btnQry" onclick="doSearch_item()"
						class="btn  button-primary button-rounded button-small">
						<i class="fa fa-search fa-lg"></i> <span>查询</span>
					</button>

				</form>

			</div>
		</div>
		
		
<script>
$(function(){
	tippy(".querytitle",{
		 arrow: true,
		 content: $(".querytitle").attr('data-tippy-content'),
			placement:'top-start',
		  arrowType: 'round', // or 'sharp' (default)
		  animation: 'perspective',
});

   $(".querytitle").click(function(){

	  if( $(".queryclass").hasClass("collaps"))
		  {
		  $(".queryclass").removeClass("collaps");
		  $(".querytitle").attr("data-tippy-content","收起查询条件");
		  }
	  else{
		  $(".queryclass").addClass("collaps");
		  $(".querytitle").attr("data-tippy-content","展开查询条件");
		  }

	  $(".querytitle")[0]._tippy.setContent($(".querytitle").attr("data-tippy-content"));

		
	   
	   })
	   

	
})
    </script>


		<div class="mainbody">
			<div class="row">
				<div class="col-xs-5" style="margin-top: 16px;">锁匠类型变更审核列表</div>
				<div class="col-xs-1 col-xs-push-6" style="padding-top: 10px;">


					<button type="button" class="hide btn btn-default" id="btnAdd_item">新增</button>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">

					<div class="table-responsive" style="margin: 10px;">
						<table id="table_list_item"
							class="table table-bordered table-hover table-striped"></table>
					</div>
				</div>
			</div>

		</div>



		<div class="hide row">




			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="header">锁匠类型变更审核列表</span>
					</div>
					<div class="panel-body">

						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row ">
								<div class=" col-sm-9"></div>

								<div class="col-sm-3 "></div>
							</div>


						</div>
					</div>


				</div>
			</div>
		</div>
		
		</div>

		<!-- 模态框（Modal） -->
		<!-- <#include "form.ftl"> -->
				<#include "formPic.ftl">
<#include "formChangeTypeConfirm.ftl">

		<script
			src="${ctx}/vendor/pageAuto/tuserinfoaudit/js/tuserinfoaudit.js"></script>
			           <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/userinfo_common.js"></script>
			                <script src="${ctx}/vendor/zfileUpload/FileUploadMuti.js"></script>
			           
			           <script>


			           function modifyAndDeleteButton_item(value, row, index) {
			        		/* return [ '<div class="">'
			        				+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
			        				+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			        				+ '</div>' ].join(""); */

	        				
			        	  	var html='<div class="">'


			        	  	if(row.auditStates==0)
			        	  		html+='<button id = "auditType" type = "button" data-tippy-content="审核" class = "tippy btn btn-warning"><i class="fa fa-check"></i> </button>'
				        	  	
			        		html+= 
			        			'<@shiro.hasPermission name="menu_tuserinfo:delete" >'   
			        	//		+'<button id = "realdelete" type = "button" data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>'
			        			'</@shiro.hasPermission>';
			        			
			        			html+= '</div>';


			        			setTimeout(function(){
			        				tippy(".tippy",{
			        						 arrow: true,
			        						  arrowType: 'round', // or 'sharp' (default)
			        						  animation: 'perspective',
			        				}
			                				)
			        			},500);
			                	
			        	
			            return [html].join("");

	        				
			        	}





			           </script>
			           
			           
</body>
</html>