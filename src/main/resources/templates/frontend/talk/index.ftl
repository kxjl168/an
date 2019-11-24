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
<link rel="stylesheet" href="${ctx}/vendor/videojs/videojs.min.css">

<link rel="stylesheet" href="${ctx}/vendor/videojs/audio.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/zfileUpload/FileUploadMuti.css">
<style type="text/css">
html {
	overflow: auto;
}
body{
background: white;
}
</style>
</head>

<body style="overflow-x:hide" >



	<div class="col-xs-12 nopadding">

		<div class="col-sm-4 col-xs-12 nopadding ">

			<div class="leftalarmlist col-xs-12 row queryclassalarm">

				<div class='querytitle '>
					<h5><i class="fa fa-newspaper-o"></i>实时报警</h5>

					<hr>
				</div>
				<div class="qcontent noscroll" >
				<div id="dataTables-example_wrapper"
					class="dataTables_wrapper form-inline dt-bootstrap no-footer">
					<div class="row ">
						<div class="table-responsive" style="margin: 1px;">
						<div class="tbhead">
								<table class="table  table-hover table-striped" style="margin-bottom:0px;">
								<thead><tr><th style="text-align: center; vertical-align: middle; " data-field="occurrence_time"><div class="th-inner ">报警时间</div><div class="fht-cell"></div></th><th style="text-align: center; vertical-align: middle; " data-field="type"><div class="th-inner ">报警类型</div><div class="fht-cell"></div></th><th style="text-align: center; vertical-align: middle; " data-field="userName"><div class="th-inner ">报警人</div><div class="fht-cell"></div></th><th style="text-align: center; vertical-align: middle; " data-field="address"><div class="th-inner ">地理位置</div><div class="fht-cell"></div></th><th style="text-align: center; " data-field="vehicleno"><div class="th-inner ">操作</div><div class="fht-cell"></div></th></tr></thead>
								</table>
								</div>
							<table id="table_list_item" class="table   table-hover "></table>
						</div>
					</div>


				</div>
				</div>
			</div>

			<div class="leftalarmlist col-xs-12 row queryclassalarm margin-top-5">
				<div class='querytitle '>
					<h5><i class="fa fa-info-circle"></i>事件详情</h5>

					<hr>
				</div>
				
								<div class="qcontent">
								
				<div id="adetail"></div>
</div>


				<!-- <div class="row orow">
					<div class="">
						<label class="col-lg-3 nopadding" style="font-weight: bold;">视频附件:</label>
						<div class="col-lg-9 nopadding ">
							<span id="vddiv"> <video id="myplayer"
									class="video-js vjs-default-skin" style="width: 100%" controls
									preload="none">
								</video>

							</span>
						</div>
					</div>

				</div> -->

			</div>
		</div>


		<div class="col-sm-3 col-xs-12 nopadding ">
			<div class="queryclassalarm queryrightdv col-xs-12 row ">
				<div class='querytitle '>
					<h5><i class="fa fa-comments-o"></i>沟通记录</h5>

					<hr>
				</div>
<div id="txtmsglist" class=" txtmsglist col-xs-12 form-control"
					readonly="readonly" rows="15" cols=""></div>
				<textarea id="txtmsginput" name="txtmsginput"
					class="col-xs-12 form-control margin-top-10" rows="5" cols=""></textarea>

				<div class="col-xs-12 nopadding margin-top-10">

					<div class="col-xs-6 nopadding">
						<select id="commonyuju" class="col-xs-12 form-control pull-right">
							<option value=''>选择常用语</option>

						</select>
					</div>
					<div class="col-xs-6">
					
					
						<button id="btnmsg" type="button"
							class="pull-right btn btn-success">
							<i class="fa fa-send">发送</i>
						</button>
					
						<button id="btnuploadimg" type="button"
							class="pull-right btn btn-default">
							<i class="fa fa-upload">选择图片</i>
						</button>	&nbsp;
					</div>


				</div>
				

			</div>

			
		</div>
		
		
		
		<div class="col-sm-5  col-xs-12 nopadding ">
			<div class="queryclassalarm queryrightdv col-xs-12 row ">
				<div class='querytitle '>
					<h5><i class="fa fa-user"></i>用户信息</h5>

					<hr>
				</div>

	<div class="qcontent qcontent2">
				<div id="adetail2"></div>
</div>
				<div class='querytitle margin-top-5'>
					<h5><i class="fa fa-map-marker"></i>定位信息</h5>

					<hr>
				</div>
				<div class="col-xs-12 qcontent3 nopadding margin-top-10">

				
							<iframe class="mapframe" src="${ctx}/talk/map" id="iframeCommunity"></iframe>
						
				</div>

			</div>

			
		</div>
		
		<#include "form.ftl"/>
			<#include "formPic.ftl"/>
			<#include "vd.ftl"/>
			<#include "video.ftl"/>
		


<div class="tpdata hide"></div>
<input type="hidden" id="httppath" value="${httppath }">
						<div id="upimgs" class="hide"></div>



		<script src="${ctx}/vendor/pageAuto/talk/talk.js"></script>
		<script src="${ctx}/vendor/pageAuto/talk/list.js"></script>
		<script src="${ctx}/vendor/pageAuto/talk/alarmTypeSelect2.js"></script>

		<script src="${ctx}/vendor/videojs/videojs.min.js"></script>
		<script src="${ctx}/vendor/videojs/audioplayer.js"></script>
		
			<script src="${ctx}/vendor/ckeditor4.8/ckeditor.js"></script>
		<script src="${ctx}/vendor/ckeditor4.8/adapters/jquery.js"></script>
		
		<script src="${ctx}/vendor/zfileUpload/FileUploadMuti.js"></script>
		
</body>
</html>