/*
    实现外观边框和内间距.
        border
        padding
*/

/**
    @name Edo.containers.Box
    @class 
    @typeName box
    @description 盒容器,实现边框border和内间距padding
    @extend Edo.containers.Container
    @example 
*/
Edo.containers.Box = function(){    
    Edo.containers.Box.superclass.constructor.call(this);
};
Edo.containers.Box.extend(Edo.containers.Container, {
    /**
        @name Edo.containers.Application#defaultWidth
        @property
        @default 60
    */ 
    defaultWidth: 60,
    /**
        @name Edo.containers.Application#defaultHeight
        @property
        @default 22
    */ 
    defaultHeight: 22, 
    /**
        @name Edo.containers.Box#border
        @property
        @type [top, right, bottom, left]
        @default [1,1,1,1]
        @description 边框线宽度
    */
    border: [1,1,1,1],    
    /**
        @name Edo.containers.Box#padding
        @property
        @type [top, right, bottom, left]
        @default [1,1,1,1]
        @description 内边距宽度
    */
    padding: [5,5,5,5],        
    
    elCls: 'e-box',    
    /**
        @name Edo.containers.Box#bodyStyle
        @property
        @type String
        @description body的style
    */
    bodyStyle: '',          //bodyStyle
    /**
        @name Edo.containers.Box#bodyCls
        @property
        @type String
        @description body的cls
    */
    bodyCls: '',
    
    getInnerHtml: function(sb){
        var box = this._getBox();
        var lb = this.getLayoutBox(box);
        
        sb[sb.length] = '<div class="e-box-scrollct ';
        sb[sb.length] = this.bodyCls;
        sb[sb.length] = '" style="';        
        sb[sb.length] = this.bodyStyle;
        sb[sb.length] = ';width:';
        sb[sb.length] = lb.width;
        sb[sb.length] = 'px;height:';
        sb[sb.length] = lb.height;
        sb[sb.length] = 'px;margin:0px;padding:0px;border:0px;position:absolute;left:';
        
        sb[sb.length] = lb.x - box.x - this.border[3];
        sb[sb.length] = 'px;top:';
        sb[sb.length] = lb.y - box.y - this.border[0];
        sb[sb.length] = 'px;';        
        sb[sb.length] = ';'+this.doScrollPolicy();
        
        
        sb[sb.length] = '"></div>';
    },
    
    createHtml: function(w, h, appendArray){    
        this._getBPStyle();
    
        return Edo.containers.Box.superclass.createHtml.call(this, w, h, appendArray);
    },
    
    createChildren: function(el){
        Edo.containers.Box.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.firstChild; //这个操作很耗时 1000个box生成,要耗掉150左右毫秒
    },
    measure: function(){
        Edo.containers.Box.superclass.measure.call(this);
        
        var b = this.border;
        var p = this.padding;        
        //1)给得到的布局参考值加上所有的偏移
        this.realWidth += b[3] + p[3] + b[1] + p[1];        //如果不是绝对设置的,则需要加上偏移
        this.realHeight += b[0] + p[0] + b[2] + p[2];
        
        //2)确保操作...
        this.measureSize();
    },
    getLayoutBox: function(box){           //获取容器的布局尺寸!!!要减掉偏移!!!
        var b = this.border;
        var p = this.padding;
        
        box = box || Edo.containers.Box.superclass.getLayoutBox.call(this);
        box.width = box.width - b[3] - p[3] - b[1] - p[1];
        box.height = box.height - b[0] - p[0] - b[2] - p[2];
        box.x += b[3] + p[3];
        box.y += b[0] + p[0];
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    syncSize: function(){
        var w = this.realWidth, h = this.realHeight;
        
        this.domWidth = w;
        this.domHeight = h;
        Edo.containers.Box.superclass.syncSize.call(this);
        
    },
    _setBodyCls: function(value){
        if(this.bodyCls != value){
            this.bodyCls = value;
            if(this.el){
                Edo.util.Dom.addClass(this.scrollEl, value);
            }
            this.changeProperty('bodyCls', value); 
        }
    },
    _setBodyStyle: function(value){
        if(this.bodyStyle != value){
            this.bodyStyle = value;
            if(this.el){
                Edo.util.Dom.applyStyles(this.scrollEl, value);    
            }
            this.changeProperty('bodyStyle', value); 
        }
    },
    _getBPStyle: function(){
        var b = this.border;//, p = this.padding;
        var style = 'border-left-width:'+ b[3]+'px;border-top-width:'+ b[0]+'px;border-right-width:'+ b[1]+'px;border-bottom-width:'+ b[2] + 'px;';
        //style += 'padding-left:'+ p[0]+'px;padding-top:'+ p[1]+'px;padding-right:'+ p[2]+'px;padding-bottom:'+ p[3]+'px;';        
        this.addStyle(style);
    },    
    _setBorder: function(value){
        value = this._toArray(value);
        if(!this._checkTheSame(this.border, value)){
            this.border = value;            
            this.addStyle(this._getBPStyle());
            this.relayout('border', value);
            this.changeProperty('border', value);
        }
    },
    _setPadding: function(value){
        value = this._toArray(value);
        if(!this._checkTheSame(this.padding, value)){
                    
            this.padding = value;
            this.addStyle(this._getBPStyle());
            
            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    }
});
Edo.containers.Box.regType('box');