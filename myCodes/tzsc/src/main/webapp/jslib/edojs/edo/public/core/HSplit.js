/**
    @name Edo.core.HSplit
    @class 
    @typeName hsplit
    @description 横向分隔符,一般用于menu的分隔符
    @extend Edo.core.Split
    @example 
*/ 
Edo.core.HSplit = function(){        
    Edo.core.HSplit.superclass.constructor.call(this);        
};
Edo.core.HSplit.extend(Edo.core.Split,{
    /**
        @name Edo.core.HSplit#height
        @property
        @default 4
    */ 
    height: 4,
    /**
        @name Edo.core.HSplit#width
        @property
        @default 100%
    */ 
    width: '100%',
    /**
        @name Edo.core.HSplit#defaultHeight
        @property
        @default 4
    */ 
    defaultHeight: 4,  
    /**
        @name Edo.core.HSplit#minHeight
        @property
        @default 4
    */   
    minHeight: 4,
    
    elCls: 'e-split e-split-v e-div'
});


Edo.core.HSplit.regType('hsplit');