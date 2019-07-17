<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_itemDetailPic"
    tabindex="-1"  role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog detailImgModal">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>图片信息</span>
                </h4>

            </div>

            <div class="modal-body">

                <div class="row">
                    <div class="">
                       <img class="detailImg img-responsive">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="close1">关闭
                </button>
            </div>


        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_itemRemark"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>工单备注</span>
                </h4>

            </div>
            
            <input type="hidden" class="remarkdOId" />

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="RemarkReasonForm" name="RemarkReasonForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">备注</label>
                                <div class="col-lg-9">
                                    <textarea name="content" class="form-control logremarkcontent" ></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="close2">关闭
                </button>
                <button id="btnLogRemark" class="btn btn-default" type="button">确认</button>
            </div>


        </div>
    </div>
</div>





<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_delRemark"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>废弃原因备注</span>
                </h4>

            </div>
            
            <input type="hidden" class="remarkdOId" />

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="deleteReasonForm" name="deleteReasonForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">备注</label>
                                <div class="col-lg-9">
                                    <textarea name="deleteReason" class="form-control logremarkcontent" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="close2">关闭
                </button>
                <button id="btnDelRemark" class="btn btn-default" type="button">确认</button>
            </div>


        </div>
    </div>
</div>




<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_MoneyModify"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header warning">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>工单改价</span>
                </h4>

            </div>
            
            <input type="hidden" class="remarkdOId" />

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="MoneyModifyForm" name="MoneyModifyForm" class="form-horizontal"
                              role="form" action="" method="post">
                             <div class="form-group">
                              <label for="name" class="col-lg-3 control-label">当前锁匠费用:</label>

                                <div class="col-lg-9">
                                    
                                  <div class="table-responsive" style="margin-bottom: 10px;">
                                    <table id="table_order_money"
                                           class="table_order_money table table-bordered table-hover table-striped"></table>
                                </div>
                                </div>
                                
                                
                            </div> 
                              
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">修改备注</label>
                                <div class="col-lg-9">
                                    <textarea name="logremarkcontent" class="form-control logremarkcontent" ></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="close2">关闭
                </button>
                <button id="btnMoneyModify" class="btn btn-default" type="button">确认</button>
            </div>


        </div>
    </div>
</div>






<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_AuditMoney"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>审核加价</span>
                </h4>

            </div>
            
            <input type="hidden" class="remarkdOId" />

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="AuditMoneyForm" name="AuditMoneyForm" class="form-horizontal"
                              role="form" action="" method="post">
                           
                             <div class="form-group">
                             
                             <@shiro.hasPermission name="menu_torderreview_money:modify" >
                             <input type="hidden" id="canModifyAuditMoney" value="true"/>
                             </@shiro.hasPermission>
                             
                                <label for="name" class="col-lg-3 control-label">申请加钱金额:</label>

                                <div class="col-lg-9">
                                    
                                    <input type="text"   class="form-control addMoney"  id="addMoney"    name="addMoney" maxlength="7">
                                   
                                     <p id='passtip' class="hide text-warning small ">增加或者减少服务费,若有价格变动则需要填写变动说明</p>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                           
                            <div class="form-group " id="auditReasonDv">
                                <label for="name" class="col-lg-3 control-label">备注</label>
                                <div class="col-lg-9">
                                    <textarea name="logremarkcontent" class="form-control logremarkcontent" maxlength="200"></textarea>
                                 
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        id="close2">关闭
                </button>
                <button id="btnAuditMoney" class="btn btn-default" type="button">确认</button>
            </div>


        </div>
    </div>
</div>


