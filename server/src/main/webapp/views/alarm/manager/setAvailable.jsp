<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript">
$(function(){
	
});
function rowsTogle(monitorId,attributeId){
	var rows = $("#threshold tr.hideRows");
    var _monitorId=monitorId;
    var _attributeId=attributeId;
	if(rows.eq(0).is(':hidden')){
        getAllActions(monitorId,attributeId);
		rows.show();
	}else{
		rows.hide();
	};
	return false;
}

/*获得动作表中的数据，后台需判断这个动作是否已经被关联*/
function getAllActions(monitorId,attributeId){
    $.ajax({
        type:"post",
        /*url:"${ctx}/alarm/manager/configemergency/actions/${monitorId}/",*/
        url:"${ctx}/alarm/manager/configemergency/actions/"+monitorId+"/"+attributeId,
        dataType:"json",
        async:false,
        success:function(data){
            var $mn1 = $("#grave");
            var $mn2 = $("#graveList");
            var $mn5 = $("#normal");
            var $mn6 = $("#normalList");

            //防止每次查询时，表格中的数据不断累积
            $mn1.html("");
            $mn2.html("");
            $mn5.html("");
            $mn6.html("");
            /*$("#grave").append("<option value='choice' >" +"--选择一个监视器--"+" </option> ");*/
            for(var i = 0; i<data.length;i++){
                /*动作id，关联动作时发送，保存到属性动作表*/
                var _key = data[i].actionId;
                var _name =data[i].actionName;
                var _severityLevel = [];
                _severityLevel = data[i].actionSeverity;
                var _addRight1=false;
                var _addRight2=false;
                var _addRight3=false;
                for(var j = 0; j<_severityLevel.length;j++){
                    if("严重"==_severityLevel[j].several){
                        /*放入严重右边*/
                        $("#graveList").append("<option value='"+_key+"' > "+_name+" </option> ");
                        _addRight1=true;
                    }else if("正常"==_severityLevel[j].several){
                        /*放入正常右边*/
                        $("#normalList").append("<option value='"+_key+"' > "+_name+" </option> ");
                        _addRight3=true;
                    }
                }
                /*放入严重左边*/
                if(!_addRight1){
                    $("#grave").append("<option value='"+_key+"' > "+_name+" </option> ");
                }
                /*放入正常左边*/
                if(!_addRight3){
                    $("#normal").append("<option value='"+_key+"' > "+_name+" </option> ");
                }
            }
        }
    });
}

function setNatureRight(name){
	var the = $('#'+name);
	var that = $('#' + name + 'List');
	var option = $(':selected',the);
	if(option.length > 0){
		option.appendTo(that)
	}else{
		msgAlert('系统消息','没有选中的动作！<br />请选择要关联的动作。')
	};	
}
function setNatureRightAll(name){
	var the = $('#'+name);
	var that = $('#' + name + 'List');
	var option = $('option',the);
	option.appendTo(that)
}
function setNatureLeft(name){
	var the = $('#'+name);
	var that = $('#' + name + 'List');
	var option = $(':selected',that);
	if(option.length > 0){
		option.appendTo(the)
	}else{
		msgAlert('系统消息','没有选中的关联动作！<br />请选择要移出的关联动作。')
	};	
}
function setNatureLeftAll(name){
	var the = $('#'+name);
	var that = $('#' + name + 'List');
	var option = $('option',that);
	option.appendTo(the)
}
</script>
</head>

