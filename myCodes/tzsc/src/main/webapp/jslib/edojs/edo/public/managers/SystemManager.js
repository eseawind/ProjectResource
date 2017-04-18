/**
    @name Edo.managers.SystemManager
    @class 
    @single
    @description 组件系统管理器   
    @example 
*/ 
Edo.managers.SystemManager = {
    all: {},    
    /**
        @name Edo.managers.SystemManager#register
        @function
        @static
        @param {Object} cmp 组件对象(所有从Edo.core.Component派生的类对象)        
        @description 将组件加入组件对象池
    */
    register: function(c){        
        if(!c.id) throw new Error("必须保证组件具备id");            
        var o = Edo.managers.SystemManager.all[c.id];
        if(o) throw new Error("已存在id为:"+o.id+"的组件");
        
        Edo.managers.SystemManager.all[c.id] = c;       
        if(c.id == 'window') 
        if(window[c.id]) throw new Error("不能设置此ID:"+o.id);
        window[c.id] = c;
    },
    /**
        @name Edo.managers.SystemManager#unregister
        @function
        @static
        @param {Object} cmp 组件对象(所有从Edo.core.Component派生的类对象)        
        @description 将组件从组件对象池注销
    */
    unregister: function(c){
        delete Edo.managers.SystemManager.all[c.id];
        //if(c == WINDOW[c.id]) WINDOW[c.id] = null;//非常耗性能!
        //WINDOW[c.id] = null;
    },
    /**
        @name Edo.managers.SystemManager#destroy
        @function
        @static        
        @description 销毁所有组件(页面关闭情况等特殊场合使用) 
    */
    destroy: function(c){        
        if(c){
            if(typeof c === 'string') c = Edo.managers.SystemManager.all[c];
            if(c) c.destroy();
        }else{           
            var un = Edo.managers.SystemManager.unregister;
            var all = Edo.managers.SystemManager.all;
            //1)找出所有的topContainer顶级容器
            var tops = [];
            for(var id in all){
                var o = all[id];
                if(!o.parent) tops.push(o);
            }
            var sss = new Date();            
            for(var i=0,l=tops.length; i<l; i++){    
                var o = tops[i];
                if(all[o.id]){
                    o.destroy(true);
                }
            }
            //Binding.clearBind();                        
        }
    },
    /**
        @name Edo.managers.SystemManager#get
        @function
        @static       
        @param {String} id 组件id
        @description 根据id获得组件对象.同Edo.get(id)
    */    
    get: function(id){
        return Edo.managers.SystemManager.all[id];
    },
    /**
        @name Edo.managers.SystemManager#getByName
        @function
        @static       
        @param {String} name 组件name
        @param {Object} parent 父容器.为空表示从全局获取
        @description 从一个父容器下获取所有name命名的组件集合.同Edo.getByName(name, parent)
    */       
    getByName: function(name, parent){
        return Edo.managers.SystemManager.getByProperty('name', name, parent);
    },    
    /**
        @name Edo.managers.SystemManager#getByProperty
        @function
        @static
        @param {String} property 属性名
        @param {Object} value 属性值
        @param {Object} parent 父容器.为空表示从全局获取
        @description 根据某一属性匹配, 获得所有匹配的组件
    */
    getByProperty: function(name, value, parent){  //
        var cs = [];
        var m = Edo.managers.SystemManager;
        var all = m.all;
        for(var id in all){
            var o = all[id];
            if(o[name] == value){
                if(!parent || (parent && m.isAncestor(parent, o))){
                    cs.push(o);
                }
            }
        }
        return cs;
    },
    /**
        @name Edo.managers.SystemManager#getType
        @function
        @static       
        @param {String} type 类别名, 如box,app,text等
        @description 根据类别名获取类对象.同Edo.getType.一般用来创建类的实例,如new Edo.getType('box')
    */         
    getType: function(type){
        return Edo.types[type.toLowerCase()];
    },
    /**
        @name Edo.managers.SystemManager#build
        @function
        @static       
        @param {Object} config 组件配置对象
        @description 根据一个组件配置对象,创建组件实例.同Edo.create, Edo.build
    */          
    build: function(config){
        if(!config) return;
        if(typeof config === 'string') {
            if(Edo.types[config]){
                config = {type: config};
            }
            else{
                //config = styleHandler(config);//
                var swf =  Edo.getParser();
                var o = swf.parserXML(config);
                if(!o.success) throw new Error(o.errorMsg); 
                var config = o.json;
                if(!config.renderTo) config.renderTo = document.body;
            }
        }        
        if(!config.constructor.superclass) {
            var cls = Edo.types[config.type.toLowerCase()];
            if(!cls) throw new Error('组件类未定义');
            var obj = new cls();
            if(obj.set) {
                obj.set(Edo.apply({}, config)); //复制配置对象, 防止配置对象被删除属性, 从而是配置对象的拷贝失效,出问题
            }
            return obj;
        }
        return config;
    },
    //判断p是否是c的父元素    
    isAncestor: function(p, c){
        if(p === c) return true;
        var ret = false;
        while (c = c.parent) {
			ret = c == p;
			if(ret) break;
		}
		return ret;
    }
};
Edo.regCmp = Edo.managers.SystemManager.register;
Edo.unregCmp = Edo.managers.SystemManager.unregister;
Edo.get = Edo.getCmp = Edo.managers.SystemManager.get;
Edo.build = Edo.create = Edo.managers.SystemManager.build;
Edo.getType = Edo.managers.SystemManager.getType;
Edo.getByName = Edo.managers.SystemManager.getByName;
Edo.getByProperty = Edo.managers.SystemManager.getByProperty;
Edo.isAncestor = Edo.managers.SystemManager.isAncestor;