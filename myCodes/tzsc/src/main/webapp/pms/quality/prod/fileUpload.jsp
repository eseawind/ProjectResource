<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/plupload.full.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/i18n/zh-CN.js"></script> 
<script type="text/javascript">
	$(function() {
		$("#uploader").pluploadQueue({
			runtimes : 'html5,gears,flash,silverlight,browserplus,html4',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			flash_swf_url : '${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			silverlight_xap_url : '${pageContext.request.contextPath}/jslib/plupload_1_5_7/plupload/js/plupload.silverlight.xap',//silverlight环境路径设置
			url : '${pageContext.request.contextPath}/UploadServlet',//上传文件路径
			max_file_size : '3gb',//100b, 10kb, 10mb, 1gb
			chunk_size : '1mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			//multiple_queues : true,//是否可以多次上传
			// 如果可能的话，压缩图片大小
			// resize : { width : 320, height : 240, quality : 90 },
			// 指定要浏览的文件类型
			filters : [ {
				title : 'Image files',
				extensions : 'jpg,gif,png'
			}, {
				title : 'Zip files',
				extensions : 'zip,7z,rar'
			}, {
				title : 'Office files',
				/* extensions : 'doc,docx,excel,ppt,txt,mpp,pdf' */
				extensions : 'pdf,txt'
			}, {
				title : 'exe files',
				extensions : 'exe,bit'
			}  ],
			init : {
				FileUploaded : function(up, file, info) {//文件上传完毕触发
					console.info(up);
					console.info(file);
					console.info(info);
					var response = $.parseJSON(info.response);
					if (response.status) {
						$("#isUploadFile").attr("value",true);//给隐藏域 赋值 表示 有上传文件
						$('#form').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
						$('#form').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
						//alert("完毕");
					}
				}
			}
		});

		// 客户端表单验证 这个没有用到
		$('#form').submit(function(e) {
			var uploader = $('#uploader').pluploadQueue();
			if (uploader.files.length > 0) {// 判断队列中是否有文件需要上传
				uploader.bind('StateChanged', function() {// 在所有的文件上传完毕时，提交表单
					if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
						$('form')[0].submit();
					}
				});
				uploader.start();
			} else {
				alert('请选择至少一个文件进行上传！');
			}
			return false;
		});

	});
</script>
	<div style="height:375px;width:604px;overflow:hidden;"><!-- border:1px solid red; -->
		<form id="form" action="" method="post">
		<table width="100%">
			<tr>
				<td colspan="4">
					<div id="uploader">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
				</td>
			</tr>
			<tr>
				<td><input type="hidden" id="isUploadFile" name="isUploadFile" value="false"/></td>
				<td>
					请设置文件的安全级别： 
					<input name="securityLevel" class="easyui-numberspinner"
     					required="required" 
     					value="10"
     					data-options="min:1,max:10,width:150"/>
				 </td>
				 <td></td>
				 <td>版本号:
				 <input name="prodVersion" maxlength="50" value="1.0"
					onkeyup="if(isNaN(value))execCommand('undo')"
					onafterpaste="if(isNaN(value))execCommand('undo')" /> 
				</td>
			</tr>
		</table>
	</form>
	</div>
	