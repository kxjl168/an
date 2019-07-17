<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="updateOrderInfoModal"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title" id="updateOrderInfoTitle">
                    <span id="updateTitle">修改工单信息</span>
                </h4>

            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="updateOrderInfoForm" name="updateOrderInfoForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <input type="hidden" id="orderId" name="id">

                           
                            
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeUpdate">关闭
                </button>
                <button id="submitConfirm" class="btn btn-default" type="button">确认</button>
            </div>
        </div>
    </div>
</div>
