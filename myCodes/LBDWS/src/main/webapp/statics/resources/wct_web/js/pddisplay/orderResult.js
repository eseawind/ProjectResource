/**
 * 
 * @requires jQuery
 * 
 * 工单实际页面js
 * 
 * @returns object
 */
//查询工单实际消耗，给弹出框赋值
/*start*/
queryDetail = function(tr) {
	var orderId=tr.find("td:first").html();
	var futRoot=$("#futRoot").html();
	$.ajax({
	  type:"POST",
	  url: futRoot+"/wct/orderResult/getOrderResultDetails.json",
	  cache: false,
	  data:"orderID="+orderId,
	  success: function(data){
		  //清空表格
		 $('#fltable').html(null);
		 var html="<tr ><th>辅料名称</th><th>辅料消耗 </th><th>单位 </th></tr>";
	  	//为产出和时间赋值
		  if(data.length>0){
		    var val=data[0];
		  	$("#outQty").html(val.outQty+val.outQtyUnit);
		  	$('#stim').html(val.rstim);
		  	$('#etim').html(val.retim);
		  	for(index in data){
		  	 	val=data[index]
		  	 	html+="<tr>"
		  	 	html+="<td>"+val.flName+"</td>";
		  	 	html+="<td>"+val.flQty+"</td>";
		  	 	html+="<td>"+val.flUnitName+"</td>";
		  	    html+="</tr>";
		  	 }
		  }
		$('#fltable').append(html);
	    $('#myModal').modal('show');
	  }
	});
}
/*end*/

