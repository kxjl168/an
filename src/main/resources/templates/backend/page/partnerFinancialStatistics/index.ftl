<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>合伙人/代理人财务报表统计</title>
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
                首页&nbsp;>&nbsp;统计管理&nbsp;><span>&nbsp;合伙人/代理人财务报表统计</span>
            </h1>
        </div>
    </div>

    <div class="modal-body">
        <div class="row">
            <div class="queryclass4">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">日期:</label>
                        <div class="col-xs-7">
                            <input id="q_month" type="text" name="q_month"
                                   class="form-control inputtxt datepicker" placeholder=""
                                   aria-controls="dataTables-example" readonly="true">
                        </div>
                    </div>

                    <script>
                        $(function () {
                            $.fn.datepicker.defaults.format = "yyyy-mm";
                            $('#q_month').datepicker({
                                language: "zh-CN",
                                endDate: new Date(),
                                clearBtn: true,
                                autoclose: true,
                                format: "yyyy-mm",
                                minViewMode: "months",
                                defaultDate: new Date(),
                            });
                            // num传入的数字，n需要的字符长度 ，批量添加房间数，房号计算，左加0
                            function PrefixInteger(num, n) {
                                return (Array(n).join(0) + num).slice(-n);
                            }
                            var date = new Date();
                            $('#q_month').val(date.getFullYear() + "-" + PrefixInteger((date.getMonth() + 1), 2));
                        });
                    </script>
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
                <div class="col-xs-2" style="margin-top: 16px;">合伙人/代理人财务报表统计</div>
                <div class="col-xs-3 col-xs-push-2" style="padding-top: 10px;">
                    <button type="button" class="btn btn-default" id="btnImportPartnerOut">导出EXCEL</button>
                </div>
                <div class="col-xs-5 col-xs-push-5" style="padding-top: 10px;">
                    <button type="button" class="btn btn-default" id="btnImportNoPartnerOut">导出EXCEL</button>
                </div>
            </div>
            <div class="row">
            <#if Session["user"].companyId?exists>
            <#else>
                <div class="col-sm-6">
                    <div class="table-responsive tbhidepage" style="margin: 10px;">
                        <div class="col-xs-5" style="margin-top: 16px;">合伙人/代理人TOP5</div>
                        <table id="table_list_item"
                               class="table table-bordered table-hover table-striped "></table>
                    </div>
                    <div class='pull-right'><a id="detaillink" class='detaillink' href='javascript:void(0);'
                                               onclick="openDetail(0,'合伙人')">更多数据</a></div>
                </div>
            </#if>
                <input type="hidden" value="" id="change">
                <div class="col-sm-6">
                    <div class="table-responsive tbhidepage" style="margin: 10px;">
                        <div class="col-xs-5" style="margin-top: 16px;">无合伙人代理人TOP5</div>
                        <table id="table_list_item1"
                               class="table table-bordered table-hover table-striped "></table>
                    </div>
                    <div class='pull-right'><a id="detaillink" class='detaillink' href='javascript:void(0);'
                                               onclick="openDetail(1,'无合伙人')">更多数据</a></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="hide row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span class="header">合伙人/代理人财务报表统计</span>
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

<!-- 模态框（Modal） -->
<#include "form.ftl">

<script
        src="${ctx}/vendor/pageAuto/partnerfinancialstatistics/js/partnerfinancialstatistics.js"></script>
</body>
</html>