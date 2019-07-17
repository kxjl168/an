$(function () {
    /**
     * 选项认证
     */
    $("#mform_item_opt").bootstrapValidator({
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
            optTitle: {
                validators: {
                    notEmpty: {
                        message: '选项描述不能为空'
                    }
                }
            },
            optSortstring: {
                validators: {
                    notEmpty: {
                        message: '排序号不能为空'
                    },
                    digits: {
                        message: '排序号只能包含数字'
                    }
                }
            },
            optScore: {
                validators: {
                    notEmpty: {
                        message: '分数不能为空'
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
     * 问卷认证
     */
    $("#myModal_item_add").bootstrapValidator({
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
            questionTitle: {
                validators: {
                    notEmpty: {
                        message: '问卷名不能为空'
                    }
                }
            }
        }
    });


    /**
     * 题目认证
     */
    $("#myModal_item_edit").bootstrapValidator({
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
            qusTitle: {
                validators: {
                    notEmpty: {
                        message: '题目名不能为空'
                    }
                }
            },
            questionSortstring: {
                validators: {
                    notEmpty: {
                        message: '排序号不能为空'
                    }
                }
            }
        }
    });



});
