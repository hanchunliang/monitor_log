<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<table class="health_table"  style="height: 305px;">
    <tbody>
    <tr class="health_head">
        <td colspan="2">可用性和当前健康状况</td>

    </tr>
    <tr>
        <td class="health_info">
            <table  >
                <tbody>
                <tr>
                    <td style="width:95px;padding:30px 0 5px 25px;">可用性</td>
                    <td style="width:95px;padding:30px 0 5px 5px;">健康性</td>

                </tr>
                <tr>
                    <td style="width:95px;padding:0 0 0 30px;">
                        <div class="${alarmViewModel.availability}"></div>

                    </td>
                    <td  style="width:95px;padding:0 0 0 0px;">
                        <div class="${alarmViewModel.severityLevel}"></div>
                    </td>

                </tr>
                </tbody>
            </table>
        </td>
        <td class="health_dec">
            <table>
                <tr>
                    <td></td>
                    <td>警报</td>
                </tr>
                <tr>
                    <td  style="width:35%">评定</td>
                    <td><div class="bar">${alarmViewModel.criticalCount}</div></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr class="health_error">
        <td colspan="2">

            <lable>根本原因:</lable>
            <p>
                ${alarmViewModel.alarmInfoes}
            </p>

            <div class="show_all_error" style="float:right;cursor:pointer;"><img src="${ctx}/global/images/spmservice/icon_plus.gif" />&nbsp;&nbsp;显示所有信息</div>
            <div class="hide_some_error" style="display:none;float:right;cursor:pointer;"><img src="${ctx}/global/images/spmservice/icon_minus.gif" />&nbsp;&nbsp;隐藏部分信息</div>
        </td>

    </tr>
    </tbody>
</table>
