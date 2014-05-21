<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <title>monitor监控系统-告警明细-详细信息</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/sinosoft.window.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/status.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/logDetail/logDetail.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var ctx = "${ctx}"; //"4028927e3d333c27013d333d15c00001"4028921c3d4d3220013d4edcdaee00be//
        var applicationId = "${applicationId}";
        var urlId = "${urlId}";
        var logId = "${logId}";
        var alarmId = "${alarmId}";
        var alarmDetailId = "${alarmDetailId}";
        var existLogId = "${existLogId}";
    </script>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
   	<script language="javascript" src="${ctx}/global/js/sinosoft.window.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.grid.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script language="javascript" src="${ctx}/global/js/application/manager/logDetail.js"></script>
    
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="log_detail" >
        <%--<ul class="crumbs">--%>
            <%--<li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>--%>
            <%--<li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>--%>
            <%--<li><a href="${ctx}/application/manager/detail/main/${applicationId}">应用透视</a> ></li>--%>
            <%--<li><a href="${ctx}/application/manager/url/main/${applicationId}/${urlId}">URL透视</a> ></li>--%>
            <%--<li><b>事件日志透视</b></li>--%>
        <%--</ul>--%>
        <ul class="crumbs">
            <li><a href="${ctx}/alarm/manager/alarmmanager/list">告警</a> > </li>
            <li><a href="${ctx}/alarm/manager/alarmmanager/list">告警信息列表</a> > </li>
            <li><a href="${ctx}/alarm/manager/alarmmanager/detail/${alarmDetailId}">告警明细</a> > </li>
            <li><b>详细信息</b></li>
        </ul>
        <div style="width:49%;float:left;">
            <div id="log_detail_grid" >
                <div class="threshold_file">
                    <div class="tool_bar_top">日志操作详细</div>
                    <table id="logDetail" class="log_detail_table" width="100%" cellpadding="0" cellspacing="0">
                    </table>
                </div>
            </div>
            <%--<div class="stuats_mark">
                <ul>
                    <li><div class="red_status"></div><div>严重</div></li>
                    <li><div class="yellow_status"></div><div>警告</div></li>
                    <li><div class="green_status"></div><div>正常</div></li>
                </ul>
            </div>--%>
        </div>
        <div style="width:49%;float:left; margin-left:5px;">
            <div class="threshold_file">
                <div class="tool_bar_top"> 参数信息</div>
                <div id="detail_grid"></div>
            </div>
        </div>
        <div style="float:left;width:100%;margin-top:20px;">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                异常信息
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li id="exceptionInfo"></li>
                    </ul>

                </div>
            </div>
        </div>
        <div style="float:left;width:100%;margin-top:20px;">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                告警信息
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li id="alarmInfo"></li>
                    </ul>

                </div>
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