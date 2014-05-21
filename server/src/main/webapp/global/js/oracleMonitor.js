/**
 * 健康状态操控板-最近24小时 列样式
 */
var day1ColumnStyle = 
	[  
		{id:'0',text:'名称',name:"appellation",index:'1',align:'',width:'100'},
		{id:'1',text:'01',name:"hour1",index:'1',align:''},
		{id:'2',text:'02',name:"hour2",index:'1',align:''},
		{id:'3',text:'03',name:"hour3",index:'1',align:''},
		{id:'4',text:'04',name:"hour4",index:'1',align:''},
		{id:'5',text:'05',name:"hour5",index:'1',align:''},
		{id:'6',text:'06',name:"hour6",index:'1',align:''},
		{id:'7',text:'07',name:"hour7",index:'1',align:''},
		{id:'8',text:'08',name:"hour8",index:'1',align:''},
		{id:'9',text:'09',name:"hour9",index:'1',align:''},
		{id:'10',text:'10',name:"hour10",index:'1',align:''},
		{id:'11',text:'11',name:"hour11",index:'1',align:''},
		{id:'12',text:'11',name:"hour11",index:'1',align:''},
		{id:'13',text:'12',name:"hour12",index:'1',align:''},
		{id:'14',text:'14',name:"hour14",index:'1',align:''},
		{id:'15',text:'15',name:"hour15",index:'1',align:''},
		{id:'16',text:'16',name:"hour16",index:'1',align:''},
		{id:'17',text:'17',name:"hour17",index:'1',align:''},
		{id:'18',text:'18',name:"hour18",index:'1',align:''},
		{id:'19',text:'19',name:"hour19",index:'1',align:''},
		{id:'20',text:'20',name:"hour20",index:'1',align:''},
		{id:'21',text:'21',name:"hour21",index:'1',align:''},
		{id:'22',text:'22',name:"hour22",index:'1',align:''},
		{id:'23',text:'23',name:"hour23",index:'1',align:''},
		{id:'24',text:'24',name:"hour24",index:'1',align:''}
	];
/**
 * 健康状态操控板-最近30天 列样式
 */
var day30ColumnStyle = 
	[  
		{id:'0',text:'名称',name:"appellation",index:'1',align:'',width:'100'},
		{id:'1',text:'01',name:"day1",index:'1',align:''},
		{id:'2',text:'02',name:"day2",index:'1',align:''},
		{id:'3',text:'03',name:"day3",index:'1',align:''},
		{id:'4',text:'04',name:"day4",index:'1',align:''},
		{id:'5',text:'05',name:"day5",index:'1',align:''},
		{id:'6',text:'06',name:"day6",index:'1',align:''},
		{id:'7',text:'07',name:"day7",index:'1',align:''},
		{id:'8',text:'08',name:"day8",index:'1',align:''},
		{id:'9',text:'09',name:"day9",index:'1',align:''},
		{id:'10',text:'10',name:"day10",index:'1',align:''},
		{id:'11',text:'11',name:"day11",index:'1',align:''},
		{id:'12',text:'11',name:"day11",index:'1',align:''},
		{id:'13',text:'12',name:"day12",index:'1',align:''},
		{id:'14',text:'14',name:"day14",index:'1',align:''},
		{id:'15',text:'15',name:"day15",index:'1',align:''},
		{id:'16',text:'16',name:"day16",index:'1',align:''},
		{id:'17',text:'17',name:"day17",index:'1',align:''},
		{id:'18',text:'18',name:"day18",index:'1',align:''},
		{id:'19',text:'19',name:"day19",index:'1',align:''},
		{id:'20',text:'20',name:"day20",index:'1',align:''},
		{id:'21',text:'21',name:"day21",index:'1',align:''},
		{id:'22',text:'22',name:"day22",index:'1',align:''},
		{id:'23',text:'23',name:"day23",index:'1',align:''},
		{id:'24',text:'24',name:"day24",index:'1',align:''},
		{id:'25',text:'25',name:"day25",index:'1',align:''},
		{id:'26',text:'26',name:"day26",index:'1',align:''},
		{id:'27',text:'27',name:"day27",index:'1',align:''},
		{id:'28',text:'28',name:"day28",index:'1',align:''},
		{id:'29',text:'29',name:"day29",index:'1',align:''},
		{id:'30',text:'30',name:"day30",index:'1',align:''}
	];

/**
 * 数据库列表视图-列样式
 *
var thresholdColumnStyle = 
	[  
		{id:'1',text:'名称',name:"appellation",index:'1',align:''},
		{id:'2',text:'可用性',name:"appellation",index:'1',align:''},
		{id:'3',text:'健康状态',name:"appellation",index:'1',align:''},
		{id:'4',text:'操作',name:"appellation",index:'1',align:''}
	];
/

/**
 * 可用性历史纪录- oracle
 * avaInfoStyle的onchange事件
 */
