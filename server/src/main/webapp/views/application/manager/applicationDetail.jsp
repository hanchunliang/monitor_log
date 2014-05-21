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
    <title>monitor监控系统-应用透视</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/apmservice/apmservice.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var ctx = "${ctx}";
        var applicationId = "${applicationId}"; 
    </script>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script language="javascript" src="${ctx}/global/js/mvc-pipe.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.grid.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script language="javascript" src="${ctx}/global/js/highcharts.src.js"></script>
 	<script language="javascript" src="${ctx}/global/js/apmservice/apmservice.js"></script> 
    <script type="text/javascript">
    $(function() {
   	 	$.ajax({
        	type:"Post",
        	async :true,
        	url:"${ctx}/application/manager/detail/alarm/${applicationId}" ,
        	success:function (data) {
           		$("#alarm").html(data);
        	}
    	})
    	$.ajax({
        	type:"Post",
        	async :true,
        	url:"${ctx}/application/manager/detail/pie/${applicationId}" ,
        	success:function (data) {
           		$("#pie").html(data);
        	}
    	})
    	
    });  
    
      function urlDetail(applicationId, urlId) {
            location.href = "${ctx}/application/manager/url/main/" + applicationId + "/" + urlId;
        } 
    </script>

</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="service_all" id="main">
        <ul class="crumbs">
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>
            <li><b>应用透视</b></li>
        </ul>
        <table cellpadding="0" cellspacing="0" width="100%" >
            <tbody>
            <tr>
                <td class="health" id="alarm" style="width: 537px;height: 300">
					
                </td>
                <td class="pie_chart" id="pie"style="width: 537px;height: 300">

                </td>
            </tr>
            </tbody>
        </table>
        <div class="grid_info">
            <div class="threshold_file">

                <div class="tool_bar_top">
                    URL地址信息
                </div>
                <div id="grid_info_table"></div>

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
<%-- <mvcpipe:writes>
    <mvcpipe:write id = "alarmId" lazyLoad = "false" targetId="alarm" />
    <mvcpipe:write id = "pieId" lazyLoad = "false" targetId="pie" />
</mvcpipe:writes> --%>
