/**
    @name Edo.managers.DragManager
    @class 
    @single
    @description 拖拽管理器.在拖拽过程中,会在拖拽对象上激发dragstart等拖拽事件
    @example         
*/

Edo.managers.DragManager = {
    drops: {},
    
    event: null,
    dragObject: null,
    dropObject: null,       //当enableDrop=true时,此属性可能为有值
    dragData: null,
    alpha: .5,
    
    
    isDragging: false,
        
    enableFeedback: false,
    feedback: 'none',
    
    drag: null,        
    
    /**        
        @name Edo.managers.DragManager#startDrag
        @function         
        @param {Object} config 拖拽配置对象        
        @description 启动拖拽器<br/>
<pre>
    {
        enableDrop: false, //是否允许投放操作
        dragObject: null,   //启动拖拽器的组件对象,必须从UIComponent派生,
        dragData: null,     //拖拽的数据
        event: e,           //当前鼠标事件对象
        xOffset: 0,         //偏移
        yOffset: 0,
        alpha: 1,       //拖拽物透明度
        proxy: true     //拖拽代理元素:true:自身的拷贝,false:自身,dom:拖拽dom元素
    }
</pre>        
    */    
    startDrag: function(cfg){            
        //1)proxy:    true:自身的拷贝,false:自身,dom:拖拽dom元素
        //2)enableDrop:       是否投放
        //dragObject:   UIComplete:启动拖拽的组件,dom
        //dragData:               拖拽数据
        //event:               当前鼠标事件对象            
        //xOffset:              //不传值,则自动计算
        //yOffset:
        //alpha:                    拖拽代理的透明度(默认0.5)
        //feedback:                 表明此次拖拽的操作效果:copy,move,none   ?
        
        if(!cfg.dragObject) throw new Error("必须有拖拽启动对象");
        if(!cfg.event) throw new Error("必须有拖拽初始事件对象");
        
        if(cfg.event.button != 0) return false;
        
        if(this.isDragging){
            this.drag.stop(this.event);
        }

        this.enableDrop = this.proxy = this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;
        
        drag = new Edo.util.Drag({
            delay: this.delay,
            capture: this.capture,
            onStart: this.onStart.bind(this),
            onMove: this.onMove.bind(this),
            onStop: this.onStop.bind(this)
        });
        this.drag = drag;
        
        Edo.apply(this, cfg, {
            delay: 80,
            capture: false,
            ondragstart: Edo.emptyFn,
            ondragmove: Edo.emptyFn,
            ondragcomplete: Edo.emptyFn,
            
            ondropenter: Edo.emptyFn,
            ondropover: Edo.emptyFn,
            ondropout: Edo.emptyFn,
            ondropmove: Edo.emptyFn,
            ondragdrop: Edo.emptyFn
        });        
        
        this.initEvent = this.event;
        
        
        drag.start(this.event);
                    
    },
    /**        
        @name Edo.managers.DragManager#acceptDragDrop
        @function 
        @description 当一个拖拽器移动到一个组件上时, 是否进行允许投放操作        
    */     
    acceptDragDrop: function(){
        this.canDrop = true;
    },
    /**        
        @name Edo.managers.DragManager#regDrop
        @function 
        @description 注册投放区Drop对象      
    */    
    regDrop: function(cmp){
        this.drops[cmp.id] = cmp;
    },
    /**        
        @name Edo.managers.DragManager#unregDrop
        @function 
        @description 注销投放区Drop对象      
    */    
    unregDrop: function(cmp){
        delete this.drops[cmp.id];
    },
    //private
    fire: function(e, o){
        var dm = Edo.managers.DragManager;
        //计算出elxy
        dm.xy = [dm.now[0]+dm.xOffset, dm.now[1]+dm.yOffset];    
        
        Edo.apply(e, dm);
        o = o || dm.dragObject;   
        
        if(o.el){
            o.fireEvent(e.type, e);
        }else{
            Edo.util.Dom.fireEvent(o, e.type, e);
        }
        e['on'+e.type].c(dm, e);
        
        //status = e.xy;
        //dm.now = e.now;//修改坐标的关键
        dm.now = [e.xy[0] - dm.xOffset, e.xy[1] - dm.yOffset];
        
        //status = dm.xy;
    },
    findDrop: function(e, t){        
        t = t || e.target;
        while(t && t !== document){
            //这里可以优化:将所有drop保存一个idHash,就不需要for,而直接判断是否有了.
            for(var id in this.drops){
                var drop = this.drops[id];
                var el = drop.el || drop;
                if(el === t) {                    
                    return drop;
                }
            }
            t = t.parentNode;
        }
    },
    onStart: function(drag){
        this.event = drag.event;
        this.now = drag.now;        
        
        this.el = this.dragObject.el || this.dragObject;                
        var xy = Edo.util.Dom.getXY(this.el);
        
        this.initXY = xy;
            
        if(!this.xOffset && this.xOffset !== 0) this.xOffset = xy[0] - drag.now[0];
        if(!this.yOffset && this.yOffset !== 0) this.yOffset = xy[1] - drag.now[1];
            
        this.isDragging = true;
        this.fire({
            type: 'dragstart',
            source: this
        });
        
        if(this.proxy === true){
            this.proxy = this.el.cloneNode(false);
            //this.proxy.className = 'e-dragdrop-proxy';
            
            Edo.util.Dom.setStyle(this.proxy, this.proxyStyle);
            Edo.util.Dom.addClass(this.proxy, this.proxyCls);
            
            document.body.appendChild(this.proxy);
            
            var size = Edo.util.Dom.getSize(this.el);
            Edo.util.Dom.setSize(this.proxy, size.width, size.height);
            
            this.removeProxy = true;
        }else if(!this.proxy){
            this.proxy = this.el;
        }
        
               
        this._zIndex = Edo.util.Dom.getStyle(this.proxy, 'z-index');
        this._opacity = 1;//Edo.util.Dom.getOpacity(this.proxy);          
        Edo.util.Dom.setStyle(this.proxy, 'z-index', 9999999);
        Edo.util.Dom.setOpacity(this.proxy, this.alpha);
        
        var position = Edo.util.Dom.getStyle(this.proxy, 'position');
        if(position != 'relative' && position != 'absolute'){
            this.proxy.style.position = 'relative';
        }
        
        Edo.util.Dom.setStyle(this.proxy, this.dragStyle);
        
        Edo.util.Dom.setXY(this.proxy, xy);
        
        this.leftTop = [parseInt(Edo.util.Dom.getStyle(this.proxy,'left')) || 0, parseInt(Edo.util.Dom.getStyle(this.proxy,'top')) || 0];
        
        //document.title= new Date();
    },
    onMove: function(drag){
    
        if(!this.isDragging) return;
        this.event = drag.event;
        this.now = drag.now;
        this.move = true;
                     
        //先激发dragmove事件,监听dragmove,修改drag.now,则可以改变拖拽的坐标    
        this.fire({
            type: 'dragmove',
            source: this        
        });
        
        //Edo.util.Dom.setXY(this.proxy, [drag.now[0] + this.xOffset, drag.now[1] + this.yOffset ]);
        if(this.move !== false && this.proxy){
            var s = this.proxy.style;
            s.left = (this.now[0] + this.xOffset - this.initXY[0] + this.leftTop[0]) + 'px';
            s.top = (this.now[1] + this.yOffset - this.initXY[1] + this.leftTop[1]) + 'px';
        }
        
        if(this.enableDrop){
        
            var el = this.event.target;
            
            //处理!!!
            var display = this.proxy.style.display;
            this.proxy.style.display = 'none';
            el = document.elementFromPoint(this.now[0], this.now[1]);
            while(el && el != document){
                if(el.tagName) break;
                el = el.parentNode;
            }
            this.proxy.style.display = display;
            
            
            var now = this.dropObject;          //保存当前的drop
            
            this.dropObject = null;
                            
            //这个循环是必要的!不要轻易去掉                        
            while(el && el !== document){
                var drop = this.findDrop.call(this, this.event, el);
                if(!drop) {                     //没找到新drop,则退出
                    if(now){
                        this.dropObject = now;
                        this.fire({
                            type: 'dropout',
                            source: this.dropObject
                        }, this.dropObject);                    
                    }
                    this.dropObject = null;
                    break;
                }
                            
                this.dropObject = drop; //将新drop赋予dropObject,并测试,如果通过,则break                        
                
                if(drop == now) break;      //如果原来的drop和新的drop是同一个,则不需要进行置换
                //如果这个等式成立,那么drop原先已经经过dropenter判断过了.
                
                this.canDrop = false;
                this.fire({
                    type: 'dropenter',
                    source: this.dropObject
                }, this.dropObject);
                if(this.canDrop){
                    if(now){
                        this.dropObject = now;
                        this.fire({                          //激发旧drop的out
                            type: 'dropout',
                            source: this.dropObject
                        }, this.dropObject);
                    }
                    this.dropObject = drop;
                    this.fire({                          //激发新drop的over
                        type: 'dropover',
                        source: this.dropObject
                    }, this.dropObject);
                    break;
                }else{
                    el = (drop.el || drop).parentNode;  //没通过,继续循环
                }
            }
            
            
        }
        
        //dropmove,当有drop对象时激发
        if(this.dropObject){
            this.fire({
                type: 'dropmove',
                source: this.dropObject
            }, this.dropObject);
        }
    },
    onStop: function(drag){      
        if(!this.isDragging) return ;  
        this.event = drag.event;
        this.now = drag.now;                    
        
        this.onMove(drag);
        
        if(this.dropObject){
            this.fire({
                type: 'dropout',
                source: this.dropObject
            }, this.dropObject);
            
            this.fire({
                type: 'dragdrop',
                source: this.dropObject
            }, this.dropObject);                                      
        }
        
        var xy = [this.now[0] + this.xOffset, this.now[1] + this.yOffset ];
        
        if(this.autoDragDrop){
            if(this.dragObject.set){
                this.dragObject.set('XY', xy);
            }else{
                Edo.util.Dom.setXY(this.dragObject, xy);
            }
        }
        
        this.fire({
            type: 'dragcomplete',
            source: this
        });                
              
        this.isDragging = false;
        this.alpha = .5;
        //this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;
        this.xy = this.enableDrop = this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;
        //this.proxy.style.position = this._position;
        
        
        Edo.util.Dom.setStyle(this.proxy, 'z-index', this._zIndex);
        Edo.util.Dom.setOpacity(this.proxy, this._opacity);
        if(this.el !== this.proxy && this.removeProxy) {
            Edo.util.Dom.remove(this.proxy);
            this.removeProxy = false;
        }
        this.proxy = null;        
    }
    
};

