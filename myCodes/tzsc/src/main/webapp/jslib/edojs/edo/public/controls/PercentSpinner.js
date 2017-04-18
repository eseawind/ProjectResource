Edo.controls.PercentSpinner = function(){

    Edo.controls.PercentSpinner.superclass.constructor.call(this);
};
Edo.controls.PercentSpinner.extend(Edo.controls.Spinner,{
    minValue: 0,
    maxValue: 100,
    _setText: function(v){
    
        v += '%';
        Edo.controls.PercentSpinner.superclass._setText.call(this, v);
    }
});

Edo.controls.PercentSpinner.regType('percentspinner');