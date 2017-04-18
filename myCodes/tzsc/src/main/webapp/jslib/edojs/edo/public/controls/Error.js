/**
    @name Edo.controls.Error
    @class
    @typeName error
    @description 错误显示条
    @extend Edo.controls.Control
*/
Edo.controls.Error = function(){
    Edo.controls.Error.superclass.constructor.call(this);    
    
};
Edo.controls.Error.extend(Edo.controls.Control,{
    /**
        @name Edo.controls.Error#text
        @property
        @type String
        @description 错误描述文本
    */
    text: '',
    /**
        @name Edo.controls.Error#autoWidth
        @property
        @default true
    */
    autoWidth: true,
    /**
        @name Edo.controls.Error#autoHeight
        @property
        @default true
    */
    autoHeight: true,
    elCls: 'e-error e-div',    
    minHeight: 16,
    /**
        @name Edo.controls.Error#visible
        @property
        @default false
    */
    visible: false,
    getInnerHtml: function(sb){
        sb[sb.length] = '<div class="e-error-inner">'+this.text+'</div><a class="e-error-close"></a>';
    },
    init: function(){
        Edo.controls.Error.superclass.init.call(this);
        
        this.on('click', function(e){
            if(Edo.util.Dom.hasClass(e.target, 'e-error-close')){
                this._onValid();
                var target = Edo.get(this.forId);
                if(!target) return;
                target.focus();
            }
        }, this);
    },
    _setText: function(value){
        if(this.text !== value){
            this.text = value;
            if(this.el){
                this.el.firstChild.innerHTML = value;
                //this.el.style.width = 'auto';
            }
            if(!Edo.isInt(this.width)){
                this.widthGeted = false;                
//                this.el.style.width = 'auto';
//                Edo.util.Dom.repaint(this.el);
            }
            if(!Edo.isInt(this.height)){
                this.heightGeted = false;
            }
            this.changeProperty('text', value);
            this.relayout('text', value);
        }
    },
    bind: function(forId){
        this.unbind(forId);
        var target = Edo.get(forId);
        if(!target) return;
        target.on('valid', this._onValid, this);
        target.on('invalid', this._onInValid, this);
    },
    unbind: function(forId){
        var target = Edo.get(forId);
        if(!target) return;
        target.un('valid', this._onValid, this);
        target.un('invalid', this._onInValid, this);
    },
    _onValid: function(e){    
        this.set('visible', false);
        this.set('text', '');
    },
    _onInValid: function(e){            
        this.set('text', e.errorMsg);
        this.set('visible', true);
    }
});

Edo.controls.Error.regType('error');