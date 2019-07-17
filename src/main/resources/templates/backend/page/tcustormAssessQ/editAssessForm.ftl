<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_edit"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <#--<button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>-->


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title_1">新增题目</span>
                </h4>

            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="qusId">
                <input type="hidden" id="qusAssessId" >
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">题目名</label>

                                <div class="col-lg-9">
                                    <input type="text" name="qusTitle"
                                           class="form-control" id="qusTitle"
                                           placeholder="题目名" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">排序号</label>

                                <div class="col-lg-9">
                                    <input type="text" name="questionSortstring"
                                           class="form-control" id="questionSortstring"
                                           placeholder="排序号" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">题目类型</label>

                                <div class="col-lg-9">
                                    <select  class="form-control" id="questionType">
                                        <option value="0">单选</option>
                                        <option value="1">可多选</option>
                                        <option value="2">手动输入</option>
                                    </select>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="" onclick="clearInit()">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_editSave">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>
