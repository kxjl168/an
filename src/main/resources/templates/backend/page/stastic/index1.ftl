<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>工单状态统计</title>


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
					首页&nbsp;><span>&nbsp;工单状态统计</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass">
		
			



				<form class="form-inline">

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">日期:</label>

						<div class="col-xs-7">
							<input id="q_month" type="text" name="q_month"
								class="form-control inputtxt datepicker" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
					
					<script>
					/*   $(function() {
						 $("#q_month").datetimepicker({
							 format: 'yyyy-mm',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#q_month").data('datetimepicker')
						 .setDate('2019-1-31 15:53:39');
                            }); */


                            $(function() {
        						
       						 $.fn.datepicker.defaults.format = "yyyy-mm";
       						 $('#q_month').datepicker({
       						   language: "zh-CN",
       						     endDate: new Date(),
								 format: "yyyy-mm",
                                 clearBtn: true,
                                 autoclose: true,
       						     minViewMode: "months",
       						     defaultDate:new Date(),
       						 });
       						
       						 
       						// num传入的数字，n需要的字符长度 ，批量添加房间数，房号计算，左加0
       						 function PrefixInteger(num, n) {
       						 	return (Array(n).join(0) + num).slice(-n);
       						 }
       						 
       						 var date=new Date();
       						 $('#q_month').val(date.getFullYear()+"-"+PrefixInteger((date.getMonth()+1),2));
       						
                            });
                            </script>


					<div class="form-group hide">
						<label for="name" class="lb_text col-xs-5 control-label">公司电话:</label>

						<div class="col-xs-7">
							<input id="q_companyPhone" type="text" name="q_companyPhone"
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


		<div class="mainbody">
			<div class="row">
				<div class="col-xs-5" style="margin-top: 16px;">工单统计</div>
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
						<span class="header">企业列表</span>
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
	<#include "form2.ftl">

		<script
			src="${ctx}/vendor/pageAuto/stastic/js/stastic.js"></script>
</body>
</html>