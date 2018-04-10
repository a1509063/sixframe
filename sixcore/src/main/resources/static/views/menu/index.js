
	
layui.use(['layer'], function(){
	var layer = layui.layer
	
	$(document).ready(function(){
		loadAuths();
	});
/* ztree setting */
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
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: false
		},
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		callback: {
			beforeRemove: zTreeBeforeRemove,
			onClick: zTreeOnClick
		}
	};
//删除节点
function zTreeBeforeRemove(treeId, treeNode) {
	layer.confirm("确定删除该权限？",function(index){
		$.axiosSubmit({
			method:'post',
			url:'/demo/menu/deleteAuthBuId',
			data:{id:treeId},
			callback: removeNode
		});
		return true;
	});
	return false;
}
function removeNode(treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("authTrees");
	treeObj.removeNode(treeNode);
}

//节点点击事件
function zTreeOnClick(event, treeId, treeNode){
	if(treeNode.level > 0){
		$.axiosSubmit({
			method:'get',
			url:'/demo/menu/findAuthInfoById',
			data:{id:treeNode.id},
			callback: loadAuthInfo
		});
	}
}
//加载返回的权限信息
function loadAuthInfo(data){
	var result = eval('(' + data + ')');
	var authInfo = result.data;
	$("#authInfos").find("form").css("display","block");
	$.autoFillForm(authInfo);
//	$("#id").val(authInfo.id);
//	$("#authorityName").val(authInfo.authorityName);
//	$("#authorityPicture").val(authInfo.authorityPicture);
//	$("#authorityType").val(authInfo.authorityType);
//	$("#authorityDescribe").val(authInfo.authorityDescribe);
}

/**
 * 设置根节点不显示删除按钮
 * @param treeId
 * @param treeNode
 */
function showRemoveBtn(treeId, treeNode){
	if(treeNode.level > 0){
		return true;
	}
	return false;
}

//增加节点
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("authTrees");
		zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};
/* 加载权限树 */
function loadAuths(){
	$.axiosSubmit({
		method:'get',
		url:'/demo/menu/findAuths',
		data:'',
		callback: loadTree
	})
}
function loadTree(data){
	var result = eval('(' + data + ')');
	var auths = result.data;
	auths.push({'id':0,'pid':0,'name':'权限、菜单','open':true});
	$.fn.zTree.init($("#authTrees"), setting, auths);
}

});