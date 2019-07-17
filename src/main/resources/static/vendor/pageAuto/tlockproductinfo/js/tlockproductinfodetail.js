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


function initDetailLog(row, rowdata) {

    var html = '<p class="phead">[' + rowdata.name + '] 详情</p> <button type="button" class="btn btn-default"  onclick=closeDetailPanel("' + $(row).parent().prev().attr("data-index") + '")>关闭</button>'
        + ''
        + '	<div class="row">  '
        + ' <div class="tabdetail modal-body nopadding margin-top-5">  '
        + '	<div class="row "> '

        + '	<ul class="nav nav-tabs" id="myTab" oid="' + rowdata.id + '"> '
        + '			<li class="active"><a href="#identifier' + rowdata.id + '" data-toggle="tab">产品详情</a></li> '
        + '	</ul> '

        + '	<div class="tab-content"> '
        + '		<div class="row tab-pane fade in  active" id="identifier' + rowdata.id + '"> '

        + '	<div class="col-lg-12 row orderinfopanel"> '


        + '<div class="row orow">'
        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">产品名称:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.name + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">产品型号:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.proType + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '
        + '	<label class="col-lg-1" style="font-weight: bold;">产品类型:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + lockType(rowdata.lockType) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '

        + '	<label class="col-lg-1" style="font-weight: bold;">产品尺寸:</label> '
        + '	<div class="col-lg-2"> '
        + '		<span >' + rowdata.proSize + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '


        + ' </div> '
        + '</div> ';


    html += '<div class="row orow">'

        + ' <div class="form-group"> '
        + '	<label class="col-lg-1" style="font-weight: bold;">产品备注:</label> '
        + '	<div class="col-lg-5"> '
        + '		<span >' + rowdata.proDesc + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '


    if ($("#managerType2").val()!='2') {
        html +='	<label class="col-lg-1" style="font-weight: bold;">国脉智联锁(需IMEI):</label> '
            + '	<div class="col-lg-2"> '
            + '		<span >'+ imeiNeed(rowdata.imeiNeed) + '</span> '
            + '		<p class="help-block"></p> '
            + '	</div> '
    }


    html += '</div> ';
            + ' </div> '



    html += '<div class="row orow">'
        + ' <div class="form-group"> '

        + '	<label class="col-lg-1" style="font-weight: bold;">产品图片:</label> '
        + '	<div class="col-lg-5"> '
        + '		<span >' + pro_pic(rowdata.proImgs) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '


        + ' </div> '
        + '</div> ';

    html += '<div class="row orow">'
        + ' <div class="form-group"> '


        + '	<label class="col-lg-1" style="font-weight: bold;">产品附件:</label> '
        + '	<div class="col-lg-5"> '
        + '		<span >' + pro_attach(rowdata.filenames, rowdata.filemd5) + '</span> '
        + '		<p class="help-block"></p> '
        + '	</div> '


        + ' </div> '
        + '</div> ';


    html += '<div class="row orow">'
        + ' <div class="form-group"> '

        + '	<label class="col-lg-1" style="font-weight: bold;">技术员姓名:</label> '
        + '	<div class="col-lg-2"> ';
    if (rowdata.techPersonName) {
        html += '		<span >' + rowdata.techPersonName + '</span> '
    }
    html += '		<p class="help-block"></p> '
        + '	</div> '

        + '	<label class="col-lg-1" style="font-weight: bold;">技术员电话:</label> '
        + '	<div class="col-lg-2"> ';
    if (rowdata.techPersonPhone) {
        html += '		<span >' + rowdata.techPersonPhone + '</span> '
    }
    html += '		<p class="help-block"></p> '
        + '	</div> '


        + ' </div> '
        + '</div> ';

    html += '<div class="row orow">'
        + ' <div class="form-group"> '

        + '	<label class="col-lg-1" style="font-weight: bold;">安装说明:</label> '
        + '	<div class="col-lg-11"> ';
    if (rowdata.proInstallDesc) {
        html += '		<span >' + rowdata.proInstallDesc + '</span> '
    }
    html += '		<p class="help-block"></p> '
        + '	</div> '


        + ' </div> '
        + '</div> ';

    if (rowdata.proInstallUrl1) {

        if (rowdata.proInstallUrl1.length > 0) {

            html += '<div class="row orow">'
                + ' <div class="form-group"> '

                + '	<label class="col-lg-2" style="font-weight: bold;">产品安装视频url地址1:</label> '
                + '	<div class="col-lg-10"> '
                + '     <span><a href=' + rowdata.proInstallUrl1 + '>地址一</a></span>'
                + '		<p class="help-block"></p> '
                + '	</div> '


                + ' </div> '
                + '</div> ';
        }
    }

    if (rowdata.proInstallUrl2) {

        if (rowdata.proInstallUrl2.length > 0) {

            html += ""
                + '<div class="row orow">'
                + ' <div class="form-group"> '
                + '	<label class="col-lg-2" style="font-weight: bold;">产品安装视频url地址2:</label> '
                + '	<div class="col-lg-10"> '
                + '		<span><a href=' + rowdata.proInstallUrl2 + '>地址二</a></span>'
                + '		<p class="help-block"></p> '
                + '	</div> '

                + ' </div> '
                + '</div> ';
        }
    }


    html += ""
        + '</div> '

        + '	</div> '

        + '</div> '
        + '</div> '

        + ' </div>'

        + '';

    $(row).html(html);

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

function lockType(type) {
    //'锁类型，0：NB-锁，1：机械锁，2：其他锁',
    if (type == "0")
        return "NB-锁";
    else if (type == "1")
        return "机械锁";
    else if (type == "2")
        return "其他锁";
}

function imeiNeed(type) {
    if(type == "0"){
        return "不需要";
    }else{
        return "需要";
    }
}

/**
 * 产品附件
 * @param filenames
 * @param filemd5s
 * @returns
 */
function pro_attach(filenames, filemd5s) {
    var attachhtml = "";
    if (filenames) {
        var itmes = filenames.split(',');
        var itmemd5s = filemd5s.split(',');

        $.each(itmemd5s, function (index, item) {
            attachhtml += "<span><a href='" + getRPath() + "/upload/file/" + item + "' class='img-responsive proimg' >" + itmes[index] + "</a></span>";
        })
    }
    return attachhtml;
}

function pro_pic(picmd5s) {
    var pichtml = "";
    if (picmd5s) {
        var pics = picmd5s.split(',');

        $.each(pics, function (index, item) {
            pichtml += "<span><img src='" + getRPath() + "/upload/file/" + item + "' class='img-responsive proimg' ></span>";
        })
    }
    return pichtml;
}
