/**
    @name Edo.controls.Password
    @class
    @typeName password
    @description 密码输入框
    @extend Edo.controls.TextInput
*/
Edo.controls.Password = function(){

    Edo.controls.Password.superclass.constructor.call(this);
};
Edo.controls.Password.extend(Edo.controls.TextInput,{
    _htmltype: 'password'
});

Edo.controls.Password.regType('password');