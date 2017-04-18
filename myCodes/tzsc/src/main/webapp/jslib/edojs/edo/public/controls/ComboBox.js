/**
    @name Edo.controls.ComboBox
    @class
    @typeName combobox
    @description 下拉选择框combobox
    @extend Edo.controls.Trigger
*/
Edo.controls.ComboBox = function(config){    
    /**
        @name Edo.controls.ComboBox#selectionchange
        @event
        @description 下拉表格选中状态改变事件
        @property {Object} selectedItem 选中对象
        @property {Number} selectedIndex 选中索引
    */

    Edo.controls.ComboBox.superclass.constructor.call(this);    
    
    //this.data = new Edo.data.DataTable();
    this._setData([]);
    
    this.selecteds = [];
};
Edo.controls.ComboBox.extend(Edo.controls.Trigger,{    
    
    //textchangeSelect: false,
    /**
        @name Edo.controls.ComboBox#triggerPopup
        @property        
        @default true
    */
    triggerPopup: true,
    /**
        @name Edo.controls.ComboBox#displayField
        @property
        @type String
        @description 下拉表格显示字段, 默认是"text"
    */
    displayField: 'text',
    /**
        @name Edo.controls.ComboBox#selectedIndex
        @property
        @type Number
        @description 选中的索引
    */   
    selectedIndex: -1,      //当前选中索引
    /**
        @name Edo.controls.ComboBox#selectedItem
        @property
        @type Number
        @description 选中的行对象
    */  
    selectedItem: null,     //当前选中对象
    /**
        @name Edo.controls.ComboBox#data
        @property 
        @type Edo.data.DataTable
        @description 表格数据对象
    */
    //data: null,
    
    popupWidth: '100%',
    
    autoSelect: true,
    selectHidePopup: true,
    
    /**
        @name Edo.controls.ComboBox#tableConfig
        @property
        @type Object
        @description 下拉框表格配置对象
    */
    tableConfig: null,
    
    set: function(o, value){
        if(!o) return;
        
        if(typeof o === 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }        
        
        var selectedIndex = o.selectedIndex;        
        delete o.selectedIndex;        
        
        Edo.controls.ComboBox.superclass.set.call(this, o);
        
        
        if(Edo.isValue(selectedIndex)){
            this._setSelectedIndex(selectedIndex);
        }        
        return this;
    },
    initEvents: function(){        
        Edo.controls.ComboBox.superclass.initEvents.c(this);
        Edo.util.Dom.on(this.field, 'keyup', this._onComboFieldKeyUp, this, 0);
    },
    
    _onComboFieldKeyUp: function(e){
        this.text = this.field.value;
        
        if(this.popupDisplayed){
            var row = this.table.getFocusItem();
            var rowIndex = this.table.data.indexOf(row);
            
            switch(e.keyCode){
            case 37:
            break;
            case 38:                //上
                rowIndex -= 1;
                if(rowIndex < 0){
                    
                }else{                    
                    this.table.focusItem(rowIndex);
                }
            break;
            case 39:                            
            break;
            case 40:                //下
            
                rowIndex += 1;
                if(rowIndex > this.table.data.getCount()){
                    
                }else{
                    this.table.focusItem(rowIndex);
                }
            break;
            case 13:
                var row = this.table.getFocusItem();
                this.table.select(row);
                
                this.hidePopup();
            break;
            case 27:
                this.hidePopup();
            break;
            default:                
                this.data.filter(this.filterFn, this);
                
                if(this.data.getCount() == 0){
                    this.hidePopup();
                }else{
                    this.showPopup();
                }
            break;
            }
        }else{
            switch(e.keyCode){
            case 13:
            case 27:
            case 37:
            case 38:
            case 39:
                
            break;
            
            case 40:                    //下
                this.showPopup();
                
                this.hoverDefaultRow();
            break;
            default:                                
                this.data.filter(this.filterFn, this);
                
                if(this.data.getCount() == 0){
                    this.hidePopup();
                }else{
                    this.showPopup();
                }
                this.hoverDefaultRow();
            break;
            }
        }
        
    },
    filterFn: function(o){
        var text = String(o[this.displayField]);
        if(text.indexOf(this.text) != -1){
            return true;
        }else{
            return false;
        }
    },
    _onTrigger: function(e){
        Edo.controls.ComboBox.superclass._onTrigger.c(this, e);
        this.data.clearFilter();
        
        //this.hoverDefaultRow();
    },
    popupReset: false,
    
    showPopup: function(){    
        if(this.popupReset){
            this.resetValue();
        }
        Edo.controls.ComboBox.superclass.showPopup.c(this);     
        
        //this.table.clearSelect();
        //this.table.select(this.selectedItem);   
    },
    hidePopup: function(){
        Edo.controls.ComboBox.superclass.hidePopup.c(this);
        this.data.clearFilter();
    },
    hoverDefaultRow: function(){
        var row = this.selectedItem;
        if(!row){
            row = this.data.getAt(0);
        }
        if(this.table && row){
            this.table.focusItem.defer(100, this.table, [row]);
        }
    },
    
    _setSelectedItem: function(value){
    
        if(this.selectedItem != value){            
            
            var index = this.data.findIndex(value);
            
            this._setSelectedIndex(index);
            
            this.changeProperty('selectedItem', value, true);
        }
    },
    delimiter: ',',
    /*
        设置一个索引, 如果没有找到对象, 则为-1, 清除选中状态, 并清空text
    */
    _setSelectedIndex: function(value){
    
        var d = this.data;
        if(value < 0 || value >= this.data.getCount()) value = -1;        
        if(this.selectedIndex != value){
                    
            var sel = this.data.getAt(value);                       
            if(this.fireEvent('beforeselectionchange', {
                type: 'beforeselectionchange',
                source: this,
                selectedIndex: value,
                selectedItem: sel
            }) === false) return false;
            
            this.selectedIndex = value;                                 
            this.selectedItem = sel;            
            
            if(!this.multiSelect){      //保留多选接口
                this.selecteds = [];
            }
            if(value != -1){
                this.selecteds.add(sel);
            }
            
            var texts = [];
            
            this.selecteds.each(function(o){
                texts.add(o[this.displayField]);
            }, this);
            
            this._setText(texts.join(this.delimiter));
            
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this,
                selectedIndex: value,
                selectedItem: this.selectedItem
            });
            
            this.changeProperty('selectedIndex', value); 
            
            return true;                       
        }
    },
