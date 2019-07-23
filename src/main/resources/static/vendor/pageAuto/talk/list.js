/**
 * 
 */

$(function() {

	var rid = GetQueryString("id");

	initValidate();
	InitQuery_item(rid);

	initAlarmTypeSelect("case_type", "1");
	initAlarmTypeSelect("case_level", "2");
	initAlarmTypeSelect("commonyuju", "3");
	
	
	// Bind an event
	$('#commonyuju').on('select2:selecting', function(e) {
		// alert("11");
		$("#txtmsginput").val( e.params.args.data.text);
	
		   
		  e.preventDefault();
			$('#commonyuju').select2('close');
		return false;
	});
	
	setTimeout(function (){
		$("html").css("overflow","visible");
	}, 1000);
	

	$("#btnAccept").click(function() {

		$("#mform_item").data('bootstrapValidator').resetForm();

		// var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		if (flag) {

			var data = $("#mform_item").serialize();
			data+="&status=2";//已受理

			$.ajax({
				type : "post",
				url : getRPath() + '/talk/saveOrUpdateAlarm',
				data : data,
				async : false,
				dataType : "json",
				success : function(response) {

					$("#myModal_item").modal('hide');
					doSearch_item();

				}
			});
		}

	})
	
	initvd();

})

function initvd(){
	
	 myplayer= videojs('myplayer', {
			
		  sources: [{
		   // src: row.video_url,
		    type: 'video/mp4'
		  }]
		});
}

