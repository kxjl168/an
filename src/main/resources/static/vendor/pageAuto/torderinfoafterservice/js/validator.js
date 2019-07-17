$(function () {
    /**
     * 添加认证
     */
    $("#mform_item_addB").bootstrapValidator({
        live: 'enabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        submitButtons: '#btnSubmit_item_editOptSave',//指定提交按钮，如果验证失败则变成disabled，
        message: '通用的验证失败消息',//好像从来没出现过
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            orderId: {
                validators: {
                    notEmpty: {
                        message: '订单编号不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '用户密码不能为空'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: '问题描述不能为空'
                    }
                }
            },
            telephone: {
                validators: {
                    notEmpty: {
                        message: '联系电话不能为空'
                    },
                    regexp: {
                        regexp: /^1([38]\d|5[0-35-9]|7[3678])\d{8}$/,
                        message: '请输入正确的手机号'
                    }
                }
            },
            nickname: {
                validators: {
                    notEmpty: {
                        message: '联系人称呼不能为空'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '联系人地址不能为空'
                    }
                }
            }
        }
    });
    /*$("#btn-test").click(function () {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
        $("#form-test").bootstrapValidator('validate');//提交验证
        if ($("#form-test").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
            alert("yes");//验证成功后的操作，如ajax
        }
    });*/



    /**
     * 编辑认证
     */
    $("#mform_item_change").bootstrapValidator({
        live: 'enabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        submitButtons: '#btnSubmit_item_editOptSave',//指定提交按钮，如果验证失败则变成disabled，
        message: '通用的验证失败消息',//好像从来没出现过
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            orderId: {
                validators: {
                    notEmpty: {
                        message: '订单编号不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '用户密码不能为空'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: '问题描述不能为空'
                    }
                }
            },
            telephone: {
                validators: {
                    notEmpty: {
                        message: '联系电话不能为空'
                    },
                    regexp: {
                        regexp: /^1([38]\d|5[0-35-9]|7[3678])\d{8}$/,
                        message: '请输入正确的手机号'
                    }
                }
            },
            nickname: {
                validators: {
                    notEmpty: {
                        message: '联系人称呼不能为空'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '联系人地址不能为空'
                    }
                }
            }
        }
    });








});
