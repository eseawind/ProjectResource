<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自检记录查询</title>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script><!--<script src="jquery/jquery-1.11.0.min.js"></script>-->

 <script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extBrowser.js" charset="utf-8"  type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/icons/icon-all.css" rel="stylesheet" type="text/css" />


<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/changeEasyuiTheme.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery.jdirk.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extEasyUI.js"></script>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extJquery.js"></script>
<script src="${pageContext.request.contextPath}/wct/js/qmPublic.js" type="text/javascript" ></script> 
<style type="text/css">
td{ font-size:12px;height:20px;width:40px}
input{font-size:12px;height:20px;width:100%}
p{text-align:left}
table tr{ text-align:center;};
</style>
<script type="text/javascript" >
var bandParams;
var showTab;
var params;
var changeClick;
var saveUpdate;
var calcu;
//及时包装机计算产量修改信息
/* function calcu(tr){
	alert(1);
	var diff;
	var q1;
	var q2;
	tr.find('td').each(function(index){
		if(index==3){
			diff=$(this).find('input').val();
			if($.isNumeric(diff)){
			}else {
				return;
			}
		}else if(index==4){
			q1=$(this).text();
		}else if(index==5){
			q2=$(this).text();
		}
	});
	
	tr.find('td').each(function(index){
		if(index==3){
			if($.isNumeric(diff)){
				$(this).find('input').val(diff);
				diff=parseFloat(diff);
				q1=parseFloat(q1);
				q2=parseFloat(q2);
			}else {
				return;
			}
		}else if(index==4){
			$(this).text(q1+diff);
		}else if(index==5){
			q2=$(this).text((q1+diff)/250);
		}
	}); 
	
}*/
calcu=function(v){
	
}
$(function(){
	//卷烟机产量打开编辑
	$("table[name='rollerTab'] tr").bind('click',function(){
		var tr=$(this);
		var indexTr=tr.index();
		if(indexTr<5){
			return;
		}
		if(tr.attr("edit")){
			return;
		}else{
			tr.attr("edit",true);
		}
		tr.find("td").each(function(index){
			var oldText=$(this).text();//原来的值   
			if(index==2){
				var se2="<input style='background-color: orange;' value='"+oldText+"''/>";
                $(this).html(se2);
                $(this).find('input').focus();
			}else if(index==4){
				var se4="<input style='background-color: orange;' value='"+oldText+"''/>";
                $(this).html(se4);
			}
		});
	});
	//包装机产量打开编辑
	$("table[name='packTab'] tr").bind('click',function(){
		var tr=$(this);
		var indexTr=tr.index();
		if(indexTr<2){
			return;
		}
		if(tr.attr("edit")){
			return;
		}else{
			tr.attr("edit",true);
		}
		tr.find("td").each(function(index){
			var td=$(this);
			var oldText=td.text();//原来的值   
			if(index==3){
				var se3="<input  style='background-color: orange;' value='"+oldText+"''/>";
                td.html(se3);
                var iup=td.find('input');
                iup.focus();
			}
		});
		
	});
	saveUpdate=function (){
		//卷烟机产量修改数据
		var rollerData = '[';
		 $("table[name='rollerTab'] tr").each(function(idx) {
			 var tr = $(this);
			 if(tr.attr("edit")){
				 td1 = tr.find("td:eq(1)").text();
				 if(td1!=0&&td1!=""){
					 td2 = tr.find("td:eq(2) input").val();
					 td3 = tr.find("td:eq(4) input").val();
					 rollerData += '{"oid":"'+td1//工单id
						+ '","rollerQty":"'+ td2//产量（千支）
						+ '","szyQty":"'+ td3//散支烟
						+ '"}';
						rollerData += ',';
				 }
			 }
		 });
		 if(rollerData!="["){
			 rollerData = rollerData.substring(0,(rollerData.length-1));
			}else{
			}
		    rollerData += ']';
		 
		 //包装机产量修改数据
		    var packerData = '[';
			 $("table[name='packTab'] tr").each(function(idx) {
				 var tr = $(this);
				 if(tr.attr("edit")){
					 td1 = tr.find("td:eq(1)").text();
					 if(td1!=0&&td1!=""){
						 td2 = tr.find("td:eq(3) input").val();
						 packerData += '{"oid":"'+td1//工单id
							+ '","diff":"'+ td2//调整数
							+ '"}';
							packerData += ',';
					 }
				 }
			 });
			 if(packerData!="["){
				 packerData = packerData.substring(0,(packerData.length-1));
				}else{
				}
			 packerData += ']';
			 
			 if(packerData=="[]"&&rollerData=="[]"){
				parent.$.messager.alert('提示','请选择要保存的数据');   
				return false;
			 }
		//保存数据
		console.info(packerData);console.info(rollerData);
		 $.ajax({
	            type: "post",//使用get方法访问后台
	            dataType: "json",//返回json格式的数据
	            url: "${pageContext.request.contextPath}/pms/sch/statjbcj/editQty.do",//要访问的后台地址
	            data: "rollerArray=" + rollerData+"&packArray="+packerData+"&filterArray=",//要发送的数据
	            success : function(r) {
					if(r.success){
						parent.$.messager.show('提示','修改数据成功！'); 
						bandParams();//重新查询下
					}else{
						bandParams();
						parent.$.messager.show('提示','修改数据失败！'); 
					}
				}
	         });
		
		
	}
	
	
	//表格名,数据集合,开始行,开始列,显示数据集合的列数组集合
	showTab=function(tableName,list,stratRow,startLine,indexs){
		var t="#"+tableName+" tr";
		var i=1;
		if(list==null){
			return;
		}
		$(t).each(function(idx){
			if(i>stratRow){
				if(list.length>idx-stratRow){
					var tr=$(this);
					var revalue=list[i-1-stratRow];
					for(var index=0;index<indexs.length;index++){
						tr.find("td:eq("+(startLine+index)+")").html(revalue[indexs[index]]);//时间
					}
				}
			}	
			i++;
		});
		
	};
	//表格名称,开始行,开始列,总列数
	var clearTab=function(tableName,stratRow,startLine,countLine){
		var t="#"+tableName+" tr";
		var i=1;
		$(t).each(function(idx){
			if(stratRow<i){
				var tr=$(this);
				for(var index=startLine;index<countLine;index++){
					tr.find("td:eq("+index+")").text("");//清空
				}
			}	
			i++;
		});
	};
	//清空界面数据
	var clearParams=function(){
		clearTab("tab1",1,0,3);
		clearTab("tab2",1,0,3);
		clearTab("tab3",1,0,3);
		
		clearTab("tab4",1,0,2);
		clearTab("tab5",1,0,2);
		clearTab("tab6",1,0,2); 
		
		clearTab("tab7",5,1,5);
		clearTab("tab8",5,1,5); 
		clearTab("tab9",5,1,5); 
		
		clearTab("tab10",2,1,6); 
		clearTab("tab11",2,1,6); 
		clearTab("tab12",2,1,6); 
		
		clearTab("tab13",1,1,5);
		clearTab("tab14",1,1,5);
		clearTab("tab15",1,1,5);
		
	};
	//数据集合，转换前的值，缺陷数，单位(数组下标的值)
	bandParams=function(){
		$('tr').removeAttr("edit");//取消编辑状态
		$.post("${pageContext.request.contextPath}/pms/sch/statjbcj/getQtyData.do",params,function(bean){
			clearParams();
			if(bean==null){
				return;
			}
			if(bean.rSum!=null){
				$('#jyQty').html(bean.rSum);
			}else {
				$('#jyQty').html("");
			}
			if(bean.pSum!=null){
				$('#bzQty').html(bean.pSum);
			}else{
				$('#bzQty').html("");
			}
			//卷烟机
			
			if(bean.jrsumQty!=null){
				showTab("tab1",bean.jrsumQty,1,0,[0,1,2]);
			}
			if(bean.yrsumQty!=null){
				showTab("tab2",bean.yrsumQty,1,0,[0,1,2]);
			}
			if(bean.brsumQty!=null){
				showTab("tab3",bean.brsumQty,1,0,[0,1,2]);
			}
			
			
			
			//包装机
			if(bean.jpsumQty!=null){
				showTab("tab4",bean.jpsumQty,1,0,[0,1]);
			}
			if(bean.ypsumQty!=null){
				showTab("tab5",bean.ypsumQty,1,0,[0,1]);
			}
			if(bean.bpsumQty!=null){
				showTab("tab6",bean.bpsumQty,1,0,[0,1]);
			}
			
			//卷烟机产量（机台）
			if(bean.jrdata!=null){
				showTab("tab7",bean.jrdata,5,0,[0,1,2,3,4]);
			}
			if(bean.yrdata!=null){
				showTab("tab8",bean.yrdata,5,0,[0,1,2,3,4]);
			}
			if(bean.brdata!=null){
				showTab("tab9",bean.brdata,5,0,[0,1,2,3,4]);
			}
			//包装机
			if(bean.jpdata!=null){
				showTab("tab10",bean.jpdata,2,0,[0,1,2,3,4,5]);
			}
			if(bean.ypdata!=null){
				showTab("tab11",bean.ypdata,2,0,[0,1,2,3,4,5]);
			}
			if(bean.bpdata!=null){
				showTab("tab12",bean.bpdata,2,0,[0,1,2,3,4,5]);
			}
		},"JSON");
	};	
	
	
	
});
</script>
</head>

