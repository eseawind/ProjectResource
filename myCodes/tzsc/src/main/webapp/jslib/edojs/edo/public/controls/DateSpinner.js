/**
    @name Edo.controls.DateSpinner
    @class
    @typeName datespinner
    @description 日期调节器
    @extend Edo.controls.Spinner
*/
Edo.controls.DateSpinner = function(config){    
    
    Edo.controls.DateSpinner.superclass.constructor.call(this);    
    
};
Edo.controls.DateSpinner.extend(Edo.controls.Spinner,{
    defaultValue: new Date(),
    /**
        @name Edo.controls.DateSpinner#value
        @property
        @default new Date()
    */ 
    value : new Date(),	
    /**
        @name Edo.controls.DateSpinner#incrementValue
        @property
        @default 1
    */ 
	incrementValue : 1,
	/**
        @name Edo.controls.DateSpinner#alternateIncrementValue
        @property
        @default 1
    */ 
	alternateIncrementValue : 1,
	
	/**
        @name Edo.controls.DateSpinner#format
        @property
        @type String
        @default Y-m-d
        @description 格式化显示日期字符串
    */   
	format : "Y-m-d",
	/**
        @name Edo.controls.DateSpinner#incrementConstant
        @property
        @type String
        @default Date.DAY
        @description 增长单位
    */   
	incrementConstant : Date.DAY,	
	/**
        @name Edo.controls.DateSpinner#alternateIncrementConstant
        @property
        @type String
        @default Date.MONTH
        @description 快速增长单位
    */   
	alternateIncrementConstant : Date.MONTH,
 
    spin: function(value, direction, alternate){
        
        var v = this.normalizeValue(value);
        var dir = (direction == 'down') ? -1 : 1 ;
		var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
		var dtconst = (alternate == true) ? this.alternateIncrementConstant : this.incrementConstant;
        
        if(Edo.isValue(direction)){
		    v = v.add(dtconst, dir*incr);
		}
        
		this._setValue(v);
    },
    _setText: function(v){
    
        v = this.value;
        if(v && v.getFullYear){
            
            v = v.format(this.format);
        }
        Edo.controls.DateSpinner.superclass._setText.call(this, v);    
    },
    //确保值的有效性
    normalizeValue : function(date){
        var dt = date;
        
        dt = Date.parseDate(dt, this.format);
        if(!dt){
            dt = this.value;
        }
        
		var min = (typeof this.minValue == 'string') ? Date.parseDate(this.minValue, this.format) : this.minValue ;
		var max = (typeof this.maxValue == 'string') ? Date.parseDate(this.maxValue, this.format) : this.maxValue ;

		if(this.minValue != undefined && dt < min){
			dt = min;
		}
		if(this.maxValue != undefined && dt > max){
			dt = max;
		}        
		return dt;
	},
	getValue: function(){
	    return this.value.format(this.format);
	}	
});

Edo.controls.DateSpinner.regType('datespinner');