function initValidate() {
	$("#mform_item").bootstrapValidator({
		feedbackIcons : {
			/* input状态样式通过，刷新，非法三种图片 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		// submitButtons : 'button[type="submit"]',// 提交按钮
		fields : {
			case_type : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
				}
			},
			case_level : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
				}
			},
		}

	});
}

function doSearch_item() {

	var opt = {
		silent : true
	};
	$("#table_list_item").bootstrapTable('refresh', opt);

	// success("test");
}

function InitQuery_item(rid) {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath() + '/talk/videoalarminfoList', // 请求后台的URL（*）
		method : 'post', // 请求方式（*）
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toolbar', // 工具按钮用哪个容器
		showHeader : true,
		searchAlign : 'left',
		buttonsAlign : 'left',

		searchOnEnterKey : true,
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : true, // 是否显示分页（*）
		sortable : false, // 是否启用排序
		sortName : 'id', // 排序字段
		sortOrder : "desc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 25 ], // 可供选择的每页的行数（*）
		search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大

		// showColumns: true, //是否显示所有的列
		uniqueId : "id", // 每一行的唯一标识，一般为主键列
		// queryParamsType : "limit",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				pageSize : params.limit, // 每页要显示的数据条数
				offset : params.offset, // 每页显示数据的开始行号
				sortName : params.sort, // 要排序的字段
				sortOrder : params.order, // 排序规则

				type : $("#q_type").val(),
				onlineseats_id : rid,

			};
			return param;
		},
		onPostBody : function() {
			
				setTimeout(function() {
					var data=$("#table_list_item").bootstrapTable('getData');
					if(data&&data.length>0)
					changeAlarm(data[0]);	
				}, 500);
				
				
		},
		  rowStyle: function (row, index) {
			  if(typeof(row.hasNewInfo)!='undefined'&&row.hasNewInfo=="1")
				{
				  var cls="newinforow"
				  var style={classes:cls};
                  
				    return style;
				}
			  else{
					if (row.status == "1")
						{
						if(row.type=="2")//视频报警，全部为已受理
							{
							  return "{classes:''}";
						}
						else
							{
							  return {classes:'toaccept'};
							}
						}
					else
						{
						  return "{classes:''}";
						}
				  
			  }
			  return "{classes:''}";
	        	//return rowStyleFormat(row,index);
	            
	        }    ,
		columns : [ {
			field : 'id',
			visible : false
		},

		{
			field : 'occurrence_time',
			title : '报警时间',
			align : 'center',
			valign : 'middle',

			formatter : function(value, row, index) {

				var flag="";
				if(typeof(row.hasNewInfo)!='undefined'&&row.hasNewInfo=="1")
					{
					flag="<span class='ninfo  ' title='新消息'><i class='fa fa-commenting-o'></i></span>";
					}
				
				var tm = value.substr(0, value.length - 2);
				// `status` varchar(2) DEFAULT NULL COMMENT '警情状态
				// 1已报警、2已受理、3已出警、4已关闭',
				var status = "";
				if (row.status == "1")
					{
					status = "已报警";
					if(row.type=="2")//视频报警，全部为已受理
						status = "已受理";
					}
					
				else if (row.status == "2")
					status = "已受理";
				else if (row.status == "3")
					status = "已出警";
				else if (row.status == "4")
					status = "已关闭";
				
				
				
				return flag+tm + "(" + status + ")";
			}

		}, {
			field : 'type',
			title : '报警类型',
			align : 'center',
			valign : 'middle',
			formatter : function(value, row, index) {

				var html = "图文报警";
				if (value == "1")
					html = "图文报警";
				else if (value == "2")
					html = "视频报警";

				return html;
			}

		},

		{
			field : 'userName',
			title : '报警人',
			align : 'center',
			valign : 'middle',

		},

		{
			field : 'address',
			title : '地理位置',
			align : 'center',
			valign : 'middle',

		},

		{
			title : '操作',
			field : 'vehicleno',
			align : 'center',
			formatter : modifyAndDeleteButton_item,
			events : PersonnelInformationEvents_item
		}

		]
	}).on("click-row.bs.table", function(event, row, rowele, field) {
		// var subTable = $(rowele).html('<table
		// class="nohide"></table>').find('table');

		changeAlarm(row);
	});
	;
}

/**
 * 提示其他报警有新的聊天消息
 * @param alarmId
 * @returns
 */
function refreshOtherAlarmNew(alarmId)
{
	
	
	$("#table_list_item").find("tr[data-uniqueid='"+alarmId+"'] td:nth-child(1) ").prepend("<span class='ninfo ' title='新消息'><i class='fa fa-commenting-o'></i></span>");
	$("#table_list_item").find("tr[data-uniqueid='"+alarmId+"']  ").addClass("newinforow");
	
	/*var data=$("#table_list_item").bootstrapTable('getData');
	if(data!=null)
		{
		for (var i = 0; i < data.length; i++) {
			if(data[i].id==alarmId)
				{
				data[i].hasNewInfo=true;
				
				$("#table_list_item").bootstrapTable('updateRow', {
					    index: i,
					    row: data[i]
					    });
				
				break;
				}
		}
		
		}*/
}


// 当前聊天报警id
var curAlarmId = null;

function changeAlarm(row) {

	curAlarmId = row.id;

	var html = getVideoInfo(row);
	$("#adetail").html(html);

	//refreshVd(row);
	loadTalk(row);
}

var myplayer=null;
function refreshVd(row){
	
	if(myplayer==null)
	 myplayer= videojs('myplayer', {
		
		  sources: [{
		   // src: row.video_url,
		    type: 'video/mp4'
		  }]
		});

	//$("#adetail").find("#vddiv").append($("#vdcontainer"));
	 
	if(typeof(row.video_url)!='undefined'){
		
		playVd(row.video_url);
		
	}
	
	
	//myplayer.play();
	//$("#adetail").find('#myplayer').
}
function showDetailImgModal(url) {
    $("#myModal_itemDetailPic").find(".detailImg").attr("src",url);
    
    $("#myModal_itemDetailPic").modal("show")
}
function playVd(url)
{
	if(typeof(url)!="undefined")
	{
		$("#myModal_itemvd").modal();
		
	
		 myplayer.src(url);
		 myplayer.load(url);
		
		
		myplayer.play();
		
	}	
}
function playAudio(url)
{
	if(typeof(url)!="undefined")
	{
		$("#myModal_itemad").modal();
		
		
		//$("#myModal_itemad").find(".audioplayer-time").remove();
		//$("#myModal_itemad").find(".audioplayer-stopped").remove();
		
		
		 $('#myvideoplayer').html("");
		 $('#myvideoplayer').attr('src',url);
		             var fry_audio=$('#myvideoplayer').get('0');
		            fry_audio.load();
	
		 $( '#myvideoplayer' ).play();//audioPlayer();
		
	}	
}



/**
 * 加载报警事件中的最近 聊天记录
 * 
 * @param row
 * @returns
 */
function loadTalk(row) {
	
	$("#txtmsglist").html("");
	
	
	
	
	
 //清除新消息标记
	
	
	$("#table_list_item").find("tr[data-uniqueid='"+row.id+"'] ").find(".ninfo").remove();
	
	

	
	$.ajax({
		type : "post",
		url : getRPath() + '/talk/tvideoalarmtalkinfoList',
		data : {
			alarmId : row.id
		},
		async : false,
		dataType : "json",
		success : function(response) {

		
			
			if(response&&response.rows.length>0)
				{
				
				if(response.rows.length<response.total)
					{
					//显示查看历史
					insertMore(response.rows[response.rows.length-1]);
					}
				
				//倒叙，事件小-》大
				for (var i = response.rows.length-1; i >=0; i--) {
					appendToList(response.rows[i].msgContent, (response.rows[i].talkType=="1")?false:true,response.rows[i].ctime.substr(0,response.rows[i].ctime.length-4));
				}
				
				}

		}
	});
}
function showMore(mintime)
{
	//$("#txtmsglist").html("");
	
	

	
	$("#txtmsglist").find(".showmoretalk").remove();
	
	$.ajax({
		type : "post",
		url : getRPath() + '/talk/tvideoalarmtalkinfoList',
		data : {
			alarmId : curAlarmId,
			maxtime:mintime,
		},
		async : false,
		dataType : "json",
		success : function(response) {

		
			
			if(response&&response.rows.length>0)
				{
				
				
				
				//正序，大-》小，往前插
				for (var i =0; i < response.rows.length; i++) {
					appendToList(response.rows[i].msgContent, (response.rows[i].talkType=="1")?false:true,response.rows[i].ctime.substr(0,response.rows[i].ctime.length-4),true);
				}
				
				//var exsitNum=$("#txtmsglist").find(".talktime").length;
				
				if(response.rows.length<response.total)
				{
				//显示查看历史
				insertMore(response.rows[response.rows.length-1]);
				}
				
				}

		}
	});
}

/**
 * 显示更多，查看历史
 * @returns
 */
function insertMore(mindata){
	
var existData= $("#txtmsglist").html();
	


	existData="<div class='showmoretalk'><a href='javascript:void(0);' onclick=\"showMore('"+mindata.ctime+"')\">点击加载更多记录</a></div>"+existData;
	
 $("#txtmsglist").html(existData);
}

function modifyAndDeleteButton_item(value, row, index) {
	if (row.status == "1")
		if(row.type=="2")//视频报警，全部为已受理
			{
			return "";
		}
		else
		return [ '<div class="">'
				+ '<button id = "accept" type = "button" class = "btn btn-info"><i class="fa fa-check-o">受理</i> </button>&nbsp;'

				+ '</div>' ].join("");
	else
		return "";
}

window.PersonnelInformationEvents_item = {
	"click #accept" : function(e, value, row, index) {

		$.ajax({
			type : "post",
			url : getRPath() + '/talk/loadAlarm',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {

				$("#mform_item").fill(response);

				$("#myModal_item").modal();

			}
		});

	},

}

function getVideoInfo(rowdata) {
	var html = "";

	var tm = rowdata.occurrence_time.substr(0,
			rowdata.occurrence_time.length - 2);

	html += '<div class="row orow">'
			+ ' <div class=""> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">报警时间:</label> '
			+ '	<div class="col-xs-9 nopadding"> ' + '		<span >'
			+ tm
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">报警人:</label> '
			+ '	<div class="col-xs-9 nopadding"> '
			+ '		<span >'
			+ rowdata.userName
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">报警身份证号码:</label> '
			+ '	<div class="col-xs-9 nopadding"> '
			+ '		<span >'
			+ rowdata.idNumber
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">联系电话:</label> '
			+ '	<div class="col-xs-9 nopadding"> '
			+ '		<span >'
			+ rowdata.phone
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">报警地点:</label> '
			+ '	<div class="col-xs-9 nopadding"> '
			+ '		<span >'
			+ rowdata.address
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">警情描述:</label> '
			+ '	<div class="col-xs-9 nopadding"> '
			+ '		<span >'
			+ rowdata.description
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">图片附件:</label> '
			+ '	<div class="col-xs-12 nopadding "> '
			+ '		<span >'
			+ initResource(rowdata.picture_url,'img')
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '
			
			
			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">语音附件:</label> '
			+ '	<div class="col-xs-12 nopadding "> '
			+ '		<span >'
			+ initResource(rowdata.audio_url,'voice')
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '


		
			+ '	<label class="col-xs-3 nopadding" style="font-weight: bold;">视频附件:</label> '
			+ '	<div class="col-xs-12 nopadding "> '
			+ '		<span >'
			+ initResource(rowdata.video_url,"vd")
			+ '</span> '
			+ '		<p class="help-block"></p> '
			+ '	</div> '

			+ ' </div> ' + '</div> ';

	return html;
}

function initResource(urls,type)
{
	 var htmlStr = '';
	     if (typeof(urls)!='undefined') {
    	 
    	  var imgIdArr = urls.split(",");

          for (var imgId in imgIdArr) {

              if (imgIdArr[imgId] == "") {

                  return htmlStr;
              }
              
    	 
    	 if(type=='img')
    		 {
    		 
    
            // var url=getRootPath() + "/upload/file/" + imgIdArr[imgId];
    		 var url=imgIdArr[imgId]
             htmlStr += "<img onclick='showDetailImgModal(\"" + url + "\")' class='img-responsive vdimg' src='" + url + "'/>"
                      
    		 }

    	 else if (type=='voice')
    		 {
    		//  var url=getRootPath() + "/upload/file/" + imgIdArr[imgId];
    		 var url=imgIdArr[imgId]
    		  var vdtpurl=getRootPath() + "/img/an/vo.jpg";
              htmlStr += "<img onclick='playAudio(\"" + url + "\")' class='img-responsive vdimg' src='" + vdtpurl + "'/>"
                  
    		 }
    	 else if (type=='vd')
		 	{
    		 // var url=getRootPath() + "/upload/file/" + imgIdArr[imgId];
    		 var url=imgIdArr[imgId]
    		  var vdtpurl=getRootPath() + "/img/an/play.png";
              htmlStr += "<img onclick='playVd(\"" + url + "\")' class=' img-responsive vdimg' src='" + vdtpurl + "'/>"
                  
		 	}
          }
     }

     return htmlStr;
}
