<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <title>monitor监控系统-创建新的邮件动作</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/global/css/sinosoft.message.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script language="javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.message.js"></script>
    <script type="text/javascript">
        $(function () {
            $("body").layout({
                top: {topHeight: 100},
                bottom: {bottomHeight: 30}
            });

            $('#emailForm').ajaxForm({
                dataType: "json",
                success: function(data) {
                    if(data.status == "success") {
                        msgSuccess("系统消息", "邮件动作保存成功！", function() {
                            location.href = "${ctx}/action/email/list";
                        });
                    }
                },
                error: function() {
                    msgSuccess("系统消息", "邮件动作保存失败！");
                }
            });

        });

        function toSaveMailAction() {
            var name = $("#name").val();
            if(!name || name == "") {
                msgAlert("系统消息", "邮件动作名称不能为空");
                return;
            }
            var fromAddress = $("#fromAddress").val();
            if(!fromAddress || fromAddress == "") {
                msgAlert("系统消息", "发件人地址不能为空");
                return;
            }

            var toAddress = $("#toAddress").val();
            if(!toAddress || toAddress == "") {
                msgAlert("系统消息", "收件人地址不能为空");
                return;
            }

            var subject = $("#subject").val();
            if(!subject || subject == "") {
                msgAlert("系统消息", "邮件主题不能为空");
                return;
            }

            $("#emailForm").submit();
        }
        function rowsTogle() {
            var rows = $("#threshold tr.hideRows");
            if (rows.eq(0).is(':hidden')) {
                rows.show();
            } else {
                rows.hide();
            }
            ;
            return false;
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main">
    	<ul class="crumbs">
            <li>管理></li>
            <li><b>创建动作</b></li>
        </ul>
    	
        <div class="add_monitor">
            
			<h2 class="title2"><b> 发送EMAIL 　</b></h2>	
            <form action="${ctx}/action/email/save" method="post" id="emailForm">
                <table width="100%" border="0" cellspacing="0" cellpadding="0"
                       class="add_monitor_box2 add_form threshold" id="threshold">
                    <input type="hidden" name="id" value="${mail.id}">
                    <tr>
                        <td width="20%">邮件动作名<span class="mandatory">*</span></td>
                        <td colspan="2"><input type="text" class="formtext" size="45" id="name" name="name" value="${mail.name}"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%">发件人地址<span class="mandatory">*</span></td>
                        <td colspan="2"><input type="text" class="formtext" size="45" id="fromAddress" name="fromAddress"
                                               value="${mail.fromAddress }"/></td>
                    </tr>
                    <tr>
                        <td width="20%">收件人地址<span class="mandatory">*</span></td>
                        <td colspan="2"><input type="text" class="formtext" size="45" id="toAddress" name="toAddress"
                                               value="${mail.toAddress}"/></td>
                    </tr>
                    <tr>
                        <td width="20%">主题<span class="mandatory">*</span></td>
                        <td colspan="2"><input type="text" class="formtext" size="45" id="subject" value="${mail.subject }"
                                               name="subject"/></td>
                    </tr>
                    <tr>
                        <td width="20%">附加消息</td>
                        <td colspan="2"><textarea class="formtext" style="height:150px; width:270px"
                                                  name="appendMessage">${mail.appendMessage}</textarea></td>
                    </tr>
                    <!--
                     <tr>
                      <td width="20%">经由</td>
                      <td colspan="2"><input type="radio" value="1" name="mailFormat"/>&nbsp;&nbsp;&nbsp;纯文本&nbsp;&nbsp;&nbsp;<input type="radio"  value="2"  name="mailFormat"/>&nbsp;&nbsp;&nbsp;HTML<input type="radio" name="mailFormat" checked="checked" value="3"/>&nbsp;&nbsp;&nbsp;两者</td>
                    </tr>
                          <tr>
                      <td > <label for="senior">添加告警消息</label></td><td><input class="checkbox1" name="senior" type="checkbox" value="" class="m_b"  id="senior" /></td>
                      <td colspan="2"></td>
                    </tr>
                    <tr>
                      <td > <label for="senior">在选择的小时内执行动作</label></td><td><input class="checkbox1" name="senior" type="checkbox" value="" class="m_b"  onclick="rowsTogle()" id="senior" /></td>
                      <td colspan="2"></td>
                    </tr>
                     <tr  class="hideRows">
                      <td width="20%">选择时间窗口<span class="mandatory">*</span></td>
                      <td colspan="2"><select name="select2" class="diySelect" style="font-family:Arial, Helvetica, sans-serif;width:130px">
                        <option selected="selected">--选择工作时间--</option>
                      </select>&nbsp;&nbsp;<a class="message-time" href="#">添加新的工作时间</a></td>
                    </tr>
                    -->
                    <tr>
                        <td class="group_name">&nbsp;</td>
                        <td colspan="2" class="group_name">
                            <input type="button" class="buttons" value="保存" onclick="toSaveMailAction()"/>　
                            <input type="reset" class="buttons" value="重置"/>　
                            <input type="button" class="buttons" value="取 消" onclick="window.history.back()"/>
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
