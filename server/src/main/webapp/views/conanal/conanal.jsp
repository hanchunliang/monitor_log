<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>分析</title>
    <link href="${ctx}/global/css/sinosoft.core.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/global/css/sinosoft.datepicker.theme.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/global/css/sinosoft.theme.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/global/css/OneDatapicker.css" rel="stylesheet" type="text/css">

    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <script language="javascript" src="${ctx}/global/js/sinosoft.core.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.widget.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.datepicker.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.datepicker-zh-CN.js"></script>
    <script type="text/javascript">
        $(function(){
            $("body").layout({
                top:{topHeight:100},
                bottom:{bottomHeight:30}
            });
            if($.browser.msie && ($.browser.version == "7.0")){
                var center = $("#layout_center");
                $("#main").width(center.width() - 31).height(center.height() - 30);
            }
        });

        //导出报表
        function exportReport() {
            <%--$.ajax({--%>
                <%--type: "POST",--%>
                <%--url: "${ctx}/conanal/exportTable",--%>
                <%--data: "name=John&location=Boston",--%>
                <%--success: function(msg){--%>
                    <%--alert( "Data Saved: " + msg );--%>
                <%--}--%>
            <%--});--%>
            window.location.href = "${ctx}/conanal/exportTable";
        }
    </script>
    <style type="text/css">
        #layout_bottom{ margin-bottom: -20px;}
        .container{ height: 100%; width:100%;}
        .left_con{ height: 100%; width: 20%; border-right: 1px solid #468ED5; float: left;}
        .right_con{ height: 100%; width: 78%; float: left;}
        .left_menu{ height: 32px; line-height: 32px; text-align: center; font-size: 16px;}
    </style>
</head>

<body>
<div id="layout_top">
    <div class="header">
        <%@include file="/WEB-INF/layouts/menu.jsp"%>
    </div>
</div>
<div id="layout_center" style="margin-top: -25px;">
    <div class="container">
        <div class="left_con">
            <div class="left_menu"><a href="javascript:void(0);">A型报表</a></div>
            <div class="left_menu"><a href="javascript:void(0);">B型报表</a></div>
        </div>
        <div class="right_con">
            <span style="padding-left: 20px;"></span>
            <select class="app_sys">
                <option value="0">请选择应用系统</option>
                <option value="1">应用系统一</option>
                <option value="2">应用系统二</option>
                <option value="3">应用系统三</option>
            </select>
            <span style="padding-left: 20px;"></span>
            <select class="area_sel">
                <option value="0">请选择机构</option>
                <option value="1">山东</option>
                <option value="2">北京</option>
                <option value="3">上海</option>
            </select>
            <span style="padding-left: 20px;"></span>
            时间从：<input id="time_from" type="text" />
            <span style="padding-left: 20px;"></span>
            到：<input id="time_to" type="text" />
            <span style="padding-left: 20px;"></span>
            <input type="button" value="报表导出" onclick="exportReport();" />
        </div>
        <div style="clear: both;"></div>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
