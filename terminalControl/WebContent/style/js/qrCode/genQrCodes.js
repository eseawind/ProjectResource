$(document).ready(function() {
	//生产二维码图片
	 genCodes=function(qrCodes){
		var qrCodeArry=qrCodes.split(",");
		var qrCode=null;
		var imgArry=[1,2,3];
		var n1=1;
		var imgIndex=0;
		for(var index=0;index < qrCodeArry.length;index++){
			n1=Math.floor(Math.random()*10/3);
			imgIndex=$.inArray(n1,imgArry);
			if(imgIndex==-1){
				n1=1;
			}
			qrCode=qrCodeArry[index];
			$("#"+qrCode).qrcode({
				render : "canvas",
				width: 150, //宽度 
			    height:150,
			    text:qrCode,
			    background : "#ffffff",//二维码的后景色
			    foreground : "#000000",//二维码的前景色
			    src: RESROOT+'/js/qrCode/'+n1+'.jpg'//二维码中间的图片
			});
		}
	};
	//将参数传递到后台
	$("#createQrCodeBtn").bind("click", function() {
		var qrcodes = $("#qrCodetextarea").val();
		if($.trim(qrcodes) == "") {
			$('#alertDialog').modal('show');
			$("#alertDialogText").html("二维码内容不能为空！");
			return;
		}
		var params=encodeURI(qrcodes);
		$.ajax({
			   type: "POST",
			   url: BASEURL+"/qrCode/batchGenQrcode",
			   data: "qrCodes="+params,
			   dataType:"html",
			   success: function(data){
			    	 $("#mainDiv").html(data);
			   }
			});
	});
	//清空填写二维码数据的文本域
	$("#clearTextBtn").bind("click", function() {
		$("#qrCodetextarea").val(null);
	});
});