/**
    @name Edo.controls.Radio
    @class
    @typeName radio
    @description 单选框
    @extend Edo.controls.CheckBox
*/
Edo.controls.Radio = function(){

    Edo.controls.Radio.superclass.constructor.call(this);
};
Edo.controls.Radio.extend(Edo.controls.CheckBox,{
    elCls: 'e-radiobox',
    _onClick: function(e){
        if(!this.enable) return;        
        if(this.checked === true) return;
        Edo.controls.Radio.superclass._onClick.call(this, e);
        //如果本控件有name,则找出所有同name的组件,如果另外的组件也是radio,则将其他的设置为false
        if(this.name){
            var os = Edo.managers.SystemManager.getByName(this.name);
            os.each(function(o){
                if(o != this && o.isType('radio')) o._setChecked(false);
            }, this);
        }
    }
});

Edo.controls.Radio.regType('radio');   
