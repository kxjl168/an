<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>已结账工单管理</title>


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
            height: 190px;
        }
    </style>
</head>

<body>


<div>
    <input type="hidden" id="managerType" value="${(Session["user"].type)?c}">
    <div class="row">

        <div class="col-lg-12 wzbj">
            <div style="padding-top: 9px; float: left; padding-right: 4px;">
                <embed src="${ctx}/img/zhuye.svg" type="image/svg+xml"></embed>
            </div>
            <h1 class="page-header">
                首页&nbsp;>&nbsp;工单管理&nbsp;><span>&nbsp;已结账工单列表</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">
        <div class="row">
        <#include "../orderinfo_common/order_search.ftl"/>
        </div>


        <div class="mainbody">
            <div class="row">
                <div class="col-xs-5" style="margin-top: 16px;">已结账工单列表</div>

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


        <div class="hide row">


            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="header">已结账工单列表</span>
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
<#include "../orderinfo_common/formPic.ftl">

    <script src="${ctx}/vendor/util/js/util.js"></script>

    <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
    <script
            src="${ctx}/vendor/pageAuto/orderCompleted/js/orderCompleted.js?1=1"></script>
            
                <script
            src="${ctx}/vendor/zscroll/zscroll.js"></script>
            
            <script
            src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/orderlogdetail.js?1=1"></script>
</body>
</html>