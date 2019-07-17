<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>月结账工单数统计</title>


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
					首页&nbsp;>&nbsp;统计管理&nbsp;><span>&nbsp;月结账工单数统计</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass4">


            <form class=" form-inline">

                <label for="datepicker" class="col-lg-2 control-label">月份：</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="q_createTime">
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
				<div class="col-xs-4" style="margin-top: 16px;">月工单数统计</div>

                <div class="col-xs-2 col-xs-push-6" style="padding-top: 10px;">
                	<button type="button" class="btn btn-default" id="btnImportOut">导出EXCEL</button>
				</div>
			</div>



            <div class="row">

				<input type="hidden" value="" id="change">
                <div class="col-sm-12">

                    <div class="table-responsive tbhidepage" style="margin: 10px;">

                        <table id="table_list_item"
                               class="table table-bordered table-hover table-striped "></table>
                    </div>
                </div>

			</div>

		</div>



		<div class="hide row">




			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="header">锁企财务年度统计</span>
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


        <script src="${ctx}/vendor/echarts/js/echarts.js"></script>

		<script src="${ctx}/vendor/pageAuto/enterpriseStatistics/js/enterpriseStatistics.js"></script>
</body>
</html>