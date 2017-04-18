/**
    @name Edo.containers.FieldSet
    @class 
    @typeName fieldset
    @description 
    @extend Edo.containers.Container
    @example 


*/ 
Edo.containers.FieldSet = function(){    
    Edo.containers.FieldSet.superclass.constructor.call(this);    
    
};
Edo.containers.FieldSet.extend(Edo.containers.Container, {
    /**
        @name Edo.containers.FieldSet#defaultWidth
        @property        
        @default 80
    */ 
    defaultWidth: 80,   
    /**
        @name Edo.containers.FieldSet#defaultHeight
        @property
        @default 30
    */ 
    defaultHeight: 20,
    /**
        @name Edo.containers.FieldSet#collapseHeight
        @property
        @default 18
    */ 
    collapseHeight: 18,        
    /**
        @name Edo.containers.FieldSet#legend
        @property
        @type {String} 
        @description 头部文本
    */
    legend: '',
     
    /**
        @name Edo.containers.Box#padding
        @property
        @type [top, right, bottom, left]
        @default [3,5,5,5]
        @description 内边距宽度
    */
    padding: [3,5,5,5],    
    
    elCls: 'e-fieldset e-div',
    
    getInnerHtml: function(sb){        
        sb[sb.length] = '<fieldset class="e-fieldset-fieldset"><legend class="e-fieldset-legend">';        
        sb[sb.length] = '<span class="'+(this.enableCollapse ? 'e-fieldset-icon' : '')+'">';
        sb[sb.length] = this.legend || '&#160;';
        sb[sb.length] = '</span></legend><div class="e-fieldset-body" style="';        
        sb[sb.length] = this.doScrollPolicy();
        sb[sb.length] = '"></div></fieldset>';
    },
    createChildren: function(el){        
        Edo.containers.FieldSet.superclass.createChildren.call(this, el);        
        
        this.fieldset = this.el.firstChild;
        this.scrollEl = this.fieldset.lastChild;
        this.legendEl = this.fieldset.firstChild;        
        this.titleEl = this.legendEl.lastChild;
    },
    initEvents:  function(){
        Edo.containers.FieldSet.superclass.initEvents.call(this);
        Edo.util.Dom.on(this.legendEl, 'click', this._onLegendClick, this);       
    },
    _onLegendClick: function(e){   
    
        if(this.enableCollapse){                 
            this.toggle();            
        }
    },
    syncSize: function(){
        Edo.containers.FieldSet.superclass.syncSize.apply(this, arguments);
        
        Edo.util.Dom.setSize(this.fieldset, this.realWidth, this.realHeight);
    },    
    measure: function(){
        Edo.containers.FieldSet.superclass.measure.call(this);
        
        this.realWidth += 2;
        this.realHeight += 20;
        
        var p = this.padding;                
        this.realWidth += p[3] + p[1];
        this.realHeight += p[0] + p[2];
        
        this.measureSize();
    },
    getLayoutBox: function(){
        var box = Edo.containers.FieldSet.superclass.getLayoutBox.call(this);
        box.x += 1;
        box.y += 1;
        box.width -= 2;
        box.height -= 20;
        
        var p = this.padding;
        box.width = box.width - p[3] - p[1];
        box.height = box.height - p[0] - p[2];
        box.x += p[3];
        box.y += p[0];
        
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    _setLegend: function(value){
        if(this.legend !== value){
            this.legend = value;
            if(this.el){                
                this.titleEl.innerText = value;
            }
            this.changeProperty('legend', value);
        }
    },
    _setPadding: function(value){
        value = this._toArray(value);
        if(!this._checkTheSame(this.padding, value)){                    
            this.padding = value;            
            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    },    
    destroy : function(){        
        Edo.util.Dom.clearEvent(this.legendEl);
        this.legendEl = null;
        
        Edo.containers.Container.superclass.destroy.call(this);
    }
});

Edo.containers.FieldSet.regType('fieldset');