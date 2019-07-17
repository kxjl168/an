<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>源匠合伙人首页</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/pageAuto/homePage/css/homePage.css">
    <style>
        .row .row{
            margin-right: -15px;
            margin-left: -15px;
            margin-top: 25px;
        }
    </style>
</head>


<body>


<div class="col-lg-12 wzbj">
    <div style="padding-top: 9px; float: left; padding-right: 4px;">
        <img src="${ctx}/img/zhuye.svg" style="height: 20px;">
    </div>
    <h5 style="margin-top: 12px;">
        首页&nbsp;
    </h5>
</div>

<div class="col-lg-12 main-chart">

    <div class="row mtbox">
        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #3085ff;" onclick="jieopenurl()">
                <span class="fa fa-tasks"></span>
                <h3 id="text1">
                    待接单工单数
                    <br>
                    <br>
                    <p id="num1"></p>
                </h3>
            </div>
        </div>

        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #fb6e56;"onclick="huiopenurl()">
                <span class="fa fa-tags"></span>
                <h3 id="text2">
                    待回访工单数
                    <br>
                    <br>
                    <p id="num2"></p>
                </h3>
            </div>
        </div>


        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #3eb36f;" onclick="payopenurl()">
                <span class="fa fa-map"></span>
                <h3 id="text3">
                    待结账工单数
                    <br>
                    <br>
                    <p id="num3"></p>
                </h3>
            </div>
        </div>


        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #ea9336;" onclick="overopenurl()">
                <span class="fa fa-exclamation-triangle"></span>
                <h3 id="text4">
                    已完成工单数
                    <br>
                    <br>
                    <p id="num4"></p>
                <#--${count.notLock}-->
                </h3>
            </div>
        </div>


    </div>


    <div class="row mt">


        <div class="col-md-6 mb">

            <div class="white-panel pn donut-chart" style="height: 326px;">

                <div class="row">
                    <label for="datepicker" class="col-lg-2 control-label">时间：</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" id="datepicker_client2">
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-default" onclick="showSecondMonthDataChart()">查看</button>
                    </div>
                    <div class=" col-md-4"></div>
                </div>

                <div class="row">

                    <div id="MonthTj2" style="width: 800px;height:300px;"></div>
                </div>
            </div>

        </div>

        <div class="col-md-6 mb" id="mform1">

            <div class="white-panel pn donut-chart" style="height: 326px;">

                <div class="row">

                    <div class="form-group">
                        <label for="name" class="col-lg-3 control-label">请选择锁匠</label>
                        <div class="col-lg-4">
                            <select id="lockerSelect"  data-live-search="true" title="未选中任何项"></select>
                            <input type="hidden" id="lockerId" name="lockerId">
                            <p class="help-block"></p>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <button type="button" class="btn btn-default" onclick="showFirstMonthDataChart()">查看</button>
                    </div>
                    <div class=" col-md-4"></div>
                </div>

                <div class="row">

                    <div id="MonthTj1" style="width: 800px;height:300px;"></div>
                </div>
            </div>

        </div>
    </div>

</div>

<script src="${ctx}/vendor/echarts/js/echarts.min.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IGLSzubVQ9eyGSgK030H6Ytl6Era5ump"></script>
<script src="${ctx}/vendor/util/js/util.js"></script>
<script src="${ctx}/vendor/pageAuto/homePage/js/homePage2.js"></script>
<script src="${ctx}/vendor/pageAuto/homePage/js/initPlugin.js"></script>
</body>
</html>