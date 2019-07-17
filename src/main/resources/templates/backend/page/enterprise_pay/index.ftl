<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>锁企待结账工单管理</title>


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
                首页&nbsp;>&nbsp;财务管理&nbsp;><span>&nbsp;锁企待结账工单列表</span>
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
                <#include "order_search.ftl"/>
            </div>
        </div>

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

        <div class="mainbody">
            <div class="row">
                <div class="col-xs-5" style="margin-top: 16px;">待结账工单列表</div>
                <div class="pull-right col-xs-push-6" style="padding-top: 10px;">

                    <button type="button" class="hide btn btn-default" id="btnAdd_item">新增</button>

   <@shiro.hasPermission name="companypay:payedit" >
                    <button type="button" class="btn btn-default" id="exportEnterpriseBillExcelBtn">导出锁企对账单</button>
                    <button type="button" class="btn btn-default" id="downLoadModule">
                        <a href="${ctx}/fileTemplate/锁企批量结账模板.xlsx" download="锁企批量结账模板.xlsx" style="color:white">下载导入模板</a>
                    </button>&nbsp;
                    <button type="button" class="btn btn-warning" id="exportOrderBillSettleAccount">批量导入结账</button>
</@shiro.hasPermission>
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


        <div class="hide row">


            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="header">待结账工单列表</span>
                    </div>
                    <div class="panel-body">

                        <div id="dataTables-example_wrapper"
                             class=" dataTables_wrapper form-inline dt-bootstrap no-footer">
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
<#include "importForm.ftl">

    <script src="${ctx}/vendor/util/js/util.js"></script>

    <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>

    <script
            src="${ctx}/vendor/pageAuto/enterprise_pay/js/torderinfo.js"></script>


    <script
            src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/orderlogdetail.js"></script>

<script
            src="${ctx}/vendor/pageAuto/enterprise_pay/js/managerEnterpriseSelect.js"></script>
    <script>


        function modifyAndDeleteButton_item(value, row, index) {
            //if (managerType==2){
            if (row.finishedState === 1) {

            } else if (row.orderState > 400) {
          /*       return ['<div class="">'
                //使用shiro权限标签，从用户配置的按钮权限上过滤
                + '<@shiro.hasPermission name="companypay:payedit" >'
                + '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">结账</i> </button>&nbsp;'
                +'</@shiro.hasPermission >'
                + '</div>'].join("");
 */
             	var html='<div class="">';

             	if(row.finishedState!=1)
             	{
             	html+= '<@shiro.hasPermission name="companypay:payedit" >'
                     + '<button id = "update" type = "button"  data-tippy-content="结账" class = "tippy btn btn-info"><i class="fa fa-cny"></i> </button>&nbsp;'
                     +'</@shiro.hasPermission >';
             	}

           	 html+='</div>';
     		

     		setTimeout(function(){
 				tippy(".tippy",{
 						 arrow: true,
 						  arrowType: 'round', // or 'sharp' (default)
 						  animation: 'perspective',
 				}
         				)
 			},500);
         	
 	
     return [html].join("");
                
            }
            //}

        }

    </script>

</body>
</html>