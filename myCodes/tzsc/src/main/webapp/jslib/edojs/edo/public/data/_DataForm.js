/**
    @name Edo.data.DataForm
    @class 
    @typeName DataForm
    @description 表单数据源
    @extend Edo.core.Component
    @example 
*/ 

Edo.data.DataForm = function(data){
    Edo.data.DataForm.superclass.constructor.call(this);
    
    //change事件
    this._setConn(new Edo.data.Connection());
    
    //valid
    //invalid
}
Edo.data.DataForm.extend(Edo.core.Component, {
    componentMode: 'data',

    autoMask: true,
    
    conn: null,
    ct: null,
    
    forForm: null,
    
    _setForForm: function(v){
        if(this.forForm){
            Edo.util.Dom.un(this.forForm, 'submit', this.onFormSubmit, this);
        }
        this.forForm = Edo.getDom(v);
        Edo.util.Dom.on(this.forForm, 'submit', this.onFormSubmit, this);
    },
    onFormSubmit: function(e){        
        this.markForm();
    },    
    _setConn: function(conn){
        if(!conn) return;
        if(this.conn) {
            this.conn.un('beforeload', this._onBeforeLoad, this);
            this.conn.un('load', this._onLoad, this);
            this.conn.un('loaderror', this._onLoadError, this);
            
            this.conn.un('beforesave', this._onBeforeSave, this);
            this.conn.un('save', this._onSave, this);
            this.conn.un('saveerror', this._onSaveError, this);
        }
        
        this.conn = conn;
        
        this.conn.on('beforeload', this._onBeforeLoad, this);
        this.conn.on('load', this._onLoad, this);       
        this.conn.on('loaderror', this._onLoadError, this); 
        
        this.conn.on('beforesave', this._onBeforeSave, this);
        this.conn.on('save', this._onSave, this);
        this.conn.on('saveerror', this._onSaveError, this);
    },
    _onBeforeLoad: function(e){
        var conn = e.source;
        
        var ex = {
            type: 'beforeload',
            source: this
        };
        this.fireEvent('beforeload', ex);        
        
        if(this.ct && this.autoMask){
            this.ct.mask();
        }
    },
    _onLoad: function(e){
    
        var data = e.data;
        try{
            data = Edo.util.Json.decode(e.data);
        }catch(eee){
            
        }
        var ex = {
            type: 'load',
            source: this,
            data: data
        };
        this.fireEvent('load', ex);
        
        this.setForm(ex.data);
        
        if(this.ct && this.autoMask){
            this.ct.unmask();
        }
    },
    _onLoadError: function(e){
        var ex = {
            type: 'loaderror',
            source: this,
            code: e.code
        };
        this.fireEvent('loaderror', ex);
        
        if(this.ct && this.autoMask){
            this.ct.unmask();
        }
    },
    _onBeforeSave: function(e){
        var conn = e.source;
        
        var ex = {
            type: 'beforesave',
            source: this,
            params: this.getForm()
        };
        this.fireEvent('beforesave', ex);
        
        conn.saveParams = ex.params;
        
        if(this.ct && this.autoMask){
            this.ct.mask();
        }
    },
    _onSave: function(e){
        var ex = {
            type: 'save',
            source: this,
            data: e.data
        };
        this.fireEvent('save', ex);
        
        if(this.ct && this.autoMask){
            this.ct.unmask();
        }
    },
    _onSaveError: function(e){
        var ex = {
            type: 'saveerror',
            source: this,
            code: e.code
        };
        this.fireEvent('saveerror', ex);
        
        if(this.ct && this.autoMask){
            this.ct.unmask();
        }
    },   
    /**
        @name Edo.data.DataForm#setForm
        @function
        @param {Object} obj 键值对数据对象, 如{name: 'edojs', ...}
        @description 设置表单组件值
     */        
    setForm: function(o, value){
        if(!o) return;
        if(typeof o == 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }
        for(var id in o){
            var field = this.getField(id);
            if(field){
                var v = o[id];
                
                var e = {
                    type: 'beforesetfield',
                    field: field,
                    name: id,
                    value: v
                };
                if(this.fireEvent('beforesetfield', e) !== false){
                    
                    field.setValue(v);
                    
                    e.type = 'setfield';
                    this.fireEvent('setfield', e);
                }
            }
        }
    },    
    /**
        @name Edo.data.DataForm#getForm
        @function        
        @description 获取表单组件值
     */          
    getForm: function(name){
        var fields = [];
        if(name) {
            var field = this.getField(name);
            fields.add(field);
        }else{            
            fields = this.getFields();
        }
        var o = {};
        for(var i=0,l=fields.length; i<l; i++){
            var fd = fields[i];
            
            o[fd.name] = fd.getValue();
        }
        
        //this.fireEvent('get', e);
        
        return o;
    },
    markForm: function(){
        var fields = this.getFields();
        for(var i=0,l=fields.length; i<l; i++){
            var fd = fields[i];
            
            fd.markFormValue();
        }
    },    
    /**
        @name Edo.data.DataForm#reset
        @function        
        @description 重置表单组件值
     */     
    reset: function(name){
        var fields = [];
        if(name) {
            var field = this.getField(name);
            fields.add(field);
        }else{            
            fields = this.getFields();
        }
        for(var i=0,l=fields.length; i<l; i++){
            var fd = fields[i];            
            
            fd.resetValue();
        }
    },
    getField: function(name){
        var c = Edo.getByName(name, this.ct)[0];
        if(c && c.componentMode == 'control' && !this.isParentControl(c) && c.enableForm) return c;
    },
    getFields: function(){
        var fields = [];
        var m = Edo.managers.SystemManager;
        var all = m.all;
        var parent = this.ct;
        for(var id in all){
            var c = all[id];
            if(c.name && c.componentMode == 'control' && c.enableForm){ 
                
                if(!parent || (parent && m.isAncestor(parent, c))){
                    if(!this.isParentControl(c)){   //如果父元素没有是control的, 才可以算
                        fields[fields.length] = c;
                    }
                }
            }
        }
        return fields;
    },
    isParentControl: function(c){
        if(!c.parent) return false;
        if(c.parent.componentMode == 'control') return true;
        return this.isParentControl(c.parent);
    },
    /**
        @name Edo.data.DataForm#valid
        @function        
        @param {Boolean} all 是否验证所有组件,false是一个个验证
        @description 验证表单
     */         
    valid: function(all){
        var fields = this.getFields();
        var ret = true;
        
        var errFields = [];
        function oninvalid(e){
            errFields.add({
                errorMsg: e.errorMsg,
                field: e.source
            });
        }
        
        for(var i=0, l=fields.length; i<l; i++){
            var fd = fields[i];
            
            //验证前增加监听器
            fd.on('invalid', oninvalid, this);
            
            var showValid = fd.showValid;
            fd.showValid = false;            
            var r = fd.valid(all);
            fd.showValid = showValid;            
            
            //验证后移除监听器
            fd.un('invalid', oninvalid, this);
            
            if(!r){               
                ret = false;
                if(!all) break;
            }
        }
        if(errFields.length > 0){
            this.fireEvent('invalid', {
                type: 'invalid',
                source: this,
                errFields: errFields,
                fields: fields
            });
            errFields.each(function(fd){
                fd.field.showInvalid.defer(100, fd.field, [fd.errorMsg]);
            });
        }else{
            this.fireEvent('valid', {
                type: 'valid',
                source: this,
                fields: fields
            });
        }
        
        
        return ret;
    }
});

Edo.data.DataForm.regType('dataform');