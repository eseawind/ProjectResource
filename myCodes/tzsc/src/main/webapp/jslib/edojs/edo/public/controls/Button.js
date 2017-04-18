/**
    @name Edo.controls.Button
    @class
    @typeName button
    @description 按钮
    @extend Edo.controls.Control
*/

Edo.controls.Button = function(){    
    /**
         @name Edo.controls.Button#toggle
         @event
         @description 当enableToggle为true时,点击按钮将会激发此事件
         @property {Boolean} pressed 是否按住
    */
    
    /**
         @name Edo.controls.Button#arrowclick
         @event
         @domevent
         @description 当设置了arrowMode后,点击arrow区域,就会激发此事件         
    */
    
    //arrowclick
    Edo.controls.Button.superclass.constructor.call(this);
}
Edo.controls.Button.extend(Edo.controls.Control,{
    enableForm: false,

    simpleButton: false,
    
    autoWidth: true,
    autoHeight: true,
    /**
        @name Edo.controls.Button#defaultWidth
        @property        
        @default 16
    */
    defaultWidth: 18,
    /**
        @name Edo.controls.Button#defaultHeight
        @property        
        @default 20
    */
    defaultHeight: 20,
    /**
        @name Edo.controls.Button#minWidth
        @property        
        @default 16
    */
    minWidth: 20,
    /**
        @name Edo.controls.Button#minHeight
        @property        
        @default 18
    */
    minHeight: 18,
    /**
        @name Edo.controls.Button#showMenu
        @property
        @type Boolean
        @default true
        @description 显示菜单
    */ 
    showMenu: true,
    /**
        @name Edo.controls.Button#popupWidth
        @property        
        @default 100
    */
    popupWidth: 100,
    
    //widthGeted: false,//当width为auto的时候,是不是要获取dom的宽度
    /**
        @name Edo.controls.Button#text
        @property
        @type String
        @description 按钮文本
    */ 
    text: '',
    /**
        @name Edo.controls.Button#icon
        @property
        @type String
        @description 按钮图标
    */ 
    icon: '',
    /**
        @name Edo.controls.Button#iconAlign
        @property
        @type String
        @description 按钮图标位置:left,top,right,bottom
    */ 
    iconAlign: 'left',
    /**
        @name Edo.controls.Button#arrowMode
        @property
        @type String
        @description 按钮arrow模式,用于更丰富的按钮效果:menu, split, close
    */
    arrowMode: '',   //menu, split, close
    /**
        @name Edo.controls.Button#arrowAlign
        @property
        @type String
        @description 按钮arrow位置:left,top,right,bottom
    */
    arrowAlign: 'right',
    
    //使用enableClose: true, 用一个占位的div来做close trigger
    
    autoClose: true,    //如果arrowMode为close,并且autoClose:true,则点击arrow区域,自动destroy此组件
    
    /**
        @name Edo.controls.Button#pressed
        @property
        @type Boolean
        @description 按钮的toggle状态
    */
    pressed: false,
    
    overCls: 'e-btn-over',
    pressedCls: 'e-btn-click',
    
    togglCls: 'e-btn-pressed',
    enableToggle: false,
    
    elCls: 'e-btn',
    
    _onMouseEvent: function(e){                
        if(!this.isEnable()) return;        
        e.source = this;
        if(!this.design){
        
            if(e.type == 'click'){
                var outer = e.target.firstChild;
                if(outer && outer.nodeType == 3){
                    outer = null;
                }
                
                if(this.arrowMode && this.arrowMode != 'menu' && Edo.util.Dom.hasClass(e.target, 'e-btn-arrow')){
                    e.type = 'arrowclick';
                    this.fireEvent('arrowclick', e);                    
                    return;
                }
            }
            
            this.fireEvent(e.type, e);
        }
    },  
    createPopup: function(){
        Edo.controls.Button.superclass.createPopup.a(this, arguments);        
        this.menu = this.popupCt;
    },
    showPopup: function(){
        this._setPressed(true);
        Edo.controls.Button.superclass.showPopup.a(this, arguments);
    },
    hidePopup: function(){
        Edo.controls.Button.superclass.hidePopup.a(this, arguments);
        
        this._setPressed(false);
        
        if(this.menu && this.menu.children){
            this.menu.children.each(function(o){
                if(o.type == 'button' && o.rendered){
                    o.hidePopup();
                }
            });
        }
    },
    getClass: function(){
        var s = '';
        if(this.icon){
            if(this.text) {
                s += ' e-btn-icontext';
                s += ' e-btn-icon-align-' + this.iconAlign;
            }                
            else s += ' e-btn-icon';
        }
        this.getArrowCls();
        s += ' ' + this.arrowCls;
        
        
        if(this.pressed) s += ' '+this.togglCls;
        
        return s;
    },  
    htmlType: 'a',
    getInnerHtml: function(sb){
    //if(this.text == '取消') debugger 
        if(this.simpleButton) return this.text || '';
        
        this.elCls += this.getClass();        
        var w = Edo.isValue(this.realWidth) ? this.realWidth + 'px' : 'auto';
        var h = Edo.isValue(this.realHeight) ? this.realHeight + 'px' : 'auto';
        
        var text = this.text || '&nbsp;';
        sb[sb.length] = '<span class="e-btn-text '+this.icon+'">'+text+'</span>';        
        if(this.arrowMode){
            sb[sb.length] = '<span class="e-btn-arrow"></span>';        
        }
    },
    createChildren: function(el){
        Edo.controls.Button.superclass.createChildren.call(this, el);
        
        if(!this.design){
            this.el.href = 'javascript:;';            
        }
        this.el.hideFocus = true;
        
        if(this.simpleButton) return '';        

        this.field = this.el.firstChild;
        if(this.splitMode == 'split') this.splitEl = this.el.lastChild;
        
        
        //this.el.onmousedown = 'return false';
        //this.el.href = '#';
        
    },
    initEvents: function(){
        if(!this.design){
            var el = this.el;
            //if(this.simpleButton) el = this.el;
            Edo.util.Dom.addClassOnClick(el, this.pressedCls);
            //Edo.util.Dom.addClassOnOver(el, this.overCls);
            
            this.on('click', this._onClick, this, 0);
            this.on('arrowclick', this._onArrowClick, this, 0);                     
        }
        
        Edo.controls.Button.superclass.initEvents.call(this);
    },
    _onClick: function(e){
        if(this.enableToggle){        
            var pressed = !this.pressed;
            this._setPressed(pressed);
        }
        if(this.popupDisplayed){
            this.hidePopup();
        }else if(this.showMenu && this.menu){
            this.showPopup();
            this.menu = this.popupCt;
            
            //this._setPressed(true);
        }
    },
    _onArrowClick: function(e){    
        if(this.arrowMode == 'close' && this.autoClose){
            this.destroy();
        }
        
        if(this.popupDisplayed){
            this.hidePopup();
        }else if(this.arrowMode == 'split' && this.showMenu && this.menu){            
            this.showPopup();
            this.menu = this.popupCt;
            
            this._setPressed(true);        
        }
    },
    getArrowCls: function(){
        var ac = this.arrowCls;
        this.arrowCls = '';
        if(this.arrowMode == 'menu') this.arrowCls = 'e-btn-arrow-' + this.arrowAlign;
        else if(this.arrowMode == 'split') this.arrowCls = 'e-btn-split-' + this.arrowAlign;
        else if(this.arrowMode == 'close') this.arrowCls = 'e-btn-close-' + this.arrowAlign;
        return this.arrowCls;
    },
    _setArrowMode: function(value){
        if(this.arrowMode != value){
            this.arrowMode = value;
            this.getArrowCls();
        }
    },
    _setArrowAlign: function(value){
        if(this.arrowAlign != value){
            this.arrowAlign = value;
            this.getArrowCls();
        }
    },
    _setMenu: function(value){
        if(this.popupCt) this.popupCt.parent.remove(this.popupCt);
        if(value instanceof Array) value = {
            type: 'menu',
            shadow: this.popupShadow,
            autoHide: true,
            children: value
        }
        this.menu = this.popupCt = value;
    },
    
    _setOverCls: function(value){
        if(this.overCls != value){
            var old = this.overCls;
            this.overCls = value;
            if(this.el){
                Edo.util.Dom.removeClass(this.el, old);
                this.el.overCls = value;
            }
            this.changeProperty('overcls', value);
        }
    },
    _setPressedCls: function(value){        
        if(this.pressedCls != value){
            var old = this.pressedCls;
            this.pressedCls = value;
            if(this.el){
                Edo.util.Dom.removeClass(this.el, old);
                this.el.clickCls = value;    
            }
            this.changeProperty('pressedcls', value);
        }
    },
   
    _setWidth: function(w){
        if(!Edo.isInt(w) && this.width != w){            
            this.widthGeted = false;//重新获得
        }
        Edo.controls.Button.superclass._setWidth.call(this, w);
    },
    addCls: function(cls){
        Edo.controls.Button.superclass.addCls.call(this, cls);
        if(!Edo.isInt(this.width)){
            this.widthGeted = false;//重新获得                        
            if(this.el) {                    
                this.el.style.width = 'auto';
            }
        }        
    },
    removeCls: function(cls){
        Edo.controls.Button.superclass.removeCls.call(this, cls);
        if(!Edo.isInt(this.width)){
            this.widthGeted = false;//重新获得
            if(this.el) {                    
                this.el.style.width = 'auto';
            }
        }
        
    },
    _setText: function(value){
        if(this.text != value){            
            this.text = value;
            if(!this.simpleButton){
                if(this.el){
                    this.field.innerHTML = value;
                    //this.field.textContent = value;
                }
            }else{
                
            }
            if(!Edo.isInt(this.width)){
                this.widthGeted = false;//重新获得
                if(this.el) {                    
                    this.el.style.width = 'auto';
                    //Edo.util.Dom.repaint(this.el);
                }
            }
            this.changeProperty('text', value);
            if(!Edo.isInt(this.width)){
                this.relayout('text', value);
            }
        }
    }, 
    _setIcon: function(value){
        if(this.icon != value){
            if(this.el){
                Edo.util.Dom.removeClass(this.field, this.icon);
                Edo.util.Dom.addClass(this.field, value);
            }
            this.icon = value;
        }
    },
    _setPressed: function(value){        
        value = Edo.toBool(value);
        if(this.pressed != value){
            this.pressed = Edo.toBool(value);
            if(this.el){
                if(this.pressed){
                    Edo.util.Dom.addClass(this.el, this.togglCls);
                }else{
                    Edo.util.Dom.removeClass(this.el, this.togglCls);
                }
            }
            this.fireEvent('toggle', {
                type: 'toggle',
                source: this,
                pressed: value
            });
            
//            if(!Edo.isInt(this.width)){
//                this.widthGeted = false;//重新获得
//                if(this.table) {                    
//                    this.table.style.width = 'auto';
//                    Edo.util.Dom.repaint(this.table);
//                }
//            }
//            this.changeProperty('pressed', value);
//            if(!Edo.isInt(this.width)){
//                this.relayout('pressed', value);
//            }
        }
    },
//    syncSize : function(){                
//        Edo.controls.Button.superclass.syncSize.call(this);               
//        this.el.style.visibility = 'visible';
//        alert(this.realHeight);
//    },
    destroy : function(){   
        
        Edo.util.Dom.clearEvent(this.field);
        Edo.controls.Button.superclass.destroy.call(this);               
    }
});
Edo.controls.Button.regType('button');