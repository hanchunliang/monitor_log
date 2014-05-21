<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>修改阈值配置文件</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/sinosoft.message.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script language="javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.message.js"></script>

<script type="text/javascript">
    $(function(){
        $("body").layout({
            top:{topHeight:100}
        });

        $('#saveThresholdForm').ajaxForm({
            dataType: "json",
            success: function(data) {
                msgSuccess("系统消息", "阈值文件保存成功！", function() {
                    location.href = "${ctx}/threshold/list";
                });
            },
            error: function() {
                msgSuccess("系统消息", "阈值文件保存失败！");
            }
        });
    });

function toSaveThreshold() {
    var name = $("#name").val();
    if(!name || name == "") {
        msgAlert("系统消息", "阈值名称不能为空");
        return;
    }
    var criticalThresholdValue = $("#criticalThresholdValue").val();
    if(!criticalThresholdValue || criticalThresholdValue == "" || isNaN(criticalThresholdValue)) {
        msgAlert("系统消息", "严重阈值界限不能为空，且必须为数字");
        return;
    }

    var warningThresholdValue = $("#warningThresholdValue").val();
    if(!warningThresholdValue || warningThresholdValue == "" || isNaN(warningThresholdValue)) {
        msgAlert("系统消息", "警告阈值界限不能为空，且必须为数字");
        return;
    }

    var infoThresholdValue = $("#infoThresholdValue").val();
    if(!infoThresholdValue || infoThresholdValue == "" || isNaN(infoThresholdValue)) {
        msgAlert("系统消息", "正常阈值界限不能为空，且必须为数字");
        return;
    }

    $("#saveThresholdForm").submit();
}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main">
		<ul class="crumbs">
            <li>管理></li>
            <li><a href="${ctx}/threshold/list">查看阈值文件</a>></li>
            <li><b>修改阈值文件</b></li>
        </ul>
    	<div class="add_monitor">
       	  <h2 class="title2"><b>修改阈值文件</b></h2>
          <form id="saveThresholdForm" action="${ctx}/threshold/save" method="post">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form threshold" id="threshold">
              <input type="hidden" name="id" value="${threshold.id }">	
              <tr>
                <td width="20%">阈值名称<span class="mandatory">*</span></td>
                <td colspan="2"><input id="name" type="text" class="formtext" size="38" name="name"  value="${ threshold.name}"/></td>
              </tr>
              <tr>
                <td><img src="${ctx}/global/images/icon_critical.gif" alt="严重重要度" class="m_b"/> <b>严重重要度:</b></td>
                <td > 被监控的值是 </td>
               	<td>  <select name="criticalThresholdCondition" class="diySelect" style="margin-right:20px;font-family:Arial, Helvetica, sans-serif;width:60px">
                         <option value="LT"   ${threshold.criticalThresholdCondition eq "LT"  ? "selected='selected'" : ''}>&lt;</option>
                         <option value="GT"   ${threshold.criticalThresholdCondition eq "GT"  ? "selected='selected'" : ''}>&gt;</option>
                         <option value="EQ"   ${threshold.criticalThresholdCondition eq "EQ"  ? "selected='selected'" : ''}>=</option>
                         <option value="NE"  ${threshold.criticalThresholdCondition eq "NE"  ? "selected='selected'" : ''}>!=</option>
                         <option value="LE"  ${threshold.criticalThresholdCondition eq "LE"  ? "selected='selected'" : ''}>&lt;=</option>
                         <option value="GE"  ${threshold.criticalThresholdCondition eq "GE"  ? "selected='selected'" : ''}>&gt;=</option>
                    </select>
