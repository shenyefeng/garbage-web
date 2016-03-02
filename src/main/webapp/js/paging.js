//分页插件，只适合inpaasmgr，如果需要用到其他项目中必须修改部分参数.
//为简便，将搜索功能也放在这里...
(function($){
	$.fn.paging=function(totalPages,choosePage,fnId){
		//this.remove();
		var idx=fnId.indexOf('_');
		var getFnName=eval('getAll'+fnId.substring(0,idx)+'ByPage');
		var pageContent='';
		if (totalPages<=7){
			for(var i=1;i<=totalPages;i++){
				pageContent+=  '<li class="li-num">'+i+'</li>';
			}
			this.find('ul').html('<li class="page-one prev-page">&laquo;</li>'+pageContent+'<li class="page-one next-page">&raquo;</li>');
			this.find('ul li.li-num').eq(choosePage-1).addClass("actived");
		}
		if (totalPages>7){
			if (choosePage==1){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num actived">1</li><li class="li-num">2</li><li class="li-num">3</li><li class="no-border">...</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else if (choosePage==2){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="li-num actived">2</li><li class="li-num">3</li><li class="li-num">4</li><li class="no-border">...</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else if (choosePage==3){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="li-num">2</li><li class="li-num actived">3</li><li class="li-num">4</li><li class="li-num">5</li><li class="no-border">...</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}					
			else if (choosePage==4){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="li-num">2</li><li class="li-num">3</li><li class="li-num actived">4</li><li class="li-num">5</li><li class="li-num">6</li><li class="no-border">...</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}					
			else if (choosePage==totalPages){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="no-border">...</li><li class="li-num">'+(totalPages-2)+'</li><li class="li-num">'+(totalPages-1)+'</li><li class="li-num actived">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else if (choosePage==totalPages-1){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="no-border">...</li><li class="li-num">'+(totalPages-3)+'</li><li class="li-num">'+(totalPages-2)+'</li><li class="li-num actived">'+(totalPages-1)+'</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else if (choosePage==totalPages-2){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="no-border">...</li><li class="li-num">'+(totalPages-4)+'</li><li class="li-num">'+(totalPages-3)+'</li><li class="li-num actived">'+(totalPages-2)+'</li><li class="li-num">'+(totalPages-1)+'</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else if (choosePage==totalPages-3){
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="no-border">...</li><li class="li-num">'+(totalPages-5)+'</li><li class="li-num">'+(totalPages-4)+'</li><li class="li-num actived">'+(totalPages-3)+'</li><li class="li-num">'+(totalPages-2)+'</li><li class="li-num">'+(totalPages-1)+'</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');
			}
			else{
				this.find('ul').html('<li class="page-one prev-page">&laquo;</li><li class="li-num">1</li><li class="no-border">...</li><li class="li-num">'+(choosePage-2)+'</li><li class="li-num">'+(choosePage-1)+'</li><li class="li-num actived">'+choosePage+'</li><li class="li-num">'+(parseInt(choosePage)+1)+'</li><li class="li-num">'+(parseInt(choosePage)+2)+'</li><li class="no-border">...</li><li class="li-num">'+totalPages+'</li><li class="page-one next-page">&raquo;</li>');	
			}
			//this.append('<div class="go-page"><span>跳转到: </span><input type="text" /><input type="button" value="go" class="go" /></div>');
		}
		if (totalPages!=1){
			if (choosePage==1){
				this.find('ul li.prev-page').addClass("disabled");
			}
			if (choosePage==totalPages){
				this.find('ul li.next-page').addClass("disabled");
			}
		}else{
			this.find('ul li.page-one').addClass("disabled");
		}
		this.find('ul li.li-num').die().live("click",function(){
			var goPage=$(this).text();
			if ($(this).is(".actived")){
				return;
			}
			getFnName(goPage);
		});
		this.find('.go').die().live("click",function(){
			var goPage=$(this).prev().val();
			getFnName(goPage);
		});
		var nowPage=this.find('ul li.actived').text();
		this.find('ul li.page-one').die().live("click", function(){
			var nowPageInt=parseInt(nowPage);
			if (!$(this).is(".disabled")){
				if ($(this).is(".prev-page")){
					getFnName(nowPageInt-1);
				}
				if ($(this).is(".next-page")){
					getFnName(nowPageInt+1);
				}
			}else{
				return;
			}
		});
		$('.man-search form').die().live('submit',function(){
			getFnName(1);
			return false;
		});
	};
})(jQuery);