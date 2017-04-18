/*
关闭
最大化(包含还原)	toggle逻辑
最小化(包含还原)
伸缩(上下,左右)		配合父容器的布局,改变伸缩逻辑和图片样式.
	伸缩的时候,重要的是控制好minWidth/minHeight,在父容器内进行一个比较小的占位
	当确定为collapse的时候,不要对子元素进行doLayout(提高性能!)
	
*/

/**
    @name Edo.containers.Panel
    @class 
    @typeName panel
    @description 实现header头容器
    @extend Edo.containers.Box
    @example 


*/ 
Edo.containers.Panel = function(){
    Edo.containers.Panel.superclass.constructor.call(this);
    
    //titleclick
};
Edo.containers.Panel.extend(Edo.containers.Box,{
    /**
        @name Edo.containers.Panel#collapseHeight
        @property        
        @default 26
    */ 
    collapseHeight: 26,
    /**
        @name Edo.containers.Panel#collapseWidth
        @property        
        @default 26
    */ 
    collapseWidth: 26,
    /**
        @name Edo.containers.Panel#minHeight
        @property        
        @default 22
    */
    minHeight: 22,          //当折叠后,此容器不调用measure和doLayout,不操作子元素    
    /**
        @name Edo.containers.Panel#headerHeight
        @property 
        @type Number
        @default 25
        @description 头部高度
    */
    headerHeight: 25,    
    /**
        @name Edo.containers.Panel#title      
        @property 
        @type String
        @description 标题文本
    */ 
    title: '',
    /**
        @name Edo.containers.Panel#titleIcon
        @property 
        @type String
        @description 标题图标
    */
    titleIcon: '',
    
    titleCollapse: false,
    
    titlebar: null,
    
    elCls: 'e-panel e-box e-div',

    getInnerHtml: function(sb){        
        var h = this.headerHeight;
        if(!isBorderBox){
            h -= 1;
        }
        sb[sb.length] = '<div class="e-panel-header" style="height:'+h+'px;line-height:'+h+'px;"><div class="e-panel-title">'+this.doTitle()+'</div><div class="e-titlebar"></div></div>';
        Edo.containers.Panel.superclass.getInnerHtml.call(this, sb);
    },
    initEvents: function(){
        Edo.containers.Panel.superclass.initEvents.call(this);
        
        this.on('click', this._onClick, this);
    },
    createChildren: function(el){                
        Edo.containers.Panel.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.lastChild;
        
        this.headerCt = this.el.firstChild;
        this.titleCt = this.headerCt.firstChild;
        this.titlebarCt = this.headerCt.lastChild; 
        
        if(this.titlebar){        
            this.titlebar.render(this.titlebarCt);
        }       
    }, 
    measure: function(){
        Edo.containers.Panel.superclass.measure.call(this);
        this.realHeight += this.headerHeight;
        this.measureSize();
    },
    getLayoutBox: function(){          
        var box = Edo.containers.Panel.superclass.getLayoutBox.call(this);        
        var hd = this.headerHeight;
        box.y += hd;
        box.height -= hd;
        
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    doTitle: function(){
        var title = "";
        if(this.titleIcon) title += '<div style="float:left;margin-right:3px;" class="e-panel-titleicon '+this.titleIcon+'"></div>';
        if(this.title) title += '<div style="float:left;">'+this.title+'</div>';
        if(this.titleCt) this.titleCt.innerHTML = title;
        return title;
    },
    _setTitle: function(value){
        if(this.title != value){
            this.title = value;
            this.doTitle();
            this.changeProperty('title', value);
        }
    },
    _setTitleIcon: function(value){
        if(this.titleIcon != value){
            this.titleIcon = value;
            this.doTitle();
            this.changeProperty('titleIcon', value);
        }
    },
    _setHeaderHeight: function(value){
        value = parseInt(value);        
        if(isNaN(value)) throw new Error('headerHeight must is Number type');
        if(this.headerHeight != value){
            this.headerHeight = value;
            
            this.collapseHeight = value + this.border[0];
            this.relayout('headerHeight', value);
        }
    },
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        var h = this.headerHeight;
        if(!isBorderBox){
            h -= 1;
        }
        if(this.el){
            this.headerCt.style.height = h + 'px';
            this.headerCt.style.lineHeight = h + 'px';
        }
        Edo.containers.Panel.superclass.syncSize.call(this);        
    },
    _setTitlebar: function(value){
        if(!(value instanceof Array)) value = [value];
        value.each(function(o){
            var c = o.cls;
            Edo.apply(o,{
                type: 'button',
                simpleButton: true,                
                height: 15,
                width: 15,
                minHeight: 15,
                minWidth: 15,
                cls: c,
                overCls: o.overCls || c+'-over',
                focusCls: o.focusCls || c+'-focus',
                pressedCls: o.pressedCls || c+'-pressed'
            });
        });
        
        if(this.titlebar){
            this.titlebar.destory();            
        }
        
        this.titlebar = Edo.create({            
            type: 'ct',
            layout: 'horizontal',
            horizontalGap: 1,
            height: 15,
            children: value,
            onclick: function(e){
                e.stop();
            }
        });
        this.titlebar.owner = this;
        
        if(this.titlebarCt){
            this.titlebar.render(this.titlebarCt);
        }
    },
    _onClick: function(e){        
        if(e.within(this.headerCt) && this.titleCollapse){
            this.toggle();
        }
    }
    
////    setCollapseProperty: function(value){
////        this.collapseProperty = value;
////        if(this.el){
////            if(value == 'width'){
////                //e-collapse-width
////                Edo.util.Dom.addClass(this.titleCt, 'e-collapse-width');
////                Edo.util.Dom.addClass(this.scrollEl, 'e-collapse-width');
////            }else{
////                Edo.util.Dom.removeClass(this.titleCt, 'e-collapse-width');
////                Edo.util.Dom.removeClass(this.scrollEl, 'e-collapse-width');
////            }
////        }
////    } 
});

Edo.containers.Panel.regType('panel', 'dialog');

Edo.containers.Dialog = Edo.containers.Panel;