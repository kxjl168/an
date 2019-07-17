/**
 * 工单日志
 */


/**
 * 显示或者隐藏工单历史子表
 */
function showOrCloseDetail(tr, data, index) {

    if ($(tr.prevObject)[0].nodeName == "#document" || (  $($(tr.prevObject)[0].nodeName && ($(tr.prevObject)[0].nodeName == "TD")) )) {

        if ($(tr.prevObject)[0].nodeName == "#document") {

        }

//最后一个行有操作按钮的，不操作子行。	
        else if ($(tr.prevObject).find("button").length > 0) {

            return;
        }


        $(tr).parent().find(".detail-view").trigger('collapse-row');


        $(tr).find(".detail-icon i").click();
    }
}



$(function(){
	
	try {
		setInterval(function(){
			doSearch_item();
			//info("刷新成功");
		}, 60000);
		
	} catch (e) {
		// TODO: handle exception
	}
});


function orderStatusTypeName(value) {
    if (value == 3) {
        return "<span class='auditstyle' >待平台确认</span>"
    }
    else if (value == 1) {
        return "<span class='auditstyle'>加钱待平台审核</span>"
    } else if (value == 2) {
        return "<span class='auditstyle' >退单待平台审核</span>"
    } else if (value == 4) {
        return "<span class='auditstyle'>平台申请待锁企审核</span>"
    } else if (value == 5) {
        return "<span class='auditstyle'>锁企不通过待平台处理</span>"
    }
    else if (value == 6) {
    	return "<span class='auditstyle'>加钱待合伙人审核</span>"
    }
    else if (value >= 101 && value <= 199) {
        return "<span class='normalstyle' >待接单</span>"
    }
    else if (value >= 201 && value <= 299) {
    	
    	  if (value ==210) {
    	        return "<span  class='comfirmstyle' >待作业(已确认)</span>"
    	    }
    	  else  if (value ==213) {
  	        return "<span  class='comfirmstyle' >已预约</span>"
  	    }
    	  else if (value ==214) {
  	        return "<span  class='backstyle' >预约失败</span>"
  	    }
    	  else  if (value ==255) {
  	        return "<span  class='backstyle' >待作业(回访问题单)</span>"
  	    }
    	  else
        return "<span  class='normalstyle' >待作业</span>"
    }
    else if (value >= 301 && value <= 399) {
        return "<span  class='normalstyle' >待回访</span>"
    }
    else if (value >= 401 && value <= 499) {
        return "<span  class='normalstyle' >待结账</span>"
    }
    else if (value >= 501 && value <= 599) {
        return "<span  class='donestyle' >已完结</span>"
    }
}

function orderFeeTypeName(value){
	  if (value)
		  {
		  if(value=="0")
			  return "标准工单";
		  else if(value=="1")
			  return "自费工单";
		  }
	    else
	        return "";
}

function orderCustomerName(rowdata){
	var name=(rowdata.customerName == undefined) ? "" : rowdata.customerName;
	var type=  (rowdata.isRoomNessary=='0')?'个人客户':"企业客户";
	return name +"("+type+")";
}

function orderTypeName(value) {
	var type="";
    if (value .indexOf('19')>-1) {
        value=value.replace(19,9);
        type+= "其他,";//  "其他"
    }
    if (value .indexOf('0')>-1) {
    	type+= "安装,";
    }
    if (value .indexOf('1')>-1) {
    	type+= "维修,"; 
    }
    if (value .indexOf('2')>-1) {
    	type+= "开锁,";  //"开锁"
    }
    if (value .indexOf('3')>-1) {
    	type+= "特殊门改造,"; // "特殊门改造"
    }
    if (value .indexOf('4')>-1) {
    	type+= "测量,"; // "测量"
    }
    if (value .indexOf('5')>-1) {
    	type+= "猫眼安装,";//  "猫眼安装"
    }
    if (value .indexOf('6')>-1) {
        type+= "换锁,";//  "换锁"
    }
    if (value .indexOf('7')>-1) {
        type+= "工程安装,";//  "工程安装"
    }
    if (value .indexOf('8')>-1) {
        type+= "工程维修,";//  "工程维修"
    }

    type=type.substr(0,type.length-1);
    return type;
}

