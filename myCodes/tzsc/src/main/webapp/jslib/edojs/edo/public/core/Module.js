/**
    @name Edo.core.Module
    @class
    @typeName module
    @description 模块加载器.能加载指定路径的文件,如user.xml, user.html, user.jsp等.
    @extend Edo.core.UIComponent
    @example 


*/ 
Edo.core.Module = function(){
    /**
        @name Edo.core.Module#load
        @event
        @description 
    */

    /**
        @name Edo.core.Module#unload
        @event
        @description 
    */

    /**
        @name Edo.core.Module#beforeunload
        @event
        @description 
    */    
    Edo.core.Module.superclass.constructor.call(this);
};
Edo.core.Module.extend(Edo.core.UIComponent,{
    elCls: 'e-module e-div',
    /**
        @name Edo.core.Module#autoMask
        @property
        @default true
        @description 自动遮罩
    */    
    autoMask: true,
    /**
        @name Edo.core.Module#src
        @property
        @default ''
        @description 模块链接的页面地址
    */        
    src: '',
    _setSrc: function(value){
        if(this.src != value){
            this.src = value;
            this.load(value);
            this.changeProperty('src', value);
        }
    },
    _setHtml: function(){},
    
    createChildren: function(){
        Edo.core.Module.superclass.createChildren.apply(this, arguments);        
        if(this.src){            
            this.load(this.src);
        }
    },
        
    syncSize: function(){
        Edo.core.Module.superclass.syncSize.call(this);
        if(this.iframe){
            var size = Edo.util.Dom.getSize(this.el, true);
            this.iframe.style.width = size.width+'px';
            this.iframe.style.height = size.height+'px';
        }
    },
//    doCreateChildWindow: function(){
//        var cEdo = this.childWindow.Edo;
//        if(cEdo){                    
//            cEdo.create({
//                type: 'button',
//                redner: '#body'
//            });
//            this.configGet = null;
//            
//            this.fireEvent('load',{
//                type: 'load',
//                source: sf                
//            });
//        }else{
//            this.doCreateChildWindow.defer(100, this);
//        }
//    },
    doLoad: function (){   
        if(this.configGet === false) {
            doLoad.defer(50, this);
            return;
        }
        
        this.childWindow = this.iframe.contentWindow;
        
//        if(this.isConfig){               //暂时不支持此功能:以后应该是edo字符串库,直接操作滴
//            //引用js和css资源,并...从ajax得到配置xml或json
//            var html = '<html><body><style type="text/css">html,body{width:100%;height:100%;overflow:hidden;}</style></body></html>'+Edo_core_Module_res+'<script type="text/javascript"></script>';
//            var doc = this.childWindow.document;
//            doc.open();
//            doc.write(html);
//            doc.close();
//            
//            this.doCreateChildWindow.defer(100, this);            
//            return;
//        }
        
        if(this.autoMask !== false) this.unmask();
        
        this.fireEvent('load',{
            type: 'load',
            source: this,
            src: this.src,
            window: this.childWindow
        });
    },
    /**
        @name Edo.core.Module#load
        @function
        @description 加载一个指定url的文件
        @param {String} src 可以是edo的xml定义文件,也可以是任何一个静态或动态的网页
        @param {Boolean} isConfig 说明src引用的是否是配置(xml,json)
    */
    load: function(src, isConfig){
        this.src = src;
        if(!this.el) return false;
        
        if(this.unload() === false) return;
        
        if(this.autoMask !== false) this.mask();
        
        this.src = src;
        this.isConfig = isConfig;
        //        
        //debugger
        var im = this.iframe = document.createElement('iframe');
        im.frameBorder = 0;
        //im.scrolling = 'no';
        im.style.width = (this.realWidth||0)+'px';
        im.style.height = (this.realHeight||0)+'px';
        
        this.el.appendChild(im);        
        
        if(isConfig){
            this.configGet = false;
            var sf = this;
            Edo.util.Ajax.request({
                url: src,
                type: 'get',
                onSuccess: function(text){
                    sf.configGet = text;
                },
                onFail: function(){
                    throw new Error("没有获得module的配置对象资源");
                }
            });
            //1)ajax src
            //2)iframe创建
            //两者都是异步的,所以,需要一个同步
        }else{
            im.src = src;
        }
        
        function checkIFrame(){
        
            var win = this.iframe.contentWindow;
            try{
                if(win && win.document && win.document.readyState=="complete"){                
                    this.doLoad();
                }else{
                    checkIFrame.defer(20, this);    
                }
            }catch(e){            
                this.doLoad(false);
            }
        }        
        if(isGecko && !isChrome){
            var sf = this;
            im.onload = this.doLoad.bind(this);                
        }else{
            checkIFrame.defer(20, this);
        }
        
        
        //优化
        //1)xml的获取由module进行
        //2)同时生成iframe,不需要等iframe加载完毕才进行xml的获取
    },
    /**
        @name Edo.core.Module#unload
        @function
        @description 卸载一个当前加载的页面    
    */
    unload: function(){
        if(this.src && this.iframe){
            //卸载不通过,则不进行更换src操作
            if(this.fireEvent('beforeunload', {type:'beforeunload',source: this}) === false) return false;
            
            this.iframe.src = 'javascript:false;';//'abort:blank';//
            Edo.util.Dom.remove(this.iframe);
            this.childWindow = this.src = this.iframe = this.isConfig = null;            
            this.fireEvent('unload', {
                type: 'unload',
                source: this
            });
        }
    }
});
Edo.core.Module.regType('module');