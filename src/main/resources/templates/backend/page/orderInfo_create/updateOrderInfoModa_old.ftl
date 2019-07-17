<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="updateOrderInfoModal"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>

                <h4 class="modal-title" id="updateOrderInfoTitle">
                    <span id="updateTitle">修改工单信息</span>
                </h4>

            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form id="updateOrderInfoForm" name="updateOrderInfoForm" class="form-horizontal"
                              role="form" action="" method="post">
                            <input type="hidden" id="orderId" name="id">

                            <div class="form-group">
                                <label for="lockCompanyName" class="col-lg-3 control-label">锁企名称</label>
                                <div class="col-lg-9">
                                    <input type="text" name="lockCompanyName"
                                           class="form-control" id="lockCompanyName"
                                           placeholder="锁企名称" readonly>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                           <!--  <div class="form-group">
                                <label for="lockCompanyName" class="col-lg-3 control-label">是否加急</label>
                                <div class="col-lg-9">
                                    <input type="radio" name="urgent" value="1" disabled>是
                                    <input type="radio" name="urgent" value="0" disabled>否
                                    <p class="help-block"></p>
                                </div>
                            </div> -->
                          
                          
                          
                          <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装的产品</label>

                                <div class="col-lg-9">
                                    <select id="updateproductId"  name="productId" data-style="" class=" form-control inputtxt" data-live-search="true"
                                            title="未选中任何项">
                                    </select>
                                <!--     <input type="hidden" id="companyId" name="sellerId"> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            

                            <div class="form-group">
                                <label for="serverType" class="col-lg-3 control-label">服务类型</label>

                                <div class="col-lg-9">
                                    <input type="checkbox" name="serverType" checked value="0">安装 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="1">维修 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="2">开锁 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="4">测量 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="5">猫眼安装 &nbsp;&nbsp;&nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="urgent" class="col-lg-3 control-label">是否加急</label>

                                <div class="col-lg-9">
                                    <input type="radio" name="urgent" checked value="0">否 &nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="urgent" value="1">是 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="isRoomNessary" class="col-lg-3 control-label">客户类型</label>

                                <div class="col-lg-9">
                                    <input type="radio" name="isRoomNessary" checked value="0">个人客户 &nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="isRoomNessary" value="1">企业客户 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>
                          
                          
                          
                          

                            <div class="form-group">
                                <label for="updateAppointmentTime" class="col-lg-3 control-label">预约时间</label>
                                <div class="col-lg-9">
                                    <input type="text" name="appointmentTime"
                                           class="form-control" id="updateAppointmentTime"
                                           placeholder="预约时间" readonly>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="updateCustomerName" class="col-lg-3 control-label">客户姓名</label>
                                <div class="col-lg-9">
                                    <input type="text" name="customerName"
                                           class="form-control" id="updateCustomerName"
                                           placeholder="客户姓名" maxlength="50">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="updateCustomerPhone" class="col-lg-3 control-label">客户电话</label>
                                <div class="col-lg-9">
                                    <input type="text" name="customerPhone"
                                           class="form-control" id="updateCustomerPhone"
                                           placeholder="客户电话" maxlength="11">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                              <div class="form-group">
                                <label for="customerPhone" class="col-lg-3 control-label">客户电话2</label>
                                <div class="col-lg-9">
                                    <input type="text" name="customerPhone2"
                                           class="form-control" id="updatecustomerPhone2"
                                           placeholder="客户电话" maxlength="11">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label for="serverType" class="col-lg-3 control-label">客户所在地</label>

                                <div class="col-lg-9">
                                    <select id="updateprovinceSelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择省份">
                                    </select>
                                    <select id="updatecitySelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择城市">
                                    </select>
                                    <select id="updatedistrictSelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择区县">
                                    </select>
                                    <input type="hidden" id="updateprovinceCode">
                                    <input type="hidden" id="updatecityCode">
                                    <input type="hidden" id="updatedistrictCode">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                      
                              <div class="form-group">
                                <label for="addressDetail" class="col-lg-3 control-label">客户安装详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="addressDetail"
                                           class="form-control" id="updateAddressDetail"
                                           placeholder="客户安装详细地址" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                             <div class="form-group">
                                <label for="addressDetail" class="col-lg-3 control-label">产品邮寄详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="mailAddressDetail"
                                           class="form-control" id="upatemailAddressDetail"
                                           placeholder="产品邮寄详细地址" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                              <div class="form-group">
                                <label for="updateCreateRemark" class="col-lg-3 control-label">备注</label>
                             
                                
                                  <div class="col-lg-9">
                                    <textarea type="text" name="createRemark"
                                    rows="5"
                                           class="form-control" id="updateCreateRemark"
                                           placeholder="创建备注" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeUpdate">关闭
                </button>
                <button id="submitConfirm" class="btn btn-default" type="button">确认</button>
            </div>
        </div>
    </div>
</div>
