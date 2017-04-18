/**
    @name Edo.controls.ScrollBar
    @class
    @typeName ScrollBar
    @description 横向滚动条
    @extend Edo.controls.Slider
*/
Edo.controls.ScrollBar = function(){
    Edo.controls.ScrollBar.superclass.constructor.call(this);    
    /**
        @name Edo.controls.ScrollBar#scrollstart
        @event
        @description 滚动条拖拽开始
        @property {String} direction 滚动方向(vertical/horizontal)
        @property {Number} value 滚动条当前值
    */
    /**
        @name Edo.controls.ScrollBar#scroll
        @event
        @description 滚动条拖拽中
        @property {String} direction 滚动方向(vertical/horizontal)
        @property {Number} value 滚动条当前值
    */
    /**
        @name Edo.controls.ScrollBar#scrollcomplete
        @event
        @description 滚动条拖拽完成
        @property {String} direction 滚动方向(vertical/horizontal)
        @property {Number} value 滚动条当前值
    */
};
Edo.controls.ScrollBar.extend(Edo.controls.Control,{
    /**
        @name Edo.controls.ScrollBar#valueField
        @property
        @default value
    */
    valueField: 'value',
    /**
        @name Edo.controls.ScrollBar#defaultValue
        @property
        @type Number
        @default 0
    */
    defaultValue: 0,
    /**
        @name Edo.controls.ScrollBar#minWidth
        @property
        @default 17
    */
    minWidth: 17,
    /**
        @name Edo.controls.ScrollBar#minHeight
        @property
        @default 17
    */
    minHeight: 17,
    /**
        @name Edo.controls.ScrollBar#defaultWidth
        @property
        @default 100
    */
    defaultWidth: 100,
    /**
        @name Edo.controls.ScrollBar#defaultHeight
        @property
        @default 17
    */
    defaultHeight: 17,
    
    upWidth: 17,
    upHeight: 17,
    downWidth: 17,
    downHeight: 17,
    /**
        @name Edo.controls.ScrollBar#maxValue
        @property
        @type Number
        @default 0
        @description 最大值
    */
    maxValue: 0,
    /**
        @name Edo.controls.ScrollBar#value
        @property
        @type Number
        @default 0
        @description 值
    */
    value: 0,
    
    minHandleSize: 7,
    
    //up/down的增量
    /**
        @name Edo.controls.ScrollBar#incrementValue
        @property
        @type Number
        @default 10
        @description 箭头点击的增量值
    */
    incrementValue: 10,    
    /**
        @name Edo.controls.ScrollBar#alternateIncrementValue
        @property
        @type Number
        @default 10
        @description 点击滚动条的增量值
    */
    alternateIncrementValue: 30,
    
    spinTime: 20,        
    
    scrollTime: 70,
    
    /**
        @name Edo.controls.ScrollBar#direction
        @property
        @type horizontal, vertical
        @default horizontal
        @description 横竖方位
    */
    direction: 'horizontal',    //horizontal
    
    elCls: 'e-div e-scrollbar',
    
    upPressedCls: 'e-scrollbar-up-pressed',
    downPressedCls: 'e-scrollbar-down-pressed',
    handlePressedCls: 'e-scrollbar-handle-pressed',
    
    set: function(o, value){
        if(!o) return;
        if(typeof o == 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }
        
        if(o.maxValue){
            this._setMaxValue(o.maxValue);
            delete o.maxValue;
        }
        if(o.maxValue){
            this._setMaxValue(o.maxValue);
            delete o.maxValue;
        }
        
        Edo.controls.ScrollBar.superclass.set.c(this, o);
    },        
    
    getInnerHtml: function(sb){
        if(this.direction == 'horizontal'){
            this.elCls += ' e-hscrollbar';
        }
        sb[sb.length] = '<div class="e-scrollbar-handle"><div class="e-scrollbar-handle-outer"></div><div class="e-scrollbar-handle-center"></div><div class="e-scrollbar-handle-inner"></div></div><div class="e-scrollbar-up"></div><div class="e-scrollbar-down"></div>';
    },
    createChildren: function(el){
        Edo.controls.ScrollBar.superclass.createChildren.call(this, el);        
        
        this.handle = this.el.firstChild;
        this.upBtn = this.el.childNodes[1];
        this.downBtn = this.el.lastChild;       
    },
    initEvents: function(){
        if(!this.design){            
            this.on('mousedown', this._onMousedown, this);       
            Edo.util.Dom.addClassOnClick(this.upBtn, this.upPressedCls);     
            Edo.util.Dom.addClassOnClick(this.downBtn, this.downPressedCls);
            Edo.util.Dom.addClassOnClick(this.handle, this.handlePressedCls);
            
            
            Edo.util.Dom.addClassOnClick(this.el, '', this._stopSpin.bind(this));
        }
        
        Edo.controls.ScrollBar.superclass.initEvents.call(this);
    },
    syncSize: function(){        
        Edo.controls.ScrollBar.superclass.syncSize.call(this);
        
        var handleSize = this.getHandleSize();
        if(this.direction == 'vertical'){
			Edo.util.Dom.setHeight(this.handle, handleSize);
		}else{
			Edo.util.Dom.setWidth(this.handle, handleSize);
		}
        
        this.syncScroll();
        
    },
    //获得handle的尺寸
    getHandleSize: function(){
        var size = 0;
        
        var btnSize,            //滚动条按钮尺寸
            size,               //滚动条尺寸
            max,                //滚动条最大尺寸
            handleSize;         //handle尺寸
            
        if(this.direction == 'vertical'){
            btnSize = this.upHeight + this.downHeight;
            size = this.realHeight;
            max = this.maxValue;
        }else{
            btnSize = this.upWidth + this.downWidth;
            size = this.realWidth;
            max = this.maxValue;
        }
        
        if(size == max + size){
			handleSize = 0;
		}else{
			handleSize = Math.max(this.minHandleSize, Math.round( (size - btnSize) * size / (max+size)) )
		}
		
		return handleSize;
    },
    //获得handle的偏移定位
    getHandleOffset: function(handleSize){    
        var btnSize,            //滚动条按钮尺寸
            size,               //滚动条尺寸
            max,                //滚动条最大尺寸
            realOffset
            
        if(this.direction == 'vertical'){
            btnSize = this.upHeight + this.downHeight;
            size = this.realHeight;
            max = this.maxValue;
            
            realOffset = this.value;
        }else{
            btnSize = this.upWidth + this.downWidth;
            size = this.realWidth;
            max = this.maxValue;
            
            realOffset = this.value;
        }
        
        //获得 size - btnSize - handleSize的尺寸
        var moveSize = size - btnSize - handleSize;     //这个就是可移动的范围
        
        offset = realOffset/max*moveSize;
        return offset;        
    },
    createvalue: function(offset, moveSize){
        var value = offset * this.maxValue / moveSize
        return Math.floor(value);
    },
    createvalue: function(offset, moveSize){
        var value = offset * this.maxValue / moveSize
        return Math.floor(value);
    },
    //获得可移动的区域
    getMoveReginBox: function(){
        var box = this.getBox();
        var size = this.getHandleSize();
        if(this.direction == 'vertical'){
            box.y += this.upHeight;
            box.height -= (this.upHeight + this.downHeight + size);
        }else{        
            box.x += this.upWidth;
            box.width -= (this.upWidth + this.downWidth + size);
        }
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    _onMousedown: function(e){
        if(e.button != 0) return false;
                
        var t = e.target;
        
        var isVertical = this.direction == 'vertical';
        this._stopSpin();
        if(Edo.util.Dom.contains(this.handle, t)){
            Edo.managers.DragManager.startDrag({
                event: e,
                capture: true,
                dragObject: this.handle,
                delay: 30,
                alpha: 1,
                enableDrop: false,
                ondragstart: this._onHandleDragStart.bind(this),
                ondragmove: this._onHandleDragMove.bind(this),
                ondragcomplete: this._onHandleDragComplete.bind(this)
            });
        }else if(Edo.util.Dom.contains(this.upBtn, t)){     //up
            this._startSpin('up', this.incrementValue, this.spinTime);
            this.canFireComplete = true;
            
        }else if(Edo.util.Dom.contains(this.downBtn, t)){   //down
            this._startSpin('down', this.incrementValue, this.spinTime);
            this.canFireComplete = true;
        }else{
            
            var moveBox = this.getMoveReginBox();
            var handleSie = this.getHandleSize();
            if(isVertical) moveBox.height += this.downHeight + handleSie;
            else moveBox.width += this.downWidth + handleSie;
            
            var moveBox2 = this.getMoveReginBox();
            
            var handleXY = Edo.util.Dom.getXY(this.handle);
            
            if(Edo.util.Dom.isInRegin(e.xy, moveBox)){
                
                if(isVertical){
                    
                    this.stopOffset = this.createvalue(Math.abs(moveBox2.y - e.xy[1]), moveBox2.height);
                    if(e.xy[1] < handleXY[1]){
                        this._startSpin('up', this.alternateIncrementValue, this.spinTime);
                    }else{
                        this._startSpin('down', this.alternateIncrementValue, this.spinTime);
                    }
                }else{                                    
                    this.stopOffset = this.createvalue(Math.abs(moveBox2.x - e.xy[0]), moveBox2.width);
                    
                    if(e.xy[0] < handleXY[0]){
                        this._startSpin('up', this.alternateIncrementValue, this.spinTime);
                    }else{
                        this._startSpin('down', this.alternateIncrementValue, this.spinTime);
                    }
                }
            }
            //alert(this.stopOffset);
            this.canFireComplete = true;
        }
        
    },
    doSpin: function(spinDirection, increment){
        var isVertical = this.direction == 'vertical';
        if(spinDirection == 'up'){
            if(isVertical){
                var v = this.value - increment;
                if(Edo.isValue(this.stopOffset) && v < this.stopOffset){
                    return;
                }
                this._setValue(v);
            }else{
                var v = this.value - increment;
                if(Edo.isValue(this.stopOffset) && v < this.stopOffset){
                    return;
                }
                this._setValue(v);
            }
        }else{
            if(isVertical){
                var v = this.value + increment;
                
                if(Edo.isValue(this.stopOffset) && v > this.stopOffset){                
                    return;
                }
                this._setValue(v);
            }else{
                var v = this.value + increment;                
                if(Edo.isValue(this.stopOffset) && v > this.stopOffset){
                    return;
                }
                this._setValue(v);
            }
        }
    },
    _startSpin: function(spinDirection, increment, spinTime){
        this.spinDirection = spinDirection;
        this.spinTimer = this.doSpin.time(spinTime, this, [spinDirection, increment], true);
    },
    _stopSpin: function(){      
        clearInterval(this.spinTimer);
        this.spinTimer = null;
        this.stopOffset = null;
        //这里也应该激发scrollComplete事件
        
        if(this.canFireComplete){
            this.completeScroll();
            this.canFireComplete = false;
        }
    },
    
    _onHandleDragStart: function(e){
        //得到可拖拽的box区域, 将拖拽范围限定        
        this.moveBox = this.getMoveReginBox();
        
        this.enableSyncScroll = false;
                
        if(!isIE8){
            var sf = this;
            this.scrollTimer = setInterval(function(){
                if(sf.direction == 'vertical'){
                    sf._setValue(sf._value);
                }else{
                    sf._setValue(sf._value);
                }            
            }, this.scrollTime);
        }
        
        this.fireEvent('scrollstart', {
            type: 'scrollstart',
            source: this,
            direction: this.direction,
            value: this.value,
            maxValue: this.maxValue
        }); 
    },
    _onHandleDragMove: function(e){
    
        var dm = e.source;
        var moveBox = this.moveBox;
        
        var isVertical = this.direction == 'vertical';
        
        if(isVertical){            
            e.xy[0] =moveBox.x;
            
            if(e.xy[1] < moveBox.y) e.xy[1] = moveBox.y;
            if(e.xy[1] > moveBox.bottom) e.xy[1] = moveBox.bottom;
            
            //先设置一个_value属性, 用来后续的设置
            this._value = this.createvalue(e.xy[1] - moveBox.y, moveBox.height);                        
        }else{
            e.xy[1] =moveBox.y;
            
            if(e.xy[0] < moveBox.x) e.xy[0] = moveBox.x;
            if(e.xy[0] > moveBox.right) e.xy[0] = moveBox.right;
            
            this._value = this.createvalue(e.xy[0] - moveBox.x, moveBox.width);            
        }
        if(isIE8){
            if(this.dragTimer){
                var d = new Date();
                if(d - this.dragTimer >= this.scrollTime){
                    this.dragTimer = d;
                    this._setValue(this._value);
                }
            } 
        }
        
    },
    _onHandleDragComplete: function(e){        
    
        this.enableSyncScroll = true;
        
        clearInterval(this.scrollTimer);                
                        
        if(Edo.isValue(this._value)){
            this._setValue(this._value);
        }
        
        this._value = null;
        
        this.completeScroll();
                
    },
    completeScroll: function(){        
        this.fireEvent('scrollcomplete', {
            type: 'scrollcomplete',            
            source: this,
            direction: this.direction,
            value: this.value
        }); 
    },
    _setValue: function(value){
        if(!Edo.isValue(value)) return;
        if(value < 0) value = 0;
        if(value > this.maxValue) value = this.maxValue;
        if(this.value != value){
            this.value = value;
            this.syncScroll();
            
            this.fireEvent('scroll', {
                type: 'scroll',
                source: this,
                direction: this.direction,
                value: this.value
            });        
        }
    },
    _setMaxValue: function(value){
        if(value < 0 ) value = 0;
        if(this.maxValue != value){
            this.maxValue = value;
            this.relayout('maxValue');
        }
    },
    //根据maxValue,maxValue, value, value,将handle定位
    syncScroll: function(){
        if(!this.el || this.enableSyncScroll == false) return;
        //根据当前的value/value,进行定位
        var handleSize = this.getHandleSize();
        if(handleSize != 0){
            var handleOffset = this.getHandleOffset(handleSize);
            
            var offset = handleOffset + (this.direction == 'vertical' ? this.upHeight : this.upWidth);
            if(this.direction == 'vertical'){
                this.handle.style.top = offset + 'px';
            }else{
                this.handle.style.left = offset + 'px';
            }
        }
    }
    
});
Edo.controls.ScrollBar.regType('scrollbar');