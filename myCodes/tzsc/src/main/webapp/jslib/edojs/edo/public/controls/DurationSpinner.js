Edo.controls.DurationSpinner = function(config){    
    
    Edo.controls.DurationSpinner.superclass.constructor.call(this);    
    
};
Edo.controls.DurationSpinner.extend(Edo.controls.Spinner,{
    value : {
        Duration: 0,
        DurationFormat: 37,
        Estimated: 1
    },
    minValue: 0,
    
	incrementValue : 1,
	alternateIncrementValue : 8,
	
	durationFormat: null,       //必须有一个工期format数组
	
	mustDay: false,
	//durationFormat:
//	incrementConstant : Date.DAY,	
//	alternateIncrementConstant : Date.MONTH,
 
    spin: function(value, direction, alternate){       
        if(!value) value = '0';
        
        var v = parseFloat(value);
        
        var Estimated = value.indexOf('?') != -1;
        
        var dv = value.replace(v, '');
        if(!dv) dv = 'd';
        else if(dv == '?') dv = 'd?';
        
        if(this.mustDay){
        
            if(dv != 'd' || dv != 'd?') dv = 'd';
        }
        
        var DurationFormat = this.durationFormat[dv];
        
        if(!DurationFormat){
             this._setValue(this.value);
             return;
        }
               
		var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        if(Edo.isValue(direction)){
		    (direction == 'down') ? v -= incr : v += incr ;
		}
		
		var f = this.durationFormat[DurationFormat];
        v *= f[0];
		
		v = {
		    Duration: v,
		    DurationFormat: DurationFormat,
		    Estimated: Estimated
		}
		
		this._setValue(v);
    },
    _setText: function(v){    
    
        if(!this.durationFormat) return;
        
        var f = this.durationFormat[v.DurationFormat];
        if(!f) {
            v = '0d';
        }else{
            v = (v.Duration / f[0]) + f[2] + (v.Estimated ? '?' : '')
        }
        Edo.controls.DurationSpinner.superclass._setText.c(this, v);    
    }
    ,
    //确保值的有效性
    normalizeValue : function(v){       
     
        v.Duration = Edo.controls.DurationSpinner.superclass.normalizeValue.call(this, v.Duration);
        
        return v;
	}
});

Edo.controls.DurationSpinner.regType('durationspinner');