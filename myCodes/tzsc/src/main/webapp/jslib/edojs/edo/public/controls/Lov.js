/**
    @name Edo.controls.Lov
    @class
    @typeName lov
    @description 页面弹出框
    @extend Edo.controls.TextInput
*/
Edo.controls.Lov = function(){
    Edo.controls.Lov.superclass.constructor.call(this);
    
    this.windowConfig = {
        width: 500,
        height: 300,
        left: 'center',
        top: 'middle',
        toolbar: 'no',
        scrollbars: 'no',
        menubar: 'no',
        resizable: 'no',
        location: 'no',
        status: 'no'
    };
    this.pageConfig = {
        
    };
    
    /**
        @name Edo.controls.Lov#beforeopen
        @event        
        @description 打开页面前事件
    */    
    /**
        @name Edo.controls.Lov#open
        @event        
        @description 打开页面事件
    */    
    /**
        @name Edo.controls.Lov#close
        @event        
        @description 关闭页面事件
    */    
    /**
        @name Edo.controls.Lov#submit
        @event        
        @description 数据提交事件
        @property {Object} data 提交的数据
    */    
    this.data = {};
};
Edo.controls.Lov.extend(Edo.controls.Trigger,{  
    /**        
        @name Edo.controls.Control#data
        @property
        @type Number
        @description 数据对象
    */
    /**        
        @name Edo.controls.Control#valueField
        @property
        @type String
        @default value
        @description 值字段
    */
    valueField: 'value',
    /**        
        @name Edo.controls.Control#displayField
        @property
        @type String 
        @default text
        @description 文本字段
    */
    displayField: 'text',
    /**        
        @name Edo.controls.Control#popupWidth
        @property
        @type Number
        @description 浮动下拉框宽度
    */
    url: '',
    /**        
        @name Edo.controls.Control#params
        @property
        @type Object
        @description 页面参数对象
    */    
    params: null,
    /**        
        @name Edo.controls.Control#pageName
        @property
        @type String
        @description 页面名称
    */    
    pageName: '',
    /**        
        @name Edo.controls.Control#autoMask
        @property
        @type Boolean
        @default true
        @description 页面打开时自动遮罩
    */  
    autoMask: true,
    maskTarget: '#body',
    readOnly: true,
    elCls: 'e-text e-search',
    initEvents: function(){             
        Edo.controls.Lov.superclass.initEvents.call(this);            
        
        if(!this.design){
            this.on('trigger', this._onLovTrigger, this, 0);
        }
    },
    _onLovTrigger: function(e){
        this.open(this.pageConfig);
    },
    createPageConfigString: function(obj){
        //坐标尺寸计算
        var sb = [];
        
        var sw = window.screen.width, sh = window.screen.height - 100;
        var w = obj.width, h = obj.height;
        for(var p in obj){
            var v = obj[p];
            if(p == 'left' && v == 'center'){
                v = sw / 2 - w / 2;
            }
            if(p == 'top' && v == 'middle'){
                v = sh / 2 - h / 2;
            }
            sb.push(p+'='+v);
        }
        return sb.join(',');
    },    
    getValue: function(){
        if(this.data) return this.data[this.valueField];
        return null;
    },    
    setValue: function(v){
        this._setData(v);
    }, 
    _setData: function(v){
        if(!Edo.isValue(v)) return;
        if(v !== this.data){
            this.data = v;
            this.set('text', v[this.displayField]);
        }
    },
    /**
        @name Edo.controls.Lov#submit
        @function
        @config
        @param {Object} data 数据对象
        @description 提交页面数据(同时关闭弹出页面)
    */ 
    submit: function(data){        
        if(typeof data != 'string'){            
            var o = {data: data};
            data = Edo.util.Json.encode(o);    
            data = Edo.util.Json.decode(data).data;        
        }else{
            data = Edo.util.Json.decode(data);
        }
        
        this.setValue(data);
        
        var e = {
            type: 'submit',
            source: this,
            data: data
        };
        this.fireEvent('submit', e);
        
        this.data = data;
        
        this.close();
    },
    /**
        @name Edo.controls.Lov#open
        @function
        @config
        @param {Object} config 页面配置对象<br/>
<pre>        
{
    width: 500,
    height: 300,
    left: 'center',
    top: 'middle',
    toolbar: 'no',
    scrollbars: 'no',
    menubar: 'no',
    resizable: 'no',
    location: 'no',
    status: 'no'
}        
</pre>
        @description 提交页面数据(同时关闭弹出页面)
    */ 
    open: function(config){
    
        if(this.pager){
            this.close();
        }
        
        var e = {
            type: 'beforeopen',
            source: this
        };
        if(this.fireEvent('beforeopen', e) === false) return false;
        
        if(this.autoMask){
            var obj = (this.maskTarget == '#body' || !this.maskTarget) ? document.body : this.maskTarget;
            if(Edo.type(obj) == 'element'){
                Edo.util.Dom.mask(obj);
            }else{
                obj.mask();
            }
        }
        var config = Edo.applyIf(this.pageConfig, this.windowConfig);
        var cstr = this.createPageConfigString(config);
        
        var url = Edo.urlEncode(this.params, this.url.indexOf('?') == -1 ? this.url+'?' : this.url);        
        
        this.pager = window.open(url, this.pageName, cstr);
        this.pager.focus();
        var sf = this;
        
        window.submitPagerData = function(data){
            sf.submit(data);
        }
        
        setTimeout(function(){
            Edo.util.Dom.on(sf.pager, 'unload',sf.onPageUnload, sf);
        }, this.deferBindUnload);
        
        this.startFocus();
        
        e.type = 'open';
        e.pager = this.pager;
        this.fireEvent('open', e);
    },
    deferBindUnload: 800,
    /**
        @name Edo.controls.Lov#close
        @function        
        @description 关闭页面
    */     
    close: function(){
    
        if(this.pager){
            this.pager.close();
            var obj = this.maskTarget == '#body' ? document.body : this.maskTarget;
            if(Edo.type(obj) == 'element'){
                Edo.util.Dom.unmask(obj);
            }else{
                obj.unmask();
            }
        }
        
        Edo.util.Dom.un(this.pager, 'unload',this.onPageUnload, this);
        this.stopFocus();
        this.pager = null;
        
        this.fireEvent('close', {
            type: 'close',
            source: this
        });
    },
    onWindowFocus: function(e){
        try{
            Edo.util.Dom.focus(this.pager);        
        }catch(e){
            
        }
    },
    onPageUnload: function(e){        
        this.close();
    },
    startFocus: function(){
        this.stopFocus();        
        Edo.util.Dom.on(window, 'focus', this.onWindowFocus, this);
    },
    stopFocus: function(){            
        Edo.util.Dom.un(window, 'focus', this.onWindowFocus, this);
    },
    destroy : function(){        
        this.close();
        
        Edo.controls.Lov.superclass.destroy.call(this);       
    }    
});

Edo.controls.Lov.regType('lov');    