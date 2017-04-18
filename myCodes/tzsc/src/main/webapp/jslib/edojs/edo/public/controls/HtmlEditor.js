/**
    @name Edo.controls.HtmlEditor
    @class
    @typeName htmleditor
    @description Html富文本编辑器
    @extend Edo.controls.Control
*/
Edo.controls.HtmlEditor = function(){
    Edo.controls.HtmlEditor.superclass.constructor.call(this);
}
Edo.controls.HtmlEditor.extend(Edo.controls.TextArea,{
    minWidth: 120,
    minHeight: 50,
    elCls: 'e-htmleditor e-div  e-text',
    
    editConfig: null,
    createChildren: function(el){        
        Edo.controls.HtmlEditor.superclass.createChildren.call(this, el);        
        this.editor = CKEDITOR.replace(this.field,
            Edo.apply({    
                skin: "v2", height:this.realHeight-52,
                toolbar: 'Basic',
                toolbarCanCollapse: false,
                resize_enabled:false,
                toolbar_Basic: [['Font', 'Bold', 'Italic','Underline','-','Subscript','Superscript','-','TextColor','BGColor', '-', 'JustifyLeft','JustifyCenter','JustifyRight','-','Maximize','Source' ]]
            },this.editConfig));             
        
    },
    syncSize: function(){
        Edo.controls.HtmlEditor.superclass.syncSize.a(this, arguments);
        
//        this.editor.wrapping.style.width = (this.realWidth-1)+'px';
//        this.editor.wrapping.style.height = (this.realHeight-1)+"px";
        
        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    },
    _setText: function(text){
        this.text = text;
        if(this.editor) this.editor.setData(text);
    },
    _getText: function(text){
        if(this.editor) this.text = this.editor.getData();        
        return this.text;
    }
});
Edo.controls.HtmlEditor.regType('htmleditor');