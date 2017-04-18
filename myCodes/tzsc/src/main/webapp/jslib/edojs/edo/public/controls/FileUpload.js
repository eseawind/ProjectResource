/**
    @name Edo.controls.FileUpload
    @class
    @typeName FileUpload
    @description flash对象
    @extend Edo.controls.Control
*/

Edo.controls.FileUpload = function(){
    if(typeof SWFUpload == 'undefined'){
        throw new Error("请引入swfupload.js");
    }
    /**
        @name Edo.controls.FileUpload#filequeued
        @event        
        @description 文件选择成功事件
        @property {Object} upload 文件上传对象
        @property {String} filename 文件名
        @property {Object} file 文件对象
    */
    /**
        @name Edo.controls.FileUpload#filequeueerror
        @event        
        @description 文件选择出错事件
        @property {Object} upload 文件上传对象
        @property {Object} file 文件对象
        @property {Object} errorCode 错误码        
        @property {String} message 错误描述
    */
    /**
        @name Edo.controls.FileUpload#filestart
        @event        
        @description 上传开始事件
        @property {Object} upload 文件上传对象
    */
    /**
        @name Edo.controls.FileUpload#fileerror
        @event        
        @description 上传失败事件
        @property {Object} upload 文件上传对象
    */
    /**
        @name Edo.controls.FileUpload#filesuccess
        @event        
        @description 上传成功事件
        @property {Object} upload 文件上传对象
        @property {Object} file
        @property {String} serverData 返回的数据字符串
    */
    Edo.controls.FileUpload.superclass.constructor.call(this);
}
Edo.controls.FileUpload.extend(Edo.controls.Control,{    
    /**
        @name Edo.controls.FileUpload#swfUploadConfig
        @property
        @type Object
        @description swfupload配置对象(请参考标准swfupload)   
    */
    swfUploadConfig: null,
    /**
        @name Edo.controls.FileUpload#buttonWidth
        @property
        @type Number
        @default 69
        @description 上传按钮宽度   
    */
    buttonWidth: 69,
    /**
        @name Edo.controls.FileUpload#autoClear
        @property
        @type Boolean
        @default true
        @description 上传后自动清空文本
    */
    autoClear: true,
    /**
        @name Edo.controls.FileUpload#textVisible
        @property
        @type Boolean
        @default true
        @description 是否显示文本
    */
    textVisible: true,
    
    elCls: 'e-fileupload e-div',
     
    getInnerHtml: function(sb){
        var id = this.id+"$place";
        var inputid = this.id+"$input";
        var w = this.realWidth - this.buttonWidth;
        sb[sb.length] = '<input id="'+inputid+'" readOnly type="text" class="e-fileupload-text" value="" style="width:'+w+'px;"> <span id="'+id+'"></span>';
    },
    createChildren: function(){
        Edo.controls.FileUpload.superclass.createChildren.a(this, arguments);
        
        var id = this.id+"$place";
        
        var c = Edo.apply({
			//upload_url: "upload.aspx",
			//file_post_name: "resume_file",
			//flash_url : "../../source/res/swfupload.swf",
			
			button_image_url : "XPButtonUploadText_61x22.png",
			//button_placeholder_id : "spanButtonPlaceholder",
			button_width: 61,
			button_height: 22,
            
			// Flash file settings
			file_size_limit : "64",
			file_types : "*.jpg;*.gif;*.png;*.bmp",
			file_types_description : "All Files",
			file_upload_limit : "0",
			file_queue_limit : "0",
			
			file_queued_handler : this._onFileQueued.bind(this),
			file_queue_error_handler: this._onFileQueueError.bind(this),
			
			upload_start_handler: this._onFileStart.bind(this),
			upload_error_handler: this._onFileError.bind(this),
			upload_success_handler: this._onFileSuccess.bind(this)			
        }, this.swfUploadConfig);
        c.button_placeholder_id = id;
        
        this.upload = new SWFUpload(c);
        
        var inputid = this.id+"$input";                
        this.inputField = document.getElementById(inputid);
    },
    _onFileQueued: function(file){
        var txt = this.inputField;
        
        var txtFileName = txt.value;
        if(txtFileName && txtFileName != file.name){
            //this.upload.cancelUpload();    
        }
        
        txt.value = file.name;
        
        var e = {
            type: 'filequeued',
            source: this,
            upload: this.upload,
            filename: file.name,
            file: file
        };
        this.fireEvent('filequeued', e);
    },
    _onFileQueueError: function(file, errorCode, message){
        var e = {
            type: 'filequeueerror',
            source: this,
            upload: this.upload,            
            file: file,
            errorCode: errorCode,
            message: message
        };
        this.fireEvent('filequeueerror', e);
    },
    _onFileStart: function(){
        var e = {
            type: 'filestart',
            source: this,
            upload: this.upload
        };
        this.fireEvent('filestart', e);
    },
    _onFileError: function(file, errorCode, message){
        var e = {
            type: 'fileerror',
            source: this,
            upload: this.upload,            
            file: file,
            errorCode: errorCode,
            message: message
        };
        this.fireEvent('fileerror', e);
    },
    _onFileSuccess: function(file, serverData){
        if(this.autoClear) this.inputField.value = '';
    
        var e = {
            type: 'filesuccess',
            source: this,
            upload: this.upload,            
            file: file,
            serverData: serverData
        };
        this.fireEvent('filesuccess', e);
    },
    syncSize: function(){
        Edo.controls.FileUpload.superclass.syncSize.call(this);
        
        if(this.textVisible){
            var w = this.realWidth - this.buttonWidth;
            Edo.util.Dom.setWidth(this.inputField, w);
            this.inputField.style.display = '';
        }else{
            
            this.inputField.style.display = 'none';
        }
    }
});

Edo.controls.FileUpload.regType('fileupload');