
/*
    实现下拉框逻辑,trigger图标
        1.combobox: grid
        2.date:     datepicker
        3.lov:      弹出url,弹出窗体 popupWindow
        4.tree
        
    实现autoComplete功能:
    popupCt:是一个box容器.
*/
/**
    @name Edo.controls.Trigger
    @class
    @typeName trigger
    @description 下拉弹出框
    @extend Edo.controls.TextInput
*/
Edo.controls.Trigger = function(){    
    /**
        @name Edo.controls.Trigger#beforetrigger
        @event
        @domevent
        @description trigger图标点击前事件
    */
    
    /**
        @name Edo.controls.Trigger#trigger
        @event
        @domevent
        @description trigger图标点击事件
    */

    Edo.controls.Trigger.superclass.constructor.call(this);
};
Edo.controls.Trigger.extend(Edo.controls.TextInput,{    
    enableResizePopup: true,
    /**
        @name Edo.controls.Trigger#maxHeight
        @property        
        @default 500
    */
    maxHeight: 500,    
    /**        
        @name Edo.controls.Trigger#triggerPopup
        @property
        @type Boolean
        @default false
        @description 为true在按trigger图片的时候,自动显示
    */      
    triggerPopup: false,

    triggerCls: 'e-trigger',
    triggerOverCls: 'e-trigger-over',
    triggerPressedCls: 'e-trigger-pressed', 
    
    triggerVisible: true,    
    
    initEvents: function(){             
        Edo.controls.Trigger.superclass.initEvents.call(this);            
        
        if(!this.design){
            Edo.util.Dom.on(this.el, 'mousedown', this._onTriggerMousedown, this);
            var el = this.trigger;
            Edo.util.Dom.addClassOnClick(el, this.triggerPressedCls);
            Edo.util.Dom.addClassOnOver(el, this.triggerOverCls);
        }
    },
    _onTriggerMousedown: function(e){
        if(this.readOnly || e.within(this.trigger)){
            this._onTrigger.defer(1, this, [e]);
        }
    },
    _onTrigger: function(e){
        e.stopDefault();
        if(!this.isEnable()) return;        
        if(e.button != Edo.util.MouseButton.left) return;
        
        e.type = 'beforetrigger';
        e.source = this;
        
        if(this.fireEvent('beforetrigger', e) === false) return;
                
        
        if(this.triggerPopup){
            if(this.popupDisplayed){
                this.hidePopup();
            }else{
                this.showPopup();
            }        
        }
        //this.focus();
        this.focus.defer(50, this);//这行代码真恶心..
        //this.field.focus();
//        var sf = this;
//        setTimeout(function(){
//            sf.field.focus();
//        }, 100);
        e.type = 'trigger';
        this.fireEvent('trigger', {
            type: 'trigger',
            source: this
        });
    },
//    setEnable: function(value){
//        Edo.controls.Trigger.superclass.setEnable.call(this, value);
//        if(this.trigger){
//            if(this.isEnable()){
//                Edo.util.Dom.removeClass(this.trigger, "e-disabled");
//            }else{        
//                Edo.util.Dom.addClass(this.trigger, "e-disabled");
//            }
//        }
//    },
    getTriggerHtml: function(){
        return '<div class="e-trigger-icon"></div>';
    },
    getInnerHtml: function(sb){
        Edo.controls.Trigger.superclass.getInnerHtml.call(this, sb);
        
        sb[sb.length] = '<div class="';
        
        sb[sb.length] = this.triggerCls;
        sb[sb.length] = '" style="display:';
        sb[sb.length] = this.triggerVisible ? 'block' : 'none';
        sb[sb.length] = ';height:';
        sb[sb.length] = this.realHeight-1;
        sb[sb.length] = 'px;">';
        sb[sb.length] = this.getTriggerHtml();
        sb[sb.length] = '</div>';     
    },   
    createChildren: function(el){
        Edo.controls.Trigger.superclass.createChildren.call(this, el);
        this.trigger = this.field.nextSibling;
    },
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.Trigger.superclass.syncSize.call(this);
        
        if(this.triggerVisible){
            this.trigger.style.display = 'block';
            Edo.util.Dom.setHeight(this.trigger, this.realHeight - 4);
        }else{
            this.trigger.style.display = 'none';
        }
    },
    triggerWidth: 19,
    getFieldWidth: function(){
        return this.realWidth - (this.triggerVisible ? this.triggerWidth : 0);
    },
    destroy : function(){   
        Edo.util.Dom.clearEvent(this.trigger);            
        Edo.util.Dom.remove(this.trigger);
        this.trigger = null;
        Edo.controls.Trigger.superclass.destroy.call(this);                 
    }
});

Edo.controls.Trigger.regType('trigger');    