function AprroTIME(rowdata) {
    if (rowdata.appointmentTime)
        return rowdata.appointmentTime
    else
        return "暂未预约";
}

function reciveTIME(rowdata) {
    if (rowdata.receiveTime)
        return rowdata.receiveTime.substr(0, rowdata.receiveTime.length - 2) 
    else
        return "暂未分配";
}

function DoneTIME(rowdata) {
    if (rowdata.doneTime)
        return rowdata.doneTime.substr(0, rowdata.doneTime.length - 2)
    else
        return "暂未完工";
}

function RemarkData(rowdata){
	if(rowdata==undefined||rowdata.createRemark==undefined)
		return "";
	else {
		/*if(rowdata.createRemark.length>20)
			return "<span class='detailmark' title='"+rowdata.createRemark+"'>"+rowdata.createRemark.substr(0,20)+"...</span>"
		else*/
			return rowdata.createRemark;
		
	}
	
}

function RemarkData2(rowdata){
	if(rowdata==undefined||rowdata.createRemark==undefined)
		return "";
	else {
		if(rowdata.createRemark.length>10)
			return "<span class='detailmark' title='"+rowdata.createRemark+"'>"+rowdata.createRemark.substr(0,10)+"...</span>"
		else
			return rowdata.createRemark;
		
	}
	
}

function ImeiNumData(rowdata) {
    if (rowdata == undefined || rowdata.imeiNum == undefined)
        return "";
    else {
        return rowdata.imeiNum;
    }
}

function money(data) {
    if (data)
        return data
    else
        return "";
}

function orderuserType(value){
	 if (value == "0") {
	        return "个人用户"
	    }
	 else 	 if (value == "1")
		 {
		 return "企业用户"
		 }
}

$(
		
		function(){
	
//error(1);
	
 
})
function InstallRemarkData(rowdata){
    if(rowdata==undefined||rowdata.installRemark==undefined)
        return "";
    else {
        return rowdata.installRemark;

    }

}
function DoorInfoData(rowdata){
    if(rowdata==undefined||rowdata.doorInfo==undefined)
        return "";
    else {
        return rowdata.doorInfo;

    }

}




