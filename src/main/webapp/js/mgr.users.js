function getAllUsersByPage(choosePage){
    var userName=$('.userName').val();
    var realName=$('.realName').val();
    var gender=$('.gender').val();
    var isAdmin=$('.isAdmin').val();
    var remark=$('.remark').val();
    var orderby=$('.orderby').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllUsersByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,userName:userName,realName:realName,gender:gender,isAdmin:isAdmin,remark:remark,orderByClause:orderby}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td>'
                    		 +'<td>'+v.userName+'</td>'
                    		 +'<td>'+v.realName+'</td>'
                    		 +'<td>'+v.score+'</td>'
                    		 +'<td>'+findByKey(v.gender, datas.gender)+'</td>'
                    		 +'<td>'+getDate(v.birthday)+'</td>'
                    		 +'<td>'+v.village+'</td>'
                    		 +'<td>'+v.phone+'</td>'
                    		 +'<td>'+v.address+'</td>'
                    		 +'<td>'+v.remark+'</td>'
                    		 +'<td>'+findByKey(v.isAdmin, datas.yesOrNo)+'</td>'
                    		 ;
                    
                    html+= generateTd(generateButton([{name:'editUsers' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editUsers' + v.id ,
                                title:dictionary['label.modifyUsersInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'users-update',
                                list:[{span:dictionary['label.username'],name:'change-users-userName', value:v.userName},
                                      {span:dictionary['label.password'],name:'change-users-password'},
                                      {span:dictionary['label.isAdmin'],name:'change-users-isAdmin', value:v.isAdmin, type:'select', list:datas.yesOrNo},
                                      {span:dictionary['label.realName'],name:'change-users-realName', value:v.realName},
                                      {span:dictionary['label.gender'],name:'change-users-gender', value:v.gender, type:'select', list:datas.gender},
                                      {span:dictionary['label.birthday'],name:'change-users-birthday', value:getDate(v.birthday)},
                                      {span:dictionary['label.phone'],name:'change-users-phone', value:v.phone},
                                      {span:dictionary['label.village'],name:'change-users-village', value:v.village},
                                      {span:dictionary['label.address'],name:'change-users-address', value:v.address},
                                      {span:dictionary['label.remark'],name:'change-users-remark', value:v.remark},
                                      {name:'change-users-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delUsers' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delUsers' + v.id, title:dictionary['label.deleteUsers'], content:dictionary['label.Delete'], btnClass:'users-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="Users_pagination"><ul></ul></div>');
                var totalPages=data.totalPage;
                var id=$('.pagination').attr('id');
                $('.pagination').paging(totalPages,choosePage,id);
                handleTable();
            }else if (data.returnCode == "0002"){
                location.href="login.jsp";
            }else{
                alert(data.returnMessage);
            }
        }
    });
}

$(function(){
    $("#tab_users").click(function(){
        changeMenuClass('tab_users');
        $boxHeader.html($("#tab_users a").text());
        var searchForm = [
                          {span:dictionary['label.username'], name:'userName'},
                          {span:dictionary['label.realName'],name:'realName'},
                          {span:dictionary['label.gender'],name:'gender', type:'select', list:datas.gender},
                          {span:dictionary['label.isAdmin'],name:'isAdmin', type:'select', list:datas.yesOrNo},
//                          {span:dictionary['label.birthday'],name:'birthday'},
//                          {span:dictionary['label.phone'],name:'phone'},
//                          {span:dictionary['label.address'],name:'address'},
                          {span:dictionary['label.orderby'], name:'orderby', type:'select', list:datas.userOrder},
                          {span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.username'],
                            dictionary['label.realName'],
                            dictionary['label.score'],
                            dictionary['label.gender'],
                            dictionary['label.birthday'],
                            dictionary['label.village'],
                            dictionary['label.phone'],
                            dictionary['label.address'],
                            dictionary['label.remark'],
                            dictionary['label.isAdmin'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_users">'+dictionary['label.addUser']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_users' ,
            title:dictionary['label.addUser'],
            confirm:dictionary['label.Confirm'],
            btnClass:'users-add',
            list:[{span:dictionary['label.username'],name:'change-users-userName'},
                  {span:dictionary['label.password'],name:'change-users-password'},
                  {span:dictionary['label.isAdmin'],name:'change-users-isAdmin', type:'select', list:datas.yesOrNo},
                  {span:dictionary['label.realName'],name:'change-users-realName'},
                  {span:dictionary['label.gender'],name:'change-users-gender', type:'select', list:datas.gender},
                  {span:dictionary['label.birthday'],name:'change-users-birthday'},
                  {span:dictionary['label.phone'],name:'change-users-phone'},
                  {span:dictionary['label.village'],name:'change-users-village'},
                  {span:dictionary['label.address'],name:'change-users-address'},
                  {span:dictionary['label.remark'],name:'change-users-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllUsersByPage(1);
        return false;
    });

    $(".users-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var userName=$(this).parent().parent().find(".change-users-userName").val();
        var password=$(this).parent().parent().find(".change-users-password").val();
        var isAdmin=$(this).parent().parent().find(".change-users-isAdmin").val();
        var realName=$(this).parent().parent().find(".change-users-realName").val();
        var gender=$(this).parent().parent().find(".change-users-gender").val();
        var birthday=$(this).parent().parent().find(".change-users-birthday").val();
        if(birthday != undefined && birthday != null && birthday != '') {
        	birthday += ' 00:00:00';
        }
        var phone=$(this).parent().parent().find(".change-users-phone").val();
        var village=$(this).parent().parent().find(".change-users-village").val();
        var address=$(this).parent().parent().find(".change-users-address").val();
        var remark=$(this).parent().parent().find(".change-users-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createUsers.html",
            dataType:"json",
            data:JSON.stringify({userName:userName,password:password,isAdmin:isAdmin,realName:realName,gender:gender,birthday:birthday,phone:phone,village:village,address:address,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllUsersByPage(1);
                    getAllUsers();
                    inputs.val('');
                    $('.pop-close').click();
                }else if (data.returnCode == "0002"){
                    location.href="login.jsp";
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });

    $(".users-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#Users_pagination ul li.actived").text();
        var userName=$(this).parent().parent().find(".change-users-userName").val();
        var password=$(this).parent().parent().find(".change-users-password").val();
        var isAdmin=$(this).parent().parent().find(".change-users-isAdmin").val();
        var realName=$(this).parent().parent().find(".change-users-realName").val();
        var gender=$(this).parent().parent().find(".change-users-gender").val();
        var birthday=$(this).parent().parent().find(".change-users-birthday").val();
        if(birthday != undefined && birthday != null && birthday != '') {
        	birthday += ' 00:00:00';
        }
        var phone=$(this).parent().parent().find(".change-users-phone").val();
        var village=$(this).parent().parent().find(".change-users-village").val();
        var address=$(this).parent().parent().find(".change-users-address").val();
        var remark=$(this).parent().parent().find(".change-users-remark").val();
        var id=$(this).parent().parent().find(".change-users-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateUsers.html",
            dataType:"json",
            data:JSON.stringify({userName:userName,password:password,isAdmin:isAdmin,realName:realName,gender:gender,birthday:birthday,phone:phone,village:village,address:address,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllUsersByPage(nowPage);
                    getAllUsers();
                    $('.pop-close').click();
                }else if (data.returnCode == "0002"){
                    location.href="login.jsp";
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });

    $(".users-delete-sure").live("click",function(){
        var nowPage=$("#Users_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteUsers.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllUsersByPage(nowPage);
                }else if (data.returnCode == "0002"){
                    location.href="login.jsp";
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });
});