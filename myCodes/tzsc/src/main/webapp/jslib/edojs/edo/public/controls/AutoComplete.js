/**
    @name Edo.controls.AutoComplete
    @class
    @typeName autocomplete
    @description 自动搜索框
    @extend Edo.controls.ComboBox
*/
Edo.controls.AutoComplete = function(){        
    Edo.controls.AutoComplete.superclass.constructor.call(this);
    /**
         @name Edo.controls.AutoComplete#beforequery
         @event
         @description 查询前
         @property {Boolean} queryParams 查询参数对象
    */
    /**
         @name Edo.controls.AutoComplete#query
         @event
         @description 查询后
         @property {String} data 查询返回的文本数据
    */    
}
Edo.controls.AutoComplete.extend(Edo.controls.ComboBox,{
    /**
        @name Edo.controls.AutoComplete#changeAction
        @property
        @type String
        @default keyup
        @description 查询内容改变激发事件 
    */
    changeAction: 'keyup',
    /**
        @name Edo.controls.AutoComplete#popupHeight
        @property
        @type Number
        @default 120
        @description 下拉框高度
    */
    popupHeight: 120,
    popupWidth: '100%',
    /**
        @name Edo.controls.AutoComplete#url
        @property
        @type String
        @description 查询地址
    */
    url: '',
    /**
        @name Edo.controls.AutoComplete#queryDelay
        @property
        @type Number
        @default 500
        @description 延迟查询间隔
    */
    queryDelay: 500,
    
    popupShadow: false,
    
    pageVisible: false,
    
    autoFill: true,
    
    maxSize: 10,
    
    //rootField: '',
    
    initEvents: function(){    
        if(!this.design){
            this.on('popupshow', this._onpopupshow, this, 0);
            this.on('keyup', this._onkeyup, this, 0);     
            this.on('trigger', this._ontrigger, this, 0);       
        }
        Edo.controls.AutoComplete.superclass.initEvents.call(this);
    },
    
    _onpopupshow: function(e){
        var combo = this;
        if(!this.pager){
        
            this.popupCt.set({
                verticalGap: 0
            });
            this.popupCt.addChild({
                visible: this.pageVisible,
                infoVisible: false,
                type: 'pagingbar',
                name: 'pager',
                cls: 'e-toolbar',
                border: [0,1,0,1],
                onpaging: function(e){
                    combo.query(combo.text, this.index, this.size);
                }
            });
            this.table.set('autoHeight', false);
            
//            this.popupCt.addChild({
//                type: 'box',
//                width: '100%',                
//                border: 0,
//                layout: 'horizontal',
//                horizontalAlign: 'right',
//                children: [
//                    {
//                        type: 'button',
//                        cls: 'e-toolbar',
//                        text: '取消'
//                    },
//                    {
//                        type: 'button',
//                        width: 50,
//                        text: '确定'
//                    }
//                ]
//            });
            this.pager = Edo.getByName('pager', this.popupCt)[0];
            
        }
    },
    _onkeyup: function(e){
        
        if(e.keyCode == 40){
            this.showPopup();
            if(this.data.getCount() == 0){
                this.pager.change();
            }else{
                //鼠标移动操作表格行
            }
        }else if(e.keyCode == 13){
            this.hidePopup();
        }else{
            var key = this.field.value;
//            if(key == ''){
//                this.data.clear();
//                //this.hidePopup();
//                return;
//            }
            this.showPopup();
            this.pager.change();
        }
    },
    _ontrigger: function(e){
        if(this.data.getCount() == 0){
            this.pager.change();
        }
    },
    
    query: function(key, index, size){        
//        if(key == '') {      
//            return;
//        }
        if(this.selectedItem && this.selectedItem[this.displayField] == key){
            return;
        }
        
        var params = {
            key: key,
            index: index || 0,
            size: size || this.maxSize
        };
        var e = {
            type: 'beforequery',
            source: this,
            queryParams: params
        };
        if(this.fireEvent('beforequery', e) === false) return;
        
        this.table.loadingMask = true;
        this.table.zeroMask = true;
        this.table.mask();
        
        if(this.queryTimer){
            clearTimeout(this.queryTimer);
            this.queryTimer = null;
        }
        var combo = this;
        this.queryTimer = setTimeout(function(){
            combo.doQuery(e.queryParams);
        }, this.queryDelay);
        
    },    
    doQuery: function(params){
        var combo = this;        
                
        if(this.queryAjax){
            Edo.util.Ajax.abort(this.queryAjax);
        }
        
        this.queryAjax = Edo.util.Ajax.request({
            url: this.url,
            params: params,
            onSuccess: function(text){ 
            
                if(combo.autoFill){
                    var data = Edo.util.Json.decode(text);
                    combo.data.load(data);
                }
                combo.fireEvent('query', {
                    type: 'query',
                    success: true,
                    data: text
                });
                combo.table.unmask();
            },
            onFail: function(code){
                if(combo.autoFill){
                    combo.data.clear();
                }
                combo.fireEvent('query', {
                    type: 'query',
                    success: false,
                    errorCode: code
                });
                combo.table.unmask();
            }
        });
        
    },
    _onTextChange: function(e){        
        Edo.controls.AutoComplete.superclass._onTextChange.call(this, e);
        if(this.field.value == ""){
            this.set('selectedIndex', -1);
        }
    }
    ,
    setValue: function(v){
        if(v && this.selectedItem != v){
            this.selectedItem = v;
            this.set('text', v[this.displayField]);
        }                    
    },
    getValue: function(){
        var o = this.selectedItem;        
        if(o) o = o[this.valueField];
        return o;
    }
});
Edo.controls.AutoComplete.regType('autocomplete');   