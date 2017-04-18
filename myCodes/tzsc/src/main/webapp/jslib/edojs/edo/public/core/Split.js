/**
    @name Edo.core.Split
    @class 
    @typeName split
    @description 竖向分隔符,一般用于toolbar的按钮分隔符
    @extend Edo.core.UIComponent
    @example 
*/ 
Edo.core.Split = function(){        
    Edo.core.Split.superclass.constructor.call(this);        
};
Edo.core.Split.extend(Edo.core.UIComponent,{
    /**
        @name Edo.core.Split#defaultWidth
        @property
        @default 6
    */   
    defaultWidth: 6,
    /**
        @name Edo.core.Split#defaultHeight
        @property
        @default 20
    */   
    defaultHeight: 20,
    /**
        @name Edo.core.Split#minWidth
        @property
        @default 6
    */   
    minWidth: 6,    
    elCls: 'e-split',
    getInnerHtml: function(sb){
        sb[sb.length] = '<div class="e-split-inner"></div>';
    },
    _setHtml: function(){}
});

Edo.core.Split.regType('split');