function getAllConfigByPage(choosePage){
    var configName=$('.configName').val();
    var configValue=$('.configValue').val();
    var remark=$('.remark').val();
    $.ajax({
        type: "POST",
        url:"admin/getAllConfigByPage.html",
        dataType:"json",
        data:JSON.stringify({currentPage:choosePage,configName:configName,configValue:configValue,remark:remark}),
        beforeSend: function(xhr){
            $tableContent.find("table tbody").html('<div class="loading"><img src="img-new/loading.gif" /></div>');
            $(".pagination").remove();
        },
        success:function(data) {
            var html='';
            if(data.returnCode == "000"){
                $.each(data.list , function(k, v){
                    html+='<tr><td>'+v.id+'</td><td>'+v.configName+'</td><td>'+v.configValue+'</td><td>'+v.remark+'</td>';
                    
                    html+= generateTd(generateButton([{name:'editConfig' + v.id, title:dictionary['label.modify'], btnclass:'icon-edit'}])
                            + generatePopupMultibox({id:'editConfig' + v.id ,
                                title:dictionary['label.modifyConfigInfo'],
                                confirm:dictionary['label.ConfirmUpdate'],
                                btnClass:'config-update',
                                list:[{span:dictionary['label.ConfigName'],name:'change-config-configName', value:v.configName},
                                      {span:dictionary['label.ConfigValue'],name:'change-config-configValue', value:v.configValue},
                                      {span:dictionary['label.remark'],name:'change-config-remark', value:v.remark},
                                      {name:'change-config-id', type:'hidden', value:v.id}]})
                            
                            +generateButton([{name:'delConfig' + v.id, title:dictionary['label.Delete'], btnclass:'icon-delete'}])
                            + generatePopupbox({id:'delConfig' + v.id, title:dictionary['label.deleteConfig'], content:dictionary['label.Delete'], btnClass:'config-delete-sure', confirm:dictionary['label.Confirm']})
                            )
                     + '</tr>';
                });
                $tableContent.find("table tbody").html(html);
                $tableContent.append('<div class="pagination pagination-centered" id="Config_pagination"><ul></ul></div>');
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
    $("#tab_config").click(function(){
        changeMenuClass('tab_config');
        $boxHeader.html($("#tab_config a").text());
        var searchForm = [
                          {span:dictionary['label.ConfigName'], name:'configName'}
                          ,{span:dictionary['label.ConfigValue'], name:'configValue'}
                          ,{span:dictionary['label.remark'], name:'remark'}
                          ];
        var tableColumns = ['ID',
                            dictionary['label.ConfigName'],
                            dictionary['label.ConfigValue'],
                            dictionary['label.remark'],
                            dictionary['label.Operating']];
        showHidden();
        $boxSearch.html(generateSearchForm(searchForm));
        
        $boxHeader.append('<a class="btn btn-popbutton add-btn-a" data-popup-id="create_config">'+dictionary['label.addConfig']+'</a>');
        $tableContent.html(generatePopupMultibox({id:'create_config' ,
            title:dictionary['label.addConfig'],
            confirm:dictionary['label.Confirm'],
            btnClass:'config-add',
            list:[{span:dictionary['label.ConfigName'],name:'change-config-configName'},
                  {span:dictionary['label.ConfigValue'],name:'change-config-configValue'},
                  {span:dictionary['label.chkencrypt'],name:'chkencrypt', type:'checkbox'},
                  {span:dictionary['label.remark'],name:'change-config-remark'}]}) + generateTableTitle(tableColumns)
                  );

        getAllConfigByPage(1);
        return false;
    });

    $(".config-add").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var configName=$(this).parent().parent().find(".change-config-configName").val();
        var configValue=$(this).parent().parent().find(".change-config-configValue").val();
        var remark=$(this).parent().parent().find(".change-config-remark").val();
        var isEncrypt=$(this).parent().parent().find(".chkencrypt")[0].checked;
        var inputs = $(this).parent().parent().find("input");
        $.ajax({
            type: "POST",
            url:"admin/createConfig.html",
            dataType:"json",
            data:JSON.stringify({configName:configName,configValue:configValue,remark:remark,isEncrypt:isEncrypt}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllConfigByPage(1);
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

    $(".config-update").live("click",function(){
        var noEmpty=$(this).parent().parent().find(".not-empty");
        for (var i=0;i<noEmpty.length;i++) {
            if (noEmpty[i].value==''){
                alert(''+dictionary['label.Alert2']+'');
                return;
            }
        }
        var nowPage=$("#Config_pagination ul li.actived").text();
        var configName=$(this).parent().parent().find(".change-config-configName").val();
        var configValue=$(this).parent().parent().find(".change-config-configValue").val();
        var remark=$(this).parent().parent().find(".change-config-remark").val();
        var id=$(this).parent().parent().find(".change-config-id").val();
        $.ajax({
            type: "POST",
            url:"admin/updateConfig.html",
            dataType:"json",
            data:JSON.stringify({configName:configName,configValue:configValue,remark:remark,id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllConfigByPage(nowPage);
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

    $(".config-delete-sure").live("click",function(){
        var nowPage=$("#Config_pagination ul li.actived").text();
        var id=$(this).parents("tr").find("td").eq(0).text();
        $.ajax({
            type: "POST",
            url:"admin/deleteConfig.html",
            dataType:"json",
            data:JSON.stringify({id:id}),
            success:function(data) {
                if(data.returnCode == "000"){
                    getAllConfigByPage(nowPage);
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