function getAllGarbageCansByPage(choosePage){
    var canName=$('.canName').val();
    var village=$('.village').val();
    var address=$('.address').val();
    var stationOp=$('.stationOp').val();
    var remark=$('.remark').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllGarbageCansByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,canName:canName,village:village,address:address,stationOp:stationOp,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td><td>'+v.canName+'</td><td>'+v.village+'</td><td>'+v.address+'</td><td>'+v.stationOp+'</td><td>'+v.remark+'</td>';
                    
                    html+= generateTd(generateButton([{name:'editGarbageCans' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGarbageCans' + v.id ,
                                title:dictionary['label.modifyGarbageCansInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'garbageCans-update',
                                list:[{span:dictionary['label.canName'],name:'change-garbageCans-canName', value:v.canName},
                                      {span:dictionary['label.village'],name:'change-garbageCans-village', value:v.village},
                                      {span:dictionary['label.address'],name:'change-garbageCans-address', value:v.address},
                                      {span:dictionary['label.stationOp'],name:'change-garbageCans-stationOp', value:v.stationOp},
                                      {span:dictionary['label.remark'],name:'change-garbageCans-remark', value:v.remark},
                                      {name:'change-garbageCans-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGarbageCans' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGarbageCans' + v.id, title:dictionary['label.deleteGarbageCans'], content:dictionary['label.Delete'], btnClass:'garbageCans-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="GarbageCans_pagination"><ul></ul></div>');
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
    $("#tab_garbagecans").click(function(){
        changeMenuClass('tab_garbagecans');
        $boxHeader.html($("#tab_garbagecans a").text());
        var searchForm = [
                          {span:dictionary['label.canName'], name:'canName'}
                          ,{span:dictionary['label.village'], name:'village'}
                          ,{span:dictionary['label.address'], name:'address'}
                          ,{span:dictionary['label.stationOp'], name:'stationOp'}
                          ,{span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.canName'],
                            dictionary['label.village'],
                            dictionary['label.address'],
                            dictionary['label.stationOp'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_garbageCans">'+dictionary['label.addGarbageCans']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_garbageCans' ,
            title:dictionary['label.addGarbageCans'],
            confirm:dictionary['label.Confirm'],
            btnClass:'garbageCans-add',
            list:[{span:dictionary['label.canName'],name:'change-garbageCans-canName'},
                  {span:dictionary['label.village'],name:'change-garbageCans-village'},
                  {span:dictionary['label.address'],name:'change-garbageCans-address'},
                  {span:dictionary['label.stationOp'],name:'change-garbageCans-stationOp'},
                  {span:dictionary['label.remark'],name:'change-garbageCans-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGarbageCansByPage(1);
        return false;
    });

    $(".garbageCans-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var canName=$(this).parent().parent().find(".change-garbageCans-canName").val();
        var village=$(this).parent().parent().find(".change-garbageCans-village").val();
        var address=$(this).parent().parent().find(".change-garbageCans-address").val();
        var stationOp=$(this).parent().parent().find(".change-garbageCans-stationOp").val();
        var remark=$(this).parent().parent().find(".change-garbageCans-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGarbageCans.html",
            dataType:"json",
            data:JSON.stringify({canName:canName,village:village,address:address,stationOp:stationOp,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageCansByPage(1);
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

    $(".garbageCans-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#GarbageCans_pagination ul li.actived").text();
        var canName=$(this).parent().parent().find(".change-garbageCans-canName").val();
        var village=$(this).parent().parent().find(".change-garbageCans-village").val();
        var address=$(this).parent().parent().find(".change-garbageCans-address").val();
        var stationOp=$(this).parent().parent().find(".change-garbageCans-stationOp").val();
        var remark=$(this).parent().parent().find(".change-garbageCans-remark").val();
        var id=$(this).parent().parent().find(".change-garbageCans-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGarbageCans.html",
            dataType:"json",
            data:JSON.stringify({canName:canName,village:village,address:address,stationOp:stationOp,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageCansByPage(nowPage);
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

    $(".garbageCans-delete-sure").live("click",function(){
        var nowPage=$("#GarbageCans_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGarbageCans.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageCansByPage(nowPage);
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