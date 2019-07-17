<!-- 修改模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_optedit"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <#--<button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>-->


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title_opt">编辑选项</span>
                </h4>

            </div>

            <form id="mform_item_opt" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="optId" value="">
                <input type="hidden" id="optParentid" value="">
                <input type="hidden" id="optAssessId" value="">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">选项描述</label>

                                <div class="col-lg-9">
                                    <input type="text" name="optTitle"
                                           class="form-control" id="optTitle"
                                           placeholder="选项描述" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">排序号</label>

                                <div class="col-lg-9">
                                    <input type="text" name="optSortstring"
                                           class="form-control" id="optSortstring"
                                           placeholder="排序号" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="optScore" class="col-lg-3 control-label">选项分值</label>

                                <div class="col-lg-9">
                                    <input type="text" name="optScore"
                                           class="form-control" id="optScore"
                                           placeholder="选项分值" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">选择选项后是否生成售后单</label>

                                <div class="col-lg-9">
                                    <select class="form-control" id="optAddService">
                                        <option value="1">是</option>
                                        <option value="0" selected>否</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="" onclick="optClearInit()">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_editOptSave">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>


