<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>monitor监控系统-方法透视</title>
    <link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var ctx = "${ctx}";
        var methodId = "${methodId}";
    </script>
    <script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
    <script language="javascript" src="${ctx}/global/js/sinosoft.layout.js"></script>
    <script type="text/javascript">
        $(function(){
            $("body").layout({
                top:{topHeight:100},
                bottom:{bottomHeight:30}
            });
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
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <ul class="crumbs">
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>
            <li><a href="${ctx}/application/manager/detail/main/${applicationId}">应用透视</a> ></li>
            <li><a href="${ctx}/application/manager/url/main/${applicationId}/${urlId}">URL透视</a> ></li>
            <li><a href="${ctx}/application/manager/appmethod/viewLogDetail/${applicationId}/${urlId}/${logId}">事件日志透视</a> ></li>
            <li><b>方法透视</b></li>
        </ul>

        <div class="conf_box">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                方法参数
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li><b>方法参数：</b>${inParam}</li>
                    </ul>
                </div>
            </div>
        </div>
        <br/>
        <div class="conf_title">
            <div class="conf_title_r"></div>
            <div class="conf_title_l"></div>
            返回值
        </div>
        <div class="conf_cont_box">
            <div class="conf_cont">
                <ul>
                    <li><b> 返回值：</b> ${outParam}</li>
                </ul>

            </div>
        </div>
</div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>