<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>配置告警</title>
    <%@ include file="/WEB-INF/layouts/base.jsp"%>
    <script type="text/javascript">

        $(function(){
            $("#monitorType").bind("change",getMonitorNames);
        });

        function setHealthOrAvailable(monitorType,monitorId,attributeType,attributeId){
            var _monitorTypeSelected=$("#monitorType").val();
            var _monitorNameSelected=$("#monitorName").val();
            if(''==_monitorTypeSelected){
                msgAlert("系统消息", "请选择监视器类型！");
            }else if('choice'==_monitorNameSelected){
                msgAlert("系统消息", "请选择监视器名字！");
            }else{
                if(null!=attributeType&&""!=attributeType){
                    setHealthOrAvailableEmergencyForm(monitorType,monitorId,attributeType);
                }else if(null!=attributeId&&""!=attributeId) {
                    setHealthOrAvailableEmergencyForm(monitorType,monitorId,attributeId);
                }
            }
        }


        function getMonitorNames(){
            $.ajax({
                type:"post",
                url:"${ctx}/alarm/manager/configemergency/monitornames/"+$("#monitorType").val(),
                dataType:"json",
                async:false,
                success:function(data){
                    var $mn = $("#monitorName");
                    //防止每次查询时，表格中的数据不断累积
                    $mn.html("");
                    $("#monitorName").append("<option value='choice' >" +"--选择一个监视器--"+" </option> ");
                    for(var i = 0; i<data.length;i++){
                        var _key = data[i].monitorId;
                        var _name =data[i].monitorName;
                        $("#monitorName").append("<option value='"+_key+"' > "+_name+" </option> ");
                    }
                }
            });
        }
        function getNewGrid(resourceType,monitorId){
            $("body").layout({
                top:{topHeight:100},
                bottom:{bottomHeight:30}
            });
            if($.browser.msie && ($.browser.version == "7.0")){
                var center = $("#layout_center");
                $("#main").width(center.width() - 31).height(center.height() - 30);
            }
            $("#myDesk").height($("#layout_center").height());
            $("#nav").delegate('li', 'mouseover mouseout', navHover);
            $("#nav,#menu").delegate('li', 'click', navClick);
            $("#natureList").html("");
            if(monitorId==null){
                var _resourceType=$("#monitorType").val();
                var _monitorId=$('#monitorName').val();
            }else{
                _resourceType=resourceType;
                _monitorId=monitorId;
            }

            $("#natureList").Grid({
                type:"post",
                url : "${ctx}/alarm/manager/configemergency/attributenames/"+_resourceType+"/"+_monitorId,
                dataType: "json",
                colDisplay: false,
                clickSelect: true,
                draggable:false,
                height: 225,
                colums:[
                    {id:'1',text:'属性名',name:"attributeCn",index:'1',align:''},
                    {id:'2',text:'阈值',name:"threshold",index:'1',align:''},
                    {id:'3',text:'动作',name:"action",index:'1',align:''}
                ],
                rowNum:9999,
                pager : false,
                number:false,
                multiselect: false
            });

        }
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

        /*通用的JS*/
        function setHealthOrAvailableEmergencyForm(monitorType,monitorId,attribute){
            var _monitorType=monitorType;
            var _monitorId=monitorId;
            var _attribute=attribute;
            var temWin = $("body").window({
                "id":"window",
                "title":'配置告警',
                "url":"${ctx}/alarm/manager/configemergency/sub/"+_monitorType+"/"+_monitorId+"/"+_attribute,
                "hasIFrame":true,
                "width": 740,
                "height":440,
                "diyButton":[{
                    "id": "btOne",
                    "btClass": "buttons",
                    "value": "保存",
                    "onclickEvent" : "selectLear",
                    "btFun": function() {
                        var option = $("#window_iframe").contents().find("#graveList option")
                        var _monitorId=$("#window_iframe").contents().find("#monitorId").val();
                        var _attributeId=$("#window_iframe").contents().find("#attributeId").val();
                        var _graveList=$("#window_iframe").contents().find("#graveList option");
                        var _garveIds=[];
                        var _alarmList=$("#window_iframe").contents().find("#alarmList option");
                        var _alarmIds=[];
                        var _normalList=$("#window_iframe").contents().find("#normalList option");
                        var _normalIds=[];
                        var _attributeThresholdId=$("#window_iframe").contents().find("#attributeThreshold option:selected").val();
                        for(var i=0;i<_graveList.length;i++){
                            _garveIds.push(_graveList[i].value);
                        }
                        for(var i=0;i<_alarmList.length;i++){
                            _alarmIds.push(_alarmList[i].value);
                        }
                        for(var i=0;i<_normalList.length;i++){
                            _normalIds.push(_normalList[i].value);
                        }
                        $.ajax({
                            type : "POST",
                            url : "${ctx}/alarm/manager/configemergency/save/"+_monitorId+"/"+_attributeId,
                            dataType : "json",
                            async:false,
                            data : {
                                CRITICAL : _garveIds,
                                WARNING : _alarmIds,
                                INFO : _normalIds,
                                THRESHOLDID:_attributeThresholdId
                            },
                            success : function(dataFromJson) {
                                msgSuccess("系统消息", "保存成功！");
                                    var _resourceTypeAfterUpdate=dataFromJson[0].resourceTypeAfterUpdate;
                                    var _monitorIdAfterUpdate=dataFromJson[0].monitorIdAfterUpdate;
                                    getNewGrid(_resourceTypeAfterUpdate,_monitorIdAfterUpdate);
                            },
                            error:function(){
                                msgAlert("系统消息", "保存失败！");
                             }
                        });
                        temWin.closeWin();
                    }
                },
                    {
                        "id": "btOne",
                        "btClass": "buttons",
                        "value": "关闭",
                        "onclickEvent" : "selectLear",
                        "btFun": function() {
                            temWin.closeWin();
                        }
                    },
                    {
                        "id": "btOne",
                        "btClass": "buttons",
                        "value": "删除配置",
                        "onclickEvent" : "selectLear",
                        "btFun": function() {
                            msgConfirm('系统消息','确定要删除当前的配置吗？',function(){
                                var _monitorId=$("#window_iframe").contents().find("#monitorId").val();
                                var _attributeId=$("#window_iframe").contents().find("#attributeId").val();
                                $.ajax({
                                    type : "POST",
                                    url : "${ctx}/alarm/manager/configemergency/delete/"+_monitorId+"/"+_attributeId,
                                    dataType : "json",
                                    async:false,
                                    success : function(dataFromJson) {
                                        if(dataFromJson != null){
                                            msgSuccess("系统消息", "删除成功！");
                                            var _resourceTypeAfterUpdate=dataFromJson[0].resourceTypeAfterUpdate;
                                            var _monitorIdAfterUpdate=dataFromJson[0].monitorIdAfterUpdate;
                                            getNewGrid(_resourceTypeAfterUpdate,_monitorIdAfterUpdate);
                                        }
                                    },
                                    error:function(){
                                        msgAlert("系统消息", "删除失败！");
                                    }
                                });
                                temWin.closeWin();
                            });
                        }
                    }
                ]
            });
        };

        function setAttributeEmergency(e){
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            /*id前面多了“rows”*/
            var _attributeId=id.substr(4,32);
            var _monitorId=$("#monitorName").val();
            var _monitorType=$("#monitorType").val();
            setHealthOrAvailable(_monitorType,_monitorId,'',_attributeId);
        }
    </script>
