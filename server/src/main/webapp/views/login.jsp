<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="${ctx}/global/css/login.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script type="text/javascript">
        $(function(){
            var msg = $("#msg").text();
//            alert(msg);
            if(msg=="-1"){
                $(".tip").css("visibility","visible");
            } else if(msg=="0"){
                $(".tip").css("visibility","visible").html("用户被锁定，请联系管理员");
            }
        });

        function trigger()
        {
            if($(".side_top input:text").val()==""){
                $(".tip").css("visibility","visible").html("用户名不能为空")
                $(".side_top input:text").focus();
                return false;
            }
            else if($(".side_top input:password").val()==""){
                $(".tip").css("visibility","visible").html("密码不能为空")
                $(".side_top input:password").focus();
                return false;
            }
            document.loginform.submit();
        }
    </script>
</head>
<body class="login_bg">
<div class="login">
    <div class="content">
        <div class="login_side fr">
            <div class="logo fl"></div>
            <form action="" method="post"  name="loginform">
                <div class="side_left fl"></div>
                <div class="side_middle fl">
                    <ul>
                        <li class="tip" style="visibility:hidden;width: 200px">用户名或密码无效或账户被锁定</li>
                        <li class="side_top"><strong>用户名：</strong><span class="input_bg">

                            <input id="loginName" name="loginName" type="text" value=""></span></li>
                        <%--<input id="msg" name="msg" type="textarea" value="${msg}" style="display: none;">--%>
                        <label id="msg" style="display: none;">${msg}</label>
                        <li class="side_top"><strong>密码：</strong><span class="input_bg"><input id="password" name="password" type="password" value=""></span></li>
                        <li class="side_check">&nbsp;<%--<input type="checkbox" name="" value=""><strong>记住密码</strong>--%></li>
                        <li><a href="#" class="btn" onclick="trigger()">登录</a></li><p class="clear"></p>
                    </ul>

                </div>
                <div class="side_right fl"></div>
            </form>
            <p class="clear"></p>
        </div>
    </div>
</div>
</body>
</html>