function initDetailPanelInfo(row, rowdata,deposit) {

    var orderNo = rowdata.orderNo;
    
    var utype=(rowdata.urgent==1)?"[<span class='tdanger'>加急</span>]":"";
    var utimetype=(rowdata.timeout==1)?"[<span class='twarning'>超时</span>]":"";

    var depositcls=" hide ";
    if(deposit)
    	 depositcls="  ";
    
    var html = '<p class="phead">工单[' + orderNo + '] 详情</p> <button type="button" class="btn btn-default"  onclick=closeDetailPanel("' + $(row).parent().prev().attr("data-index") + '")>关闭</button>'
        + ''
        + '	<div class="row">  '
        + '<div class="tabdetail modal-body nopadding margin-top-5">  '
        + '	<div class="row "> '

        + '		<ul class="nav nav-tabs" id="myTab" did="'+deposit+'" oid="'+orderNo+'"> '
        
     
        
        + '			<li class="active"><a href="#identifier' + orderNo + '1" data-toggle="tab">工单详情'+utype+utimetype+'</a></li> '
        + '			<li class=""><a href="#identifier' + orderNo + '2" data-toggle="tab">工单记录</a></li> '
        + '			<li class=""><a href="#identifier' + orderNo + '3" data-toggle="tab">产品信息</a></li> '
        
        + '			<li class="'+depositcls+'"><a href="#identifier' + orderNo + '4" data-toggle="tab">提现详情'+'</a></li> '
        + '			<li class="'+depositcls+'"><a href="#identifier' + orderNo + '5" data-toggle="tab">工单金额明细'+'</a></li> '

        + '	  </ul> '

        + '	<div class="tab-content"> '
        + '		<div class="row tab-pane fade in  active" id="identifier' + orderNo + '1"> '


        + '	<div class="col-lg-12 row orderinfopanel"> '


        + '<div class="row orow">'
        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">工单编号:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + orderNo + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">服务类型:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + orderTypeName(rowdata.serverType) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">工单状态:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + orderStatusTypeName(rowdata.orderState) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">工单类型:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + orderFeeTypeName(rowdata.orderFeeType) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        

        + ' </div> '
        + '</div> '

    var lockfeicss="";
    var lockerfeicss="";
    var managerType=$("#managerType2").val();// 模板中获取当前登陆用户类型
    if(managerType==2)
    {
        lockerfeicss=" hide ";
    }
    else{
        lockfeicss="  ";
    }


    html +=""


        + '<div class=" row orow">'

        + ' <div class="form-group"> '

        + ' <div class="'+""+'"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">锁企费用:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + money(rowdata.sellerTotalPrice) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	</div> '


        + ' <div class="'+lockerfeicss +'"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">锁匠费用:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + money(rowdata.lockerTotalPrice) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	</div> ';
        
        if(rowdata.orderComplateCode!=null)
        	{
        	var showbtncss=" hide ";
        	//if(rowdata.orderComplateCodeSend=='0')
        	//	showbtncss=" ";
        	
        	showbtncss=' hide ';
        	
       
        	if(window.location.href.indexOf("doing")>0)
        		showbtncss='';
        	
        	
    html += ' <div class="'+lockerfeicss +'"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">核销码:</label> '
        + '	<div class="col-lg-4 " > '
        + '		<b class="bold pull-left text-danger">' + rowdata.orderComplateCode + '</b> '
        + '		<div class="bold pull-left " style="height:20px"><button type="button" style="   margin-top: -10px;  margin-left: 5px;" class="'+ showbtncss+'btn btn-danger"  onclick=sendCompleteCode("' +((rowdata.customerPhone == undefined) ? "" : rowdata.customerPhone)+'","' +orderNo + '")>重发短信</button></div> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	</div> '
        	}
        


        html += ' </div> '
        + '</div> ';
    
    
    if(lockerfeicss=="")
	{
    	//锁企信息
    html +=""


        + '<div class=" row orow">'

        + ' <div class="form-group"> '

        + ' <div class="'+lockfeicss+'"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">发单锁企:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.enterpriseName + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	</div> '


        + ' <div class="'+lockerfeicss +'"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">锁企电话:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.enterprisePhone + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	</div> '


        + ' </div> '
        + '</div> ';
	}



    html +=""

        + '<div class="row orow">'
        + ' <div class="form-group"> '

        + '	<label class="col-lg-1" style="font-weight: bold;">创建时间:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.createTime.substr(0, rowdata.createTime.length - 2) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">预约时间:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + AprroTIME(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">分单时间:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + reciveTIME(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        
        + '	<label class="col-lg-1" style="font-weight: bold;">锁具数量:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.lockNum+ '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
     

        + ' </div> '
        + '</div> '
        
        
        
        
        
        + '<div class="row orow">'
        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">客户姓名:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + orderCustomerName(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">电话1:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.customerPhone == undefined) ? "" : rowdata.customerPhone) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">电话2:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.customerPhone2 == undefined) ? "" : rowdata.customerPhone2) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">地区:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.areaName == undefined) ? "" : rowdata.areaName) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        
    

        + ' </div> '
        + '</div> '
        
        
        
        
        
        
        
        

        + '<div class="row orow">'
        + ' <div class="form-group"> '

        
        
        
        + '	<label class="col-lg-1" style="font-weight: bold;">安装地址:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.addressDetail == undefined) ? "" : rowdata.addressDetail) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">邮寄地址:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.mailAddressDetail == undefined) ? "" : rowdata.mailAddressDetail) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        + '	<label class="col-lg-1" style="font-weight: bold;">备注:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + RemarkData(rowdata) + '</span>'
        + '		<p class="help-block"></p> '
        + '	</div> '
        
        
        + '	<label class="col-lg-1" style="font-weight: bold;">锁IMEI:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ImeiNumData(rowdata) + '</span>'
        + '		<p class="help-block"></p> '
        + '	</div> '
        

        + ' </div> '
        + '</div> ';

        
    if(lockerfeicss=="")
    	{
        
    	   html +=""
    		   +'<div class="row orow">'
        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">锁匠姓名:</label> '
        + '	<div class="col-lg-2"> '

        + '		<span >' + ((rowdata.lockName == undefined) ? "" : rowdata.lockName) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">锁匠电话:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + ((rowdata.lockerPhone == undefined) ? "" : rowdata.lockerPhone) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '

        + ' </div> '
        + '</div> ';
    	}


    html +=""
    	+'<div class="row orow">'
        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">完工时间:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + DoneTIME(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">客户评分:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + ((rowdata.serviceScore == undefined) ? "暂未评分" : rowdata.serviceScore) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
      

        + ' </div> '
        + '</div> ';
        
    html +=""
	+'<div class="row orow">'
    + ' <div class="form-group"> '
        //lzq 0619
        + '	<label class="col-lg-1" style="font-weight: bold;">注意事项:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + InstallRemarkData(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">房门信息:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + DoorInfoData(rowdata) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
     + ' </div> '
    + '</div> ';
        


        //tab-pane 1 end
        html +=""
        	+ '</div> '
        + '	</div> ';
        

        html +=""
        	+'		<div class="row tab-pane fade in  " id="identifier' + orderNo + '2"> '

        + '			<table class="nohide detailtable"></table> '
        + '		</div> '
        
        
        + '		<div class="row tab-pane fade in  " id="identifier' + orderNo + '3"> '

             + '  <div class="col-lg-12 row proinfopanel"> '

        
        

                 + '<div class="row orow">'
                 + ' <div class="form-group"> '

                 + ' </div> '
                 + '</div> '
        
            + '		</div> '
         + '	</div> '
         
         
         + '		<div class="row tab-pane fade in  " id="identifier' + orderNo + '4"> '

         			+ '  <div class="col-lg-12 row depositinfopanel"> '
         		   + ' </div> '
         + '		</div> '
         
         + '		<div class="row tab-pane fade in  " id="identifier' + orderNo + '5"> '

         + '			<table class="nohide moneydetailtable"></table> '
         + '		</div> '
         
        
        
        + '</div> '
        + '</div> '

        + ' </div>'

        + '';
    return html;
}


