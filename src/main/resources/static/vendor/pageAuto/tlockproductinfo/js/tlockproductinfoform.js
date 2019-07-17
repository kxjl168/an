function isProductinfoAvailable(obj) {
    var proType=$('#proType').val();
    var oldProType=$('#oldProType').val();
    if (oldProType == proType){
        return ;
    }
    if(obj.val().length>0){
        $.ajax({
            type: "post",
            url: getRootPath() + "/manager/tlockproductinfo/checkProductInfo",
            data: {proType:proType},
            success: function (msg) {
                if (true== msg) {
                    $("#proTypeMessage").html("该产品标识已存在!");
                    $('#proTypeMessage').show();
                } else {
                    $("#proTypeMessage").html("");
                    $("#proTypeMessage").hide();
                }
            },
            error: function () {
                alert("调用异常");
            }
        });
    }
    else {
        $('#proTypeMessage').hide();
    }
}
