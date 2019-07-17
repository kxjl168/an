<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>用户意见反馈表管理</title>


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
                首页&nbsp;>&nbsp;财务管理&nbsp;><span>&nbsp;用户提现审核</span>
            </h1>
        </div>
    </div>


    <div class="modal-body">
        <div class="row">


            <div class="queryclass">
                
                
                <#include "query.ftl"/>


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
                <div class="col-xs-5" style="margin-top: 16px;">用户提现申请列表</div>
                <div class="pull-right col-xs-push-6" style="padding-top: 10px; padding-right: 10px;">


					<button type="button" class="btn btn-default" id="exportLockerBillExcelBtn">导出锁匠对账单</button>
					<button type="button" class="btn btn-default" id="downLoadModule">
                        <a href="${ctx}/fileTemplate/提现账单模板.xlsx" download="提现账单模板.xlsx" style="color:white">下载导入模板</a>
                    </button>&nbsp;
					<button type="button" class="btn btn-default" id="exportOrderBillAudit">批量导入审核</button>
					<button type="button" class="btn btn-warning" id="exportOrderBillSettleAccount">批量导入结账</button>
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


        

    </div>
    
    <#include "../orderinfo_common/formPic.ftl">
<#include "importForm.ftl">
<#include "notPassReasonModal.ftl"/>

    <script src="${ctx}/vendor/util/js/util.js"></script>
    <script src="${ctx}/vendor/pageAuto/user_deposit/js/userDeposit.js"></script>
    <script src="${ctx}/vendor/pageAuto/orderinfo_common/initPlugin.js"></script>
   
    
    <script src="${ctx}/vendor/pageAuto/user_deposit/js/LockerSelect2.js"></script>
   
    <script src="${ctx}/vendor/pageAuto/torderinfooperatelog/js/orderlogdetail.js"></script>
    <script src="${ctx}/vendor/util/js/util.js"></script>

    <script>
            function modifyAndDeleteButton_item(value, row, index) {
                /* var passString
                var failString
                if (value == 0) {
                    passString = "审核通过"
                    failString = "审核不通过"
                } else if (value == 1) {
                    passString = "转账成功"
                    failString = "转账失败"
                } else {
                    return null
                } */
                
               // return ['<div class="">'
              //  + '<button type = "button" class = "pass btn btn-info" onclick="pass(\'' + row.id + '\')"><i class="glyphicon glyphicon-pencil">'
             //   + passString + '</i> </button>&nbsp;'
              //  + '<button type = "button" class = "fail btn btn-danger" onclick="fail(\'' + row.id + '\')"><i class="glyphicon glyphicon-pencil">'
             //   + failString + '</i> </button>'
             //   + '</div>'].join("");
                
				var html='<div class="">';
                
            	
                	//'<@shiro.hasPermission name="order_doing_money:modify" >'
            	     if (row.depositStatus == 0) 
            	    	 html+='<button id = "moneyModify2" type = "button" data-tippy-content="工单改价"    class = "tippy btn btn-warning"><i class="fa fa-cny"></i> </button>&nbsp;';
                	//+'</@shiro.hasPermission>';

                	  if (row.depositStatus == 0) 
                	html+= '<button  id = "moneypass"   type = "button" data-tippy-content="审核通过"    class = "tippy pass btn btn-info" ><i class="fa fa-check"></i> </button>&nbsp;';

                	if (row.depositStatus == 1) 
                	html+= '<button id = "moneypass2" type="button" data-tippy-content="工单结账"    class = "tippy pass btn btn-warning"><i class="fa fa-money"></i> </button>&nbsp;';

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

            </script>
            
    
</body>
</html>