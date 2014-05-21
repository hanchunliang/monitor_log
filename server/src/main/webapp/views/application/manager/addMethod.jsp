<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统-新增Method</title>
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
function save(){
	msgSuccess("系统消息", "操作成功，监视器已保存！");
	window.location.href="manageMethod.html";
}
/*校验数据*/
function isValid(form) {
    String.prototype.trim = function(){
        return this.replace(/(^\s*|\s*$)/g,'');
    }
    if (form.className.value==null||form.className.value.trim()=="") {
        msgAlert('系统消息','全类名不能为空或者空格！')
        return false;
    }
    var clName=/^[\w\.]+$/;
    if (!clName.test(form.className.value)) {
        msgAlert('系统消息',"全类名必须是英文，数字，“_”，“.”（或者以上的组合）！");
        return false;
    }
    if (form.className.value.length>500) {
        msgAlert('系统消息','全类名长度不能超过500！')
        return false;
    }
    if (form.methodName.value==null||form.methodName.value.trim()=="") {
        msgAlert('系统消息','方法名不能为空或者空格！')
        return false;
    }
    var meName=/^[\w(),]+$/;
    if (!meName.test(form.methodName.value)) {
        msgAlert('系统消息',"方法名必须是英文,(),“_”,“,”或数字（或者以上的组合）！");
        return false;
    }
    if (form.methodName.value.length>100) {
        msgAlert('系统消息','方法名长度不能超过300！')
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
       	  <h2 class="title2"><b>添加方法　</b>
          	
          </h2>
          <form:form id="addMethod" action="${ctx}/application/manager/methodmanager/addmethod/${bizScenarioId}/${urlId}" method="post"
                     class="form-horizontal" onsubmit="return isValid(this);">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
              <tr>
                <td colspan="2" class="group_name">基本信息</td>
              </tr>
              <tr>
                <td>全类名<span class="mandatory">*</span></td>
                <td><input id="className" name="className" type="text" value="${method.className}" class="formtext" style="width: 700px;" />
                    <msg:errorMsg property="className" type="message"/>
                </td>
              </tr>
              <tr>
                <td>方法名<span class="mandatory">*</span></td>
                <td><input id="methodName" name="methodName" type="text" value="${method.methodName}" class="formtext" style="width: 300px;" />
                    <msg:errorMsg property="methodName" type="message"/>
                </td>
              </tr>
              <tr>
                  <td width="25%">方法描述<span class="mandatory"></span></td>
                  <td><textarea name="description"  value="${method.description}" class="formtext" style="height: 60px;width: 220px"></textarea></td>
              </tr>
              <%--<tr>
                  <td width="25%">方法阈值<span class="mandatory"></span></td>
                  <td><input name="threshold" type="text" value="${method.className}" class="formtext" size="100" /></td>
              </tr>--%>

              <tr>
                <td class="group_name">&nbsp;</td>
                <td class="group_name">
                	<input type="submit" class="buttons" value="确定添加" />　
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
