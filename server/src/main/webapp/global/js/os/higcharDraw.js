Highcharts.setOptions({
	global : {
		useUTC : false
	}
});
function creatSimpleChart(url, renderTo, text,time) {
	$.ajax({
				type : "post",
				url : url,
				async:false,
				dataType : "json",
				cache : false,
				success : function(data) {
					var seriesArr = [];
					for (var name in data) {
						var series={};
						series.name=name;
						series.data=data[name];
						seriesArr.push(series);
					}
				
				new Highcharts.Chart({
					
								chart : {
									renderTo : renderTo,
									type : 'line',
									height : 300
								},
								title : {
									text : ''
								},
								subtitle : {
									text : ''
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
					                   
					                },
								 labels:{
										rotation: -45,
										align: 'right',
										step:1
										}
								},
								yAxis : {
									title : {
										text : text
									}

								},
								tooltip : {
									enabled : true,
									formatter: function() {
										if(time==false){
											return '<b>'+ this.series.name +'</b><br/>'+Highcharts.dateFormat('%H. %M', this.x) +': '+ this.y ;
										}else{
											return '<b>'+ this.series.name +'</b><br/>'+Highcharts.dateFormat('%Y-%m-%d', this.x) +': '+ this.y ;
										}
											
									}

								},
								plotOptions : {
									line : {
										connectNulls : true,
										enableMouseTracking : true
									},
									series: {
			                        	marker: {
			                        		radius: 3
			                        	}
									}
								},
								credits : {
									text : '',
									href : ''
								},
								exporting: {
									enabled: false  //设置导出按钮 true为可用,false为不可用
								},
								series : seriesArr,
								colors : ['#00b200', '#0000b2', '#b200b2']
							});
				
				}
			});
}


