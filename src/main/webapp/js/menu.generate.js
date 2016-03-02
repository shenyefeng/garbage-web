$(function(){
    $.ajax({
        type: "POST",
        url:"admin/getMenus.html",
        dataType:"json",
		async:false,
        data:JSON.stringify({username:'${sessionScope.userName}'}),
        beforeSend: function(xhr){
            $sidebar.html('<div class="loading"><img src="img-new/loading.gif" /></div>');
        },
        success:function(data) {
            var html='<ul class="function-list">';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html += '<li class="roof"><div class="warp"><span class="fun-bgcolor"><i class="'+v.styleClass+'"></i></span><a ';
                    if((v.subMenus == undefined || v.subMenus.length == 0) && v.menuItemId != null) {
                        html += 'id="'+v.menuItemId+'"';
                    }
                    
                    html += ' href="#" class="aa">'+v.title+'</a>';
                    if(v.subMenus != undefined && v.subMenus.length > 0) {
                        html += '<img style="float:right;" src="img-new/arrow-1.png" />';
                    }
                    html += '</div>';
                    
                    if(v.subMenus != undefined && v.subMenus.length > 0) {
                        html += '<ul class="manage-list">';
                        $.each(v.subMenus , function(i, j){
                            html += '<li id="'+j.menuItemId+'"><span class="man-bgcolor"></span><a href="#">'+j.title+'</a></li>';
                        });
                        
                        html += '</ul>';
                    }
                    
                    html += '</li>';
                });
                
                html += '</ul>';
                $sidebar.html(html);
            }else if (data.returnCode == "0002"){
                location.href="login.jsp";
            }else{
                alert(data.returnMessage);
            }
        }
    });

    $('.function-list').menutree({
        animate: true,
        speed: 500,
        openCallback: function($clickedEl) {},
        closeCallback: function($clickedEl) {}
    });
});
