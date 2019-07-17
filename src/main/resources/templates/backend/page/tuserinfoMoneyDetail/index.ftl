<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>锁匠账户流水查询</title>


<link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

<link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">
</head>

<body>





	<div>

		<div class="row">

			<div class="col-lg-12 wzbj">
				<div style="padding-top: 9px; float: left; padding-right: 4px;">
					<embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
				</div>
				<h1 class="page-header">
					首页&nbsp;>&nbsp;财务管理&nbsp;><span>&nbsp;锁匠账户流水查询</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass collaps">
		
			

			
	<#include "../tuserinfoMoney/lockquery.ftl">

				<!-- <form class="form-inline">

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">手机号:</label>

						<div class="col-xs-7">
							<input id="q_phone" type="text" name="q_phone"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
			

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">姓名:</label>

						<div class="col-xs-7">
							<input id="q_name" type="text" name="q_name"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

				</form> -->


				<form class=" form-inline margin-top-10">
					


					<button type="button" id="btnQry" onclick="doSearch_item()"
						class="btn  button-primary button-rounded button-small">
						<i class="fa fa-search fa-lg"></i> <span>查询</span>
					</button>

				</form>

			</div>
		</div>


		<div class="mainbody">
			<div class="row">
				<div class="col-xs-5" style="margin-top: 16px;"></div>
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



		
		</div>

        <!-- 模态框（Modal） -->

        <script src="${ctx}/js/ztree/jquery.ztree.all.min.js"></script>
                <script src="${ctx}/vendor/util/js/util.js"></script>
                
  <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
     
		<script
			src="${ctx}/vendor/pageAuto/tuserinfoMoney/js/tuserinfoDetail.js"></script>
		          <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/userinfo_common.js"></script>
		          
		          
		          <script>
		          function modifyAndDeleteButton_item(value, row, index) {
		        		//return [ '<div class="">'
		        	//			+ '<button id = "more" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">账户流水明细</i> </button>&nbsp;'
		        		//		+ '</div>' ].join("");

		        		var html='<div class="">';

		        		html+= '<button id = "more" type = "button" data-tippy-content="账户流水" class = "tippy btn btn-info"><i class="fa fa-cny"></i> </button>&nbsp;';

		        		html+= '<button id = "more2" type = "button" data-tippy-content="提现列表" class = "tippy btn btn-info"><i class="fa fa-money"></i> </button>&nbsp;';

		        		


		        		 html+='</div>';
		        		/* html+=
		            		'<@shiro.hasPermission name="tuserinfo:hei" >'   
		                	 +'<button id = "black" type = "button" data-tippy-content="拉黑" class = "tippy btn btn-danger"> <i class="fa fa-ban  "></i></button>&nbsp;'
		                	  +'</@shiro.hasPermission>'; */

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