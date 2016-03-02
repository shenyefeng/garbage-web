$(function(){
	if ($.cookie("username")&&$.cookie("password")){
		$("#username").val($.cookie("username"));
		$("#password").val($.cookie("password"));
		$("#rem")[0].checked = true;
	}
	
	$("#resrc").click(function(){
		$(this).attr("src", "validationCode?dt=" + Math.random());
	});
	
	$('.login-box').bind('keypress',function(event){
        if(event.keyCode == "13"){
            $("#login").click();
        }
    });
	
	$("#login").click(function(){
		if ($("#rem")[0].checked){
			$.cookie("username", $("#username").val(),{path:'/',expires:10});
			$.cookie("password", $("#password").val(),{path:'/',expires:10});
		}else{
			$.cookie("username", null,{path:'/'});
			$.cookie("password", null,{path:'/'});
		}
		$.ajax({
			type: "POST",
			url:"loginAjax.html",
			dataType:"json",
			data:JSON.stringify({userName:$("#username").val(),password:$("#password").val(),validationCode:$("#validationCode").val()}),
			success:function(data) {
				if(data.returnCode == "000"){
					location.href="home.jsp";
				}else{
					alert(data.returnMessage);
				}
			}
		});
	});
});	
	