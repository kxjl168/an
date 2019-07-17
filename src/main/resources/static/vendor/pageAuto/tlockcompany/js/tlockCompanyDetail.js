/**
 * 显示或者隐藏工单历史子表
 */
function showOrCloseDetail(tr, data, index) {

    if ($(tr.prevObject)[0].nodeName == "#document" || ($($(tr.prevObject)[0].nodeName && ($(tr.prevObject)[0].nodeName == "TD")))) {

        if ($(tr.prevObject)[0].nodeName == "#document") {

        } else if ($(tr.prevObject).find("button").length > 0) {//最后一个行有操作按钮的，不操作子行。

            return;
        }

        $(tr).parent().find(".detail-view").trigger('collapse-row');

        $(tr).find(".detail-icon i").click();
    }
}


function ccity(rowdata){
	
	var city="";
	city+= (rowdata.province === undefined)?"":rowdata.province+" ";
	city+=(rowdata.city === undefined)?"":rowdata.city+" ";// rowdata.city;
	city+=(rowdata.district === undefined)?"":rowdata.district+" "; //rowdata.district;
	
	return city;
	
}

function initDetailLog(row, rowdata) {

    $.ajax({
        type: "post",
        url: getRPath() + '/manager/tlockcompany/load',
        data: {
            id: rowdata.id
        },
        async: false,
        dataType: "json",
        success: function (response) {

            var enterpriseName = rowdata.enterpriseName;

            var enterpriseId = rowdata.id;

            var html = '<p class="phead">[' + enterpriseName + '] 详情</p> <button type="button" class="btn btn-default"  onclick=closeDetailPanel("' + $(row).parent().prev().attr("data-index") + '")>关闭</button>'
                + ''
                + '	<div class="row">  '
                + ' <div class="tabdetail modal-body nopadding margin-top-5">  '
                + '	<div class="row "> '

                + '	<ul class="nav nav-tabs" id="myTab" oid="' + enterpriseId + '"> '
                + '			<li class="active"><a href="#identifier' + enterpriseId + '1" data-toggle="tab">锁企详情</a></li> '
                + '			<li class=""><a href="#identifier' + enterpriseId + '2" data-toggle="tab">费用相关</a></li> '
                + '	</ul> '

                + '	<div class="tab-content"> '
                + '		<div class="row tab-pane fade in  active" id="identifier' + enterpriseId + '1"> '

                + '	<div class="col-lg-12 row orderinfopanel"> '

                + '<div class="row orow">'
                + ' <div class="form-group"> '
                + '	<label class="col-lg-1" style="font-weight: bold;">锁企名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + enterpriseName + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">联系人名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.contackPersonName === undefined)?"":rowdata.contackPersonName) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">联系人电话:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + rowdata.enterprisePhone + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '
                + ' </div> '
                + '</div> ';


            html += ""

                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">所在地:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ccity(rowdata)+ '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">详细地址:</label> '
                + '	<div class="col-lg-4"> '
                + '		<span >' +(rowdata.enterpriseAddressDetail === undefined)?"":rowdata.enterpriseAddressDetail + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '




                + ' </div> '
                + '</div> ';

            html += ""

                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">订单负责人名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.orderManagerName === undefined)?"":rowdata.orderManagerName)+ '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '



                + '	<label class="col-lg-1" style="font-weight: bold;">市场负责人名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.marketManagerName === undefined)?"":rowdata.marketManagerName)+ '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + '	<label class="col-lg-1" style="font-weight: bold;">锁企承担税点:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.taxPoint === undefined)?"":rowdata.taxPoint) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + ' </div> '
                + '</div> ';



            html += ""

                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">技术人员名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.techPersonName === undefined)?"":rowdata.techPersonName) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">技术人员联系方式:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.techPersonPhone === undefined)?"":rowdata.techPersonPhone) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '
                + '	<label class="col-lg-1" style="font-weight: bold;">客服负责人名称:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.customerManagerName === undefined)?"":rowdata.customerManagerName) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">客服负责人联系方式:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.customerManagerPhone === undefined)?"":rowdata.customerManagerPhone) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + ' </div> '
                + '</div> ';






            html += ""

                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">是否开票:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.receiptEnable === undefined)?"":(rowdata.receiptEnable===1)?"是":"否") + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">税率承担方:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.taxBear === undefined)?"":rowdata.taxBear) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">发票类型:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.receiptType === undefined)?"":(rowdata.receiptType===1)?"增值税专用发票":"普通发票") + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">发票抬头:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + ((rowdata.receiptTitle === undefined)?"":rowdata.receiptTitle) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

             
                + ' </div> '
                + '</div> ';
            
            
//            html += ""
//
//                + '<div class=" row orow">'
//
//                + ' <div class="form-group"> '
//
//               
//
//                + ' </div> '
//                + '</div> ';


            html += ""

                + '<div class=" row orow">'

                + ' <div class="form-group"> '






                + ' </div> '
                + '</div> '
                + '</div> '
                + '</div> ';





            html +=""

                + '		<div class="row tab-pane fade in " id="identifier' + enterpriseId + '2"> '

                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">安装费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.buildPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">维修费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.fixPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">开锁费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.openLockPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">测量费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.measutePrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + ' </div> '
                + '</div> ';


            html +=""


                + '<div class=" row orow">'

                + ' <div class="form-group"> '



                + '	<label class="col-lg-1" style="font-weight: bold;">安装猫眼费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.catBuildPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">换锁费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.changeLockPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">工程安装费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.engineeringInstallationPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">工程安装费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.engineeringMaintenancePrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + ' </div> '
                + '</div> ';


            html +=""


                + '<div class=" row orow">'

                + ' <div class="form-group"> '



                + '	<label class="col-lg-1" style="font-weight: bold;">其他费用:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.otherFee) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">特殊门费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.specialDoorPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">改装费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.refitPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">空跑费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.hurryInVainPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + ' </div> '
                + '</div> ';

            html +=""


                + '<div class=" row orow">'

                + ' <div class="form-group"> '

                + '	<label class="col-lg-1" style="font-weight: bold;">远途费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.longDistancePrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + '	<label class="col-lg-1" style="font-weight: bold;">加急费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.urgentPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '

                + '	<label class="col-lg-1" style="font-weight: bold;">假锁费:</label> '
                + '	<div class="col-lg-2"> '
                + '		<span >' + money(response.falseLockPrice) + '</span> '
                + '		<p class="help-block"></p> '
                + '	</div> '


                + ' </div> '
                + '</div> '

                + '</div> '

                + '	</div> '

                + '</div> '
                + '</div> '

                + ' </div>'

                + '';

            $(row).html(html);
        }
    });
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

function money(data) {
    if (data)
        return data
    else
        return "";
}
