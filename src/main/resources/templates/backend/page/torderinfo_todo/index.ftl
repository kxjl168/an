<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>待接单工单管理</title>


    <link rel="stylesheet" type="text/css" href="${ctx}/css/iot.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/vendor/fileinput/css/fileinput.min.css">

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
                首页&nbsp;>&nbsp;工单管理&nbsp;><span>&nbsp;待接单工单列表</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">
        <div class="row">
            <#include "../orderinfo_common/order_search.ftl"/>
        </div>


        <div class="mainbody">
            <div class="row">
                <div class="col-xs-4" style="margin-top: 16px;">待接单工单列表</div>
                
                
                
                
             <div class="col-xs-3 col-xs-push-6" style="padding-top: 10px;">

  <button type="button" class="btn btn-success hide" id="btnrefreshOrderTimer">刷新工单</button>

<#--   
                    <button type="button" class="btn btn-default" id="btnAdd_item">新增</button>
                    <button type="button" class="btn btn-default" id="btnImport_item">导入工单</button>
                    -->
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
                        <span class="header">待接单工单列表</span>
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
<#include "../orderinfo_common/allocateOrderModal.ftl">
<#include "../orderinfo_common/formPic.ftl">

    <script src="${ctx}/vendor/fileinput/js/fileinput.min.js"></script>
    <script src="${ctx}/vendor/fileinput/js/locales/zh.js"></script>
    <script src="${ctx}/vendor/util/js/util.js"></script>
    <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
    <script src="${ctx}/vendor/pageAuto/torderinfo_todo/js/torderinfo.js"></script>
    
    <script src="${ctx}/vendor/pageAuto/orderinfo_common/managerCompanySelect.js"></script>
    
    
    
     <script
            src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/orderlogdetail.js"></script>
            
            
            <script>

            /**
             * 按钮格式化
             * @param value
             * @param row
             * @param index
             * @returns {string}
             */
            function modifyAndDeleteButton_item(value, row, index) {
            	 var managerType = $("#managerType").val()
            	 
                if (managerType != 2) {
                   /*  return ['<div class="">'
                    + '<button id = "allocateOrder" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">分派工单</i> </button>&nbsp;'
                    + '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
                    + '</div>'].join(""); */


                 	var html='<div class="">'
                 		 + '<button id = "allocateOrder" type = "button" data-tippy-content="分派工单"  class = "tippy btn btn-info"><i class="fa fa-user"></i> </button>&nbsp;';
                       // + '<button id = "audit" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-check">审核</i> </button>&nbsp;';
//                       + '<button id = "untying" type = "button" class = "btn btn-info"><i class="fa fa-chat">解绑微信</i> </button>&nbsp;'
 
                        

  
                        
                    	if(row.dataState==1)
                    	html+= '<button id = "delete" type = "button"   data-tippy-content="废弃" class = "tippy btn btn-warning"><i class="fa fa-remove"></i> </button>&nbsp;';
                    	
                    	if(row.dataState==0)
                    	html+= '<button id = "reset" type = "button"   data-tippy-content="恢复" class = "tippy btn btn-success"><i class="fa fa-refresh"></i> </button>&nbsp;';

                    	html+= '<button id = "addRemark" type = "button"  data-tippy-content="备注" class = "tippy btn btn-info"><i class="fa fa-edit"></i> </button>&nbsp;';
                        
                    	html+='<@shiro.hasPermission name="order_to_assgin_money:modify" >'   
                        	+ '<button id = "moneyModify" type = "button" data-tippy-content="工单改价"  data-toggle="tooltip"  class = "tippy btn btn-warning"><i class="fa fa-cny"></i> </button>&nbsp;'
                        	+'</@shiro.hasPermission>';

                        	
                  		html+= 
                    			'<@shiro.hasPermission name="order_to_assgin:delete" >'   
                    			+'<button id = "realdelete" type = "button"  data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>'
                    			'</@shiro.hasPermission>';
                    			
                    			html+= '</div>';


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
            }

             
            </script>
            

</body>
</html>