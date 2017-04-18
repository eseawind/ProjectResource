/**
    @name Edo.controls.Date
    @class
    @typeName date
    @description 日期输入框
    @extend Edo.controls.Trigger
*/
Edo.controls.Date = function(config){    
    /**
        @name Edo.controls.Date#beforedatechange
        @event
        @description 日期改变事件                
        @property {Date} date 日期值
    */
    /**
        @name Edo.controls.Date#datechange
        @event
        @description 日期改变事件                
        @property {Date} date 日期值
    */

    Edo.controls.Date.superclass.constructor.call(this);    
    //this.date = new Date();
};
Edo.controls.Date.extend(Edo.controls.Trigger,{
    /**        
        @name Edo.controls.Date#enableResizePopup
        @property
        @default false
    */  
    enableResizePopup: false,
    /**        
        @name Edo.controls.Date#valueField
        @property
        @default date
    */
    valueField: 'date',
    /**        
        @name Edo.controls.Date#popupWidth
        @property
        @default 178
    */
    popupWidth: 178,
    /**        
        @name Edo.controls.Date#popupHeight
        @property
        @default auto
    */
    popupHeight: 'auto',
    /**        
        @name Edo.controls.Date#popupType
        @property
        @default ct
    */
    popupType: 'ct',
    /**        
        @name Edo.controls.Date#triggerPopup
        @property
        @default true
    */
    triggerPopup: true,
    
    
    
    /**
        @name Edo.controls.Date#format
        @property
        @type String
        @description 格式化显示日期字符串,显示在text上
    */    
    format: 'Y-m-d',
    /**
        @name Edo.controls.Date#inputFormat
        @property
        @type String
        @description 如果输入日期字符串,则用此格式化器得到日期对象
    */
    inputFormat: 'Y-m-dTH:i:s',
    /**
        @name Edo.controls.Date#date
        @property
        @type Date
        @description 日期,默认是今天
    */    
    
    elCls: 'e-text e-date',
    
    enableTime: true,
    
    datepickerClass: 'datepicker',
    
    valueFormat: false,
    
    getValue: function(){
        var v = Edo.controls.DatePicker.superclass.getValue.call(this);
        if(v && this.valueFormat) v = v.format(this.format);
        return v;
    },
    
    convertDate : function(date){
        var v = date;
        if(!Edo.isDate(date)) {
            date = Date.parseDate(v, this.inputFormat);
            if(!Edo.isDate(date)) date = Date.parseDate(v, this.format);
        }
        return date;
    },
    /**
        @name Edo.controls.Date#required
        @property
        @type Boolean
        @default true
        @description 是否不能为空
    */   
    required: true,
    _setRequired: function(v){
        if(this.required != v){
            this.required = v;
            if(this.datePicker){
               this.datePicker.set('required', v); 
            }
        }
    },
    _setDate: function(value){    
    //if(this.id == 'birthday') debugger
        value = this.convertDate(value);
        if(!Edo.isValue(value)) {
            value = null;
            if(this.required) return false;
        }
        
        if(this.date != value){
        
            if(this.fireEvent('beforedatechange', {
                type: 'beforedatechange',
                source: this,
                date: value
            }) == false) return;
        
            this.date = value;
            
            this._setText(value ? value.format(this.format) : '');
            
            this.changeProperty('date', value);
            
            this.fireEvent('datechange', {
                type: 'datechange',
                target: this,
                date: this.date
            });
        }
    },
    _dateChangeHandler: function(e){    
        if(this.datePicker && this.datePicker.created){
            this._setDate(e.date);
            this.hidePopup();
            this.focus();
        }
    },
    within: function(e){      //传递一个事件对象,判断是否在此控件的点击区域内.
    
        var r = false;
        if(this.datePicker) r = this.datePicker.within(e);      //datePicker有一些下拉框是脱离这个环境的
        return Edo.controls.Date.superclass.within.call(this, e) || r;
    },
    createPopup: function(){ 
    
        Edo.controls.Date.superclass.createPopup.apply(this, arguments);        
        
        if(!this.datePicker){
            this.datePicker = Edo.build({                
                type: this.datepickerClass,                
                width: '100%',
                height: '100%',
                date: this.date,
                required: this.required,
                onDatechange: this._dateChangeHandler.bind(this)                
            });
            
            this.popupCt.addChild(this.datePicker);
            
            //this.popupCt.doLayout();
        }else{
            this.datePicker.set('date', this.date);
        }
    },
    _onTextChange: function(e){   
        
        Edo.controls.Date.superclass._onTextChange.c(this, e);        
        
        this._setDate(this.text);        
    }
});
Edo.controls.Date.regType('date');