/**
 * 添加历史字表数据
 * @param row
 * @param orderNo
 * @returns
 */
function initDetailLog(row, orderNo,depositid) {


    $.ajax({
        type: "post",
        url: getRPath() + '/manager/torderinfo_pay/loadbyOrderNo',
        data: {
            id: orderNo
        },
        async: false,
        dataType: "json",
        success: function (response) {

            // <button type="button" class="btn btn-default" id="btnAdd_item">新增</button>
            var subTable = $(row).html(initDetailPanelInfo(row, response,depositid)).find('.detailtable');
            
            
            $(row).find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            	var oid=$(e.target).parent().parent().attr('oid');
            	var did=$(e.target).parent().parent().attr('did');
            	
            	if($(e.target).attr('href')=="#identifier"+oid+"3")
            	{
            		showProductInfo(oid,response);
            		//error(oid); // 激活的标签页
            	}
            	
            	if($(e.target).attr('href')=="#identifier"+oid+"4")
            	{
            		showDepositInfo(oid,did);
            		//error(oid); // 激活的标签页
            	}
            	if($(e.target).attr('href')=="#identifier"+oid+"5")
            	{
            		  var subMoneyTable = $(row).find('.moneydetailtable');
            		showMoneyLog(subMoneyTable,response.id);
            		//error(oid); // 激活的标签页
            	}
            	
          //e.relatedTarget // 前一个激活的标签页
          
        })
            
            
            
            showDetailLog($(subTable), orderNo);
        }
    });


}

