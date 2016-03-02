function getAllGiftGrantsByPage(choosePage){
    var userName=$('.userName').val();
    var realName=$('.realName').val();
    var giftName=$('.giftName').val();
    var remark=$('.remark').val();

    $.ajax({
        type: "POST",
        url:"admin/getAllGiftGrantsByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,userName:userName,realName:realName,giftName:giftName,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td>';
			           		 if(v.gifts == null) {
			        			 html+='<td>'+v.gifts+'</td>';
			        		 } else {
			        			 html+='<td>'+v.gifts.giftName+'</td>';
			        		 }
			           		 
			           		 if(v.users == null) {
			        			 html+='<td>'+v.users+'</td>'
			        			 	  +'<td>'+v.users+'</td>';
			        		 } else {
			        			 html+='<td>'+v.users.userName+'</td>'
			        			      +'<td>'+v.users.realName+'</td>';
			        		 }
			           		 
			           	html+='<td>'+v.giftNum+'</td>'
                    		 +'<td>'+v.createdAt+'</td>'
                    		 +'<td>'+v.remark+'</td>'
                    		 ;
                    
                    html+= generateTd(generateButton([{name:'editGiftGrants' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGiftGrants' + v.id ,
                                title:dictionary['label.modifyGiftGrantsInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'giftGrants-update',
                                list:[{span:dictionary['label.username'],name:'change-giftGrants-userId', value:v.userId, type:'select', list:allUsers},
                                      {span:dictionary['label.giftName'],name:'change-giftGrants-giftId', value:v.giftId, type:'select', list:allGifts},
                                      {span:dictionary['label.giftGrantNum1'],name:'change-giftGrants-giftNum', value:v.giftNum},
                                      {span:dictionary['label.remark'],name:'change-giftGrants-remark', value:v.remark},
                                      {name:'change-giftGrants-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGiftGrants' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGiftGrants' + v.id, title:dictionary['label.deleteGiftGrants'], content:dictionary['label.Delete'], btnClass:'giftGrants-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="GiftGrants_pagination"><ul></ul></div>');
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
    $("#tab_giftgrants").click(function(){
        changeMenuClass('tab_giftgrants');
        $boxHeader.html($("#tab_giftgrants a").text());
        var searchForm = [
                          {span:dictionary['label.username'], name:'userName'},
                          {span:dictionary['label.realName'],name:'realName'},
                          {span:dictionary['label.giftName'],name:'giftName'},
                          {span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.giftName'],
                            dictionary['label.username'],
                            dictionary['label.realName'],
                            dictionary['label.giftGrantNum1'],
                            dictionary['label.createdAt'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_giftGrants">'+dictionary['label.addGiftGrants']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_giftGrants' ,
            title:dictionary['label.addGiftGrants'],
            confirm:dictionary['label.Confirm'],
            btnClass:'giftGrants-add',
            list:[{span:dictionary['label.username'],name:'change-giftGrants-userId', type:'select', list:allUsers},
                  {span:dictionary['label.giftName'],name:'change-giftGrants-giftId', type:'select', list:allGifts},
                  {span:dictionary['label.giftGrantNum1'],name:'change-giftGrants-giftNum'},
                  {span:dictionary['label.remark'],name:'change-giftGrants-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGiftGrantsByPage(1);
        return false;
    });

    $(".giftGrants-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var userId=$(this).parent().parent().find(".change-giftGrants-userId").val();
        var giftId=$(this).parent().parent().find(".change-giftGrants-giftId").val();
        var giftNum=$(this).parent().parent().find(".change-giftGrants-giftNum").val();
        var remark=$(this).parent().parent().find(".change-giftGrants-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGiftGrants.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,giftId:giftId,giftNum:giftNum,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftGrantsByPage(1);
                    inputs.val('');
                    $('.pop-close').click();
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });

    $(".giftGrants-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#GiftGrants_pagination ul li.actived").text();
        var userId=$(this).parent().parent().find(".change-giftGrants-userId").val();
        var giftId=$(this).parent().parent().find(".change-giftGrants-giftId").val();
        var giftNum=$(this).parent().parent().find(".change-giftGrants-giftNum").val();
        var remark=$(this).parent().parent().find(".change-giftGrants-remark").val();
        var id=$(this).parent().parent().find(".change-giftGrants-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGiftGrants.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,giftId:giftId,giftNum:giftNum,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftGrantsByPage(nowPage);
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

    $(".giftGrants-delete-sure").live("click",function(){
        var nowPage=$("#GiftGrants_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGiftGrants.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftGrantsByPage(nowPage);
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