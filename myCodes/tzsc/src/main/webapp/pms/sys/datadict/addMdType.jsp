<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 数据字典新增 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var zjId =$("#mdEqpTypeBeanId").val();
	 $(function(){
		var code1=$('#code1');
		if($.trim(code1.val())==""){
		var d = new Date()
		var str="ZB_"+d.getFullYear()+(d.getMonth() + 1)+ d.getDate()+d.getHours()+d.getMinutes()+d.getSeconds();
		code1.attr('value',str);
		}
	}); 
	
	
	$("#oilId").combobox({
		url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryAllLubricant.do?type=all',//all是 添加 请选择 
		textField:'lubricantName',
		valueField:'id',
		required:false,
	    onLoadSuccess: function(data){
		    if(data!=null&&data.length>0){
			    if(zjId==null||zjId==""){
			    	$("#oilId").combobox("setValue",data[0].id);
				}
			}
	    }
	});
	$("#fillUnit").combobox({
		url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryListById.do?key=8af2d43f4d938ab6014d94890f560002&&type=all',
		textField:'lubricantName',
		valueField:'id',
		required:false,
		onBeforeLoad: function(param){
			//alert("onBeforeLoad:"+param);
		},
	    onLoadSuccess: function(data){
		    if(data!=null&&data.length>0){
		    	if(zjId==null||zjId==""){
		    		$("#fillUnit").combobox("setValue",data[0].id);
		    	}
			}
	    }
	});

	$("#fashion").combobox({
		url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryListById.do?key=8af2d43f4d938ab6014d948996e60003&&type=all',
		textField:'lubricantName',
		valueField:'id',
		required:false,
	    onLoadSuccess: function(data){
		    if(data!=null&&data.length>0){
		    	if(zjId==null||zjId==""){
		    		$("#fashion").combobox("setValue",data[0].id);
		    	}
			}
	    }
	});

	$("#djMethodId").combobox({
		url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryListById.do?key=402899894db7df6f014db7f7080e0001&&type=all',
		textField:'lubricantName',
		valueField:'id',
		multiple:true,//多选
		required:false,
	    onLoadSuccess: function(data){
		    if(data!=null&&data.length>0){
		    	if(zjId==null||zjId==""){
		    		$("#djMethodId").combobox("setValue",data[0].id);
		    	}else{//有主键 表示 编辑状态
		    		var djMethodIds =$("#djMethodIds").val();
		    		$('#djMethodId').combobox('setValues',djMethodIds.split(','));
			    }
			}
	    }
	});