function lockType(type){
	 //'锁类型，0：NB-锁，1：机械锁，2：其他锁',
	if(type=="0")
		return "NB-锁";
	else if(type=="1")
		return "机械锁";
	else if(type=="2")
		return "其他锁";
}


function showDepositInfo(oid,depostid){
	
	 $.ajax({
	        type: "post",
	        url: getRPath() + '/manager/userDeposit/load',
	        data: {
	            id: depostid
	        },
	        async: false,
	        dataType: "json",
	        success: function (rowdata) {

	        	var htmlPro="";
	        	
	        	htmlPro +=""
	        		   +'<div class="row orow">'
	            + ' <div class="form-group"> '
	            
	            
	            + '	<label class="col-lg-1" style="font-weight: bold;">锁匠ID:</label> '
	            + '	<div class="col-lg-2"> '
	            + '		<span >' + ((rowdata.userId == undefined) ? "" : rowdata.userId) + '</span> '
	            + '		<p class="help-block"></p> '
	            + '	</div> '
	            
	            + '	<label class="col-lg-1" style="font-weight: bold;">锁匠姓名:</label> '
	            + '	<div class="col-lg-2"> '

	            + '		<span >' + ((rowdata.userName == undefined) ? "" : rowdata.userName) + '</span> '
	            + '		<p class="help-block"></p> '
	            + '	</div> '
	            + '	<label class="col-lg-1" style="font-weight: bold;">锁匠电话:</label> '
	            + '	<div class="col-lg-2"> '
	            + '		<span >' + ((rowdata.lockerPhone == undefined) ? "" : rowdata.lockerPhone) + '</span> '
	            + '		<p class="help-block"></p> '
	            + '	</div> '
	            
	          

	            + ' </div> '
	            + '</div> ';
	        	

	        	
	        	htmlPro+= '<div class="row orow">'
		              + ' <div class="form-group"> '
		         
		          
		              + '	<label class="col-lg-1" style="font-weight: bold;">提现金额:</label> '
		              + '	<div class="col-lg-2"> '
			            + '		<span >' + ((rowdata.depositMoney == undefined) ? "" : rowdata.depositMoney) + '</span> '
			            + '		<p class="help-block"></p> '
			            + '	</div> '
			            
			            + '	<label class="col-lg-1" style="font-weight: bold;">收款银行:</label> '
			              + '	<div class="col-lg-2"> '
				            + '		<span >' + ((rowdata.bankName == undefined) ? "" : rowdata.bankName) + '</span> '
				            + '		<p class="help-block"></p> '
				            + '	</div> '
				            
				            + '	<label class="col-lg-1" style="font-weight: bold;">银行卡号:</label> '
				              + '	<div class="col-lg-2"> '
					            + '		<span >' + ((rowdata.depositNumber == undefined) ? "" : rowdata.depositNumber) + '</span> '
					            + '		<p class="help-block"></p> '
					            + '	</div> '
					            
					            + '	<label class="col-lg-1" style="font-weight: bold;">收款人:</label> '
					              + '	<div class="col-lg-2"> '
						            + '		<span >' + ((rowdata.depositReceiver == undefined) ? "" : rowdata.depositReceiver) + '</span> '
						            + '		<p class="help-block"></p> '
						            + '	</div> '
		             
		              
		              + ' </div> '
		              + '</div> ';
	        	
	        	
	        	
	        	
	            $("#identifier"+oid+"4").find(".depositinfopanel").html(htmlPro);
	        	  
	     
	        }
	    });
}



