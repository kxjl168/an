/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */


CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config
	
	
	
	
    // var now = new Date();
    //   editor.insertHtml( 'The current date and time is: <em>' + now.toString() + '</em>' );
   
	//config.filebrowserImageUploadUrl = '/UploadCKFile.action';
	config.filebrowserImageUploadUrl = getRPath()+'/UploadCKFile.action';
   
	//config.extraPlugins= 'codesnippet,colorbutton,font,justify,print,tableresize,pastefromword,liststyle',
	
	var plugins="codesnippet,colorbutton,font,liststyle,justify,print,tableresize,pastefromword,copyformatting";
	plugins="";
	if(typeof( CKEDITOR.morePluginnames)!="undefined")
	config.extraPlugins=plugins+","+ CKEDITOR.morePluginnames;
	else
		config.extraPlugins=plugins;
	
	//移除默认的上传图片组件
	if(typeof( CKEDITOR.removePlugins)!="undefined")
	config.removePlugins = CKEDITOR.removePlugins;
	
	//允许输入class等
	config.allowedContent = false;

	config.codeSnippet_theme= 'obsidian';
	config.height= 356;
	
	config.toolbarCanCollapse=false;
	
	config.toolbarGroups = [
		{ name: 'others', groups: [ 'others' ] },
		/*
	                		{ name: 'links', groups: [ 'links' ] },
	                		{ name: 'forms', groups: [ 'forms' ] },
	                		{ name: 'others', groups: [ 'others' ] },
	                		{ name: 'insert', groups: [ 'insert' ] },
	                		{ name: 'colors', groups: [ 'colors' ] },
	                		{ name: 'styles', groups: [ 'styles' ] },
	                		'/',
	                		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	                		{ name: 'paragraph', groups: [ 'list', 'indent', 'align', 'blocks', 'bidi', 'paragraph' ] },
	                		{ name: 'about', groups: [ 'about' ] },
	                		{ name: 'tools', groups: [ 'tools' ] },
	                		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] }*/
	                	];

	                	config.removeButtons = 'Subscript,Superscript,Indent,Outdent,Print,About';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';
	
	

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
};


//modal 控件冲突处理
$.fn.modal.Constructor.prototype.enforceFocus = function() {
    modal_this = this;
    $(document).on('focusin.modal', function (e) {
        if (modal_this.$element[0] !== e.target && !modal_this.$element.has(e.target).length
            && !$(e.target.parentNode).hasClass('cke_dialog_ui_input_select')
            && !$(e.target.parentNode).hasClass('cke_dialog_ui_input_text')
            && !$(e.target.parentNode).hasClass('cke_dialog_ui_input_textarea')
                
        ) {
            modal_this.$element.focus()
        }
        
      // $(".cke_source").focus();
    })
};