</head>

<body>
<div id="layout_top">
    <div class="header">
        <%@ include file="/WEB-INF/layouts/menu.jsp"%>
    </div>
</div>
<div id="layout_center">
    <div class="main" id="main">
        <ul class="crumbs">
            <li><a href="#">管理</a> ></li>
            <li><b>配置告警</b></li>
        </ul>
        <div class="add_monitor set_emergency">
            <h2 class="title2"><b>配置告警</b></h2>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box">
                <tr>
                    <td width="33%"> 选择监视器类型</td>
                    <td><select id="monitorType" name="monitorType"  class="diySelect" style="width:200px">
                        <option value="">--选择一个监视器类型--</option>
                        <option value="APPLICATION">应用系统</option>
                        <option value="DB">数据库</option>
                        <%--<option value="OS">操作系统</option>--%>
                    </select></td>
                </tr>
                <tr>
                    <td> 监视器</td>
                    <td><select id="monitorName" name="monitorName" class="diySelect" style="width:200px" onchange="getNewGrid($($('#monitorType').val(),$('#monitorName').val()))">
                    </select></td>
                </tr>
            </table>
        </div>
        <div class="conf_box">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                健康状态
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li><b>动作：</b>配置动作，当产生告警时配置动作将被执行。</li>
                    </ul>
                    <%--<p class="set_etc"><input type="button" id="Health" class="buttons" onclick="setHealthEmergency($('#monitorType').val(),$('#monitorName').val(),'Health','')" value="配置健康状态" /><span>动作</span></p>--%>
                    <p class="set_etc"><input type="button" id="Health" class="buttons" onclick="setHealthOrAvailable($('#monitorType').val(),$('#monitorName').val(),'Health','')" value="配置健康状态" /><%--<span>动作</span>--%></p>
                </div>
            </div>
        </div>
        <div class="conf_box">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                可用性
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li><b>动作：</b>配置动作，当产生告警时配置动作将被执行。</li>
                    </ul>
                    <%--<p class="set_etc"><input type="button" id="Availability" class="buttons" onclick="setAvailableEmergency($('#monitorType').val(),$('#monitorName').val(),'Availability','')" value="　配置可用性　" /><span class="is_set">动作</span></p>--%>
                    <p class="set_etc"><input type="button" id="Availability" class="buttons" onclick="setHealthOrAvailable($('#monitorType').val(),$('#monitorName').val(),'Availability','')" value="　配置可用性　" /><%--<span>动作</span>--%></p>
                </div>
            </div>
        </div>
        <br />
        <div class="threshold_file">
            <h3 class="title3">配置属性告警：</h3>
            <div id="natureList"></div>
        </div>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>
