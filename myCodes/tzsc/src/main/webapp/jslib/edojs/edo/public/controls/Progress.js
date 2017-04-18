/**
    @name Edo.controls.Progress
    @class
    @typeName progress
    @description 进度条
    @extend Edo.core.UIComponent
*/
Edo.controls.Progress = function(){
    
    /**
        @name Edo.controls.Progress#progresschange
        @event
        @desction 进度改变事件
        @property {Number} progress 进度
        @property {String} text 进度文本
    */    

    Edo.controls.Progress.superclass.constructor.call(this);
};
Edo.controls.Progress.extend(Edo.core.UIComponent,{
    valueField: 'progress',
    defaultValue: 0,

    minWidth: 50,
    defaultWidth: 100,
    /**
        @name Edo.controls.Progress#showText
        @property
        @type Boolean
        @default true
        @description 是否显示文本
    */    
    showText: true,
    /**
        @name Edo.controls.Progress#progress
        @property
        @type Number
        @default 0(0~100)
        @description 进度值
    */
    progress: 0,
    /**
        @name Edo.controls.Progress#text
        @property
        @type String   
        @description 文本
    */
    text: '',
    
    elCls: 'e-progress e-div',
    _setProgress: function(value){
        if(value <0) value = 0;
        if(value > 100) value = 100;
        if(this.progress != value){
            this.progress = value;
            if(this.el){
                this.textEl.innerHTML = this.getProgressDesc();
                Edo.util.Dom.setWidth(this.progressEl, this.getProgressWidth());
            }
            this.changeProperty('progress');
            
            this.fireEvent('progresschange', {
                type: 'progresschange',
                source: this,
                progress: value,
                text: this.text
            });
        }
    },
    _setText: function(value){
        if(this.text != value){
            this.text = value;
            if(this.el){
                this.textEl.innerHTML =  this.getProgressDesc();
            }
            this.changeProperty('text');
        }
    },
    getProgressDesc: function(){        
        return this.showText ? this.progress+'% '+this.text : '&#160';
    },
    getProgressWidth: function(){
        return parseInt(this.realWidth * this.progress / 100) - 1;
    },
    getInnerHtml: function(sb){
        sb[sb.length] = '<div class="e-progress-bar"></div><div class="e-progress-text"></div>';
    },
    createChildren: function(el){
        Edo.controls.Label.superclass.createChildren.call(this, el);
        
        this.progressEl = this.el.firstChild;        
        this.textEl = this.el.lastChild;        
    }
});

Edo.controls.Progress.regType('progress');   