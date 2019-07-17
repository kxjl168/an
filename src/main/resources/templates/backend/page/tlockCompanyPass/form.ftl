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
                    <span id="myModal_item_title">添加</span> 锁企
                </h4>
            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">名称</label>
                                <div class="col-lg-9">
                                    <input type="text" name="enterpriseName"
                                           class="form-control" id="enterpriseName"
                                           placeholder="名称" maxlength="30">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">电话</label>
                                <div class="col-lg-9">
                                    <input type="text" name="enterprisePhone"
                                           class="form-control" id="enterprisePhone"
                                           placeholder="电话">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="name" class="col-lg-3 control-label">所在地</label>
                                <div class="col-lg-9">
                                    <div id="distpicker" data-toggle="distpicker" class="in-line selectdist ">
                                        <select class="form-control" id="province" name="province"
                                                data-province="---- 选择省 ----">
                                        </select> <select class="form-control" id="city" name="city"
                                                          data-city="---- 选择市 ----"></select>
                                        <select class="form-control" id="district" name="district"
                                                data-district="---- 选择区 ----"></select>
                                        <input type="hidden" id="areaCode" name="areaCode">
                                    </div>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="enterpriseAddressDetail"
                                           class="form-control" id="enterpriseAddressDetail"
                                           placeholder="详细地址" maxlength="30"
                                           onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
                                           onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
                                           oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装费</label>
                                <div class="col-lg-9">
                                    <input type="text" name="buildPrice"
                                           class="form-control" id="buildPrice"
                                           placeholder="安装费" maxlength="9">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">维修费</label>
                                <div class="col-lg-9">
                                    <input type="text" name="fixPrice"
                                           class="form-control" id="fixPrice"
                                           placeholder="维修费" maxlength="9">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">安装费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="buildPrice"-->
                                           <#--class="form-control" id="buildPrice"-->
                                           <#--placeholder="安装费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">维修费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="fixPrice"-->
                                           <#--class="form-control" id="fixPrice"-->
                                           <#--placeholder="维修费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">特殊门</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="specialDoorPrice"-->
                                           <#--class="form-control" id="specialDoorPrice"-->
                                           <#--placeholder="特殊门" >-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">假锁</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="fakeLockPrice"-->
                                           <#--class="form-control" id="fakeLockPrice"-->
                                           <#--placeholder="假锁">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">测量费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="measutePrice"-->
                                           <#--class="form-control" id="measutePrice"-->
                                           <#--placeholder="测量费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">空跑费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="wastedJourneyPrice"-->
                                           <#--class="form-control" id="wastedJourneyPrice"-->
                                           <#--placeholder="空跑费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">猫眼安装费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="catBuildPrice"-->
                                           <#--class="form-control" id="catBuildPrice"-->
                                           <#--placeholder="猫眼安装费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">开锁费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="openLockPrice"-->
                                           <#--class="form-control" id="openLockPrice"-->
                                           <#--placeholder="开锁费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
                            <#--<div class="form-group">-->
                                <#--<label for="name" class="col-lg-3 control-label">加急费</label>-->
                                <#--<div class="col-lg-9">-->
                                    <#--<input type="number" name="hurryPrice"-->
                                           <#--class="form-control" id="hurryPrice"-->
                                           <#--placeholder="加急费">-->
                                    <#--<p class="help-block"></p>-->
                                <#--</div>-->
                            <#--</div>-->
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
