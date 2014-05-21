<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统-orale监控</title>
<link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/oracle.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/sinosoft.tabs.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/sinosoft.window.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var monitorId = "${monitorId}";
</script>
<script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.grid.js"></script>
<script language="javascript" src="${ctx}/global/js/highcharts.src.js"></script>
<script language="javascript" src="${ctx}/global/js/highcharts-more.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.tabs.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.window.js"></script>
<script language="javascript" src="${ctx}/global/js/oracle.js"></script>
<script type="text/javascript">
var ctx = '${ctx}';
$(function(){
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

	$("#table_space_detail").Grid({
		url : ctx+"/db/oracle/home/viewTableSpace/${monitorId}",
		dataType: "json",
		height: 'auto',
		colums:[
			{id:'1',text:'名称',name:"methodName",width:'',index:'1',align:'',color:''},
			{id:'2',text:'表空间使用情况',name:"maxTime",width:'300',index:'1',align:'',color:''},
			{id:'3',text:'总空间大小(MB) ',name:"minTime",width:'',index:'1',align:'',color:''},
			{id:'4',text:'总块数',name:"avgTime",width:'',index:'1',align:'',color:''},
			{id:'5',text:'已使用(MB) ',name:"status",width:'',index:'1',align:'',color:''},
            {id:'6',text:'已用百分比(%) ',name:"status",width:'',index:'1',align:'',color:''},
            {id:'7',text:'未使用(MB) ',name:"status",width:'',index:'1',align:'',color:''},
			{id:'8',text:'可用百分比(%)',name:"status",width:'',index:'1',align:'',color:''}
		],
		rowNum:10,
		rowList:[10,20,30],
		pager : false,
		number:false,
		multiselect:false
	});
    $("#table_space_overview").Grid({
        url : ctx+"/db/oracle/home/viewTableSpaceOverPreview/${monitorId}",
        dataType: "json",
        height: 'auto',
        colums:[
            {id:'1',text:'名称',name:"methodName",width:'',index:'1',align:'',color:''},
            {id:'7',text:'未使用(MB) ',name:"status",width:'',index:'1',align:'',color:''},
            {id:'8',text:'可用百分比(%)',name:"status",width:'',index:'1',align:'',color:''}
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager : false,
        number:false,
        multiselect:false
    });


//	$("#table_space_status").Grid({
//		url : "oracle2.json",
//		dataType: "json",
//		height: 'auto',
//		colums:[
//			{id:'1',text:'名称',name:"methodName",width:'',index:'1',align:'',color:''},
//			{id:'2',text:'状态',name:"maxTime",width:'300',index:'1',align:'',color:''},
//			{id:'3',text:' 	数据文件 ',name:"minTime",width:'',index:'1',align:'',color:''},
//			{id:'4',text:'读次数/分 ',name:"avgTime",width:'',index:'1',align:'',color:''},
//			{id:'5',text:'写次数/分',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'6',text:'读时间 ',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'7',text:'写时间 ',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'8',text:'健康状况',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'9',text:'告警配置',name:"status",width:'',index:'1',align:'right',color:''}
//		],
//		rowNum:10,
//		rowList:[10,20,30],
//		pager : false,
//		number:false,
//		multiselect:true
//	});

//	$("#table_space_performance").Grid({
//		url : "oracle1.json",
//		dataType: "json",
//		height: 'auto',
//		colums:[
//			{id:'1',text:'数据文件名 ',name:"methodName",width:'',index:'1',align:'',color:''},
//			{id:'2',text:'表空间名  ',name:"maxTime",width:'300',index:'1',align:'',color:''},
//			{id:'3',text:'状态',name:"minTime",width:'',index:'1',align:'',color:''},
//			{id:'4',text:'自动扩展',name:"avgTime",width:'',index:'1',align:'',color:''},
//			{id:'5',text:'数据文件大小 (MB)',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'6',text:'读次数 ',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'7',text:'写次数  ',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'8',text:'平均读时间 (ms)  ',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'9',text:'健康状况',name:"status",width:'',index:'1',align:'',color:''},
//			{id:'10',text:'告警配置',name:"status",width:'',index:'1',align:'',color:''}
//		],
//		rowNum:10,
//		rowList:[10,20,30],
//		pager : false,
//		number:false,
//		multiselect:true
//	});

    <%--由于SGA信息的查询依赖数据库版本，故先注释掉sga的相关信息--%>
		<%--$("#sga_detail").Grid({--%>
		<%--url :  ctx+"/db/oracle/home/viewSGADetail/${monitorId}",--%>
		<%--dataType: "json",--%>
		<%--height: 'auto',--%>
		<%--colums:[--%>
			<%--{id:'1',text:'属性 ',name:"methodName",width:'',align:'',color:''},--%>
			<%--{id:'2',text:'值  ',name:"maxTime",width:'300',align:'',color:''},--%>
			<%--{id:'3',text:'阈值',name:"minTime",width:'',align:'',color:''}--%>
		<%--],--%>
		<%--rowNum:10,--%>
		<%--rowList:[10,20,30],--%>
		<%--pager : false,--%>
		<%--number:false,--%>
		<%--multiselect:false--%>
	<%--});--%>

	$("#sga_status").Grid({
		url : ctx+"/db/oracle/home/viewSGAStatus/${monitorId}",
		dataType: "json",
		height: 'auto',
		colums:[
			{id:'1',text:'属性 ',name:"methodName",width:'',index:'1',align:'',color:''},
			{id:'2',text:'值  ',name:"maxTime",width:'300',index:'1',align:'',color:''},
			{id:'3',text:'阈值',name:"minTime",width:'',index:'1',align:'',color:''}
		],
		rowNum:10,
		rowList:[10,20,30],
		pager : false,
		number:false,
		multiselect:false
	});
	$("#tabs").tabs({closeTab:false});
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

function createSevenDayConnect() {
	var temWin = $("body").window({
			"id":"testOne9",
			"title":"连接时间 ",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/1/1/1",
			"hasIFrame":true,
			"width":850,
			"height":440,
			"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}
function createThirtyDayConnect() {
	var temWin = $("body").window({
			"id":"testOne8",
			"title":"连接时间 ",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/1/1/2",
			"hasIFrame":true,
			"width":850,
			"height":440,
		"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}
function createSevenDayUser() {
	var temWin = $("body").window({
			"id":"testOne11",
			"title":"用户数   ",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/2/2/1",
			"hasIFrame":true,
			"width":850,
			"height":440,
		"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}
function createThirtyDayUser() {
	var temWin = $("body").window({
			"id":"testOne10",
			"title":"用户数   ",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/2/2/2",
			"hasIFrame":true,
			"width":850,
			"height":440,
		"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}
function createSevenDayHitRate() {
	var temWin = $("body").window({
			"id":"testOne7",
			"title":"缓冲器击中率 ",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/3/3/1",
			"hasIFrame":true,
			"width":850,
			"height":440,
		"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}
function createThirtyDayHitRate() {
	var temWin = $("body").window({
			"id":"testOne6",
			"title":"缓冲器击中率",
			"url":ctx+"/db/oracle/sta/view/${monitorId}/3/3/2",
			"hasIFrame":true,
			"width":850,
			"height":440,
		"diyButton":[{
			"id": "btOne",
			"btClass": "buttons",
			"value": "关闭",
			"onclickEvent" : "selectLear",
			"btFun": function() {
					temWin.closeWin();
				}
			}
		]
	});
}

</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
	<div class="main" id="main">
  <ul class="crumbs">
      <li><a href="#">管理</a> </li>
      <li><b>配置告警</b></li>
    </ul>
     <hr class="top_border" />
     <div id="tabs">
        	<ul>
            	<li class="tabs_select">概览</li>
                <li>表空间</li>
                <li>SGA</li>
            </ul>
  	 <br />
    
    
    <div id="db_overview" >
  	<table class="main_table" width="100%" cellpadding="0" cellspacing="0" >
      <tr >
        <td style="vertical-align:top" width="50%">
          <div class="threshold_file" style="width:100%">
            <div class="sub_title">基本信息</div>
            <table class="base_info" width="100%" cellpadding="0" cellspacing="0" >
              <tr><td>名称</td><td>${oracleInfoModel.monitorName }</td></tr>
              <%--<tr><td>健康状况</td><td>${oracleInfoModel.health[2] }</td></tr>--%>
              <tr><td>类型</td><td>${oracleInfoModel.monitorType }</td></tr>
              <tr><td>Oracle版本</td><td>${oracleInfoModel.version }</td></tr>
              <tr><td>Oracle启动时间</td><td>${oracleInfoModel.startTime }</td></tr>
              <tr><td>端口</td><td>${oracleInfoModel.port }</td></tr>
              <tr><td>主机名</td><td>${oracleInfoModel.hostName }</td></tr>
              <tr><td>操作系统</td><td>${oracleInfoModel.os }</td></tr>
              <tr><td>最后轮询时间</td><td>${oracleInfoModel.lastExecTime }</td></tr>
              <tr><td>下次轮询时间</td><td>${oracleInfoModel.nextExecTime }</td></tr>
            </table>
          </div>  
        </td>
        <td style="vertical-align:top" width="50%">
          <div class="threshold_file"  style="width:100%">
            <div class="sub_title">今日可用性</div>
            <div class="days_data"><!--
              <a href="#"><div class="thirty_days"></div></a>
            	<a href="#"><div class="seven_days"></div></a>
            --></div>
            <div id="day_available" ></div>
             <!--<a href="#" ><div class="tool_bar_bottom"><div class="warn_set">警告配置</div></div></a>
          --></div>
        </td>
      </tr>
      <tr>
      	<td style="vertical-align:top" >
      		 <div class="threshold_file">
            <div class="sub_title">连接时间图 - 最后1小时</div>
            <div class="days_data">
              <a onclick="createThirtyDayConnect()"><div class="thirty_days"></div></a>
            	<a onclick="createSevenDayConnect()"><div class="seven_days"></div></a>
            </div>
            <div id="last_onehour" ></div>
          </div>
          <div class="threshold_file">
            	<table class="last_onehour_table" cellpadding="0" cellspacing="0">
              	<tr><th>属性</th><th>值</th>
                      <%--<th>阈值</th>--%>
                  </tr>
                <tr><td>连接时间</td><td>${oracleDetailModel.connectTime }ms</td>
                    <%--<td></td>--%>
                </tr><!--
                <tr><td colspan="3"><a href="#" ><div class="warn_set">警告配置</div></a></td></tr>
              --></table>
         	</div>
         
      	</td>
        <td style="vertical-align:top" >
        	 <div class="threshold_file">
            <div class="sub_title">用户活动性 - 最后1小时</div>
            <div class="days_data">
               <a onclick="createThirtyDayUser()"><div class="thirty_days"></div></a>
            	<a onclick="createSevenDayUser()"><div class="seven_days"></div></a>
            	<div style="clear:both"></div>
            </div>
            <div id="user_last_onehour" ></div>
          </div>
           <div class="threshold_file">
            	<table class="last_onehour_table" cellpadding="0" cellspacing="0">
              	<tr><th>属性</th><th>值</th>
                      <%--<th>阈值</th>--%>
                  </tr>
                <tr><td>用户数</td><td>${oracleDetailModel.activeCount }</td>
                    <%--<td></td>--%>
                </tr><!--
                  <tr><td colspan="3"><a href="#" ><div class="warn_set">警告配置</div></a></td></tr>
              --></table>
         	</div>
        </td>
      </tr>
      <tr>
      	<td colspan="2">
        	<div class="threshold_file">
              	<div class="sub_title">
                	最少可用字节的表空间
                </div>
                <div id="table_space_overview"></div>
         	</div>
        </td>
      </tr>
			<tr>
      	<td colspan="2">
      		<div class="threshold_file">
            	<table class="last_onehour_table" cellpadding="0" cellspacing="0">
              	<div class="sub_title">数据库明细</div>
                <tr class="samll_th"><td>属性</td><td>值</td></tr>
                <tr><td>数据库创建时间 </td><td> ${oracleDetailModel.dbCreateTime }</td></tr>
                <tr><td>Open模式</td><td>${oracleDetailModel.openType }</td></tr>
                <tr><td>Log模式 </td><td>${oracleDetailModel.logType }</td></tr>
              </table>
         	</div>
      	</td>
      </tr>
      <tr>
      	<td style="vertical-align:top;"width="50%">
        	<div class="threshold_file">
            <div class="sub_title">击中率</div>
            <div class="days_data">
               <a onclick="createThirtyDayHitRate()"><div class="thirty_days"></div></a>
               <a onclick="createSevenDayHitRate()"><div class="seven_days"></div></a>
            </div>
            	<div class="hit_rate">
              	<div style="width:100%;">
                	<div class="hit_rate_head">缓冲器</div>
                </div>
                <div  class="hit_rate_c_l"></div>
                <div class="hit_rate_c">
                	<div class="hit_rate_c_t">
                  	<div class="hit_rate_c_m">
                    	<div class="hit_rate_c_m_m"></div>
                    </div>
                    <div  class="hit_rate_c_m_b" >${sgaStateModel.bufferHitRate}%</div>
                  </div>
                </div>
                <div class="hit_rate_c_r"></div>
                <div style="clear:both"></div>
                <div class="hit_rate_b_l">&nbsp;</div>
                <div class="hit_rate_b"></div>
                <div class="hit_rate_b_r" >&nbsp;</div>
              </div>
              <div class="hit_rate">
              	<div style="width:100%;">
                	<div class="hit_rate_head">数据字典</div>
                </div>
                <div  class="hit_rate_c_l"></div>
                <div class="hit_rate_c">
                	<div class="hit_rate_c_t">
                  	<div class="hit_rate_c_m">
                    	<div class="hit_rate_c_m_m"></div>
                    </div>
                    <div  class="hit_rate_c_m_b" >${sgaStateModel.dictHitRate}%</div>
                  </div>
                </div>
                <div class="hit_rate_c_r"></div>
                <div style="clear:both"></div>
                <div class="hit_rate_b_l">&nbsp;</div>
                <div class="hit_rate_b"></div>
                <div class="hit_rate_b_r" >&nbsp;</div>
              </div>
              <div class="hit_rate">
              	<div style="width:100%;">
                	<div class="hit_rate_head">库</div>
                </div>
                <div  class="hit_rate_c_l"></div>
                <div class="hit_rate_c">
                	<div class="hit_rate_c_t">
                  	<div class="hit_rate_c_m">
                    	<div class="hit_rate_c_m_m"></div>
                    </div>
                    <div  class="hit_rate_c_m_b" >${sgaStateModel.libHitRate}%</div>
                  </div>
                </div>
                <div class="hit_rate_c_r"></div>
                <div style="clear:both"></div>
                <div class="hit_rate_b_l">&nbsp;</div>
                <div class="hit_rate_b"></div>
                <div class="hit_rate_b_r" >&nbsp;</div>
              </div>
          </div>
        </td>
        <%--由于SGA信息依赖数据库版本过先注释掉--%>
        <%--<td style="vertical-align:top;width:50%">--%>
        	<%--<div class="threshold_file">--%>
            <%--<div class="sub_title">共享的SGA</div>--%>
            <%--<div id="share_sga" ></div>--%>
          <%--</div>--%>
        <%--</td>--%>
        
      </tr>
      <!--<tr>
      	<td colspan="2">
        	<div class="threshold_file">
            	<table class="last_onehour_table" cellpadding="0" cellspacing="0">
              	<div class="sub_title">服务器快照-<a href="#">192.168.18.217</a></div>
                <tr>
                  <td>
                    <a href="#"><div id="use_cpu"></div></a>
                    <a href="#"><div id="use_memory"></div></a>
                    <a href="#"><div id="use_disk"></div></a>
                  </td>
                </tr>
              </table>
         	</div>
        </td>
      </tr>
  	--></table>
    </div>
    
    <div id="table_space" >
    	 <div class="threshold_file">
            <div class="sub_title">表空间明细</div>
            <div id="table_space_detail"></div>
          	<div class="tool_bar_top">
              <div><img src="${ctx}/global/images/db/legend-green.png" /><span>可用空间</span>
             <img src="${ctx}/global/images/db/legend-red.png" /><span>已用空间</span></div>
            </div>
       </div>
       <%--<div class="threshold_file">--%>
            <%--<div class="sub_title">表空间状态</div>--%>
            <%--<div id="table_space_status"></div>--%>
          	<%--<div class="tool_bar_top"></div>--%>
       <%--</div>--%>
       <%--<div class="threshold_file">--%>
            <%--<div class="sub_title">数据文件的性能指标</div>--%>
            <%--<div id="table_space_performance"></div>--%>
          	<%--<div class="tool_bar_top"></div>--%>
       <%--</div>--%>
    </div>
    <div id="sga">
    		<table width="100%" cellpadding="0" cellspacing="0">
        	<tr>
          	<td colspan="2" >
            	<div class="threshold_file">
                <div class="sub_title">SGA的指标</div>
                <div class="days_data">
                   <a onclick="createThirtyDayHitRate()"><div class="thirty_days"></div></a>
                   <a onclick="createSevenDayHitRate()"><div class="seven_days"></div></a>
                </div>
                <div id="sga_target" style="width:90%"></div>
              </div>
            </td>
          </tr>
          <tr>
             <!--SGA明细对数据库版本有依赖故先不让其显示-->
          	<%--<td width="50%" style="vertical-align:top;">--%>
            	<%--<div style=' border:1px solid #dfe9f2;'>--%>
                <%--<div class="sub_title">SGA明细</div>--%>
                <%--<div id="sga_detail"></div>--%>
             <%--<!----%>
                <%--<a href="#" ><div class="tool_bar_bottom"><div class="warn_set">警告配置</div></div></a>--%>
              <%----></div>--%>
            <%--</td>--%>
            <td width="50%"  style="vertical-align:top; ">
            	<div style=' border:1px solid #dfe9f2;'>
                <div class="sub_title">SGA状态</div>
                <div id="sga_status"></div><!--
                <a href="#" ><div class="tool_bar_bottom"><div class="warn_set">警告配置</div></div></a>
              --></div>
            </td>
          </tr>
        </table>
    </div>
  </div>
</div></div>
<div id="layout_bottom">
	<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>

