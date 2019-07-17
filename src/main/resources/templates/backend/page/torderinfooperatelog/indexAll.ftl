<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>工单综合查询</title>


    <link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

    <link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">


    <style>

        .lb_text {
            width: 90px;
        !important;
            line-height: 35px;
        !important;
        }

        .queryclass {
            height: 280px;
        }
    </style>

</head>

<body>

<input type="hidden" id="managerType" value="${(Session["user"].type)?c}">


<div>

    <div class="row">

        <div class="col-lg-12 wzbj">
            <div style="padding-top: 9px; float: left; padding-right: 4px;">
                <embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
            </div>
            <h1 class="page-header">
                首页&nbsp;>&nbsp;工单管理&nbsp;><span>&nbsp;工单综合查询</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">


        <div class="row">
        <#include "order_search.ftl"/>
        </div>


        <div class="mainbody">
            <div class="row">
                <div class="col-xs-5" style="margin-top: 16px;">工单列表</div>

                <div class="col-xs-2 col-xs-push-5" style="padding-top: 10px;">
                    <button type="button" class="btn btn-default" id="btnImportOut">导出EXCEL</button>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">

                    <div class="table-responsive" style="margin: 10px;">
                        <table id="table_list_item"
                               class="hidefirst table table-bordered table-hover table-striped"></table>
                    </div>
                </div>
            </div>

        </div>


<#include "../orderinfo_common/formPic.ftl">


    </div>
    <script src="${ctx}/vendor/util/js/util.js"></script>

    <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/torderinfoall.js"></script>

    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/orderlogdetail.js"></script>
    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/managerEnterpriseSelect.js"></script>
</body>
</html>