function showProductInfo(oid,rowdata){
	if( rowdata.productId)
	 $.ajax({
	        type: "post",
	        url: getRPath() + '/manager/tlockproductinfo/load',
	        data: {
	            id: rowdata.productId
	        },
	        async: false,
	        dataType: "json",
	        success: function (response) {

	        	var htmlPro="";
	        	
	        	htmlPro+= '<div class="row orow">'
	              + ' <div class="form-group"> '
	              + '	<label class="col-lg-1" style="font-weight: bold;">产品名称:</label> '
	              + '	<div class="col-lg-2"> '
	              + '		<span >' + response.name+ '</span> '
	              + '		<p class="help-block"></p> '
	              + '	</div> '
	              + '	<label class="col-lg-1" style="font-weight: bold;">产品型号:</label> '
	              + '	<div class="col-lg-2"> '
	              + '		<span >' + response.proType + '</span> '
	              + '		<p class="help-block"></p> '
	              + '	</div> '
	              + '	<label class="col-lg-1" style="font-weight: bold;">产品类型:</label> '
	              + '	<div class="col-lg-2"> '
	              + '		<span >' + lockType(response.lockType) + '</span> '
	              + '		<p class="help-block"></p> '
	              + '	</div> '
	              
	              + '	<label class="col-lg-1" style="font-weight: bold;">产品尺寸:</label> '
	              + '	<div class="col-lg-2"> '
	              + '		<span >' + response.proSize + '</span> '
	              + '		<p class="help-block"></p> '
	              + '	</div> '
	              
	              

	              + ' </div> '
	              + '</div> ';
	        	
	        	htmlPro+= '<div class="row orow">'
		              + ' <div class="form-group"> '
		              + '	<label class="col-lg-1" style="font-weight: bold;">产品备注:</label> '
		              + '	<div class="col-lg-4"> '
		              + '		<span >' + response.proDesc+ '</span> '
		              + '		<p class="help-block"></p> '
		              + '	</div> '
		        
		              
		              + ' </div> '
		              + '</div> ';
	        	
	        	htmlPro+= '<div class="row orow">'
		              + ' <div class="form-group"> '
		         
		              + '	<label class="col-lg-1" style="font-weight: bold;">产品图片:</label> '
		              + '	<div class="col-lg-5"> '
		              + '		<span >' + pro_pic(response.proImgs) + '</span> '
		              + '		<p class="help-block"></p> '
		              + '	</div> '
		             
		             
		              
		              + ' </div> '
		              + '</div> ';
	        	
	        	htmlPro+= '<div class="row orow">'
		              + ' <div class="form-group"> '
		         
		          
		              + '	<label class="col-lg-1" style="font-weight: bold;">产品附件:</label> '
		              + '	<div class="col-lg-5"> '
		              + '		<span >' + pro_attach(response.filenames,response.filemd5) + '</span> '
		              + '		<p class="help-block"></p> '
		              + '	</div> '
		             
		              
		              + ' </div> '
		              + '</div> ';
	        	
	        	
	        	
	        	
	            $("#identifier"+oid+"3").find(".proinfopanel").html(htmlPro);
	        	  
	     
	        }
	    });
}

/**
 * 产品图片
 * @param picmd5s
 * @returns
 */
function pro_pic(picmd5s){
	var pichtml="";
	if(picmd5s)
		{
	var pics=picmd5s.split(',');
	
	$.each(pics,function(index,item){
		pichtml+="<span><img src='"+getRPath()+"/upload/file/"+item+"' class='img-responsive proimg' ></span>";
	})
		}
	return pichtml;
}

/**
 * 产品附件
 * @param filenames
 * @param filemd5s
 * @returns
 */
function pro_attach(filenames,filemd5s){
	var attachhtml="";
	if(filenames)
		{
	var itmes=filenames.split(',');
	var itmemd5s=filemd5s.split(',');
	
	$.each(itmemd5s,function(index,item){
		attachhtml+="<span><a href='"+getRPath()+"/upload/file/"+item+"' class='img-responsive proimg' >"+itmes[index]+"</a></span>";
	})
		}
	return attachhtml;
}


/**
 * 关闭子表
 * @param index
 * @returns
 */
