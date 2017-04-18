/*
    树形
    source: 除delete之外的所有
    view:   当前数据视图,用于配合table,tree显示
    
    数据存储是数组,但是可以体现树形逻辑
*/
/**
    @name Edo.data.DataTree
    @class 
    @typeName datatree
    @description 树形数据源
    @extend Edo.data.DataTable
    @example 
*/ 
Edo.data.DataTree = function(data){
    Edo.data.DataTree.superclass.constructor.call(this, data);
}
Edo.data.DataTree.extend(Edo.data.DataTable, {    
    /**
        @name Edo.data.DataTree#load
        @function
        @description 加载数据
        @param {Array} tree 树形数据
    */    
    load: function(tree, view){
        if(!tree) tree = [];
        if(!(tree instanceof Array)) tree = [tree];     
        
        this.children = tree;
        
        this.source = [];
        var data = [];
        this._createTable(tree, data);
        this.source = data;
        
        if(!view){
            view = [];
            this._createTree(this.children, -1, 0, view, this.filterFn);
        }
        
        Edo.data.DataTree.superclass.load.call(this, data, view);
    },
    //是不是在本方法内,重新排定table?
    syncTreeView: function(action, event){
        var view = [];
        this._createTree(this.children, -1, 0, view, this.filterFn);
        this.refresh(view, action, event);
    },
    //deep深度, stopNode截止的节点, 
    /**
        @name Edo.data.DataTree#collapse
        @function
        @description 收缩节点
        @param {Object} node 节点对象
        @param {Boolean} deep 是否影响子级
    */    
    collapse: function(node, deep){
        node.expanded = false;
        
        if(deep){
            this.iterateChildren(node, function(o){
                o.expanded = false;
            });
        }
        this.canFire = true;
        this.syncTreeView('collapse',{record: node});            
    },
    /**
        @name Edo.data.DataTree#expand
        @function
        @description 展开节点
        @param {Object} node 节点对象        
        @param {Boolean} deep 是否展开所有子节点
    */
    expand: function(node, deep){
        node.expanded = true;
        
        var p = this.findParent(node);
        while(p){
            p.expanded = true;
            p = this.findParent(p);
        }
        
        if(deep){
            this.iterateChildren(node, function(o){
                o.expanded = true;
            });
        }
        
        this.canFire = true;
        this.syncTreeView('expand',{record: node});            
    },
    /**
        @name Edo.data.DataTree#toggle
        @function
        @description 伸展节点
        @param {Object} node 节点对象
        @param {Boolean} deep 是否影响子级
    */    
    toggle: function(node, deep){
    
        if(node.expanded) this.collapse(node,deep);
        else this.expand(node,deep);
    },    
    /**
        @name Edo.data.DataTree#add
        @function
        @description 增加节点
        @param {Object} node 节点对象
        @param {Object} parentNode 父节点
    */    
    add: function(node, p){
        if(!p) p = this;
        if(!p.children) p.children = [];
        return this.insert(p.children.length, node, p);
    },
    /**
        @name Edo.data.DataTree#addRange
        @function
        @description 增加节点
        @param {Array} nodes 节点对象
        @param {Object} parentNode 父节点
    */
    addRange: function(nodes, p){
        if(!p) p = this;
        if(!p.children) p.children = [];
        return this.insertRange(p.children.length, nodes, p);
    },
    /**
        @name Edo.data.DataTree#insert
        @function
        @description 增加节点到索引处
        @param {Number} index 索引
        @param {Object} node 节点对象
        @param {Object} parentNode 父节点
    */
    insert: function(index, node, p){
        this.insertRange(index, [node], p);
    },
    /**
        @name Edo.data.DataTree#insertRange
        @function
        @description 增加节点到索引处
        @param {Number} index 索引
        @param {Array} nodes 节点对象
        @param {Object} parentNode 父节点
    */
    insertRange: function(index, records, p){    
        if(!records || !(records instanceof Array)) return;
        
        if(!p) p = this;
        if(!p.children) p.children = [];                        
        
        var cs = p.children;
        for(var i = 0, len = records.length; i < len; i++){
            var r = records[i];            
            cs.insert(index+i, r);            
            this._doAdd(r);
            
            this.iterateChildren(r, function(n){            
                this._doAdd(n);
            }, this);
        }                
        
        //重新生成表格, 这时候, 这个data应该是source, 没有任何过滤
        var data = [];
        this._createTable(this.children, data);
        this.source = data;
        
        this.syncTreeView('add',{
            records: records,
            index: index,
            parentNode: p
        });       
        this.changed = true;
    },
    /**
        @name Edo.data.DataTree#remove
        @function
        @description 删除节点        
        @param {Object} node 节点对象        
    */    
    remove: function(node){
        var p = this.findParent(node);
        if(p){
            p.children.remove(node);
            
            this._doRemove(node);
            
            var data = [];
            this._createTable(this.children, data);                     
            this.source = data;
            
            this.syncTreeView('remove',{
                
                records: [node],
                parent: p
            });
            this.changed = true;
        }                        
    },
    /**
        @name Edo.data.DataTree#removeAt
        @function
        @description 删除节点    
        @param {Number} index 删除索引号    
        @param {Object} parentNode 父节点对象        
    */        
    removeAt: function(index, p){
        if(!p) throw new Error("父节点为空");
        var node = p && p.children ? p.children[index] : null;
        if(node){
            this.remove(node, p);
        }
    },
    /**
        @name Edo.data.DataTree#removeRange
        @function
        @description 删除数据
        @param {Array} records 数据对象数组
        @params {Object} node 节点
    */
    removeRange: function(records, node){
    
        if(!records || !(records instanceof Array)) return;  
        records = records.clone();
        for(var i=0,l=records.length; i<l; i++){
            var record = records[i];
            
            node = this.findParent(record);
            if(node){
                node.children.remove(record);
                this._doRemove(record);
            }
        }
        
        this.changed = true;
        this.syncTreeView('remove',{
            records: records,
            parent: node
        });
        
    },
    /**
        @name Edo.data.DataTree#move
        @function
        @description 移动节点
        @param {Object} node 节点对象
        @param {Object} targetNode 目标节点对象
        @param {String} action 如何移动插入:数字,preend, append, add
    */
    move: function(nodes, target, action){    
        if(!nodes) return;
        if(!Edo.isArray(nodes)) nodes = [nodes];
        var p = this.findParent(target);
        if(!p) return;
        
        this.beginChange();
        for(var i=0,l=nodes.length; i<l; i++){
            var node = nodes[i];
            
            var index = p.children.indexOf(target);
        
            if(Edo.isNumber(action)){
                p = target;
                index = action;
            }else if(action == 'append') {
                index+=1;
            }else if(action == 'add'){
                p = target;
                if(!p.children) p.children = []
                index = p.children.length;
            }else if (action == "preend"){
                
            }
            
            //这里好像有点性能问题
            if(this.isAncestor(node, p) || node == p){
                return false;
            }            
            
            var p1 = this.findParent(node);
            if(p1 == p){
                var index2 = p1.children.indexOf(node);
                if(index2 <= index){
                    index -= 1;
                }
            }
            
            var status = node.__status;
            var status2 = p.__status;
            
            this.remove(node);
            this.insert(index, node, p);        
            
            
            node.__status = status;
            p.__status = status2;
        }
        
        this.endChange('move', {records: nodes, index: index, parentNode: p});        
        
        this.changed = true;
    },
    /**
        @name Edo.data.DataTree#filter
        @function
        @description 筛选过滤节点(树状方式遍历过滤)
        @param {Function} fn 过滤方法
        @param {Object} scope        
    */   
    filter: function(fn, scope){
        //遍历每一个节点,如果true,则生成
        var view = [];
        this.filterFn = fn;
        this._createTree(this.children, -1, 0, view, fn, scope);
        this.refresh(view, 'filter');
    },
    /**
        @name Edo.data.DataTree#findParent
        @function
        @description 查找父节点
        @param {Object} node 节点对象               
    */       
    findParent: function(node){
        //return findParent(this, node);
        var p = this.getById(node.__pid);
        if(!p && this.getById(node.__id)) p = this;
        return p;
    },
    findTop: function(node){        
        while(1){
            if(node.__pid == -1) return node;
            node = this.getById(node.__pid);            
        }        
    },
    /**
        @name Edo.data.DataTree#getChildAt
        @function
        @description 获得一个节点下children中index位置的节点
        @param {Object} parentNode 父节点对象               
        @param {Number} index               
        @return {Object}
    */       
    getChildAt: function(parentNode, index){
        if(parentNode.children){
            return parentNode.children[index];
        }
    },
    /**
        @name Edo.data.DataTree#indexOfChild
        @function
        @description 获得一个节点在父节点下的索引号
        @param {Object} parentNode 父节点对象               
        @param {Object} node               
        @return {Number}
    */           
    indexOfChild: function(parentNode, node){
        if(parentNode.children){
            return parentNode.indexOf(node);
        }
        return -1;
    },
    /**
        @name Edo.data.DataTree#isFirst
        @function
        @description 是否是第一个节点                
        @param {Object} node               
        @return {Boolean}
    */        
    isFirst: function(node){
        return !node.__preid;
    },
    /**
        @name Edo.data.DataTree#isLast
        @function
        @description 是否是最后一个节点                
        @param {Object} node               
        @return {Boolean}
    */
    isLast: function(node){
        return !node.__nextid;
    },
    /**
        @name Edo.data.DataTree#isLeaf
        @function
        @description 是否是叶子节点(没有子节点)               
        @param {Object} node               
        @return {Boolean}
    */
    isLeaf: function(node){
        return !node.children ||  node.children.length == 0;
    },
    getPrev: function(node){
        return this.getById(node.__preid);
    },
    getNext: function(node){
        return this.getById(node.__nextid);
    },
    //Returns the path for this node. The path can be used to expand or select this node programmatically.
    //attr: (可选) 默认的组成路径的属性,默认是__id.
    getPath: function(node, attr){
        
    },
    /**
        @name Edo.data.DataTree#getDepth
        @function
        @description 获得节点层次
        @param {Object} node               
        @return {Number}
    */
    getDepth: function(node){
        return node.__depth;
    },
    /**
        @name Edo.data.DataTree#eachChildren
        @function
        @description 遍历节点下一个子级
        @param {Object} node      
        @param {Function} fn                 
        @param {Object} scope
    */
    eachChildren: function(node, fn, scope){
        var cs = node.children;
        if(cs){
            cs.each(fn, scope);
        }
    },    
    /**
        @name Edo.data.DataTree#iterateChildren
        @function
        @description 遍历所有层次的子节点
        @param {Object} node      
        @param {Function} fn                 
        @param {Object} scope
    */    
    iterateChildren: function(p, fn, scope){
        if(!fn) return;
        p = p || this;
        var cs = p.children;
        if(cs){
            for(var i=0,l=cs.length; i<l; i++){
                var c = cs[i];
                if(fn.call( scope|| this, c, i) === false) return;
                this.iterateChildren(c, fn, scope);
            }
        }
    },
    /**
        @name Edo.data.DataTree#contains
        @function
        @description 是否包含子节点
        @param {Object} parentNode      
        @param {Object} childNode
    */     
    contains: function(parentNode, childNode){
        while(childNode.__pid != -1){
            if(childNode.__pid == parentNode.__id) return true;
            childNode = this.getById(childNode.__pid);
        }
        return false;        
    },
    /**
        @name Edo.data.DataTree#hasChildren
        @function
        @description 是否有子节点
        @param {Object} node
    */  
    hasChildren: function(node){
        return node.children && node.children.length > 0;
    },
    
    //将树形结构数据,转换为表格结构(其实是得到source源数据表格)
    _createTable: function(tree, data){       //是一个树结构的数组
        
        for(var i=0,l=tree.length; i<l; i++){
            var t = tree[i];
            
            if(!t.__id) t.__id =  Edo.data.DataTable.id++;
                        
            var cs = t.children;            
            
            data[data.length] = t;
                        
            if(cs && cs.length > 0){
                this._createTable(cs, data);
            }
        }
    },
    //得到树形的view数据视图表格
    _createTree: function(tree, pid, depth, data, fn, scope){
        if(!fn) fn = this.filterFn;
    
        var hasInView = false;  //是否有包含在视图内的节点
        for(var i=0,l=tree.length; i<l; i++){
            var t = tree[i];
            
            t.__pid = pid;
            t.__depth = depth;
            t.__hasChildren = t.children && t.children.length > 0;
                        
            if(typeof(t.expanded) === 'undefined') t.expanded = true;
            else t.expanded = !!t.expanded;            
            
            //__preid, __nextid
            t.__preid = t.__nextid = null;
            if(i != 0) t.__preid = tree[i-1].__id;
            if(i != l-1) t.__nextid = tree[i+1].__id;
            
            var next = fn ? fn.call(scope, t, i) : true;   //是否继续
            
            var index = data ? data.length : 0;    //保留索引
            
            if(next !== false){     //如果next不为false,则先加上了
                if(data) data[index] = t;
                
                hasInView = true;
            }
            var childInView = false;
                        
            if(t.__hasChildren){
                //如果确定是不是收缩的,则传递data, 否则传递null
                childInView = this._createTree(t.children, t.__id, depth+1, t.expanded !== false ? data : null, fn);
            }
            
            if(childInView && next === false){  //如果next为false,但是子元素内有符合条件的, 则插入
                if(data) data.insert(index, t);
                
                hasInView = true;
            }
        }
        return hasInView;
    },
    isAncestor: function(p, n){
        if(!p || !n) return false;
        while(n){
            n = this.findParent(n);
            if(n == p) return true;            
        }
        return false;        
    },
    isDisplay: function(record){
        var p = this.findParent(record);
        if(p == this) return true;
        if(!p.expanded) return false;
        return this.isDisplay(p);
    },
    /**
        @name Edo.data.DataTree#getChildren
        @function
        @description 获得节点的子节点(或者是深度子节点集合)
        @param {Object} node 节点对象
        @param {Boolean} deep 是否获取深度子节点集合
    */
    getChildren: function(node, deep){
        if(deep){
            var children = [];
            this.iterateChildren(record, function(node){
                children.add(node);
            });
            return children;
        }else{
            return node.children;
        }
    }
});

Edo.data.DataTree.regType('datatree');

//判断n是否是p的子节点
Edo.data.DataTree.isAncestor = function(p, n){                    
    if(!p || !n) return false;
    var cs = p.children;
    if(cs){
        for(var i=0,l=cs.length; i<l; i++){
            var c = cs[i];
            if(c == n) return true;
            var r = Edo.data.DataTree.isAncestor(c, n);
            if(r) return true;
        }
    }
    return false;
}