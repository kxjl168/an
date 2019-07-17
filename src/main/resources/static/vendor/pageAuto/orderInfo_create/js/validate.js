/**
 * 创建的表单校验
 */
function initCreateFormValidate() {
    $("#mform_item").bootstrapValidator({
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
                  //  notEmpty: {
                  //      message: '不能为空'
                  //  }
                }
            },
            companyId: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            
            
            lockNum: {
                validators: {
                    notEmpty: {
                        message: '请填写锁数量'
                    },
                    callback: {
                        message: '输入的内容须为1-10的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                        	
                        	if(!/\d+/.test(value))
                        		return false;
                        	
                        	
                        	
                        	
                        	try {
                        		  if (parseInt( value) >10) {
                                      return false
                                  }
                        		  if (parseInt( value) <1) {
                                      return false
                                  }
							} catch (e) {
								return false;
							}
                          
                            return true
                        }
                    }
                }
            },
            productId: {
                validators: {
                    notEmpty: {
                        message: '请选择产品'
                    }
                }
            },

            customerName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于50字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 50) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },

            customerPhone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        message: '请输入正确的手机号',
                        regexp : "^1(3|4|5|7|8|9)\\d{9}$"
                    }
                }
            },
            customerPhone2: {
                validators: {
                   
                    regexp: {
                        message: '请输入正确的手机号',
                        regexp : "^1(3|4|5|7|8|9)\\d{9}$"
                    }
                }
            },
            createRemark: {
                validators : {
                	/* notEmpty: {
                         message: '不能为空'
                     },*/
                    callback: {
                        message: '输入的内容长度须小于400字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 400) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            
            doorInfo: {
                validators : {
                	/* notEmpty: {
                         message: '不能为空'
                     },*/
                    callback: {
                        message: '输入的内容长度须小于400字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 400) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            installRemark: {
                validators : {
                	/* notEmpty: {
                         message: '不能为空'
                     },*/
                    callback: {
                        message: '输入的内容长度须小于400字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 400) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },

            addressDetail: {
                validators: {
                   /* notEmpty: {
                        message: '不能为空'
                    },*/
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            
                            return true
                        }
                    }
                }
            },
            mailAddressDetail: {
                validators: {
                   /* notEmpty: {
                        message: '不能为空'
                    },*/
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
        }
    });
}


function checknumber(String) {
    var Letters = "1234567890";
    var i;
    var c;
    for (i = 0; i < String.length; i++) {
        c = String.charAt(i);
        if (Letters.indexOf(c) == -1) {
            return true;
        }
    }
    return false;
}

/**
 * 创建工单导入验证
 */
function initImportFormValidate() {
    $("#importModalForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            excelFile: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            }
        }
    });
}

/**
 * 更新的表单校验
 */
function initUpdateFormValidate() {
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
                  //  notEmpty: {
                  //      message: '不能为空'
                  //  }
                }
            },

            customerName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于50字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 50) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },

            customerPhone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        message: '请输入正确的手机号',
                        regexp : "^1(3|4|5|7|8|9)\\d{9}$"
                    }
                }
            },

            addressDetail: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            createRemark: {
                validators : {
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            }
        }
    });
}