function updateFormValidator() {
    $("#updateOrderInfoForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            appointmentTime: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            customerName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            customerPhone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            addressDetail: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
        }
    });
}
