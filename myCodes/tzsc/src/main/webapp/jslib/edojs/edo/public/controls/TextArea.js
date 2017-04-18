/**
    @name Edo.controls.TextArea
    @class
    @typeName textarea
    @description 多行输入框
    @extend Edo.controls.TextInput
*/
Edo.controls.TextArea = function(){        
    Edo.controls.TextArea.superclass.constructor.call(this);
}
Edo.controls.TextArea.extend(Edo.controls.TextInput,{
    _mode: 'textarea',
    lineHeight: 14,
    
    /**
        @name Edo.controls.TextArea#defaultHeight
        @property
        @default 40
    */
    defaultHeight: 40
});
Edo.controls.TextArea.regType('textarea');   