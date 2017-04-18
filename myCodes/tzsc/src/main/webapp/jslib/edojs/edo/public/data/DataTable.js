/*
    数据表格
    source: 除delete之外的所有
    view:   当前数据视图,用于配合table,tree显示
    
1.source    是完整的数据表格
2.view      是过滤,排序后的数据表格
3.idHash    保存的是source的id引用?

是不是加一个原则:
    1.source    是完整的数据, 可以增删改
    2.view      只是数据视图,只能排序,过滤,但是不能进行增删改操作
    
    就像sql2000数据库的逻辑一样
*/
/**
    @name Edo.data.DataTable
    @class 
    @typeName datatable
    @description 表格数据源
    @extend Edo.core.Component
    @example 
*/ 
Edo.data.DataTable = function(data){
    Edo.data.DataTable.superclass.constructor.call(this);
    
    this.source = this.view = [];
    this.modified = {};
    this.removed = {};      
    this.idHash = {};
    
    //if(data) 
    this.load(data);
    
    //this._setConn(new Edo.data.PagingConnection());
/**
    @name Edo.data.DataTable#datachange
    @event 
    @description 数据改变事件
    @property {String} action 数据改变动作(add,remove,update,move,clear,reset,resetfield,beforeload,load,refresh,filter,collapse,expand,sort)
 */
}
Edo.data.DataTable.extend(Edo.core.Component, {
    componentMode: 'data',
    
    dataTable: true,
    
/**
    @name Edo.data.DataTable#source
    @property 
    @type Array
    @description 源Array数组对象
 */    
 /**
    @name Edo.data.DataTable#view
    @property 
    @type Array
    @description 数据视图(如过滤/排序/折叠后的数据视图)
 */    
    _setData: function(data){
        this.load(data);
    },
    
    refresh: function(view, action, event){                //刷新数据视图
        if(view){
            this.length = 0;
            this.view = view;
        }
        this.fire(action || 'refresh', event);                 //数据组件,只需要监听refresh事件即可!
    },
    /**
        @name Edo.data.DataTable#load
        @function
        @description 加载数据
        @param {Array} data 数组形式的数据
    */      
    load: function(data, view){
        if(!data) data = [];
        var e = {data: data};
        if(this.fire('beforeload', e) !== false){   //这是属于"破坏"性的操作,如果有必要,要保存原来的操作结果,请使用beforeload事件
            
            this._doLoad(data);
            
            this.view = this.source = data;       //更新原始数据                    
            this.modified = {};
            this.removed = {};
            //this.idHash = {};
            
            if(view) this.view = view;
            
            this.changed = false;   //修改changed标记(表示数据是没有经过任何数据操作:增,删,改)
            
            this.canFire = true;
            
            this.fire('load', e);
        }
    },  
    /**
        @name Edo.data.DataTable#reload
        @function
        @description 重加载数据, 去除新增/删除/修改的数据标记        
    */  
    reload: function(){
        this.load(this.source, this.view);
    },
    /**
        @name Edo.data.DataTable#add
        @function
        @description 增加数据
        @param {Object} record 单个对象
    */      
    add: function(record){           
        this.insert(this.view.length, record);
    },
    /**
        @name Edo.data.DataTable#addRange
        @function
        @description 增加数据
        @param {Array} records 多个对象
    */  
    addRange: function(records){           
        this.insertRange(this.view.length, records);
    },
    /**
        @name Edo.data.DataTable#insert
        @function
        @description 增加数据
        @param {Number} index 
        @param {Object} record 单个对象  
    */
    insert: function(index, record){
        this.insertRange(index, [record]);
    },
    /**
        @name Edo.data.DataTable#insertRange
        @function
        @description 增加多个数据
        @param {Number} index
        @param {Array} records 数组对象        
    */
    insertRange: function(index, records){
        if(!records || !(records instanceof Array)) return;  
        
        var d = this.source, v = this.view;
        
        for(var i = 0, len = records.length; i < len; i++){
            var r = records[i];            
            v.insert(index, r);
            
            if(d !== v) d.insert(index, r);         //在源数据上也新增
            
            this._doAdd(r);
        }
        this.fire('add', {
            records: records, 
            index: index
        });
        this.changed = true;
    },
    
    /**
        @name Edo.data.DataTable#remove
        @function
        @description 删除数据
        @param {Object} record 数据对象
    */      
    remove: function(record){                
        this.removeRange([record]);
    },
    /**
        @name Edo.data.DataTable#removeAt
        @function
        @description 根据索引删除数据
        @param {Number} index
    */     
    removeAt: function(index){
        this.remove(this.getAt(index));
    },
    /**
        @name Edo.data.DataTable#removeRange
        @function
        @description 删除数据
        @param {Array} records 数据对象数组
    */
    removeRange: function(records){
        if(!records || !(records instanceof Array)) return;  
        records = records.clone();
        
        for(var i=0,l=records.length; i<l; i++){
            var record = records[i];
            var index = this.view.indexOf(record);
            if(index > -1){
                this.view.removeAt(index);
                this.source.remove(record);             //从源数据上也删除
//                delete this.idHash[record.__id];
//                delete this.modified[record.__id];
                
                this._doRemove(record);                
            }
        }
        
        this.fire('remove', {records: records});
        this.changed = true;
    },
    /**
        @name Edo.data.DataTable#updateRecord
        @function
        @description 批量更新数据对象属性值
        @param {Object} record 数据对象
        @param {Object} properties {key: value, ..}形式的对象
    */        
    updateRecord: function(record, o){
        var canFire = this.canFire;
        this.beginChange();
        for(var p in o){
            this.update(record, p, o[p]);
        }
        this.canFire = canFire;
        this.fire('update',{
            record: record
        });
        this.changed = true;
    },
    /**
        @name Edo.data.DataTable#update
        @function
        @description 更新数据对象属性值
        @param {Object} record 数据对象
        @param {String} field 属性名
        @param {Object} value 属性值
    */  
    update: function(record, field, value){
        var old = Edo.getValue(record, field);
        //var old = record[field];
        //alert(old+":"+value);
        var type = typeof(old);
        if(type == 'object'){
            if(old === value) return false;
        }
        else if(String(old) == String(value)) return false;
        
        //record[field] = value;
        Edo.setValue(record, field, value);
        
        //to data
        this._doUpdate(record, field, old);
        
        this.fire('update',{
            record: record,
            field: field,
            value: value
        });
        this.changed = true;
        return true;
    },
    /**
        @name Edo.data.DataTable#move
        @function
        @description 移动数据对象
        @param {Object} record 数据对象
        @param {Number} index
    */       
    move: function(records, index){//index:数字, 行对象       
     
        var target = Edo.isInt(index) ? this.getAt(index) : index;
        
        if(!Edo.isArray(records)) records = [records];
        
        for(var j=0,l=records.length; j<l; j++){
            var record = records[j];
            
            this.view.remove(record);
            
            if(target){
                var index = this.indexOf(target);
                this.view.insert(index, record);
            }else{
                this.view.add(record);
            }
        }
        
        //alert(this.indexOf(target));       
        this.fire('move',{
            records: records,
            index: this.indexOf(target)
        });
        
        this.changed = true;
        
    },
    /**
        @name Edo.data.DataTable#clear
        @function
        @description 清楚数据
    */       
    clear: function(){
        this.source = this.view = [];
        this.modified = {};
        this.removed = {};
        this.fire('clear');
        this.changed = true;
    },    
    /**
        @name Edo.data.DataTable#reset
        @function
        @description 还原行数据对象,一个被修改过的行对象, 换到到修改前
        @param {Object} record 数据对象        
    */     
    reset: function(record){
        var r = this.modified[record.__id];
        if(r){
            Edo.apply(record, r);
            
            delete this.modified[record.__id];
            delete record.status;
            
            this.fire('reset',{
                record: record
            });
        }
    },    
    /**
        @name Edo.data.DataTable#resetField
        @function
        @description 还原行数据对象的某个属性
        @param {Object} record 数据对象        
        @param {String} field 属性名        
    */     
    resetField: function(record, field){
        var r = this.modified[record.__id];
        if(r){
            var v = r[field];
            if(typeof v !== 'undefined'){
                delete r[field];
                record[field] = v;
                
                this.fire('resetfield',{
                    record: record,
                    field: field,
                    value: v
                });
            }
        }
    }, 
    /**
        @name Edo.data.DataTable#sort
        @function
        @description 排序数据
        @param {Function} sortFn 排序算法函数        
    */   
    sort: function(sortFn){
//        function sortFn(pre, now){
//            if(fn(pre, now) == true) return 1;
//            else return -1;
//        }
        //可以自己实现一个优化排序算法
        this.view.sortByFn(sortFn);
        if(this.view !== this.source){
            this.source.sortByFn(sortFn);    
        }
        this.fire('sort');
    },    
    /**
        @name Edo.data.DataTable#sortField
        @function
        @description 针对某一个field行属性进行升降排序
        @param {String} field 属性名
        @param {String} direction 排序方向.ASC或DESC
    */
    sortField : function(field, direction){
        direction = direction || 'ASC';
        var fn;
        if(direction.toUpperCase() == 'ASC'){
            fn = function(r1, r2){
                var v1 = r1[f], v2 = r2[f];
                return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
            };
        }else{
            fn = function(r1, r2){
                var v1 = r1[f], v2 = r2[f];
                return v1 < v2 ? 1 : (v1 > v2 ? -1 : 0);
            };
        }
        this.sort(fn);
    },
    /**
        @name Edo.data.DataTable#filter
        @function
        @description 过滤数据
        @param {Function} fn 过滤算法函数
        @param {Object} scope 过滤函数中的this指向对象
    */
    filter: function(fn, scope){
        var view = this.view = [];
        var data = this.source;
	    for (var i = 0, j = data.length; i < j; i++){
	        var record = data[i];
		    if (fn.call(scope, record, i, data) !== false) view[view.length] = record;
	    }
        this.fire('filter');
    },
    /**
        @name Edo.data.DataTable#clearFilter
        @function
        @description 清除过滤, 还原数据视图
    */
    clearFilter : function(){
        this.filterFn = null;
        if(this.isFiltered()){
            this.view = this.source;         
            this.fire('filter');
        }
    },
    /**
        @name Edo.data.DataTable#isFiltered
        @function
        @description 判断数据视图是否被过滤过
        @return Boolean
    */
    isFiltered: function(){
        return this.view.length != this.source.length;
    },
    //查找:传递一个object,找出属性符合最多的对象的Index.
    //这其实是个很复杂的概念:1)默认是从当前数据视图查找;2)如果传递source对象,就从source对象查找(并且如果是删除的数据,不会查找出来)
    /**
        @name Edo.data.DataTable#findIndex
        @function
        @description 查找一个符合特征的对象
        @param {Object} attributes {key:value}形式的特性对象
        @return {Number} 
    */
    findIndex: function(attribute, array){
        if(array === true) array = this.source;
        array = array || this.view;
        for(var i=0,l=array.length; i<l; i++){
            var o = array[i];
            if(o === attribute) return i;
            
            var all = true;     //全部匹配才行
            for(var p in attribute){
                if(attribute[p] != o[p]) {
                    all = false;
                    break;
                }
            }
            if(all) return i;
        }
        return -1;
    },
    /**
        @name Edo.data.DataTable#find
        @function
        @description 查找一个符合特征的对象
        @param {Object} attributes {key:value}形式的特性对象
        @return {Object} 
    */
    find: function(attribute, array){
        if(array !== false) array = this.source;
        array = array || this.view;
        return array[this.findIndex(attribute, array)];
    },       
    /**
        @name Edo.data.DataTable#getById
        @function
        @description 根据id查找对象(从源数据)
        @param {String} id row.__id
        @return {Object} 
    */
    getById: function(id){
        return this.idHash[id];
    },
    /**
        @name Edo.data.DataTable#getViewById
        @function
        @description 根据id查找对象(从数据视图)
        @param {String} id row.__id
        @return {Object} 
    */
    getViewById: function(id){
        return this.viewHash[id];
    },
    /**
        @name Edo.data.DataTable#getAt
        @function
        @description 根据index查找对象(从数据视图)
        @param {Number} index
        @return {Object} 
    */
    getAt: function(index){
        return this.view[index];
    },
    /**
        @name Edo.data.DataTable#indexOf
        @function
        @description 根据对象查找对象的索引号
        @param {Object} record
        @return {Number} 
    */
    indexOf: function(record){
        return this.view.indexOf(record);
    },
    /**
        @name Edo.data.DataTable#indexOfId 
        @function
        @description 根据id查找对象的索引号
        @param {String} id
        @return {Number} 
    */
    indexOfId : function(id){
        var r = this.idHash[id];
        return r ? this.indexOf(r) : -1;
    },
    /**
        @name Edo.data.DataTable#getCount 
        @function
        @description 数据视图行对象数目     
        @return {Number} 
    */
    getCount: function(){
        return this.view.length;
    },
    /**
        @name Edo.data.DataTable#isEmpty 
        @function
        @description 是否是空数据     
        @return {Boolean} 
    */
    isEmpty: function(){
        return this.view.length == 0;
    },
    /**
        @name Edo.data.DataTable#each 
        @function
        @description 遍历数据视图     
        @param {Function} fn
        @param {Object} scope
    */
    each : function(fn, scope){
        this.view.each(fn, scope);
    },
    fire: function(action, event){
        if(this.canFire){                    
            event = Edo.apply({
                type: 'datachange',
                action: action,
                source: this
            }, event);
            this._onFire(event);
            
            if(['beforeload', 'load', 'refresh', 'filter', 'collapse', 'expand', 'sort'].indexOf(action) == -1){
                this.changed = true;
            }
            
            return this.fireEvent('datachange', event);
        }
    },    
    /**
        @name Edo.data.DataTable#isModify 
        @function
        @description 判断一个行数据是否被修改过     
        @param {Object} record
        @return {Boolean}
    */
    isModify: function(record){
        var ro = this.modified[record.__id];
        if(!ro) return false;
        return true;
    },   
    /**
        @name Edo.data.DataTable#isFieldModify 
        @function
        @description 判断一个行数据的某个属性是否被修改过     
        @param {Object} record
        @param {String} field
        @return {Boolean}
    */ 
    isFieldModify: function(record, field){
        var ro = this.modified[record.__id];
        if(!ro) return false;
        if(typeof(ro[field]) !== 'undefined') return true;
        return false;
    },        
    /**
        @name Edo.data.DataTable#beginChange 
        @function
        @description 开始改变数据(这时候调用add,update,remove等方法是不会激发datachange事件的)        
    */ 
    beginChange: function(){
        this.canFire = false;
    },    
    /**
        @name Edo.data.DataTable#endChange 
        @function
        @description 结束改变数据(激发datachange事件)
    */ 
    endChange: function(action, event){
        this.canFire = true;
        this.refresh(null, action, event);        
    },
    /**
        @name Edo.data.DataTable#getDeleted 
        @function
        @description 获得删除的数据       
        @return {Array}
    */ 
    getDeleted: function(){
        var rs = [];
        var os = this.removed;
        for(var id in os){
            var o = os[id];
            if(o.__status == 'remove'){
                rs.add(os[id]);
            }
        }
        return rs;
    },
    /**
        @name Edo.data.DataTable#getDeleted 
        @function
        @description 获得修改的数据
        @param {String} idField 行记录的唯一值属性,比如id, uid等
        @return {Array}
    */ 
    getUpdated: function(idField){   
        idField = idField ||  this.idField;    
        var rs = [];
        var os = this.modified;        
        for(var id in os){
            var o = os[id];
            var r = this.getById(id);
            o[idField] = r[idField];
            rs.add(o);
            
            for(var p in o){
                o[p] = r[p];
            }
        }
        return rs;
    },
    /**
        @name Edo.data.DataTable#getAdded 
        @function
        @description 获得新增的数据       
        @return {Array}
    */
    getAdded: function(){
        var rs = [];   
        for(var i=0,l=this.source.length; i<l; i++){
            var o = this.source[i];
            if(o.__status == 'add'){
                rs.add(o);
            }
        }
        return rs;
    },
    _doLoad: function(data){
        var idHash = this.idHash = {};    //load的时候,清空原有的idHash
        var id = Edo.data.DataTable.id;
        for(var i=0,l=data.length; i<l; i++){
            var r = data[i];
            if(!r.__id || idHash[r.__id]) r.__id = id++;        //千万不能动...
            //r.__id = id++;
            idHash[r.__id] = r;
            delete r.__status;
            //r.__status = 'normal';
        }
        Edo.data.DataTable.id = id;
    },
    _doAdd: function(record){
        if(record.__id){
            var r = this.idHash[record.__id];
            if(r){
                if(r !== record){
                    record.__id = null;
                }
            }
        }
        if(!record.__id) {
            record.__id = Edo.data.DataTable.id++;
        }
        this.idHash[record.__id] = record;
        if(!record.__status) record.__status = 'add';
        else delete record.__status;
    },
    _doRemove: function(record){
        if(record.__status != 'add'){
            record.__status = 'remove';
        }
        this.removed[record.__id] = record;
        delete this.idHash[record.__id];
        delete this.modified[record.__id];
        //delete record.__id;
    },
    idField: 'id',
    _doUpdate: function(record, field, value){
        if(record.__status != 'add'){
            record.__status = 'update';
            var erow = this.modified[record.__id];
            if(!erow){
                erow = this.modified[record.__id] = {};
            }
            if(typeof erow[field] === 'undefined') erow[field] = value;   //只保存一次,初始值
        }
    },
    _onFire: function(e){
        if(e.action == 'update') return;      
        var viewHash = this.viewHash = {};         //viewHash是可视的数据视图快速索引  
        var view = this.view;
        for(var i=0,l=view.length; i<l; i++){
            var r = view[i];
            viewHash[r.__id] = r;
            r.__index = i;
        }
    },
    
    getRecord: function(record){
        var type = Edo.type(record);
        if(type == 'number'){
            record = this.getAt(record);
        }else if(type == 'string'){
            record = this.getById(record);
        }
        return record;
    },
    
    //数据指针:选中状态逻辑(单选)    
    selected: null,
    getSelectedIndex: function(){
        return this.indexOf(this.selected);
    },
    getSelected: function(){
        if(!this.selected || this.indexOf(this.selected) == -1) {
            this.selected = null;
            return null;
        }            
        return this.selected;
    },
    isSelected: function(record){
        record = this.getRecord(record);
        if(record == this.selected) return true;
        return false;
    },
    select: function(record){
        record = this.getRecord(record);        
        if(!record || this.isSelected(record)) return false;
        if(this.fireEvent('beforeselectionchange',{
            type: 'beforeselectionchange',
            source: this,
            selected: record
        }) === false) return false;
        this.selected = record;
        this.fireEvent('selectionchange', {
            type: 'selectionchange',
            source: this,
            selected: this.selected
        });
    },
    deselect: function(record){
        record = this.getRecord(record);
        if(!record || !this.isSelected(record)) return false;
        if(this.fireEvent('beforeselectionchange',{
            type: 'beforeselectionchange',
            source: this,
            selected: this.selected,
            deselected: record
        }) === false) return false;
        this.selected = null;        
        this.fireEvent('selectionchange', {
            type: 'selectionchange',
            source: this,
            selected: this.selected
        });
    },
    clearSelect: function(){
        this.deselect(this.selected);
    },
    firstSelect: function(){
        this.select(0);
    },
    prevSelect: function(){
        this.select(this.getSelectedIndex() - 1);
    },
    nextSelect: function(){
        this.select(this.getSelectedIndex() + 1);
    },
    lastSelect: function(){
        this.select(this.getCount()-1);
    }
});

Edo.data.DataTable.id = 1000;

Edo.data.DataTable.regType('datatable');


Edo.data.cloneData = function(o){
    var c = null;
    if(o instanceof Array){
        c = o.clone();
    }else{
        c = Edo.apply({}, o);
    }
    delete c.__id;    
    delete c.__index;
    delete c.__status;
    
    delete c.__pid;
    delete c.__preid;
    delete c.__nextid;
    delete c.__hasChildren
    
    return c;
}

/*
    一些思考:
    
    1.索引, 新增, 删除等, 应该是在source进行操作.
    2.view的index是不准的, 应该通过record来锚记和定位.
    3.是否默认的数据操作是针对source的???       也不太好
    
*/