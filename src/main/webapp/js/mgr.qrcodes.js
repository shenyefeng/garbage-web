function getAllQrCodesByPage(choosePage){
    var userName=$('.userName').val();
    var realName=$('.realName').val();
    var qrCode=$('.qrCode').val();
    var remark=$('.remark').val();

    $.ajax({
        type: "POST",
        url:"admin/getAllQrCodesByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,userName:userName,realName:realName,qrCode:qrCode,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td>';
			           		 if(v.users == null) {
			        			 html+='<td>'+v.users+'</td>'
			        			 	  +'<td>'+v.users+'</td>';
			        		 } else {
			        			 html+='<td>'+v.users.userName+'</td>'
			        			      +'<td>'+v.users.realName+'</td>';
			        		 }
			           	html+='<td>'+v.qrCodeStart+'</td>'
                    		 +'<td>'+v.qrCodeEnd+'</td>'
                    		 +'<td>'+v.createdAt+'</td>'
                    		 +'<td>'+v.remark+'</td>'
                    		 ;
                    
                    html+= generateTd(generateButton([{name:'editQrCodes' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editQrCodes' + v.id ,
                                title:dictionary['label.modifyQrCodesInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'qrCodes-update',
                                list:[{span:dictionary['label.username'],name:'change-qrCodes-userId', value:v.userId, type:'select', list:allUsers},
                                      {span:dictionary['label.qrCodeStart'],name:'change-qrCodes-qrCodeStart scan', value:v.qrCodeStart},
                                      {span:dictionary['label.qrCodeEnd'],name:'change-qrCodes-qrCodeEnd', value:v.qrCodeEnd},
                                      {span:dictionary['label.remark'],name:'change-qrCodes-remark', value:v.remark},
                                      {name:'change-qrCodes-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delQrCodes' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delQrCodes' + v.id, title:dictionary['label.deleteQrCodes'], content:dictionary['label.Delete'], btnClass:'qrCodes-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="QrCodes_pagination"><ul></ul></div>');
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
    $("#tab_qrcodes").click(function(){
        changeMenuClass('tab_qrcodes');
        $boxHeader.html($("#tab_qrcodes a").text());
        var searchForm = [
                          {span:dictionary['label.username'], name:'userName'},
                          {span:dictionary['label.realName'],name:'realName'},
                          {span:dictionary['label.qrCode'],name:'qrCode'},
                          {span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.username'],
                            dictionary['label.realName'],
                            dictionary['label.qrCodeStart'],
                            dictionary['label.qrCodeEnd'],
                            dictionary['label.createdAt'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_qrCodes">'+dictionary['label.addQrCode']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_qrCodes' ,
            title:dictionary['label.addQrCode'],
            confirm:dictionary['label.Confirm'],
            btnClass:'qrCodes-add',
            list:[{span:dictionary['label.username'],name:'change-qrCodes-userId', type:'select', list:allUsers},
                  {span:dictionary['label.qrCodeStart'],name:'change-qrCodes-qrCodeStart scan'},
                  {span:dictionary['label.qrCodeEnd'],name:'change-qrCodes-qrCodeEnd'},
                  {span:dictionary['label.remark'],name:'change-qrCodes-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllQrCodesByPage(1);
        return false;
    });

    $(".qrCodes-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var userId=$(this).parent().parent().find(".change-qrCodes-userId").val();
        var qrCodeStart=$(this).parent().parent().find(".change-qrCodes-qrCodeStart").val();
        var qrCodeEnd=$(this).parent().parent().find(".change-qrCodes-qrCodeEnd").val();
        var remark=$(this).parent().parent().find(".change-qrCodes-remark").val();
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createQrCodes.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,qrCodeStart:qrCodeStart,qrCodeEnd:qrCodeEnd,remark:remark}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllQrCodesByPage(1);
                    inputs.val('');
                    $('.pop-close').click();
                }else{
                    alert(data.returnMessage);
                }
            }
        });
        return false;
    });

    $(".qrCodes-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#QrCodes_pagination ul li.actived").text();
        var userId=$(this).parent().parent().find(".change-qrCodes-userId").val();
        var qrCodeStart=$(this).parent().parent().find(".change-qrCodes-qrCodeStart").val();
        var qrCodeEnd=$(this).parent().parent().find(".change-qrCodes-qrCodeEnd").val();
        var remark=$(this).parent().parent().find(".change-qrCodes-remark").val();
        var id=$(this).parent().parent().find(".change-qrCodes-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateQrCodes.html",
            dataType:"json",
            data:JSON.stringify({userId:userId,qrCodeStart:qrCodeStart,qrCodeEnd:qrCodeEnd,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllQrCodesByPage(nowPage);
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

    $(".qrCodes-delete-sure").live("click",function(){
        var nowPage=$("#QrCodes_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteQrCodes.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllQrCodesByPage(nowPage);
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