<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改监视器</title>
<link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/global/css/sinosoft.message.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.message.js"></script>
<script type="text/javascript">
	$(function() {
		$("body").layout({
			top : {
				topHeight : 100
			},
			bottom : {
				bottomHeight : 30
			}
		});
		$("#myDesk").height($("#layout_center").height());
		$("#nav").delegate('li', 'mouseover mouseout', navHover);
		$("#nav,#menu").delegate('li', 'click', navClick);
	});
	function navHover() {
		$(this).toggleClass("hover")
	}
	function navClick() {
		$(this).addClass("seleck").siblings().removeClass("seleck");
		if ($(this).hasClass('has_sub')) {
			var subMav = $(this).children("ul.add_sub_menu");
			var isAdd = false;
			if ($(this).parent().attr("id") == "menu") {
				isAdd = true;
			}
			;
			subMav.slideDown('fast', function() {
				$(document).bind('click', {
					dom : subMav,
					add : isAdd
				}, hideNav);
				return false;
			});
		}
		;
	}
	function hideNav(e) {
		var subMenu = e.data.dom;
		var isAdd = e.data.add;
		subMenu.slideUp('fast', function() {
			if (isAdd) {
				subMenu.parent().removeClass('seleck');
			}
			;
		});
		$(document).unbind();
	}
	function update() {
		var flag = false;
		$('#addSystem').submit();
		/* $.ajax({
			type : "Post",
			url : "/monitor/os/isIpExists",
			data : "ipAddr=" + $("#ipAddr").val(),
			success : function(data) {
				if (data == true) {
					$("#ipAddr").next().addClass("prompt").html("监视器已存在！");
				} else {
					msgSuccess("系统消息", "操作成功，监视器已保存！", function() {
						
					});
				}
			}
		}); */
	}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
<div class="main">
<div class="add_monitor">
<!-- <h2 class="title2"><b>新建监视器类型 </b> <select name=""
	class="diySelect" onchange="top.location=this.value;">
	<optgroup label="应用服务器">
		<option value="addSystem.html">应用系统</option>
	</optgroup>
	<optgroup label="数据库">
		<option value="addOracle.html">Oracle</option>
	</optgroup>
	<optgroup label="操作系统">
		<option selected="selected" value="addLinux.jsp">Linux</option>
	</optgroup>
</select></h2> -->
<form id="addSystem" action="${ctx }/os/edit" method="post"
	class="form-horizontal">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="add_monitor_box add_form">
	<tr>
		<td colspan="2" class="group_name">基本信息</td>
	</tr>
	<tr>
		<td width="25%">显示名称<span class="mandatory">*</span></td>
		<td><input name="os.name" type="text" class="formtext"
			value="${os.name }" /></td>
			<input name="os.osInfoId" value="${os.osInfoId }" type="hidden"/>
	</tr>
	<tr>
		<td>主机名/IP地址<span class="mandatory">*</span></td>
		<td><input id="ipAddr" name="os.ipAddr" readonly="readonly" type="text"
			class="formtext" value="${os.ipAddr }" /><span></span></td>
	</tr>
	<tr>
		<td>子网掩码<span class="mandatory">*</span></td>
		<td><input name="os.subnetMask" type="text" class="formtext"
			value="255.255.255.0" size="30" /></td>
	</tr>
	<tr>
		<td>OS类型<span class="mandatory">*</span></td>
		<td><select name="os.type" class="diySelect" style="width: 100px">
			<option value="${os.type }">Linux</option>
		</select></td>
	</tr>
	<tr>
	<!-- 	<td>端口<span class="mandatory">*</span></td>
		<td><input name="input2" type="text" class="formtext" size="8" /></td> -->
	</tr>
	<tr>
		<td>轮询间隔<span class="mandatory">*</span></td>
		<td><input name="os.intercycleTime" type="text" class="formtext" size="8" value="${os.intercycleTime }"/>
		分</td>
	</tr>
	<tr>
		<td class="group_name">&nbsp;</td>
		<td class="group_name"><input type="button" class="buttons"
			value="确定修改" onclick="update()" /> <input type="reset" class="buttons"
			value="重 置" /> <input type="button" class="buttons" value="取 消"
			onclick="window.history.back()" /></td>
	</tr>
</table>
</form>
</div>
</div>
</div>
<div id="layout_bottom">
<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
