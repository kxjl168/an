<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
    role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">工单录入</span>
                </h4>

            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <input type="hidden" id="modalHasShown">
                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">

                            <input type="hidden" id="id" name="id">

                        <#if Session["user"].companyId?exists>
                        
                         <input type="hidden" id="companyId" 
                                   value="${(Session["user"].companyId)?c}">
                        
                            <input type="hidden" id="sellerId" name="sellerId"
                                   > 
                        <#else>
                            <div class="form-group" id="dvSellerId">
                                <label for="name" class="col-lg-3 control-label">工单所属锁企</label>

                                <div class="col-lg-8">
                                    <select id="sellerId"  name="sellerId" data-style="" class=" form-control inputtxt" data-live-search="true"
                                            title="未选中任何项">
                                    </select>
                                    
                                    
                                <!--     <input type="hidden" id="companyId" name="sellerId"> -->
                                    <p class="help-block"></p>
                                </div>
                                  <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            
                             <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">工单类型</label>

                                <div class="col-lg-8">
                                    <select id="orderFeeType"  name="orderFeeType" data-style="" class=" form-control inputtxt" data-live-search="true"
                                            title="未选中任何项">
                                            	<option value="0" >标准工单</option>
                                            	<option value="1">自费工单</option>
                                    </select>
                                <!--     <input type="hidden" id="companyId" name="sellerId"> -->
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                        </#if>
                        
                       
                        <div class="form-group hide" id="dvEnterpriseName">
                                <label for="name" class="col-lg-3 control-label">锁企</label>

                                <div class="col-lg-8">
                                      <input type="text" name="enterpriseName" readonly="readonly"
                                           class="form-control" id="enterpriseName"
                                           placeholder="锁企" maxlength="50">
                                <!--     <input type="hidden" id="companyId" name="sellerId"> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                        
                        <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">安装的产品</label>

                                <div class="col-lg-8">
                                    <select id="productId"  name="productId" data-style="" class=" form-control inputtxt" data-live-search="true"
                                            title="未选中任何项">
                                    </select>
                                <!--     <input type="hidden" id="companyId" name="sellerId"> -->
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            
                              <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">锁数量</label>

                                <div class="col-lg-8">
                                    <input type="text" name="lockNum"
                                           class="form-control" id="lockNum"
                                           placeholder="锁数量" max="10">
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            

                            <div class="form-group">
                                <label for="serverType" class="col-lg-3 control-label">服务类型</label>

                                <div class="col-lg-8">
                                    <input type="checkbox" name="serverType" checked value="0">安装 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="4">测量 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="1">维修 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="2">开锁 &nbsp;&nbsp;&nbsp;
                                    <input type="checkbox" name="serverType" value="6">换锁 &nbsp;&nbsp;&nbsp;
                                    
                                    <input type="checkbox" name="serverType" value="5">猫眼安装 &nbsp;&nbsp;&nbsp;
                                      
                                        <input type="checkbox" name="serverType" value="7">工程安装 &nbsp;&nbsp;&nbsp;
                                          <input type="checkbox" name="serverType" value="8">工程维修 &nbsp;&nbsp;&nbsp;
                                            <input type="checkbox" name="serverType" value="19">其他 &nbsp;&nbsp;&nbsp;
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="urgent" class="col-lg-3 control-label">是否加急</label>

                                <div class="col-lg-8">
                                    <input type="radio" name="urgent" checked value="0">否 &nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="urgent" value="1">是 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="isRoomNessary" class="col-lg-3 control-label">客户类型</label>

                                <div class="col-lg-8">
                                    <input type="radio" name="isRoomNessary" checked value="0">个人客户 &nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="isRoomNessary" value="1">企业客户 &nbsp;
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="appointmentTime" class="col-lg-3 control-label">预约时间</label>

                                <div class="col-lg-8">
                                    <input type="text" name="appointmentTime"

                                           class="form-control" id="appointmentTime"
                                           placeholder="预约时间" readonly>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="customerName" class="col-lg-3 control-label">客户姓名</label>
                                <div class="col-lg-8">
                                    <input type="text" name="customerName"
                                           class="form-control" id="customerName"
                                           placeholder="客户姓名" maxlength="25">
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="customerPhone" class="col-lg-3 control-label">客户电话</label>
                                <div class="col-lg-8">
                                    <input type="text" name="customerPhone"
                                           class="form-control" id="customerPhone"
                                           placeholder="客户电话" maxlength="11">
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            <div class="form-group">
                                <label for="customerPhone" class="col-lg-3 control-label">客户电话2</label>
                                <div class="col-lg-8">
                                    <input type="text" name="customerPhone2"
                                           class="form-control" id="customerPhone2"
                                           placeholder="客户电话" maxlength="11">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="serverType" class="col-lg-3 control-label">客户所在地</label>

                                <div class="col-lg-8">
                                    <select id="provinceSelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择省份">
                                    </select>
                                    <select id="citySelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择城市">
                                    </select>
                                    <select id="districtSelect" class="selectpicker form-control inputtxt" data-live-search="true" data-style=""
                                            title="请选择区县">
                                    </select>
                                    <input type="hidden" id="provinceCode">
                                    <input type="hidden" id="cityCode">
                                    <input type="hidden" id="districtCode">
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                              <div class="form-group">
                            
                                <label for="addressDetail" class="col-lg-3 control-label"></label>
                                <div class="col-lg-8">
                                 <p class="text-info small  control-label ">*请注意安装详细地址与产品邮寄地址的不同，准确填写 *</p>
                                </div>
                               </div>
                            
                            <div class="form-group">
                            
                                <label for="addressDetail" class="col-lg-3 control-label">客户安装详细地址</label>
                                <div class="col-lg-8">
                                
                                    <input type="text" name="addressDetail"
                                           class="form-control" id="addressDetail"
                                           placeholder="客户安装详细地址" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                             <div class="form-group">
                                <label for="addressDetail" class="col-lg-3 control-label">产品邮寄详细地址</label>
                                <div class="col-lg-8">
                                    <input type="text" name="mailAddressDetail"
                                           class="form-control" id="mailAddressDetail"
                                           placeholder="产品邮寄详细地址" maxlength="200">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                            
                            <div class="form-group">
                                <label for="createRemark" class="col-lg-3 control-label">创建备注</label>

                                <div class="col-lg-8">
                                   <p class="text-info small  control-label ">重要! 其他需要备注的重要信息 *</p>
                                    <textarea type="text" name="createRemark"
                                    rows="5"
                                           class="form-control" id="createRemark"
                                           placeholder="创建备注" ></textarea>
                                    <p class="help-block"></p>
                                  
                                </div>
                                 <p class="need col-lg-1 control-label hide ">*</p>
                            </div>
                            
                            
                            <div class="form-group">
                                <label for="createRemark" class="col-lg-3 control-label">安装注意事项</label>

                                <div class="col-lg-8">
                                    <textarea type="text" name="installRemark"
                                    rows="5"
                                           class="form-control" id="installRemark"
                                           placeholder="安装注意事项" ></textarea>
                                    <p class="help-block"></p>
                                     <p class="text-info small hide  control-label ">重要! 其他需要备注的重要信息 *</p>
                                </div>
                                 <p class="need col-lg-1 control-label hide ">*</p>
                            </div>
                            
                            <div class="form-group">
                                <label for="createRemark" class="col-lg-3 control-label">客户房门信息</label>

                                <div class="col-lg-8">
                                    <textarea type="text" name="doorInfo"
                                    rows="5"
                                           class="form-control" id="doorInfo"
                                           placeholder="客户房门信息" ></textarea>
                                    <p class="help-block"></p>
                                     <p class="text-info small hide  control-label ">重要! 其他需要备注的重要信息 *</p>
                                </div>
                                 <p class="need col-lg-1 control-label hide ">*</p>
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
