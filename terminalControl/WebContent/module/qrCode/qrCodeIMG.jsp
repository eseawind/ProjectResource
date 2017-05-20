<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="JavaScript">

</script>
<c:forEach var="qrCode" items="${qrCodeList}">
	<div class="col-sm-3 col-md-3>
		<div class="center-block  text-center" >
			<div class="panel panel-warning " style="margin-bottom:0px;">
			  <div class="panel-body" id="${qrCode.remark}">
			  	<img src="${baseURL}/qrCode/genQRcodes?context=${qrCode.qrCode}" class="img-rounded img-responsive" >
			  </div>
			  <div class="panel-footer text-center bg-info inline-block text-overflow  ">
			  	<small>${qrCode.remark}</small>
			  </div>
			</div>
		</div>
	</div>
</c:forEach>
