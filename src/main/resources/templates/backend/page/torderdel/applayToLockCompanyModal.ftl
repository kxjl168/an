<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="applyToLockCompanyModal"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title">
                    <span>向锁企申请加钱</span>
                </h4>

            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="applyToLockCompanyForm" name="applyToLockCompanyForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">申请加钱金额</label>
                                <div class="col-lg-9">
                                    <input type="text" class="form-control" name="proposMoney" id="proposMoney" maxlength="6">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">申请理由</label>
                                <div class="col-lg-9">
                                    <textarea name="proposReason" class="form-control" id="proposReason" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button id="applyToLockCompanySubmit" class="btn btn-default" type="button">确认</button>
            </div>


        </div>
    </div>
</div>
