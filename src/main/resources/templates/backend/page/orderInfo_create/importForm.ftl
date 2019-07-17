<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="importFileModal"
     role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="importFormTitle">
                    <span id="importTitle">导入工单</span>
                </h4>

            </div>

            <form id="importModalForm" name="importModalForm" class="form-horizontal"
                  role="form" action="" method="post" enctype="multipart/form-data">

                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">请选择工单所属的工程（若不选择则为散单）</label>-->

                                <#--<div class="col-lg-9">-->
                                    <#--<select id="importProjectSelect" class="selectpicker" data-live-search="true"-->
                                            <#--title="未选中任何项">-->
                                    <#--</select>-->
                                    <#--<input type="hidden" id="importProjectId" name="projectId">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#if Session["user"].companyId?exists>
                            <input type="hidden" id="importCompanyId" value="${(Session["user"].companyId)?c}" name="sellerId">
                        <#else>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">请选择工单所属锁企</label>

                                <div class="col-lg-9">
                                    <select id="importCompanySelect" class="form-control"  data-live-search="true"
                                            title="未选中任何项">
                                    </select>
                                    <input type="hidden" id="importCompanyId" name="sellerId">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </#if>
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
                    <button type="button" class="btn btn-info" id="excelFileTemplate">
                        <a href="${ctx}/fileTemplate/导入模板(3).zip" download="导入模板(3).zip">下载导入模板</a>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
