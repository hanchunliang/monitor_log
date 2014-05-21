<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath }" var="ctx"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>monitor监控系统</title>
    <%@ include file="/WEB-INF/layouts/base.jsp" %>
    <link href="${ctx }/global/css/sinosoft.tabs.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript">
        var ctx = '${ctx}';
        var monitorId = "${monitorId}";
        $(function () {
            $("body").layout({
                top:{topHeight:100},
                bottom:{bottomHeight:30}
            });
            if($.browser.msie && ($.browser.version == "7.0")){
        		var center = $("#layout_center");
        		$("#main").width(center.width() - 31).height(center.height() - 30);
        	}
            $("#myDesk").height($("#layout_center").height());
            $("#nav").delegate('li', 'mouseover mouseout', navHover);
            $("#nav,#menu").delegate('li', 'click', navClick);

            $("#sevenday_grid").Grid({
                url:ctx + "/db/oracle/sta/viewTable/"+monitorId+"/2/2/2",
                dataType:"json",
                height:'auto',
                colums:[
                    {id:'1', text:'日期', name:"methodName", width:'', index:'1', align:'', color:''},
                    {id:'2', text:'时间', name:"maxTime", width:'300', index:'1', align:'', color:''},
                    {id:'3', text:'最小值  ', name:"minTime", width:'', index:'1', align:'', color:''},
                    {id:'4', text:'最大值 ', name:"avgTime", width:'', index:'1', align:'', color:''},
                    {id:'5', text:'每小时平均值 ', name:"status", width:'', index:'1', align:'', color:''}
                ],
                rowNum:10,
                rowList:[10, 20, 30],
                pager:false,
                number:false,
                multiselect:false
            });
        });
        $(

                function () {
                    getSevenDayGraph();
                }
        )
        function getSevenDayGraph() {
            var _data ;
            $.ajax({
                url:ctx+"/db/oracle/sta/viewGrid/"+monitorId+"/2/2/1",
                dataType:"json",
                cache:false,
                async:false,
                success:function (return_data) {
                    //refreshChart2(_data["xaxis"], _data["connectSeries"], "last_sevenday", "连接时间ms", "时间", "ms");
                     _data = return_data;
                }
            });
            var chart;
            $(document).ready(function () {
                chart = new Highcharts.Chart({
                    chart:{
                        renderTo:'last_sevenday',
                        type:'line',
                        height:200
                    },
                    title:{
                        text:''
                    },
                    subtitle:{
                        text:''
                    },
                    xAxis:_data["xAxis"],
                    yAxis:{
                        title:{
                            text:'用户数 '
                        }


                    },
                    tooltip:{
                        enabled:false,
                        formatter:function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                    this.x + ': ' + this.y;
                        }
                    },
                    plotOptions:{
                        line:{
                            dataLabels:{
                                enabled:true
                            },
                            enableMouseTracking:false,
                            marker:{
                                enabled:false
                            }
                        }
                    },
                    credits:{
                        text:'',
                        href:''
                    },
                    series: _data["series"],
                    colors:['#00b200', '#0000b2', '#b200b2']
                });
            })
        }
        function navHover() {
            $(this).toggleClass("hover")
        }
        function navClick() {
            $(this).addClass("seleck").siblings().removeClass("seleck");
            if ($(this).hasClass('has_sub')) {
                var subMav = $(this).children("ul.add_sub_menu");
                var isAdd = false;
                if ($(this).parent().attr("id") == "menu") {
                    isAdd = true;
                }
                ;
                subMav.slideDown('fast', function () {
                    $(document).bind('click', {dom:subMav, add:isAdd}, hideNav);
                    return false;
                });
            }
            ;
        }
        function hideNav(e) {
            var subMenu = e.data.dom;
            var isAdd = e.data.add;
            subMenu.slideUp('fast', function () {
                if (isAdd) {
                    subMenu.parent().removeClass('seleck');
                }
                ;
            });
            $(document).unbind();
        }
    </script>
</head>

<body>
<div id="layout_center">
    <div class="main" style="padding-bottom:60px;" id="main">
        <div class="threshold_file">
            <div class="sub_title">最近30天的用户连接数</div>

            <table class="base_info" width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td>监视器名称</td>
                    <td>${osdm.monitorName}</td>
                </tr>
                <tr>
                    <td>属性</td>
                    <td>连接时间 ms</td>
                </tr>
                <tr>
                    <td>从</td>
                    <td>${osdm.begin}</td>
                </tr>
                <tr>
                    <td>到</td>
                    <td> ${osdm.end}</td>
                </tr>

                <tr>
                    <td colspan="2">
                        <div class="days_data">
                            <a href="1">
                                <div class="seven_days"></div>
                            </a>
                            <a>
                                <div class="seven_days_unable"></div>
                            </a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="last_sevenday"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="buttom_mark">
                            <div>连接时间:</div>
                            <div>1</div>
                            <div>最小平均值</div>
                            <div>${osdm.minAvg}</div>
                            <div>最大平均值:</div>
                            <div>${osdm.maxAvg}</div>
                            <div>平均:</div>
                            <div>${osdm.avg}</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="buttom_mark">
                            <%--<div>阈值明细:</div>--%>
                            <%--<div>2</div>--%>
                            <%--<div>严重:</div>--%>
                            <%--<div> > ${osdm.error } </div>--%>
                            <%--<div>警告:</div>--%>
                            <%--<div> > ${osdm.warn }</div>--%>
                            <%--<div>正常:</div>--%>
                            <%--<div> <= ${osdm.normal }</div>--%>
                        </div>
                    </td>
                </tr>
            </table>

        </div>

        <div class="threshold_file">
            <div class="sub_title">用户数</div>
            <div id="sevenday_grid"></div>
        </div>
    </div>
</div>
</body>
</html>
