function getAllGiftsByPage(choosePage){
    var giftName=$('.giftName').val();
    var giftNum=$('.giftNum').val();
    var giftGrantNum=$('.giftGrantNum').val();
    var remark=$('.remark').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllGiftsByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,giftName:giftName,giftNum:giftNum,giftGrantNum:giftGrantNum,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td><td>'+v.giftName+'</td><td>'+v.score+'</td><td>'+v.giftNum+'</td><td>'+v.giftGrantNum+'</td><td>'+v.remark+'</td>';
                    
                    html+= generateTd(generateButton([{name:'editGifts' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGifts' + v.id ,
                                title:dictionary['label.modifyGiftsInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'gifts-update',
                                list:[{span:dictionary['label.giftName'],name:'change-gifts-giftName', value:v.giftName},
                                      {span:dictionary['label.giftNum'],name:'change-gifts-giftNum', value:v.giftNum},
                                      {span:dictionary['label.score'],name:'change-gifts-score', value:v.score},
                                      //{span:dictionary['label.giftGrantNum'],name:'change-gifts-giftGrantNum', value:v.giftGrantNum},
                                      {span:dictionary['label.remark'],name:'change-gifts-remark', value:v.remark},
                                      {name:'change-gifts-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGifts' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGifts' + v.id, title:dictionary['label.deleteGifts'], content:dictionary['label.Delete'], btnClass:'gifts-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="Gifts_pagination"><ul></ul></div>');
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
    $("#tab_gifts").click(function(){
        changeMenuClass('tab_gifts');
        $boxHeader.html($("#tab_gifts a").text());
        var searchForm = [
                          {span:dictionary['label.giftName'], name:'giftName'}
                          ,{span:dictionary['label.giftNum'], name:'giftNum'}
                          ,{span:dictionary['label.giftGrantNum'], name:'giftGrantNum'}
                          ,{span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.giftName'],
                            dictionary['label.score'],
                            dictionary['label.giftNum'],
                            dictionary['label.giftGrantNum'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_gifts">'+dictionary['label.addGifts']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_gifts' ,
            title:dictionary['label.addGifts'],
            confirm:dictionary['label.Confirm'],
            btnClass:'gifts-add',
            list:[{span:dictionary['label.giftName'],name:'change-gifts-giftName'},
                  {span:dictionary['label.giftNum'],name:'change-gifts-giftNum'},
                  {span:dictionary['label.score'],name:'change-gifts-score'},
                  //{span:dictionary['label.giftGrantNum'],name:'change-gifts-giftGrantNum'},
                  {span:dictionary['label.remark'],name:'change-gifts-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGiftsByPage(1);
        return false;
    });

    $(".gifts-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var giftName=$(this).parent().parent().find(".change-gifts-giftName").val();
        var giftNum=$(this).parent().parent().find(".change-gifts-giftNum").val();
        var score=$(this).parent().parent().find(".change-gifts-score").val();
        var giftGrantNum=$(this).parent().parent().find(".change-gifts-giftGrantNum").val();
        var remark=$(this).parent().parent().find(".change-gifts-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGifts.html",
            dataType:"json",
            data:JSON.stringify({giftName:giftName,giftNum:giftNum,score:score,giftGrantNum:giftGrantNum,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftsByPage(1);
                    inputs.val('');
                    getAllGifts();
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

    $(".gifts-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#Gifts_pagination ul li.actived").text();
        var giftName=$(this).parent().parent().find(".change-gifts-giftName").val();
        var giftNum=$(this).parent().parent().find(".change-gifts-giftNum").val();
        var score=$(this).parent().parent().find(".change-gifts-score").val();
        var giftGrantNum=$(this).parent().parent().find(".change-gifts-giftGrantNum").val();
        var remark=$(this).parent().parent().find(".change-gifts-remark").val();
        var id=$(this).parent().parent().find(".change-gifts-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGifts.html",
            dataType:"json",
            data:JSON.stringify({giftName:giftName,giftNum:giftNum,score:score,giftGrantNum:giftGrantNum,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftsByPage(nowPage);
                    getAllGifts();
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

    $(".gifts-delete-sure").live("click",function(){
        var nowPage=$("#Gifts_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGifts.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGiftsByPage(nowPage);
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