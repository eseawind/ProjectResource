/**
 * @author 孙宇
 */

$.changeTheme = function(themeName) {/* 更换主题 */
	var prevPath = $.basePath+"/jslib/jquery-extensions-master/jquery-easyui-theme/";
	var nextPath = "/easyui.css";
	var $easyuiTheme = $('#easyuiTheme');
	var path = prevPath + themeName + nextPath;
	systemTheme = path;
	$easyuiTheme.attr("href",systemTheme);
	alert(systemTheme);
	$.cookie('easyuiThemeName', themeName, {
		expires : 30
	});
};