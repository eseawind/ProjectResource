/**
    @name Edo.data.Convertor
    @private
    @class 
    @typeName Convertor
    @description 数据转换器
    @extend Edo.core.Component
    @example 
*/ 
Edo.data.Convertor = function(){
    Edo.data.Convertor.superclass.constructor.call(this);
    
    this.fields = [];
}
Edo.data.Convertor.extend(Edo.core.Component, {
    /*
        name: 'a', 
        mapping: 'BS', 
        
        convert: function(v){return ...}, 
        type: 'string',
        dateFormat
    */
    _setFields: function(fields){    
        if(!fields) fields = [];
        for(var i=0,l=fields.length; i<l; i++){
            var f = fields[i];
            if(typeof f == 'string'){
                 fields[i] = {name: f};
            }
            if(typeof f.mapping == 'undefined'){
                f.mapping = f.name;
            }
            if(!f.convert && f.type){
                f.convert = Edo.data.Convertor[f.type];
            }
        }
        this.fields = fields;
    },
    convert: function(data){
        if(!data) data = [];
        if(!(data instanceof Array)) data = [data];
        
        for(var i=0, l=data.length; i<l; i++){
            var o = data[i];
            this.convertObject(o);
        }
        
        return data;
    },
    convertObject: function(o){
        var fields = this.fields;
        
        var getMapping = this.getMappingValue;
        
        for(var i=0,l=fields.length; i<l; i++){
            var f = fields[i];
            
            var mapping = f.mapping;
            var v = mapping.indexOf('.') == -1 ? o[mapping] : getMapping(o, mapping);
            
            o[f.name] = f.convert ? f.convert(v, o, f) : v;
            
        }
        return o;
    },
    getMappingValue: function(o, mapping){
        var maps = mapping.split('.');
        var v = o;
        for(var i=0,l=maps.length; i<l; i++){
            v = v[maps[i]];
        }
        return v;
    }
});

Edo.data.Convertor.regType('convertor');

//converts
Edo.apply(Edo.data.Convertor, {
    stripRe: /[\$,%]/g,
    
    'string': function(v, o, f){
        return (v === undefined || v === null) ? '' : String(v); 
    },
    'int': function(v, o, f){
        return v !== undefined && v !== null && v !== '' ?
            parseInt(String(v).replace(this.stripRe, ""), 10) : 0;        
    },
    'float': function(v, o, f){
        return v !== undefined && v !== null && v !== '' ?
            parseFloat(String(v).replace(stripRe, ""), 10) : '';
    },
    'bool': function(v, o, f){
        return v === true || v === "true" || v == 1; 
    },
    'date': function(v, o, f){
        if(!v){
            return '';
        }
        if(Edo.isDate(v)){
            return v;
        }
        var dateFormat = f.dateFormat;
        if(dateFormat){
            if(dateFormat == "timestamp"){
                return new Date(v*1000);
            }
            if(dateFormat == "time"){
                return new Date(parseInt(v, 10));
            }
            return Date.parseDate(v, dateFormat);
        }
        var parsed = Date.parse(v);
        return parsed ? new Date(parsed) : null;
    },
    'array': function(v, o, f){
        if(!v && (v !== 0 || v !== false)) return [];
        return toString.apply(v) === '[object Array]' ? v : [v];
    }
});