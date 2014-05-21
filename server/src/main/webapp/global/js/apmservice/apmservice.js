$(document).ready(function() {
    $("body").layout({
        top:{topHeight:100}
    });

    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center");
        $("#main").width(center.width() - 38).height(center.height() - 30);
    }

    $(".show_all_error").live("click",showAllError);
    $(".hide_some_error").live("click",hideSomeError);

    $("#grid_info_table").Grid({
        url : ctx + "/application/manager/detail/urls/" + applicationId + "?time=" + new Date().getTime(),
        dataType: "json",
        height: 'auto',
        colums:[
            {id:'1',text:'url地址',name:"methodName",width:'',index:'1',align:'',color:''},
            {id:'2',text:'最小响应时间(ms)',name:"minTime",width:'',index:'1',align:'',color:''},
            {id:'3',text:'最大响应时间(ms)',name:"maxTime",width:'',index:'1',align:'',color:''},
            {id:'4',text:'平均响应时间(ms)',name:"avgTime",width:'',index:'1',align:'',color:''},
            {id:'5',text:'健康度',name:"healthBar",width:'',index:'1',align:'',color:''}
        ],
        rowNum:99,
        rowList:[10,20,30],
        pager : false,
        number:false,
        multiselect:false
    });

    function showAllError(){
        $(this).hide();
        $(this).siblings("p").css("overflow","auto");
        $(".hide_some_error").show();
    }
    function hideSomeError(){
        $(this).hide();
        $(".show_all_error").show();
        $(this).siblings("p").css("overflow","hidden");
    }

});
