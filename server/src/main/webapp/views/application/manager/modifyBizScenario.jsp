<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<% request.setAttribute("appId",request.getParameter("appId")); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>monitor监控系统-编辑业务场景信息</title>
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
/*校验数据*/
function isValid(form) {
    if (form.name.value==null||form.name.value=="") {
        msgAlert('系统消息','场景名称不能为空！')
        return false;
    }
    var bsGrade=form.bizScenarioGrade.value;
    if(bsGrade!="高"&&bsGrade!="中"&&bsGrade!="低"){
        msgAlert('系统消息','必须选择场景级别！')
        return false;
    }
    return true;
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
    	<div class="add_monitor">
       	  <h2 class="title2"><b>编辑业务场景　</b>
          	
          </h2>
          <form:form id="modifyBizScenario" action="${ctx}/application/manager/bsmanager/update/${appId}/${bizScenario.id}"
                     method="post" class="form-horizontal" onsubmit="return isValid(this);">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
              <tr>
                <td colspan="2" class="group_name">基本信息</td>
              </tr>
              <tr>
                <td width="25%">场景名称<span class="mandatory">*</span></td>
                <td><input id="name" name="name" value="${bizScenario.name}" type="text" class="formtext" />
                    <msg:errorMsg property="name" type="message"/>
                </td>
              </tr>
              <tr>
                <td>级别<span class="mandatory">*</span></td>
                <td>
                	<select id="bizScenarioGrade" name="bizScenarioGrade" class="diySelect" >
                    <option <c:if test="${bizScenario.bizScenarioGrade=='HIGH'}">selected="selected"</c:if> value="高">高</option>
                    <option <c:if test="${bizScenario.bizScenarioGrade=='INTERMEDIATE'}">selected="selected" </c:if> value="中">中</option>
                    <option <c:if test="${bizScenario.bizScenarioGrade=='LOW'}">selected="selected"</c:if> value="低">低</option>
                  </select>
                    <msg:errorMsg property="bizScenarioGrade" type="message"/>
                </td>
              </tr>
              <tr>
                <td class="group_name">&nbsp;</td>
                <td class="group_name">
                	<input id="submit" type="submit" class="buttons" value="确定修改"　
                    <input type="reset" class="buttons" value="重 置" />　
                    <input type="button" class="buttons" value="取 消" onclick="window.history.back()" />
                </td>
              </tr>
            </table>
            </form:form>
        </div>
    </div>
</div>
<div id="layout_bottom">
	<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
