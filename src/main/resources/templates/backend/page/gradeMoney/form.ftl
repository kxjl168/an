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
                    <span id="myModal_item_title">新增奖励规则</span>
                </h4>

            </div>

            <form id="mform_item_add" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="modalHasShown">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">起始分数</label>

                                <div class="col-lg-9">
                                    <input type="text" name="startScore"
                                           class="form-control" id="startScore"
                                           placeholder="起始分数" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">结束分数</label>

                                <div class="col-lg-9">
                                    <input type="text" name="endScore"
                                           class="form-control" id="endScore"
                                           placeholder="结束分数" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">奖励金额</label>

                                <div class="col-lg-9">
                                    <input type="text" name="addMoney"
                                           class="form-control" id="addMoney"
                                           placeholder="奖励金额" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="gmClose">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_add">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_edit"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">修改奖励规则</span>
                </h4>

            </div>

            <form id="mform_item_edit" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="changeId">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">修改为</label>

                                <div class="col-lg-9">
                                    <input type="text" name="changMoney"
                                           class="form-control" id="changMoney"
                                           placeholder="金额" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="gmeClose">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_edit">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>
