<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>客户评价问卷管理</title>


<link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

<link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">

<style>
    .detail-view {
        background: none !important;
    }
</style>
</head>

<body>





	<div>

		<div class="row">

			<div class="col-lg-12 wzbj">
				<div style="padding-top: 9px; float: left; padding-right: 4px;">
					<embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
				</div>
				<h1 class="page-header">
					首页&nbsp;>&nbsp;客户问卷管理&nbsp;><span>&nbsp;客户评价问卷管理</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass">
		
			



				<form class="form-inline">


					<div class="form-group" >
						<label for="name" class="lb_text col-xs-3 control-label">问卷标题:</label>

						<div class="col-xs-7">
							<input id="title" type="text" name="title"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

                    <div class="form-group" >
                        <label for="name" class="lb_text col-xs-3 control-label">问卷状态:</label>

                        <div class="col-xs-7">
                            <select id="curUse" name="curUse" class="form-control">
								<option value="">请选择</option>
								<option value="0">禁用</option>
								<option value="1">启用</option>
							</select>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label for="startTime" class="lb_text col-xs-3 control-label">起始时间:</label>

                        <div class="col-xs-7">
                            <input id="startTime" type="text" name="startTime"
                                   class="form-control inputtxt" placeholder=""
                                   aria-controls="dataTables-example">
                        </div>
                    </div>

                    <div class="form-group" >
                        <label for="endTime" class="lb_text col-xs-3 control-label">终止时间:</label>

                        <div class="col-xs-7">
                            <input id="endTime" type="text" name="endTime"
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
				<div class="col-xs-5" style="margin-top: 16px;">客户评价问卷列表</div>
			</div>
            <div class="col-xs-1 col-xs-push-6" style="padding-top: 10px;">
                <button type="button" class="btn btn-default" id="btnAdd_item">新增问卷</button>
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
						<span class="header">客户评价问卷列表</span>
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
	<#include "editAssessForm.ftl">
	<#include "editOptionForm.ftl">

        <script src="${ctx}/vendor/util/js/util.js"></script>
		<script src="${ctx}/vendor/pageAuto/tcustormAssess/js/tcustormassessTable.js"></script>
		<script src="${ctx}/vendor/pageAuto/tcustormAssess/js/tcustormassessQ.js"></script>
        <script src="${ctx}/vendor/pageAuto/tcustormAssess/js/validator.js"></script>
        <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>


</body>
</html>