<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>接警单位管理</title>


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
					首页&nbsp;><span>&nbsp;接警单位列表</span>
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
						<label for="name" class="lb_text col-xs-5 control-label">单位名称:</label>

						<div class="col-xs-7">
							<input id="q_name" type="text" name="q_name"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
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
				<div class="col-xs-5" style="margin-top: 16px;"></div>
				<div class="col-xs-1 col-xs-push-6" style="padding-top: 10px;">


					<button type="button" class="btn btn-default" id="btnAdd_item">新增</button>
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
						<span class="header">单位信息列表</span>
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
<#include "unitAdmin.ftl">

		<script
			src="${ctx}/vendor/pageAuto/tunitinfo/js/tunitinfo.js"></script>
			
		
			
					<script
			src="${ctx}/vendor/pageAuto/tunitinfo/js/sysUserSelect2.js"></script>
			
					
					<script
			src="${ctx}/vendor/pageAuto/tunitinfo/js/unitSelect2.js"></script>
</body>
</html>