/**
    @name Edo.MessageBox
    @class     
    @single
    @description 弹出框:alert,confirm,prompt,loading,saving等
    @example 


*/ 
Edo.MessageBox = {
    OK: ['ok'],
    CANCEL: ['cancel'],
    OKCANCEL: ['ok', 'cancel'],
    YESNO: ['yes', 'no'],
    YESNOCANCEL: ['yes', 'no', 'cancel'],
    
    INFO : 'e-messagebox-info',
    WARNING : 'e-messagebox-warning',
    QUESTION : 'e-messagebox-question',
    ERROR : 'e-messagebox-error',
    DOWNLOAD : 'e-messagebox-download',
    
    buttonText : {
        ok : "确定",//"OK",
        cancel : "取消",//"Cancel",
        yes : "是",//"Yes",
        no : "否"//"No"
    },
    saveText: '保存中...',
    
    hide: function(e){    
        var c = this.config;
        
        if(c && e){
            c.callback.call(c.scope, e ? e.source.action || 'cancel' : 'cancel');
        }
        clearInterval(this.timer);
        if(this.dlg) this.dlg.hide();
    }, 
    /**
        @name Edo.MessageBox#show
        @function
        @description 
        @param {Object} config 弹出框显示配置对象<br/>
<pre>
{
    title: "",            //弹出框标题
    titleIcon: '',      //弹出框标题图标
    icon: '',           //面板左侧图标        
    msg: '',            //面板文本内容
    
    prompt: false,      //是否显示弹出输入框
    multiline: false,   //显示输入框模式是否多行
    
    progress: false,    //是否显示进度条
    progressText: '',   //进度条文本
    progressValue: 0,   //进度值
    
    wait: false,        //是否显示为不断加载模式
        
    enableClose: true,  //是否显示右上角的头部关闭按钮
    autoClose: false,   //是否自动关闭面板,如果为是,则按closeTime延迟关闭
    closeTime: 3000,
    
    buttons: [],        //按钮模式,['ok','yes', 'no', 'cancel']
    
    callback: Edo.emptyFn,  //面板隐藏时的回调函数,会传递按钮的action字符串
    scope: null,            //回调函数的scope
    
    width: 'auto',      //面板宽度
    height: 'auto'      //面板高度        
}            
</pre>          
    */    
    show: function(c){
    
        var children = c.children;
        delete c.children;
        
        Edo.applyIf(c, {
            autoClose: false,           //是否自动关闭
            closeTime: 3000,
            enableClose: true,
            title: '',
            titleIcon: '',
            msg: '',
            width: 'auto',
            height: 'auto',
            callback: Edo.emptyFn
        });
        clearInterval(this.timer);
            
        this.config = c;
        
        if(!this.dlg){
            this.dlg = Edo.create({
                //render: '#body',
                cls: 'e-dragtitle',
                type: 'window',
                minWidth: 180,
                minHeight: 60,
                verticalGap: 0,
                titlebar:[
                    {
                        cls: 'e-titlebar-close',
                        onclick: this.hide.bind(this)
                    }
                ],
                children: [
                    {
                        type: 'box',                        
                        width: '100%',                  
                        border: 0,
                        minHeight: 40,
                        layout: 'horizontal'
                    },{
                        type: 'ct',                                              
                        width: '100%',
                        layout: 'horizontal',
                        defaultHeight: 28,
                        horizontalAlign: 'center',
                        verticalAlign: 'bottom',
                        horizontalGap: 10
                    }
                ]
            });
            
            this.body = this.dlg.getChildAt(0);
            this.foot = this.dlg.getChildAt(1);
        }
        
        var g = this.dlg;
        g.set(c);
        
        g.titlebar.getChildAt(0).set('visible', c.enableClose !== false);
        
//        this.body.removeAllChildren(true);
//        this.foot.removeAllChildren(true);
        
        var o = {
            layout: 'horizontal',
            children: [
                {
                    type: 'div',
                    width: 44,
                    height: 35,
                    cls: c.icon,
                    visible: !!c.icon
                },{
                    type: 'label',                                        
                    text: c.msg
                }
            ]
        };
        
        if(c.prompt){
            o = {                
                layout: 'vertical',
                verticalGap: 0,
                children: [
                    {
                        type: 'label',                        
                        width: '100%',                        
                        text: c.msg
                    },{
                        type: c.multiline ? 'textarea' : 'text',
                        text: c.text,
                        width: '100%',
                        minHeight: c.multiline ? 80 : 22
                    }
                ]
            };
            
        }else if(c.progress){
            o = {
                layout: 'vertical',
                verticalGap: 0,
                children: [
                    {
                        type: 'label',
                                
                        text: c.msg
                    },{
                        type: 'progress',
                        width: '100%',                
                        text: c.progressText || '',
                        progress: c.progressValue || 0
                    }
                ]
            };
        }
        if(c.wait){
            var o = {
            layout: 'horizontal',
            children: [
                    {
                        type: 'div',
                        width: 44,
                        height: 50,
                        cls: c.icon,
                        visible: !!c.icon
                    },
                    {
                        type: 'ct',
                        width: '100%',
                        height: '100%',
                        children: [
                            {
                                type: 'label',                            
                                text: c.msg
                            }, {
                                type: 'progress',
                                showText: false,
                                text: c.progressText || '',
                                progress: c.progressValue || 0,
                                width: '100%'
                            }
                        ]
                    }
                    
                ]
            };
        }
        
        if(children){
            o = {
                children: children
            }
        }
        
        this.body.set(o);
        
        //var btns = 
        var buttons = c.buttons || [];
        var btns = [];
        buttons.each(function(o){
            o = typeof o == 'string' ? {
                    type: 'button',                    
                    text: this.buttonText[o],
                    action: o,
                    minWidth: 70,
                    defaultHeight: 22,
                    onclick: this.hide.bind(this)
                } : o;
                            
            btns.add(o);
                  
        }, this);
        
        this.foot.set({
            visible: btns.length > 0,
            children: btns
        });
        
        if(c.wait){
            var p = this.body.getChildAt(1).getChildAt(1);
            var pi = 0;
            this.timer = setInterval(function(){    
                pi += 10;
                p.set({
                    progress: pi
                });
                if(pi >= 100) pi = 0;
            }, c.interval || 200);
        }
        
        g.show(c.x, c.y, true);
        if(c.autoClose){
            var closeTime = c.autoClose === true ? c.closeTime : c.autoClose;
            this.hide.defer(closeTime, this);
        }
        return this;
    },
    /**
        @name Edo.MessageBox#alert
        @function
        @description 
        @param {String} title 标题
        @param {String} msg 内容
        @param {Function} callback 回调函数.
        @param {String} scope 回调函数的scope
    */            
    alert: function(title, msg, callback, scope){
        this.show({
            title : title,
            msg : msg,
            buttons: this.OK,
            callback: callback,
            scope : scope
        });
        return this;
    },
    /**
        @name Edo.MessageBox#confirm
        @function
        @description 
        @param {String} title 标题
        @param {String} msg 内容
        @param {Function} callback 回调函数
        @param {String} scope 回调函数的scope
    */
    confirm : function(title, msg, callback, scope){
        this.show({
            title : title,
            msg : msg,
            buttons: this.YESNO,
            callback: callback,
            scope : scope,
            icon: this.QUESTION
        });
        return this;
    },
    /**
        @name Edo.MessageBox#prompt
        @function
        @description 
        @param {String} title 标题
        @param {String} msg 内容
        @param {Function} callback 回调函数
        @param {String} scope 回调函数的scope
        @param {Boolean} multiline 是否多行输入框
        @param {String} text 输入框文本
    */    
    prompt : function(title, msg, callback, scope, multiline, text){
        this.show({
            title : title,
            msg : msg,
            buttons: this.OKCANCEL,
            callback: callback,
            minWidth:250,
            scope : scope,
            prompt:true,
            multiline: multiline,
            text: Edo.isValue(text) ? text: ''
        });
        return this;
    },
    /**
        @name Edo.MessageBox#updateProgress
        @function
        @description 更新进度条内容
        @param {Number} progressValue 进度值
        @param {String} progressText 进度条文本
    */
    updateProgress: function(value, text){
        var p = this.body.getChildAt(1);
        if(p && p.type == 'progress'){
            p.set({
                progress: value,
                text: text
            });
        }
    },
    /**
        @name Edo.MessageBox#loading
        @function
        @description 加载状态进度显示
        @param {String} title 标题
        @param {String} msg 内容
    */
    loading: function(title, msg){
        Edo.MessageBox.show({
            title: title,   
            msg: msg,
           children: [
            {
                type: 'div',
                cls: 'e-messagebox-wait',
                width: '100%',
                height: '100%'
            }
           ],
           width:250
        });
    },
    /**
        @name Edo.MessageBox#saveing
        @function
        @description 保存状态进度显示
        @param {String} title 标题
        @param {String} msg 内容
    */    
    saveing: function(title, msg){
        Edo.MessageBox.show({
            title: title,
           msg: msg,
           enableClose: false,
           progressText: this.saveText,
           width:300,
           wait:true,
           interval: 200,
           icon: 'e-messagebox-save'
        }); 
    }
};
