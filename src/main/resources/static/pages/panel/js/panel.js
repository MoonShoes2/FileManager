function box_file_count() {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('box_file_count'));

	// 指定图表的配置项和数据
	var option = {
		title: {
			text: '文件总量：1024'
		},
		tooltip: {
			trigger: 'item'
		},
		legend: {
			left: 'left', // 图例放置在左侧
			top: 'center', // 图例垂直居中
			orient: 'vertical' // 设置图例垂直排列
		},
		series: [{
			name: 'Access From',
			type: 'pie',
			radius: ['40%', '70%'],
			avoidLabelOverlap: false,
			itemStyle: {
				borderRadius: 10,
				borderColor: '#fff',
				borderWidth: 2
			},
			label: {
				show: false,
				position: 'center'
			},
			emphasis: {
				label: {
					show: true,
					fontSize: 20,
					fontWeight: 'bold'
				}
			},
			labelLine: {
				show: false
			},
			data: [{
					value: 1048,
					name: '其他文件'
				},
				{
					value: 735,
					name: 'apk文件'
				},
				{
					value: 580,
					name: '数据库文件'
				}
			]
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function box_download_count() {
	var chartDom = document.getElementById('box_download_count');
	var myChart = echarts.init(chartDom);
	var option = {
		title: {
			text: '近7日下载量'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine'],
			top: "8%"
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
		},
		yAxis: {
			type: 'value'
		},
		series: [{
				name: 'Email',
				type: 'line',
				stack: 'Total',
				data: [120, 132, 101, 134, 90, 230, 210]
			},
			{
				name: 'Union Ads',
				type: 'line',
				stack: 'Total',
				data: [220, 182, 191, 234, 290, 330, 310]
			},
			{
				name: 'Video Ads',
				type: 'line',
				stack: 'Total',
				data: [150, 232, 201, 154, 190, 330, 410]
			},
			{
				name: 'Direct',
				type: 'line',
				stack: 'Total',
				data: [320, 332, 301, 334, 390, 330, 320]
			},
			{
				name: 'Search Engine',
				type: 'line',
				stack: 'Total',
				data: [820, 932, 901, 934, 1290, 1330, 1320]
			}
		]
	};
	option && myChart.setOption(option);
}

box_file_count()
box_download_count()