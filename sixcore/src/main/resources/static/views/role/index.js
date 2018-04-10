/**
 * 角色管理
 */
$(document).ready(function(){
	
});
layui.use(['layer','table','element','form'], function(){ 
	  var layer = layui.layer
	  ,table = layui.table
	  ,form = layui.form
	  ,element = layui.element;
	  
	  form.render();
	  
	  table.render({
		  url:'/demo/role/getRoles'
		  ,elem:'#tables'
		  ,size:'sm'
		  ,page:true
		  ,cols:[[
			{field:'id', width:80, sort: true,title:'ID'}
			,{field:'roleName', width:100,title:'角色代码'}
			,{field:'roleDescribe', width:100,title:'角色名'}
			,{fixed: 'right',title:'操作', width:150, align:'center', toolbar: '#toolBar'}
		  ]]
	  	  ,response: {
	  		  statusName: 'status' //数据状态的字段名称，默认：code
			  ,statusCode: 200 //成功的状态码，默认：0
			  ,msgName: 'message' //状态信息的字段名称，默认：msg
			  ,countName: 1000 //数据总数的字段名称，默认：count
			  ,dataName: 'data' //数据列表的字段名称，默认：data
			}
	  });
	  
	//监听工具条
	  table.on('tool', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	    var data = obj.data //获得当前行数据
	    ,layEvent = obj.event; //获得 lay-event 对应的值
	    if(layEvent === 'detail'){
	      layer.msg('查看操作'+data.id);
	    } else if(layEvent === 'del'){
	      layer.confirm('真的删除行么', function(index){
	        obj.del(); //删除对应行（tr）的DOM结构
	        layer.close(index);
	        //向服务端发送删除指令
	      });
	    } else if(layEvent === 'edit'){
	      editRole(data.id)
	    }
	  });
	
	  //监听提交
	  form.on('submit(formDemo)', function(data){
		  submitEdit(data);
		  var roleInfo = JSON.stringify(data.field);
		  $.axiosSubmit({
			  method:'post'
			  ,url:'/demo/role/updateRoleInfo'
			  ,data:roleInfo
			  ,contentType:'application/json'
			  ,callback:function(data){
				  layer.alert("修改成功！",{icon:1});
			  }
		  });
		  return false;
	  });
//====================================================================================================

/**
 * 编辑角色
 * @param id
 */
function editRole(id){
	$.axiosSubmit({
		method:'get',
		url:'/demo/role/findRoleInfoById',
		data:{id:id},
		callback: loadRoleInfo
	});
}
/**
 * 加载角色权限
 * @param data
 */
function loadRoleInfo(data){
	var data = eval('(' + data + ')');
	var result = data.data
	var roleAuths = result.roleAuths;
	roleAuths.push({'id':0,'pid':0,'name':'权限、菜单','open':true});
	$.fn.zTree.init($("#authTrees"), setting, roleAuths);
	$.autoFillForm(result.roleInfo);
	layer.open({
  	  type: 1,
  	  title:"角色详情",
  	  area: ['700px', '480px'], //宽高
  	  content: $("#userInfo")
    });
}

function submitEdit(data){
	var treeObj = $.fn.zTree.getZTreeObj("authTrees");
	var nodes = treeObj.getCheckedNodes(true);
	var subData = data.field;
	var authorities = [];
	for(var i = 0;i<nodes.length;i++){
		var authority = {'id':nodes[i].id,'parentAuthorityId':nodes[i].pid};
		authorities.push(authority);
	}
	subData.authorities=authorities;
}

/**
 * ztree 设置
 */
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: 'id',
				pIdKey: 'pid',
				rootPid: 0
			}
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view: {
			addHoverDom: false,
			removeHoverDom: false,
			selectedMulti: false
		},
		callback: {
		}
		,check: {
			enable: true,
//			chkStyle: "checkbox",
			chkboxType: { "Y": "", "N": "" }
		}
	};


});