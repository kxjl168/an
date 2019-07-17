<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>工单历史管理</title>


    <link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">

    <link rel="stylesheet" href="${ctx}/js/ztree/zTreeStyle.css">
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
                首页&nbsp;>&nbsp;工单管理&nbsp;><span>&nbsp;工单历史列表</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">
        <div class="row">


            <div class="queryclass3">


                <form class="form-inline">

                    <div class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">订单编号:</label>

                        <div class="col-xs-7">
                            <input id="q_orderNo" type="text" name="q_orderNo"
                                   class="form-control inputtxt" placeholder=""
                                   aria-controls="dataTables-example">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">操作对象:</label>

                        <div class="col-xs-7">
                            <select name="q_type" id="q_type" class="form-control inputtxt">
                                <option value="">请选择</option>
                                <option value="1">客服操作</option>
                                <option value="2">锁匠操作</option>
                            </select>
                        </div>
                    </div>

                </form>

                <form class="form-inline margin-top-10">


                    <div class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">详细内容</label>

                        <div class="col-xs-7">
                            <input id="q_content" type="text" name="q_content"
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


        <div class="mainbody">
            <div class="row">
                <div class="col-xs-5" style="margin-top: 16px;">工单历史列表</div>
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
                        <span class="header">工单历史列表</span>
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


    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/torderinfooperatelog.js"></script>
    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/managerEnterpriseSelect.js"></script>
</body>
</html>