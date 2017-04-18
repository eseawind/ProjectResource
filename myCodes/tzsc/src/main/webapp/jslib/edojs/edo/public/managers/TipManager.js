/**
    @name Edo.managers.TipManager
    @class 
    @single
    @description 提示信息管理器   
    @example 
*/ 
Edo.managers.TipManager = {        
    tips: {},    
    //tpl: new Edo.util.Template('<table class="e-tip <%=this.cls%>" cellspacing="0" border="0" cellpadding="0"><tr class="e-group-t"><td class="e-group-tl"></td><td class="e-group-tc"></td><td class="e-group-tr"></td></tr><tr class="e-group-m"><td class="e-group-ml"></td><td class="e-group-mc"><%if(this.showTitle){%><div class="e-tip-header"><%= this.title%><%if(this.showClose){%><div class="e-tip-close" onclick="Edo.managers.TipManager.hide(\'<%= this.target.id%>\')"></div><%}%></div><%}%><div class="e-group-body"><%= this.html%></div></td><td class="e-group-mr"></td></tr><tr class="e-group-b"><td class="e-group-bl"></td><td class="e-group-bc"></td><td class="e-group-br"></td></tr></table>'),
    tpl: new Edo.util.Template('<div class="e-tip <%=this.cls%>"><%if(this.showTitle){%><div class="e-tip-header"><%= this.title%><%if(this.showClose){%><div class="e-tip-close" onclick="Edo.managers.TipManager.hide(\'<%= this.target.id%>\')"></div><%}%></div><%}%><div class="e-group-body"><%= this.html%></div></div>'),
    show: function(x, y, cfg){
        var target =cfg.target;
        clearTimeout(cfg.hideTimer);
        if(!cfg.tipEl){
            
            var s = this.tpl.run(cfg);
           
            cfg.tipEl = Edo.util.Dom.append(document.body, s);
            
//            var w = Edo.util.Dom.getWidth(cfg.tipEl);
//            Edo.util.Dom.setWidth(cfg.tipEl, w);
            //Edo.util.Dom.repaint(cfg.tipEl);
        }                            
        Edo.util.Dom.setXY(cfg.tipEl, [x+cfg.mouseOffset[0], y+cfg.mouseOffset[1]]);
                    
    },
    hide: function(cfg){
        if(!cfg) return;
        if(typeof cfg === 'string') cfg = this.tips[cfg];                                                
        Edo.util.Dom.remove(cfg.tipEl);
        cfg.tipEl = null;
    },
    clear: function(tip){
        if(tip.tipEl){
            Edo.util.Dom.remove(tip.tipEl);
            tip.tipEl = null;
        }
        return tip;
    },
    /**        
        @name Edo.managers.TipManager#reg
        @function 
        @description 注册提示器
        @param {Object} config 提示器配置对象<br/>
<pre>
{
    target: null,               //注册提示的对象
    cls: '',                    //
    html: '',
    title: '',
    
    ontipshow: Edo.emptyFn,
    ontiphide: Edo.emptyFn,
    
    showTitle: false,           //是否显示标题
    autoShow: true,             //是否mouseover显示
    autoHide: true,             //是否mouseout隐藏
    showClose: false,           //是否显示关闭按钮
    showImage: false,           //
    trackMouse: false,          //是否跟随鼠标移动而显示
    showDelay: 200,             //显示延迟
    hideDelay: 100,             //隐藏延迟            
    
    mouseOffset:[15,18]         //显示的偏移坐标
}
</pre>        
    */          
    reg: function(cfg){
        cfg = Edo.apply({},cfg, {
            target: null,               //注册提示的对象
            cls: '',                    //
            html: '',
            title: '',
            
            ontipshow: Edo.emptyFn,
            ontiphide: Edo.emptyFn,
            
            showTitle: false,           //是否显示标题
            autoShow: true,             //是否mouseover显示
            autoHide: true,             //是否mouseout隐藏
            showClose: false,           //是否显示关闭按钮
            showImage: false,           //
            trackMouse: false,          //是否跟随鼠标移动而显示
            showDelay: 200,             //显示延迟
            hideDelay: 100,             //隐藏延迟            
            
            mouseOffset:[15,18]         //显示的偏移坐标
        });
        
        if(!cfg.target) return false;
        
        //注册提示配置信息
        this.unreg(cfg.target);
        
        var target = cfg.target;
        this.tips[target.id] = cfg;

        //if(target.id == 'btn2') debugger;
        if(cfg.autoShow){
            if(cfg.trackMouse){
                target.on('mousemove', this.onmousemove, this);
            }else{
                target.on('mouseover', this.onmouseover, this);
            }
        }
        //如果是autoHide,则绑定上去,要不不绑定
        target.on('mouseout', this.onmouseout, this);
        return cfg;
    },
    /**        
        @name Edo.managers.TipManager#unreg
        @function 
        @description 注销提示器
        @param {UIComponent} 组件
    */        
    unreg: function(cmp){
        //取消提示
        var tip = this.tips[cmp.id];
        if(tip){
            this.hide(tip);
            var target = tip.target;
            target.un('mouseover', this.onmouseover, this);
            target.un('mousemove', this.onmousemove, this);
            target.un('mouseout', this.onmouseout, this);
            
            delete this.tips[cmp.id];
        }
    },
    //private
    onmouseover: function (e){                        
        var tip = this.tips[e.source.id];
        if(tip.tipEl){
            var el = e.getRelatedTarget();        
            if(Edo.util.Dom.contains(e.source.el, el)) return;
        }
        if(tip.ontipshow(e) !== false){
            this.showTimer = this.show.defer(tip.showDelay, this, [e.xy[0], e.xy[1], tip]);
        }else{
            if(tip.autoHide) tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);
        }
    },
    onmousemove: function (e){
        var tip = this.tips[e.source.id];
        
        if(tip.ontipshow(e) !== false){
            this.show(e.xy[0], e.xy[1], tip);
        }else{
            if(tip.autoHide) tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);
        }
    },
    onmouseout: function(e){
        var el = e.getRelatedTarget();
        var tip = this.tips[e.source.id];
        if(Edo.util.Dom.contains(e.source.el, el)) return;        
        //alert(1);
        if(tip.autoHide){       
            tip.ontiphide(e);
            if(this.showTimer) {
                clearTimeout(this.showTimer);
                this.showTimer = null;
            }
            
            tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);
            
        }
    }    
}