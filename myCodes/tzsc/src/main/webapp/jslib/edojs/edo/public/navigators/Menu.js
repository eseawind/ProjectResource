/**
    @name Edo.navigators.Menu
    @class
    @typeName menu
    @description 菜单
    @extend Edo.navigators.Navigator
*/
Edo.navigators.Menu = function(){
    Edo.navigators.Menu.superclass.constructor.call(this);    
    
};
Edo.navigators.Menu.extend(Edo.navigators.Navigator,{
    /**
        @name Edo.navigators.Menu#layout
        @property
        @default 'vertical'
    */
    layout: 'vertical',
    /**
        @name Edo.navigators.Menu#minWidth
        @property
        @default 40
    */
    minWidth: 40,
    minHeight: 20,
    defaultHeight: 20,
    /**
        @name Edo.navigators.Menu#defaultWidth
        @property
        @default 100
    */
    defaultWidth: 100,
    /**
        @name Edo.navigators.Menu#verticalGap
        @property
        @default 0
    */
    verticalGap: 0,
    /**
        @name Edo.navigators.Menu#padding
        @property
        @default [1,1,1,1]
    */
    padding: [1,1,1,1],
    
    startWidth: 8,
    startHeight: 8,
    endWidth: 8,
    endHeight: 8,
    
    /**
        @name Edo.navigators.Menu#autoHide
        @property
        @type Boolean
        @default false
        @description 控制当poup时,是否自动隐藏
    */
    autoHide: false,        
    
    elCls: 'e-box e-menu ',//e-toolbar e-toolbar-over
    
    initEvents: function(){
        this.on('click', this._onClick, this);
        
        Edo.navigators.Menu.superclass.initEvents.c(this);
    },
    _onClick: function(e){
        
        if(this.autoHide){
            this.hideMenu();            
        }
    },
    hideMenu: function(){        
        Edo.managers.PopupManager.removePopup(this);
    
        if(this.owner && this.owner.type == 'button'){
            this.owner.hidePopup();
            
            
            if(this.owner.parent && this.owner.parent.type == "menu"){            
                this.owner.parent.hideMenu();
            }
        }
    },
    _onChildOver: function(e){
        var btn = e.source;
        var oi = this.overItem;
        if(oi && oi.menu){
            oi.hidePopup();            
        }
        this.overItem = btn;
        if(btn.menu) {
            var box = btn._getBox(true);
            if(this.layout == 'vertical'){
                btn.showPopup(box.x + box.width, box.y, false, null, -btn.realWidth, btn.realHeight);
            }else{
                btn.showPopup();
            }
            //btn.menu.hideMenu();
        }
    },

    addChildAt: function(){
        var o = arguments[1];
        if(this.layout == 'vertical'){
            o.width = '100%';
            o.icon = o.icon || ' ';
            if(o.menu) o.arrowMode = 'menu';
            else o.arrowMode = null;            
        }        
        o.showMenu = false;
        
        var c = Edo.navigators.Menu.superclass.addChildAt.apply(this, arguments);        
        c.on('mouseover', this._onChildOver, this);        
        return c;
    }
});
Edo.navigators.Menu.regType('menu');