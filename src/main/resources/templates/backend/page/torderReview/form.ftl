<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog auditWd">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>申请信息</span>
                </h4>

            </div>

            <div class="modal-body">

                <div class="row">
                    <div class="col-lg-12">
                        <input type="hidden" id="auditId">
                        <table id="orderInfoAuditTable"></table>
                    </div>
                </div>
            </div>
            <div id="auditModalFooter" class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                        id="close1">关闭
                </button>
            </div>


        </div>
    </div>
</div>
