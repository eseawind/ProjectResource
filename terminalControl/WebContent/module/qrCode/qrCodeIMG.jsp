<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${resRoot}/jquery.qrcode.min.js"></script>
<script language="JavaScript">
$(function() {
	var qrCodes="${qrCodes}";
	var qrCodeArry=qrCodes.split(",");
	var qrCode=null;
	for(var index=0;index < qrCodeArry.length;index++){
		qrCode=qrCodeArry[index];
		$("#"+qrCode).qrcode(qrCode);
	}
})
</script>
<c:forEach var="qrCode" items="${qrCodeList}">
	<div class="col-sm-4 col-md-4>
		<div class="thumbnail">
			<div class="img-responsive center-block img-thumbnail text-center" >
				<div class="panel panel-warning " style="margin-bottom: 0px;">
				  <div class="panel-body" id="${qrCode}"></div>
				  <mark>${qrCode}</mark>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
