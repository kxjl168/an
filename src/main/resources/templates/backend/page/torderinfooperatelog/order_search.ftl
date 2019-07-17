<div class="queryclass collaps">

    <div class='querytitle ' data-tippy-content="展开查询条件">
        <h5>查询条件 <i class="querytitlebtn fa fa-chevron-down"></i></h5>

        <hr>
    </div>

    <form class="form-inline">

        <input id="q_createTime" type="hidden">
        <input id="operateUserId" type="hidden">

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">客户姓名:</label>

            <div class="col-xs-8">
                <input id="q_customerName" type="text" name="q_customerName"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">客户手机:</label>

            <div class="col-xs-8">
                <input id="q_CustPhone" type="text" name="q_CustPhone"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">创建时间:</label>

            <div class="col-xs-8">
                <input type="text" name="startTime"
                       class="form-control inputtxt" id="startTime"
                       placeholder="开始时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">~</label>

            <div class="col-xs-8">
                <input type="text" name="endTime"
                       class="form-control inputtxt" id="endTime"
                       placeholder="结束时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">订单编号:</label>

            <div class="col-xs-8">
                <input id="q_orderNo" type="text" name="q_orderNo"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">服务类型:</label>

            <div class="col-xs-8">
                <select name="serverType" id="q_ServerType" class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0">安装</option>
                    <option value="1">维修</option>
                    <option value="2">开锁</option>
                    <option value="6">换锁</option>
                    <option value="4">测量</option>
                    <option value="5">猫眼安装</option>

                    <option value="7">工程安装</option>
                    <option value="8">工程维修</option>
                    <option value="9">其他</option>
                </select>
            </div>
        </div>

        <div class="form-group cview chide" id="companyIdView" style="display: inline-block;">
            <label for="name" class="lb_text col-xs-5 control-label">锁企名称:</label>

            <div class="col-xs-8">
                <select type="text" name="lockEnterpriseID" class="inputtxt form-control"
                        id="lockEnterpriseID" placeholder="所属锁企" maxlength="11">
                </select>
            </div>
        </div>

        <#--<div class="form-group hide">-->
            <#--<label for="name" class="lb_text col-xs-5 control-label">锁企id:</label>-->

            <#--<div class="col-xs-8">-->
                <#--<input id="q_EnterpriseId" type="text" name="q_EnterpriseId"-->
                       <#--class="form-control inputtxt" placeholder=""-->
                       <#--aria-controls="dataTables-example">-->
            <#--</div>-->
        <#--</div>-->

        <div class="form-group chide">
            <label for="name" class="lb_text col-xs-5 control-label">锁企电话:</label>

            <div class="col-xs-8">
                <input id="q_EnterprisePhone" type="text" name="q_EnterprisePhone"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group hide">
            <label for="name" class="lb_text col-xs-5 control-label">下发类型:</label>

            <div class="col-xs-8">
                <select name="q_choice" id="q_choice" class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0">总新建下发</option>
                </select>
            </div>
        </div>
        
        
          <div class="form-group chide">
            <label for="name" class="lb_text col-xs-5 control-label">锁匠姓名:</label>

            <div class="col-xs-8">
                <input id="q_lockName" type="text" name="q_lockName"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>

        <div class="form-group chide">
            <label for="name" class="lb_text col-xs-5 control-label">锁匠手机:</label>

            <div class="col-xs-8">
                <input id="q_lockPhone" type="text" name="q_lockPhone"
                       class="form-control inputtxt" placeholder=""
                       aria-controls="dataTables-example">
            </div>
        </div>
        
             <div class="form-group ">
            <label for="name" class="lb_text col-xs-5 control-label">费用类型:</label>

            <div class="col-xs-8">
               <select id="q_orderFeeType"  name="q_orderFeeType" data-style="" class=" form-control inputtxt" data-live-search="true"
                                            title="未选中任何项">
                                            <option value="" >请选择</option>
                                            	<option value="0" >标准工单</option>
                                            	<option value="1">自费工单</option>
                                    </select>
            </div>
        </div>
        <div class="form-group ">
            <label for="name" class="lb_text col-xs-5 control-label">订单状态:</label>

            <div class="col-xs-8">
                <select id="q_type"  name="q_type" data-style="" class=" form-control inputtxt" data-live-search="true"
                        title="未选中任何项">
                    <option value="" >请选择</option>
                    <option value="0">待确认</option>
                    <option value="1">待接单</option>
                    <option value="2">待作业</option>
                    <option value="3">待回访</option>
                    <option value="5">已完成</option>
                </select>
            </div>
        </div>
        

        <input type="hidden" id="q_provinceCode">
        <input type="hidden" id="q_cityCode">
        <input type="hidden" id="q_districtCode">

    </form>

    <form class="form-inline">
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">省:</label>

            <div class="col-xs-8">
                <select id="q_province" class="selectpicker form-control inputtxt" data-live-search="true"
                        data-style="">
                    <option value="">请选择省份</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">市:</label>

            <div class="col-xs-8">
                <select id="q_city" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择城市" data-style="">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">区:</label>

            <div class="col-xs-8">
                <select id="q_district" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择地区" data-style="">
                </select>
            </div>
        </div>
    </form>


    <div class='hide'>
        <h5>操作过滤</h5>
        <hr>
    </div>
    <form class="form-inline">


        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">操作时间:</label>

            <div class="col-xs-8">
                <input type="text" name="logstartTime"
                       class="form-control inputtxt" id="logstartTime"
                       placeholder="操作起始时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">~</label>

            <div class="col-xs-8">
                <input type="text" name="logendTime"
                       class="form-control inputtxt" id="logendTime"
                       placeholder="操作结束时间" readonly>
            </div>
        </div>


        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">操作类型:</label>

            <div class="col-xs-8">


                <select name="q_operlogType" id="q_operlogType" onchange="operTypeChange()"
                        class="form-control inputtxt">
                    <option value="">请选择</option>
                    <option value="0">锁企操作</option>
                    <option value="1">客服操作</option>
                    <option value="2">锁匠操作</option>

                </select>
            </div>
        </div>


        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">子类型:</label>

            <div class="col-xs-8">


                <select name="q_operlogSubType" id="q_operlogSubType" class="form-control inputtxt">
                    <option value="">请选择</option>

                </select>
            </div>
        </div>


    </form>


    <form class=" form-inline margin-top-10">


        <button type="button" id="btnQry" onclick="doSearch_item()"
                class="btn  button-primary button-rounded button-small">
            <i class="fa fa-search fa-lg"></i> <span>查询</span>
        </button>

    </form>
    <script>
        $(function () {
            tippy(".querytitle", {
                arrow: true,
                content: $(".querytitle").attr('data-tippy-content'),
                placement: 'top-start',
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective',
            });

            $(".querytitle").click(function () {

                if ($(".queryclass").hasClass("collaps")) {
                    $(".queryclass").removeClass("collaps");
                    $(".querytitle").attr("data-tippy-content", "收起查询条件");
                }
                else {
                    $(".queryclass").addClass("collaps");
                    $(".querytitle").attr("data-tippy-content", "展开查询条件");
                }

                $(".querytitle")[0]._tippy.setContent($(".querytitle").attr("data-tippy-content"));


            })


            
	    var managerType=$("#managerType2").val();// 模板中获取当前登陆用户类型
    if(managerType==2)
    {
    	$(".chide").removeClass("cview")
        $(".chide").hide();
        
    }


        })
    </script>
</div>