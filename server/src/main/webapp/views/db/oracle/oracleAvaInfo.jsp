<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="my_table">
  <c:forEach items="${oracleAvaInfoList}" var="oracleAvaInfo">
   <tr>
       <td>
           <a href="${ctx }/db/oracle/home/viewInfo/${oracleAvaInfo.monitorID}">${oracleAvaInfo.monitorName}</a>
       </td>
       <td>
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="xp_available">
               <tr>
                   <c:forEach items="${oracleAvaInfo.graphInfo}" var="point">
                   <td width="${point[2]}%" class="a${point[1]}">&nbsp;</td>
                   </c:forEach>
               </tr>
           </table>
           <script type="text/javascript">
           $(".xp_available").find(".a0").addClass("not_available");
           $(".xp_available").find(".a1").addClass("green_bar");
           $(".xp_available").find(".a2").addClass("xp_available");
           $(".green_bar").each(function(){
        	($(this).attr("width").split("%")[0]/1)==0?$(this).removeClass("green_bar"):"";
        	//alert(($(this).attr("width").split("%")[0]/1)<1);
           });
          
           </script>
       </td><!--
       <td>可用：${oracleAvaInfo.avaRate}%</td>
   --></tr>
   </c:forEach>
  <tr class="last_row">
    <td>&nbsp;</td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="time_bar">
      <tr>
      	<c:forEach items="${dateList }" var="d">
        <td>${d }</td>
      	</c:forEach>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
</table>