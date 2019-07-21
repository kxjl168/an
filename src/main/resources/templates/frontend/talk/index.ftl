<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>报警事件在线沟通</title>


<link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

<link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">

<style type="text/css">

html{
overflow: auto;
}
</style>
</head>

<body>



	<div class="col-xs-12 ">

		<div class="col-xs-6 nopadding">

			<div class="col-xs-12 row queryclass">

				<div class='querytitle '>
					<h5>事件</h5>

					<hr>
				</div>
				<div id="dataTables-example_wrapper"
					class="dataTables_wrapper form-inline dt-bootstrap no-footer">
					<div class="row ">
						<div class="table-responsive" style="margin: 10px;">
							<table id="table_list_item"
								class="table   table-hover "></table>
						</div>
					</div>


				</div>
			</div>

			<div class="col-xs-12 row queryclass margin-top-10">
				<div class='querytitle '>
					<h5>事件详情</h5>

					<hr>
				</div>


				<div id="adetail">

				
					

				</div>
			</div>
		</div>


		<div class="col-xs-6 nopadding">
			<div class="queryclass col-xs-12 row ">
				<div class='querytitle '>
					<h5>沟通记录</h5>

					<hr>
				</div>

				<div id="txtmsglist" class="col-xs-12 form-control" readonly="readonly" rows="15" cols=""></div>
				<textarea id="txtmsginput" class="col-xs-12 form-control margin-top-10" rows="5"
					cols=""></textarea>

				<div class="col-xs-12 nopadding margin-top-10">

					<div class="col-xs-6">
						<select id="commonyuju" class="col-xs-12 form-control pull-right">
							<option value=''>选择常用语</option>
							
						</select>
					</div>
					<div class="col-xs-6">
						<button id="btnmsg" type="button"
							class="pull-right btn btn-success">
							<i class="fa fa-send">发送</i>
						</button>
					</div>


				</div>

			</div>

<#include "form.ftl"/>
		</div>








		<script src="${ctx}/vendor/pageAuto/talk/talk.js"></script>
		<script src="${ctx}/vendor/pageAuto/talk/list.js"></script>
		<script src="${ctx}/vendor/pageAuto/talk/alarmTypeSelect2.js"></script>
</body>
</html>