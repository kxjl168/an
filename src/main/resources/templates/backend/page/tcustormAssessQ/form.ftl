<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_add"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">新增问卷</span>
                </h4>

            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="modalHasShown">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">问卷标题名</label>

                                <div class="col-lg-9">
                                    <input type="text" name="questionTitle"
                                           class="form-control" id="questionTitle"
                                           placeholder="试卷标题" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="addClose">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>
