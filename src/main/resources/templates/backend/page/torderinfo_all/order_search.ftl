<div class="queryclass">


    <form class="form-inline">

        <input id="q_createTime" type="hidden">
       
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">客户姓名:</label>

            <div class="col-xs-7">
                <input id="q_customerName" type="text" name="q_customerName"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">客户手机:</label>

            <div class="col-xs-7">
                <input id="q_CustPhone" type="text" name="q_CustPhone"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">创建时间:</label>

            <div class="col-xs-7">
                <input type="text" name="startTime"
                       class="form-control inputtxt" id="startTime"
                       placeholder="开始时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">~</label>

            <div class="col-xs-7">
                <input type="text" name="endTime"
                       class="form-control inputtxt" id="endTime"
                       placeholder="结束时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">订单编号:</label>

            <div class="col-xs-7">
                <input id="q_orderNo" type="text" name="q_orderNo"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">服务类型:</label>

            <div class="col-xs-7">
                <select name="serverType" id="q_ServerType" class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0">安装</option>
                    <option value="1">维修</option>
                    <option value="2">开锁</option>
                    <option value="4">测量</option>
                    <option value="5">猫眼安装</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">省:</label>

            <div class="col-xs-7">
                <select id="q_province" class="selectpicker form-control inputtxt" data-live-search="true" data-style="btn-primary">
                    <option value="">请选择省份</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">市:</label>

            <div class="col-xs-7">
                <select id="q_city" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择城市" data-style="btn-primary">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">区:</label>

            <div class="col-xs-7">
                <select id="q_district" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择地区" data-style="btn-primary">
                </select>
            </div>
        </div>
        
        
          <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">锁企名称:</label>

            <div class="col-xs-7">
                <input id="q_EnterpriseName" type="text" name="q_EnterpriseName"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>
        
        
          <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">支付状态:</label>

            <div class="col-xs-7">
    
                       
                         <select name="q_finishedState" id="q_finishedState" class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0">待结账</option>
                    <option value="1">已结账</option>
                  
                </select>
            </div>
        </div>
        
        
        <input type="hidden" id="q_provinceCode">
        <input type="hidden" id="q_cityCode">
        <input type="hidden" id="q_districtCode">

    </form>


    <form class=" form-inline margin-top-10">


        <button type="button" id="btnQry" onclick="doSearch_item()"
                class="btn  button-primary button-rounded button-small">
            <i class="fa fa-search fa-lg"></i> <span>查询</span>
        </button>

    </form>

</div>