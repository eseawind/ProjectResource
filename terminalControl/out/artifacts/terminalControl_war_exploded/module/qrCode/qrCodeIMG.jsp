<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${resRoot}/js/qrCode/utf.js"></script>
<script type="text/javascript" src="${resRoot}/js/qrCode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${resRoot}/js/qrCode/genQrCodes.js"></script>
<script language="JavaScript">
function initParams(genCodes){
	var qrCodes="${qrCodes}";
	return genCodes(qrCodes);
}
$(function(){
	initParams(genCodes);
});
</script>
<c:forEach var="qrCode" items="${qrCodeList}">
	<div class="col-sm-3 col-md-3>
		<div class="thumbnail">
			<div class="img-responsive center-block img-thumbnail text-center" >
				<div class="panel panel-warning " style="margin-bottom:0px;">
				  <div class="panel-body" id="${qrCode}"></div>
				  <mark>${qrCode}</mark>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
