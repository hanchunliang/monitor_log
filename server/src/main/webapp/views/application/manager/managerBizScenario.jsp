<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>monitor监控系统-管理业务场景</title>

    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <link href="${ctx}/global/css/manageBusScene/manageBusScene.css" rel="stylesheet" type="text/css" />
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
    getBizScenarioList();
	$("#myDesk").height($("#layout_center").height());
	$("#nav").delegate('li', 'mouseover mouseout', navHover);
	$("#nav,#menu").delegate('li', 'click', navClick);
    $("#bizScenarioGrade").bind("change",getBizScenarioOfGivenGrade);
});
function navHover(){
	$(this).toggleClass("hover")
}
/*获得指定级别的业务场景*/
function getBizScenarioOfGivenGrade(){
    getBizScenarioList();
}
/*获得业务场景列表*/
function getBizScenarioList(){
    var _url="${ctx}/application/manager/bsmanager/bizscenariolist/${appId}";
    var _givenGrade=$("#bizScenarioGrade").val();
    if(''!=_givenGrade){
        _url="${ctx}/application/manager/bsmanager/bizscenariolist/${appId}"+"/"+_givenGrade;
    }
    var $mn=$("#thresholdList");
    $mn.html("");
    $("#thresholdList").Grid({
        type:"post",
        url : _url,
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'场景名称',name:"name",index:'1',align:''},
            {id:'2',text:'添加人',name:"userName",index:'1',align:''},
            {id:'3',text:'添加时间', width:180,name:"recodeCreateTime",index:'1',align:''},
            {id:'4',text:'级别',width:80,name:"bizScenarioGrade",index:'1',align:''},
            {id:'5',text:'操作',name:"operation",index:'1',align:''}
        ],
        rowNum:9999,
        pager : false,
        number:false,
        multiselect: true
    });
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
function delRow(e){
    msgConfirm('系统消息','确定要删除当前的业务场景吗？',function(){
        var rows = $(e).parent().parent();
        var id = rows.attr('id');
        /*编辑业务场景页面*/
        var bizScenarioId=id.substr(4,32);
        window.location.href="${ctx}/application/manager/bsmanager/delete/${appId}/"+bizScenarioId;
    });

}
function managerUrl(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    /*管理url页面*/
    var bizScenarioId=id.substr(4,32);
    window.location.href="${ctx}/application/manager/urlmanager/urllist/"+bizScenarioId;
}
function editRow(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    /*编辑业务场景页面*/
    var bizScenarioId=id.substr(4,32);
    window.location.href="${ctx}/application/manager/bsmanager/updateform/${appId}/"+bizScenarioId;
}
function bizScenarioBatchDel(){
    var $g = $("#thresholdList div.grid_view > table");
    var selecteds = $("td.multiple :checked",$g);
    if(selecteds.length > 0){
        msgConfirm('系统消息','确定要删除这些业务场景吗？',function(){
            var _bizScenarioIds = [];
            selecteds.each(function(){
                var rows = $(this).parent().parent();
                /*id前面多了“rows”*/
                _bizScenarioIds.push(rows.attr('id').substr(4,32));
            });
            $.ajax({
                type:"post",
                url:"${ctx}/application/manager/bsmanager/batchdelete/${appId}",
                data:{bizScenarioIds:_bizScenarioIds},
                success:function(data){
                    if(data){
                        selecteds.each(function(){
                            $(this).parent().parent().remove();
                        });
                        msgSuccess('系统消息','删除成功！')
                    }
                },
                error:function(){
                    msgAlert('系统消息','删除失败！')
                }
            });
        });
    }else{
        msgAlert('系统消息','没有选中的业务场景！<br />请选择要删除的业务场景后，继续操作。')
    };
}
</script>
</head>

<body>
<div id="layout_top">
	<div class="header">
        <%@include file="/WEB-INF/layouts/menu.jsp"%>
    </div>
</div>
<div id="layout_center">
    <div class="main" id="main">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td style="vertical-align:top">
                    <div class="threshold_file">
                        <h2 class="title2"><b>应用名称 : ${applicationName}</b></h2>
                        <div class="tool_bar_top">
                            <div style="float:right;">
                                <span>级别</span>
                                <select id="bizScenarioGrade" name="bizScenarioGrade" class="diySelect">
                                    <option value="">选择级别</option>
                                    <option value="HIGH">高</option>
                                    <option value="INTERMEDIATE">中</option>
                                    <option value="LOW">低</option>
                                </select>
                            </div>
                            <a href="${ctx}/application/manager/bsmanager/createbizscenario/${appId}"
                               class="add_bus_scene">添加业务场景</a>
                            <a href="javascript:void(0);" class="batch_del" onclick="bizScenarioBatchDel()">批量删除</a>

                        </div>
                        <div id="thresholdList"></div>
                        <div class="tool_bar"></div>
                    </div>
                </td>

            </tr>
        </table>
    </div>
</div>
<div id="layout_bottom">
	<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
