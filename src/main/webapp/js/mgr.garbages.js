function getAllGarbagesByPage(choosePage){
    var userName=$('.userName').val();
    var realName=$('.realName').val();
    var stationName=$('.stationName').val();
    var typeName=$('.typeName').val();
    var qrCode=$('.qrCode').val();
    var remark=$('.remark').val();

    $.ajax({
        type: "POST",
        url:"admin/getAllGarbagesByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,userName:userName,realName:realName,stationName:stationName,typeName:typeName,qrCode:qrCode,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td>'
                    		 +'<td>'+v.qrCode+'</td>'
                    		 +'<td>'+v.weight+'</td>'
                    		 +'<td>'+v.score+'</td>';
                    		 if(v.garbageStations == null) {
                    			 html+='<td>'+v.garbageStations+'</td>';
                    		 } else {
                    			 html+='<td>'+v.garbageStations.stationName+'</td>';
                    		 }
                    		 if(v.garbageTypes == null) {
                    			 html+='<td>'+v.garbageTypes+'</td>';
                    		 } else {
                    			 html+='<td>'+v.garbageTypes.typeName+'</td>';
                    		 }
                    		 if(v.users == null) {
                    			 html+='<td>'+v.users+'</td>'
                    			 	  //+'<td>'+v.users+'</td>'
                    			 	  ;
                    		 } else {
                    			 html+='<td>'+v.users.userName+'</td>'
                    			      //+'<td>'+v.users.realName+'</td>'
                    			      ;
                    		 }
                    	html+='<td>'+v.createdAt+'</td>'
                    		 +'<td>'+v.remark+'</td>'
                    		 ;
                    
                    html+= generateTd(generateButton([{name:'editGarbages' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editGarbages' + v.id ,
                                title:dictionary['label.modifyGarbagesInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'garbages-update',
                                list:[{span:dictionary['label.stationName'],name:'change-garbages-garbageStationId', value:v.garbageStationId, type:'select', list:allGarbageStations},
                                      {span:dictionary['label.typeName'],name:'change-garbages-garbageTypeId', value:v.garbageTypeId, type:'select', list:allGarbageTypes},
                                      {span:dictionary['label.qrCode'],name:'change-garbages-qrCode scan', value:v.qrCode},
                                      {span:dictionary['label.weight'],name:'change-garbages-weight read-weight', value:v.weight},
                                      {span:dictionary['label.remark'],name:'change-garbages-remark', value:v.remark},
                                      {name:'change-garbages-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delGarbages' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delGarbages' + v.id, title:dictionary['label.deleteGarbages'], content:dictionary['label.Delete'], btnClass:'garbages-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="Garbages_pagination"><ul></ul></div>');
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
    $("#tab_garbages").click(function(){
        changeMenuClass('tab_garbages');
        $boxHeader.html($("#tab_garbages a").text());
        var searchForm = [
                          {span:dictionary['label.username'], name:'userName'},
                          {span:dictionary['label.realName'],name:'realName'},
                          {span:dictionary['label.qrCode'],name:'qrCode'},
                          {span:dictionary['label.stationName'],name:'stationName'},
                          {span:dictionary['label.typeName'],name:'typeName'},
                          {span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = [{value:'ID', width:'5%'},
                            dictionary['label.qrCode'],
                            {value:dictionary['label.weight'], width:'5%'},
                            {value:dictionary['label.score'], width:'5%'},
                            dictionary['label.stationName'],
                            dictionary['label.typeName'],
                            dictionary['label.username'],
                            //dictionary['label.realName'],
                            dictionary['label.createdAt'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_garbages">'+dictionary['label.addGarbages']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_garbages' ,
            title:dictionary['label.addGarbages'],
            confirm:dictionary['label.Confirm'],
            btnClass:'garbages-add',
            list:[{span:dictionary['label.stationName'],name:'change-garbages-garbageStationId', type:'select', list:allGarbageStations},
                  {span:dictionary['label.typeName'],name:'change-garbages-garbageTypeId', type:'select', list:allGarbageTypes},
                  {span:dictionary['label.qrCode'],name:'change-garbages-qrCode scan'},
                  {span:dictionary['label.weight'],name:'change-garbages-weight read-weight'},
                  {span:dictionary['label.remark'],name:'change-garbages-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllGarbagesByPage(1);
        return false;
    });

    $(".garbages-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var userId=$(this).parent().parent().find(".change-garbages-userId").val();
        var garbageStationId=$(this).parent().parent().find(".change-garbages-garbageStationId").val();
        var garbageTypeId=$(this).parent().parent().find(".change-garbages-garbageTypeId").val();
        var qrCode=$(this).parent().parent().find(".change-garbages-qrCode").val();
        var weight=$(this).parent().parent().find(".change-garbages-weight").val();
        var remark=$(this).parent().parent().find(".change-garbages-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createGarbages.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,garbageStationId:garbageStationId,garbageTypeId:garbageTypeId,qrCode:qrCode,weight:weight,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbagesByPage(1);
                    inputs.val('');
                    inputs[0].focus();
                    //$('.pop-close').click();
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });

    $(".garbages-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#Garbages_pagination ul li.actived").text();
        var userId=$(this).parent().parent().find(".change-garbages-userId").val();
        var garbageStationId=$(this).parent().parent().find(".change-garbages-garbageStationId").val();
        var garbageTypeId=$(this).parent().parent().find(".change-garbages-garbageTypeId").val();
        var qrCode=$(this).parent().parent().find(".change-garbages-qrCode").val();
        var weight=$(this).parent().parent().find(".change-garbages-weight").val();
        var remark=$(this).parent().parent().find(".change-garbages-remark").val();
        var id=$(this).parent().parent().find(".change-garbages-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateGarbages.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,garbageStationId:garbageStationId,garbageTypeId:garbageTypeId,qrCode:qrCode,weight:weight,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbagesByPage(nowPage);
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

    $(".garbages-delete-sure").live("click",function(){
        var nowPage=$("#Garbages_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteGarbages.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllGarbagesByPage(nowPage);
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