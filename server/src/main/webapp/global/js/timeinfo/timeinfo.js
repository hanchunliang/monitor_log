
var responseTimeChart;
var visitNumberChart;
$(document).ready(function() {
    $("body").layout({
        top:{topHeight:100}
    });
    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center");
        $("#main").width(center.width() - 38).height(center.height() - 30);
    }
    $("#list_table").Grid({
        url : ctx + "/application/manager/url/methodresponsetime/" + applicationId + "/" + urlId + "?time=" + new Date().getTime(),
        dataType: "json",
        height: 'auto',
        colums:[
            {id:'1',text:'方法名称',name:"methodName",width:'400',index:'1',align:'center',color:''},
            {id:'2',text:'最大响应时间(ms)',name:"maxTime",width:'',index:'1',align:'center',color:''},
            {id:'3',text:'最小响应时间(ms)',name:"minTime",width:'',index:'1',align:'center',color:''},
            {id:'4',text:'平均响应时间(ms)',name:"avgTime",width:'',index:'1',align:'center',color:''}
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager : true,
        number:false,
        multiselect:false
    });

    $("#event_log_grid").Grid({
        url : ctx + "/application/manager/url/tracelog/" + urlId + "?time=" + new Date().getTime(),
        dataType: "json",
        height: 'auto',
        type : 'POST',
        colums:[
            {id:'1',text:'IP',name:"userIp",width:'400',index:'1',align:'',color:''},
            /*{id:'2',text:'访问者',name:"visitor",width:'',index:'1',align:'',color:''},*/
            {id:'3',text:'时间',name:"recordTime",width:'',index:'1',align:'',color:''},
            {id:'4',text:'状态',name:"status",width:'',index:'1',align:'',color:''},
            {id:'5',text:'操作',name:"operate",width:'',index:'1',align:'',color:''}
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager : true,
        number:false,
        multiselect:false
    });

    initUrlHealthAndAva();
    initUrlCountSta();

    $("#tabs").tabs({closeTab:false});
});

function initUrlHealthAndAva() {
    $.ajax({
        url : ctx + "/application/manager/url/healthava/" + applicationId + "/" + urlId + "?time=" + new Date().getTime(),
        type : "GET",
        dataType : "json",
        async : false,
        success : function(data) {
            var times = data.times;
            var urlHealths = data.urlHealths;
            var urlAvas = data.urlAvas;

            var availabilityBar = $("#availabilityBar");
            var availabilityTime = $("#availabilityTime");
            var healthBar = $("#healthBar");
            var healthTime = $("#healthTime");

            availabilityBar.html("");
            availabilityTime.html("");
            healthBar.html("");
            healthTime.html("");

            for(var i = 0, len=times.length; i<len; i++) {
                var time = times[i];
                var urlHealth = urlHealths[time];
                var urlHealthCss = "normal";
                if(urlHealth == "CRITICAL") {
                    urlHealthCss = "serious";
                } else if(urlHealth == "WARNING") {
                    urlHealthCss = "warning";
                } else if(urlHealth == "INFO") {
                    urlHealthCss = "normal";
                }

                urlHealthCss += " mark_show"

                healthTime.append("<td><b>" + time + "</b></td>");
                healthBar.append("<td ><div class=" + urlHealthCss + "></div></td>");

                var urlAva = urlAvas[time];

                var avaCount = 0;
                var failCount = 0;

                if(urlAva && urlAva != "") {
                    var urlAvaArray = urlAva.split(":");
                    avaCount = urlAvaArray[0];
                    failCount = urlAvaArray[1];
                }

                if(avaCount == 0 && failCount == 0) {
                    //modified by hanchunliang
                    //failCount = 100;
                    avaCount = 100;
                }
                availabilityBar.append("<li class=\"show_bar\"><div class=\"ky\" style=\"width:" + avaCount + "%\"></div><div class=\"bky\" style=\"width:" + failCount + "%\"></div></li>");
                availabilityTime.append(" <li>" + time + "</li>");
            }

        },
        error : function() {
            msgSuccess("系统消息", "URL 可用性数据以及健康度数据加载失败！");
        }

    });
}


function initUrlCountSta() {
    $.ajax({
        url : ctx + "/application/manager/url/urlcountsta/" + applicationId + "/" + urlId + "?time=" + new Date().getTime(),
        type : "GET",
        dataType : "json",
        async : false,
        success : function(data) {
            var times = data.times;
            var urlResponseTimes = data.urlResponseTimes;
            var urlVisitNumbers = data.urlVisitNumbers;

            var responseTimeArray = [];
            var visitNumberArray = [];

            for(var i= 0, len=times.length; i<len; i++) {
                var time = times[i];
                var responseTime = urlResponseTimes[time];
                responseTime = (responseTime) ? responseTime : 0;

                responseTimeArray.push(responseTime);

                var visitNumber = urlVisitNumbers[time];
                visitNumber = (visitNumber) ? visitNumber : 0;
                visitNumberArray.push(visitNumber);
            }
            createResponseTimeChart(times, responseTimeArray);
            createVisitNumberChart(times, visitNumberArray);

        },
        error : function() {
            msgSuccess("系统消息", "URL 响应时间以及访问数量加载失败！");
        }

    });
}

function createVisitNumberChart(times, data) {
    responseTimeChart = new Highcharts.Chart({
        chart: {
            renderTo: 'time_times',
            type: 'line',
            marginRight: 130,
            marginBottom: 25
        },
        title: {
            text: 'URL访问次数',
            x: -20 //center
        },
        xAxis: {
            categories: times
        },
        yAxis: {
            title: {
                text: '访问次数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            min : 0
        },
        plotOptions:{
            line:{              // 数据点的点击事件
                events:{
                    click: function(event){
//                        alert('The bar was clicked, and you can add any other functions.');
                    }
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                    + this.y +'次';
            }
        },
        legend: {
            enabled :false

        },
        series: [{
            name: '访问次数',
            data: data
        }]
    });
}

function createResponseTimeChart(times, data) {
    visitNumberChart = new Highcharts.Chart({
        chart: {
            renderTo: 'time_response_time',
            type: 'line',
            marginRight: 130,
            marginBottom: 25
        },
        title: {
            text: 'URL响应时间',
            x: -20 //center
        },
        xAxis: {
            categories: times
        },
        yAxis: {
            title: {
                text: '响应时间(ms)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            min : 0
        },
        plotOptions:{
            line:{              // 数据点的点击事件
                events:{
                    click: function(event){
//                        alert('The bar was clicked, and you can add any other functions.');
                    }
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                    + this.y +'ms';
            }
        },
        legend: {
            enabled :false

        },
        series: [{
            name: '响应时间',
            data: data
        }]
    });
}

function operateDetail(id) {
    location.href = ctx + "/application/manager/appmethod/viewLogDetail/" + applicationId + "/" + urlId + "/" + id + "?time=" + new Date().getTime();
}