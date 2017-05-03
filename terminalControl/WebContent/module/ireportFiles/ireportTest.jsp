<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-info">
    <div class="panel-heading">
        <button class="btn bg-warning">预览</button>
    </div>
    <div class="panel-body bg-warning" style="height: 500px">
        <iframe src="${baseURL}/ireport/testIreport" style="border: none;height: 100%;width: 100%">

        </iframe>
    </div>
    <div class="panel-footer">
        <button id="createQrCodeBtn" type="button" class="btn btn-success">确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
        <button id="clearTextBtn" type="button" class="btn btn-warning">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
    </div>
</div>