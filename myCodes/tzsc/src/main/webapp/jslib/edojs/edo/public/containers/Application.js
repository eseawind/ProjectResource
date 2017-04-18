/**
    @name Edo.containers.Application
    @class 
    @typeName app
    @description 自适应浏览器尺寸的顶级容器
    @extend Edo.containers.Box
*/ 
Edo.containers.Application = function(){    
    Edo.containers.Application.superclass.constructor.call(this);
};
Edo.containers.Application.extend(Edo.containers.Box, {
    fireTimer: null,
    
    /**
        @name Edo.containers.Application#minWidth
        @property
        @default 400
    */ 
    minWidth: 400,
    /**
        @name Edo.containers.Application#minHeight
        @property
        @default 200
    */ 
    minHeight: 200,
    
    elCls: 'e-app e-box e-div',
    
    notHasParent: 'the app module cannot have the father object',
    
    initEvents: function(){                
        if(!this.design){
            if(this.parent) throw new Error(this.notHasParent);
            Edo.util.Dom.on(window, 'resize', function(e){
            
                //if(isIE){
                    if(this.fireTimer) clearTimeout(this.fireTimer);                   
                    this.fireTimer = this.onWindowResize.defer(100, this, [e]);                   
    //            }else{
    //                this.onWindowResiz(e);
    //            }
            }, this);                        
        }
        Edo.containers.Application.superclass.initEvents.call(this);        
    },
    measure: function(){
        //if(this.id == 'design_app') debugger
        if(!this.design) this.syncViewSize();        
        Edo.containers.Application.superclass.measure.call(this);        
    },
    syncViewSize: function(){
        var dh = Edo.util.Dom, doc = document;
        
        
        var w = dh.getViewWidth(doc);
        var h = dh.getViewHeight(doc);
        
        this.width = w;
        this.height = h;
        
        
    },
    onWindowResize: function(e){    
        
        this.syncViewSize();
        
        this.relayout('size', this);
        
        this.fireTimer = null;
    }, 
//    render: function(dom){    
//        if(!this.design) dom = '#body';
//        Edo.containers.Application.superclass.render.call(this, dom);
//    },
    destroy : function(){
        //DomHelper.clearEvent(window);
        Edo.containers.Application.superclass.destroy.call(this);
    }
});
Edo.containers.Application.regType('app');