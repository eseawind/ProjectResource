/**
    @name Edo.controls.SWF
    @class
    @typeName swf
    @description flash对象
    @extend Edo.controls.Control
*/

Edo.controls.SWF = function(){        
    Edo.controls.SWF.superclass.constructor.call(this);
}
Edo.controls.SWF.extend(Edo.controls.Control,{
    /**
        @name Edo.controls.SWF#minWidth
        @property
        @type Number
        @default 100        
    */
    minWidth: 100,
    /**
        @name Edo.controls.SWF#minHeight
        @property
        @type Number
        @default 50        
    */
    minHeight: 50,
    /**
        @name Edo.controls.SWF#defaultWidth
        @property
        @type Number
        @default 100        
    */
    defaultWidth: 100,
    /**
        @name Edo.controls.SWF#defaultHeight
        @property
        @type Number
        @default 50
    */
    defaultHeight: 50,
    
    flashVersion : '9.0.45',
    /**
        @name Edo.controls.SWF#backgroundColor
        @property
        @type color
        @default #ffffff
        @description 背景色
    */
    backgroundColor: '#ffffff',
    /**
        @name Edo.controls.SWF#wmode
        @property
        @type String
        @default opaque
        @description 透明模式
    */
    wmode: 'opaque',   
    /**
        @name Edo.controls.SWF#url
        @property
        @type String
        @description flash地址
    */
    url: undefined,
    swfId : undefined,
    swfWidth: '100%',
    swfHeight: '100%',
    
    expressInstall: false,
    
    createChildren: function(el){          
        Edo.controls.SWF.superclass.createChildren.call(this, el);        
        
        var params = {
            allowScriptAccess: 'always',
            bgcolor: this.backgroundColor,
            wmode: this.wmode
        }, vars = {
            allowedDomain: document.location.hostname,
            //elementID: this.getId(),
            elementID: this.id
            //,eventHandler: 'Ext.FlashEventProxy.onEvent'
        };
        
        var id = this.id + 'swf';
        
        Edo.util.Dom.append(this.el, '<div id="'+id+'"></div>');
        
        new swfobject.embedSWF(this.url, id, this.swfWidth, this.swfHeight, this.flashVersion,
            this.expressInstall ? Edo.controls.SWF.EXPRESS_INSTALL_URL : undefined, vars, params);
        
        this.swf = Edo.getDom(id);
    },
    syncSize: function(){
        Edo.controls.SWF.superclass.syncSize.a(this, arguments);
        
        
//        this.editor.wrapping.style.width = (this.realWidth-1)+'px';
//        this.editor.wrapping.style.height = (this.realHeight-1)+"px";
//        
//        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    }
});

Edo.controls.SWF.regType('swf');   
Edo.controls.SWF.EXPRESS_INSTALL_URL = 'http:/' + '/swfobject.googlecode.com/svn/trunk/swfobject/expressInstall.swf';
