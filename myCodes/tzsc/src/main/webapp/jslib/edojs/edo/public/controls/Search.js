/**
    @name Edo.controls.Search
    @class
    @typeName search
    @description 搜索框
    @extend Edo.controls.Trigger
*/
Edo.controls.Search = function(config){    
    /**
        @name Edo.controls.Search#cleartrigger
        @event
        @domevent
        @description 关闭图标点击事件
    */

    Edo.controls.Search.superclass.constructor.call(this);    
    
};
Edo.controls.Search.extend(Edo.controls.Trigger,{
    /**
        @name Edo.controls.Search#clearVisible
        @property
        @type Boolean
        @default false
        @description 是否显示关闭图标
    */
    clearVisible: false,
    
    elCls: 'e-text e-search',
    
    clearTriggerCls: 'e-trigger e-trigger-clear e-close',
    clearTriggerOverCls: 'e-trigger-over',
    
    getFieldWidth: function(){
        return this.realWidth - (this.clearVisible ? this.triggerWidth*2: (this.triggerVisible ? this.triggerWidth : 0));
    },
    getInnerHtml: function(sb){
        Edo.controls.Search.superclass.getInnerHtml.call(this, sb);
        
        sb[sb.length] = '<div class="';
        
        sb[sb.length] = this.clearTriggerCls;
        sb[sb.length] = '" style="display:'+(this.clearVisible ? 'block' : 'none')+';right:'+this.triggerWidth+'px;height:';
        sb[sb.length] = this.realHeight-1;
        sb[sb.length] = 'px;">';
        sb[sb.length] = this.getTriggerHtml();
        sb[sb.length] = '</div>';
    },
    initEvents: function(){             
        Edo.controls.Search.superclass.initEvents.call(this);
        
        if(!this.design){
            Edo.util.Dom.on(this.clearTrigger, 'mousedown', this._onClearTrigger, this);
            Edo.util.Dom.addClassOnOver(this.clearTrigger, this.clearTriggerOverCls);            
        }        
    },
    createChildren: function(el){    
        Edo.controls.Search.superclass.createChildren.call(this, el);
        this.clearTrigger = this.el.lastChild;
    },   
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.Search.superclass.syncSize.call(this);                    
        this.clearTrigger.style.display = this.clearVisible ? 'block' : 'none';
        
        this.clearTrigger.style.height = (this.realHeight -6) + 'px';
        
        var fieldwidth = this.getFieldWidth();
        if(this.clearVisible){
            this.clearTrigger.style.left = fieldwidth+'px';
        }
    },
    _setClearVisible: function(value){
        if(this.clearVisible != value){
            this.clearVisible = value;            
            if(this.el){
                this.syncSize();
            }
            this.changeProperty('clearVisible', value);
        }
    },
    _onClearTrigger: function(e){
        if(!this.enable) return;
        if(e.button != Edo.util.MouseButton.left) return;
        this._setClearVisible(false);
        
        this.focus.defer(50, this);        
        
        e.type = 'cleartrigger';
        e.source = this;
        this.fireEvent('cleartrigger', e);
        
    }
    
});
Edo.controls.Search.regType('search');   