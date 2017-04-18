/**
    @name Edo.controls.Label
    @class
    @typeName label
    @description 文本显示框
    @extend Edo.controls.Control
*/
Edo.controls.Label = function(){

    Edo.controls.Label.superclass.constructor.call(this);
};
Edo.controls.Label.extend(Edo.controls.Control,{
    elCls: 'e-label',
    /**
        @name Edo.controls.Label#autoWidth
        @property
        @default true
    */
    autoWidth: true,
    /**
        @name Edo.controls.Label#autoHeight
        @property
        @default true
    */
    autoHeight: true,
    /**
        @name Edo.controls.Label#minWidth
        @property
        @default 20
    */
    minWidth: 20,
        

    /**
        @name Edo.controls.Label#text
        @property
        @type String
        @description 文本
    */
    text: '',    
    /**
        @name Edo.controls.Label#forId
        @property    
        @type {String}
        @description 目标组件ID
    */
    forId: '',
    
//    sizeSet: false,
//    widthGeted: false,
      
    getInnerHtml: function(sb){
        sb[sb.length] = this.text;
    },
    _setText: function(value){
        if(this.text !== value){
            this.text = value;
            if(this.el){
                this.el.innerHTML = value;
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
    }     
});

Edo.controls.Label.regType('label');