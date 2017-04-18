<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/js/lib/modernizr.js" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/css/lib/bootstrap-yeti.min.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/css/annotator-pro-editor.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/css/annotator-pro.min.css" type="text/css"></link>
<script>
	function onjqueryClick(obj){
		//alert(obj.id); 
	}
</script>
</head>
<body>
    <div id="wrap">
        <div class="container-fluid">
           
            <form role="form" class="form">
            <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-lg btn-default active">
                    <input type="radio" name="radio-editor-mode" id="radio-editor-mode-edit" checked>编辑
                </label>
                <label class="btn btn-lg btn-default" id="radio-editor-mode-preview-label">
                    <input type="radio" name="radio-editor-mode" id="radio-editor-mode-preview">预览
                </label>
                <label class="btn btn-lg btn-default" id="radio-editor-mode-jquery-label">
                    <input type="radio" name="radio-editor-mode" id="radio-editor-mode-jquery">jQuery代码
                </label>
                <label class="btn btn-lg btn-default" id="radio-editor-mode-load-label">
                    <input type="radio" name="radio-editor-mode" id="radio-editor-mode-load">测试代码正确性
                </label> 
                
               <label class="btn btn-lg btn-default" id="radio-editor-mode-save-label">
                   <input type="radio" name="radio-editor-mode">保存
                </label>
                 <input type="hidden" id="twoRowId" value="${mdEqpTypeBean.twoRowId}"/>  
                 <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/> 
                 <input type="hidden" id="filePath" value="${mdEqpTypeBean.uploadUrl}"/> 
                 <input type="hidden" id="contextDes" value="${mdEqpTypeBean.contextDes}"/> 
            </div>
            
            </form>
            <div class="panel panel-default" id="panel-editor">
                <div class="panel-body" id="panel-canvas">
                    <div class="ndd-drawable-canvas">
                        <img src="${pageContext.request.contextPath }${mdEqpTypeBean.uploadUrl}" class="ndd-drawable-canvas-image">
                        <div class="ndd-drawables-container">
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" id="panel-preview">
                <div class="panel-body" id="plugin-container" style="width: 100%; height: 600px;">
                </div>
            </div>
            <div class="panel panel-default" id="panel-jquery">
                <div class="panel-body">
                    <label class="btn btn-primary" id="button-select-jquery">
                        全选</label><br>
                    <br>
                    <div class="well" id="well-jquery">
                    </div>
                </div>
            </div>
            <div class="panel panel-default" id="panel-load">
                <div class="panel-body">
                    <textarea class="form-control" rows="10" id="textarea-load"></textarea><br>
                    <label class="btn btn-lg btn-primary" id="button-load" disabled>
                        验证</label>
                </div>
            </div>
            <h2>
                设置</h2>
            <div class="row">
                <!-- Popups -->
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div id="panel-disabler">
                        </div>
                        <div class="panel-heading">
                            注释</div>
                        <div class="panel-body">
                            <form role="form" class="form-horizontal">
                            <div class="col-md-6 col-sm-6">
                                <!-- Content Type -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        内容类别</label>
                                    <div class="col-md-9">
                                        <div class="btn-group" data-toggle="buttons">
                                            <label class="btn btn-default active">
                                                <input type="radio" name="radio-content-type" id="radio-content-type-text" checked>文本
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-content-type" id="radio-content-type-custom-html">自定义HTML
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Title -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        标题</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="input-title" placeholder="Enter title">
                                    </div>
                                </div>
                                <!-- Text -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        文本</label>
                                    <div class="col-md-9">
                                        <textarea class="form-control" rows="3" id="textarea-text"></textarea>
                                    </div>
                                </div>
                                <!-- Text Color -->
                                <div class="form-group">
                                    <label for="color-text-color" class="col-md-3 control-label">
                                        文本颜色</label>
                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <input type="color" class="form-control" id="color-text-color" value="#ffffff">
                                            <span class="input-group-addon" id="color-text-color-hex">#fff</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- HTML -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        HTML</label>
                                    <div class="col-md-9">
                                        <textarea class="form-control" rows="3" id="textarea-html"></textarea>
                                    </div>
                                </div>
                                <!-- ID -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        ID</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="input-id" placeholder="Enter ID">
                                        <p class="help-block">
                                           用于深度链接</p>
                                    </div>
                                </div>
                                <!-- Deep linking URL -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        深度链接URL</label>
                                    <div class="col-md-9">
                                        <div id="input-deep-link-url" class="well"></div>
                                        <p id="input-deep-link-url-help" class="help-block">
                                        </p>
                                    </div>
                                </div>
                                <!-- Delete -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        删除</label>
                                    <div class="col-md-9">
                                        <label class="btn btn-danger" data-toggle="modal" data-target="#modal-delete">
                                            删除注释</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <!-- Tint Color -->
                                <div class="form-group">
                                    <label for="color-tint-color" class="col-md-3 control-label">
                                        提示框颜色</label>
                                    <div class="col-md-9">
                                        <div class="input-group">
                                            <input type="color" class="form-control" id="color-tint-color" value="#000000">
                                            <span class="input-group-addon" id="color-tint-color-hex">#000</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- Style -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        风格</label>
                                    <div class="col-md-9">
                                        <div class="btn-group btn-group-no-margin" data-toggle="buttons" id="btn-group-style-circle">
                                            <label class="btn btn-default active">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-style-1" checked><div
                                                    class="icon-in-label ndd-spot-icon icon-style-1">
                                                    <div class="ndd-icon-main-element">
                                                    </div>
                                                    <div class="ndd-icon-border-element">
                                                    </div>
                                                </div>
                                            </label>

                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-style-2"><div class="icon-in-label ndd-spot-icon icon-style-2">
                                                    <div class="ndd-icon-main-element">
                                                    </div>
                                                    <div class="ndd-icon-border-element">
                                                    </div>
                                                </div>
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-style-3"><div class="icon-in-label ndd-spot-icon icon-style-3">
                                                    <div class="ndd-icon-main-element">
                                                    </div>
                                                    <div class="ndd-icon-border-element">
                                                    </div>
                                                </div>
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-style-4"><div class="icon-in-label ndd-spot-icon icon-style-4">
                                                    <div class="ndd-icon-main-element">
                                                    </div>
                                                    <div class="ndd-icon-border-element">
                                                    </div>
                                                </div>
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-style-0"><div style="height: 44px;
                                                    line-height: 44px;">
                                                    隐藏</div>
                                            </label>
                                        </div>
                                       
                                    </div>
                                </div>
                                <!-- Popup Width -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        提示框宽度</label>
                                    <div class="col-md-7">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="input-popup-width">
                                            <span class="input-group-addon" id="input-popup-width-addon">px</span>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" id="checkbox-popup-width-auto" checked>Auto
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Popup Height -->
                                <div class="form-group">
                                    <label class="col-md-3 control-label">
                                        提示框高度</label>
                                    <div class="col-md-7">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="input-popup-height">
                                            <span class="input-group-addon" id="input-popup-height-addon">px</span>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" id="checkbox-popup-height-auto" checked>Auto
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Popup Position -->
                                <div class="form-group">
                                    <label for="radio-popup-position" class="col-md-3 control-label">
                                        提示框位置</label>
                                    <div class="col-md-9">
                                        <div class="btn-group" data-toggle="buttons">
                                            <label class="btn btn-default active">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-position-top" checked>上面
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-position-bottom">下面
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-position-left">左面
                                            </label>
                                            <label class="btn btn-default">
                                                <input type="radio" name="radio-popup-position" id="radio-popup-position-right">右面
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- Navigation -->
                <div class="col-md-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            导航</div>
                        <div class="panel-body">
                            <form role="form" class="form-horizontal">
                            <!-- Width -->
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    宽</label>
                                <div class="col-md-7">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="input-width">
                                        <span class="input-group-addon" id="input-width-addon">px</span>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="checkbox-width-auto" checked>Auto
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <!-- Height -->
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                    高</label>
                                <div class="col-md-9">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="input-height">
                                        <span class="input-group-addon" id="input-height-addon">px</span>
                                    </div>
                                </div>
                            </div>
                            <!-- Max Zoom -->
                            <div class="form-group">
                                <label class="col-md-3 control-label">
                                   放大</label>
                                <div class="col-md-9">
                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="btn btn-default active">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-1-1" checked>1:1
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-1">1x
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-2">2x
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-3">3x
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-4">4x
                                        </label>
                                        <label class="btn btn-default">
                                            <input type="radio" name="radio-max-zoom" id="radio-max-zoom-custom">自定义
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-3 col-md-9">
                                    <input type="text" class="form-control" id="input-max-zoom" value="4">
                                </div>
                            </div>
                            <!-- Show Navigator -->
                            <div class="form-group">
                                <label for="checkbox-navigator" class="col-md-3 control-label">
                                    导航</label>
                                <div class="col-md-9">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="checkbox-navigator">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <!-- Show Navigator Image Preview -->
                            <div class="form-group">
                                <label for="checkbox-navigator-image-preview" class="col-md-3 control-label">
                                   导航图片预览</label>
                                <div class="col-md-9">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="checkbox-navigator-image-preview">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <!-- Enable Fullscreen -->
                            <div class="form-group">
                                <label for="checkbox-fullscreen" class="col-md-3 control-label">
                                    宽屏</label>
                                <div class="col-md-9">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="checkbox-fullscreen">
                                        </label>
                                    </div>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modals -->
    <!-- Modal -->
    <div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">
                        确定吗?</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        关闭</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" id="delete-annotation-button">
                        删除</button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/js/lib/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/js/lib/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/js/annotator-pro-editor.js"></script>

    <script src="${pageContext.request.contextPath}/pms/sys/datadict/imagesHotspot/js/annotator-pro.min.js"></script>

</body>
</html>
