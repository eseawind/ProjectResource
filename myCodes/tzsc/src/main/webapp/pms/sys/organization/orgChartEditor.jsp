<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script src="go.release.js"></script> 
<script type="text/javascript">
$(function(){
	collapsePanel();
	 $("#myOverview").offset(function (n, c) {
         newPos = new Object();
         newPos.left = $("#myDiagram").offset().left;
         newPos.top = $("#myDiagram").offset().top;
         return newPos;
     });
});
function expandPanel(){
	$('#mainLayout').layout('expand','east');
}

function collapsePanel(){
	$('#mainLayout').layout('collapse','east');
}
</script>
<script id="code">
	function init() {
		var $ = go.GraphObject.make; // for conciseness in defining templates
		// 必须是ID，或者依赖div
		myDiagram = $(go.Diagram, "myDiagram", {
			//整个拓扑图的位置
			initialContentAlignment : go.Spot.Center,
			// 确保所有用户只创建树
			validCycle : go.Diagram.CycleDestinationTree,
			"draggingTool.dragsTree": true,
	        "commandHandler.deletesTree": true,
			//用户一次只能选择一个
			maxSelectionCount : 1,
			layout : $(go.TreeLayout, {
				treeStyle : go.TreeLayout.StyleLastParents,
				arrangement : go.TreeLayout.ArrangementHorizontal,
				// 对于大多数的树的性质
				angle : 90,
				layerSpacing : 35,
				//为“最后的父母属性”：
				alternateAngle : 90,
				alternateLayerSpacing : 35,
				alternateAlignment : go.TreeLayout.AlignmentBus,
				alternateNodeSpacing : 20
			}),
			//支持编辑HTML中的属性选择的人
			"ChangedSelection" : onSelectionChanged,
			"TextEdited" : onTextEdited,
			// 是否能撤销恢复
			"undoManager.isEnabled" : true
		}); 
		 /* myDiagram = $(go.Diagram, "myDiagram", {
            	allowCopy: true,
            	allowDelete: true,
            	allowMove: true,
            	initialContentAlignment: go.Spot.Center,
            	initialAutoScale: go.Diagram.Uniform,
            	//用户一次只能选择一个
    			maxSelectionCount : 1,
    			// 确保所有用户只创建树
    			validCycle : go.Diagram.CycleDestinationTree,
            	layout: $(FlatTreeLayout,{ 
              		angle: 90, 
              		compaction: go.TreeLayout.CompactionNone 
              	}),
            	"undoManager.isEnabled": true
          }); */
		//添加修改监听事件
		// 当文件被修改，添加一个“*”的称号，使“保存”按钮
		myDiagram.addDiagramListener("Modified", function(e) {
			var button = document.getElementById("SaveButton");
			if (button)
				button.disabled = !myDiagram.isModified;
			var idx = document.title.indexOf("*");
			if (myDiagram.isModified) {
				if (idx < 0)
					document.title += "*";
			} else {
				if (idx >= 0)
					document.title = document.title.substr(0, idx);
			}
		});
          
          
		//级别颜色 
		var levelColors = [ "#30B340/#30AA40", "#2672EC/#2E8DEF",
				"#8C0095/#A700AE", "#5133AB/#643EBF", "#008299/#00A0B1",
				"#D24726/#DC572E", "#008A00/#00A600", "#094AB2/#0A5BC4" ];

		// 重写treelayout.commitnodes也修改基于树的深度平面背景刷
		myDiagram.layout.commitNodes = function() {
			//行为的标准
			go.TreeLayout.prototype.commitNodes.call(myDiagram.layout); 
			// 然后通过所有的顶点和设置相应的节点的shape.fill
			// 刷新一个依赖于treevertex.level
			myDiagram.layout.network.vertexes.each(function(v) {
				if (v.node) {
					var level = v.level % (levelColors.length);
					var colors = levelColors[level].split("/");
					var shape = v.node.findObject("SHAPE");
					if (shape){
						shape.fill = $(go.Brush, go.Brush.Linear, {
							0 : colors[0],
							1 : colors[1],
							start : go.Spot.Left,
							end : go.Spot.Right
						});
					}
				}
			});
		}

		// 绑定节点双击事件，添加一个新节点，在该节点的下一个级别
		function nodeDoubleClick(e, obj) {
			var clicked = obj.part;
			if (clicked !== null) {
				var thisemp = clicked.data;
				myDiagram.startTransaction("添加员工");
				var nextkey = (myDiagram.model.nodeDataArray.length + 1).toString();
				var newemp = {
					key : nextkey,
					name : "(new person)",
					title : "",
					parent : thisemp.key
				};
				myDiagram.model.addNodeData(newemp);
				myDiagram.commitTransaction("添加员工");
			}
		}

		// 判断节点是否可以拖动
		function mayWorkFor(node1, node2) {
			//必须是一个节点
			if (!(node1 instanceof go.Node))
				return false; 
			//不能节点本身
			if (node1 === node2)
				return false; 
			//不能是你的下级
			if (node2.isInTreeOf(node1))
				return false; 
			var root = node2.findTreeRoot();
			//不能是根节点
			if(root && node1 === root)
				return false;
			return true;
		}

		// 这个方法提供了一个共同的风格大部分textblocks。
		// 这些值可以在一个特定的TextBlock重写。
		function textStyle() {
			return {
				font : "bold 14pt 微软雅黑",
				stroke : "white"
			};
		}

		//获取头像图片
		function findHeadShot(key) {
			return "plugins/gojs/assets/img/HS" + key + ".png";
		}
		myDiagram.toolManager.hoverDelay = 200;
		//定义节点模版
		myDiagram.nodeTemplate = $(go.Node, "Vertical", {
				doubleClick : nodeDoubleClick,
				selectionObjectName: "BODY"
			},{
				toolTip:
			        $(go.Adornment, "Auto", $(go.Shape, {
				        	fill: "lightyellow" 
				        }),
				        $(go.Panel, "Horizontal", $(go.Picture, {
							maxSize : new go.Size(39, 50),
							margin : new go.Margin(6, 8, 6, 10),
						}, 
						//绑定图片资源
						new go.Binding("source", "key", findHeadShot)),
				        $(go.TextBlock, { margin: 3 },new go.Binding("text", "key")))
			        )
			},{ 
				//鼠标拖动节点事件
				mouseDragEnter : function(e, node, prev) {
					var diagram = node.diagram;
					var selnode = diagram.selection.first();
					var shape = null;
					if (!mayWorkFor(selnode, node)){
						alert();
						myDiagram.UndoManager().undo();
					}else{
						shape =  node.findObject("SHAPE");
					}
					if (shape) {
						shape._prevFill = shape.fill; //记住原来的
						shape.fill = "darkred";
					}
				},
				mouseOver: function(e,node,text){
					//alert();
				},
				//拖动离开
				mouseDragLeave : function(e, node, next) {
					var shape = node.findObject("SHAPE");
					if (shape && shape._prevFill) {
						shape.fill = shape._prevFill; //保存原来的
					}
				},
				//鼠标按下事件
				mouseDrop : function(e, node) {
					var diagram = node.diagram;
					//假设在选择一个节点
					var selnode = diagram.selection.first(); 
					if (mayWorkFor(selnode, node)) {
						// 找到任何现有的连接到所选节点
						var link = selnode.findTreeParentLink();
						if (link !== null) { 
							//重置连接
							link.fromNode = node;
						} else { 
							//否则创建一个新连接
							diagram.toolManager.linkingTool.insertLink(node,node.port, selnode, selnode.port);
						}
					}
				}
			},
			//排序，有node.text是data.name
			new go.Binding("text", "name"),
			// 结合part.layername控制节点层取决于是否选择
			new go.Binding("layerName", "isSelected", function(sel) {
				return sel ? "Foreground" : "";
			}).ofObject(),
			$(go.Panel, "Auto",{
					name : "BODY"
				},
				//定义节点的外形 圆角矩形
				$(go.Shape, "RoundedRectangle", {
					name : "SHAPE",
					fill : "white",
					stroke : null,
					//设置节点属性
					portId : "",
					fromLinkable : true,
					toLinkable : true,
					cursor : "pointer"
				}),
				//定义水平面板
				$(go.Panel, "Horizontal", 
					/* $(go.Picture, {
							maxSize : new go.Size(39, 50),
							margin : new go.Margin(6, 8, 6, 10),
						}, 
						//绑定图片资源
						new go.Binding("source", "key", findHeadShot)
					), */
					//定义面板上的文字显示布局
					$(go.Panel, "Table", {
							maxSize : new go.Size(300, 999),
							margin : new go.Margin(15, 20, 15, 20),
							defaultAlignment : go.Spot.Left
						}, 
						$(go.RowColumnDefinition, { //两列
							column : 2,
							width : 4
						}), 
						$(go.TextBlock, textStyle(), {//名称
								row : 0,
								column : 0,
								columnSpan : 5,
								font : "14pt 微软雅黑",
								editable : true,
								isMultiline : false,
								minSize : new go.Size(10, 16)
							}, 
							new go.Binding("text", "name").makeTwoWay()
						)
						//标题显示
						/* $(go.TextBlock, "部门名称: ", textStyle(), {
							row : 1,
							column : 0
						}),
						$(go.TextBlock, textStyle(), {
								row : 1,
								column : 1,
								columnSpan : 4,
								editable : true,
								isMultiline : false,
								minSize : new go.Size(10, 14),
								margin : new go.Margin(0, 0, 0,0)
							}, 
							new go.Binding("text", "title").makeTwoWay()
						), 
						$(go.TextBlock, "节点ID: ", textStyle(), {
							row : 2,
							column : 0
						}),
						$(go.TextBlock, textStyle(), {
								row : 2,
								column : 1,
								columnSpan : 4,
								editable : true,
								isMultiline : false,
								minSize : new go.Size(10, 14),
								margin : new go.Margin(0, 0, 0,0)
							}, 
							new go.Binding("text", "key").makeTwoWay()
						),
						$(go.TextBlock, "父节点: ", textStyle(), {
							row : 3,
							column : 0
						}),
						$(go.TextBlock, textStyle(), {
								row : 3,
								column : 1,
								columnSpan : 4,
								editable : true,
								isMultiline : false,
								minSize : new go.Size(10, 14),
								margin : new go.Margin(0, 0, 0,0)
							}, 
							new go.Binding("text", "parent").makeTwoWay()
						),
						$(go.TextBlock, textStyle(),{
								row : 3,
								column : 0,
								columnSpan : 5,
								font : "italic 9pt sans-serif",
								wrap : go.TextBlock.WrapFit,
								editable : true, //默认换行符被允许
								minSize : new go.Size(10, 14)
							}, 
							new go.Binding("text", "comments").makeTwoWay()
						) */
					) // 结束table面板
				)//结束水平面板 
			),//结束主面板
			//添加tree展开按钮 下面是主体
			$(go.Panel,{ height: 15 },$("TreeExpanderButton"))
		); // 结束节点
		//定义连接模版
		myDiagram.linkTemplate = $(go.Link, go.Link.Orthogonal,{ 
				corner: 10,
				relinkableFrom : true,
				relinkableTo : true 
			},
          	$(go.Shape,{ strokeWidth:2,stroke : "#00a4a4" }),
          	$(go.Shape, { toArrow: "Standard",stroke : "#00a4a4" })
        );

		//加载JSON格式数据
		load();
		//缩略图
		//显示在HTML元素上面
	    myOverview = $(go.Overview, "myOverview",{ 
	    	observed: myDiagram 
	    }); 
	}

	// 当选择一个节点时，允许用户编辑
	function onSelectionChanged(e) {
		var node = e.diagram.selection.first();
		if (node instanceof go.Node) {
			updateProperties(node.data);
		} else {
			updateProperties(null);
			collapsePanel();
		}
	}

	// Update the HTML elements for editing the properties of the currently selected node, if any
	function updateProperties(data) {
		expandPanel();
		if (data === null) {
			document.getElementById("propertiesPanel").style.display = "none";
			document.getElementById("name").value = "";
			document.getElementById("title").value = "";
			document.getElementById("comments").value = "";
		} else {
			document.getElementById("propertiesPanel").style.display = "block";
			document.getElementById("name").value = data.name || "";
			document.getElementById("title").value = data.title || "";
			document.getElementById("comments").value = data.comments || "";
		}
	}

	//这就是当用户完成文本编辑
	function onTextEdited(e) {
		var tb = e.subject;
		if (tb === null || !tb.name)
			return;
		var node = tb.part;
		if (node instanceof go.Node) {
			updateProperties(node.data);
		}
	}

	// 更新字段数据
	function updateData(text, field) {
		var node = myDiagram.selection.first();
		// 最大选择一个节点, 因此这是一个部分的集合
		var data = node.data;
		if (node instanceof go.Node && data !== null) {
			var model = myDiagram.model;
			model.startTransaction("modified " + field);
			if (field === "name") {
				model.setDataProperty(data, "name", text);
			} else if (field === "title") {
				model.setDataProperty(data, "title", text);
			} else if (field === "comments") {
				model.setDataProperty(data, "comments", text);
			}
			model.commitTransaction("modified " + field);
		}
		collapsePanel();
	}

	//保存JSON
	function save() {
		document.getElementById("mySavedModel").value = myDiagram.model.toJson();
		myDiagram.isModified = false;
	}
	//加载数据
	function load() {
		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
	}
	
	//自定义布局
	function FlatTreeLayout() {
	  //调用基本结构
      go.TreeLayout.call(this);  
    }
    go.Diagram.inherit(FlatTreeLayout, go.TreeLayout);
    FlatTreeLayout.prototype.commitLayout = function() {
      	go.TreeLayout.prototype.commitLayout.call(this);  // call base method first
      	//找到Y坐标的所有节点
      	var y = -Infinity;
      	//遍历
      	this.network.vertexes.each(function(v) {
        	y = Math.max(y, v.node.position.y);
	    });
      	// 移动所有的节点，但是X坐标不变
      	this.network.vertexes.each(function(v) {
          	var node = v.node;
          	if (node.isTreeLeaf) {
            	node.position = new go.Point(node.position.x, y);
          	}
      	});
    };
    //自定义布局结束
