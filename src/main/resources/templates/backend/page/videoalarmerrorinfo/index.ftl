<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>统计分析管理</title>


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
					首页&nbsp;><span>&nbsp;统计分析列表</span>
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
		
			


			<form class="form-inline margin-top-10">

						<div class="form-group large">
							<label for="name" class="lb_text col-xs-5 control-label">时间:</label>

							<div class="col-xs-7">
								<input type="text" name="startTime"
									class="form-control inputtxt" id="startTime" placeholder="开始时间"
									readonly>
							</div>
						</div>

						<div class="form-group large">
							<label for="name" class="lb_text col-xs-5 control-label">~</label>

							<div class="col-xs-7">
								<input type="text" name="endTime" class="form-control inputtxt"
									id="endTime" placeholder="结束时间" readonly>
							</div>
						</div>


					
					
				</form>
				

					
			



				<form class=" form-inline margin-top-10">
					


					<button type="button" id="btnQry" onclick="doSearch_item()"
						class="btn  button-primary button-rounded button-small">
						<i class="fa fa-search fa-lg"></i> <span>查询1</span>
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

<div class="mainbody-chart">
  <div class="row  nopadding ">
  
  
  <div class="col-xs-6 nopadding bd" >
    <div id="piechart"  class="mchart" style="height:300px;"></div>
  </div>
 
 <div class="col-xs-6  nopadding bd" >
    <div id="linechart"  class="mchart" style="height:300px;"></div>
</div>

                  
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



		
		
		</div>

		<!-- 模态框（Modal） -->
		<#include "form.ftl">

<script src="${ctx}/vendor/echarts/js/echarts.min.js"></script>

		<script
			src="${ctx}/vendor/pageAuto/videoalarmerrorinfo/js/videoalarmerrorinfo.js"></script>
				<script
			src="${ctx}/vendor/pageAuto/videoalarmerrorinfo/js/chart.js"></script>
			
			
			
			<script
			src="${ctx}/vendor/pageAuto/common/common.js"></script>
			
</body>
</html>