function getAllGarbageStationsByPage(choosePage){
    var stationName=$('.stationName').val();
    var phone=$('.phone').val();
    var address=$('.address').val();
    var stationOp=$('.stationOp').val();
    var remark=$('.remark').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllGarbageStationsByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,stationName:stationName,phone:phone,address:address,stationOp:stationOp,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td><td>'+v.stationName+'</td><td>'+v.phone+'</td><td>'+v.address+'</td><td>'+v.stationOp+'</td><td>'+v.remark+'</td>';
                    
                    html+= generateTd(generateButton([{name:'editGarbageStations' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGarbageStations' + v.id ,
                                title:dictionary['label.modifyGarbageStationsInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'garbageStations-update',
                                list:[{span:dictionary['label.stationName'],name:'change-garbageStations-stationName', value:v.stationName},
                                      {span:dictionary['label.phone'],name:'change-garbageStations-phone', value:v.phone},
                                      {span:dictionary['label.address'],name:'change-garbageStations-address', value:v.address},
                                      {span:dictionary['label.stationOp'],name:'change-garbageStations-stationOp', value:v.stationOp},
                                      {span:dictionary['label.remark'],name:'change-garbageStations-remark', value:v.remark},
                                      {name:'change-garbageStations-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGarbageStations' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGarbageStations' + v.id, title:dictionary['label.deleteGarbageStations'], content:dictionary['label.Delete'], btnClass:'garbageStations-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="GarbageStations_pagination"><ul></ul></div>');
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
    $("#tab_stations").click(function(){
        changeMenuClass('tab_stations');
        $boxHeader.html($("#tab_stations a").text());
        var searchForm = [
                          {span:dictionary['label.stationName'], name:'stationName'}
                          ,{span:dictionary['label.phone'], name:'phone'}
                          ,{span:dictionary['label.address'], name:'address'}
                          ,{span:dictionary['label.stationOp'], name:'stationOp'}
                          ,{span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.stationName'],
                            dictionary['label.phone'],
                            dictionary['label.address'],
                            dictionary['label.stationOp'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_garbageStations">'+dictionary['label.addGarbageStations']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_garbageStations' ,
            title:dictionary['label.addGarbageStations'],
            confirm:dictionary['label.Confirm'],
            btnClass:'garbageStations-add',
            list:[{span:dictionary['label.stationName'],name:'change-garbageStations-stationName'},
                  {span:dictionary['label.phone'],name:'change-garbageStations-phone'},
                  {span:dictionary['label.address'],name:'change-garbageStations-address'},
                  {span:dictionary['label.stationOp'],name:'change-garbageStations-stationOp'},
                  {span:dictionary['label.remark'],name:'change-garbageStations-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGarbageStationsByPage(1);
        return false;
    });

    $(".garbageStations-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var stationName=$(this).parent().parent().find(".change-garbageStations-stationName").val();
        var phone=$(this).parent().parent().find(".change-garbageStations-phone").val();
        var address=$(this).parent().parent().find(".change-garbageStations-address").val();
        var stationOp=$(this).parent().parent().find(".change-garbageStations-stationOp").val();
        var remark=$(this).parent().parent().find(".change-garbageStations-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGarbageStations.html",
            dataType:"json",
            data:JSON.stringify({stationName:stationName,phone:phone,address:address,stationOp:stationOp,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageStationsByPage(1);
                    getAllGarbageStations();
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

    $(".garbageStations-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#GarbageStations_pagination ul li.actived").text();
        var stationName=$(this).parent().parent().find(".change-garbageStations-stationName").val();
        var phone=$(this).parent().parent().find(".change-garbageStations-phone").val();
        var address=$(this).parent().parent().find(".change-garbageStations-address").val();
        var stationOp=$(this).parent().parent().find(".change-garbageStations-stationOp").val();
        var remark=$(this).parent().parent().find(".change-garbageStations-remark").val();
        var id=$(this).parent().parent().find(".change-garbageStations-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGarbageStations.html",
            dataType:"json",
            data:JSON.stringify({stationName:stationName,phone:phone,address:address,stationOp:stationOp,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageStationsByPage(nowPage);
                    getAllGarbageStations();
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

    $(".garbageStations-delete-sure").live("click",function(){
        var nowPage=$("#GarbageStations_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGarbageStations.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbageStationsByPage(nowPage);
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