<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <title>monitor监控系统-用户管理</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/sinosoft.message.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script language="javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.message.js"></script>
    <script type="text/javascript">
        $(function () {
            $("body").layout({
                top: {topHeight: 100},
                bottom: {bottomHeight: 30}
            });
            saveUserForm

            $('#saveUserForm').ajaxForm({
                dataType: "json",
                success: function(data) {
                    if(data.status == "success") {
                        msgSuccess("系统消息", "用户保存成功！", function() {
                            location.href = "${ctx}/account/user/list";
                        });
                    }else{
                    	 msgFailed("系统消息",data.message);
                    }
                   
                   /*  if(data.status == "success") {
                        msgSuccess("系统消息", "用户保存成功！", function() {
                            location.href = "${ctx}/account/user/list";
                        });
                    } */
                },
                error: function() {
                	msgFailed("系统消息", "用户保存失败！");
                }
            });

        });

        function toSaveUser() {
            var loginName = $("#loginName").val();
            if(!loginName || loginName == "") {
                msgAlert("系统消息", "用户名不能为空");
                return;
            }
            var password = $("#password").val();
            if(!password || password == "") {
                msgAlert("系统消息", "密码不能为空");
                return;
            }

            var passwordConfirm = $("#passwordConfirm").val();
            if(!passwordConfirm || passwordConfirm == "" ) {
                msgAlert("系统消息", "确认密码不能为空");
                return;
            }
            if(password != passwordConfirm) {
                msgAlert("系统消息", "密码与确认密码不一致");
                return;
            }

            var name = $("#normalStatus").val();
            if(!$("#normalStatus").attr("checked") && !$("#lockStatus").attr("checked")) {
                msgAlert("系统消息", "状态必须选择");
                return;
            }

            $("#saveUserForm").submit();
        }

    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main">
    	<ul class="crumbs">
            <li>用户管理></li>
            <li><b>新增用户</b></li>
        </ul>
        <div class="add_monitor user_manager">
            <h2 class="title2"><strong class="right"><a href="${ctx}/account/user/list">返回用户列表</a></strong><b>用户信息</b>
            </h2>

            <form action="${ctx}/account/user/save" method="post" id="saveUserForm">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
                    <input type="hidden" name="id" value="${user.id }"/>
                    <tr>
                        <td colspan="2" class="group_name">用户信息</td>
                    </tr>
                    <tr>
                        <td>用户名<span class="mandatory">*</span></td>
                        <td><input name="loginName" id="loginName" type="text" class="formtext" value="${user.loginName }"/></td>
                    </tr>
                    <tr>
                        <td>密码<span class="mandatory">*</span></td>
                        <td><input name="password" id="password" type="password" class="formtext" value="${user.password }"/></td>
                    </tr>
                    <tr>
                        <td>确认密码<span class="mandatory">*</span></td>
                        <td><input name="passwordConfirm" id="passwordConfirm" type="password" class="formtext" value="${user.password }"/></td>
                    </tr>
                    <tr>
                        <td>状态<span class="mandatory">*</span></td>
                        <td><input id="normalStatus" name="status" type="radio"
                                   value="1" ${user.status eq "1"  ? "checked='checked'" : ''} />
                            <label for="status">正常</label>
                            　 <input id="lockStatus" type="radio" name="status"
                                     value="0"  ${user.status eq "0"  ? "checked='checked'" : ''} />
                            <label for="status">锁定</label></td>
                    </tr>
                    <tr>
                        <td>用户级别<span class="mandatory">*</span></td>
                        <td><input id="userlevel" name="authflag" type="radio"
                                   value="1" ${user.authflag eq "1"  ? "checked='checked'" : ''} />
                            <label for="authflag">普通用户</label>
                            　 <input id="adminlevel" type="radio" name="authflag"
                                     value="0"  ${user.authflag eq "0"  ? "checked='checked'" : ''} />
                            <label for="authflag">管理员</label></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="group_name">基本信息</td>
                    </tr>
                    <tr>
                        <td width="25%">姓名</td>
                        <td><input name="name" id="name" value="${user.name }" type="text" class="formtext"/></td>
                    </tr>
                    <tr>
                        <td>手机号</td>
                        <td><input name="phone" id="phone" type="text" class="formtext" value="${user.phone }"/></td>
                    </tr>
                    <tr>
                        <td>邮箱</td>
                        <td><input name="email" id="email" type="text" class="formtext" value="${user.email }"/></td>
                    </tr>

                    <tr>
                        <td class="group_name">&nbsp;</td>
                        <td class="group_name">
                            <input type="button" class="buttons" value="确定" onclick="toSaveUser()"/>　
                            <input type="reset" class="buttons" value="重 置"/>　
                            <input type="button" class="buttons" value="取 消" onclick="window.history.back()"/>
                        </td>
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
