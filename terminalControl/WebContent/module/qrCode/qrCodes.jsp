<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="style/jquery.min.js" ></script>
        <script type="text/javascript" src="style/qrcode.min.js" ></script>
        <script>
        	
        	 var qrcode = new QRCode(document.getElementById("qrcode"), {
           
        });
        function showQRCode(qrCodeText){
			qrcode.makeCode(qrCodeText);
			$("#tab").hide();
            $("#reSet").show();
		}
        </script>
    </head>
    <body>
 	</body>
</html>