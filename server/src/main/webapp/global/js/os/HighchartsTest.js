$(function() {
			getForm();
		});
setInterval(getForm, 1000 * 10*60);
function getForm() {
	$.ajax({
				type : "POST",
				url :   "/monitor/os/performanceList",
				dataType : "json",
				cache : false,
				success : function(data) {
					for (var formName in data) {
						var series = [];
						for (var name in data[formName]) {
							var categories = [];
							var i = 0, x = [], y = {
								data : []
							};
							while (i < data[formName][name].length) {
								if (i == 0) {
									y.name = name;
								}
								categories.push(data[formName][name][i].x);
								if(data[formName][name][i].y==-1){
									data[formName][name][i].y=null;
								}
								y.data.push(data[formName][name][i].y);
								i += 1;
							}
							series.push(y);
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
										categories : categories
									},
									yAxis : {
										title : {
											text : '%'
										},
										plotLines : false
									},
									plotOptions : {
										line : {
											dataLabels : {
												enabled : true
											},
											connectNulls : true,
											enableMouseTracking : false,
											marker : {
												enabled : false
											}
										}
									},
									credits : {
										text : '',
										href : ''
									},
									tooltip : {
										formatter : function() {
											return this.y + "%";
										}
									},
									legend : {
										enabled : true
									},
									series : series
								});
					}
				}
			});
}
