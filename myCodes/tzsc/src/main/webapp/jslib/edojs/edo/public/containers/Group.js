/**
    @name Edo.containers.Group
    @class 
    @typeName group
    @description 实现圆角的容器
    @extend Edo.containers.Container
    @example 


*/ 
Edo.containers.Group = function(){    
    Edo.containers.Group.superclass.constructor.call(this);    
    
};
Edo.containers.Group.extend(Edo.containers.Container, {
    /**
        @name Edo.containers.Group#defaultWidth
        @property        
        @default 60
    */ 
    defaultWidth: 60,   
    /**
        @name Edo.containers.Group#defaultHeight
        @property        
        @default 22
    */ 
    defaultHeight: 22, 
    
    elCls: 'e-group e-ct e-div',
    
    /**
        @name Edo.containers.Group#padding
        @property        
        @default [2, 3, 3, 3]
        @description 内边距宽度
    */ 
    padding: [2, 3, 3, 3],
    
    syncScrollEl: function(box){
        
        //Edo.util.Dom.applyStyles(this.scrollEl, 'border:0px;padding:0px;margin:0px;');
        if( this.scrollEl != this.el){     //扩展后,scrollEl和el可能不是同一个对象
            //&& this.mustSyncSize !== false    算了,容器就一直弄吧
            //trace('<b>syncScrollEl:</b>'+this.id);
        
            var bx = this._getBox();            
            var left = box.x -bx.x;// - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderLeftWidth')) || 0);
            var top = box.y -bx.y;// - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderTopWidth')) || 0);
            if(box.width<0)box.width=0;
            if(box.height<0)box.height=0;
            this.scrollEl.style.width = box.width + 'px';
            this.scrollEl.style.height = box.height + 'px';            
        }
    },
    getHeaderHtml: function(){
        return '';
    },
    getInnerHtml: function(sb){
        var p = this.padding;
        var l = p[3], t = p[0], r = p[1], b = p[2];
        var h = this.realHeight - t - b;
        var w = this.realWidth - l - r;
        var headerHtml = this.getHeaderHtml(sb);
        sb[sb.length] = '<table cellspacing="0" border="0" cellpadding="0"><tr class="e-group-t"><td class="e-group-tl" style="height:'+t+'px;width:'+l+'px;"></td><td class="e-group-tc" style="width:'+w+'px;">'+headerHtml+'</td><td class="e-group-tr" style="width:'+r+'px;"></td></tr><tr class="e-group-m"><td class="e-group-ml"></td><td class="e-group-mc"><div class="e-group-body" style="width:'+w+'px;height:'+h+'px;"></div></td><td class="e-group-mr"></td></tr><tr class="e-group-b"><td class="e-group-bl" style="height:'+b+'px;width:'+l+'px;"></td><td class="e-group-bc"></td><td class="e-group-br" style="width:'+r+'px;"></td></tr></table>';                
    },
    createChildren: function(el){        
        Edo.containers.Group.superclass.createChildren.call(this, el);        
        this.table = this.el.firstChild;
        this.tc = this.table.rows[0].cells[1];
        this.mc = this.table.rows[1].cells[1];
        this.bc = this.table.rows[2].cells[1];
        this.scrollEl = this.mc.firstChild;
        
        this.bodyEl = this.mc.firstChild;
//        this.Group = this.el.firstChild;
//        this.scrollEl = this.Group.lastChild;
//        this.legendEl = this.Group.firstChild;
//        this.checkbox = this.legendEl.firstChild;
//        this.titleEl = this.legendEl.lastChild;
    },

    measure: function(){
        Edo.containers.Group.superclass.measure.call(this);
        
        this.realWidth += this.padding[1] + this.padding[3];
        this.realHeight += this.padding[0] + this.padding[2];
        this.measureSize();
    },
    syncSize: function(){        
        Edo.containers.Group.superclass.syncSize.call(this);
        
        
        var p = this.padding;
        var t = p[0], r = p[1], b = p[2], l = p[3];
        var h = this.realHeight - t - b;
        var w = this.realWidth - l - r;
        
        this.tc.style.width = w+'px';
        
        this.bodyEl.style.width = w+'px';
        this.bodyEl.style.width = w+'px';
        
        
    },
    getLayoutBox: function(){    
        var box = Edo.containers.Group.superclass.getLayoutBox.call(this);
        box.x += this.padding[0];
        box.y += this.padding[1];
        box.width -= (this.padding[1] + this.padding[3]);
        box.height -= (this.padding[0] + this.padding[2]);
        
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    _setPadding: function(value){
        value = this._toArray(value);
        if(!this._checkTheSame(this.padding, value)){
            var b = this.padding = value;
            //this.addStyle(this._getBPStyle());
            
            
            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    }
});
Edo.containers.Group.regType('group');