<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统-orale监控</title>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<link href="${ctx }/global/css/sinosoft.tabs.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx }/global/js/sinosoft.tabs.js"></script>
<script language="javascript" src="${ctx }/global/js/highcharts.src.js"></script>
<script language="javascript" src="${ctx }/global/js/oracleMonitor.js"></script>
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
	$("#avaInfoStyle").bind("change", {}, avaInfoList);
	$("#avaInfoStyle").trigger("change");
	
	$.ajax({
		type:"get",
		dataType: "json",
		url : rootPath+"/db/oracle/performance",  
		success:function(data) {
			$(data).each(function(i, d){
				buildHighchart(d);
			});
		}
	});
	
	$("#healthListStyle").bind("change", {}, healthList);
	$("#healthListStyle").trigger("change");
	
	$("#thresholdList").Grid({
		url : rootPath+"/db/oracle/thresholdList",  
		dataType: "json",
		colDisplay: false,  
		clickSelect: true,
		draggable:false,
		height: 'auto',  
		colums: [  
		 		{id:'1',text:'名称',name:"appellation",index:'1',align:''},
				{id:'2',text:'可用性',name:"appellation",index:'1',align:''},
				{id:'3',text:'健康状态',name:"appellation",index:'1',align:''},
				<shiro:hasPermission name="admin">
				{id:'4',text:'操作',name:"appellation",index:'1',align:''}
				</shiro:hasPermission>
			],  
		rowNum:9999,
		pager : false,
		number:false,  
		<shiro:lacksPermission name='admin'>
		multiselect: false 
		</shiro:lacksPermission>
	});
	
	$("#tabs").tabs({closeTab:false});
	$("#myDesk").height($("#layout_center").height());
	$("#nav").delegate('li', 'mouseover mouseout', navHover);
	$("#nav,#menu").delegate('li', 'click', navClick);
	
});
function wrapP(){
	$(".normal,.warn,.critical").wrapInner("<p style='display:none'></p>");
	$(".normal,.warn,.critical").each(function(){
		$(this).attr("title",$(this).find("p").text()).css("cursor","pointer");
	});
}
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
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
	<div class="main" id="main">
    	<ul class="crumbs">
        	<li><a href="#">监视器</a> ></li>
            <li><b> oracle - 批量配置视图</b></li>
        </ul>
        <hr class="top_border" />
        <div id="tabs">
        	<ul>
            	<li class="tabs_select">可用性</li>
                <li>性能</li>
                <li>列表视图</li>
            </ul>
            <div><br />
            	<div class="threshold_file availability">
                  <h2 class="title2"><b>可用性历史纪录- oracle　</b>
                  	<select id="avaInfoStyle" name="" class="diySelect">
                            <option value="1">最近24小时</option>
                            <%--<option value="30">最近30天</option>--%>
                    </select>
                  </h2>
                  <div id="avaInfoList"></div>
                    <div class="explain">
                    	<ul>
                        	<li><span class="ex_no"></span>不可用</li>
                            <li><span class="ex_is"></span>可用</li>
                            <li><span class="ex_xp"></span>未知</li>
                        </ul>
                    	查看监视器/业务组最近24小时或30天的可用性状态
                    </div>
                </div>
            </div>
            <div>
            	<br />
                <div class="threshold_file">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="my_table">
                      <tr>
                        <td class="group_name"><h3><strong>缓冲区击中率</strong></h3></td>
                        <td class="group_name"><h3><strong>连接时间</strong></h3></td>
                      </tr>
                      <tr>
                        <td width="50%"><div id="memory_utilization"></div></td>
                        <td width="50%"><div id="exchange_utilization"></div></td>
                      </tr>
                      <tr>
                        <td class="group_name"><h3><strong>活动的用户连接数</strong></h3></td>
                        <td class="group_name"><!-- <h3><strong>活动的远程连接数</strong></h3> --></td>
                      </tr>
                      <tr>
                        <td><div id="reply_utilization"></div></td>
                        <td><!-- div id="CPU_utilization"></div --></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                	<div class="threshold_file availability">
                	  <h2 class="title2"><b> 健康状态操控板</b>
                	    <select id="healthListStyle" name="select" class="diySelect">
                	      <option value="1">最近24小时</option>
                	      <option value="30">最近30天</option>
              	      </select>
              	    </h2>
                	  <div id="healthList"></div>
                	  <div class="explain">
                	    <ul>
                          <li><span class="ex_is"></span>正常</li>
                          <li><span class="ex_warn"></span>警告</li>
                	      <li><span class="ex_no"></span>严重</li>
                	      <li><span class="ex_xp"></span>未知</li>
              	      </ul>
                	     查看监视器/业务组过去24小时或30天的健康状态告警</div>
              	  </div>
                </div>
            </div>
            <div>
            	<br />
                <div class="threshold_file">
                  <h2 class="title2"><b>数据库列表视图　</b></h2>
                  <div class="tool_bar_top">
                  <shiro:hasPermission name="admin">
                  <a href="javascript:void(0);" class="batch_del" onclick="batchDel()">批量删除</a>
                  </shiro:hasPermission>
                  </div>
                  <div id="thresholdList"></div>
                  <div class="tool_bar"></div>
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
