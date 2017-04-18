/**
    @name Edo.data.DataForm
    @class 
    @typeName DataForm
    @description 表单数据源
    @extend Edo.core.Component
    @example     
*/ 

Edo.data.DataForm = function(data){
    Edo.data.DataForm.superclass.constructor.call(this);
}
Edo.data.DataForm.extend(Edo.core.Component, {
    componentMode: 'data'
});

Edo.data.DataForm.regType('dataform');