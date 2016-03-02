$(function(){
	$(".english").click(function(){
	   $.cookie("lang", "en_US"); 
	   location.reload();
	});
	$(".chinese").click(function(){
		   $.cookie("lang", "zh_CN"); 
		   location.reload();
	});
});
