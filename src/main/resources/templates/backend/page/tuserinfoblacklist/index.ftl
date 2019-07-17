<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>拉黑锁匠管理</title>


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
					首页&nbsp;>&nbsp;锁匠管理&nbsp;><span>&nbsp;拉黑锁匠管理</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass collaps">
		
			



			
	<#include "../user_common/lockquery.ftl">


				

				


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


        <script src="${ctx}/vendor/util/js/util.js"></script>

  <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>

     <script src="${ctx}/js/ztree/jquery.ztree.all.min.js"></script>
    <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/tuserArea.js"></script>
		<script src="${ctx}/vendor/pageAuto/tuserinfoblacklist/js/tuserinfo.js"></script>
        
            <script src="${ctx}/vendor/pageAuto/tuserinfo/js/common/userinfo_common.js"></script>
        
        <script>
       

          function modifyAndDeleteButton_item(value, row, index) {
       	var html='<div class="">'
              html+= '<button id = "reset" type = "button"data-tippy-content="恢复" class = "tippy btn btn-success"><i class="fa fa-refresh"></i> </button>&nbsp;';


      		 html+= '<button id = "realdelete" type = "button" data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>';
              
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