Edo.managers.DragProxy = function(cfg){
    Edo.apply(this, cfg,{
        feedback: 'no',   //copy,move,no,yes,add,none,between,preend,append
        html: ' '
    });
};
Edo.managers.DragProxy.prototype = {
    setFeedback: function(feedback){
        if(this.feedback != feedback){
            Edo.util.Dom.removeClass(this.el, this.getFeedbackCls(this.feedback));        
            this.feedback = feedback;
            Edo.util.Dom.addClass(this.el, this.getFeedbackCls(this.feedback));
        }
    },    
    setHtml: function(html){    
        this.innerEl.innerHTML = html;
    },
    getFeedbackCls: function(value){
        return 'e-dragproxy-'+value;
    },
    render: function(p){
        this.el = Edo.util.Dom.append(p || document.body,
                '<div class="e-dragproxy '+this.getFeedbackCls(this.feedback)+'"><div class="feedback"></div><div class="inner">'+this.html+'</div></div>');
        this.feedbackEl = this.el.firstChild;
        this.innerEl = this.el.lastChild;
        
        if(this.shadow) Edo.util.Dom.addClass(this.el, 'e-shadow');
        
        var size = Edo.util.Dom.getSize(this.el);
        if(size.width < 25) Edo.util.Dom.setWidth(this.el, 25);
        if(size.height < 24) Edo.util.Dom.setHeight(this.el, 24);
        return this;
    },
    destroy: function(){
        Edo.util.Dom.remove(this.el);
        this.el = this.feedbackEl = this.innerEl = null;
    }
}

