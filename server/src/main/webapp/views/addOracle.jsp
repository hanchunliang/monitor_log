<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>monitor监控系统-新建oracle监视器</title>
    <%@ include file="/WEB-INF/layouts/base.jsp" %>
    <link href="${ctx }/global/css/sinosoft.tabs.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="${ctx }/global/js/sinosoft.tabs.js"></script>
    <script language="javascript" src="${ctx }/global/js/highcharts.src.js"></script>
    <script language="javascript" src="${ctx }/global/js/oracleMonitor.js"></script>
    <script type="text/javascript">
        var editFlag = '${!empty oracleInfo.id}'=='true';
        $(function(){
            $("body").layout({
                top:{topHeight:100},
                bottom:{bottomHeight:30}
            });
            $("#monitorType option[id='oracle_option']").attr({"selected":"selected"});
            if(editFlag) {$("#monitorType").attr({"disabled":true})};
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
        function saveOracleMonitor(){
            var action = rootPath+ "/db/oracle/";
            if(editFlag) {
                action = action + "edit";
            } else {
                action = action + "save";
            }
            $.ajax({
                url: action,
                type: "post",
                dataType: "json",
                data: $("#oracleInfoForm").serialize(),
                success: function(msg) {
                    if(msg.result) {
                        msgSuccess("系统消息", "操作成功，监视器已保存！", function() {
                            //if(editFlag) {
                                window.location = rootPath + "/db/oracle/oracleMonitor";
                            //}
                        });
                    } else {
                        if(msg.type=='disconnect') {
                            msgFailed("系统消息", msg.tip);
                        }
                    }
                }
            });
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main">
    	<ul class="crumbs">
    		<li>管理></li>
            <li><a href="${ctx}/addmonitor/list">新建监视器</a>></li>
            <li><b>新建oracle监视器</b></li>
         </ul>
        <div class="add_monitor">
            <%@include file="/WEB-INF/layouts/selectMonitorType.jsp"%>
            <form id="oracleInfoForm" action="" method="post">
                <input name="id" type="hidden" value="${oracleInfo.id}"/>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
                    <tr>
                        <td colspan="2" class="group_name">基本信息</td>
                    </tr>
                    <tr>
                        <td width="25%">显示名称<span class="mandatory">*</span></td>
                        <td><input name="name" value="${oracleInfo.name}" type="text" class="formtext" /></td>
                    </tr>
                    <tr>
                        <td>数据库地址<span class="mandatory">*</span></td>
                        <td><input name="ipAddress" value="${oracleInfo.ipAddress}" type="text" class="formtext" size="30" /></td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td>子网掩码<span class="mandatory">*</span></td>--%>
                        <%--<td><input name="subnetMask" value="${oracleInfo.subnetMask}" type="text" class="formtext" size="30" /></td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td>端口<span class="mandatory">*</span></td>
                        <td><input name="port" value="${oracleInfo.port}" type="text" class="formtext" size="8" /></td>
                    </tr>
                    <tr>
                        <td>Instance名(服务名) <span class="mandatory">*</span></td>
                        <td><input name="instanceName" value="${oracleInfo.instanceName}" type="text" class="formtext" value="orcl" /></td>
                    </tr>
                    <tr>
                        <td>执行频率<span class="mandatory">*</span></td>
                        <td><input name="pullInterval" value="${oracleInfo.pullInterval}" type="text" class="formtext" size="8" /> 分</td>
                    </tr>
                    <tr>
                        <td colspan="2" class="group_name">用户信息</td>
                    </tr>
                    <tr>
                        <td>用户名<span class="mandatory">*</span></td>
                        <td><input name="username" value="${oracleInfo.username}" type="text" class="formtext" /></td>
                    </tr>
                    <tr>
                        <td>密码<span class="mandatory">*</span></td>
                        <td><input name="password" value="${oracleInfo.password}" type="password" class="formtext" /></td>
                    </tr>
                    <tr>
                        <td class="group_name">&nbsp;</td>
                        <td class="group_name">
                            <input type="button" class="buttons" value="确定保存" onclick="saveOracleMonitor();" />　
                            <input type="reset" class="buttons" value="重 置" />　
                            <input type="button" class="buttons" value="取 消" onclick="window.history.back()" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
