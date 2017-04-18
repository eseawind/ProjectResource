﻿/**
    @name Edo.containers.Window
    @class 
    @typeName window
    @description 弹出面板容器类
    @extend Edo.containers.Panel
    @example 
*/ 
Edo.containers.Window = function(){
    Edo.containers.Window.superclass.constructor.call(this);        
};
Edo.containers.Window.extend(Edo.containers.Panel,{
    /**
        @name Edo.containers.Window#renderTo
        @property
        @default '#body'
    */    
    renderTo: '#body',
    /**
        @name Edo.containers.Window#shadow
        @property
        @default true
    */
    shadow: true,
    /**
        @name Edo.containers.Window#minWidth
        @property
        @default 180
    */
    minWidth: 180,
    /**
        @name Edo.containers.Window#minHeight
        @property
        @default 80
    */
    minHeight: 80,
    
    //elCls: 'e-window e-dialog e-group',
    
    initEvents: function(){
        Edo.containers.Window.superclass.initEvents.call(this);
        
        this.on('mousedown', this.onMouseDown, this);
    },
    
    onMouseDown: function(e){
        if(e.within(this.headerCt)){
            Edo.managers.DragManager.startDrag({
                event: e,
                delay: 0,
                capture: false,
                autoDragDrop: true,                          
                proxy: true,
                proxyCls: 'e-dragdrop-proxy',                
                dragObject: this
            });            
        }
        this.focus();
    },
    /**
        @name Edo.containers.Window#show
        @function
        @description 根据坐标显示面板
        @param {Number} x 
        @param {Number} y 
        @param {Boolean} modal 是否使用遮罩
    */
    show: function(x, y, modal){ 
        this.render(this.renderTo);
        this.addCls('e-drag-title');
        Edo.managers.PopupManager.createPopup({
            target: this,
            x: x,
            y: y,
            modal: modal
        });
        return this;
    },
    /**
        @name Edo.containers.Window#show
        @function
        @description 根据坐标显示面板
        @param {Number} x 
        @param {Number} y 
        @param {Boolean} modal 是否使用遮罩
    */
    hide: function(){
        Edo.managers.PopupManager.removePopup(this);
        return this;
    }
});

Edo.containers.Window.regType('window');