<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">添加</span> 锁匠地区费用
                </h4>
            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">省名称</label>
                                <div class="col-lg-9">
                                    <select id="provinceEditSelect"  name="provinceEditSelect" class="selectpicker" data-live-search="true" title="请选择省名称">
                                        <option value="00">默认省份</option>
                                    </select>
                                    <input type="hidden" id="provinceEditCode" name="provinceEditCode">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">市名称</label>
                                <div class="col-lg-9">
                                    <select id="cityEditSelect"  class="selectpicker"   data-live-search="true" title="请选择市名称"></select>
                                    <input type="hidden" id="cityEditCode" name="cityEditCode">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装费</label>
                                <div class="col-lg-9">
                                    <input type="text" name="money"
                                           class="form-control" id="money"
                                           placeholder="安装费" maxlength="6">
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
                    <button type="button" class="btn btn-primary" id="commitBtn">
                        提交更改
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
