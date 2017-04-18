/*
    load.js 
    
    作用:
        1.  不需要将js和css打包成一个文件, 就可以引用到页面进行调试
            比如修改某个js文件, 可以直接引用到页面, 不需要合并成一个文件再引用
            比如给某个js文件设置debugger断点, 可以直接调试
            
        2.  发布时用于打包js
        
    使用: 
        load.js 如何使用, 请参考debug.html文件
*/
var scripts = [
    'public/managers/SystemManager.js',
    'public/managers/DragManager.js',    
    'public/managers/PopupManager.js',
    'public/managers/ResizeManager.js',
    'public/managers/TipManager.js',
        
    'public/data/DataTable.js',
    'public/data/DataTree.js',
    'public/data/DataForm.js',
    'public/data/Convert.js', 
    'public/data/Connection.js',
            
    'public/core/Validator.js',    
    'public/core/Space.js',
    'public/core/Split.js',
    'public/core/HSplit.js',
    'public/core/MessageBox.js',
    'public/core/Module.js',    
    
    'public/containers/Box.js',    
    'public/containers/Group.js',    
    'public/containers/FieldSet.js',
    'public/containers/Panel.js',
    'public/containers/Form.js',
    'public/containers/FormItem.js',
    'public/containers/Application.js',
    'public/containers/Window.js',
    
    'public/navigators/Navigator.js',
    'public/navigators/Menu.js',
    'public/navigators/ToggleBar.js',
    'public/navigators/TabBar.js',
    'public/navigators/VTabBar.js',
    'public/navigators/PagingBar.js',    
    
    'public/controls/Button.js',    
    'public/controls/Error.js',
    'public/controls/Slider.js',    
    'public/controls/VSlider.js',    
    'public/controls/ScrollBar.js',
    'public/controls/VScrollBar.js',    
    'public/controls/CheckBox.js',
    'public/controls/Radio.js',    
    'public/controls/TextInput.js',
    'public/controls/Password.js',
    'public/controls/TextArea.js',
    'public/controls/Trigger.js',
    'public/controls/Search.js',
    'public/controls/ComboBox.js',
    'public/controls/Date.js',
    'public/controls/DatePicker.js',
    'public/controls/Label.js',
    'public/controls/Spinner.js',
    'public/controls/DateSpinner.js',
    'public/controls/TimeSpinner.js',
    'public/controls/HtmlEditor.js',
    'public/controls/CodeEditor.js',    
    'public/controls/Progress.js',       
    'public/controls/MultiSelect.js',        
    'public/controls/DataView.js',
    'public/controls/CheckGroup.js',
    'public/controls/RadioGroup.js',    
    'public/controls/SWF.js',
    'public/controls/FileUpload.js',    
    'public/controls/PercentSpinner.js',  
    'public/controls/MultiCombo.js',
    'public/controls/TreeSelect.js',
    'public/controls/DurationSpinner.js',
    'public/controls/AutoComplete.js',
    'public/controls/Lov.js',
    'public/controls/SuperSelect.js'    
    
];

var rootPath = 'scripts/edo/';

document.write('<link href="'+rootPath+'res/css/base.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+rootPath+'res/css/core.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+rootPath+'res/css/skin.css" rel="stylesheet" type="text/css" />');

document.write('<link href="/res/product/project/css/project.css" rel="stylesheet" type="text/css" />');

for(var i=0,l=scripts.length; i<l; i++){
    document.write('<script src="'+rootPath+scripts[i]+'" type="text/javascript"></script>');
}