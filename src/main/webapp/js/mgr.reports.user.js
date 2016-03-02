function getAllUserReportByPage(choosePage){
    var userId=$('.userId').val();
    var userName=$('.userName').val();
    var userPhone=$('.userPhone').val();
    var typeId=$('.typeName').val();
    $.ajax({
        type: "POST",
        url:"admin/userGarbageTypeReport.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,userId:userId,userName:userName,userPhone:userPhone,typeId:typeId}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.userId+'</td><td>'+v.userName+'</td><td>'+v.userPhone+'</td><td>'+v.typeName+'</td><td>'+v.sum+'</td>';
                    
                    html+= '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="UserReport_pagination"><ul></ul></div>');
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
    $("#tab_user_report").click(function(){
        changeMenuClass('tab_user_report');
        $boxHeader.html($("#tab_user_report a").text());
        var searchForm = [
                          {span:dictionary['label.userId'], name:'userId'}
                          ,{span:dictionary['label.userName'], name:'userName'}
                          ,{span:dictionary['label.userPhone'], name:'userPhone'}
                          ,{span:dictionary['label.typeName'], name:'typeName',type:'select', list:allGarbageTypes}
                          ];
        var tableColumns = [dictionary['label.userId'],
                            dictionary['label.userName'],
                            dictionary['label.userPhone'],
                            dictionary['label.typeName'],
                            dictionary['label.sum']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $tableContent.html(generateTableTitle(tableColumns));

        getAllUserReportByPage(1);
        return false;
    });
});