/*
    Validator验证器
    
        1.验证逻辑函数,返回true,false
        2.
        

    <text id="a"/>        
    
    <validator target="a" property="text" valid="fn(e)" errorMsg="a" />


    target:
    property:
    autoValid: true  //false的话,在change的时候,不调用doValid方法,由使用者统一调用
    error:      //错误描述信息,如果返回false,则使用此描述
    
    
*/
/**
    @name Edo.core.Validator
    @class
    @typeName validator
    @description 验证器,所有从Edo.controls.Control都可以应用此验证器进行验证信息提示.1)监听对象的属性变化事件;2)验证逻辑函数;3)调用对象的valid和invalid方法表达验证成功或错误
    @extend Edo.core.Component
    @example 


*/ 
Edo.core.Validator = function(){
    Edo.core.Validator.superclass.constructor.call(this);
};
Edo.core.Validator.extend(Edo.core.Component,{
    /**
        @name Edo.core.Validator#error
        @property
        @type String
        @description 验证失败时的错误描述
    */
    error: '错误',
    /**
        @name Edo.core.Validator#target
        @property
        @type Component
        @description 验证的目标组件对象
    */
    _setTarget: function(value){
        if(typeof value == 'string') value = Edo.get(value);
        this.target = value;
        this.bindTarget();
    },
    /**
        @name Edo.core.Validator#property
        @property
        @type String
        @description 验证目标属性
    */
    _setProperty: function(value){
        this.property = value;
        this.bindTarget();
    },
    /**
        @name Edo.core.Validator#valid
        @property
        @type Function
        @description 验证逻辑函数
    */
    _setValid: function(value){
        var _ = value;
        if(typeof value !== 'function'){
            eval('_ = function(value){'+value+'}');
        }
        this.validFn = _;
    },
    bindTarget: function(){
        if(this.target && this.property){
            this.doBind.defer(100, this);//延迟100毫秒
        }
    },
    doBind: function(){
        this.target = typeof this.target === 'string' ? window[this.target] : this.target;
        if(this.target && this.property){
            var etype = this.target.validPropertyEvent;
            this.target.un(etype, this.doValid, this);
            this.target.on(etype, this.doValid, this);
        }
    },            
    doValid: function(e){ 
        var t = this.target;
        if(!t.autoValid) return;
        if(t.validPropertyEvent !== 'propertychange' || (e.property == this.property)){
            //如果是propertychange事件, 则改变的属性, 必须符合约定
            //如果不是propertychange事件, 则可以直接获取值, 进行验证
            var v = t.validPropertyEvent !== 'propertychange' ? e[this.property] : e['value'];
            
            return this.valid(v, e);
        }
    },
    /**
        @name Edo.core.Validator#valid
        @function 
        @description 验证目标对象的属性是否通过验证器函数的验证
    */
    valid: function(v, e){        
        var t = this.target;
        if(typeof v == 'undefined') v = t[this.property];
        if(this.validFn){
            //验证之前, 绑定所有的error错误信息组件, 使error组件绑定forId组件的valid和invalid, 并自动控制错误显示信息
            var errors = Edo.getByProperty('forId', t.id);
            errors.each(function(error){
                if(error.type == 'error'){
                    error.bind(error.forId);
                }
            });
        
            var ret = this.validFn.call(this.target, v, e, t);
            if(ret === true || ret === undefined) {
                t.fireEvent('valid', {
                    type: 'valid',
                    source: t,
                    property: this.property,
                    value: v                    
                });
                t.clearInvalid(); 
                
                return true;
            }
            else {
                var msg = ret === false ? this.error : ret;
                t.fireEvent('invalid', {
                    type: 'invalid',
                    source: t,
                    errorMsg: msg,
                    property: this.property,
                    value: v                                        
                });
                t.showInvalid(msg);            
                return false;
            }
        }
        return true;
    }
});

//提供一些标准常用的验证逻辑,如邮件,网址,等...
Edo.core.Validator.regType('validator');

Edo.apply(Edo.core.Validator, {
    //不能为空
    NoEmpty: function(v, e, obj){
        if(v === undefined || v === '') return false;
        return true;
    },    
    //长度限制
    Length: function(v){
        var min = Edo.isNumber(this.minLength) ? this.minLength : 0;
        var max = Edo.isNumber(this.maxLength) ? this.maxLength : 5;
        if(!Edo.isValue(v)) return false;
        if(v.length > min && v.length <= max) return true;
        return "不在"+min+"~"+max+"长度范围内";
    },
    //必须是数字
    Number: function(v){
        var text = v;
        var num = parseInt(text);
        if(num != text){
            return "必须输入数字";
        }
        var min = this.minValue || 0;
        var max = Edo.isNumber(this.maxValue) ? this.maxValue : 100;
        if(num <min || num > max){
            return '只能输入'+min+'~'+max+'数值范围';
        }
        return true;
    }
});