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

                <h4 class="modal-title">
                    <span>输入不通过理由</span>
                </h4>

            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="notPassReasonForm" name="notPassReasonForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">不通过理由</label>
                                <div class="col-lg-9">
                                    <textarea name="auditReason" class="form-control" id="auditReason" maxlength="200"></textarea>
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
