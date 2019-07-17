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


                            <ul class="nav nav-tabs" id="myTab">
                                <li class="active"><a href="#identifier1" data-toggle="tab">锁企详情</a></li>
                                <li class=""><a href="#identifier2" data-toggle="tab">费用相关</a></li>
                            </ul>
                            <div class="tab-content">

                            <div class="row tab-pane fade in  active" id="identifier1">
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="enterpriseName"
                                               class="form-control" id="enterpriseName"
                                               placeholder="名称" maxlength="30">
                                        <p class="help-block"></p>
                                    </div>
                                    <p class="need col-lg-1 control-label ">*</p>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">锁企联系人名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="contackPersonName" class="form-control" id="contackPersonName" placeholder="锁企联系人名称">
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">锁企联系人电话</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="enterprisePhone"
                                               class="form-control" id="enterprisePhone"
                                               placeholder="锁企联系人电话" maxlength="15">
                                        <p class="help-block"></p>
                                    </div>
                                    <p class="need col-lg-1 control-label ">*</p>
                                </div>

                                <div class="form-group ">
                                    <label for="name" class="col-lg-3 control-label">所在地</label>
                                    <div class="col-lg-8">
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
                                    <p class="need col-lg-1 control-label ">*</p>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">详细地址</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="enterpriseAddressDetail"
                                               class="form-control" id="enterpriseAddressDetail"
                                               placeholder="详细地址" maxlength="30"
                                               onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
                                               onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
                                               oncontextmenu="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
                                        >
                                        <p class="help-block"></p>
                                    </div>
                                    <p class="need col-lg-1 control-label ">*</p>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">合同开始时间</label>

                                    <div class="col-lg-8">
                                        <input type="text" name="agreenStartTime"

                                               class="form-control" id="agreenStartTime"
                                               placeholder="合同开始时间" readonly>
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">合同结束时间</label>

                                    <div class="col-lg-8">
                                        <input type="text" name="agreenEndTime"

                                               class="form-control" id="agreenEndTime"
                                               placeholder="合同结束时间" readonly>
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">订单负责人名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="orderManagerName" class="form-control" id="orderManagerName" placeholder="订单负责人名称">
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">市场负责人名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="marketManagerName" class="form-control" id="marketManagerName" placeholder="市场负责人名称">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">技术人员名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="techPersonName" class="form-control" id="techPersonName" placeholder="技术人员名称">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">技术人员联系方式</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="techPersonPhone" class="form-control" id="techPersonPhone" placeholder="技术人员联系方式" maxlength="15">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">客服负责人名称</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="customerManagerName" class="form-control" id="customerManagerName" placeholder="客服负责人名称">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">客服负责人联系方式</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="customerManagerPhone" class="form-control" id="customerManagerPhone" placeholder="客服负责人联系方式" maxlength="15">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">税率承担方</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="taxBear" class="form-control" id="taxBear" placeholder="税率承担方">
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">发票类型</label>
                                    <div class="col-lg-8">
                                        <select class="form-control in-line " id="receiptType" name="receiptType">
                                            <option value="" selected="selected">请选择</option>
                                            <option value="1">增值税专用发票</option>
                                            <option value="2">普通发票</option>
                                        </select>
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">发票抬头</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="receiptTitle" class="form-control" id="receiptTitle" placeholder="发票抬头">
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">是否开票</label>
                                    <div class="col-lg-8">
                                        <select class="form-control in-line " id="receiptEnable" name="receiptEnable">
                                            <option value="" selected="selected">请选择</option>
                                            <option value="1">是</option>
                                            <option value="2">否</option>
                                        </select>
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-lg-3 control-label">锁企承担税点</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="taxPoint" class="form-control" value="0" id="taxPoint" placeholder="锁企承担税点">
                                        <p class="help-block"></p>
                                    </div>
                                </div>

                            </div>
                            <div class="row tab-pane fade in  " id="identifier2">

                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">安装费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="buildPrice"
                                                   class="form-control" id="buildPrice"
                                                   placeholder="安装费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                        <p class="need col-lg-1 control-label ">*</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">维修费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="fixPrice"
                                                   class="form-control" id="fixPrice"
                                                   placeholder="维修费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                        <p class="need col-lg-1 control-label ">*</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">开锁费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="openLockPrice"
                                                   class="form-control" id="openLockPrice"
                                                   placeholder="开锁费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">测量费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="measutePrice"
                                                   class="form-control" id="measutePrice"
                                                   placeholder="测量费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">猫眼安装费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="catBuildPrice"
                                                   class="form-control" id="catBuildPrice"
                                                   placeholder="猫眼安装费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">换锁费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="changeLockPrice"
                                                   class="form-control" id="changeLockPrice"
                                                   placeholder="换锁费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">工程安装费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="engineeringInstallationPrice"
                                                   class="form-control" id="engineeringInstallationPrice"
                                                   placeholder="工程安装费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">工程维修费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="engineeringMaintenancePrice"
                                                   class="form-control" id="engineeringMaintenancePrice"
                                                   placeholder="工程维修费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">其他费用</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="otherFee"
                                                   class="form-control" id="otherFee"
                                                   placeholder="其他费用" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">特殊门费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="specialDoorPrice"
                                                   class="form-control" id="specialDoorPrice"
                                                   placeholder="特殊门费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">改装费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="refitPrice"
                                                   class="form-control" id="refitPrice"
                                                   placeholder="改装费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">空跑费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="hurryInVainPrice"
                                                   class="form-control" id="hurryInVainPrice"
                                                   placeholder="空跑费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">远途费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="longDistancePrice"
                                                   class="form-control" id="longDistancePrice"
                                                   placeholder="远途费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">加急费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="urgentPrice"
                                                   class="form-control" id="urgentPrice"
                                                   placeholder="加急费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="col-lg-3 control-label">假锁费</label>
                                        <div class="col-lg-8">
                                            <input type="text" name="falseLockPrice"
                                                   class="form-control" id="falseLockPrice"
                                                   placeholder="假锁费" maxlength="6">
                                            <p class="help-block"></p>
                                        </div>
                                    </div>

                            </div>
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
