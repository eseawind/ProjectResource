<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(document).ready(function () {
        //后台组织数据
        $("#pre1").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreport");
        })
//已有数据源
        $("#pre2").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreportHasSql");
        })

//导出Excel
        $("#pre3").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreport");
        })
//导出PDF
        $("#pre4").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreport");
        })

//导出DOC
        $("#pre5").bind("click",function(e){
            $("#iframeDIV").attr("src","${baseURL}/ireport/testIreport");
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
    </div>
    <div class="panel-body bg-warning"  style="height: 500px">
        <iframe id="iframeDIV" src="${baseURL}/ireport/testIreport" style="border: none;height: 100%;width: 100%">

        </iframe>
    </div>
    <div class="panel-footer">
        <button id="createQrCodeBtn" type="button" class="btn btn-success">确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
        <button id="clearTextBtn" type="button" class="btn btn-warning">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
    </div>
</div>