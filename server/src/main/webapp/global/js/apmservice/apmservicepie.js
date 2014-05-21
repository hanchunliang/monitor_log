var availabilityChart;
var healthChart;
$(document).ready(function() {
    var availabilityValue = $("#availabilityValue").val();
    var availabilityCounts = availabilityValue.split(":");
    availabilityChart = new Highcharts.Chart({
        chart: {
            renderTo: 'pie_availability',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ''
        },
                    credits: {
                        text: '',
                        href: ''
                    },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(0) +' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                {
                    name: '不可用',
                    y: parseInt(availabilityCounts[1]),
                    sliced: false,
                    selected: false
                },
                ['可用',parseInt(availabilityCounts[0])]
            ]
        }],
                    colors: ['#Ff4f4f', '#5cff5c']
    });

    var healthValue = $("#healthValue").val();
    var healthCounts = healthValue.split(":");
    healthChart = new Highcharts.Chart({
        chart: {
            renderTo: 'pie_health',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ''
        },
                    credits: {
                        text: '',
                        href: ''
                    },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(0) +' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                {
                    name: '严重',
                    y: parseInt(healthCounts[0]),
                    sliced: false,
                    selected: false
                },
                ['警告', parseInt(healthCounts[1])],
                ['正常', parseInt(healthCounts[2])]

            ]
        }],
        colors: ['#Ff4f4f','#f6a107', '#5cff5c']
    });
});
