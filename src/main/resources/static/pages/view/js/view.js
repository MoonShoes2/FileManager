let navs = null //左侧导航栏组件
let tabs = null //标签页组件
//标签页面导航
const pageTabs = {
	"home": { //标签名
		"label": "首页", //标签别名
		"url": "http://127.0.0.1:8848/static/pages/tools/tools.html" //标签页地址
	},
	"panel": { //标签名
		"label": "面板", //标签别名
		"url": "http://127.0.0.1:9607/FileManager/panel" //标签页地址
	},
	"app_manager": {
		"label": "手机安装包",
		// "url": "http://127.0.0.1:9607/FileManager/appManager",
		"url": "http://127.0.0.1:8848/static/pages/appmanager/appmanager.html",
	},
	"file_database": {
		"label": "数据库文件",
		"url": "http://127.0.0.1:8848/static/pages/appmanager/appmanager.html"
	},
	"file_folder": {
		"label": "文件夹",
		"url": "http://127.0.0.1:8848/static/pages/appmanager/appmanager.html"
	},
	"file_other": {
		"label": "其他文件",
		"url": "http://127.0.0.1:8848/static/pages/tools/tools.html"
	},
	"database": {
		"label": "数据库",
		"url": "http://127.0.0.1:8848/static/pages/tools/tools.html"
	},
	"folder": {
		"label": "文件夹",
		"url": "http://127.0.0.1:8848/static/pages/tools/tools.html"
	},
	"tools": {
		"label": "工具箱",
		"url": "http://127.0.0.1:8848/static/pages/tools/tools.html"
	}
}

// 左侧菜单
navs = new Vue({
	el: 'nav',
	data: function() {
		return {
			isCollapse: false, // 菜单是否折叠
		}
	},
	methods: {
		handleSelect(key, keyPath) {
			tabs.addTab(key)
		}
	}
})

// 右侧标签页
tabs = new Vue({
	el: '#tabs',
	data: function() {
		return {
			editableTabsValue: "home", //选择了哪一个标签页
			editableTabs: [],
			fileList: [], //选中的文件列表
		}
	},
	methods: {
		/* 添加标签页 */
		addTab(key) {
			if (!this.editableTabs.some(item => item.name === key)) {
				this.editableTabs.push({
					name: key,
					src: pageTabs[key].url,
					label: pageTabs[key].label,
				});
			}
			this.editableTabsValue = key // 选中
		},
		/* 删除标签页 */
		removeTab(key) {
			const index = this.editableTabs.findIndex(tab => tab.name === key);
			if (index !== -1) {
				this.editableTabs.splice(index, 1)
				const nextTab = this.editableTabs[index] || this.editableTabs[index - 1];
				if (nextTab && key === this.editableTabsValue) {
					this.editableTabsValue = nextTab.name
				}
			}
		},
		/* 上传文件中 */
		uploading() {
			console.log("上传文件");
		},
	}
})

tabs.addTab("app_manager") //页面启动点击正在做的页面