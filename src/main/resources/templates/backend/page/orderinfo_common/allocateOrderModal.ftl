<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="allocateOrderModal"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="allocateOrderModalTitle">
                    <span id="myModal_item_title">分派工单</span>
                </h4>

            </div>

            <form id="allocateOrderForm" name="allocateOrder" class="form-horizontal"
                  role="form" action="" method="post">
                <input id="orderId" name="id" type="hidden">
                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">
                        
                        
                        <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">锁匠作业区域:</label>

                                <div class="col-lg-9">
                                    
                                    <input type="text"   class="form-control" readonly="readonly" id="tip" name="tip">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            <input type="hidden" id="orderInfoId">
                            
                            
                             <div class="form-group">
                              <label for="name" class="col-lg-3 control-label">工单锁匠费用:</label>

                                <div class="col-lg-9">
                                    
                                  <div class="table-responsive" style="margin-bottom: 10px;">
                                    <table id="table_list_money"
                                           class="table table-bordered table-hover table-striped"></table>
                                </div>
                                </div>
                                
                                
                            </div>
                        
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">请选择锁匠/合伙人</label>

                                <div class="col-lg-9">
                                    <select id="lockerSelect"  name="lockerSelect"
                                            title="未选中任何项">
                                    </select>
                                    <input type="hidden" id="lockerId" name="lockerId">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                              
                        <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">价格变动:</label>

                                <div class="col-lg-9">
                                    
                                    <input type="text"   class="form-control"  id="addMoney" name="addMoney" maxlength="7">
                                     <p id='passtip' class="text-warning small ">增加或者减少服务费,若有价格变动则需要填写变动说明</p>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">价格说明:</label>

                                <div class="col-lg-9">
                                    
                                    <textarea type="text"  rows="5"  class="form-control"  id="addMoneyDesc" name="addMoneyDesc"></textarea>
                                    <p class="help-block">
                                </div>
                            </div>
                            
                            
                            
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="closeAllocate">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="allocateSubmit">
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
