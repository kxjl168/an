<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>锁匠服务费管理</title>

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
                首页&nbsp;>&nbsp;锁匠管理&nbsp;><span>&nbsp;锁匠服务费管理列表</span>
            </h1>
        </div>
    </div>

    <div class="modal-body">
        <div class="row">
            <div class="queryclass">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">服务类型:</label>
                        <div class="col-xs-7">
                            <select id="serverSelect" class=" form-control "
                                    data-live-search="true"
                                    title="请选择服务类型" data-style="">
                                <option value="" type="">请选择服务类型</option>
                                <option value="1" parentType="1">维修费</option>
                                <option value="5" parentType="1">猫眼安装费</option>
                                <option value="4" parentType="1">测量费</option>
                                <option value="2" parentType="1">开锁费</option>
                                <option value="6" parentType="1">换锁费</option>
                                <option value="7" parentType="1">工程安装费</option>
                                <option value="8" parentType="1">工程维修费</option>
                                <option value="19" parentType="1">其他</option>

                                <option value="4" parentType="2">特殊门</option>
                                <option value="3" parentType="2">改装费</option>
                                <option value="1" parentType="2">空跑费</option>
                                <option value="2" parentType="2">远途费</option>
                                <option value="5" parentType="2">加急费</option>
                                <option value="6" parentType="2">假锁费</option>
                            </select>
                            <input type="hidden" id="serverType" value="serverType">
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

        <div class="mainbody">
            <div class="row">
                <div class="col-xs-5" style="margin-top: 16px;">锁匠服务费列表</div>
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

        <div class="hide row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="header">锁匠服务费列表</span>
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

    <script src="${ctx}/vendor/pageAuto/price_locksmith_other/js/price_locksmith_other.js"></script>
</body>
</html>