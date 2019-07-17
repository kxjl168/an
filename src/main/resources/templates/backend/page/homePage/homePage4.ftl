<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>锁企客服首页</title>

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


    <div class="queryclass hide" style="min-height: 30px;">
        <p>工单数据</p>
    </div>

    <div class="queryclass margin-top-10" style="margin-top: 10;height: 80px;">


        <div>
            <h4>工单数据</h3>
                <hr>
        </div>

        <div class="row ">
            <div class="col-md-4 col-sm-2 box0">
                <div class="box1" style="background-color: #3085ff;"  onclick="toOrderConfirm()">
                    <span class="fa fa fa-handshake-o"></span>
                    <h3 >
                        待确认工单
                        <br>
                        <br>
                        <p id="numToConfirm"></p>
                    </h3>
                </div>
            </div>

            <div class="col-md-4 col-sm-2 box0">
                <div class="box1" style="background-color: #fb6e56;" onclick="toOrderTodo()">
                    <span class="fa fa-podcast"></span>
                    <h3 id="text2">
                        待接单工单
                        <br>
                        <br>
                        <p id="numToDo"></p>
                    </h3>
                </div>
            </div>
            <div class="col-md-4 col-sm-2 box0">
                <div class="box1" style="background-color: #3eb36f;"  onclick="toOrderDoing()">
                    <span class="fa fa-clock-o"></span>
                    <h3 id="text3">
                        待作业工单
                        <br>
                        <br>
                        <p id="numDoing"></p>
                    </h3>
                </div>
            </div>


        </div>
        <div class="row margin-top-10">

            <div class="col-md-4 col-sm-2 box0">
                <div class="box1" style="background-color: #ea9336;"  onclick="toOrderAudit()">
                    <span class="fa fa-commenting "></span>
                    <h3 id="text4">
                        待审核工单
                        <br>
                        <br>
                        <p id="numAudit"></p>
                    <#--${count.notLock}-->
                    </h3>
                </div>
            </div>

            <div class="col-md-4 col-sm-2 box0 ">
                <div class="box1" style="background-color: #d4b491;"  onclick="toOrderHuiche()">
                    <span class="fa fa-phone"></span>
                    <h3 id="text4">
                        工单回收站
                        <br>
                        <br>
                        <p id="numHuiche"></p>
                    <#--${count.notLock}-->
                    </h3>
                </div>
            </div>

            <div class="col-md-4 col-sm-2 box0 ">
                <div class="box1" style="background-color: #da5ebf;"  onclick="toOrderCompleted()">
                    <span class="fa fa-phone"></span>
                    <h3 id="text4">
                        已完结工单
                        <br>
                        <br>
                        <p id="numCompleted"></p>
                    <#--${count.notLock}-->
                    </h3>
                </div>
            </div>

            <div class="col-md-4 col-sm-2 box0 hide">
                <div class="box1" style="background-color: #da5ebf;"  onclick="toOrderBack()">
                    <span class="fa fa-question-circle"></span>
                    <h3 id="text4">
                        锁匠新反馈问题
                        <br>
                        <br>
                        <p id="numBack"></p>
                    <#--${count.notLock}-->
                    </h3>
                </div>
            </div>

            <div class="col-md-4 col-sm-2 box0 hide">
                <div class="box1" style="background-color: #da5ebf;"  onclick="toOrderAfter()">
                    <span class="fa fa-question-circle"></span>
                    <h3 id="text4">
                        售后问题工单
                        <br>
                        <br>
                        <p id="numAfter"></p>
                    <#--${count.notLock}-->
                    </h3>
                </div>
            </div>

        </div>
    </div>

    <div class="margin-top-10 hide">
    <#include "home_search.ftl"/>
    </div>


    <input type="hidden" id="operateUserId" name="operateUserId"
           value="${operateUserId}">

    <div class="queryclass hide " style="min-height: 30px;">
        <p>今日数据</p>
    </div>
    <div class="queryclass box2 margin-top-10 hide" >
        <div>
            <h4>今日工单</h3>
                <hr>
        </div>

        <div class="row hide">
        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #3085ff;"  onclick="openurlCreate('','','')">
                <span class="fa fa-tasks"></span>
                <h3 id="text1">
                    新下发工单
                    <br>
                    <br>
                    <p id="num1"></p>
                </h3>
            </div>
        </div>

        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #fb6e56;" onclick="openurl(1,5,'')">
                <span class="fa fa-tags"></span>
                <h3 id="text2">
                    源匠已处理工单
                    <br>
                    <br>
                    <p id="num2"></p>
                </h3>
            </div>
        </div>


        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #3eb36f;"  onclick="toOrderAudit()">
                <span class="fa fa-map"></span>
                <h3 id="text3">
                    待处理申请加钱工单
                    <br>
                    <br>
                    <p id="num3"></p>
                </h3>
            </div>
        </div>


        <div class="col-md-3 col-sm-2 box0">
            <div class="box1" style="background-color: #ea9336;"  onclick="openurl(0,2)">
                <span class="fa fa-exclamation-triangle"></span>
                <h3 id="text4">
                    已处理的源匠附加费工单
                    <br>
                    <br>
                    <p id="num4"></p>
                <#--${count.notLock}-->
                </h3>
            </div>
        </div>


    </div>


    <div class="row mt hide">


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



        </div>
    </div>

</div>

<script src="${ctx}/vendor/echarts/js/echarts.min.js"></script>

<#--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=IGLSzubVQ9eyGSgK030H6Ytl6Era5ump"></script>-->
<script src="${ctx}/vendor/util/js/util.js"></script>
<script src="${ctx}/vendor/pageAuto/homePage/js/homePage4.js"></script>
<script src="${ctx}/vendor/pageAuto/homePage/js/initPlugin.js"></script>
</body>
</html>