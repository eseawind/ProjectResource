/**
    @name Edo.controls.MultiSelect
    @class 
    @typeName multiselect
    @description 多选框
    @extend Edo.lists.Table
    @example 
*/ 
Edo.controls.MultiSelect = function(){

    Edo.controls.MultiSelect.superclass.constructor.call(this);
};
Edo.controls.MultiSelect.extend(Edo.lists.Table,{
    headerVisible: false,
    minWidth: 80,
    minHeight: 50,
    
    rowSelectMode: 'multi',    
    enableDragDrop: true,    //允许行拖拽
    dragDropAction: 'move',//copy
    
    displayText: '名称',
    displayField: 'text',
    valueField: 'text',  
    
    autoExpandColumn: 'display',      
    
    init: function(){        
        this.set('columns',  [
            Edo.lists.Table.createMultiColumn(),
            {id: 'display', width: '100%', header: this.displayText, dataIndex: this.displayField}
        ]);
        Edo.controls.MultiSelect.superclass.init.call(this);
    }
});

Edo.controls.MultiSelect.regType('multiselect');