function closeDetailPanel(index) {
    tr = $("tr[data-index=" + index + "]");
    showOrCloseDetail(tr);
}


function sendCompleteCode(phone,oid){
	//var oid=$("tr[data-index=" + index + "]").find.attr('oid');
	
	var msg="确定要给客户手机号 "+phone+" 重发工单核销码吗?";
	
	cconfirm(msg,function(){
		$.ajax({
	        type: "post",
	        url: getRPath() + '/manager/torderinfo_todo/reSendCompleteNo',
	        data: {
	        	orderNo: oid
	        },
	        async: false,
	        dataType: "json",
	        success: function (response) {
	        	if(response.bol)
	        	success("操作完成!")
	        	else
	        		success("操作失败!")
	        },
	        error:function(a,b,c){
	        	error("发生错误!");
	        }
	  });
	})
	
	  
	
	
}



function refreshDetailLog(){
	 var opt = {
		        silent: true,
		        pageNumber: 1
		    };
	$(".detailtable").bootstrapTable('refresh', opt);
	

}


/**
 * 工单金额明细
 * @param ele
 * @param orderNo
 * @returns
 */
function showMoneyLog(ele,orderNo){
	$(ele).bootstrapTable({
		url : getRPath()+'/manager/torderinfo_pay/torderinfoMoneyList', // 请求后台的URL（*）
		method : 'post', // 请求方式（*）
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toolbar', // 工具按钮用哪个容器
		showHeader : true,
		searchAlign : 'left',
		buttonsAlign : 'left',

		searchOnEnterKey : true,
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : false, // 是否显示分页（*）
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
				
				
				
				orderinfoId :  orderNo,
               
				
			};
			return param;
		},
		/*onPostBody:function(){
			setTimeout(function(){
				initMoneyValidater();
			},
			 200);
			
		},*/
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
			field : 'operateTime',
			title : '时间',
			visible : false,
			align : 'center',
			valign : 'middle',
			   
	 formatter: function (value, row, index) {
         return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
     }
			
		},
		 {
			field : 'reasonDes',
			title : '说明',
			align : 'center',
			valign : 'middle',
			   
			
		},
	
	 {
			field : 'changeValueLocker',
			title : '金额',
			align : 'center',
			valign : 'middle',
			   formatter: function (value, row, index) {
				  /* if(row.reasonDes!="总计")
					   {
					   return "<input name='money' onkeyup='dynamicValidate(this)' data-bv-field='"+row.id+"' class='moneydetail form-control' value='"+value+"' id='"+row.id+"' >"
   	            	+'<small class="help-block" id="validate'+row.id+'" data-bv-validator="callback" data-bv-for="'+row.id+'" data-bv-result="" style="display: none;">金额异常</small>';   
					   }
	            	
				   else*/
					   return value;
	        }
			
		},
	
		],

	    });
}


/**
 * 工单操作日志
 * @param ele
 * @param orderNo
 * @returns
 */