Edo.managers.DragProxy.getUpDownProxy = function(){
    if(!this.up){
        this.up = Edo.util.Dom.append(document.body, '<div class="e-dragproxy-up"></div>');
    }
    if(!this.down){
        this.down = Edo.util.Dom.append(document.body, '<div class="e-dragproxy-down"></div>');
    }
    this.up.style.visibility = 'visible';
    this.down.style.visibility = 'visible';
    
    return [this.up, this.down];
}
Edo.managers.DragProxy.hideUpDownProxy = function(){
    if(this.up) {
        this.up.style.visibility = 'hidden';
        
    }        
    if(this.down) {
        
        this.down.style.visibility = 'hidden';
    }        
}
Edo.managers.DragProxy.clearUpDownProxy = function(){
    if(this.up) {
        Edo.util.Dom.remove(this.up);
        this.up = null;
    }        
    if(this.down) {
        Edo.util.Dom.remove(this.down);
        this.down = null;
    }        
}

Edo.managers.DragProxy.getInsertProxy = function(direction){
    if(!this.insert){
        this.insert = Edo.util.Dom.append(document.body, '<div></div>');
    }
    this.insert.className = 'e-dragproxy-insert'+(direction||'');
    this.insert.style.visibility = 'visible';
    return this.insert;
}
Edo.managers.DragProxy.hideInsertProxy = function(){
    if(this.insert) {
        this.insert.style.visibility = 'hidden';
        
    }            
}
