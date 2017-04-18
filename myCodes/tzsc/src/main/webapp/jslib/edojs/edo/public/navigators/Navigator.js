/*
    Navigator导航容器:
        增加左右(horizontal),上下(vertical)导航按钮.
        从Box派生,增加navCtWidth逻辑.
        startBtn
        endBtn
        
*/
/**
    @name Edo.navigators.Navigator
    @class 
    @typeName navigator, nav
    @description 实现导航功能
    @extend Edo.containers.Box
    @example 


*/ 
Edo.navigators.Navigator = function(){
    Edo.navigators.Navigator.superclass.constructor.call(this);
    
    //titleclick
};
Edo.navigators.Navigator.extend(Edo.containers.Box,{
    /**
        @name Edo.navigators.Navigator#layout
        @property
        @default 'horizontal'
    */
    layout: 'horizontal',
    scrollOffset: 30,
    
    startVisible: true,
    endVisible: true,
    startWidth: 18,
    startHeight: 18,
    endWidth: 18,
    endHeight: 18,
    startCls: '',
    endCls: '',
    
    //导航容器,滚动条必须设置为off
    /**
        @name Edo.navigators.Navigator#horizontalScrollPolicy
        @property
        @default 'off'
    */
    horizontalScrollPolicy: 'off',
    /**
        @name Edo.navigators.Navigator#verticalScrollPolicy
        @property
        @default 'off'
    */
    verticalScrollPolicy: 'off',
    
    //这里不加measure应该是对的
//    measure: function(){
//        Edo.navigators.Navigator.superclass.measure.call(this);
//        if(this.startVisible){
//            if(this.layout == 'horizontal'){
//                this.realWidth += this.startWidth;                
//            }else if(this.layout == 'vertical'){
//                this.realHeight += this.startHeight;
//            }
//        }
//        if(this.endVisible){
//            if(this.layout == 'horizontal'){                                
//                this.realWidth += this.endWidth;   
//            }else if(this.layout == 'vertical'){                
//                this.realHeight += this.endHeight;
//            }
//        }        
//        this.measureSize();
//    },
    getNavLayoutBox: function(){
        //var box = Edo.navigators.Navigator.superclass.getLayoutBox.c(this); 
        var box = this.getLayoutBox();
        if(this.startVisible){
            if(this.layout == 'horizontal'){
                box.x += this.startWidth;
                box.width -= this.startWidth;
            }else if(this.layout == 'vertical'){
                box.y += this.startHeight;
                box.height -= this.startHeight;
            }
        }
        if(this.endVisible){
            if(this.layout == 'horizontal'){                
                box.width -= this.endWidth;
            }else if(this.layout == 'vertical'){
                box.height -= this.endHeight;
            }
        }
//        if(this.navBarWidthGet){
//            box.x += this.buttonWidth;
//            box.width -= this.buttonWidth * 2;            
//            this.navBarWidthGet = false;
//        }

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    syncSize: function(){
        Edo.navigators.Navigator.superclass.syncSize.call(this);
        
        //如果超出滚动条,则...
        var dom = this.scrollEl;
        var doNav = false;
        if(this.layout == 'horizontal' && dom.scrollWidth > dom.clientWidth){
            doNav = true;
        }else if(this.layout == 'vertical' && dom.scrollHeight > dom.clientHeight){
            doNav = true;
        }
        if(doNav){            
            var box = this.getNavLayoutBox();
            this.syncScrollEl(box);
            
            if(this.startVisible){                
                var left = box.x, top = box.y, w = this.startWidth, h = this.startHeight;
                if(this.layout == 'horizontal') {
                    left -= (this.startWidth + this.padding[3]);
                    h = box.height;
                }
                if(this.layout == 'vertical') {
                    top -= (this.startHeight + this.padding[0]);
                    w = box.width;
                }                
                this.startButton._setBox({
                    x:left,
                    y:top,
                    width: w,
                    height: h
                });
                //this.startButton._setVisible(true);
            }else{
                //this.startButton._setVisible(false);
            }
            if(this.endVisible){
                var left = box.x, top = box.y, w = this.endWidth, h = this.endHeight;
                if(this.layout == 'horizontal') {
                    left += box.width + this.padding[1];
                    h = box.height;
                }
                if(this.layout == 'vertical') { 
                    top += box.height + this.padding[2];
                    w = box.width;
                }
                
                this.endButton._setBox({
                    x:left,
                    y:top,
                    width: w,
                    height: h
                });
                //this.endButton._setVisible(true);
            }else{
                //this.endButton._setVisible(false);
            }
        }else{
            this.startButton.el.style.left = '-300px';
            this.endButton.el.style.left = '-300px';
            this.startButton.el.style.position = 'absolute';
            this.endButton.el.style.position = 'absolute';
//            this.startButton._setVisible(false);
//            this.endButton._setVisible(false);
        }
        
    },
    createChildren: function(el){
        Edo.navigators.Navigator.superclass.createChildren.call(this, el);
//        this.endButtonCt = this.el.lastChild;        
//        this.startButtonCt = this.endButtonCt.previousSibling;

        this.startButton = Edo.create({
            type: 'button',
            icon: 'e-nav-start',
            minHeight: 5,
            //simpleButton: true,
            onclick: this.preScrollView.bind(this),
            render: this.el
        });
        this.endButton = Edo.create({
            icon: 'e-nav-end',
            minHeight: 5,
            type: 'button',
            //simpleButton: true,
            onclick: this.nextScrollView.bind(this),
            render: this.el
        });
    },
    _setHorizontalScrollPolicy: function(value){
        this.horizontalScrollPolicy = 'off';        
    },
    _setVerticalScrollPolicy: function(value){
        this.verticalScrollPolicy = 'off';       
    },
//    _onButtonClick: function(e){
//        var offset = 0;
//        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
//        if(this.startButton == e.source){
//            offset = -30;
//        }else{
//            offset = 30;
//        }
//        this.scrollEl[sp] += offset;
//    },
    preScrollView: function(){
        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
        this.scrollEl[sp] -= this.scrollOffset;
    },
    nextScrollView: function(){
        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
        this.scrollEl[sp] += this.scrollOffset;
    }
});

Edo.navigators.Navigator.regType('navigator', 'nav');