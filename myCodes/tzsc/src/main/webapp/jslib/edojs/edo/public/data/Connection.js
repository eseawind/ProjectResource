/**
    @name Edo.data.Connection
    @private
    @class 
    @typeName Connection
    @description 数据连接器(用于服务端数据交互)
    @extend Edo.core.Component
    @example 
*/ 
Edo.data.Connection = function(data){
    Edo.data.Connection.superclass.constructor.call(this);        
    
}
Edo.data.Connection.extend(Edo.core.Component, {
    
    loadUrl: '',
    saveUrl: '',
    method: 'post',
    sync: true,
    
    load: function(url, params, callback){     
        if(url !== null) this.loadUrl = url;
        this.loadCallback = callback;
        
        if(this.loadAjax) {
            this.loadAjax.abort();
        }
        
        this.fireEvent('beforeload', {
            type: 'beforeload',
            source: this
        });
        if(params) this.loadParams = params;
        this.loadAjax = this.doConnection(this.loadUrl, this.loadParams, this.onLoadSuccess.bind(this), this.onLoadFail.bind(this));
    },
    save: function(url, params, callback){
        if(url !== null) this.saveUrl = url;
        this.saveCallback = callback;
        
        if(this.saveAjax) {
            this.saveAjax.abort();
        }
        
        this.fireEvent('beforesave', {
            type: 'beforesave',
            source: this
        });
        if(params) this.saveParams = params;
        this.saveAjax = this.doConnection(this.saveUrl, this.saveParams, this.onSaveSuccess.bind(this), this.onSaveFail.bind(this));        
    },
    doConnection: function(url, params, success, fail){
        var ajax = Edo.util.Ajax.request({
            url: url,
            type: this.method,
            sync: this.sync,
            params: params,
            onSuccess: success,
            onFail: fail
        });
        return ajax;
    },
    onLoadSuccess: function(text){
        this.loadAjax = null;
        this.fireEvent('load', {
            type: 'load',
            source: this,
            data: text
        });
        if(this.loadCallback) this.loadCallback(text);
        
        //this.loadParams = null;
    },
    onLoadFail: function(code){
        this.loadAjax = null;
        this.fireEvent('loaderror',{
            type: 'loaderror',
            source: this,
            code: code
        });
    },
    onSaveSuccess: function(text){
        this.saveAjax = null;
        this.fireEvent('save', {
            type: 'save',
            source: this,
            data: text
        });
        if(this.saveCallback) this.saveCallback(text);
        
        //this.saveParams = null;
    },
    onSaveFail: function(code){
        this.saveAjax = null;
        this.fireEvent('saveerror',{
            type: 'saveerror',
            source: this,
            code: code
        });
        //this.saveParams = null;
    }
});

Edo.data.Connection.regType('connection', 'conn');