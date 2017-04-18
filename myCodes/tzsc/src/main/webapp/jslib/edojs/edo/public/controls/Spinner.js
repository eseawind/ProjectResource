/**
    @name Edo.controls.Spinner
    @class
    @typeName spinner
    @description 数值调节器
    @extend Edo.controls.Trigger
*/
Edo.controls.Spinner = function(config){        
/**
    @name Edo.controls.Spinner#beforevaluechange
    @event
    @description 值改变前激发
    @property {Object} value 要改变的值     
*/        
/**
    @name Edo.controls.Spinner#valuechange
    @event
    @description 值改变事件
    @property {Object} value 当前值    
*/        
/**
    @name Edo.controls.Spinner#spin
    @event
    @description 激发spin事件,就是用户通过操作,发生了up/down的值调整
    @property {String} direction 调节方向, up或down
*/        

    Edo.controls.Spinner.superclass.constructor.call(this);    
    
};
Edo.controls.Spinner.extend(Edo.controls.Trigger,{
    valueField: 'value',
    defaultValue: 0,
    /**
        @name Edo.controls.Spinner#value
        @property
        @type Number
        @description 值
    */
    value: 0,
    /**
        @name Edo.controls.Spinner#minValue
        @property
        @type Number
        @description 最小值
    */
//    minValue: 0,
    /**
        @name Edo.controls.Spinner#maxValue
        @property
        @type Number
        @description 最大值
    */
//    maxValue: 100,    
    /**
        @name Edo.controls.Spinner#incrementValue
        @property
        @type Number
        @description 增加值
    */
    incrementValue: 1,
    /**
        @name Edo.controls.Spinner#alternateIncrementValue
        @property
        @type Number
        @description 快速增加值
    */
    alternateIncrementValue: 5, //shift+click时
    /**
        @name Edo.controls.Spinner#alternateKey
        @property
        @type String
        @description 快速增加键,默认是"shiftKey"
    */
    alternateKey: 'shiftKey',

    spinTime: 100,
    //triggerCls: 'e-trigger-empty',
    //withKeyUp: false,
    valueField: 'value',
    
    getTriggerHtml: function(){
        this.elCls += ' e-spinner';        
        return '<div class="e-spinner-up" unselectable="on"><div class="e-trigger-icon" unselectable="on"></div></div><div class="e-spinner-split"></div><div class="e-spinner-down" unselectable="on"><div class="e-trigger-icon" unselectable="on"></div></div>'
    },
    
    createChildren: function(el){
        Edo.controls.Spinner.superclass.createChildren.call(this, el);
        this.upEl = this.trigger.firstChild;
        this.downEl = this.trigger.lastChild;
        
        var v = this.value;
        this.value = null;
        this._setValue(v);
    },
    initEvents: function(){
        if(!this.design){
            
            Edo.util.Dom.addClassOnOver(this.upEl, 'e-spinner-up-over');
            Edo.util.Dom.addClassOnClick(this.upEl, 'e-spinner-up-pressed');
            Edo.util.Dom.addClassOnOver(this.downEl, 'e-spinner-down-over');
            Edo.util.Dom.addClassOnClick(this.downEl, 'e-spinner-down-pressed');
            
            Edo.util.Dom.on(this.field, 'keydown', this._onKeyDown, this);
            
            Edo.util.Dom.on(document.body, 'mouseup', this.stopSpin, this);
            
            this.on('spin', this._onSpin, this);
        }
        
        Edo.controls.Spinner.superclass.initEvents.call(this);
    },
    _onTrigger: function(e){    
        if(!this.enable) return;
        Edo.controls.Spinner.superclass._onTrigger.call(this, e);
        var direction;
        
        if(e.within(this.upEl)){
            direction = 'up';
        }else if(e.within(this.downEl)){
            direction = 'down';
        }
        if(direction){
            this.startSpin(direction, e);
        }
    },
    startSpin: function(direction, e){
        this.fireSpin(direction, e);
        
        this.spinTimer = this.fireSpin.time(this.spinTime, this, [direction, e]);
        
        
    },
    stopSpin: function(e){
        var sf = this;
        setTimeout(function(){
            clearInterval(sf.spinTimer);
            sf.spinTimer = null;
        }, 1);
        //Edo.util.Dom.un(document.body, 'mouseup', this.stopSpin, this);
    },
    _onKeyDown: function(e){
        switch(e.keyCode){
        case 38:
            this.fireSpin('up', e);
        break;
        case 40:
            this.fireSpin('down', e);
        break;
        }
    },
    fireSpin: function(direction, e){
        //
    
        this.fireEvent('spin', {
            type: 'spin',
            direction: direction,
            source: this,
            event: e
        });
    },
    _onSpin: function(e){
        this.text = this.field.value;
        this.spin(this.field.value, e.direction, e.event[this.alternateKey]);
    },
    spin: function(value, direction, alternate){
        var v = this.normalizeValue(value);
        
		var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        if(Edo.isValue(direction)){
		    (direction == 'down') ? v -= incr : v += incr ;
		}						
		this._setValue(v);
    },
    //确保值的有效性
    normalizeValue : function(value){        
		var v = value;
        
        var v = parseFloat(v);
        if(isNaN(v)) v = this.value;
        
		if(this.minValue != undefined && v < this.minValue){
			v = this.minValue;
		}
		if(this.maxValue != undefined && v > this.maxValue){
			v = this.maxValue;
		}        
		return v;
	},
	_setValue: function(v){	
        v = this.normalizeValue(v);       
        
        var e = {
            type: 'beforevaluechange',
            source: this,
            name: this.name,
            value: v,            
            text: v
        };
        if(this.fireEvent('beforevaluechange', e) !== false){                        
        //if(v !== this.value && this.fireEvent('beforevaluechange', e !== false)){
            this.value = e.value;                        
            //this.setText(e.text);
            this._setText(e.text);
            
            e.type = 'valuechange';
            //this.fireEvent('valuechange', e);
            
            this.changeProperty('value', this.value);
        }
    },
    _onTextChange: function(e){
    
        this.text = this.field.value;
        
        this.spin(this.field.value);
    }
});

Edo.controls.Spinner.regType('spinner');   