//    _setMultiSelect: function(value){
//        value = Edo.toBool(value);
//        if(this.multiSelect != value){
//            this.multiSelect = value;
//            if(this.table){
//                this.table.set('rowSelectMode', this.multiSelect ? 'multi' : 'single');
//            }
//            this.changeProperty('multiSelect', value); 
//        }
//    },
    _setData: function(data){
        if(typeof data === 'string') data = window[data];
        if(!data) return;
        if(!data.dataTable) data = new Edo.data.DataTable(data || []);
        
        this.set('selectedIndex', -1);
        if(this.data){
            this.data.un('datachange', this._onDataChange);
        }   
             
        this.data = data;
        
        this.data.on('datachange', this._onDataChange, this);
        
        if(this.table) this.table.set('data', data);                
        
        this.changeProperty('data', data);
        
    },
    _onSelection: function(e){
        var table = e.source; 
        
        if(this.canChange === false) return;
        if(this.selectedItem == table.selected) return;
           
        if(this.autoSelect){
            this._setSelectedItem(table.selected);
            
            if(this.selectHidePopup){
                this.hidePopup();
            }
            
            //this.focus.defer(100, this);
            this.focus();
        }
    },
    createPopup: function(){    
        Edo.controls.ComboBox.superclass.createPopup.a(this, arguments);
//        Edo.applyIf(this.tableConfig, {
//            horizontalLine: false
//        });
        
        if(!this.table){
            var sf = this;
            var columns = [{id: 'display',width: '100%',header: '', dataIndex: this.displayField, style: 'cursor:default;'}];
            
            this.table = Edo.build(Edo.apply({
                type: 'table',  
                style: 'border:0',
                outRemoveFocus: false,
                verticalLine: false,
                horizontalLine: false,
                enableCellSelect: false,
                headerVisible: false,
                enableTrackOver: true,
                width: '100%',
                height: '100%',
                minHeight: 10,
                autoHeight: true,
                rowHeight: 20,
                autoColumns: true,
                //autoExpandColumn: 'display',
                selectedCls: 'e-table-row-over',
                data: this.data,
                columns: columns,
                onmousedown: function(e){
                    sf.focus();
                },
                onselectionchange: this._onSelection.bind(this)
            }, this.tableConfig));

            
            
            this.popupCt.addChild(this.table);                        
            
            this.table.set('cls', 'e-table-hover');
        }
        
        this.canChange = false;
        this.table.deselect();
        
        if(this.selectedItem && this.selectedIndex != -1) this.table.select(this.selectedItem);
        
        this.canChange = true;
    },
    _onDataChange: function(e){      //combo的data改变后,要将table的数据改变    
    
        this.set('selectedIndex', -1);
        if(this.table){
            this.table._setData(this.data);
        }        
    },
    
    valueField: 'text',
    defaultValue: '',
    setValue: function(v){    
        var o = {};
        o[this.valueField] = v;  //{id: 1}
        var index = this.data.findIndex(o);
        this._setSelectedIndex(index);
    },
    getValue: function(){
        var o = this.selectedItem;
        if(o) o = o[this.valueField];
        return o;
    }
});

Edo.controls.ComboBox.regType('combo');