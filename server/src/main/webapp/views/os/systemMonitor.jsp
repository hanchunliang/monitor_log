<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统-linux监控</title>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<link href="${ctx}/global/css/sinosoft.tabs.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx }/global/js/sinosoft.tabs.js"></script>
<script language="javascript" src="${ctx }/global/js/highcharts.src.js"></script>
<script language="javascript"
	src="${ctx }/global/js/os/systemMonitor.js"></script>
<script type="text/javascript">
	$(function() {
			Highcharts.setOptions({
						global : {
							useUTC : false
						}
					});
		$("body").layout({
			top : {
				topHeight : 100
			},
			bottom : {
				bottomHeight : 30
			}
		});
		if($.browser.msie && ($.browser.version == "7.0")){
			var center = $("#layout_center");
			$("#main").width(center.width() - 31).height(center.height() - 30);
		}
		getForm();
		
		$("#thresholdList").empty();
		$("#thresholdList").Grid({
			url :  "/monitor/os/systemList",  
			dataType: "json",
			colDisplay: false,  
			clickSelect: true,
			draggable:false,
			height: "auto",  
			colums: [  
			 		{id:'1',text:'名称',name:"appellation",index:'1',align:''},
					{id:'2',text:'可用性',name:"appellation",index:'1',align:''},
					{id:'3',text:'健康状态',name:"appellation",index:'1',align:''},
					<shiro:hasPermission name='admin'>
					{id:'4',text:'操作',name:"appellation",index:'1',align:''}
					</shiro:hasPermission>
				],  
			rowNum:9999,
			pager : false,
			number:false,  
			<shiro:lacksPermission name='admin'>
			multiselect: false 
			</shiro:lacksPermission>
		});
		$("#tabs").tabs({
			closeTab : false
		});
	});
</script>
</head>

<body>
	<%@include file="/WEB-INF/layouts/menu.jsp"%>
	<div id="layout_center">
		<div class="main" id="main">
			<ul class="crumbs">
				<li><a href="#">监视器</a> ></li>
				<li><b> Linux - 批量配置视图 </b></li>
			</ul>
			<hr class="top_border" />
			<div id="tabs">
				<ul>
					<li class="tabs_select">可用性</li>
					<li>性能</li>
					<li>列表视图</li>
				</ul>
				<div>
					<br />
					<div class="threshold_file availability">
						<h2 class="title2">
							<b>可用性历史纪录- Linux </b> <select name="" class="diySelect"
								onchange="availableChange(this)">
								<option value="24">最近24小时</option>
								<!-- <option value="30">最近30天</option> -->
							</select>
						</h2>
						<div id="systemMonitorTable"></div>
						<div class="explain">
							<ul>
								<li><span class="ex_no"></span>不可用</li>
								<li><span class="ex_is"></span>可用</li>
							</ul>
							查看监视器/业务组最近24小时或30天的可用性状态
						</div>
					</div>
				</div>
				<div>
					<br />
					<div class="threshold_file">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="my_table">
							<tr>
								<td class="group_name"><h3>物理内存利用率</h3></td>
								<td class="group_name"><h3>交换内存利用率</h3></td>
							</tr>
							<tr>
								<td width="50%"><div id="chartMem"></div></td>
								<td width="50%"><div id="chartSwap"></div></td>
							</tr>
							<tr>
								<td class="group_name"><h3>CPU利用率</h3></td>
								<td class="group_name"><h3>应答时间</h3></td>
							</tr>
							<tr>
								<td><div id="chartCpu"></div></td>
								<td><div id="chartReply"></div></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
						<%--<div class="threshold_file availability">--%>
							<%--<h2 class="title2">--%>
								<%--<b> 健康状态操控板</b> <select name="select" class="diySelect"--%>
									<%--onchange="healthChange(this)">--%>
									<%--<option value="24">最近24小时</option>--%>
									<%--<option value="30">最近30天</option>--%>
								<%--</select>--%>
							<%--</h2>--%>
							<%--<div id="healthList"></div>--%>
							<%--<div class="explain">--%>
								<%--<ul>--%>
									<%--<li><span class="ex_is"></span>正常</li>--%>
									<%--<li><span class="ex_xp"></span>警告</li>--%>
									<%--<li><span class="ex_no"></span>严重</li>--%>
								<%--</ul>--%>
								<%--查看监视器/业务组过去24小时或30天的健康状态告警--%>
							<%--</div>--%>
						<%--</div>--%>
					</div>
				</div>
				<div>
					<br />
					<div class="threshold_file">
						<h2 class="title2">
							<b>操作系统列表视图 </b>
						</h2>
						<div class="tool_bar_top">
							<shiro:hasPermission name="admin">
							<a href="javascript:void(0);" class="batch_del"
								onclick="batchDel()">批量删除</a>
							</shiro:hasPermission>
						</div>
						<div id="thresholdList"></div>
						<div class="tool_bar"></div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<div id="layout_bottom">
		<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
	</div>
</body>
</html>
