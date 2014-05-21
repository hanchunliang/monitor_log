<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/layouts/base.jsp" %>
<title>monitor监控系统</title>

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

    $("#thresholdList").Grid({
        url : "${ctx}/logquery/appList",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'名称',name:"name1",index:'1',align:''},
            {id:'2',text:'IP地址',name:"name2",index:'1',align:''},
            {id:'3',text:'端口地址',name:"name3",index:'1',align:''},
            {id:'4',text:'创建时间',name:"name4",index:'1',align:''},
            {id:'5',text:'创建人',name:"name5",index:'1',align:''}
        ],
        rowNum:9999,
        pager : false,
        number:false,
        multiselect: false
    });
	$("#myDesk").height($("#layout_center").height());
});

</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main">
        <div class="threshold_file">
            <h3 class="title3">应用系统：</h3>
            <div id="thresholdList"></div>
        </div>
        <br />
    </div>
</div>
<div id="layout_bottom">
	<p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
