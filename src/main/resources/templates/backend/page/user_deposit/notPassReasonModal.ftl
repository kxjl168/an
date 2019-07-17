<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="noPassReasonModal"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">输入不通过原因</span>
                </h4>

            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="notPassReasonForm" name="notPassReasonForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <input type="hidden" id="depositId" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label" id="failReason">失败原因</label>
                                <div class="col-lg-9">
                                    <textarea name="failCause" id="failCause"></textarea>
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
                <button id="noPassReasonSubmit" class="btn btn-default" type="button">确认</button>
            </div>
        </div>
    </div>
</div>
