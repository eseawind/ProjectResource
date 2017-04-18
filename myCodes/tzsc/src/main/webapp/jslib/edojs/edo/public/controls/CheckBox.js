/**
    @name Edo.controls.CheckBox
    @class
    @typeName check,checkbox
    @description 多选框
    @extend Edo.controls.Control
*/
Edo.controls.CheckBox = function(config){
    /**
        @name Edo.controls.CheckBox#checkedchange
        @event        
        @description 选中状态改变事件
        @property {Boolean} checked 是否选中
    */
    Edo.controls.CheckBox.superclass.constructor.call(this);     
};
Edo.controls.CheckBox.extend(Edo.controls.Control,{
    /**
        @name Edo.controls.CheckBox#valueField
        @property
        @default 'checked'
    */
    valueField: 'checked',    
    /**
        @name Edo.controls.CheckBox#defaultValue
        @property        
        @default false        
    */
    defaultValue: false,
    /**
        @name Edo.controls.CheckBox#autoWidth
        @property        
        @default true
    */
    autoWidth: true,
    
    /**
        @name Edo.controls.CheckBox#minWidth
        @property        
        @default 20        
    */
    minWidth: 20,   
    /**
        @name Edo.controls.CheckBox#text
        @property
        @type String
        @default ''
        @description 文本
    */     
    text: '',
    /**
        @name Edo.controls.CheckBox#checked
        @property
        @type Boolean
        @default false
        @description 是否选中状态
    */   
    checked: false,
    
    elCls: 'e-checkbox',
    checkedCls: 'e-checked', 
    overcheckedCls: 'e-checked-over',
    pressedcheckedCls: 'e-checked-pressed',    
    
    getInnerHtml: function(sb){
        if(this.checked) this.elCls += ' '+this.checkedCls;
        sb[sb.length] = '<div class="e-checked-icon"><div class="e-checked-icon-inner"></div></div><div class="e-checked-text">';
        sb[sb.length] = this.text;
        sb[sb.length] = '</div>';        
    },
    createChildren: function(el){
        Edo.controls.CheckBox.superclass.createChildren.call(this, el);        
        this.iconEl = this.el.firstChild;
        this.textEl = this.el.lastChild;
    },    
    initEvents: function(){
        if(!this.design){
            var el = this.el;
            
            Edo.util.Dom.addClassOnOver(el, this.overcheckedCls);
            Edo.util.Dom.addClassOnClick(el, this.pressedcheckedCls);
            
            this.on('click', this._onClick, this);          
        }        
        Edo.controls.CheckBox.superclass.initEvents.call(this);
    },
    _onClick: function(e){
        if(!this.enable) return;        
        
        var checked = !this.checked;
        this._setChecked(checked);
    },
    _setText: function(value){
        if(this.text !== value){
            this.text = value;
            if(this.el){
                this.textEl.innerHTML = this.text;
            }
            if(!Edo.isInt(this.width)){
                this.widthGeted = false;
            }
            this.changeProperty('text', value);
            this.relayout('text', value);
        }
    },     
    _setChecked: function(value){        
        value = Edo.toBool(value);
        if(this.checked !== value){
            this.checked = value;
            if(this.el){
                if(value){
                    Edo.util.Dom.addClass(this.el, this.checkedCls);
                }else{                
                    Edo.util.Dom.removeClass(this.el, this.checkedCls);
                }
            }
            this.fireEvent('checkedchange',{
                type: 'checkedchange',
                source: this,
                checked: value
            });
            this.changeProperty('checked', value);
        }
    }
});

Edo.controls.CheckBox.regType('checkbox', 'check');