<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <title>应用性能</title>

    <style type="text/css">
        .td_href{ margin-left: 5px;}
    </style>
    <script type="text/javascript">
        var operateFlag = [
            {
                "name" : "name6",
                "text" : "管理业务场景",
                operateFun : function(row) {
                    var rowId = row.id.split("_");
                    rowId = rowId[0];
                    window.location.href = "${ctx}/application/manager/bsmanager/list/" + rowId;
                }
            },
            {
                "name" : "name6",
                "text" : "编辑",
                operateFun : function(row) {
                    var rowId = row.id.split("_");
                    rowId = rowId[0];
                    window.location.href = "${ctx}/application/manager/appmanager/update/" + rowId;
                }
            },
            {
                "name" : "name6",
                "text" : "删除",
                operateFun : function(row) {
                    var rowId = row.id.split("_");
                    rowId = rowId[0];
                    msgConfirm('系统消息','确定要删除当前的应用吗？',function(){
                        window.location.href="${ctx}/application/manager/appmanager/delete/"+rowId;
                    });
                }
            }
        ];
        $(function(){
            $("body").layout({
                top:{topHeight:100}
            });
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
                    {id:'5',text:'创建人',name:"name5",index:'1',align:''},
                    {id:'5',text:'管理',name:"name6",index:'1',align:''}
                ],
                rowNum:9999,
                pager : false,
                number:false,
                multiselect: false,
                operateObj : operateFlag
            });
        });
        function creNewApp() {
            window.location.href = "${ctx}/addmonitor/addapp";
        }
    </script>
    <style type="text/css">
        a{text-decoration: underline}
    </style>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <div class="threshold_file">
            <h3 class="title3">
                应用系统：
                <input onclick="creNewApp();" type="button" value="新建应用" />
            </h3>
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
