<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<script type="text/javascript" src="${resRoot}/js/qrCode/genQrCodes.js"></script>
<div class="panel panel-info">
	<div class="panel-heading">批量生成二维码</div>
	<div class="panel-body bg-warning">
		<textarea id="qrCodetextarea" class="form-control" rows="26"placeholder="多个数据以,分割"></textarea>
	</div>
	<div class="panel-footer">
		<button id="createQrCodeBtn" type="button" class="btn btn-success">确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
		<button id="clearTextBtn" type="button" class="btn btn-warning">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
	</div>
</div>