/**
    @name Edo.controls.DatePicker
    @class
    @typeName datepicker
    @description 日期选择组件
    @extend Edo.containers.Box
*/
Edo.controls.DatePicker = function(config){        
    /**
        @name Edo.controls.DatePicker#beforedatechange
        @event
        @description 日期改变前事件
        @property {Date} date 日期值
    */   
    /**
        @name Edo.controls.DatePicker#datechange
        @event
        @description 日期改变事件
        @property {Date} date 日期值
    */
    /**
        @name Edo.controls.DatePicker#dateselection
        @event
        @description 日期选择改变事件,由界面激发
        @property {Date} date 日期值
    */

    Edo.controls.DatePicker.superclass.constructor.call(this);    
    
    this.viewDate = new Date();
};
Edo.controls.DatePicker.extend(Edo.containers.Box,{    
    componentMode: 'control',
    /**        
        @name Edo.controls.Date#valueField
        @property
        @default date
    */
    valueField: 'date',
    /**
        @name Edo.controls.DatePicker#weekStartDay
        @property
        @type Number
        @description 第一个列星期日
    */   
    weekStartDay: 0,
    weeks: ['日','一','二','三','四','五','六'],
    
    /**
        @name Edo.controls.DatePicker#valueField
        @property
        @default date
    */  
    valueField: 'date',
    /**
        @name Edo.controls.DatePicker#width
        @property
        @default 177
    */ 
    width: 185,
    
    minWidth: 185,
    defaultWidth: 185,
    minHeight: 160,
    defaultHeight: 190,
    
    /**
        @name Edo.controls.DatePicker#padding
        @property
        @default [0,0,0,0] ( [left, top, right, bottom] )  
    */ 
    padding: [0, 0, 0, 0],
    /**
        @name Edo.controls.DatePicker#verticalGap
        @property
        @default 0
    */ 
    verticalGap: 0,
    
    elCls: 'e-box e-datepicker e-div',
    /**
        @name Edo.controls.DatePicker#date
        @property
        @type Date
        @description 当前选中的日期,默认是今天
    */
    /**
        @name Edo.controls.DatePicker#viewDate
        @property
        @type Date
        @description 当前的视图日期
    */
    
    /**
        @name Edo.controls.DatePicker#format
        @property
        @type String
        @default 'Y-m-d'
        @description 格式化显示日期字符串
    */    
    format: 'Y-m-d',   
    
    yearFormat: 'Y年',
    monthFormat: 'm月',
    
    todayText: '今天',
    clearText: '清除',
    autoSelectDate: true,
    //clearVisible: true,
    /**
        @name Edo.controls.DatePicker#required
        @property
        @type Boolean
        @default true
        @description 不能为空
    */    
    required: true,
    
    valueFormat: false,
    
    footerVisible: true,
    headerVisible: true,
    footerHeight: 35,
    headerHeight: 25,
    
    isWorkingDate: function(date){
        var day = date.getDay();
        return !(day == 0 || day == 6);
    },
    
    dateRenderer: function(v, r, c, i, d, t){
        var h = t.rowHeight-3;
        var cls = (v.viewdate.getMonth() != v.date.getMonth() ? 'e-datepicker-outmonthday' : '');
        if(!t.owner.isWorkingDate(v.date)) c.cls = ' e-datepicker-noworkingday';
        else c.cls = '';
        return '<a style="line-height:'+h+'px;100%" class="'+cls+'" href="javascript:;" hidefocus>'+v.date.getDate()+'</a>';
    },
    
    getValue: function(){
        var v = Edo.controls.DatePicker.superclass.getValue.call(this);
        if(v && this.valueFormat) v = v.format(this.format);
        return v;
    },
    
    within: function(e){      //传递一个事件对象,判断是否在此控件的点击区域内.        
        //var dom = DomQuery.findParent(e.target, '.e-datepicker-pupselect', 2);
        
        var r1 = this.monthpop ? e.within(this.monthpop) : false;        
        var r2 = this.yearpop ? e.within(this.yearpop) : false;        
        
        return Edo.controls.DatePicker.superclass.within.call(this, e) || r1 || r2;
    },
    //private
    monthClickHandler: function(e){
        var dh = Edo.util.Dom;
        var doc = document, bd = doc.body;
        if(!this.monthpop){
            var s = '<div class="e-datepicker-pupselect">';
            for(var i=1; i<=12; i++){
                s += '<a href="#" onclick="return false">'+i+'</a>';
            }
            s += '</div>';
            
            //var s = '<div class="e-datepicker-pupselect"><a href="javascript://void(0);">1</a><a href="javascript://void(0);">2</a><a href="javascript://void(0);">3</a><a href="javascript://void(0);">4</a><a href="javascript://void(0);">5</a><a href="javascript://void(0);">6</a><a href="javascript://void(0);">7</a><a href="javascript://void(0);">8</a><a href="javascript://void(0);">9</a><a href="javascript://void(0);">10</a><a href="javascript://void(0);">11</a><a href="javascript://void(0);">12</a></div>';
            this.monthpop = Edo.util.Dom.append(bd, s);
                        
        }
        this.monthpop.style.display = 'block';
        var bs = dh.getBox(this.monthEl);
        //var xy =dh.getXY(this.header);
        var xy = this.headerCt.getBox();
        dh.setXY(this.monthpop, [bs.x,xy.y+bs.height]);
        
        function onmousedown(e){ 
        
            if(e.within(this.monthpop)){
                var d = this.viewDate.clone();
                d.setMonth(parseInt(e.target.innerHTML)-1);
                this._setViewDate(d);
            }
            //dh.remove.defer(10, dh, [this.monthpop]);
            //dh.remove(this.monthpop);
            //this.monthpop = null;
            this.monthpop.style.display = 'none';
            dh.un(doc, 'mousedown', onmousedown, this);
        }
        dh.on(doc, 'mousedown', onmousedown, this);    
    },
    yearClickHandler: function(e){
        var dh = Edo.util.Dom;
        var doc = document, bd = doc.body;
        var date = this.viewDate;
        function createYear(){
            var year = date.getFullYear();//在当前年上+-4
            if(!this.yearpop){
                var sb = ['<div class="e-datepicker-pupselect">'];                                
                sb.push('<a class="pre" href="#" onclick="return false" >-</a>');
                for(var i=year-4; i<=year+4; i++){
                    sb.push('<a href="#" onclick="return false">',i,'</a>');
                }
                sb.push('<a class="next" href="#" onclick="return false" >+</a>');
                this.yearpop = Edo.util.Dom.append(bd, sb.join(''));
            }else{
                var j = 1;
                var cs = this.yearpop.childNodes;
                for(var i=year-4; i<=year+4; i++){
                    cs[j++].innerHTML = i;
                }
            }
            this.yearpop.style.display = 'block';
        }
        createYear.call(this);
        
        var bs = dh.getBox(this.yearEl);
        var xy = this.headerCt.getBox();
        dh.setXY(this.yearpop, [bs.x,xy.y+bs.height]);
                
        var index = 0, tt = null;
        var sf = this;
        function updateYear(){            
            date = date.add(Date.YEAR, index);
            createYear.call(sf);
        }
        
        function onmouseup(e){            
            clearInterval(tt);                        
            
            dh.un(doc, 'mouseup', onmouseup, this);
        }
        
        function onmousedown(e){
            var remove = false;
            if(e.within(this.yearpop)){
                var t = e.target;
                if(dh.hasClass(t, 'pre')){
                    
                    dh.on(doc, 'mouseup', onmouseup, this);      
                    index = -1;
                    updateYear();
                    tt = setInterval(updateYear, 100);                        
                              
                }else if(dh.hasClass(t, 'next')){
                    dh.on(doc, 'mouseup', onmouseup, this);
                    index = 1;
                    updateYear();
                    tt = setInterval(updateYear, 100);
                    
                }else{                
                    var d = this.viewDate.clone();
                    var y = parseInt(e.target.innerHTML);
                    if(isNaN(y)) return;
                    d.setFullYear(y);
                    this._setViewDate(d);
                    remove = true;
                }
            }else{
                remove = true;
            }
            
            if(remove){                
                //dh.remove.defer(10, dh, [this.yearpop]);
                //this.yearpop = null;
                this.yearpop.style.display = 'none';
                                
                dh.un(doc, 'mousedown', onmousedown, this);
            }
            
        }        
        
        dh.on(doc, 'mousedown', onmousedown, this);
        
    },
    preMonth: function(e){
        this._setViewDate(this.viewDate.add(Date.MONTH, -1));
    },
    nextMonth: function(e){
        this._setViewDate(this.viewDate.add(Date.MONTH, 1));
    },
    preYear: function(e){
        this._setViewDate(this.viewDate.add(Date.YEAR, -1));
    },
    nextYear: function(e){
        this._setViewDate(this.viewDate.add(Date.YEAR, 1));
    },
    selectToday: function(e){        
        this._setDate(new Date());
        this.fireEvent('dateselection',{
            type: 'dateselection',
            source: this,
            date: this.date
        });
    },
    clearDate: function(){
        this.set('date', null);
    },
    _onMouseDown: function(e){
        if(e.within(this.leftYearEl)) this.preYear(e);
        else if(e.within(this.rightYearEl)) this.nextYear(e);
        else if(e.within(this.leftEl)) this.preMonth(e);
        else if(e.within(this.rightEl)) this.nextMonth(e);
        else if(e.within(this.monthEl)) this.monthClickHandler(e);
        else if(e.within(this.yearEl)) this.yearClickHandler(e);
        else if(e.within(this.daysCt)) this.daysCtClickHandler(e);
        
        e.stopDefault();
    },        
    _onBeforeTableCellSelectionChange: function(e){
    
        var cell = e.value;
        if(cell){
            var d = cell.date;
            
            if(!this.date || (d.format(this.format) != this.date.format(this.format))){                
                if(this.autoSelectDate){
                    this._setDate(d);
                    this.fireEvent('dateselection',{
                        type: 'dateselection',
                        source: this,
                        date: this.date
                    });
                }
                return false;
            }
           
        }        
    },
    init: function(){
    
        //trace("datepicker init!!!");
        var year = this.viewDate.format(this.yearFormat), month = this.viewDate.format(this.monthFormat);
        
        //创建内置的对象
        this.setChildren([
            {                
                type: 'div',width: '100%',height: this.headerHeight,
                html: '<table class="e-datepicker-header" border="0" cellpadding="0" cellspacing="0" ><tr><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-year-left"></a></td><td  class="e-datepicker-year">'+year+'</td><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-year-right"></a></td><td ></td><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-left"></a></td><td  class="e-datepicker-month">'+month+'</td><td width="10%" style="text-align:center;"><a href="javascript:;" hidefocus class="e-datepicker-right"></a></td></tr></table>'
            },
            {                                
                type: 'table',enableCellSelect: true,enableSelect: false,cellPaddingLeft: 0,cellPaddingRight: 0,enableHoverRow: false,cls: 'e-datepicker-body',style: 'border:0',width: '100%',height: '100%',verticalScrollPolicy: 'off',horizontalScrollPolicy: 'off',minHeight: 134,verticalLine: false,horizontalLine: false,autoColumns: true,enableDragDrop: false,
                columns: this.createDateColumns(),                
                onbeforecellselectionchange: this._onBeforeTableCellSelectionChange.bind(this)
            },
            {
                layout: 'horizontal',type: 'box',cls: 'e-datepicker-foot',border:[1,0,0,0],width: '100%',height: this.footerHeight,verticalAlign: 'middle',horizontalAlign: 'center',padding: 0,
                children: [
                    {
                        type: 'button',width: 50,height:22,text: this.todayText,
                        onclick: this.selectToday.bind(this)
                    },
                    {type: 'space', width: '10%', visible: !this.required},
                    {
                        type: 'button',visible: !this.required,width: 50,height:22,text: this.clearText,
                        onclick: this.clearDate.bind(this)
                    }
                ]
            }
        ]);
        this.headerCt = this.getChildAt(0);
        this.table = this.getChildAt(1);
        this.footCt = this.getChildAt(2);
        
        this.headerCt.set('visible', this.headerVisible);
        this.footCt.set('visible', this.footerVisible);
        
        this.table.owner = this;
        
        this.on('click', this._onMouseDown, this);        
        
        this.table.set('rowHeight', this.getDateHeight());
        this.table.set('data', this.createViewData());
        
        Edo.controls.DatePicker.superclass.init.c(this);        
    },
    createDateColumns: function(){
        var columns = [];
        
        for(var i = this.weekStartDay, dataIndex = 0, l=this.weekStartDay+7; i<l; i++, dataIndex++){
            var week = i >= 7 ? i-7 : i;
            columns.add({
                header: this.weeks[week],
                enableMove: false,
                enableSort: false,
                enableResize: false,
                headerAlign: 'center',
                dataIndex: dataIndex,
                //width: '100%',
                renderer: this.dateRenderer.bind(this)
            });
        }
        return columns;
    },
    //根据viewdate的year,month,生成一个7列6行的dataTable数据,用于表格生成
    createViewData: function(){
        var data = [];
        
        var date = this.viewDate.add(Date.DAY, -this.viewDate.getDate()+1); //获得本月第一天        
        date = date.add(Date.DAY, -date.getDay() + this.weekStartDay);  //获得当前周的第一天
        
        //      
        
        var h = this.getDateHeight();
        
        for(var j=0; j<5; j++){
            var record = [];
            //record.__height =  h;
            for(var i = this.weekStartDay,l=this.weekStartDay+7; i<l; i++){
                record.add({
                    viewdate: this.viewDate,
                    date: date
                });
                
                if(j==0 && record.length==1) this.startDate = date.clone();
                if(j==4 && record.length==7) this.finishDate = date.clone();
                
                date = date.add(Date.DAY, 1);                                
            }
            data.add(record);
        }
        
        //this.viewData = data;
        
        return data;
    },
    _setWeekStartDay: function(value){
        this.weekStartDay = value;
    },
    _setViewDate: function(value){
        if(typeof value == 'string') {
            value = Date.parseDate(value, this.format);
        }
        if(!Edo.isDate(value)) return;
        if(this.viewDate != value){
            this.viewDate = value;
            if(this.inited){
                this.table.set('data', this.createViewData());
                //this.refresh();   //在syncSize的时候会自动调用,所以注释掉
            }
        }
    },    
    _setDate: function(value){    
        if(typeof value == 'string') {
            value = Date.parseDate(value, this.format);
        }            
        if(!Edo.isDate(value)) {
            value = null;
            if(this.required) return false;
        }
        if(this.date != value){
        
            if(this.fireEvent('beforedatechange', {
                type: 'beforedatechange',
                source: this,
                date: value
            }) == false) return;
            
            this.date = value;
            
            this._setViewDate.defer(100, this, [this.date ? this.date.clone() : new Date()]);
                        
            this.changeProperty('date', value);
            this.fireEvent('datechange', {
                type: 'datechange',
                source: this,
                date: value
            });
        }
    },    
    refresh: function(){       
        var year = this.viewDate.format(this.yearFormat), month = this.viewDate.format(this.monthFormat);
        
        if(this.el){
            this.yearEl.innerHTML = year;
            this.monthEl.innerHTML = month;
        }
        
        var rowHeight = this.getDateHeight();
        this.table.set('rowHeight', rowHeight);

        this.table.deferRefresh();
        
        this.viewSelectDate(true);        
        
    },
    viewSelectDate: function(select){        
        var row = col = -1;
        var view = this.table.data.view;//this.createViewData();
        var dateFormat = this.date ? this.date.format('Y-m-d') : null;
        for(var i=0, l=view.length; i<l; i++){
            var r = view[i];
            for(var j=0; j<7; j++){
                if(r[j].date.format('Y-m-d') == dateFormat){
                    row = i;
                    col = j;
                    break;
                }
            }
        }        
        if(row != -1 && col != -1){
            if(select !== false){
                this.table.selectCell(row, col)
            }else{
                this.table.deselectCell(row, col)
            }
        }
        
        
    },
    syncSize: function(){
        Edo.controls.DatePicker.superclass.syncSize.call(this);                
       
        
        this.refresh();
    },
    getDateHeight: function(){
        var h = this.table ? (this.realHeight - (this.footerVisible ? this.footerHeight : 0) - (this.headerVisible ? 28 : 0) - this.table.getHeaderHeight()) / 5 : 23;
        
        return h;
    },
    render: function(p){        
        Edo.controls.DatePicker.superclass.render.call(this, p);        
        
        var hel = this.headerCt.el.firstChild; //!!!
        
        var tds = hel.rows[0].cells;
        this.leftEl = Edo.util.Dom.getbyClass('e-datepicker-left', hel);
        this.rightEl = Edo.util.Dom.getbyClass('e-datepicker-right', hel);
        
        this.monthEl = Edo.util.Dom.getbyClass('e-datepicker-month', hel);
        this.yearEl = Edo.util.Dom.getbyClass('e-datepicker-year', hel);
        
        this.leftYearEl = Edo.util.Dom.getbyClass('e-datepicker-year-left', hel);
        this.rightYearEl = Edo.util.Dom.getbyClass('e-datepicker-year-right', hel);
                
        
//        this.table.set('data', this.createViewData());
//        this.viewSelectDate(true);
    }
    
});    

Edo.controls.DatePicker.regType('datepicker');