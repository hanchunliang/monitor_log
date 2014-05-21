<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>查看预警配置文件</title>
<link href="${ctx}/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/sinosoft.message.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/sinosoft.window.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx}/static/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/static/js/sinosoft.layout.js"></script>
<script language="javascript" src="${ctx}/static/js/sinosoft.grid.js"></script>
<script language="javascript" src="${ctx}/static/js/sinosoft.window.js"></script>
<script language="javascript" src="${ctx}/static/js/sinosoft.message.js"></script>
<script type="text/javascript">
$(function(){
	$("#thresholdList").Grid({
		url : "thresholdList.json",  
		dataType: "json",
		colDisplay: false,  
		clickSelect: true,
		draggable:false,
		height: "auto",  
		colums:[  
			{id:'1',text:'名称',name:"appellation",index:'1',align:''},
			{id:'2',text:'描述',name:"appellation",index:'1',align:''},
			{id:'3',text:'严重告警条件',name:"appellation",index:'1',align:''},
			{id:'4',text:'警告告警条件',name:"appellation",index:'1',align:''},
			{id:'5',text:'正常告警条件',name:"appellation",index:'1',align:''},
		],  
		rowNum:9999,
		pager : false,
		number:false,  
		multiselect: false  
	});
	$("#MonitorList").Grid({
		url : "MonitorList.json",  
		dataType: "json",
		colDisplay: false,  
		clickSelect: true,
		draggable:false,
		height: 225,  
		colums:[  
			{id:'1',text:'监视器名称',name:"appellation",index:'1',align:''},
			{id:'2',text:'类型',name:"appellation",index:'1',align:''},
			{id:'3',text:'属性吗',name:"appellation",index:'1',align:''},
			{id:'4',text:'状态',name:"appellation",index:'1',align:''},
			{id:'5',text:'操作',name:"appellation",index:'1',align:''}
		],  
		rowNum:9999,
		pager : false,
		number:false,  
		multiselect: false  
	});
});
function shiftOut(e){
	var rows = $(e).parent().parent();
	var id = rows.attr('id');
	msgConfirm('系统消息','是否确定移出使用阈值？',function(){		
		rows.remove();
		msgSuccess("系统消息", "操作成功，使用阈值已移出！");
	});
}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="window_main">
    	<div class="threshold_file">
        	<h3 class="title3">阈值明细：</h3>
        	<div id="thresholdList"></div>
        </div>
    	<div class="threshold_file">
        	<h3 class="title3">监视器使用的阈值：</h3>
        	<div id="MonitorList"></div>
        </div>
    </div>
</div>
</body>
</html>
