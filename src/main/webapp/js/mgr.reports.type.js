function getAllTypeReportByPage(choosePage){

    $.ajax({
        type: "POST",
        url:"admin/garbageTypeReport.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.typeName+'</td><td>'+v.sum+'</td>';
                    
                    html+= '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="TypeReport_pagination"><ul></ul></div>');
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
    $("#tab_type_report").click(function(){
        changeMenuClass('tab_type_report');
        $boxHeader.html($("#tab_type_report a").text());
        var tableColumns = [dictionary['label.typeName'],
                            dictionary['label.sum']];
        showHidden();
        $boxSearch.html("");
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_gifts">'+dictionary['label.addGifts']+'</a>');
        $tableContent.html(generateTableTitle(tableColumns));

        getAllTypeReportByPage(1);
        return false;
    });
});