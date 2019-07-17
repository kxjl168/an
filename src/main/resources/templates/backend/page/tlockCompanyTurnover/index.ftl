<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>锁企营业额统计</title>


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
                首页&nbsp;>&nbsp;统计报表&nbsp;><span>&nbsp;锁企营业额统计</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">
        <div class="row">

            <div class="queryclass collaps">

                <div class='querytitle ' data-tippy-content="展开查询条件">
                    <h5>查询条件 <i class="querytitlebtn fa fa-chevron-down"></i></h5>
                    <hr>
                </div>

                <form class="form-inline" id="mform">

                    <div id="companyIdView" class="form-group">
                        <label for="name" class="lb_text col-xs-5 control-label">锁企名称:</label>

                        <div class="col-xs-7">
                            <select type="text" name="lockEnterpriseID" class="form-control"
                                    id="lockEnterpriseID" placeholder="所属锁企" maxlength="11">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="time" class="lb_text col-xs-5 control-label">选择日期:</label>

                        <div class="col-xs-7">
                            <input id="q_month" type="text" name="q_month"
                                   class="form-control inputtxt datepicker" placeholder=""
                                   aria-controls="dataTables-example">
                        </div>
                    </div>

                    <script>
                        $(function () {

                            $.fn.datepicker.defaults.format = "yyyy";
                            $('#q_month').datepicker({
                                language: "zh-CN",
                                endDate: new Date(),
                                format: "yyyy",
                                clearBtn: true,
                                autoclose: true,
                                minViewMode: "years",
                                defaultDate: new Date(),
                            });


                            // num传入的数字，n需要的字符长度 ，批量添加房间数，房号计算，左加0
                            function PrefixInteger(num, n) {
                                return (Array(n).join(0) + num).slice(-n);
                            }

                            var date = new Date();
                            $('#q_month').val(date.getFullYear());

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
        <#--<div class="row">-->
        <#--<div class="col-xs-4" style="margin-top: 16px;">锁企月报</div>-->

        <#--<div class="col-xs-2 col-xs-push-6" style="padding-top: 10px;">-->
        <#--<button type="button" class="btn btn-default" id="btnImportOut">导出EXCEL</button>-->
        <#--</div>-->
        <#--</div>-->

            <div class="row">

                <input type="hidden" value="" id="change">
                <div class="col-sm-12">

                    <div class="table-responsive tbhidepage" style="margin: 10px;">

                        <table id="table_list_item"
                               class="table table-bordered table-hover table-striped "></table>
                    </div>
                </div>

            </div>

        </div>

        <div class="hide row">

            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="header">锁企月单量统计</span>
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

    <script src="${ctx}/vendor/echarts/js/echarts.js"></script>

    <script src="${ctx}/vendor/pageAuto/tlockCompanyTurnover/js/manager.js"></script>
    <script src="${ctx}/vendor/pageAuto/tlockproductinfo/js/managerEnterpriseSelect.js"></script>

    <script>
        $(function () {
            tippy(".querytitle", {
                arrow: true,
                content: $(".querytitle").attr('data-tippy-content'),
                placement: 'top-start',
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective',
            });

            $(".querytitle").click(function () {

                if ($(".queryclass").hasClass("collaps")) {
                    $(".queryclass").removeClass("collaps");
                    $(".querytitle").attr("data-tippy-content", "收起查询条件");
                }
                else {
                    $(".queryclass").addClass("collaps");
                    $(".querytitle").attr("data-tippy-content", "展开查询条件");
                }

                $(".querytitle")[0]._tippy.setContent($(".querytitle").attr("data-tippy-content"));

            })
        })
    </script>

</body>
</html>