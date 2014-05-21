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
<script language="javascript" src="${ctx}/global/js/os/higcharDraw.js"></script>
		<script type="text/javascript">
		$(function(){
			var id= $("#osid").val();
			creatSimpleChart('${ctx}/os/historyCPUStatiLine/7/'+id, 'last_sevenday', 'CPU用户利用率%');
			$("#sevenday_grid").Grid({
				type : "post",
				url :  '/monitor/os/historyCPUStatiGrid/7/'+id,
				dataType: "json",
				height: 'auto',
				colums:[
					{id:'1',text:'时间',name:"time",width:'300',index:'1',align:'',color:''},
					{id:'2',text:'最小值  ',name:"minValue",width:'',index:'1',align:'',color:''},
					{id:'3',text:'最大值 ',name:"maxValue",width:'',index:'1',align:'',color:''},
					{id:'4',text:'平均值 ',name:"averageValue",width:'',index:'1',align:'',color:''}
				],
				rowNum:40,
				rowList:[10,20,30],
				pager : false,
				number:false,
				multiselect:true,
			});
		
		
		})
$(function(){
	$("body").layout({
		top:{topHeight:100},
		bottom:{bottomHeight:30}
	});
	$("#myDesk").height($("#layout_center").height());
	$("#nav").delegate('li', 'mouseover mouseout', navHover);
	$("#nav,#menu").delegate('li', 'click', navClick);
	
	
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
</script>
	</head>

	<body>
		<div id="layout_center">
			<div class="main" style="margin-bottom: 60px;">
				<div class="threshold_file">
			
					<div class="sub_title">
						最近7天的CPU用户利用率
					</div>
					<input id="osid" type="hidden" value="${os.osInfoId }" />
					<table class="base_info" width="100%" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								监视器名称
							</td>
							<td>
								${os.name}
							</td>
						</tr>
						<tr>
							<td>
								属性
							</td>
							<td>
								CPU用户利用率
							</td>
						</tr>
						<tr>
							<td>
								从
							</td>
							<td>
								${beginDate}
							</td>
						</tr>
						<tr>
							<td>
								到
							</td>
							<td>
								${currentDate}
							</td>
						</tr>

						<tr>
							<td colspan="2">
								<div class="days_data">
									<a href="${ctx}/os/historyCPU/30/${os.osInfoId }"><div class="thirty_days"></div>
									</a>
									<a href="#"><div class="seven_days_unable"></div>
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
									<div>
										CPU用户利用率(%) :
									</div>
									<div>
										最大平均值:
									</div>
									<div>
										${MaxAgv }
									</div>
									<div>
										最小平均值
									</div>
									<div>
										${MinAgv}
									</div>
									<div>
										平均:
									</div>
									<div>
										${Agv }
									</div>
								</div>
							</td>
						</tr>
					</table>


				</div>

				<div class="threshold_file">
					<div class="sub_title">
						CPU利用率(%)
					</div>
					<div id="sevenday_grid"></div>
				</div>
			</div>
		</div>
	</body>
</html>
