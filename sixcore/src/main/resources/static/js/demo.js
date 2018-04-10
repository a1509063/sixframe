$(document).ready(function(){
	$(".side-menu").on("mouseover","ul li",function(){
		$(this).siblings(".active").removeClass("active");
		$(this).addClass("active");
	});
	$(".side-menu").on("mouseleave","ul li",function(){
//		$(this).siblings(".active").removeClass("active");
		$(this).removeClass("active");
	});
	//mini menu 点击
	$(".toggle-btn").on("click",function(){
		$("#body-content").toggleClass("mini-size");
		SmoothlyMenu();
	});
	//菜单平滑事件
	 function SmoothlyMenu() {
	 	if(!$("#body-content").hasClass("mini-size")){
	 		$(".menu-ul").hide();
	 		setTimeout(
	 			function(){
	 				$(".menu-ul").fadeIn("slow");
	 			},300);
	 	}else{
	 		$(".menu-ul ").removeAttr('style');
	 	}
	 };
	 //折叠展开二级子菜单
	 $(".side-menu").on("click",".menu-close",function(){
	 	$(this).children("ul").toggle(300);
	 	if($(this).children("ul").css("opacity")=="1"){
	 		$(this).children("a").children("b").removeClass("fa-angle-up").addClass("fa-angle-down");
	 	}else{
	 		$(this).children("a").children("b").removeClass("fa-angle-down").addClass("fa-angle-up");
	 	}
	 	return false;
	 });
	 //menu的点击事件
	 $(".side-menu").on("click",".menu-ul li",function(){
		 if($(this).hasClass("chose")){
			 return false;
		 }
		 $(this).siblings(".chose").removeClass("chose");
		 $(this).addClass("chose");
		 var url = $(this).children("a").attr("name");
		 $("a",this).get(0).click();
		 return false;
	 });
});
//配合屏幕自适应为body添加或删除样式
$(window).bind("resize",function(){
	if($(this).width() < 769){
		$('body').addClass('body-small');
	}else{
		$('body').removeClass('body-small');
	}
});