layui.use(['table','laydate','element','form'], function(){
	  var table = layui.table
	  ,laydate = layui.laydate
	  ,element = layui.element
	  ,form = layui.form; // 元素操作
	  
	  table.render({
		  url:'/sixcore/dataSource/currentDataSource'
		  ,elem:'#tables'
		  ,page:true
		  ,even:true
		  ,limit:1
		  ,cols:[[
			{field:'dataSourceName', width:120, sort: true,title:'数据源名'}
			,{field:'driverClassName', width:120,title:'driverClass'}
			,{field:'url', width:300,title:'url'}
			,{field:'username', width:80,title:'用户名'}
			,{field:'password', width:80,title:'密码'}
			,{field:'filters', width:80,title:'过滤器'}
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
	      if(editUser(data.id)){
	    	  layer.open({
		    	  type: 1,
		    	  title:"用户详情",
		    	  area: ['480px', '480px'], // 宽高
		    	  content: $("#dataSourceInfo"),
		    	  offset:'50px'
		    	});
	      }
	    }
	  });
	  
	  // 日期
	  laydate.render({
	    elem: '#date'
	  });
	
	  form.on('radio', function(data){
      });
	  
	  form.on('submit(dataSourceInfo)', function(data){
		  console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
		  console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
		  console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
		  addDataSource(data);
		  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
	  
	/** 编辑 */
	function editUser(userId){
		return $.axiosSubmit({
			url: '/sixcore/dataSource/dataSourceInfo'
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
	}
	
	/**新增*/
	function addDataSource(data){
		$.axiosSubmit({
			url:'/sixcore/dataSource/addDataSource'
			,data:data.field
			,method:'post'
			,callback:function(response){
				var result = eval("(" + response + ")");
				var status = result.status;
				if(status == '200'){
					layer.alert("新增成功！",function(){
						location.reload();
					});
					layer.close(currentIndex);
				}
			}
		});
	}
});
var currentIndex;
function openAdd(){
	currentIndex = layer.open({
		type: 1,
		title:"新增数据源",
		area: ['480px', '480px'], // 宽高
		content: $("#dataSourceInfo"),
		offset:'50px'
//		,end: function(){
//			location.reload();
//		}
	});
}
