/**
    @name Edo.controls.Slider
    @class
    @typeName slider
    @description 拖拽调节器
    @extend Edo.controls.Control
*/
Edo.controls.Slider = function(){

/**
    @name Edo.controls.Slider#beforevaluechange
    @event 
    @description 值改变前激发
    @property {Number} value 当前新值    
*/
/**
    @name Edo.controls.Slider#valuechange
     @event 
     @description 值改变事件
     @property {Number} value 当前值
*/
    Edo.controls.Slider.superclass.constructor.call(this);
    
    
};
Edo.controls.Slider.extend(Edo.controls.Control,{    
    /**
        @name Edo.controls.Slider#valueField
        @property
        @default 'value'
    */
    valueField: 'value',
    /**
        @name Edo.controls.Slider#defaultValue
        @property
        @type Number
        @default 0
    */
    defaultValue: 0,
    /**
        @name Edo.controls.Slider#value
        @property
        @type Number
        @default 0
        @description 值
    */
    value: 0,
    /**
        @name Edo.controls.Slider#minValue
        @property
        @type Number
        @description 最小值
    */
    minValue: 0,
    /**
        @name Edo.controls.Slider#maxValue
        @property
        @type Number
        @default 100
        @description 最大值
    */
    maxValue: 100,
    //keyIncrement: 1,
    /**
        @name Edo.controls.Slider#increment
        @property
        @type Number
        @default 0
        @description 最小增加值
    */
    increment: 0,    
    /**
        @name Edo.controls.Slider#defaultWidth
        @property
        @default 100
    */
    defaultWidth: 100,
    /**
        @name Edo.controls.Slider#defaultHeight
        @property
        @default 22
    */
    defaultHeight: 22,
    /**
        @name Edo.controls.Slider#minHeight
        @property
        @default 22
    */
    minHeight: 22,
    /**
        @name Edo.controls.Slider#minWidth
        @property
        @default 30
    */
    minWidth: 30,
    /**
        @name Edo.controls.Slider#direction
        @property
        @type horizontal, vertical
        @default horizontal
        @description 横竖方位
    */
    direction: 'horizontal',    //vertical
    
    thumbCls: 'e-slider-thumb',
    thumbOverCls: 'e-slider-thumb-over',
    thumbPressedClass: 'e-slider-thumb-pressed',

    elCls: 'e-slider',
    getInnerHtml: function(sb){
        if(this.direction == 'vertical'){
            this.elCls += ' e-slider-v';
        }
        sb[sb.length] = '<div class="e-slider-start"><div class="e-slider-end"><div class="e-slider-inner" style="';
        if(this.direction == 'vertical') {
            sb[sb.length] = 'height:';
            sb[sb.length] = this.realHeight - 14;
            sb[sb.length] = 'px;';
        }            
        sb[sb.length] = '"><div class="';
        sb[sb.length] = this.thumbCls;
        sb[sb.length] = '"></div></div></div></div>';
    },
    createChildren: function(el){
        Edo.controls.Slider.superclass.createChildren.call(this, el);
        this.startEl = this.el.firstChild;
        this.endEl = this.startEl.firstChild;
        this.inner = this.endEl.lastChild;
        this.thumb = this.inner.lastChild;
        this.halfThumb = 15/2;
        
        this.thumbDrag = new Edo.util.Drag({
            //onStart: this.onThumbDragStart.bind(this),
            onMove: this._onThumbDragMove.bind(this),
            onStop: this._onThumbDragComplete.bind(this)
        });                
    },
    initEvents: function(){                       
        if(!this.design){
            this.on('mousedown', this._onMouseDown);
            Edo.util.Dom.addClassOnClick(this.thumb, this.thumbPressedClass);
            Edo.util.Dom.addClassOnOver(this.thumb, this.thumbOverCls);
        }
        Edo.controls.Slider.superclass.initEvents.call(this);
        
    },
   
    syncSize: function(){
        //this.realWidth -= 7;
        Edo.controls.Slider.superclass.syncSize.call(this);
        
        if(this.mustSyncSize !== false){
            if(this.direction == 'vertical'){
                //this.inner.style.height = (this.realHeight - 14) + 'px';//偏移
                //Edo.util.Dom.setHeight(this.el, this.realHeight);
            }else{
                //Edo.util.Dom.setWidth(this.el, this.realWidth);
            }
        }
        //do value  :   以后会发展成为一个模式set...
        var v = this.normalizeValue(this.value);
        if(!isNaN(v)){
            this.value = v;
            this.moveThumb(this.translateValue(this.value));   
                         
        }
        
        if(this.direction == 'vertical') {            
            this.inner.style.height = (this.realHeight - 14) + 'px';            
        }     
    },
    _onMouseDown: function(e){
        if(e.button == Edo.util.MouseButton.left){
            this.ctBox = Edo.util.Dom.getBox(this.inner);
            if(e.within(this.thumb)){                
                this.initXY = Edo.util.Dom.getXY(this.thumb);
                this.xOffset = this.initXY[0] - e.xy[0];
                this.yOffset = this.initXY[1] - e.xy[1];
                
                this.thumbDrag.start(e);
                
                this.fireEvent('slidestart', {
                    type: 'slidestart',
                    source: this,
                    xy: Edo.util.Dom.getXY(this.thumb)
                });
            }else{
                //点击thumb之外
                //var pos = (drag.now[index] + offset) - t;
                var index = this.direction == 'horizontal' ? 0 : 1;        
                var t = this.direction == 'horizontal' ? this.ctBox.x : this.ctBox.y;                
                var pos = e.xy[index] - t;
                this.anim = true;
                this._setValue(Math.round(this.reverseValue(pos)));
                this.anim = false;
            }
        }
    },
//    onThumbDragStart: function(e){
//        this.fireEvent('slidestart', {
//            type: 'slidestart',
//            source: this,
//            xy: [e.now[0]+this.xOffset, e.now[1]+this.yOffset]
//        });
//    },
    _onThumbDragMove: function(drag){
        var xy = this.initXY;
        
        var index = this.direction == 'horizontal' ? 0 : 1;
        var offset = this.direction == 'horizontal' ? this.xOffset : this.yOffset;
        var t = this.direction == 'horizontal' ? this.ctBox.x : this.ctBox.y;
        
        
        
        var pos = (drag.now[index] + offset) - t;                
        this._setValue(Math.round(this.reverseValue(pos)));
        
        
        xy = drag.now;
        xy[index] = t + this.translateValue(this.value);
        this.fireEvent('slide', {
            type: 'slide',
            source: this,
            xy: xy
        });
    },
    _onThumbDragComplete: function(e){
        this.fireEvent('slidecomplete', {
            type: 'slidecomplete',
            source: this,
            xy: [e.now[0]+this.xOffset, e.now[1]+this.yOffset]
        });
    },
    _setValue: function(v){
        v = this.normalizeValue(v);
        if(v !== this.value && this.fireEvent('beforevaluechange', {
                type: 'beforevaluechange',
                source: this,                
                value: v
            }) !== false){
            
            this.value = v;
            if(this.el){
                this.moveThumb(this.translateValue(v));
            }
//            this.fireEvent('valuechange', {
//                type: 'valuechange',
//                source: this,
//                value: v
//            });
            
            this.changeProperty('value', this.value);
        }
    },
    //将value转换为坐标
    translateValue : function(v){
        var ratio = this.getRatio();
        return (v * ratio)-(this.minValue * ratio)-this.halfThumb;
    },
    //将坐标转换为值
	reverseValue : function(pos){
        var ratio = this.getRatio();
        return (pos+this.halfThumb+(this.minValue * ratio))/ratio;
    },
    getRatio : function(){
        var fn = this.direction == 'horizontal' ? 'getWidth' : 'getHeight';
        var now = Edo.util.Dom[fn](this.inner);
        
        var v = this.maxValue - this.minValue;
        return v == 0 ? now : (now/v);
    },
    moveThumb: function(pos){
        if(this.thumb){   
            var p = this.direction == 'horizontal' ? 'left' : 'top';                                 
            if(this.anim){
                var o = {};
                o[p] = pos;
                new Edo.util.Fx.Style({
                    el: this.thumb,
                    duration: 300
                }).start(o);
            }else{
                this.thumb.style[p] = pos + 'px';
            }
        }
    },
    //确保值的有效性
    normalizeValue : function(v){
       if(typeof v != 'number'){
            v = parseInt(v);
        }
        if(isNaN(v)) v = this.minValue;
        v = Math.round(v);
        v = this.doSnap(v);//v = Edo_controls_Slider__doSnap.c(this, v);    => v = AAA.c(this.v);
        v = v.constrain(this.minValue, this.maxValue);
        return v;
    },
    //根据increment增值,以及当前的value,得到一个新的有效value
    doSnap : function(value){
        if(!this.increment || this.increment == 1 || !value) {
            return value;
        }
        var newValue = value, inc = this.increment;
        var m = value % inc;
        if(m > 0){
            if(m > (inc/2)){
                newValue = value + (inc-m);
            }else{
                newValue = value - m;
            }
        }
        return newValue.constrain(this.minValue,  this.maxValue);
    },
    destroy : function(){        
        Edo.util.Dom.clearEvent(this.thumb);
        this.thumb = null;
        
        Edo.controls.Slider.superclass.destroy.call(this);       
    }
    
});
Edo.controls.Slider.regType('slider');

/*
    1.tick: 刻度
    
    2.tip:  提示信息
*/