function getAllGarbageTypesByPage(choosePage){
    var typeName=$('.typeName').val();
    var remark=$('.remark').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllGarbageTypesByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,typeName:typeName,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td><td>'+v.typeName+'</td><td>'+v.scoreWeight+'</td><td>'+v.createdAt+'</td><td>'+v.remark+'</td>';
                    
                    html+= generateTd(generateButton([{name:'editGarbageTypes' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGarbageTypes' + v.id ,
                                title:dictionary['label.modifyGarbageTypesInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'garbageTypes-update',
                                list:[{span:dictionary['label.typeName'],name:'change-garbageTypes-typeName', value:v.typeName},
                                      {span:dictionary['label.scoreWeight'],name:'change-garbageTypes-scoreWeight', value:v.scoreWeight},
                                      {span:dictionary['label.remark'],name:'change-garbageTypes-remark', value:v.remark},
                                      {name:'change-garbageTypes-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGarbageTypes' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGarbageTypes' + v.id, title:dictionary['label.deleteGarbageTypes'], content:dictionary['label.Delete'], btnClass:'garbageTypes-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="GarbageTypes_pagination"><ul></ul></div>');
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
    $("#tab_garbagetypes").click(function(){
        changeMenuClass('tab_garbagetypes');
        $boxHeader.html($("#tab_garbagetypes a").text());
        var searchForm = [
                          {span:dictionary['label.typeName'], name:'typeName'}
                          ,{span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.typeName'],
                            dictionary['label.scoreWeight'],
                            dictionary['label.createdAt'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_garbageTypes">'+dictionary['label.addGarbageTypes']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_garbageTypes' ,
            title:dictionary['label.addGarbageTypes'],
            confirm:dictionary['label.Confirm'],
            btnClass:'garbageTypes-add',
            list:[{span:dictionary['label.typeName'],name:'change-garbageTypes-typeName'},
                  {span:dictionary['label.scoreWeight'],name:'change-garbageTypes-scoreWeight'},
                  {span:dictionary['label.remark'],name:'change-garbageTypes-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGarbageTypesByPage(1);
        return false;
    });

    $(".garbageTypes-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var typeName=$(this).parent().parent().find(".change-garbageTypes-typeName").val();
        var scoreWeight=$(this).parent().parent().find(".change-garbageTypes-scoreWeight").val();
        var remark=$(this).parent().parent().find(".change-garbageTypes-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGarbageTypes.html",
            dataType:"json",
            data:JSON.stringify({typeName:typeName,scoreWeight:scoreWeight,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageTypesByPage(1);
                    getAllGarbageTypes();
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

    $(".garbageTypes-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#GarbageTypes_pagination ul li.actived").text();
        var typeName=$(this).parent().parent().find(".change-garbageTypes-typeName").val();
        var scoreWeight=$(this).parent().parent().find(".change-garbageTypes-scoreWeight").val();
        var remark=$(this).parent().parent().find(".change-garbageTypes-remark").val();
        var id=$(this).parent().parent().find(".change-garbageTypes-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGarbageTypes.html",
            dataType:"json",
            data:JSON.stringify({typeName:typeName,scoreWeight:scoreWeight,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageTypesByPage(nowPage);
                    getAllGarbageTypes();
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

    $(".garbageTypes-delete-sure").live("click",function(){
        var nowPage=$("#GarbageTypes_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGarbageTypes.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageTypesByPage(nowPage);
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