/**
    @name Edo.controls.VSlider
    @class
    @typeName vslider
    @description 竖向拖拽调节器
    @extend Edo.controls.Slider
*/
Edo.controls.VSlider = function(){
    Edo.controls.VSlider.superclass.constructor.call(this);
};
Edo.controls.VSlider.extend(Edo.controls.Slider,{
    /**
        @name Edo.controls.VSlider#direction
        @property
        @default 'vertical'
    */
    direction: 'vertical',
    /**
        @name Edo.controls.VSlider#minWidth
        @property
        @default 22
    */
    minWidth: 22,
    /**
        @name Edo.controls.VSlider#minHeight
        @property
        @default 30
    */
    minHeight: 30,
    /**
        @name Edo.controls.VSlider#defaultHeight
        @property
        @default 100
    */
    defaultHeight: 100,
    /**
        @name Edo.controls.VSlider#defaultWidth
        @property
        @default 22
    */
    defaultWidth: 22
});
Edo.controls.VSlider.regType('vslider');