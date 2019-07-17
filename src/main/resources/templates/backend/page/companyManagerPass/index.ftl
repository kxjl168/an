<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>合伙人下已审核锁匠</title>


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
					锁匠管理&nbsp;><span>&nbsp;合伙人下已审核锁匠</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass">
		
			



				<#include "../user_common/lockparterquery.ftl">


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
						<span class="header"></span>
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
	<#include "form.ftl">

     
                   <script src="${ctx}/vendor/util/js/util.js"></script>

  <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
        
        
         <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/tuserArea.js"></script>

        
        
        
          <script src="${ctx}/js/ztree/jquery.ztree.all.min.js"></script>
        
		<script src="${ctx}/vendor/pageAuto/tcompanyManager/js/tpasseduserinfo.js"></script>
		
		  <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/userinfo_common.js"></script>
        
        <script>
       

          function modifyAndDeleteButton_item(value, row, index) {
       	var html='<div class="">'
            + '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
           + '<button id = "untying" type = "button" class = "btn btn-info"><i class="fa fa-chat">解绑微信</i> </button>&nbsp;'

       	+'<@shiro.hasPermission name="menu_companyuser_pass:ownlocker" >'   
           + '<button id = "ownlocker" type = "button" class = "btn btn-warning"><i class="fa fa-chat">变为自由锁匠</i> </button>&nbsp;'
       	+'</@shiro.hasPermission>';
           
            
        	if(row.dataState==1)
        	html+= '<button id = "delete" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-trash">废弃</i> </button>&nbsp;';
        	
        	if(row.dataState==0)
        	html+= '<button id = "reset" type = "button" class = "btn btn-success"><i class="fa fa-refresh">恢复</i> </button>&nbsp;';
            
        
      		html+= 
        			'<@shiro.hasPermission name="menu_companyuser_pass:delete" >'   
        			+'<button id = "realdelete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        			'</@shiro.hasPermission>';
        			
        			html+= '</div>';
        	
        	
            return [html].join("");
        }
                    

            </script>
        
      
</body>
</html>