<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="importFileModal"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="importFormTitle">
                    <span id="importTitle">导入锁企结账工单</span>
                </h4>

            </div>

            <form id="importModalForm" name="importModalForm" class="form-horizontal"
                  role="form" action="" method="post" enctype="multipart/form-data">

                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="excelFile" class="col-lg-3 control-label">上传excel文件</label>

                                <div class="col-lg-9">
                                    <input type="file" name="excelFile"
                                           class="form-control" id="excelFile">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="closeImport">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="submitFileImport">
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