function showDetailLog(ele, orderNo) {


    $(ele).bootstrapTable({
        url: getRPath() + '/manager/torderinfooperatelog/torderinfooperatelogList', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
        showHeader: true,
        searchAlign: 'left',
        buttonsAlign: 'left',

        searchOnEnterKey: true,
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortable: false, // 是否启用排序
        sortName: 'id', // 排序字段
        sortOrder: "desc", // 排序方式
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 30, // 每页的记录行数（*）
        pageList: [10, 25], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大

        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则

                orderNo: orderNo,

            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'operateTime',
                title: '操作时间',
                align: 'center',
                width: '200px',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return  dateFormat(value,"yyyy-MM-dd hh:mm:ss");
                }

            },
            {
                field: 'operateUserName',
                title: '操作人',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                	var t=row.type;
                	var name=value?value:"";
                	
                    if (t == 0) {
                    	name+= "(锁企操作)";
                    }
                    else if (t == 1) {
                    	name+= "(客服操作)"
                    } else if (t == 2) {
                    	name+= "(锁匠操作)"
                    }
                    else if (t == 3) {
                    	name+= "(财务)"
                    }
                    return name;
                }


            },
            {
                field: 'type',
                title: '操作类型',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "锁企操作"
                    }
                    else if (value == 1) {
                        return "客服操作"
                    } else if (value == 2) {
                        return "锁匠操作"
                    }
                }
            },

            {
                field: 'subType',
                title: '服务内容',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (row.type == 0) {
                        if (value == 1) {
                            return "新建下发"
                        } else if (value == 2) {
                            return "审核加钱"

                        }
                        
                        
                    }
                    else if (row.type == 1) {
                        if (value == 0) {
                            return "创建工单"
                        }
                        else if (value == 1) {
                            return "派单"
                        } else if (value == 2) {
                            return "审核"
                        } else if (value == 3) {
                            return "回访评分"
                        }
                        else if (value == 4) {
                            return "结账"
                        } else if (value == 5) {
                            return "确认锁企工单"
                        } else if(value==6){
                            return "确认锁企付款"
                        }
                        else if(value==7){
                            return "向锁企提交申请"
                        }
                        else if(value==8){
                            return "重新派单"
                        }
                        else if(value==9){
                            return "备注"
                        }
                    } else if (row.type == 2) {
                        if (value == 1) {
                            return "接单"
                        } else if (value == 2) {
                            return "申请加钱"
                        } else if (value == 3) {
                            return "申请退单"
                        } else if(value==4) {
                            return "确认完成"
                        }
                    }
                }
            },
            {
                field: 'content',
                title: '备注',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    var html=getSubTypeName(row,row.subType);
                	
                	if (value) {
                		
                		
                		var utype=$("#uType").val();
                		if(utype=="2")
                			{
                		   if (row.type == 1) {
                		    	 if (row.subType == -4) {
                		             return "审核改价"
                		         }
                		    	 else if (row.subType == -3) {
                		             return "工单改价"
                		         }
                		   }
                			}
                		
                    	return html+": "+value;
                    }
                   // else if(row.auditFailReason){
                   // 	return html+": "+ row.auditFailReason;
                   // }
                    else
                    	return  html+"";
                }

            },
        ]
    });
}

function getSubTypeName(row,value){
	 if (row.type == 0) {
         
		 if (value == -2) {
             return "工单恢复"
         }
		 else if (value == -1) {
             return "工单废弃"
         }
		 else if (value == 1) {
             return "新建下发"
         } else if (value == 2) {
             return "审核加钱"

         }
         
         
     }
     else if (row.type == 1) {
    	 if (value == -4) {
             return "审核改价"
         }
    	 else if (value == -3) {
             return "工单改价"
         }
    	 else if (value == -2) {
             return "工单恢复"
         }
		 else if (value == -1) {
             return "工单废弃"
         }
		 else if (value == 0) {
             return "创建工单"
         }
         else if (value == 1) {
             return "派单"
         } else if (value == 2) {
             return "审核"
         } else if (value == 3) {
             return "回访评分"
         }
         else if (value == 4) {
             return "结账"
         } else if (value == 5) {
             return "确认锁企工单"
         } else if(value==6){
             return "确认锁企付款"
         }
         else if(value==7){
             return "向锁企提交申请"
         }
         else if(value==8){
             return "重新派单"
         }else if(value==9){
             return "备注"
         }
     } else if (row.type == 2) {
         if (value == 1) {
             return "接单"
         } else if (value == 2) {
             return "申请加钱"
         } else if (value == 3) {
             return "申请退单"
         } else if(value==4) {
             return "确认完成"
         }
         else if(value==5) {
             return "合伙人重新分配工单"
         }
         else if(value==7) {
             return "合伙人审核"
         }else if(value==8) {
             return "提现申请"
         }
     }
     else if (row.type == 3) {
         if (value == 1) {
             return "提现审核"
         } else if (value == 2) {
             return "提现打款"
         } 
     }
}
