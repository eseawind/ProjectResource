/**
    @name Edo.navigators.VTabBar
    @class
    @typeName vtabbar    
    @extend Edo.navigators.TabBar
    @description 实现竖向的tabbar
*/
Edo.navigators.VTabBar = function(config){
    Edo.navigators.VTabBar.superclass.constructor.call(this);     
    
};

Edo.navigators.VTabBar.extend(Edo.navigators.TabBar,{
    /**
        @name Edo.navigators.VTabBar#width
        @property
        @default 'auto'
    */
    width: 'auto',
    /**
        @name Edo.navigators.VTabBar#height
        @property
        @default '100%'
    */
    height: '100%',
    /**
        @name Edo.navigators.VTabBar#verticalAlign
        @property
        @default 'top'
    */
    verticalAlign: 'top',
    /**
        @name Edo.navigators.VTabBar#horizontalAlign
        @property
        @default 'left'
    */
    horizontalAlign: 'left',
    /**
        @name Edo.navigators.VTabBar#defaultWidth
        @property
        @default 22
    */
    defaultWidth: 22,
    /**
        @name Edo.navigators.VTabBar#defaultHeight
        @property
        @default 60
    */
    defaultHeight: 60, 
    /**
        @name Edo.navigators.VTabBar#position
        @property
        @default 'left'
    */
    position: 'left',
    /**
        @name Edo.navigators.VTabBar#layout
        @property
        @default 'vertical'
    */
    layout: 'vertical',
    /**
        @name Edo.navigators.VTabBar#padding
        @property
        @default [2,2,2,3]
    */
    padding: [2,2,2,3],
    verticalGap: 2
});
Edo.navigators.VTabBar.regType('vtabbar');