<body>
<div id="layout_center">
	<div class="window_main">
    	<div class="threshold_file">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form" id="threshold">
        	  <tr>
        	    <td colspan="2" class="group_name">基本信息</td>
      	    </tr>
        	  <tr>
        	    <td width="25%">监视器名称</td>
        	    <td>${monitorName}</td>
      	    </tr>
        	  <tr>
        	    <td>属性</td>
        	    <td>${attributeName}</td>
      	    </tr>
        	  <tr>
        	    <td>
                    <input type="hidden" id="monitorId" name="monitorId" value="${monitorId}"/>
                    <input type="hidden" id="attributeId" name="attributeId" value="${attributeId}"/>
                	<input name="senior" type="checkbox" value="" class="m_b"  onclick="rowsTogle($('#monitorId').val(),$('#attributeId').val())" id="senior" /> <label for="senior">配置属性级别动作</label>
                </td>
        	    <td>&nbsp;</td>
      	    </tr>
        	  <tr class="hideRows">
        	    <td colspan="2" class="group_name">关联动作</td>
      	    </tr>
        	  <tr class="hideRows">
        	    <td>&nbsp;</td>
        	    <td><table width="480" border="0" cellspacing="0" cellpadding="0" class="select_tab">
        	      <tr>
        	        <td width="200"><strong>可用动作</strong></td>
        	        <td align="center">&nbsp;</td>
        	        <td width="200"><strong>关联动作</strong></td>
      	        </tr>
       	        </table></td>
      	    </tr>
        	  <tr class="hideRows">
        	    <td style="vertical-align:top">如果监视器 <img src="${ctx}/global/images/bussinessY2.gif" width="14" height="14" /> 停止,则调用动作</td>
        	    <td><table width="480" border="0" cellspacing="0" cellpadding="0" class="select_tab">
        	      <tr>
        	        <td width="200" rowspan="4">
        	        	<select name="select2" size="8" class="formtext" style="width:200px;height:120px" id="grave">
                        	<%--<option>Restart the Server</option>
                            <option>数据异常</option>--%>
      	            	</select>
                    </td>
        	        <td align="center">
        	          <input type="button" class="buttons" value=">" style="width:50px" onclick="setNatureRight('grave')" />
        	        </td>
        	        <td width="200" rowspan="4"><select name="select3" size="8" id="graveList"  class="formtext" style="width:200px;height:120px">
</select></td>
      	        </tr>
        	      <tr>
        	        <td align="center">
        	          <input type="button" class="buttons" value=">>"  style="width:50px" onclick="setNatureRightAll('grave')" />
        	        </td>
      	        </tr>
        	      <tr>
        	        <td align="center">
        	          <input type="button" class="buttons" value="<" style="width:50px"  onclick="setNatureLeft('grave')"   />
        	        </td>
      	        </tr>
        	      <tr>
        	        <td align="center">
        	          <input type="button" class="buttons" value="<<" style="width:50px"  onclick="setNatureLeftAll('grave')"  />
        	        </td>
      	        </tr>
      	        </table>
       	        <p>&nbsp;</p></td>
      	    </tr>
        	  <tr class="hideRows">
        	    <td style="vertical-align:top">如果监视器 <img src="${ctx}/global/images/bussinessY.gif" width="14" height="14" /> 正常运行,则调用动作</td>
        	    <td><table width="480" border="0" cellspacing="0" cellpadding="0" class="select_tab">
        	      <tr>
        	        <td width="200" rowspan="4"><select name="select4" size="8" id="normal"  class="formtext" style="width:200px;height:120px">
        	          <%--<option>Restart the Server</option>
        	          <option>数据异常</option>--%>
      	          </select></td>
                      <td align="center"><input type="button" class="buttons" value="&gt;" style="width:50px" onclick="setNatureRight('normal')" /></td>
                      <td width="200" rowspan="4"><select name="select4" size="8" id="normalList"  class="formtext" style="width:200px;height:120px">
                      </select></td>
                  </tr>
                    <tr>
                        <td align="center"><input type="button" class="buttons" value="&gt;&gt;"  style="width:50px" onclick="setNatureRightAll('normal')" /></td>
                    </tr>
                    <tr>
                        <td align="center"><input type="button" class="buttons" value="&lt;" style="width:50px"  onclick="setNatureLeft('normal')"   /></td>
                    </tr>
                    <tr>
                        <td align="center"><input type="button" class="buttons" value="&lt;&lt;" style="width:50px"  onclick="setNatureLeftAll('normal')"  /></td>
                    </tr>
      	      </table>
       	        <p>&nbsp;</p></td>
      	    </tr>        	  
      	      </table>
       	        <p>&nbsp;</p>
       	        <p>&nbsp;</p></td>
      	    </tr>
       	  </table>
    	</div>
    </div>
</div>
</body>
</html>
