<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>视频接警大厅</title>


<link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

<link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">
<link rel="stylesheet" href="${ctx}/ifont/iconfont.css">
</head>

<body>





	<div class="indexbg">

		<div class="hide row">

			<div class="col-lg-12 wzbj">
				<div style="padding-top: 9px; float: left; padding-right: 4px;">
					<embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
				</div>
				<h1 class="page-header">
					首页&nbsp;><span>&nbsp;大厅</span>
				</h1>
			</div>
		</div>


		<div class="modal-body">

			<div class="col-sm-12 nopadding">

				<div class="col-xs-2 hide nopadding">
					<div class="queryclassleft ">


						<div class="leftTree ztree" id="Areatree" style="margin-top: 0;"></div>

					</div>
				</div>


				<div class="col-xs-12 nopadding">
					<div class="row">



						<div class="queryclass hide ">

							<div class='querytitle ' data-tippy-content="展开查询条件">
								<h5>
									查询条件 <i class="querytitlebtn fa fa-chevron-up"></i>
								</h5>

								<hr>
							</div>




							<form class="form-inline">

								<div class="form-group">
									<label for="name" class="lb_text col-xs-5 control-label">坐席名称:</label>

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

												if ($(".queryclass").hasClass(
														"collaps")) {
													$(".queryclass")
															.removeClass(
																	"collaps");
													$(".querytitle")
															.attr(
																	"data-tippy-content",
																	"收起查询条件");
												} else {
													$(".queryclass").addClass(
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

					<div class="">
						<div class="queryclass row fullwidth ">
						
						<div class="indexcenter">
						 <i class="icontop iconfont iconmethod-draw-image"></i>
						 <span>视频接警大厅</span>
						 
						 
						</div>
						
							<div class="indexright">
								<span class='rightrect hide'>状态说明:</span> <span class='rightrect'><i
									class='seat white'></i>空闲坐席</span> <span class='rightrect'><i
									class='seat green'></i>正在接警(繁忙)</span> <span class='rightrect'><i
									class='seat blue'></i>等待接警(空闲)</span> <span class='rightrect'><i
									class='seat red'></i>暂时离开</span>
									<span class='rightrect'><i
									class='seat leave'></i>人员离线</span>
							</div>
						</div>

					</div>
					<div class="mainbody-noborder fullwidth">


				<div class="row ff">
							<div class="col-sm-12">

								<div class="table-responsive" style="margin: 10px;">
									<table id="table_list_item"
										class="tableindex table table-bordered table-hover table-striped"></table>
								</div>
							</div>
						</div>
						

						<div class="row hide">

							<div class="dvcontainer">
								<div class="stdv color green">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">工号:001</div>
									<div class="st_name">姓名:张三</div>
								</div>

								<div class="stdv color blue">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

								<div class="stdv color red">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>
								
								<div class="stdv color white">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

								<div class="stdv">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

								<div class="stdv">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>
								<div class="stdv">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

								<div class="stdv">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

								<div class="stdv">
									<div class="st_title">A01</div>
									<div class="st_img">
										<img src='/an/img/blueSkin/head.png'>
									</div>
									<div class="st_idno">001</div>
									<div class="st_name">张三</div>
								</div>

							</div>


						</div>



					</div>



					

				</div>


			</div>





		</div>

		<!-- 模态框（Modal） -->
		<#include "form.ftl">


		<script src="${ctx}/vendor/pageAuto/tseatinfoAdmin/js/tseatinfo.js"></script>

		<script src="${ctx}/js/ztree/jquery.ztree.all.min.js"></script>
		<script src="${ctx}/vendor/pageAuto/common/common.js"></script>
		<script src="${ctx}/vendor/pageAuto/common/leftAreaTree.js"></script>
</body>
</html>