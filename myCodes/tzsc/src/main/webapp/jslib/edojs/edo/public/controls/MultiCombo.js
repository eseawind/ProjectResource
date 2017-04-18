/**
    @name Edo.controls.MultiCombo
    @class 
    @typeName MultiCombo
    @description 多选框下拉框
    @extend Edo.controls.Trigger
    @example 
*/
Edo.controls.MultiCombo = function(){

    Edo.controls.MultiCombo.superclass.constructor.call(this);
};
Edo.controls.MultiCombo.extend(Edo.controls.Trigger,{
    tableConfig: null,
    
    data: null,
    
    valueField: 'id',
    displayText: '名称',
    displayField: 'text',
    valueField: 'text',
    delimiter: ',',
        
    triggerPopup: true,
    readOnly: true,
    
    multiSelect: true,
//    _setValue: function(values){        
//        this.table.setValue(vs);
//    },
    setValue: function(vv){    
        this.table.setValue(vv);
    },
    getValue: function(){
    
        return this.table.getValue();
    },    
    
    viewText: function(){    
        var texts = [];
        
        var selecteds = this.table.getSelecteds();
        selecteds.each(function(o){
            texts.add(o[this.displayField]);
        }, this);
        this._setText(texts.join(this.delimiter));
    },
    
    _setData: function(data){
        if(typeof data === 'string') data = window[data];
        if(!data) return;
        if(!data.dataTable) data = new Edo.data.DataTable(data || []);
        
        this.data = data;
                
        if(this.table) this.table.set('data', data);
        
        this.changeProperty('data', data);        
    },
    
    onselectionchange: function(e){
        this.selectionchanged = true;
        this.viewText();
    },
    init: function(){    
        Edo.controls.MultiCombo.superclass.init.a(this, arguments);
        
        if(!this.table){
            this.table = Edo.create(Edo.apply({
                type: 'multiselect',
                style: 'border:0',
                width: '100%',
                height: '100%',
                autoHeight: true,
                multiSelect: this.multiSelect,
                minHeight: 80,
                maxHeight: 250,
                displayText: this.displayText,
                displayField: this.displayField,
                valueField: this.valueField,
                delimiter: this.delimiter,
                onselectionchange: this.onselectionchange.bind(this)
            }, this.tableConfig));
        }
        this.table.set('data', this.data);
    },
    showPopup: function(){    
        Edo.controls.MultiCombo.superclass.showPopup.a(this, arguments);
        
        if(!this.table.parent){
            this.popupCt.addChild(this.table);
        }
        var sels = this.table.getSelecteds().clone();
        this.table.set('data', this.data);
        this.table.selectRange(sels);
    },    
    hidePopup: function(){
        Edo.controls.MultiCombo.superclass.hidePopup.a(this, arguments);
        
        if(this.selectionchanged){
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this
            });
        }
        this.selectionchanged = false;                
    }
});

Edo.controls.MultiCombo.regType('multicombo');