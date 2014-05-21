<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>monitor监控系统-新建应用系统监视器</title>
<%@include file="/WEB-INF/layouts/base.jsp"%>
<script type="text/javascript">
$(function(){
	$("body").layout({
		top:{topHeight:100},
		bottom:{bottomHeight:30}
	});
	$("#myDesk").height($("#layout_center").height());
	$("#nav").delegate('li', 'mouseover mouseout', navHover);
	$("#nav,#menu").delegate('li', 'click', navClick);
});
function navHover(){
	$(this).toggleClass("hover")
}
function navClick(){
	$(this).addClass("seleck").siblings().removeClass("seleck");
	if($(this).hasClass('has_sub')){
		var subMav = $(this).children("ul.add_sub_menu");
		var isAdd = false;
		if($(this).parent().attr("id") == "menu"){
			isAdd = true;
		};
		subMav.slideDown('fast',function(){
			$(document).bind('click',{dom:subMav,add:isAdd},hideNav);
			return false;
		});		
	};
}
function hideNav(e){
	var subMenu = e.data.dom;
	var isAdd = e.data.add;
	subMenu.slideUp('fast',function(){
		if(isAdd){
			subMenu.parent().removeClass('seleck');
		};
	});	
	$(document).unbind();
}
</script>
</head>

<body>
<div id="layout_top">
	<div class="header">
    	<%@include file="/WEB-INF/layouts/menu.jsp"%>
    </div>
</div>
<div id="layout_center">
	<div class="main">
		<ul class="crumbs">
            <li>管理></li>
            <li><b>新建监视器</b></li>
        </ul>
    	<div class="add_monitor">
       	  <h2 class="title2"><b>新建监视器<span>(选择并配置监视器)</span></b></h2>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box">
              <tr>
                <td width="33%">
                	<h3 class="title3">应用服务器</h3>
                	<ul>
                    	<li><a href="${ctx}/addmonitor/addapp">应用系统</a></li>
                    </ul>
                </td>
                <td width="33%" class="center">
               	  <h3 class="title3">数据库</h3>
                	<ul>
                    	<li><a href="${ctx}/addmonitor/addoracle">Oracle</a></li>
                    </ul>
                </td>
                <%--<td width="33%">--%>
               	  <%--<h3 class="title3">操作系统</h3>--%>
                	<%--<ul>--%>
                    	<%--<li><a href="${ctx}/addmonitor/addos">Linux</a></li>--%>
                    <%--</ul>--%>
                <%--</td>--%>
              </tr>
            </table>
        </div>
    </div>
</div>
<div id="layout_bottom">
	<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
