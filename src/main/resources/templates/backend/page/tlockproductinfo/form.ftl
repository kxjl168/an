<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;overflow-y: auto;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">添加</span> 锁企产品
                </h4>

            </div>

            <form id="mform" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">

                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">

                            <input type="hidden" id="id" name="id">


                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">产品名称</label>

                                <div class="col-lg-8">
                                    <input autocomplete="off" type="text" name="name"

                                           class="form-control" id="name"
                                           placeholder="产品名称">
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group" id="companyView" name="companyView">
                                <label for="telephone" id="companyLabel" class="col-lg-3 control-label">所属锁企</label>
                                <div class="col-lg-8">
                                    <select type="text" name="lockEnterpriseID" class="form-control"
                                            id="lockEnterpriseID" placeholder="所属锁企" maxlength="11">
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">产品型号-标识</label>

                                <div class="col-lg-8">
                                    <input type="text" name="proType"
                                            oninput="isProductinfoAvailable($(this))"
                                           class="form-control" id="proType"
                                           placeholder="产品型号-标识">
                                    <input type="hidden" name="oldProType"
                                           class="form-control" id="oldProType"
                                           placeholder="原产品型号-标识">
                                    <p class="help-block"><span style="color: red;display: none" id="proTypeMessage"></span>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>


                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">产品尺寸</label>

                                <div class="col-lg-8">
                                    <input type="text" name="proSize"

                                           class="form-control" id="proSize"
                                           placeholder="产品尺寸">
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">产品说明</label>

                                <div class="col-lg-8">
                                    <input type="text" name="proDesc"

                                           class="form-control" id="proDesc"
                                           placeholder="产品说明">
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="lockType" class="col-lg-3 control-label">产品类型</label>
                                <div class="col-lg-9">
                                    <input type="radio" name="lockType" checked value="0"> NB-锁 &nbsp;&nbsp;
                                    <input type="radio" name="lockType" value="1"> 机械锁 &nbsp;&nbsp;
                                    <input type="radio" name="lockType" value="2"> 其他锁 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group" id="ImeiNeedView">
                                <label for="ImeiNeed" class="col-lg-3 control-label">国脉智联锁(需IMEI)</label>
                                <div class="col-lg-9">
                                     <input type="radio" name="imeiNeed" checked  value="0">不需要 &nbsp;
                                    <input type="radio" name="imeiNeed"  value="1">需要 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password" class="col-lg-3 control-label">产品图片</label>
                                <div class="col-lg-8">
                                    <div id="proImgs"></div>
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">附件</label>
                                <div class="col-lg-8">
                                    <div id="annexs"></div>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">技术员姓名</label>

                                <div class="col-lg-8">
                                    <input type="text" name="techPersonName"

                                           class="form-control" id="techPersonName"
                                           placeholder="技术员姓名">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">技术员电话</label>

                                <div class="col-lg-8">
                                    <input type="text" name="techPersonPhone"

                                           class="form-control" id="techPersonPhone"
                                           placeholder="技术员电话">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装视频url地址1</label>

                                <div class="col-lg-8">
                                    <input type="text" name="proInstallUrl1"

                                           class="form-control" id="proInstallUrl1"
                                           placeholder="产品安装视频url地址1" maxlength="450">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装视频url地址2</label>

                                <div class="col-lg-8">
                                    <input type="text" name="proInstallUrl2"

                                           class="form-control" id="proInstallUrl2"
                                           placeholder="产品安装视频url地址2" maxlength="450">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="createRemark" class="col-lg-3 control-label">产品安装说明</label>

                                <div class="col-lg-8">
                                    <p class="text-info small  control-label ">重要! 请简明填写产品安装说明*</p>
                                    <textarea type="text" name="proInstallDesc"
                                              rows="5"
                                              class="form-control" id="proInstallDesc"
                                              placeholder="产品安装说明" maxlength="500"></textarea>
                                    <p class="help-block"></p>

                                </div>
                                <p class="need col-lg-1 control-label hide ">*</p>
                            </div>

                            <script>
                                $(function () {
                                    $("#createTime").datetimepicker({
                                        format: 'yyyy-mm-dd hh:ii:ss',
                                        language: 'zh-CN',
                                        autoclose: true,
                                        startDate: new Date()
                                    });
                                    $("#createTime").data('datetimepicker')
                                            .setDate('2019-5-13 17:06:44');
                                });
                            </script>


                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="close">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item">
                        提交更改
                    </button>
                </div>
            </form>


        </div>
    </div>
</div>
