/**
    @name Edo.controls.CheckGroup
    @class
    @typeName checkgroup
    @description 多选框列表
    @extend Edo.controls.DataView
*/
Edo.controls.CheckGroup = function(config){
    Edo.controls.CheckGroup.superclass.constructor.call(this);     
};
Edo.controls.CheckGroup.extend(Edo.controls.DataView,{
    /**
        @name Edo.controls.CheckGroup#autoWidth
        @property
        @default true
    */
    autoWidth: true,
    /**
        @name Edo.controls.CheckGroup#autoHeight
        @property
        @default true
    */
    autoHeight: true,
    /**
        @name Edo.controls.CheckGroup#multiSelect
        @property
        @default true
    */
    multiSelect: true,
    //mustSelect: false,
    /**
        @name Edo.controls.CheckGroup#repeatSelect
        @property
        @default false
    */
    repeatSelect: false,
    selectOnly: false,     
    
    elCls: 'e-checkgroup',
    
    itemSelector: 'e-checkgroup-item',
    itemCls: 'e-checkgroup-item',
    selectedCls: 'e-checkgroup-item-checked',
    /**
        @name Edo.controls.CheckGroup#displayField
        @property
        @type String
        @default text
        @description 文本显示字段
    */
    displayField: 'text',    
    /**
        @name Edo.controls.CheckGroup#valueField
        @property
        @type String
        @default value
    */
	valueField: 'value',
    /**
        @name Edo.controls.CheckGroup#repeatLayout
        @property
        @type table,flow
        @default table
        @description 排布方式
    */
    repeatLayout: 'table',  //flow
    /**
        @name Edo.controls.CheckGroup#repeatDirection
        @property
        @type vertical,horizontal
        @default vertical
        @description 排布方向
    */
	repeatDirection: 'vertical',// horizontal
	/**
        @name Edo.controls.CheckGroup#repeatItems
        @property
        @type Number
        @default 10000
        @description 排布方向最大数目
    */
	repeatItems: 10000,
	/**
        @name Edo.controls.CheckGroup#itemWidth
        @property        
        @default auto
        @description 选择项宽度
    */
	itemWidth: 'auto',
	/**
        @name Edo.controls.CheckGroup#itemHeight
        @property        
        @default auto
        @description 选择项高度
    */
	itemHeight: 'auto',
	/**
        @name Edo.controls.CheckGroup#cellPadding
        @property        
        @type Number
        @default 0
        @description 选择项内边距
    */
	cellPadding: 0,	
	/**
        @name Edo.controls.CheckGroup#cellSpacing
        @property        
        @type Number
        @default 2
        @description 选择项外边距
    */
	cellSpacing: 2,
	
	tpl: '<%=this.createView();%>',
	
	itemStyle: 'padding-right:3px;',
	itemRenderer: function(){
	    
	},
    createView: function(){
        var data = this.data;
        if(!data || data.getCount() == 0) return '';   
        data = this.data.view;
        if(this.repeatLayout == 'table'){
            data = this.createTableData(data);
            var sb = ['<table cellpadding="'+this.cellPadding+'" cellspacing="'+this.cellSpacing+'">'];
            for(var i=0,l=data.length; i<l; i++){
                var r = data[i];
                sb[sb.length] = '<tr>';
                for(var j=0,k=r.length; j<k; j++){
                    var o = r[j];
                    sb[sb.length] = '<td style="vertical-align:top;">';
                    sb[sb.length] = this.getItemHtml(o);
                    sb[sb.length] = '</td>';
                    
                }
                sb[sb.length] = '</tr>';                    
            }
            sb[sb.length] = '</table>';
            return sb.join('');  
        }else{
            var sb = [];
            for(var i=0,l=data.length; i<l; i++){
                var o = data[i];
                sb[sb.length] = this.getItemHtml(o, 'float:left');                
            }
            return sb.join('');
        }                   
    },
    createTableData: function(data){
        var view = [];
        
        for(var i=0,l=data.length; i<l; i++){
            var o = data[i];
            
            var index = parseInt(i / this.repeatItems);
            if(this.repeatDirection == 'vertical'){
                index = i % this.repeatItems;
                
            }        
            var r = view[index];
            if(!r) {
                r = [];
                view.add(r);
            }                
            r.add(o);
        }
        return view;
    },
    getItemHtml: function(o, style){
        var s = '<div id='+this.createItemId(o)+' class="'+this.itemCls+' '+(o.enable === false ? 'e-disabled' : '')+'" style="float:left;'+style+';'+this.itemStyle+';width:'+this.itemWidth+'">'+o[this.displayField]+'</div>';
        return s;
    }
});
Edo.controls.CheckGroup.regType('checkgroup');