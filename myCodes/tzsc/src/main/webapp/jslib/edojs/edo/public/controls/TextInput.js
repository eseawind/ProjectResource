/**
    @name Edo.controls.TextInput
    @class
    @typeName textinput, text
    @description 文本输入框:单行/密码/多行
    @extend Edo.controls.Control
*/
Edo.controls.TextInput = function(){
    Edo.controls.TextInput.superclass.constructor.call(this);
    
    /**
        @name Edo.controls.TextInput#beforetextchange
        @event        
        @description 文本改变前事件
        @property {String} text 文本
    */
    /**
        @name Edo.controls.TextInput#textchange
        @event        
        @description 文本改变事件
        @property {String} text 文本
    */       
    /**
        @name Edo.controls.TextInput#textfocus
        @event
        @domevent
        @description 输入框获得焦点
    */   
    /**
        @name Edo.controls.TextInput#textblur
        @event
        @domevent
        @description 输入框失去焦点
    */
};
Edo.controls.TextInput.extend(Edo.controls.Control,{
    _htmltype: 'text',
    _mode: 'text',
    
    lineHeight: true,   //true(跟realHeight一样), false,不设置, 具体值30...
    
    /**
        @name Edo.controls.TextInput#defaultWidth
        @property
        @default 100
    */
    defaultWidth: 100,        
    /**
        @name Edo.controls.TextInput#minWidth
        @property
        @default 20
    */
    minWidth: 20,
    
    defaultHeight: 21,        
    minHeight: 20,
    /**
        @name Edo.controls.TextInput#text
        @property
        @type String
        @description 文本
    */ 
    text: '',    
    /**
        @name Edo.controls.TextInput#readOnly
        @property
        @type Boolean
        @default false
        @description 是否只读,默认false
    */
    readOnly: false,
    
    /**
        @name Edo.controls.TextInput#selectOnFocus
        @property
        @type Boolean
        @default false
        @description 是否在获得焦点的时候,选择文本.默认false
    */
    selectOnFocus: false,
    
    textStyle: '',
    textCls: '',
    
    elCls: 'e-text',
    
    /**
        @name Edo.controls.TextInput#emptyText
        @property
        @type String
        @description 文本为空时的显示文本
    */ 
    emptyText: '',
    /**
        @name Edo.controls.TextInput#emptyCls
        @property
        @type String
        @default 'e-text-empty'
        @description 文本为空时的显示样式
    */ 
    emptyCls: 'e-text-empty',
    
    //disabledClass : "",
    focusClass: 'e-text-focus',        
    
    //激发el.onchange
    /**
        @name Edo.controls.TextInput#changeAction
        @property
        @type String
        @default change
        @description 文本textchange事件由什么DOM事件激发,如change/blur/keyup/keydown等
    */ 
    changeAction: 'change',
    
    focus: function(){    
        //if(this.field) this.field.focus();
        var el = this.field;
        if(this.blurTimer) {
            clearTimeout(this.blurTimer);
            this.blurTimer = null;
        }
        setTimeout(function(){
            Edo.util.Dom.focus(el);
        },100);
    },
    blur: function(){        
        var sf = this;
        this.blurTimer = setTimeout(function(){
            Edo.util.Dom.blur(sf.field);
        }, 100);
        //if(this.field) this.field.blur();
    },
    getInnerHtml: function(sb){   
         
        var w = this.getFieldWidth() - 8;//8是 (1border + 3padding) * 2        
        var h = lh = this.realHeight - 4;//4是 (1border + 1padding) * 2
        
        if(this.lineHeight && this.lineHeight !== true){
            lh = this.lineHeight;
        }
        
        var text = this.text;
//        var text = Edo.isValue(this.text) ? this.text : this.emptyText;
//        this.elCls += ' ' + (text === this.emptyText ? this.emptyCls : '');
        
        if(this._mode == 'text'){
            sb[sb.length] = '<input type="'+this._htmltype+'" ';
        }else{
            sb[sb.length] = '<textarea ';
        }
        
        if(!this.isEnable()) sb[sb.length] = ' disabled="disabled" '; 
        sb[sb.length] = ' autocomplete="off" class="e-text-field" style="height:';
        sb[sb.length] = h;
        sb[sb.length] = 'px;line-height:';
        sb[sb.length] = lh;
        sb[sb.length] = 'px;width:';
        sb[sb.length] = w;
        sb[sb.length] = 'px;';
        sb[sb.length] = this.textStyle;
        sb[sb.length] = '" ';
        
        if(this.readOnly) {
            sb[sb.length] = ' readonly="true" ';
            this.elCls += ' e-text-readonly ';
        }            
        
        if(this._mode == 'text'){
            sb[sb.length] = ' value="';
            sb[sb.length] = text;
            sb[sb.length] = '" />';
        }else{
            sb[sb.length] = '>';
            sb[sb.length] = text;
            sb[sb.length] = '</textarea>';
        }        
    },
    createChildren: function(el){        
        Edo.controls.TextInput.superclass.createChildren.call(this, el);
        this.field = this.el.firstChild;        
    },
    initEvents: function(){        
        Edo.controls.TextInput.superclass.initEvents.c(this);
        var de = Edo.util.Dom;
        var f = this.field;
        de.on(f, 'blur', this._onBlur, this);
        de.on(f, 'focus', this._onFocus, this);
        
        
        de.on(f, this.changeAction, this._onTextChange, this);        
        //是否及时change?         
    },
    _onBlur: function(e){    
        if(this.within(e)){
            return;
        }
    
        var sf = this;
        this.blurTimer = setTimeout(function(){
            Edo.util.Dom.removeClass(sf.el, sf.focusClass);        
        }, 10);
        
        e.type = 'textblur';
        e.target = this;
        this.fireEvent('textblur', e);
        
        e.type = 'blur';
        this.fireEvent('blur', e);
        
//        if(this.field){
//            if(this.field.value === this.emptyText){
//                this.field.value = '';
//                Edo.util.Dom.addClass(this.el, this.emptyCls);
//            }else{
//                Edo.util.Dom.removeClass(this.el, this.emptyCls);
//            }
//            
//        }
    },
    _onFocus: function(e){
        e.stopDefault();
        
//        if(this.blurTimer) {
//            clearTimeout(this.blurTimer);
//            this.blurTimer = null;
//        }
        
        //Edo.util.Dom.addClass(this.el, this.focusClass);  
        
        if(!this.isEnable()){            
            this.blur();            
            return;
        }
        
        var sf = this;
        setTimeout(function(){
            Edo.util.Dom.addClass(sf.el, sf.focusClass);        
        }, 1);      
        function _(){
            if(this.field) this.field.select();
        }
        if(this.selectOnFocus){
            _.defer(20, this);
        }
        
        e.type = 'textfocus';
        e.target = this;
        this.fireEvent('textfocus', e);
        
        e.type = 'focus';
        this.fireEvent('focus', e);
        
//        if(this.field){
//            if(this.field.value === this.emptyText){
//                this.field.value = '';
//                Edo.util.Dom.addClass(this.el, this.emptyCls);
//            }else{
//                Edo.util.Dom.removeClass(this.el, this.emptyCls);
//            }
//            
//        }
    },
    _onTextChange: function(e){
        this._setText(this.field.value);
    },
    _setEnable: function(value){
        Edo.controls.TextInput.superclass._setEnable.call(this, value);  
        if(this.field){
            this.field.disabled = !this.isEnable();
        }
    },
    _setText: function(value){    
        if(!Edo.isValue(value)) value = '';   
        
        if(this.text !== value){
            if(this.fireEvent('beforetextchange', {
                type: 'beforetextchange',
                source: this,
                text: value
            }) === false) {
                if(this.el) this.field.value = this.text;
                return;
            }
        
            this.text = value;      //text就是传递进来的值
            if(this.el){
//                if(!Edo.isValue(value)) {   //如果为空,则使用emptyText显示
//                    value = this.emptyText;                                        
//                }
                this.field.value = value;
//                if(value === this.emptyText){
//                    Edo.util.Dom.addClass(this.el, this.emptyCls);
//                }else{
//                    Edo.util.Dom.removeClass(this.el, this.emptyCls);
//                }
            }
            
            this.changeProperty('text', value);
            
            this.fireEvent('textchange', {
                type: 'textchange',
                source: this,
                text: this.text
            });
        }
    }, 
    _setReadOnly: function(value){
        if(this.readOnly != value){
            this.readOnly = value;
            if(this.el){
                this.field.readOnly = value;                
                if(value){
                    Edo.util.Dom.addCls(this.el, 'e-text-readonly');
                }else{
                    Edo.util.Dom.removeCls(this.el, 'e-text-readonly');
                }
            }            
        }
    },
    //
    getFieldWidth: function(){
        var w = this.realWidth;
//        if(isSafari) {
//            w -= 8;
//        }
        return w;
    },
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.TextInput.superclass.syncSize.call(this);
                
        var w = this.getFieldWidth(), h = this.realHeight, f = this.field;
        Edo.util.Dom.setSize(f, w, h-2);
        //line-height
        if(this.lineHeight){
            var h = this.lineHeight === true ? (h-4) : this.lineHeight;                       
            f.style.lineHeight = h+'px';
        }
    },     
    destroy : function(){
        Edo.util.Dom.clearEvent(this.field);            
        Edo.util.Dom.remove(this.field);
        this.field = null;
        Edo.controls.TextInput.superclass.destroy.call(this);       
    },
    //---------------验证
    clearInvalid: function(){
        Edo.controls.TextInput.superclass.clearInvalid.call(this);        
        if(this.el) Edo.util.Dom.removeClass(this.el, 'e-form-invalid');        
    },
    showInvalid: function(msg){
        Edo.controls.TextInput.superclass.showInvalid.call(this, msg);
        if(this.showValid){
            if(this.el) Edo.util.Dom.addClass(this.el, 'e-form-invalid');
            else this.cls += ' e-form-invalid';
        }
    }
//    ,   
//    //---------------编辑    
//    startEdit: function(data, x, y, w, h){
//        Edo.controls.TextInput.superclass.startEdit.apply(this, arguments);
//        //this.field.select();
//    }
});

Edo.controls.TextInput.regType('textinput', 'text');    