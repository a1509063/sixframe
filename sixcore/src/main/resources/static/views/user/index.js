layui.use(['table','laydate','element','form'], function(){
	  var table = layui.table
	  ,laydate = layui.laydate
	  ,element = layui.element
	  ,form = layui.form; // 元素操作
	  
	  table.render({
		  url:'/demo/user/getUserLists'
		  ,elem:'#tables'
		  ,page:true
		  ,size:'sm'
		  ,cols:[[
			{field:'id', width:80, sort: true,title:'ID'}
			,{field:'userName', width:80,title:'用户名'}
			,{fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#toolBar'}
		  ]]
	  });
	  form.render();
	  // 监听工具条
	  table.on('tool', function(obj){ // 注：tool是工具条事件名，test是table原始容器的属性
										// lay-filter="对应的值"
	    var data = obj.data // 获得当前行数据
	    ,layEvent = obj.event; // 获得 lay-event 对应的值
	    if(layEvent === 'detail'){
	      layer.msg('查看操作'+data.id);
	    } else if(layEvent === 'del'){
	      layer.confirm('真的删除行么', function(index){
	        obj.del(); // 删除对应行（tr）的DOM结构
	        layer.close(index);
	        // 向服务端发送删除指令
	      });
	    } else if(layEvent === 'edit'){
	      editUser(data.id);
	      layer.open({
	    	  type: 1,
	    	  title:"用户详情",
	    	  area: ['480px', '480px'], // 宽高
	    	  content: $("#userInfo"),
	    	  offset:'50px'
	    	});
	    }
	  });
	  
	  // 日期
	  laydate.render({
	    elem: '#date'
	  });
	
	  form.on('radio', function(data){
      });
	  
	/** 编辑 */
	function editUser(userId){
		$.axiosSubmit({
			url: '/demo/user/findUserInfoById'
			,data:{
			  id:userId
			}
			,callback:function(response){
				var result = eval("(" + response + ")");
				var data = result.data;
				console.log(data);
				var userInfo = data.userInfo;
				$("input[name=userName]").val(userInfo.userName);
				if(!userInfo.accountNonLocked){
					$("input[name=accountNonLocked]").attr("checked",true);
					form.render("checkbox");
				}else{
					$("input[name=accountNonLocked]").attr("checked",false);
					form.render("checkbox");
				}
			}
		})
//		axios({
//		  method: 'get',
//		  url: '/demo/user/findUserInfoById',
//		  headers:{
//			  'Authorization': access_token
//		  },
//		  params:{
//			  id:userId
//		  }
//		}).then(function (response) {
//			
//		}).catch(function (error) {
//			alert(error);
//		    console.log(error);
//		});
	}
});