<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <link rel='stylesheet' href='${pageContext.request.contextPath}/pms/equ/wcplan/js/cupertino/jquery-ui.min.css' /> --%>
<link href='${pageContext.request.contextPath}/jslib/js/fullcalendar.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/jslib/js/fullcalendar.print.css'/>
<script src='${pageContext.request.contextPath}/jslib/js/moment.min.js'></script>
<%-- <script src='${pageContext.request.contextPath}/pms/equ/wcplan/js/jquery.min.js'></script> --%>
<script src='${pageContext.request.contextPath}/jslib/js/fullcalendar.min.js'></script>
<script src='${pageContext.request.contextPath}/jslib/js/lang-all.js'></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#calendar').fullCalendar({
		 editable: true,
		 eventSources: [
            // your event source
            {
                url: '${pageContext.request.contextPath}/pms/wcp/queryWCPlanCalendar.do',
                type: 'GET',
                
                //endParam:$.fullCalendar.formatDate('startParam','yyyy-MM-dd'),
                error: function() {
                    alert('加载失败!');
                },
                //color: 'yellow',   // a non-ajax option
                textColor: 'black', // a non-ajax option
                editable:false
            }
        ],
        selectable:false,
        lang: 'zh-cn',
		//events:eventsList,
		header: {
			left:   '',
		    center: 'title',
		    right:  'today prev,next'
		},
		eventClick: function(calEvent, jsEvent, view) {
			//alert(calEvent.id);
			
			/* $.fancybox({
				'type':'ajax',
				'href':'event.php?action=edit&id='+calEvent.id
			}); */
    	}
	});
	
	jQuery('.fc-button-prev').unbind('click');  
    jQuery('.fc-button-next').unbind('click');  
	//重新注册事件
    jQuery('.fc-button-prev').bind('click', prev);  
    jQuery('.fc-button-next').bind('click', next);  
    //jQuery('.fc-button-today').bind('click', next);  
    function prev() {  
    	$('#calendar').fullCalendar('prev');
    } 
    function next() {  
    	$('#calendar').fullCalendar('next');
    }
});

</script>
<style>

	body {
		margin: 0;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#top {
		background: #eee;
		border-bottom: 1px solid #ddd;
		padding: 0 10px;
		line-height: 40px;
		font-size: 12px;
	}

	#calendar {
		max-width: 600px;
		margin: 5px auto;
		padding: 0 10px;
	}


</style>


<div id="main"  style="width:600px;heigth:540px;">
   <div id='calendar'></div>
</div>
