/*
    封装代码编辑器
*/

Edo.controls.CodeEditor = function(){

    Edo.controls.CodeEditor.superclass.constructor.call(this);
};
Edo.controls.CodeEditor.extend(Edo.controls.TextArea,{
    minWidth: 120,
    minHeight: 50,
    elCls: 'e-codeeditor e-div  e-text',
    path: "/",
    createChildren: function(el){
        Edo.controls.CodeEditor.superclass.createChildren.call(this, el);        
        
        var w = isNaN(this.realWidth) ? this.defaultWidth : this.realWidth-1;
        var h = isNaN(this.realHeight) ? this.defaultHeight : this.realHeight-1; 
        
               
        this.editor = CodeMirror.fromTextArea(this.field, {
            width: w+'px',
            height: h+"px",
            parserfile: ["tokenizejavascript.js", "parsejavascript.js"],
            stylesheet: [this.path+"css/jscolors.css"],
            path: this.path+'js/'
            //,lineNumbers: true
        });
        
    },
    
    syncSize: function(){
        Edo.controls.CodeEditor.superclass.syncSize.a(this, arguments);
        
        this.editor.wrapping.style.width = (this.realWidth-1)+'px';
        this.editor.wrapping.style.height = (this.realHeight-1)+"px";
        
        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    },
    _setText: function(text){
        this.text = text;
        if(this.editor) {
            try{
                this.editor.setCode(text);
            }catch(e){
                this._setText.defer(100, this,[text]);
            }
        }
    },
    _getText: function(text){
        if(this.editor) this.text = this.editor.getCode();        
        return this.text;
    }
});
Edo.controls.CodeEditor.regType('codeeditor');
