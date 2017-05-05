<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(document).ready(function () {
        //后台组织数据
        $("#pre1").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/queryData");
        })
		//已有数据源
        $("#pre2").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreportHasSql");
        })

		//导出Excel
        $("#pre3").bind("click",function(e){
       	    var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			parent.window.open("${baseURL}/ireport/exportFile?exportFileSuffix=XLS&exportFileName=导出用户excle报表","报表导出","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
			//没有弹框
			//window.location.href="${baseURL}/ireport/exportExcel";
        })
		//导出PDF
        $("#pre4").bind("click",function(e){
        	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			parent.window.open("${baseURL}/ireport/exportFile?exportFileSuffix=PDF&exportFileName=导出用户PDF报表","报表导出","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
			//没有弹框
			//window.location.href="${baseURL}/ireport/exportExcel";
        })

		//导出DOC
        $("#pre5").bind("click",function(e){
        	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			parent.window.open("${baseURL}/ireport/exportFile?exportFileSuffix=DOC&exportFileName=导出用户PDF报表","报表导出","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
			//没有弹框
			//window.location.href="${baseURL}/ireport/exportExcel";
        })
		//导出DOC
        $("#pre6").bind("click",function(e){
        	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			parent.window.open("${baseURL}/ireport/exportFile?exportFileSuffix=HTML&exportFileName=导出用户HTML报表","报表导出","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
			//没有弹框
			//window.location.href="${baseURL}/ireport/exportExcel";
        })
    });

</script>
<div class="panel panel-info">
    <div class="panel-heading">
        <button class="btn bg-warning" id="pre1">后台添加数据源</button>
        <button class="btn bg-danger" id="pre2">已有数据源</button>
        <button class="btn bg-info" id="pre3">导出Excel</button>
        <button class="btn bg-info" id="pre4">导出PDF</button>
        <button class="btn bg-info" id="pre5">导出DOC</button>
        <button class="btn bg-info" id="pre6">导出HTML</button>
    </div>
    <div class="panel-body bg-warning"  style="height: 500px">
        <iframe id="iframeDIV" src="${baseURL}/ireport/queryData" style="border: none;height: 100%;width: 100%">

        </iframe>
    </div>
    <div class="panel-footer">
        <button id="createQrCodeBtn" type="button" class="btn btn-success">确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
        <button id="clearTextBtn" type="button" class="btn btn-warning">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
    </div>
</div>