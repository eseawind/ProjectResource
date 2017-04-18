/**
    @name Edo.managers.ResizeManager
    @class
    @single
    @description 尺寸调节管理器   
    @example 


*/ 
Edo.managers.ResizeManager = {
    all: {},
    overlay: null,
    
    startResize: function(cfg){
        //event:事件对象
        //handler:方向
        //handlerEl:方向元素
        //minWidth,minHeight
        //target:   目标对象
        
        Edo.apply(this, cfg,{
            minWidth: 10,
            minHeight: 10,
            onresizestart: Edo.emptyFn,
            onresize: Edo.emptyFn,
            onresizecomplete: Edo.emptyFn
        });
        var drag = new Edo.util.Drag({
            onStart: this.onStart.bind(this),
            onMove: this.onMove.bind(this),
            onStop: this.onStop.bind(this)
        });
        
        //初始化拖拽需要的一些坐标属性    
        var el = this.target.el || this.target;
        this.initXY = this.event.xy;                     //鼠标的初始点击位置
        this.handlerBox = Edo.util.Dom.getBox(this.handlerEl);        //目标handler的初始位置
        this.box = this.box || Edo.util.Dom.getBox(el);
    
        drag.start(this.event);
        
    },  
    /**        
        @name Edo.managers.ResizeManager#reg
        @function 
        @description 注册尺寸调节器
        @param {Object} config 尺寸调节配置对象
<pre>
{
    target: 目标组件对象,
    transparent: false, //是否透明
    handlers: [e东,s南,w西,n北, es东南, ws西南, sn西北, en东北]//不传递为['se']
}
</pre>        
    */      
    reg: function(o){
        //target: 目标组件对象
        //transparent:是否透明
        //handlers: [e东,s南,w西,n北, es东南, ws西南, sn西北, en东北]
        //mode: 'in','out','hide'   //在内显示,在外显示,隐藏等...默认在内不显示
        
        var el = o.target.el || o.target;
        var id = el.id;
        if(!id) throw new Error('必须指定id');
        if(this.all[id]) this.unreg(o);            
        this.all[id] = o;
        o.minWidth = o.minWidth || o.target.minWidth ||10;
        o.minHeight = o.minHeight || o.target.minHeight || 10;
        
        o.els = {};
        if(!o.handlers) o.handlers = ['se'];
        for(var i=0; i<o.handlers.length; i++){
            var h = o.handlers[i];
            var s = '<div direction="'+h+'" class="e-resizer e-resizer-'+h+'"></div>';
            var d = Edo.util.Dom.append(el, s);
            d.direction = h;
            if(o.resizable !== false){
                Edo.util.Dom.on(d, 'mousedown', this.onMouseDown, o);
            }
            o.els[h] = d;
        }
        
        if(!o.transparent) Edo.util.Dom.addClass(el, 'e-resizer-over');
        if(o.cls) Edo.util.Dom.addClass(el, o.cls);
        if(o.square) Edo.util.Dom.addClass(el, 'e-resizer-square');
        
    },    
    /**        
        @name Edo.managers.ResizeManager#unreg
        @function 
        @description 注销尺寸调节器
        @param {UIComponent} 组件
    */    
    unreg: function(id){
        id = id.id || id;
        var o = this.all[id];
        if(o){             
            for(var h in o.els){
                var el = o.els[h];
                Edo.util.Dom.clearEvent(el);    
                Edo.util.Dom.removeClass(el, 'e-resizer-over');
                Edo.util.Dom.removeClass(el, 'e-resizer-square');
                Edo.util.Dom.remove(el);
            }                   
            delete this.all[o.id];                                
        }
    },
    //private
    fire: function(e, o){
        
        this.size = this.box = this.getResizeBox(this.event);
        
        e = Edo.apply(e, this);
        o = o || this.target;   
        
        if(o.el){
            o.fireEvent(e.type, e);
        }else{
            Edo.util.Dom.fireEvent(o, e.type, e);
        }
        e['on'+e.type].c(this, e);
    },
    getOverlay: function (cursor){
        var bd = document;
        if(!this.overlay){
            this.overlay = Edo.util.Dom.append(bd.body, '<div class="e-resizer-overlay"></div>');
            Edo.util.Dom.selectable(this.overlay, false);
        }
        Edo.util.Dom.setStyle(this.overlay, "cursor", cursor);        
        Edo.util.Dom.setSize(this.overlay, Edo.util.Dom.getScrollWidth(bd), Edo.util.Dom.getScrollHeight(bd));
        bd.body.appendChild(this.overlay);        
    },
    getResizeBox: function(e){                 
        //拖拽的时候,要注意,不能小于minWidth,minHeight
        var xy = e.xy;
        
        this.offsetX = this.handlerBox.right - this.initXY[0];
        this.offsetY = this.handlerBox.bottom - this.initXY[1]; //右下
        this.offsetX2 = this.initXY[0] - this.handlerBox.x;     //左上
        this.offsetY2 = this.initXY[1] - this.handlerBox.y;
        
        var px = xy[0] + this.offsetX, py = xy[1] + this.offsetY;//当前x,y鼠标位置,加入了偏移   
        var px2 = xy[0] - this.offsetX, py2 = xy[1] - this.offsetY;//当前x,y鼠标位置,加入了偏移   
                 
        
        var x = this.box.x, y = this.box.y, w = this.box.width, h = this.box.height, right = this.box.right, bottom = this.box.bottom;            
        
        var mw = this.minWidth || 0, mh = this.minHeight || 0;
        switch(this.handler){
            case "e":
                w = px - x;
                w = Math.max(mw, w);
                break;
            case "s":
                h = py - y;
                h = Math.max(mh, h);
                break;
            case "se":
                w = px - x;
                w = Math.max(mw, w);
                h = py - y;
                h = Math.max(mh, h);                    
                break;
            case "n":
                if(bottom - py2 < mh){
                    y = bottom - mh;
                }else{
                    y = py2;
                }
                h = bottom - y;
                break;
            case "w":
                if(right - px2 < mw){
                    x = right - mw;
                }else{
                    x = px2;
                }
                w = right - x;
                break;
            case "nw":
                if(bottom - py2 < mh){
                    y = bottom - mh;
                }else{
                    y = py2;
                }
                h = bottom - y;                
                if(right - px2 < mw){
                    x = right - mw;
                }else{
                    x = px2;
                }
                w = right - x;
                break;
            case "ne":
                if(bottom - py2 < mh){
                    y = bottom - mh;
                }else{
                    y = py2;
                }
                h = bottom - y;
                w = px - x;
                w = Math.max(mw, w);
                break;
           case "sw":
                if(right - px2 < mw){
                    x = right - mw;
                }else{
                    x = px2;
                }
                w = right - x;
                h = py - y;
                h = Math.max(mh, h);
                break;
        }        
        return {x: x, y: y, width: w, height: h, right: x + w, bottom: y + h}
    },
    onStart: function(drag){
        this.event = drag.event;
        var t = this.handlerEl;
        
        if(this.autoProxy !== false){        
            this.proxy = Edo.util.Dom.append(document.body, '<div class="e-resizer-proxy"></div>');        
            Edo.util.Dom.setBox(this.proxy, this.box);
        }
        
        this.getOverlay(Edo.util.Dom.getStyle(t, 'cursor'));
        
        
        this.fire({
            type: 'resizestart',
            target: this
        });
    },
    onMove: function(drag){
        this.event = drag.event;        
//        fire({
//            type: 'resize',
//            target: this
//        });
        if(this.autoProxy !== false){        
            var box = this.getResizeBox(this.event);
            Edo.util.Dom.setBox(this.proxy, box);
        }
        this.fire({
            type: 'resize',
            target: this
        });
    },
    onStop: function (drag){
        this.event = drag.event;                
    
        var size = this.getResizeBox(this.event);
        
        if(this.autoResize !== false){
            if(this.target.el){
                this.target.set('size', size);
            }else{
                Edo.util.Dom.setSize(this.target, size.width, size.height);
            }
        }
        Edo.util.Dom.remove(this.overlay);
        if(this.autoProxy !== false){
            Edo.util.Dom.remove(this.proxy);
        }
        
        this.fire({
            type: 'resizecomplete',
            target: this
        });
        this.autoResize = this.handler = this.handlerEl = this.proxy = this.initXY = this.handlerBox = this.box = this.autoProxy = null;
    },
    onMouseDown: function(e){
        if(e.button != 0) return false;
                
        var t = e.target;
        
        //得到目标DOM元素
        var el = this.target.el || this.target;        
        //得到拖拽方向
        var d = t.direction;
        
        if(d && t.parentNode === el){
//            Edo.managers.ResizeManager.startResize({
//                target: this.target,
//                event: e,
//                handler: d,
//                handlerEl: t,
//                minWidth: this.minWidth,
//                minHeight: this.minHeight
//            });
            Edo.managers.ResizeManager.startResize(Edo.applyIf({
                target: this.target,
                event: e,
                handler: d,
                handlerEl: t,
                minWidth: this.minWidth,
                minHeight: this.minHeight
            }, this));
            e.stop();
        }
    }
};