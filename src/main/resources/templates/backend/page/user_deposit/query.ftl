<div class='querytitle ' data-tippy-content="展开查询条件">
    <h5>查询条件 <i class="querytitlebtn fa fa-chevron-down"></i></h5>

    <hr>
</div>


<form class="form-inline">


    <div class="form-group large">
        <label for="name" class="lb_text col-xs-3 control-label">提现锁匠:</label>

        <div class="col-xs-9">
            <select id="q_userId" type="text" name="q_userId"
                    class="form-control " placeholder=""
                    aria-controls="dataTables-example"></select>
        </div>
    </div>
    
    </form>
    <form class="form-inline">

    <div class="form-group large">
        <label for="name" class="lb_text col-xs-3 control-label">提现状态:</label>
        <div class="col-xs-9">
            <select name="depositState" id="depositState" class="form-control inputtxt">
                <option value="" selected="selected">全部</option>
                <option value="0" class=""   >待审核</option>
                <option  value="1" >待打款</option>
                <option class="hide" value="2">审核不通过</option>
                <option value="3">打款成功</option>
                <option class="hide" value="4">打款失败</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="name" class="lb_text col-xs-4 control-label">完成时间:</label>

        <div class="col-xs-8">
            <input type="text" name="startTime"
                   class="form-control inputtxt" id="startTime"
                   placeholder="开始时间" readonly>
        </div>
    </div>

    <div class="form-group">
        <label for="name" class="lb_text col-xs-4 control-label">~</label>

        <div class="col-xs-8">
            <input type="text" name="endTime"
                   class="form-control inputtxt" id="endTime"
                   placeholder="结束时间" readonly>
        </div>
    </div>

    <div class="form-group hide">
        <label for="name" class="lb_text col-xs-5 control-label">手机号:</label>

        <div class="col-xs-7">
            <input id="q_phone" type="text" name="q_phone"
                   class="form-control inputtxt" placeholder=""
                   aria-controls="dataTables-example">
        </div>
    </div>


</form>


<input type="hidden" id="q_provinceCode_query">
<input type="hidden" id="q_cityCode_query">
<input type="hidden" id="q_districtCode_query">

<form class="form-inline">


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


    })
</script>
