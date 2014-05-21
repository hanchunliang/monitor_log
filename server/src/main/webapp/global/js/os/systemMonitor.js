/**
 * 数据库列表视图-列样式
 */
var thresholdColumnStyle = 
	[  
		{id:'1',text:'名称',name:"appellation",index:'1',align:''},
		{id:'2',text:'可用性',name:"appellation",index:'1',align:''},
		{id:'3',text:'健康状态',name:"appellation",index:'1',align:''},
		{id:'4',text:'操作',name:"appellation",index:'1',align:''}
	];
/**
 * 健康状态操控板-最近24小时 列样式
 */
var day1ColumnStyle = 
	[  
		{id:'0',text:'名称',name:"appellation",index:'1',align:'',width:'100'},
		{id:'1',text:'00',name:"hour1",index:'1',align:''},
		{id:'2',text:'01',name:"hour2",index:'1',align:''},
		{id:'3',text:'02',name:"hour3",index:'1',align:''},
		{id:'4',text:'03',name:"hour4",index:'1',align:''},
		{id:'5',text:'04',name:"hour5",index:'1',align:''},
		{id:'6',text:'05',name:"hour6",index:'1',align:''},
		{id:'7',text:'06',name:"hour7",index:'1',align:''},
		{id:'8',text:'07',name:"hour8",index:'1',align:''},
		{id:'9',text:'08',name:"hour9",index:'1',align:''},
		{id:'10',text:'09',name:"hour10",index:'1',align:''},
		{id:'11',text:'10',name:"hour11",index:'1',align:''},
		{id:'12',text:'11',name:"hour11",index:'1',align:''},
		{id:'13',text:'12',name:"hour12",index:'1',align:''},
		{id:'14',text:'13',name:"hour14",index:'1',align:''},
		{id:'15',text:'14',name:"hour15",index:'1',align:''},
		{id:'16',text:'15',name:"hour16",index:'1',align:''},
		{id:'17',text:'16',name:"hour17",index:'1',align:''},
		{id:'18',text:'17',name:"hour18",index:'1',align:''},
		{id:'19',text:'18',name:"hour19",index:'1',align:''},
		{id:'20',text:'19',name:"hour20",index:'1',align:''},
		{id:'21',text:'20',name:"hour21",index:'1',align:''},
		{id:'22',text:'21',name:"hour22",index:'1',align:''},
		{id:'23',text:'22',name:"hour23",index:'1',align:''},
		{id:'24',text:'23',name:"hour24",index:'1',align:''}
	];
/**
 * 健康状态操控板-最近30天 列样式
 */
var day30ColumnStyle = 
	[  
		{id:'0',text:'名称',name:"appellation",index:'1',align:'',width:'100'},
		{id:'1',text:'00',name:"day1",index:'1',align:''},
		{id:'2',text:'01',name:"day2",index:'1',align:''},
		{id:'3',text:'02',name:"day3",index:'1',align:''},
		{id:'4',text:'03',name:"day4",index:'1',align:''},
		{id:'5',text:'04',name:"day5",index:'1',align:''},
		{id:'6',text:'05',name:"day6",index:'1',align:''},
		{id:'7',text:'06',name:"day7",index:'1',align:''},
		{id:'8',text:'07',name:"day8",index:'1',align:''},
		{id:'9',text:'08',name:"day9",index:'1',align:''},
		{id:'10',text:'09',name:"day10",index:'1',align:''},
		{id:'11',text:'10',name:"day11",index:'1',align:''},
		{id:'12',text:'11',name:"day11",index:'1',align:''},
		{id:'13',text:'12',name:"day12",index:'1',align:''},
		{id:'14',text:'13',name:"day14",index:'1',align:''},
		{id:'15',text:'14',name:"day15",index:'1',align:''},
		{id:'16',text:'15',name:"day16",index:'1',align:''},
		{id:'17',text:'16',name:"day17",index:'1',align:''},
		{id:'18',text:'17',name:"day18",index:'1',align:''},
		{id:'19',text:'18',name:"day19",index:'1',align:''},
		{id:'20',text:'19',name:"day20",index:'1',align:''},
		{id:'21',text:'20',name:"day21",index:'1',align:''},
		{id:'22',text:'21',name:"day22",index:'1',align:''},
		{id:'23',text:'22',name:"day23",index:'1',align:''},
		{id:'24',text:'23',name:"day24",index:'1',align:''},
		{id:'25',text:'24',name:"day25",index:'1',align:''},
		{id:'26',text:'25',name:"day26",index:'1',align:''},
		{id:'27',text:'26',name:"day27",index:'1',align:''},
		{id:'28',text:'27',name:"day28",index:'1',align:''},
		{id:'29',text:'28',name:"day29",index:'1',align:''},
		{id:'30',text:'29',name:"day30",index:'1',align:''}
	];


