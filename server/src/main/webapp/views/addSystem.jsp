<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:c="http://www.springframework.org/schema/beans">
<head>
    <title>monitor监控系统-新建监视器</title>
    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <script type="text/javascript">

        function save() {
            msgSuccess("系统消息", "操作成功，监视器已保存！");
        }
        /*校验数据*/
        function isValid(form) {
            String.prototype.trim = function(){
                return this.replace(/(^\s*|\s*$)/g,'');
            }
//            var appName="^[A-Za-z]+$";
//            if (!form.applicationName.value.match(appName)) {
//                msgAlert("系统消息", "显示名称必须是英文！");
//                return false;
//            }
            var appNameRegex = /^\w+$/;
            if (!appNameRegex.test(form.applicationName.value)) {
                msgAlert("系统消息", "显示名称必须是英文数字或下滑线！");
                return false;
            }
            if (form.cnName.value==null||form.cnName.value=="") {
                msgAlert("系统消息","中文名称不能为空！");
                return false;
            }
            var appIp="^[0-9.]+$";
            if(!form.applicationIp.value.match(appIp)){
                msgAlert("系统消息","主机IP地址必须是数字和\".\"的组合！");
                return false;
            }
            var appPort="^[0-9]+$";
            if(!form.applicationPort.value.match(appPort)||form.applicationPort.value.length>5){
                msgAlert("系统消息","端口必须是5位以内的数字！");
                return false;
            }            
            if(form.interval.value.trim()==""){
                msgAlert("系统消息","轮询间隔不能为空或者空格！");
                return false;
            }
            var appInterval="^[1-9][0-9]*$";
            if(!form.interval.value.match(appInterval)){
                msgAlert("系统消息","轮询间隔必须是大于0！");
                return false;
            }
            if(form.interval.value.length>10){
                msgAlert("系统消息","轮询间隔必须是大于0的数字！");
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
   	 <%--<ul class="crumbs">--%>
    		<%--<li>管理></li>--%>
            <%--<li><a href="${ctx}/addmonitor/list">新建应用</a>></li>--%>
            <%--<li><b>新建应用系统监视器</b></li>--%>
         <%--</ul>--%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <div class="add_monitor">
                        <%--<h2 class="title2"><b>新建监视器类型　</b>
                            <select name="" class="diySelect" onchange="top.location=this.value;">
                                <optgroup label="应用服务器">
                                    <option selected="selected" value="addSystem.html">应用系统　　</option>
                                </optgroup>
                                <optgroup label="数据库">
                                    <option value="addOracle.html">Oracle</option>
                                </optgroup>
                                <optgroup label="操作系统">
                                    <option value="addLinux.html">Linux</option>
                                </optgroup>
                            </select>
                        </h2>--%>
                        <%@include file="/WEB-INF/layouts/selectMonitorType.jsp"%>
                        <form:form id="addSystem" action="${ctx}/addmonitor/addapp" method="post"
                                   class="form-horizontal" onsubmit="return isValid(this);">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0"
                                   class="add_monitor_box add_form">
                                <tr>
                                    <td colspan="2" class="group_name">基本信息</td>
                                </tr>
                                <tr>
                                    <td width="25%">显示名称<span class="mandatory">*</span></td>
                                    <td><input id="applicationName" name="applicationName"
                                               value="${application.applicationName}" type="text" class="formtext required"/>
                                        <msg:errorMsg property="applicationName" type="message"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%">中文名称<span class="mandatory">*</span></td>
                                    <td><input id="cnName" name="cnName" value="${application.cnName}" type="text"
                                               class="formtext"/>
                                        <msg:errorMsg property="cnName" type="message"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>主机IP地址<span class="mandatory">*</span></td>
                                    <td><input id="applicationIp" name="applicationIp"
                                               value="${application.applicationIp}" type="text" class="formtext"
                                               size="30"/>
                                        <msg:errorMsg property="applicationIp" type="message"/>
                                    </td>
                                </tr>
                                <!--<tr>
                                  <td>子网掩码<span class="mandatory">*</span></td>
                                  <td><input name="input3" type="text" class="formtext" value="255.255.255.0" size="30" /></td>
                                </tr>-->
                                <tr>
                                    <td>端口<span class="mandatory">*</span></td>
                                    <td><input id="applicationPort" name="applicationPort"
                                               value="${application.applicationPort}" type="text" class="formtext"
                                               size="8"/>
                                        <msg:errorMsg property="applicationPort" type="message"/>
                                    </td>
                                </tr>
                                <tr style="display: none;">
                                    <td>轮询间隔（分钟）<span class="mandatory">*</span></td>
                                    <%--<td><input id="interval" name="interval" value="${application.interval}" type="text"--%>
                                               <%--class="formtext" size="10"/>--%>
                                    <td>
                                        <input id="interval" name="interval" value="10" type="text"
                                               class="formtext" size="10"/>
                                        <msg:errorMsg property="interval" type="message"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="group_name">&nbsp;</td>
                                    <td class="group_name">
                                        <!--<input type="button" class="buttons" value="确定添加" onclick="save()" />　-->
                                        <input id="submit" type="submit" class="buttons" value="确定添加"/>　
                                        <input type="reset" class="buttons" value="重 置"/>　
                                        <input type="button" class="buttons" value="取 消"
                                               onclick="window.history.back()"/>
                                    </td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                </td>
                <td width="15">&nbsp;</td>
                <td width="33%" style="vertical-align:top">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
