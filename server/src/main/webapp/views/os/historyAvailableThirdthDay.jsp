<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统</title>
<link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/oracle.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/sinosoft.window.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.grid.js"></script>  
<script language="javascript" src="${ctx}/global/js/highcharts.js"></script>
<script language="javascript" src="${ctx}/global/js/exporting.js"></script>
<script language="javascript" src="${ctx}/global/js/highcharts-more.js"></script>
<script language="javascript" src="${ctx}/global/js/sinosoft.window.js"></script>
<script type="text/javascript">
$(function(){
	$("body").layout({
		top:{topHeight:100},
		bottom:{bottomHeight:30}
	});
	$("#myDesk").height($("#layout_center").height());
	$("#nav").delegate('li', 'mouseover mouseout', navHover);
	$("#nav,#menu").delegate('li', 'click', navClick);
});
$(function(){
	var id=$("#osid").val()
	var chart;
	var normalTime;
	var errorTime;
	$(document).ready(function(){
		$.ajax({
			type : "post",
			url : "/monitor/os/historyAvailablePie/30/"+id,
			dataType : "json",
			cache : false,
			success : function(data){
			var normalTime = data.usable;
			var errorTime = data.unusable;
			   new Highcharts.Chart({
		            chart: {
		                renderTo: 'available',
		                plotBackgroundColor: null,
		                plotBorderWidth: null,
		                plotShadow: false,
										height:260
		            },
		            title: {
		                text: ''
		            },
								credits: { 
									text: '',
									href: ''
								},
		            tooltip: {
		                formatter: function() {
		                    return '<b>'+ this.point.name +'</b>: '+ this.percentage +'%';
		                }
		            },
		            plotOptions: {
		                pie: {
		                    allowPointSelect: true,
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: true,
														formatter: function() {
		                            return this.percentage +' %';
		                        }
		                    },
		                    showInLegend: true
		                }
		            },
		            series: [{
		                type: 'pie',
		                name: 'Browser share',
		                data: [
		                    
		                    ['正常运行时间',normalTime],['故障时间',errorTime]
		                ]
		            }],
								colors: ['#5cff5c'] 
		        });
			}
			});
		$("#sevenday_grid").Grid({
			type : "post",
			url : "/monitor/os/historyAvailableGrid/30/"+id,
			dataType: "json",
			height: 'auto',
			colums:[
				{id:'1',text:'时间',name:"time",width:'',index:'1',align:'',color:''},
				{id:'2',text:'正常运行时间 %',name:"normalRun",width:'300',index:'1',align:'',color:''},
				{id:'3',text:'总停机时间',name:"crashTime",width:'',index:'1',align:'',color:''},
				{id:'4',text:'平均修复时间',name:"aveRepairTime",width:'',index:'1',align:'',color:''},
				{id:'5',text:'平均故障时间',name:"aveFaultTime",width:'',index:'1',align:'',color:''}
			],
			rowNum:30,
			rowList:[10,20,30],
			pager : false,
			number:false,
			multiselect:false,
		});
		})	
})
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
function createSevenDayAvailable() { 
	$("body").window({  
			"id":"thirtyDayAvailable",   
			"title":"7天可用性",  
			"url":"sevenDayAvailableLinux.html",   
			"hasIFrame":false,   
			"top":20,   
			"left":20,
			"width":850,
			"height":650  
	}); 
} 
</script>
</head>
	
<body>
<div id="layout_center">
  <div class="main" style="padding-bottom:60px;">
     <div class="threshold_file">
          <div class="sub_title">最近30天的可用性 </div>
           <input id="osid" type="hidden" value="${os.osInfoId }" />
          <table class="base_info" width="100%" cellpadding="0" cellspacing="0">
            <tr><td>监视器名称</td><td>${os.name}</td></tr>
            <tr><td>属性 </td><td>可用性 </td></tr>
            <tr><td>从  </td><td>  ${beginDate} </td></tr>
            <tr><td>到 </td><td> ${currentDate}</td></tr>
           
            <tr><td colspan="2"> 
            	<div class="days_data">
                  <a href="${ctx}/os/historyAvaylable/7/${os.osInfoId }"><div class="seven_days"></div></a>
                  <a><div class="seven_days_unable"></div></a>
                </div></td></tr>
             <tr><td colspan="2"><div id="available" ></div></td></tr>
             
          </table>
          
         
     </div>
     
     <div class="threshold_file">
       <div class="sub_title">监视器停机时间历史</div>
       <div id="sevenday_grid"></div>
     </div>
  </div>
</div>
</body>
</html>

