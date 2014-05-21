<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>monitor监控系统-日志查询</title>
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
            $("#time_from").datepicker({
                onSelect: function( selectedDate ) {
                    $( "#time_to" ).datepicker( "option", "minDate", selectedDate );
                }
            });
            $("#time_to").datepicker({
                onSelect: function( selectedDate ) {
                    $( "#time_from" ).datepicker( "option", "maxDate", selectedDate );
                }
            });
            getAlarmListOfGivenTimeAndType();
        });

        /*得到指定时间段和指定类型的告警信息列表*/
        function getAlarmListOfGivenTimeAndType(queryCon){
            var _url="${ctx}/logquery/getLogInfo";
            var queryData = "";
            if(!queryCon) {
                queryData = {};
            } else {
                queryData = queryCon;
            }
            //防止每次查询时，表格中的数据不断累积
            $("#thresholdList").html("");
            $("#thresholdList").Grid({
                type:"post",
                url : _url,
                dataType: "json",
                queryData: queryData,
                colDisplay: false,
                clickSelect: true,
                draggable:false,
                height: "auto",
                colums:[
                    {id:'1',text:'操作信息',name:"status",index:'1',align:'',width:'200', sort:"disable"},
                    {id:'2',text:'应用名称',name:"message",index:'1',align:''},
                    {id:'3',text:'用户名',name:"appName",index:'1',align:''},
                    {id:'4',text:'IP',name:"monitorType",index:'1',align:''},
                    {id:'5',text:'时间',name:"recordTime",index:'1',align:''}
                ],
                rowNum:10,
                pager : true,
                number:false
                <shiro:lacksPermission name='admin'>
                ,multiselect: false
                </shiro:lacksPermission>
            });
        }

        //根据查询条件查询日志信息
        function queryLogInfo() {
            //应用名称ID
            var appId = $("#app_name").val();
            //地区名称
            var areaName = $("#sel_area").val();
            //用户名
            var userName = $(".user_name").val();
            //ip
            var userIp = $(".user_ip").val();
            //开始时间
            var fromTime = $("#time_from").val();
            //结束时间
            var endTime = $("#time_to").val();
            var queryCon = {};
            queryCon.appId = appId;
            queryCon.areaName = areaName;
            queryCon.userName = userName;
            queryCon.userIp = userIp;
            queryCon.fromTime = fromTime;
            queryCon.endTime = endTime;
            getAlarmListOfGivenTimeAndType(queryCon);
        }

        //隐藏或显示更多查询条件
        function showOrHideMoreQuery(toggleEle) {
            if($(toggleEle).hasClass("hide_more")) {
                $(toggleEle).removeClass("hide_more").addClass("show_more");
                $(".up").removeClass("up").addClass("down");
                $(".query_condition").animate({
                    opacity: 'show'
                }, 200);
            } else {
                $(toggleEle).removeClass("show_more").addClass("hide_more");
                $(".down").removeClass("down").addClass("up");
                //用户名
                $(".user_name").val("");
                //ip
                $(".user_ip").val("");
                //开始时间
                $("#time_from").val("");
                //结束时间
                $("#time_to").val("");
                $(".query_condition").animate({
                    opacity: 'hide'
                }, 200);
            }
        }
    </script>
    <style type="text/css">
        .hide_more, .show_more{
            float: right;
            margin-right: 30px;
        }
        .query_condition{ display: none;}
        .hide_more .up{
            padding-right: 14px;
            background: url("${ctx}/global/images/glyphicons-halflings.png") no-repeat;
            background-position: -289px -119px;
        }
        .show_more .down{
            padding-right: 14px;
            background: url("${ctx}/global/images/glyphicons-halflings.png") no-repeat;
            background-position: -313px -119px;
        }
    </style>
</head>

<body>
<div id="layout_top">
    <div class="header">
        <%@include file="/WEB-INF/layouts/menu.jsp"%>
    </div>
</div>
<div id="layout_center" style="margin-top: -25px;">
    <div class="main" id="main">
        <div class="threshold_file alerts">
            <h2 class="title2" style="border-bottom: 1px solid #DFE9F2;">
                <b>日志信息列表　</b>
            </h2>
            <div style="background-color:#EDF2F7; padding: 10px; border-bottom: 1px solid #DFE9F2;">
                应用系统：
                <select id="app_name">
                    <option value="">选择应用名称</option>
                    <option value="1">应用一</option>
                    <option value="2">应用二</option>
                    <option value="3">应用三</option>
                    <option value="4">应用四</option>
                </select>
                <span style="padding-left: 50px;"></span>
                机构：
                <select id="sel_area">
                    <option value="">选择地区名称</option>
                    <option value="SD">山东</option>
                    <option value="BJ">北京</option>
                    <option value="SH">上海</option>
                </select>
                <span style="padding-left: 50px;"></span>
                <input type="button" value="查询" style="width:80px;" onclick="queryLogInfo();" />
                <a class="hide_more" href="javascript:void(0);" onclick="showOrHideMoreQuery(this);">
                    <span>更多</span>
                    <span class="more_query up"></span>
                </a>
                <div class="query_condition" style="margin-top: 10px;">
                    <table style="width: 100%">
                        <tr>
                            <td width="20%">&nbsp;&nbsp;&nbsp;&nbsp;用户名：<input class="user_name" type="text" /></td>
                            <td width="20%">IP：<input class="user_ip" type="text" /></td>
                            <td width="20%">时间从：<input id="time_from" type="text" /></td>
                            <td width="20%"> 到：<input id="time_to" type="text" /></td>
                            <td width="20%"></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="thresholdList"></div>
        </div>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
