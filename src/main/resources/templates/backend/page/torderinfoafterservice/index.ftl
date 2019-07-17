<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>订单售后表管理</title>


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
					首页&nbsp;>&nbsp;售后管理&nbsp;><span>&nbsp;订单售后表列表</span>
				</h1>
			</div>
		</div>


	<div class="modal-body">
		<div class="row">
		
	
		
		<div class="queryclass4">
			




				<form class="form-inline">
					 

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">订单id:</label>

						<div class="col-xs-7">
							<input id="q_orderId" type="text" name="q_orderId"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

 

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">用户密码:</label>

						<div class="col-xs-7">
							<input id="q_password" type="text" name="q_password"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

                    <div class="form-group">
                        <label for="startTime" class="lb_text col-xs-5 control-label">创建时间:</label>

                        <div class="col-xs-7">
                            <input id="startTime" type="text" name="startTime"
                                   class="form-control inputtxt" placeholder="开始时间"
                                   aria-controls="dataTables-example">
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="endTime" class="lb_text col-xs-5 control-label">&nbsp;&nbsp;&nbsp;~ </label>

                        <div class="col-xs-7">
                            <input id="endTime" type="text" name="endTime"
                                   class="form-control inputtxt" placeholder="结束时间"
                                   aria-controls="dataTables-example">
                        </div>

                    </div>





				</form>


            <form class=" form-inline margin-top-10">
                <div class="form-group">
                    <label for="name" class="lb_text col-xs-5 control-label">联系电话:</label>

                    <div class="col-xs-7">
                        <input id="q_telephone" type="text" name="q_telephone"
                               class="form-control inputtxt" placeholder=""
                               aria-controls="dataTables-example">
                    </div>
                </div>



                <div class="form-group">
                    <label for="name" class="lb_text col-xs-5 control-label">联系人称呼:</label>

                    <div class="col-xs-7">
                        <input id="q_nickname" type="text" name="q_nickname"
                               class="form-control inputtxt" placeholder=""
                               aria-controls="dataTables-example">
                    </div>
                </div>



                <div class="form-group">
                    <label for="name" class="lb_text col-xs-5 control-label">售后状态：</label>

                    <div class="col-xs-7">
                        <select class="form-control" id="q_serviceState" name="q_serviceState">
                            <option value="">请选择</option>
                            <option value="0">已提交</option>
                            <option value="1">客服已确认</option>
                            <option value="2">已安排师傅处理</option>
                            <option value="3">处理完成</option>
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


		<div class="mainbody">
			<div class="row">
				<div class="col-xs-5" style="margin-top: 16px;">订单售后表列表</div>
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
						<span class="header">订单售后表列表</span>
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


		<script src="${ctx}/vendor/pageAuto/torderinfoafterservice/js/torderinfoafterservice.js"></script>
		<script src="${ctx}/vendor/pageAuto/torderinfoafterservice/js/validator.js"></script>
        <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
</body>
</html>