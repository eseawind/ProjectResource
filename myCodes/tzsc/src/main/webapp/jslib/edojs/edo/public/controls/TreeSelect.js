/**
    @name Edo.controls.TreeSelect
    @class 
    @typeName TreeSelect
    @description 树形选框下拉框
    @extend Edo.controls.Trigger
    @example 
*/
Edo.controls.TreeSelect = function(){

    Edo.controls.TreeSelect.superclass.constructor.call(this);
};
Edo.controls.TreeSelect.extend(Edo.controls.Trigger,{
    treeConfig: null,
    
    data: null,
    
    multiSelect: true,
    popupMinWidth: 150,
    
    enableResizePopup: true,
    
    popupHeight: 150,
    
    valueField: 'id',
    displayText: '名称',
    displayField: 'text',
    valueField: 'text',
    delimiter: ',',
    
    autoExpandColumn: 'display',
        
    triggerPopup: true,
    readOnly: true,
    
//    _setValue: function(values){        
//        this.tree.setValue(vs);
//    },
    setValue: function(vv){        
        this.tree.setValue(vv);
    },
    getValue: function(){
        return this.tree.getValue();
    },    
    
    viewText: function(){    
        var texts = [];
        
        var selecteds = this.tree.getSelecteds();
        selecteds.each(function(o){
            texts.add(o[this.displayField]);
        }, this);
        this._setText(texts.join(this.delimiter));
    },
    
    _setData: function(data){
        if(typeof data === 'string') data = window[data];
        if(!data) return;
        if(!data.dataTable) data = new Edo.data.DataTree(data || []);
        
        this.data = data;
                
        if(this.tree) this.tree.set('data', data);                
        
        this.changeProperty('data', data);        
    },
    
    onselectionchange: function(e){
        this.selectionchanged = true;
        this.viewText();
        this.changeProperty('value', this.getValue(), true);   
    },
    init: function(){    
        Edo.controls.TreeSelect.superclass.init.a(this, arguments);
        
        if(!this.tree){        
            this.tree = Edo.create(Edo.apply({
                type: 'tree',
                style: 'border:0',
                width: '100%',
                height: '100%',
                //autoHeight: true,                
                minHeight: 80,
                maxHeight: 250,
                treeColumn: 'display',
                multiSelect: this.multiSelect,
                rowSelectMode: this.multiSelect ? 'multi' : 'single',    
                autoExpandColumn: this.autoExpandColumn,
                valueField: this.valueField,
                columns: [
                    Edo.lists.Table.createMultiColumn(),
                    {id: 'display', width: '100%', header: this.displayText, dataIndex: this.displayField}
                ],
                delimiter: this.delimiter,
                onselectionchange: this.onselectionchange.bind(this)
            }, this.treeConfig));
        }
        this.tree.set('data', this.data);
    },
    showPopup: function(){    
        Edo.controls.TreeSelect.superclass.showPopup.a(this, arguments);
        
        if(!this.tree.parent){
            this.popupCt.addChild(this.tree);
        }
        var sels = this.tree.getSelecteds().clone();
        this.tree.set('data', this.data);
        this.tree.selectRange(sels);
    },    
    hidePopup: function(){
        Edo.controls.TreeSelect.superclass.hidePopup.a(this, arguments);
        
        if(this.selectionchanged){
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this
            });
        }
        this.selectionchanged = false;                
    }
});

Edo.controls.TreeSelect.regType('TreeSelect');