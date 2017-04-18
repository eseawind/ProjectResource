<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="leejean">
<script type="text/javascript">
var pathName=window.document.location.pathname;
//获取带"/"的项目名，如：/Tmall
$.basePath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
</script>