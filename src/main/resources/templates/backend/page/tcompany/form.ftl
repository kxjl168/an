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
                    <span id="myModal_item_title">添加</span> 合伙人
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
                                    <input type="text" name="companyName"
                                           class="form-control" id="companyName"
                                           placeholder="名称" maxlength="30">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">电话</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyPhone"
                                           class="form-control" id="companyPhone"
                                           placeholder="电话" maxlength="15">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">类型</label>
                                <div class="col-lg-9">
                                    合伙人 <input type="radio" name="companyType" checked value="0"> &nbsp;
                                    代理人 <input type="radio" name="companyType" value="1"> &nbsp;
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="name" class="col-lg-3 control-label">所在地</label>
                                <div class="col-lg-9">
                                   <!--  <div id="distpicker" data-toggle="distpicker" class="in-line selectdist "> -->
                                       
                                                 <select id="province1" name="province1" class="selectpicker form-control inputtxt" data-live-search="true" data-style="">
                    <option value="">请选择省份</option>
                </select>
                   <select id="city1" name="city1" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择城市" data-style="">
                </select>
                   <select id="district1" name="district1" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择地区" data-style="">
                </select>
                <input type="hidden" id="q_provinceCode">
        <input type="hidden" id="q_cityCode">
        <input type="hidden" id="q_districtCode"> 
                                                
                                           <!--  <select class="form-control" id="province" name="province"
                                                data-province="---- 选择省 ----">     
                                        </select> <select class="form-control" id="city" name="city"
                                                          data-city="---- 选择市 ----"></select>
                                        <select class="form-control" id="district" name="district"
                                                data-district="---- 选择区 ----"></select>  -->
                                        <input type="hidden" id="areaCode" name="areaCode">
                                   <!--  </div> -->
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need hide col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyAddressDetail"
                                           class="form-control" id="companyAddressDetail"
                                           placeholder="详细地址" maxlength="30">
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
                    <button type="button" class="btn btn-primary" id="btnSubmit_item">
                        提交更改
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
