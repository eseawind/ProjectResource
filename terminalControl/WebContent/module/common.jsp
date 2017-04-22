<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<script type="text/javascript" src="${resRoot}/jquery.min.js"></script>
<script type="text/javascript" src="${resRoot}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${resRoot}/bootstrap-3.3.7-dist/css/bootstrap.css" />
<link rel="stylesheet" href="${resRoot}/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
<!--
      	作者：hothoot@163.com
      	时间：2017-04-17
      	描述：提示框
-->
<div id="alertDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
      				<h4 class="modal-title" id="exampleModalLabel">消息提示</h4>
    				</div>
    				<div class="modal-body text-center text-info" id="alertDialogText">
    				</div>
    				<div class="modal-footer">
		        		<button type="button" class="btn btn-warning" data-dismiss="modal">关闭</button>
    				</div>
		</div>
	</div>
</div>