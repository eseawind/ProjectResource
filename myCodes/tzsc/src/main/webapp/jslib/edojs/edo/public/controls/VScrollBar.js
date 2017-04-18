/**
    @name Edo.controls.VScrollBar
    @class
    @typeName VScrollBar
    @description 竖向滚动条
    @extend Edo.controls.ScrollBar
*/
Edo.controls.VScrollBar = function(){
    Edo.controls.VScrollBar.superclass.constructor.call(this);        
};
Edo.controls.VScrollBar.extend(Edo.controls.ScrollBar,{
    /**
        @name Edo.controls.VScrollBar#defaultWidth
        @property
        @type Number
        @default 17        
    */
    defaultWidth: 17,
    /**
        @name Edo.controls.VScrollBar#defaultHeight
        @property
        @type Number
        @default 100
    */
    defaultHeight: 100,
    /**
        @name Edo.controls.VScrollBar#direction
        @property
        @type horizontal, vertical
        @default vertical
    */
    direction: 'vertical'
});

Edo.controls.VScrollBar.regType('vscrollbar');