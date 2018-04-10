/**rootPath 需修改为项目根路径名对应controller包的html文件应该以views视为根目录创建*/
$.extend({
	//页面路由
	router:function(contents){
		console.log(contents);
		var rootPath = "/demo";
		var data = eval('(' + contents + ')');
		var results;
		if(data != null){
			var url = data.path;
			if(url != null){
				url = rootPath + "/views" + url.substring(url.indexOf(rootPath)+rootPath.length,url.length) + ".html";
			}
			$.ajax({ 
				async:false, 
				url : url, 
				success : function(result){ 
					results = result;
				} 
			}); 
		}
		return results;
	},
	
	//ajax提交
	axiosSubmit:function(options){
		var layerIndex = layer.msg('处理中...', {
			  icon: 16
			  ,time:3000
			  ,shade: 0.05
		});
		var config = $.extend({
			method: 'get'
			,url: 'index.html'
			,contentType: 'application/x-www-form-urlencoded; charset=UTF-8'
			,data: {}
			,params: {}
			,timeout:60000
			,backToLogin:false
		},options);
		var access_token = localStorage.getItem('access_token');
		axios({
		  method: config.method
		  ,url: config.url
		  ,headers:{
			  'Authorization': access_token
			  ,'Content-Type': config.contentType
		  }
		  ,params: config.data
		  ,data: config.data
		  ,timeout: config.timeout
		}).then(function (response) {
			layer.close(layerIndex);
			var data = response.data;
			if(data.status != null && data.status != "200"){
				if(config.backToLogin){
					layer.alert(data.message,{icon:2,title:'警告'},function(index){
						localStorage.removeItem('access_token');
						localStorage.removeItem('refresh_token');
						location.href = "login.html";
					});
				}else{
					layer.alert(data.message,{icon:2,title:'警告'});
				}
				return false;
			}else if(data.status != null){
				var result = JSON.stringify(data);
				config.callback(result);
				return true;
			}
		}).catch(function (error) {
			console.log(error + "===" + layerIndex);
			layer.close(layerIndex);
			layer.alert("服务器异常，请稍后再试！",{icon:2,title:'警告'});
			return false;
		});
	},
	
	/**
	 * 自动填充表单内容
	 */
	autoFillForm:function(data){
		for(var key in data){
			$("#"+key).val(data[key]);
		}
	}
});