阈值界限
                    <input  type="text" class="formtext"  size="4" id="criticalThresholdValue" name="criticalThresholdValue" value="${ threshold.criticalThresholdValue}"/>
                </td>
              </tr>
              <%--<tr>
                <td><input name="senior" type="checkbox" value="" class="m_b"  onclick="rowsTogle()" id="senior" /> <label for="senior">显示高级选项</label></td>
                <td colspan="2"></td>
              </tr>--%>
              <tr>
                <td>&nbsp;</td>
                <td width="8%" valign="top">信息</td>
                <td><textarea cols="48" class="formtext" style="height:60px" name="criticalThresholdMessage"  >${threshold.criticalThresholdMessage eq null ? "严重警告信息" : threshold.criticalThresholdMessage}</textarea></td>
              </tr>
              <tr>
                <td colspan="3">
                	<hr class="hr" />
                </td>
              </tr>
              <tr>
                <td><img src="${ctx}/global/images/icon_warning.gif" alt="严重重要度" class="m_b"/> <b>警告重要度:</b></td>
                <td>被监控的值是</td>
                <td><select name="warningThresholdCondition" class="diySelect" style="margin-right:20px;font-family:Arial, Helvetica, sans-serif;width:60px">
                   		  <option value="LT"   ${threshold.warningThresholdCondition eq "LT"  ? "selected='selected'" : ''}>&lt;</option>
		                  <option value="GT"   ${threshold.warningThresholdCondition eq "GT"  ? "selected='selected'" : ''}>&gt;</option>
		                  <option value="EQ"   ${threshold.warningThresholdCondition eq "EQ"  ? "selected='selected'" : ''}>=</option>
		                  <option value="NE"  ${threshold.warningThresholdCondition eq "NE"  ? "selected='selected'" : ''}>!=</option>
		                  <option value="LE"  ${threshold.warningThresholdCondition eq "LE"  ? "selected='selected'" : ''}>&lt;=</option>
		                  <option value="GE"  ${threshold.warningThresholdCondition eq "GE"  ? "selected='selected'" : ''}>&gt;=</option>
                </select>
阈值界限
			<input name="warningThresholdValue" id="warningThresholdValue" type="text" class="formtext" size="4" value="${ threshold.warningThresholdValue}" /></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td width="8%" valign="top">信息</td>
                <td><textarea cols="48" class="formtext" style="height:60px" name="warningThresholdMessage" >${threshold.warningThresholdMessage eq null ? "警告告警信息" : threshold.warningThresholdMessage}</textarea></td>
              </tr>
              <tr>
                <td colspan="3"><hr class="hr" /></td>
              </tr>
              <tr>
                <td><img src="${ctx}/global/images/icon_clear.gif" alt="严重重要度" class="m_b"/> <b>正常重要度:</b></td>
                <td>被监控的值是                  </td>
                <td>
                    <select name="infoThresholdCondition" class="diySelect" style="margin-right:20px;font-family:Arial, Helvetica, sans-serif;width:60px">
                        <option value="LT"   ${threshold.infoThresholdCondition eq "LT"  ? "selected='selected'" : ''}>&lt;</option>
                        <option value="GT"   ${threshold.infoThresholdCondition eq "GT"  ? "selected='selected'" : ''}>&gt;</option>
                        <option value="EQ"   ${threshold.infoThresholdCondition eq "EQ"  ? "selected='selected'" : ''}>=</option>
                        <option value="NE"  ${threshold.infoThresholdCondition eq "NE"  ? "selected='selected'" : ''}>!=</option>
                        <option value="LE"  ${threshold.infoThresholdCondition eq "LE"  ? "selected='selected'" : ''}>&lt;=</option>
                        <option value="GE"  ${threshold.infoThresholdCondition eq "GE"  ? "selected='selected'" : ''}>&gt;=</option>
                    </select>
阈值界限
	                <input type="text" class="formtext"  size="4" id="infoThresholdValue" name="infoThresholdValue"  value="${ threshold.infoThresholdValue}"/></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td valign="top">信息</td>
                <td><textarea name="infoThresholdMessage" cols="48" class="formtext" style="height:60px" >${threshold.infoThresholdMessage eq null ? "正常告警信息" : threshold.infoThresholdMessage}</textarea></td>
              </tr>
              <tr>
                <td class="group_name">&nbsp;</td>
                <td colspan="2" class="group_name">
                	<input id="createPropFile" type="button" class="buttons" value="创建阈值配置文件" onclick="toSaveThreshold()"/>　
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
<script type="text/javascript">
        if($("#criticalThresholdValue").val() || $("#warningThresholdValue").val() || $("infoThresholdValue").val()){
            $("#createPropFile").val("确定修改");
        }
</script>
</html>
