var navs = new Vue({
	el: '#upload_file',
	data: function() {
		return {
			file: {}, //当前上传文件对象
			percent: 0, //上传进度百分比
			percentType: "#409EFF", //进度条颜色 蓝：warning 绿：success
			fileGroupInfo: {
				projectName: "", //项目名称
				latestVersion: "", //当前版本
			},
			form: {
				name: '', //应用名称
				icon: "", //应用图标
				size: "", //文件大小
				versionCode: "", //版本号
				versionName: "", //版本号名称
				packageId: "", //应用标识
				publishMoment: 0, //0立即发布 1定时发布
				publistDate: "", //发布日期
				publistTime: "", //发布时间
				mome: '', //提交说明
			}
		}
	},
	methods: {
		/* 上传文件中 */
		uploading() {
			console.log("上传文件中");
		},
		/* 上传文件前 */
		beforeUpload() {
			console.log("上传文件前");
		},
		/* 自定义上传实现 */
		upload(file) {
			const that = this
			that.percentType = "#409EFF"
			file = file.file;
			const chunkSize = 1024 * 1024; // 每个块的大小，1MB
			const totalChunks = Math.ceil(file.size / chunkSize); // 总块数
			let currentChunk = 0; // 当前上传的块索引
			// 递归上传每一个文件块
			const uploadChunk = () => {
				const start = currentChunk * chunkSize; // 当前块的起始位置
				const end = Math.min(start + chunkSize, file.size); // 当前块的结束位置
				const chunk = file.slice(start, end); // 获取文件的当前块
				// 创建 FormData 对象，用于封装上传的文件块
				const formData = new FormData();
				formData.append('file', chunk); // 将文件块添加到 FormData 中
				formData.append('fileName', file.name); // 附加文件名信息
				formData.append('currentChunk', currentChunk); // 当前块的索引
				formData.append('totalChunks', totalChunks); // 文件总块数，用于告知服务器上传进度
				formData.append('userId', 1); // 用户id，用于生成临时文件地址，防止覆盖
				// 创建 XMLHttpRequest 对象发送文件块
				const xhr = new XMLHttpRequest();
				xhr.open('POST', window.url + "/apk_file/upload", true); // 设置请求类型为 POST
				// 监听上传进度
				xhr.upload.addEventListener('progress', (event) => {
					if (event.lengthComputable) {
						// 计算并输出当前上传进度
						const progress = (currentChunk / totalChunks) * 100 + (event.loaded / event
							.total) * (100 / totalChunks);
						console.log(`上传进度: ${Math.round(progress)}%`); // 显示上传进度
						that.percent = Math.round(progress)
					}
				});
				// 上传成功后的回调
				xhr.onload = () => {
					var result = JSON.parse(xhr.responseText)
					currentChunk++; // 更新当前块索引
					if (currentChunk < totalChunks) {
						uploadChunk(); // 递归调用上传下一个块
					} else {
						console.log("文件上传完成！"); // 所有文件块上传完毕，打印完成消息
						that.percentType = "#67C23A"
						that.form.icon = window.url + "/apk_file/file_temp?userId=" + 1 + "&fileName=" +
							result["data"]["icon"]
						that.form.name = result["data"]["packageName"]
						that.form.size = result["data"]["size"]
						that.form.versionCode = result["data"]["versionCode"]
						that.form.versionName = result["data"]["versionName"]
						that.form.packageId = result["data"]["packageId"]
						that.queryFileGroupByPackageId(result["data"]["packageId"])
					}
				};
				// 上传失败的回调
				xhr.onerror = () => {
					console.error(`上传第 ${currentChunk + 1} 个块失败，错误信息：${xhr.statusText}`); // 上传失败后打印错误信息
					that.percentType = "#F56C6C"
				};
				// 发送文件块
				xhr.send(formData);
			};
			// 开始上传第一个块
			uploadChunk();
		},
		// 查询文件组信息
		queryFileGroupByPackageId(packageId) {
			const that = this
			const xhr = new XMLHttpRequest();
			const formData = new FormData();
			formData.append('packageId', packageId); // 将文件块添加到 FormData 中
			xhr.open('POST', window.url + "/apk_file/query_group_info_by_packageid", true); // 设置请求类型为 POST
			xhr.onload = () => {
				var result = JSON.parse(xhr.responseText)
				console.log(result);
				that.fileGroupInfo["projectName"] = result["data"]["name"]
				that.fileGroupInfo["latestVersion"] = result["data"]["latestVersion"]
			};
			xhr.onerror = () => {
				console.log("失败");
				var result = JSON.parse(xhr.responseText)
				console.log(result);
			};
			xhr.send(formData);
		},
		// 发布
		onSubmit() {

		},
		// 取消
		cancel() {

		},
	}
})