</script>
</head>
<body onload="init()">
	<div id="myOverview" style="border: solid 1px red; width: 180px; height: 80px; position: absolute; top:0px; left: 0px; background-color: aliceblue; z-index: 900"></div>       
	<div id="mainLayout" class="easyui-layout" data-options="fit: true,border:false">
		<div data-options="region: 'north',height:35">
			<div class="easyui-toolbar">
				<a onclick="getAllOrgs();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">查询</a>
			<a onclick="goToOrgAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">添加</a>
			<a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_next'">展开</a>
			<a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">折叠</a>
			<a onclick="toView();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">切换到视图显示</a>
			<a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">切换到表格显示</a>
			</div>
		</div>
		<div id="propertiesPanel" title="属性" data-options="region: 'east',border:false,iconCls: 'icon-standard-map', split: true" style="width:200px; padding:0px;">
			<table class="table-edit" width="100%" align="center">
				<tr>
					<th >名称:</th>
					<td >
						<input type="text" id="name" value="" onchange="updateData(this.value, 'name')" />
					</td>
				</tr>
				<tr>
					<th > 标题:</th>
					<td>
						 <input type="text" id="title" value="" onchange="updateData(this.value, 'title')" />
					</td>
				</tr>
				<tr>
					<th >备注:</th>
					<td >
						<input type="text" id="comments" value="" onchange="updateData(this.value, 'comments')" />
					</td>
				</tr>
				<tr>
					<td style="text-align: center" colspan="2">
						<button id="SaveButton" onclick="save()">保存</button>
						<button onclick="load()">查询</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="myDiagram" data-options="region: 'center',border:false, split: true" style="padding: 0px;">
			
		</div>
		<div style="display:none;">
			<textarea id="mySavedModel" style="width: 100%; height: 250px">
				{ "class": "go.TreeModel",
				  "nodeDataArray": [
				{"key":"1", "name":"上海兰宝传感科技股份有限公司", "title":"执行总监"}, 
				{"key":"2", "name":"研发部", "title":"财务总监", "parent":"11"}, 
				{"key":"3", "name":"销售部", "title":"销售经理", "parent":"11"}, 
				{"key":"4", "name":"人力资源部", "title":"市场部经理", "parent":"11"}, 
				{"key":"5", "name":"信息系统部", "title":"研发部经理", "parent":"11"}, 
				{"key":"6", "name":"生产部", "title":"软件工程师", "parent":"11"}, 
				{"key":"7", "name":"客户服务部", "title":"行政助理", "parent":"11"},
				{"key":"8", "name":"审计会", "title":"办公室主任", "parent":"1"},
				{"key":"9", "name":"生管课", "title":"科长", "parent":"6"},
				{"key":"10", "name":"制造课", "title":"工程师", "parent":"7"},
				{"key":"11", "name":"兰宝传感", "title":"人事部职员", "parent":"1"},
				{"key":"12", "name":"兰宝环保", "title":"软件工程师", "parent":"1"},
				{"key":"13", "name":"项目营销部", "title":"测试人员", "parent":"12"},
				{"key":"14", "name":"项目研发部", "title":"硬件维护", "parent":"10"},
				{"key":"15", "name":"项目工程部", "title":"质量检测", "parent":"5"},
				{"key":"16", "name":"项目服务部", "title":"销售代理", "parent":"3"} 
				 ]
				}
		 </textarea>
		</div>
	</div>
</body>
</html>