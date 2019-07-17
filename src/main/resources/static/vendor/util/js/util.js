function sendRequest(url, parameter, callback) {
    $.ajax({
        type: "post",
        url: url,
        async: false,
        dataType: "json",
        data: parameter,
        success : function (data) {
            callback(data)
        }
    })
}

function sendMultiRequest(url, parameter, callback) {
    $.ajax({
        type: "post",
        url: url,
        async: false,
        dataType: "json",
        data: parameter,
        processData: false,
        contentType: false,
        success : function (data) {
            callback(data)
        }
    })
}

function initFileInput() {
    $("#excelFile").fileinput({
        language : "zh",//设置语言
        showCaption: true,//是否显示标题
        showUpload: false, //是否显示上传按钮
        allowedFileExtensions: ["xls","xlsx"], //接收的文件后缀
        maxFileCount: 1,//最大上传文件数限制
        maxFileSize: 1000,//最大上传文件大小
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        allowedPreviewTypes: null,
        previewFileIconSettings: {
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
        },
        previewFileExtSettings: {
            'doc': function(ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function(ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function(ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function(ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function(ext) {
                return ext.match(/(php|js|css|htm|html)$/i);
            },
            'txt': function(ext) {
                return ext.match(/(txt|ini|md)$/i);
            },
            'mov': function(ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function(ext) {
                return ext.match(/(mp3|wav)$/i);
            },
        }
    });
}