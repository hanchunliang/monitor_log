<%@ page language="java" pageEncoding="UTF-8" %>
<%--<h2 class="title2"><b>新建监视器类型　</b>--%>
<h2 class="title2"><b>新建应用　</b>
    <select id="monitorType" name="monitorType" class="diySelect" onchange="top.location=this.value;" style="display: none;">
        <optgroup label="应用服务器">
            <option selected="selected" value="${ctx}/addmonitor/addapp">应用系统　　</option>
        </optgroup>
        <optgroup label="数据库">
            <option value="${ctx}/addmonitor/addoracle">Oracle</option>
        </optgroup>
    </select>
</h2>
</h2>
<script type="text/javascript">
    var _val =   location.href.substring( location.href.lastIndexOf("/")+1);
    if(_val =="addapp")  {
        $("#monitorType").find("optgroup:eq(0)").find("option").attr("selected","selected")
    }else if(_val=="addoracle")  {
        $("#monitorType").find("optgroup:eq(1)").find("option").attr("selected","selected")
    }else if(_val=="addos"){
        $("#monitorType").find("optgroup:eq(2)").find("option").attr("selected","selected")
    }

</script>