<body bgcolor="#FFFFFF">
<div align="center" style="height:500px;width:1000px;">
<table width="158%" border="1" bordercolor="#000000" style="border-collapse:collapse">
  <tr>
    <td colspan="27">产量综合计算</td>
  </tr>
  <tr>
    <td>包装产量</td>
    <td id='bzQty'>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="15">&nbsp;</td>
  </tr>
  <tr>
    <td>卷烟产量</td>
    <td id='jyQty'>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4">
    <table id='tab1' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
        <td>散支烟（箱）</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="4">
    <table id='tab2' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
        <td>散支烟（箱）</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="4">
   <table id='tab3' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
        <td>散支烟（箱）</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
	<table id='tab4' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
    <table id='tab5' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
   <table id='tab6' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td>牌号</td>
        <td>产量</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td colspan="4">
   <table id='tab7' name='rollerTab' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="5">机台号</td>
        <td style="display:none"></td>
        <td colspan="3">甲卷产量</td>
        </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td colspan="3">计数器</td>
        </tr>
      <tr>
        <td style="display:none"></td>
        <td>千支</td>
        <td>箱</td>
        <td>散支烟</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="4">
    <table id='tab8' name='rollerTab' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="5">机台号</td>
        <td style="display:none"></td>
        <td colspan="3">乙卷产量</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td colspan="3">计数器</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>千支</td>
        <td>箱</td>
        <td>散支烟</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="4">
    <table id='tab9' name='rollerTab' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="5">机台号</td>
        <td style="display:none"></td>
        <td colspan="3">丙卷产量</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td colspan="3">计数器</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>千支</td>
        <td>箱</td>
        <td>散支烟</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
    <table id='tab10' name="packTab" width="400" height="100%" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="2" style="height:98px">机台号</td>
        <td style="display:none"></td>
        <td colspan="4">甲包产量</td>
        </tr>
      
      <tr>
        <td style="display:none"></td>
        <td>条数</td>
        <td>调整数</td>
        <td>调整后</td>
        <td>实际产量</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
    <table id='tab11'  name="packTab" width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="2" style="height:98px">机台号</td>
        <td style="display:none"></td>
        <td colspan="4">乙包产量</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>条数</td>
        <td>调整数</td>
        <td>调整后</td>
        <td>实际产量</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td colspan="5">
    <table id='tab12' name="packTab" width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td rowspan="2" style="height:98px">机台号</td>
        <td style="display:none"></td>
        <td colspan="4">丙包产量</td>
      </tr>
      <tr>
        <td style="display:none"></td>
        <td>条数</td>
        <td>调整数</td>
        <td>调整后</td>
        <td>实际产量</td>
      </tr>
      <tr>
        <td>1#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>2#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>3#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>4#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>5#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>6#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>7#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>8#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>9#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>11#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>12#</td>
        <td style="display:none"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" rowspan="8">
  <table id='tab13' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td colspan="4">嘴棒数量交接（仅供计算使用）</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈德门</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈纯</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>红塔山</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>宏图</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>绿孔府</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
    </table></td>
    <td colspan="4" rowspan="8">
    <table id='tab14' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td colspan="4">嘴棒数量交接（仅供计算使用）</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈德门</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈纯</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>红塔山</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>宏图</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>绿孔府</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
    </table></td>
    <td colspan="4" rowspan="8">
    <table id='tab15' width="400" border="1" cellpadding="0" cellspacing="0" frame="void" style="border-collapse:collapse;">
      <tr>
        <td colspan="4">嘴棒数量交接（仅供计算使用）</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈德门</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>哈纯</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>红塔山</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>宏图</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>绿孔府</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td style="display:none"></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
