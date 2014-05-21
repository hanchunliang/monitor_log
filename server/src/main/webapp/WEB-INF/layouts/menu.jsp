<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
    $(function () {
        $("body").layout({
            top:{topHeight:100},
            bottom:{bottomHeight:30}
        });
        $("#myDesk").height($("#layout_center").height());
        $("#nav").delegate('li', 'mouseover mouseout', navHover);
        $("#nav,#menu").delegate('li', 'click', navClick);
    });
    function navHover() {
        $(this).toggleClass("hover")
    }
    function navClick() {
        $(this).addClass("seleck").siblings().removeClass("seleck");
        if ($(this).hasClass('has_sub')) {
            var subMav = $(this).children("ul.add_sub_menu");
            var isAdd = false;
            if ($(this).parent().attr("id") == "menu") {
                isAdd = true;
            }
            ;
            subMav.slideDown('fast', function () {
                $(document).bind('click', {dom:subMav, add:isAdd}, hideNav);
                return false;
            });
        };
    }
    function hideNav(e) {
        var subMenu = e.data.dom;
        var isAdd = e.data.add;
        subMenu.slideUp('fast', function () {
            if (isAdd) {
                subMenu.parent().removeClass('seleck');
            };
        });
        $(document).unbind();
    }
    function save() {
        msgSuccess("系统消息", "操作成功，监视器已保存！");
    }
    function rowsTogle() {
        var rows = $("#threshold tr.hideRows");
        if (rows.eq(0).is(':hidden')) {
            rows.show();
        } else {
            rows.hide();
        };
        return false;
    }
</script>
<div id="layout_top">
    <div class="header">
        <shiro:hasPermission name="admin">
            <p class="user">您好,系统管理员 <span>|</span> <a href="${ctx}/login">退出系统</a></p>
        </shiro:hasPermission>
        <shiro:lacksPermission name="admin">
            <p class="user">您好,用户${sessionScope.loginUserName} <span>|</span> <a href="${ctx}/login">退出系统</a></p>
        </shiro:lacksPermission>
        <div class="menu_box">
            <ul class="nav" id="nav">
                <li><a href="${ctx }/index">首页</a></li>
                <li class="has_sub">
                    <a href="${ctx}/application/manager/appmanager/applist/1">配置管理</a>
                </li>
                <li><a href="${ctx}/logquery/logquery">日志查询</a></li>
                <li><a href="${ctx}/conanal/conanal">统计报表</a></li>
                <shiro:hasPermission name="admin">
                    <li><a href="${ctx}/account/user/list">用户管理</a></li>
                </shiro:hasPermission>
            </ul>
        </div>

    </div>
</div>