</script>
	<div style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
			<c:if test="${mdEqpTypeBean.id==null }">
			<legend>二级数据新增</legend>
			</c:if>
			<c:if test="${mdEqpTypeBean.id!=null }">
			<legend>二级数据修改</legend>
			</c:if>
				<table class="table" style="width: 100%;">
					<tr>
						<th>一级名称</th>
						<td>
							<c:if test="${mdEqpTypeBean.id==null }">
							<samp>${mdEqpTypeBean.categoryName }</samp>
							<input type="hidden" name="categoryId"  value="${mdEqpTypeBean.categoryId }"/>			
							</c:if>
							<c:if test="${mdEqpTypeBean.id!=null}">
								<samp>${mdEqpTypeBean.categoryName }</samp>
								<input type="hidden" id="mdEquCategoryType" name="categoryId"
								data-options="panelHeight:'auto',width:176,required:true"
								value="${mdEqpTypeBean.categoryId}" />
						</c:if>
						</td>
						<th>是否启用</th>
						<td>
							<c:choose>
								<c:when test="${mdEqpTypeBean.enable==1}">
									<select name="enable" class="easyui-combobox fselect"
										data-options="panelHeight:'auto',width:176,required:true"
										value="${mdEqpTypeBean.enable }">
										<option value="1" selected="selected">启用</option>
										<option value="0">禁用</option>
									</select>
								</c:when>
								<c:when test="${mdEqpTypeBean.enable==0}">
									<select name="enable" class="easyui-combobox fselect"
										data-options="panelHeight:'auto',width:176,required:true"
										value="${mdEqpTypeBean.enable }">
										<option value="1" >启用</option>
										<option value="0" selected="selected">禁用</option>
									</select>
								</c:when>
								<c:otherwise>
									<select name="enable" class="easyui-combobox fselect"
										data-options="panelHeight:'auto',width:176,required:true"
										value="1">
										<option value="1" selected="selected">启用</option>
										<option value="0">禁用</option>
									</select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>二级编码</th>
						<td>
							<input name="id" id="mdEqpTypeBeanId" type="hidden" value="${mdEqpTypeBean.id}"/> 
							<input type="text" id='code1' name="code" class="easyui-validatebox" data-options="required:true" value="${mdEqpTypeBean.code }" />
						</td>
						<th>二级名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${mdEqpTypeBean.name }"/>
						</td>
					</tr>
					<tr>
						<th>数据描述</th>
						<td colspan="3">
							<textarea style="width:92%;height:20px;resize:none" 
							name="des"  maxlength="1000" >${mdEqpTypeBean.des}</textarea>
						</td>
					</tr>
					<tr>
						<th style="text-align:left" colspan="4">针对轮保(保养)</th>
					</tr>
					<tr>
						<th>轮保类型</th>
						<td colspan="3">
							<select name="lxType" class="easyui-combobox fselect"
								data-options="panelHeight:'auto',width:176,required:false"
								value="">
								<option value="">请选择</option>
								<option value="8af2d43f4d73d86d014d73df6da90000" <c:if test="${mdEqpTypeBean.lxType=='8af2d43f4d73d86d014d73df6da90000'}">selected="selected"</c:if> >操作工项目</option>
								<option value="8af2d43f4d73d86d014d73e1615a0001" <c:if test="${mdEqpTypeBean.lxType=='8af2d43f4d73d86d014d73e1615a0001'}">selected="selected"</c:if> >机械轮保工项目</option>
								<option value="8af2d49050d2002d0150da33910005b2" <c:if test="${mdEqpTypeBean.lxType=='8af2d49050d2002d0150da33910005b2'}">selected="selected"</c:if> >电气轮保工项目</option>
							</select>
						</td>
					</tr>
					<tr>
						<th style="text-align:left" colspan="4">针对润滑(二级名称 即 润滑位置)</th>
					</tr>
					<tr>
						<th>点数</th>
						<td>
							<input name="number" class="easyui-numberspinner"
        					value="${mdEqpTypeBean.number}"
        					data-options="min:1,max:9999,width:176"/>
						</td>
						<th>润滑油(脂)</th>
						<td>
							<input  id="oilId" name="oilId" value="${mdEqpTypeBean.oilId}"/>
						</td>
					</tr>
					<tr>
						<th>加注量</th>
						<td>
							<input name="fillQuantity" class="easyui-numberspinner"
        					value="${mdEqpTypeBean.fillQuantity}"
        					data-options="min:0,max:9999,width:73,precision:2"/>
        					<input  id="fillUnit" name="fillUnit" data-options="width:97" value="${mdEqpTypeBean.fillUnit}"/>
						</td>
						<th>方 式</th>
						<td>
							<input  id="fashion" name="fashion" value="${mdEqpTypeBean.fashion}"/>
						</td>
					</tr>
					
					
					<tr>
					<th>润滑部位</th>
					<td >
					   <select name="rhPart" class="easyui-combobox fselect"
								data-options="panelHeight:'auto',width:176,required:false"
								value="">
								<option value="">请选择</option>
								<option value="VE" <c:if test="${mdEqpTypeBean.rhPart=='VE'}">selected="selected"</c:if> >VE-卷烟机</option>
								<option value="SE" <c:if test="${mdEqpTypeBean.rhPart=='SE'}">selected="selected"</c:if> >SE-卷烟机</option>
								<option value="MAX" <c:if test="${mdEqpTypeBean.rhPart=='MAX'}">selected="selected"</c:if> >MAX-卷烟机</option>
								<option value="HCF" <c:if test="${mdEqpTypeBean.rhPart=='HCF'}">selected="selected"</c:if> >HCF-卷烟机</option>
								<option value="ZF12B" <c:if test="${mdEqpTypeBean.rhPart=='ZF12B'}">selected="selected"</c:if> >ZF12B-卷烟机</option>
								<option value="YB25" <c:if test="${mdEqpTypeBean.rhPart=='YB25'}">selected="selected"</c:if> >YB25-包装机</option>
								<option value="YB45" <c:if test="${mdEqpTypeBean.rhPart=='YB45'}">selected="selected"</c:if> >YB45-包装机</option>
								<option value="YB55" <c:if test="${mdEqpTypeBean.rhPart=='YB55'}">selected="selected"</c:if> >YB55-包装机</option>
								<option value="YB65" <c:if test="${mdEqpTypeBean.rhPart=='YB65'}">selected="selected"</c:if> >YB65-包装机</option>
								<option value="YB95" <c:if test="${mdEqpTypeBean.rhPart=='YB95'}">selected="selected"</c:if> >YB95-包装机</option>
					    </select>
					</td>
					<th>润滑周期</th>
					<%-- <input value="${mdEqpTypeBean.unit_id}" id="period" name="period" style="width:100px;" value="" type="text" />  --%>
					<td>
					   <input value="${mdEqpTypeBean.period}" id="period" name="period" style="width:100px;" value="" type="text" /> 
					    <font style="color:red;font-size: 12px;font-weight: bold;">&nbsp;&nbsp;周期：</font>
					   <select name="unit_id" id="unit_id">
					      <option value="3" <c:if test="${mdEqpTypeBean.unit_id=='3'}">selected="selected"</c:if> >月</option>
					      <option value="4" <c:if test="${mdEqpTypeBean.unit_id=='4'}">selected="selected"</c:if> >年</option>
					      <option value="2" <c:if test="${mdEqpTypeBean.unit_id=='2'}">selected="selected"</c:if> >周</option>
					      <option value="1" <c:if test="${mdEqpTypeBean.unit_id=='1'}">selected="selected"</c:if>>日</option>
					   </select>
					  
					</td>
					
					</tr>
					
					
					<tr>
						<th style="text-align:left" colspan="4">针对点检</th>
					</tr>
					<tr>
						<th>点检类型</th><!-- value 值为sys_role表id -->
						<td>
							<select name="djType" class="easyui-combobox fselect"
								data-options="panelHeight:'auto',width:176,required:false"
								value="">
								<option value="">请选择</option><!-- 写死的数据 -->
								<option value="8af2d43f4d73d86d014d73df6da90000" <c:if test="${mdEqpTypeBean.djType=='8af2d43f4d73d86d014d73df6da90000'}">selected="selected"</c:if> >操作工点检</option>
								<option value="402899894db72650014db78d4035004f" <c:if test="${mdEqpTypeBean.djType=='402899894db72650014db78d4035004f'}">selected="selected"</c:if> >机械维修工点检</option>
								<option value="402899894db72650014db78daf010050" <c:if test="${mdEqpTypeBean.djType=='402899894db72650014db78daf010050'}">selected="selected"</c:if> >机械维修主管点检</option>
								<option value="8af2d49050d2002d0150da342dfb05b3" <c:if test="${mdEqpTypeBean.djType=='8af2d49050d2002d0150da342dfb05b3'}">selected="selected"</c:if> >电气维修工点检</option>
								<option value="8af2d49050d2002d0150da35251d05b4" <c:if test="${mdEqpTypeBean.djType=='8af2d49050d2002d0150da35251d05b4'}">selected="selected"</c:if> >电气维修主管点检</option>
							</select>
						</td>
						<th>点检方法</th>
						<td>
							<input type="text" name="djMethodId" value="${mdEqpTypeBean.djMethodId}"/>
							<input  id="djMethodIds" type="hidden" value="${mdEqpTypeBean.djMethodId}"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
