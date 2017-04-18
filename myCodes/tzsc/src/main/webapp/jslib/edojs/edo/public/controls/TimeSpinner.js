/**
    @name Edo.controls.TimeSpinner
    @class
    @typeName timespinner
    @description 小时调节器
    @extend Edo.controls.DateSpinner
*/
Edo.controls.TimeSpinner = function(){    
    
    Edo.controls.TimeSpinner.superclass.constructor.call(this);    
    
};
Edo.controls.TimeSpinner.extend(Edo.controls.DateSpinner,{    
    /**
        @name Edo.controls.TimeSpinner#format
        @property
        @default "H:i"
    */
    format : "H:i",
    /**
        @name Edo.controls.TimeSpinner#incrementValue
        @property
        @default 1
    */
	incrementValue : 1,
	/**
        @name Edo.controls.TimeSpinner#incrementConstant
        @property
        @default Date.MINUTE
    */
	incrementConstant : Date.HOUR,
	/**
        @name Edo.controls.TimeSpinner#alternateIncrementValue
        @property
        @default 1
    */
	alternateIncrementValue : 1,
	/**
        @name Edo.controls.TimeSpinner#alternateIncrementConstant
        @property
        @default Date.HOUR
    */
	alternateIncrementConstant : Date.HOUR,
	normalizeValue : function(value){
	
	    if(typeof value == 'string'){
	        var v = Date.parseDate(value, 'H:i:s');
	        if(v) value = v;
	    }
	    return Edo.controls.TimeSpinner.superclass.normalizeValue.call(this, value);    
	}
});
Edo.controls.TimeSpinner.regType('timespinner');    