/**
    @name Edo.core.Space
    @class 
    @typeName space
    @description 在布局器中,帮助进行布局的类,占位但不显示.
    @extend Edo.core.UIComponent
    @example 
*/ 
Edo.core.Space = function(){        
    Edo.core.Space.superclass.constructor.call(this);        
};
Edo.core.Space.extend(Edo.core.UIComponent,{
    /**
        @name Edo.core.Space#defaultWidth
        @property
        @default 0
    */   
    defaultWidth: 0,
    /**
        @name Edo.core.Space#defaultHeight
        @property
        @default 0
    */   
    defaultHeight: 0,
    /**
        @name Edo.core.Space#minWidth
        @property
        @default 0
    */   
    minWidth: 0,
    /**
        @name Edo.core.Space#minHeight
        @property
        @default 0
    */   
    minHeight: 0,
    _setHtml: function(){}
});


Edo.core.Space.regType('space');    