function getForm() {
	 systemMonitorTable("/monitor/os/systemMonitorTable/24");
	$.ajax({
		type : "post",
		url : "/monitor/os/performanceList",
		dataType : "json",
		cache : false,
		success : function(data) {
			for (var formName in data) {
				var seriesArr = [];
				var lineData =  data[formName];
				for(var lineName in lineData){
					var series = {};
					series.name = lineName;
					series.data = lineData[lineName];
					seriesArr.push(series);
				}
				new Highcharts.Chart({
					chart : {
						renderTo : formName,
						type : 'line',
						marginRight : 50,
						marginBottom : 75,
						height : 200
					},
					title : {
						text : ' ',
						x : -20
					// center
					},
					xAxis : {
					    type: 'datetime',
			                dateTimeLabelFormats: { // don't display the dummy year
			                    second: '%Y-%m-%d<br/>%H:%M:%S',
			                    minute: '%Y-%m-%d<br/>%H:%M',
			                    hour: '%Y-%m-%d<br/>%H:%M',
			                    day: '%Y<br/>%m-%d',
			                    week: '%Y<br/>%m-%d',
			                    month: '%Y-%m',
			                    year: '%Y'
			           }
					},
					yAxis : {
						title : {
							text : '%'
						},
						plotLines : false
					},
					plotOptions : {
						line : {
							connectNulls : true,
							enableMouseTracking : true,
						},
						series: {
	                        marker: {
	                            radius: 3
	                        }
	                    }
					},
					tooltip : {
						enabled: true,
						formatter: function() {
	                        return '<b>'+ this.series.name +'</b><br/>'+
	                        Highcharts.dateFormat('%H. %M', this.x) +': '+ this.y ;
						}
					},
					legend : {
						enabled : true
					},
					series : seriesArr
				});
			}
		}
	});
	//healthGrid("/monitor/os/healthList/24");
}
//rootPath + "/os/systemList"

function healthGrid(url) {
	var columnStyle;
	if(url=="/monitor/os/healthList/24"){
		columnStyle=day1ColumnStyle;
	}
	if(url=="/monitor/os/healthList/30"){
		columnStyle=day30ColumnStyle;
	}
	$("#healthList").empty();
	$("#healthList").Grid({
		url : url,
		dataType : "json",
		colDisplay : false,
		clickSelect : true,
		draggable : false,
		height : 'auto',
		colums : columnStyle,
		rowNum : 9999,
		pager : false,
		number : false,
		multiselect : false
	});
}
function systemMonitorTable(url) {
	$.ajax({
			type : "post",
			url : url,
			dataType : "html",
			cache : false,
			success : function(data) {
				$("#systemMonitorTable").html(data);
			}
	});
}

function healthChange(ele) {
	if ($(ele).val() == '24') {
		//healthGrid("/monitor/os/healthList/24");
	}
	if ($(ele).val() == '30') {
		//healthGrid("/monitor/os/healthList/30");
	}
}
//点击可用性下拉
function availableChange(ele) {
	if ($(ele).val() == '24') {
		systemMonitorTable("/monitor/os/systemMonitorTable/24");
	}
	if ($(ele).val() == '30') {
		availableList("/monitor/os/systemMonitorTable/30");
	}
}
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
		};
		subMav.slideDown('fast', function() {
			$(document).bind('click', {
						dom : subMav,
						add : isAdd
					}, hideNav);
			return false;
		});
	};
}
function hideNav(e) {
	var subMenu = e.data.dom;
	var isAdd = e.data.add;
	subMenu.slideUp('fast', function() {
		if (isAdd) {
			subMenu.parent().removeClass('seleck');
		};
	});
	$(document).unbind();
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
			url: rootPath+"/os/remove/",
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
				url: rootPath+"/os/remove",
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
setInterval(getForm, 1000 * 10 * 60);