<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>接警手机信息管理</title>


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
					首页&nbsp;><span>&nbsp;接警手机信息列表</span>
				</h1>
			</div>
		</div>


		<div class="modal-body">
			<div class="row">
				<div class="col-sm-12 nopadding">

					<div class="col-xs-2 nopadding">
						<div class="queryclassleft ">


							<div class="leftTree ztree" id="Areatree" style="margin-top: 0;"></div>

						</div>
					</div>


					<div class="col-xs-10 nopadding">

						<div class="queryclass ">

							<div class='querytitle ' data-tippy-content="展开查询条件">
								<h5>
									查询条件 <i class="querytitlebtn fa fa-chevron-up"></i>
								</h5>

								<hr>
							</div>




							<form class="form-inline">

								<div class="form-group">
									<label for="name" class="lb_text col-xs-5 control-label">接警手机号</label>

									<div class="col-xs-7">
										<input id="q_phone" type="text" name="q_phone"
											class="form-control inputtxt" placeholder=""
											aria-controls="dataTables-example">
									</div>
								</div>


								<div class="form-group">
									<label for="name" class="lb_text col-xs-5 control-label">手机名称:</label>

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




						<script>
							$(function() {
								tippy(".querytitle", {
									arrow : true,
									content : $(".querytitle").attr(
											'data-tippy-content'),
									placement : 'top-start',
									arrowType : 'round', // or 'sharp' (default)
									animation : 'perspective',
								});

								$(".querytitle")
										.click(
												function() {

													if ($(".queryclass")
															.hasClass("collaps")) {
														$(".queryclass")
																.removeClass(
																		"collaps");
														$(".querytitle")
																.attr(
																		"data-tippy-content",
																		"收起查询条件");
													} else {
														$(".queryclass")
																.addClass(
																		"collaps");
														$(".querytitle")
																.attr(
																		"data-tippy-content",
																		"展开查询条件");
													}

													$(".querytitle")[0]._tippy
															.setContent($(
																	".querytitle")
																	.attr(
																			"data-tippy-content"));

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
					</div>
				</div>
			</div>

		</div>

	</div>


	<!-- 模态框（Modal） -->
	<#include "form.ftl">


	<script src="${ctx}/vendor/pageAuto/tphoneinfo/js/tphoneinfo.js"></script>

	<script src="${ctx}/js/ztree/jquery.ztree.all.min.js"></script>
	<script src="${ctx}/vendor/pageAuto/common/common.js"></script>
	<script src="${ctx}/vendor/pageAuto/common/leftAreaTree.js"></script>
</body>
</html>