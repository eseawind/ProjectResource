/**
    @name Edo.containers.FormItem
    @class 
    @typeName formitem
    @description 带label左侧文本显示框的容器,提供表单布局方式
    @extend Edo.containers.Box
    @example 


*/ 
Edo.containers.FormItem = function(){    
    Edo.containers.FormItem.superclass.constructor.call(this);    
    
};
Edo.containers.FormItem.extend(Edo.containers.Box, {
    /**
        @name Edo.containers.FormItem#defaultHeight
        @property        
        @default 22
    */ 
    defaultHeight: 22, 
    /**
        @name Edo.containers.FormItem#minHeight
        @property        
        @default 22
    */ 
    minHeight: 22,
    /**
        @name Edo.containers.FormItem#border
        @property        
        @default [0,0,0,0]
    */ 
    border: [0,0,0,0],
    /**
        @name Edo.containers.FormItem#padding
        @property        
        @default [0,0,0,0]
    */ 
    padding: [0,0,0,0],
    
    /**
        @name Edo.containers.FormItem#labelPosition
        @property
        @type {String}
        @default left
        @description label方位.top/right/bottom/left
    */
    labelPosition: 'left',
    /**
        @name Edo.containers.FormItem#label
        @property
        @type {String}        
        @description 文本
    */
    label: '',
    /**
        @name Edo.containers.FormItem#labelWidth
        @property
        @type {String}        
        @description 文本宽度
    */
    labelWidth: 60,
    labelHeight: 22,
    /**
        @name Edo.containers.FormItem#labelAlign
        @property
        @type {String}        
        @description 文本偏移
    */
    labelAlign: 'left',
    /**
        @name Edo.containers.FormItem#labelCls
        @property
        @type {String}        
        @description 文本cls
    */
    labelCls: '',
    /**
        @name Edo.containers.FormItem#labelStyle
        @property
        @type {String}        
        @description 文本style
    */
    labelStyle: '',    
    
    elCls: 'e-formitem e-box e-div',
    
    forId: '',  //如果没有设置, 可以focus第一个child
    
    measure: function(){
        Edo.containers.FormItem.superclass.measure.call(this);
        if(this.labelPosition=='left' || this.labelPosition == 'right'){
            this.realWidth += this.labelWidth;
        }else{
            this.realHeight += this.labelHeight;
        }
        
        this.measureSize();
    },    
    getLayoutBox: function(){
        var box = Edo.containers.FormItem.superclass.getLayoutBox.call(this);        
        
        switch(this.labelPosition){
        case "top":
            box.y += this.labelHeight;
            box.height -= this.labelHeight;
        break;
        case "right":            
            box.width -= this.labelWidth;
        break;
        case "bottom":            
            box.height -= this.labelHeight;
        break;
        case "left":
            box.x += this.labelWidth;
            box.width -= this.labelWidth;
        break;
        }
        
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    
    doLabel: function(){
        if(this.el){
            this.labelCt.innerHTML = '<div style="float:'+this.labelAlign+';">'+this.label+'</div>';
        }
    },
    _setLabel: function(value){       
        if(this.label != value){
            this.label = value;            
            this.doLabel();
        }
    },
    _setLabelAlign: function(value){       
        if(this.labelAlign != value){
            this.labelAlign = value;            
            this.doLabel();
        }
    },
    _setLabelPosition: function(value){       
        if(this.labelPosition != value){
            this.labelPosition = value;            
            this.relayout('labelPosition', value);
        }
    },
    _setLabelStyle: function(value){       
        if(this.labelStyle != value){
            this.labelStyle = value;
            if(this.labelCt) Edo.util.Dom.applyStyles(this.labelCt, value);
        }
    },
    _setLabelCls: function(value){       
        if(this.labelCls != value){
            this.labelCls = value;
            Edo.util.Dom.addClass(this.labelCt, this.labelCls);
        }
    },
    _setLabelWidth: function(value){
        value = parseInt(value);
        if(isNaN(value)) throw new Error('labelWidth must is Number type');        
        if(this.labelWidth != value){
            this.labelWidth = value;
            
            this.relayout('labelWidth', value);
        }
    },   
    _setForId: function(value){              
    
        if(this.forId != value){
            this.forId = value;
            
            var cmp = Edo.getCmp(value);
            if(cmp){
                //点击事件,激发cmp的focus方法.
                
                //获取cmp的focusEl,设置htmlFor
            }else{
                this.labelCt.dom.htmlFor = value;                
            }
        }
    },
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.containers.FormItem.superclass.syncSize.call(this);
        
        var box = this.getLayoutBox();
        var w = this.labelWidth, h = this.labelHeight;   
        var style = 'top:auto;right:auto;bottom:auto;left:auto;';     
        switch(this.labelPosition){
        case "top":
            w = box.width;
            style += 'left:'+this.padding[3]+'px;top:'+this.padding[0]+'px';
        break;        
        case "right":
            h = box.height;
            style += 'right:'+this.padding[1]+'px;top:'+this.padding[0]+'px';
        break;
        case "bottom":
            w = box.width;
            style += 'left:'+this.padding[3]+'px;bottom:'+this.padding[2]+'px';
        break;
        case "left":
            h = box.height;
            style += 'left:'+this.padding[3]+'px;top:'+this.padding[0]+'px';
        break;
        }        
        
        Edo.util.Dom.setSize(this.labelCt, w, h);
        Edo.util.Dom.applyStyles(this.labelCt, style);
    },
    getInnerHtml: function(sb){    
        sb[sb.length] = '<label for="'+this.forId+'" class="e-formitem-label '+this.labelCls+'" style="overflow:hidden;text-align:'+this.labelAlign+';'+this.labelStyle+';">'+this.label+'</label>';
        Edo.containers.Panel.superclass.getInnerHtml.call(this, sb);
    },        
    createChildren: function(el){        
        Edo.containers.FormItem.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.lastChild;
        
        this.labelCt = this.el.firstChild;
    }
});

Edo.containers.FormItem.regType('formitem');