function avaInfoList() {
	/* 可用性显示范围:1->最近24小时；30->最近30天*/
	var avaInfoStyle = $("#avaInfoStyle").val();
	$.ajax({
		type:"get",
		dataType: "html",
		url : rootPath+"/db/oracle/avaInfoList/"+avaInfoStyle,  
		success:function(msg) {
			$("#avaInfoList").html(msg);
		}
	});
}

/**
 * 健康状态操控板
 */
function healthList() {
	var healthListStyle = $("#healthListStyle").val();
	var columnStyle;
	switch (healthListStyle) {
	case '1': //最近24小时
		columnStyle=day1ColumnStyle;
		break;
	case '30': //最近30天
		columnStyle=day30ColumnStyle;
		break;
	default: //默认返回24小时
		columnStyle=day1ColumnStyle;
	break;
	}
	
	$("#healthList").html("");
	$("#healthList").Grid({
		url : rootPath+"/db/oracle/healthList/"+healthListStyle,  
		dataType: "json",
		colDisplay: false,  
		clickSelect: true,
		draggable:false,
		height: 'auto',  
		colums: columnStyle,  
		rowNum:9999,
		pager : false,
		number:false,  
		multiselect: false  
	});
	wrapP();
}

/**
 * 删除一条记录
 * @param e
 */
function delRow(e){
	var rows = $(e).parent().parent();
	var rowId = rows.attr('id');
	msgConfirm('系统消息','确定要删除该条记录吗？',function(){
		$.ajax({
			url: rootPath+"/db/oracle/remove",
			type: "get",
			dataType: "json",
			data: {"monitorIds":rowId.split('_')[1]},
			success: function(msg) {
				if(msg.result) {
					msgSuccess("系统消息", "操作成功，记录已删除！");
					rows.remove();
				}
			}
		});
	});
}

/**
 * 批量删除记录
 */
function batchDel(){
	var $g = $("#thresholdList div.grid_view > table");
	var selecteds = $("td.multiple :checked",$g);
	if(selecteds.length > 0){
		msgConfirm('系统消息','确定要删除该条记录吗？',function(){
			var checks = [];
			var monitorIds = [];
			selecteds.each(function(){
				var rows = $(this).parent().parent();
				var rowId = rows.attr('id');
				monitorIds.push(rowId.split('_')[1]);
				checks.push(rows);
			});
			$.ajax({
				url: rootPath+"/db/oracle/remove",
				type: "get",
				dataType: "json",
				data: {"monitorIds":monitorIds.toString()},
				success: function(msg) {
					if(msg.result) {
						$(checks).each(function(i,d){
							$(this).remove();
						});
						msgSuccess("系统消息", "操作成功，记录已删除！");
					}
				}
			});
		});
	}else{
		msgAlert('系统消息','没有选中的记录！<br />请选择要删除的记录后，继续操作。')
	};
}

function buildHighchart(_highChart) {
    var _series = [];
    var s = _highChart.series;
    for(var i=0;i<s.length;i++){
        var iterm = {};
        iterm.name = s[i].name;
        var _data = s[i].data;
        var datai = [];
        for(var j=0;j<_data.length;j++){
            var  d = _data[j];
            var dataj = [];
            dataj[0] = eval(d[0]);
            dataj[1] = parseFloat(d[1]) ;
            datai[j] =  dataj;
        }
        iterm.data = datai;
        _series[i] = iterm;
    }
	new Highcharts.Chart({
		chart: {
		    renderTo: _highChart.renderId,
		    type: 'line',
		    marginRight: 50,
		    marginBottom: 75,
			height:200,
            width:650
		},
		title: {
		    text: ' ',
		    x: -20 //center
		},
//		xAxis: {
//		    categories: xData,
//		    labels: {
//             //修改步长为自动调整 modified by hanchunliang 3013-03-26
//           	 step:_highChart.step
//			 }
//		},
        xAxis: {
            labels: {
                formatter: function() {
                    return  Highcharts.dateFormat('%H:%M', this.value);
                },
                step:_highChart.step
            }
        },
		yAxis: {
		    title: {
		        text: ''
	        },
	        plotLines: false
		},
		plotOptions:{
			series: {
                marker: {
                    radius: 3
                }
            }
		},
		credits: { 
		    text: '',
		    href: ''
		},
		tooltip:{
            enabled:true,
            formatter:function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%H:%M', this.x) + ': ' + this.y.toFixed(2);
            }
        },
		legend: {
			enabled :true
		},
		series: _series
	});
}
