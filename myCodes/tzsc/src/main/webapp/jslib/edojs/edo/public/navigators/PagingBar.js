/**
    @name Edo.navigators.PagingBar
    @class
    @description 分页组件,配合表格组件使用
    @extend Edo.containers.Box
*/
Edo.navigators.PagingBar = function(config){   
    
    Edo.navigators.PagingBar.superclass.constructor.call(this);     
    /**
        @name Edo.navigators.PagingBar#beforepaging
        @event        
        @description 分页前事件
        @property {Number} index 分页索引
        @property {Number} size 每页分页数
        @property {Object} params 分页参数对象
    */   
    /**
        @name Edo.navigators.PagingBar#paging
        @event        
        @description 分页前事件
        @property {Number} index 分页索引
        @property {Number} size 每页分页数
        @property {Object} params 分页参数对象
    */    
};
Edo.navigators.PagingBar.extend(Edo.containers.Box,{
    elCls: 'e-pagingbar e-box e-ct e-div',
    /**
        @name Edo.navigators.PagingBar#size
        @property
        @type Number
        @default 20
        @description 每页多少行记录
    */
    size: 20,
    /**
        @name Edo.navigators.PagingBar#index
        @property
        @type Number
        @default 0
        @description 页码
    */
    index: 0,
    /**
        @name Edo.navigators.PagingBar#total
        @property
        @type Number
        @description 总记录数
    */
    total: -1,
    /**
        @name Edo.navigators.PagingBar#totalPage
        @property
        @type Number
        @description 总页码数
    */
    totalPage: -1,
    loading: false,
    /**
        @name Edo.navigators.PagingBar#autoPaging
        @property
        @type Boolean
        @default false
        @description 创建后是否自动激发分页动作
    */
    autoPaging: false,   //创建成功,自动激发分页
    
    totalTpl: '第 <input class="e-pagingbar-input" onchange="<%=this.id%>.change(parseInt(this.value)-1, <%=this.size%>);" onkeydown="if(event.keyCode==13) <%=this.id%>.change(parseInt(this.value)-1, <%=this.size%>);" type="text" value="<%=this.index+1%>"/> / <%=this.totalPage%>页, 每页<input onchange="<%=this.id%>.change(<%=this.index%>, this.value);" onkeydown="if(event.keyCode==13) <%=this.id%>.change(<%=this.index%>, this.value);" class="e-pagingbar-input"  type="text" value="<%=this.size%>"/>条, 共<%=this.total%>条',

    layout: 'horizontal',
    layout: 'horizontal',
    border: [0,0,0,0],
    padding: [0,0,0,0],
    /**
        @name Edo.navigators.PagingBar#barVisible
        @property
        @type Boolean
        @default true
        @description 是否显示分页操作按钮
    */
    barVisible: true,
    /**
        @name Edo.navigators.PagingBar#infoVisible
        @property
        @type Boolean
        @default true
        @description 是否显示分页信息
    */
    infoVisible: true,
    
    _setSize: function(v){
        this.size = v;
    },
    init: function(){
        var pager = this;
        this.set({
            verticalAlign: 'middle',
            'children': [
                {
                    name: 'barct', type: 'ct',layout: 'horizontal', horizontalGap: 0,verticalAlign: 'middle', height:'100%',
                    children: [
                        {
                            type: 'button',
                            name: 'first',
                            defaultHeight: 16,                            
                            icon: 'e-page-first',                  
                            onclick: this.doFirst.bind(this)
                        },
                        {
                            type: 'button',
                            name: 'pre',
                            defaultHeight: 16,
                            icon: 'e-page-pre',
                            onclick: this.doPre.bind(this)
                        },  
                        {
                            type: 'button',
                            name: 'next',
                            defaultHeight: 16,
                            icon: 'e-page-next',
                            onclick: this.doNext.bind(this)
                        },
                        {
                            type: 'button',
                            name: 'last',
                            defaultHeight: 16,
                            icon: 'e-page-last',
                            onclick: this.doLast.bind(this)
                        }                
                    ]
                },
                {
                    type: 'button',
                    name: 'refresh',
                    defaultHeight: 16,
                    icon: 'e-icon-refresh',
                    onclick: this.doRefresh.bind(this)
                },
                {
                    type: 'button',
                    name: 'nowait',
                    defaultHeight: 16,
                    icon: 'e-page-nowait'
                },
                {
                    type: 'space', name: 'space', width: '100%'
                },
                {
                    name: 'infoct', type: 'ct',layout: 'horizontal', verticalAlign: 'middle',
                    children: [
//                        {
//                            type: 'text',
//                            name: 'index',
//                            textStyle: 'text-align:center',
//                            text: '1',
//                            width: 25,
//                            ontextchange: function(e){
//        //                        var index = parseInt(this.text);
//        //                        if(isNaN(index)){
//        //                            alert("只能输入数字");
//        //                            this.set('text', pager.index);
//        //                            return;
//        //                        }
//        //                        pager.change(index);
//                            }
//                        },
                        {
                            type: 'label',
                            name: 'view'                
                        }                     
                    ]
                }
            ]
        });
        Edo.navigators.PagingBar.superclass.init.apply(this, arguments);
        
        var sf = this;
        function get(name){
            return Edo.getByName(name, sf)[0];
        }
        
        var btns = this.children;
        this.firstBtn = get("first");
        this.preBtn = get("pre");
        this.nextBtn = get("next");
        this.lastBtn = get("last");
        
        this.refreshBtn =  get("refresh");
        this.loadBtn =  get("nowait");
        
        //this.indexText = get("index");
        //this.sizeText = get("size");
        this.viewText = get("view");
        //
        this.barCt = get('barct');
        this.infoCt = get('infoct');
        
        this.space = get('space');
        
        this.barCt.set('visible', this.barVisible);
        this.infoCt.set('visible', this.infoVisible);
        
        if(this.autoPaging){
            this.change.defer(this.padingDelay, this);
        }
    },
    padingDelay: 100,
    doFirst: function(e){
        this.change(0);
    },
    doPre: function(e){
        this.change(this.index-1);
    },
    doNext: function(e){
        this.change(this.index+1);
    },
    doLast: function(e){    
        this.change(this.totalPage-1);
    },
    doRefresh: function(e){
        this.change();
    },
    /**
        @name Edo.navigators.PagingBar#change
        @function 
        @param {Number} index 页码
        @param {Number} size 每页记录数
        @param {Object} params 分页参数
        @description 列配置
    */
    change: function(index, size, params){       
        index = parseInt(index);
        size = parseInt(size);
        var e = {
            type: 'beforepaging',
            source: this,
            index: Edo.isValue(index) ? index : this.index,
            size: Edo.isValue(size) ? size : this.size,
            params: params
        }
        
        if(this.fireEvent('beforepaging', e) !== false){
        
            this.index = e.index;
            this.size = e.size;
                        
            this.compute();
            
            this.loading = true;
            this.refresh();
            
            e.type = 'paging';
            this.fireEvent('paging', e);
        }
        
    },
    compute: function(index, size, total){
        if(Edo.isValue(index)) this.index = index;
        if(Edo.isValue(size)) this.size = size;
        if(Edo.isValue(total)) this.total = total;
        this.totalPage = parseInt(this.total / this.size) + (this.total % this.size ? 1 : 0);
        //this.totalPage -= 1;
        
        if(this.index >= this.totalPage-1){
            this.index = this.totalPage - 1;
        }
        if(this.index < 0) this.index = 0;
    },
    /**
        @name Edo.navigators.PagingBar#refresh
        @function 
        @param {Number} total 总记录数        
        @description 更新分页状态信息
    */
    refresh: function(total){
        if(Edo.isValue(total)){
            this.total = total;
        }
        //根据当前的分页数据,更新按钮状态(可用/不可用等..)
        
        this.compute();
        
        //loading
        if(this.inited) {        
            this.firstBtn.set('enable', this.index != 0);
            this.preBtn.set('enable', this.index != 0);
            
            this.nextBtn.set('enable', !(this.totalPage == 0) && this.index != this.totalPage-1);
            this.lastBtn.set('enable', !(this.totalPage == 0) && this.index != this.totalPage-1);
                        
            this.viewText.set('text', '');
            this.viewText.set('text', new Edo.util.Template(this.totalTpl).run(this));
            
            this.loadBtn.set('icon', this.loading ? 'e-page-wait' : 'e-page-nowait');
            
            this.loading = false;
        }
    }
});

Edo.navigators.PagingBar.regType('pagingbar');
Edo.navigators.PagingBar.prototype.refreshView = Edo.navigators.PagingBar.prototype.refresh;