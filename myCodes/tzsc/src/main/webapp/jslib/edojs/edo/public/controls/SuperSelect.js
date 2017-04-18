/**
    @name Edo.controls.SuperSelect
    @class
    @typeName superselect
    @description 超级选择框
    @extend Edo.controls.ComboBox
*/
Edo.controls.SuperSelect = function(){
    Edo.controls.SuperSelect.superclass.constructor.call(this);
        
    
};
Edo.controls.SuperSelect.extend(Edo.controls.ComboBox,{
    /**        
        @name Edo.controls.SuperSelect#multiSelect
        @property
        @type Boolean
        @default true
    */  
    multiSelect: true,
    /**        
        @name Edo.controls.SuperSelect#autoHeight
        @property
        @type Boolean
        @default true
    */  
    autoHeight: true,
    
    popupReset: false,
    /**        
        @name Edo.controls.SuperSelect#triggerVisible
        @property
        @type Boolean
        @default false
    */  
    triggerVisible: false,
    
    elCls: 'e-superselect',    
    
    delimiter: ',',
    /**        
        @name Edo.controls.SuperSelect#valueField
        @property
        @type String
        @default id
    */  
    valueField: 'id',
    /**        
        @name Edo.controls.SuperSelect#textField
        @property
        @type String
        @default text
        @description 文本显示字段
    */
    textField: 'text',    
    itemRenderer: function(r, rowIndex){        
        return '<a href="javascript:void(0);" hidefocus id="'+this.createItemId(r)+'" class="'+this.itemCls+'">'+r[this.textField]+'<span id="'+rowIndex+'" class="e-superselect-item-close"></span></a>';
    },
    
    setValue: function(vv){           
        if(!Edo.isValue(vv)) vv = [];
        if(typeof vv == 'string') vv = vv.split(this.delimiter);
        if(!(vv instanceof Array)) vv = [vv];
                
        this.selecteds = [];
        vv.each(function(v){
            var o = {};
            o[this.valueField] = v;  //{id: 1}
            var index = this.data.findIndex(o);
            if(index != -1){
                 this.selecteds.add(this.data.getAt(index));
            }
        }, this);        
        
        this.view.data.load(this.selecteds);
        
        this.fixAutoSize();
        this.relayout();
    },
    getValue: function(){
        var rs = [];
        
        this.selecteds.each(function(o){
            if(this.valueField == '*') rs.add(o);
            else rs.add(o[this.valueField]);
        }, this);
        
        return rs.join(this.delimiter);
    },
        
    initEvents: function(){
        
        this.on('keydown', this._onkeydown, this);
        this.on('click', this._onclick, this);
        
        this.on('beforeselectionchange', this._onSuperSelectBeforeSelectionChange, this, 0);
     
        Edo.controls.SuperSelect.superclass.initEvents.call(this);
    },
    _onclick: function(e){
        if(Edo.util.Dom.hasClass(e.target, 'e-superselect-item-close')){
            var rowIndex = parseInt(e.target.id);                        
            var row = this.view.data.getAt(rowIndex);
            
            this.selecteds.remove(row);
            this.view.data.load(this.selecteds);
            
            //调整尺寸
            this.fixAutoSize();
            
            this.fixInput(this.itemIndex);
            
            this.relayout('select');
        }
    },
    _onkeydown: function(e){
    
        var inField = this.field == e.target;
        if(inField && this.field.value != '') return;
        
        if(this.popupDisplayed) {
            if(e.keyCode == 8 && this.field.value == ''){
                this.hidePopup.defer(100, this);
            }
            return;
        }            
        var autoSize = true;
        
        switch(e.keyCode){
        case 8:            
            e.stopDefault();         
            if(inField){
                this.view.data.removeAt(this.itemIndex);
            }else{
                var sels = this.view.getSelecteds(); 
                this.view.data.removeRange(sels);
                
            }
            this.itemIndex -= 1;
            var item = this.view.getByIndex(this.itemIndex);            
            if(!item){
                this.itemIndex -= 1;
                item = this.view.getByIndex(this.itemIndex);
            }
            
        break;
        case 127:       
            if(inField){
                this.view.data.removeAt(this.itemIndex+1);
            }else{
                var sels = this.view.getSelecteds(); 
                this.view.data.removeRange(sels);
            }
            
        break;
        case 37:
        case 38:
            this.itemIndex -= 1;

            
            this.view.clearSelect();
        break;
        case 39:               
            this.itemIndex += 1;
//            
            this.view.clearSelect();            
        break;
        case 40: 
            return;
        break;
        case 36: //home
            this.itemIndex = -1;
            
            this.view.clearSelect();
        break;
        case 35: //end
            this.itemIndex = this.view.data.getCount()-1;
            
            this.view.clearSelect();
        break;
        default:
//            this.view.fixAutoSize();
//            this.view.relayout('input');
//            this.fixAutoSize();
//            this.relayout('input');
        return;
        }   
        if(this.itemIndex < -1){
            this.itemIndex = -1;
        }
        if(this.itemIndex > this.view.data.getCount()-1){
            this.itemIndex = this.view.data.getCount()-1;
        }        
        this.fixInput(this.itemIndex);     
        
        this.fixAutoSize();
        this.relayout();
        
    },
    _onSuperSelectBeforeSelectionChange: function(e){
    
    
        var sel = e.selectedItem;
        if(!sel) return false;
        
        if(!this.multiSelect){
            this.selecteds = [];
        }
        this.itemIndex += 1;        
        
        this.selecteds.insert(this.itemIndex, sel);
        
        if(this.itemIndex < -1){
            this.itemIndex = -1;
        }
        if(this.itemIndex > this.view.data.getCount()-1){
            this.itemIndex = this.view.data.getCount()-1;
        }
        
        //调整尺寸
        
        
        this.view.data.load(this.selecteds);
        
        this.field.value = '';        
        
        //this.fixInput(this.itemIndex);
        this.fixInput.defer(10, this, [this.itemIndex]);   //###        
        
        this.fixAutoSize();
        this.relayout('select');
        
        return false;
    },    
    createChildren: function(el){
        Edo.controls.SuperSelect.superclass.createChildren.call(this, el);
        
        //this.selecteds.addRange(this.data.source);
        
        this.view = Edo.create({
            type: 'dataview',
            render: this.el,
            itemSelector: 'e-superselect-item',
            itemCls: 'e-superselect-item',
            selectedCls: 'e-superselect-item-checked',            
            cls: 'e-superselect-inner',
            
            width: (this.realWidth || this.defaultWidth) - (this.triggerVisible ? 20 : 0),
            //height: this.realHeight || this.defaultHeight,
            autoHeight: true,
            
            data: this.selecteds,
            
            emptyText: '请选择',
            tpl: '<%=this.createView()%>',
            
            valueField: this.valueField,
            textField: this.textField,
            itemRenderer: this.itemRenderer,
            
            enableDeferRefresh: false,
            
            createView: function(){            
                var data = this.data;
                if(!data || data.getCount() == 0) return '';   
                data = this.data.view;
                
                var text = '<input id="'+this.id+'text'+'" type="text" class="e-superselect-text" autocomplete="off" size="24" />';
                
                var sb = [''];
                for(var i=0,l=data.length; i<l; i++){
                    var o = data[i];
                    sb[sb.length] = this.itemRenderer(o, i);
                }
                //sb[sb.length] = text;
                
                //sb[sb.length] = this.itemRenderer(data[0]);
                
                sb[sb.length] = '<div class="e-clear"></div>';
                        
                //sb[sb.length] =text;
                return sb.join('');
            },
            onmousedown: this._onViewMouseDown.bind(this)
        });
        
        Edo.util.Dom.addClass(this.field, 'e-superselect-text');
        
        var viewEl = this.view.el;
        Edo.util.Dom.append(viewEl, this.field);
        
        this.initInput();
                
        
        Edo.util.Dom.on(document, 'mousedown', function(e){
            if(this.within(e)){
                Edo.util.Dom.addClass(this.el, this.focusClass);    
            }else{                   
                this.view.clearSelect();
                Edo.util.Dom.removeClass(this.el, this.focusClass);    
            }
        }, this);
    },
    createPopup: function(){    
        
        Edo.controls.SuperSelect.superclass.createPopup.a(this, arguments);        
        
    },
    showPopup: function(e){
        Edo.controls.SuperSelect.superclass.showPopup.c(this, e);
        this.data.filter(this.filterFn, this);
    },    
    filterFn: function(o){
        var data = this.selecteds;
        if(data.indexOf(o) != -1) return false;
        
        var text = String(o[this.displayField]);
        if(text.indexOf(this.text) != -1){
            return true;
        }else{
            return false;
        }
    },    
    syncSize: function(){    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.SuperSelect.superclass.syncSize.call(this);
        
        
        this.autoInput();
    },
    initInput: function(){
        if(!this.input){
            this.input = this.field;
            Edo.util.Dom.on(this.input, 'keydown', function(e){
                this.autoInput();
            }, this);
            Edo.util.Dom.on(this.input, 'keyup', function(e){
                this.autoInput();
            }, this);
        }
        
    },
    //自适应输入框的宽度
    autoInput: function(){
        var text = this.field.value;
        if(text != ''){
            Edo.util.Dom.addClass(this.field, 'e-supserselect-text-inputed');
            
            var size = Edo.util.TextMetrics.measure(this.field, text);
            
            Edo.util.Dom.setWidth(this.field, size.width+21);//2是padding-right
        }else{
            Edo.util.Dom.removeClass(this.field, 'e-supserselect-text-inputed');
            this.field.style.width = '15px';
        }
        this.field.style.height = '20px';
        this.field.style.lineHeight = '20px';
        //Edo.util.Dom.repaint(this.input);
        //实时看组件的高度是否有变动, 如果有,则调整尺寸
        
//        
//        this.heightGet = false;
//        this.view.heightGet = false;
//        this.relayout('input');
    },
    fixInput: function(index){    
        var items = this.view.getItems()
        var targetItem = items[index];
        var viewEl = this.view.el;
        if(targetItem){
            Edo.util.Dom.after(targetItem, this.field);
        }else {
            Edo.util.Dom.preend(viewEl, this.field);
        }
        this.focus.defer(50, this);
    },
    _onViewMouseDown: function(e){
        var el = this.view.getItemEl(e.target);
        
        if(el){
            this.itemIndex = this.view.getItemIndex(el);
            return;
        }
        
        this.view.clearSelect();
        
        //根据鼠标坐标, 判断在哪一个之后(如果没有找到目标记录, 则加在第一个)        
        this.itemIndex = this.findTargetItem(e.xy);
        this.fixInput(this.itemIndex);
        //if(e.target != this.field){
            
        //}
        
        //
    },
    findTargetItem: function(xy){
        var boxs = [];
        var items = this.view.getItems()
        for(var i=0,l=items.length; i<l; i++){
            var item = items[i];
            var box = Edo.util.Dom.getBox(item);
            boxs.push(box);
            
            box.el = item;
        }
        var elBox = this.getBox();
        
        for(var i=0,l=boxs.length; i<l; i++){
            var box = boxs[i];
            var next = boxs[i+1];
            if(next && box.y == next.y){
                box.right = next.x;                
            }else{
                box.right = elBox.right;
            }
            box.width = box.right - box.x;
        }
        for(var i=0,l=boxs.length; i<l; i++){
            var box = boxs[i];                        
            
            if(Edo.util.Dom.isInRegin(xy, box)){                
                return i;      //放指定之后
            }
            
            if(i==0){                
                box.right = box.x;
                box.x = elBox.x;
                box.width = box.right - box.x;
                
                if(Edo.util.Dom.isInRegin(xy, box)){                
                    return -1;   //放第一个
                }
            }
        }
        //否则放最后一个
        return items.length - 1;
    },
    destroy : function(){
        
        Edo.controls.SuperSelect.superclass.destroy.call(this);       
    }
});

Edo.controls.SuperSelect.regType('SuperSelect');    