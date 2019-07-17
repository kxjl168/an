<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item2"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">审核</span> 合伙人
                </h4>
            </div>

            <form id="mform_item2" name="mform_item2" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">名称</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyName" class="form-control"
                                           id="companyName" readonly="readonly" placeholder="名称">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">电话</label>
                                <div class="col-lg-9">
                                    <input type="number" name="companyPhone" class="form-control"
                                           id="companyPhone" readonly="readonly" placeholder="公司电话">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyAddressDetail"
                                           class="form-control" id="companyAddressDetail"
                                           readonly="readonly" placeholder="合伙人详细地址">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div>
                                <h3>审核</h3>
                                <hr>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">审核状态</label>
                                <div class="col-lg-9">
                                    <select class="form-control in-line " id="auditFlag"
                                            name="auditFlag">
                                        <option value="1" selected="selected">通过</option>
                                        <option value="2">不通过</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group " id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">不通过原因</label>
                                <div class="col-lg-9">
                                    <textarea rows="5" cols="45" id="auditReason" name="auditReason"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="close">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_audit">
                        确认审核
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
