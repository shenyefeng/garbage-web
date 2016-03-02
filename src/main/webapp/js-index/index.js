$(function(){
	//使用自定义动画
	//过渡效果
	var animations		= ['right','left','top','bottom','rightFade','leftFade','topFade','bottomFade'];
	var total_anim		= animations.length;
	//设置选择效果
	var easeType		= 'swing';
	//每个转换的速度
	var animSpeed		= 450;
	//缓存
	var $hs_container	= $('#hs_container');
	var $hs_areas		= $hs_container.find('.hs_area');
	
	//第一预加载的所有图像
	$hs_images          = $hs_container.find('img');
	var total_images    = $hs_images.length;
	var cnt             = 0;
	$hs_images.each(function(){
		var $this = $(this);
		$('<img/>').load(function(){
			++cnt;
			if(cnt == total_images){
				$hs_areas.each(function(){
					var $area 		= $(this);
					//当鼠标进入该区域时，我们制作动画的电流
					//图像（随机阵列动画）
					//使下一个得到可见。
					//"over" 是一个标志，如果我们可以动画 
					//一个地区或没有（我们不希望2动画
					//为每个区域在同一时间）
					$area.data('over',true).bind('mouseenter',function(){
						if($area.data('over')){
							$area.data('over',false);
							//在这方面多少图像？
							var total		= $area.children().length;
							//可见光图像
							var $current 	= $area.find('img:visible');
							//可见光图像的索引
							var idx_current = $current.index();
							//的将被显示的下一个图像。
							//无论是下一个，或第一个如果当前是最后
							var $next		= (idx_current == total-1) ? $area.children(':first') : $current.next();
							//显示下一个（不可见）
							$next.show();
							//得到一个随机的动画
							var anim		= animations[Math.floor(Math.random()*total_anim)];
							switch(anim){
								//从当前幻灯片的权利
								case 'right':
								$current.animate({'left':$current.width()+'px'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','left':'0px'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从左边滑动
								case 'left':
								$current.animate({'left':-$current.width()+'px'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','left':'0px'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从顶部的幻灯片
								case 'top':
								$current.animate({'top':-$current.height()+'px'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','top':'0px'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从底部滑动
								case 'bottom':
								$current.animate({'top':$current.height()+'px'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','top':'0px'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从当前幻灯片的右侧和淡出
								case 'rightFade':
								$current.animate({'left':$current.width()+'px','opacity':'0'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','left':'0px','opacity':'1'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从当前幻灯片的左侧和淡出
								case 'leftFade':
								$current.animate({'left':-$current.width()+'px','opacity':'0'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','left':'0px','opacity':'1'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//从当前幻灯片的顶部和淡出
								case 'topFade':
								$current.animate({'top':-$current.height()+'px','opacity':'0'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','top':'0px','opacity':'1'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
								//当前幻灯片，从底部淡出
								case 'bottomFade':
								$current.animate({'top':$current.height()+'px','opacity':'0'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','top':'0px','opacity':'1'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;		
								default:
								$current.animate({'left':-$current.width()+'px'},animSpeed,easeType,function(){
									$current.hide().css({'z-index':'1','left':'0px'});
									$next.css('z-index','9999');
									$area.data('over',true);
								});
								break;
							}	
						}
					});
				});

				//当点击hs_container各个领域得到滑动
				$hs_container.bind('click',function(){
					$hs_areas.trigger('mouseenter');
				});
				
			}

		}).attr('src',$this.attr('src'));

	});	
	
});
function maskClose() {
	$('#maskClose').hide();
	$($.maskid).hide();
	$('#mask').hide();
	$.maskid = null
}
function mask(id) {
	$.maskid = id = '#' + id;
	if ($('#mask').length == 0) {
		$('body').append('<div id="mask" style="display:none"></div>');
		$('#mask').click(function() {
			if ($($.maskid).data('maskclose')) {
				maskClose()
			}
		})
	}
	if ($('.mask-close', id).length == 0) {
		$(id)
				.prepend(
						'<a class="mask-close" href="javascript:maskClose()"><span></span></a>')
	}
	$('#mask').show().css({
		'position' : 'absolute',
		'top' : 0,
		'z-index' : 99990,
		'width' : $(window).width(),
		'height' : $(document).height(),
		'background-color' : '#000',
		'opacity' : 0.8
	});
	var left = $(window).scrollLeft() + $(window).width() / 2
			- $(id).outerWidth() / 2, top = $(window).scrollTop()
			+ $(window).height() / 2 - $(id).outerHeight() / 2;
	top = Math.max(10, top);
	if ($($.maskid).data('top')) {
		top = $(window).scrollTop() + $($.maskid).data('top')
	}
	if ($($.maskid).data('left')) {
		left = $(window).scrollLeft() + $($.maskid).data('left')
	}
	$(id).show().css({
		'box-shadow' : '0px 0px 20px #999',
		'-moz-box-shadow' : '0px 0px 20px #999',
		'-webkit-box-shadow' : '0px 0px 20px #999',
		'border-radius' : '3px 3px 3px 3px',
		'-moz-border-radius' : '3px',
		'-webkit-border-radius' : '3px',
		top : top,
		left : left,
		'z-index' : 99998,
		'position' : 'absolute'
	})
}
$(window).resize(
		function() {
			if ($.maskid) {
				var left = $(window).scrollLeft() + $(window).width() / 2
						- $($.maskid).outerWidth() / 2, top = $(window)
						.scrollTop()
						+ $(window).height()
						/ 2
						- $($.maskid).outerHeight()
						/ 2;
				top = Math.max(10, top);
				if ($($.maskid).data('top')) {
					top = $(window).scrollTop() + $($.maskid).data('top')
				}
				if ($($.maskid).data('left')) {
					left = $(window).scrollLeft() + $($.maskid).data('left')
				}
				$('#mask').show().css({
					'width' : $(window).width(),
					'height' : $(document).height()
				});
				$($.maskid).show().css({
					top : top,
					left : left
				})
			}
		});
var zm = zm || {};
zm.langs = {
	"pagebar" : {
		"first" : "&lt;&lt;",
		"pre" : "&lt;",
		"next" : "&gt;",
		"last" : "&gt;&gt;",
		"pageSizePrefix" : "每页",
		"pageSizeSuffix" : "条",
		"info" : "显示{1}-{2}条 共{0}条"
	},
	"data" : {
		"nodata" : "无数据...",
		"loading" : "正在加载中...",
		"errorLoading" : "与后台联系错误"
	}
};
(function($) {
	$.fn.mask = function(msg) {
		var self = $(this);
		var _pMaskEl = self.parent();
		_pMaskEl.css({
			position : 'relative'
		});
		$('.__zm_mask', _pMaskEl).remove();
		var maskEl = $('<div style="background-color: rgb(200, 200, 200);background-color: rgba(200, 200, 200, 0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#00EFEFEF, endColorstr=#00EFEFEF);-ms-filter: \"progid:DXImageTransform.Microsoft.gradient(startColorstr=#00EFEFEF, endColorstr=#00EFEFEF)\";"></div>');
		maskEl.addClass('__zm_mask');
		maskEl.css({
			position : 'absolute',
			zIndex : (parseInt(_pMaskEl.css("zIndex"), 100) + 1) + '',
			height : _pMaskEl.outerHeight() + 'px',
			width : _pMaskEl.outerWidth() + 'px',
			top : '0px',
			left : '0px'
		});
		var msgAttr = {
			width : 100,
			height : 20
		};
		maskEl
				.html('<div style="text-align:center;height:'
						+ msgAttr.height
						+ 'px;width:'
						+ msgAttr.width
						+ 'px;margin-left:'
						+ (_pMaskEl.width() - msgAttr.width)
						/ 2
						+ 'px;margin-top:'
						+ (_pMaskEl.height() - msgAttr.height)
						/ 2
						+ 'px;border:solid 1px #999;padding:5px;color:black;background-color:#FFF;cursor:wait;">'
						+ msg || zm.langs.data.loading + '<div>');
		_pMaskEl.append(maskEl)
	};
	$.fn.unmask = function() {
		var self = $(this);
		var _pMaskEl = self.parent();
		$('.__zm_mask', _pMaskEl).remove()
	};
	$.fn.serializeObject = function(upBlank) {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (!upBlank && this.value == '')
				return true;
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ]
				}
				o[this.name].push(this.value)
			} else {
				o[this.name] = this.value
			}
		});
		return o
	};
	String.format = function() {
		var s = arguments[0];
		for (var i = 0; i < arguments.length - 1; i++) {
			var reg = new RegExp("\\{" + i + "\\}", "gm");
			s = s.replace(reg, arguments[i + 1])
		}
		return s
	};
	$.fn.grid = function(config) {
		var self = this;
		self.store = {
			data : {}
		};
		config = $.extend({
			pageOptions : [ 10, 20, 50, 100, 200 ],
			title : false,
			curPage : 1,
			totalPage : 1,
			sm : false,
			stripeRows : true,
			height : 450
		}, config);
		config.params = $.extend({
			start : 0,
			limit : 20
		}, config.params);
		self.initConfig = config;
		config.perPage = config.params.limit;
		self.view = {
			topbar : $('<div class="zm-grid-topbar"></div>'),
			pagebar : $('<div class="zm-grid-pagebar"></div>'),
			grid : {
				thead : $('<div class="zm-grid-thead"></div>'),
				tbody : $('<div class="zm-grid-tbody"></div>')
			},
			noData : '<div style="color:red;line-height:40px;text-align:center;">'
					+ zm.langs.data.nodata + '</div>',
			page : {
				first : '<a class="js-page-first XXX" href="javascript:void(0)">'
						+ zm.langs.pagebar.first + '</a>',
				prev : '<a class="js-page-pre XXX" href="javascript:void(0)">'
						+ zm.langs.pagebar.pre + '</a>',
				next : '<a class="js-page-next XXX" href="javascript:void(0)">'
						+ zm.langs.pagebar.next + '</a>',
				last : '<a class="js-page-last XXX" href="javascript:void(0)">'
						+ zm.langs.pagebar.last + '</a>',
				pageInfo : zm.langs.pagebar.info,
				number : '<a href="javascript:void(0)" class="js-page-number">{0}</a>',
				refresh : '<b>&nbsp;</b>',
				curNumber : '<em>{0}</em>'
			}
		};
		self.view.grid.wrap = $(
				'<div class="zm-grid-wrap" style="overflow:auto;"></div>')
				.append(self.view.grid.thead).append(self.view.grid.tbody);
		self.view.table = $('<div class="zm-grid"></div>').append(
				self.view.topbar).append(self.view.grid.wrap).append(
				self.view.pagebar);
		$(self).html('').append(self.view.table);
		function initTopBar() {
			if (config.tbar) {
				$.each(config.tbar, function(i, bar) {
					var _bar = '<' + (bar.tag || 'button') + ' href="#" type="'
							+ (bar.btnType || 'button')
							+ '" class="zm-grid-topbar-button '
							+ (bar.cls ? bar.cls : '') + '" style="'
							+ (bar.style ? bar.style : '') + '">'
							+ (bar.text || '') + '</' + (bar.tag || 'button')
							+ '>';
					$(_bar).appendTo(self.view.topbar).click(bar.handler)
				})
			}
			if (!self.view.topbar.html()) {
				self.view.topbar.height(0)
			}
		}
		;
		var hasFilter = false, fixWidth = 0;
		function _header() {
			var strHeader = '<ol class="zm-grid-header">';
			var strFilter = '';
			var strCol = '';
			if (config.sm) {
				fixWidth += 30;
				strHeader += '<li style="width:20px;" class="zm-grid-column-sm"><input type="checkbox" name="zm-item"></input></th>';
				strFilter += '<li style="width:20px"></li>'
			}
			for (var i = 0; i < config.columns.length; i++) {
				var col = config.columns[i];
				var w = col.width ? col.width : 100;
				var expandColumnCls = '';
				if (col.autoWidth) {
					expandColumnCls = 'zm-grid-column-expand'
				} else {
					fixWidth += parseInt(w) + 10
				}
				strHeader += '<li style="width:' + w + 'px" class="'
						+ expandColumnCls + '">' + col.header + '</li>';
				if (col.filter) {
					if (col.filter.options) {
						var strOpts = [];
						for (var j = 0, len = col.filter.options.length; j < len; j++) {
							var opt = col.filter.options[j];
							strOpts.push('<option value="' + opt[0] + '">'
									+ opt[1] + '</option>')
						}
						;
						strFilter += '<li style="width:' + w
								+ 'px" class="zm-grid-column-filter '
								+ expandColumnCls
								+ '"><div><select class="filter-field" name="'
								+ (col.filter.name || col.dataIndex) + '">'
								+ strOpts.join('') + '</select></div></li>'
					} else {
						strFilter += '<li style="width:'
								+ w
								+ 'px" class="zm-grid-column-filter '
								+ expandColumnCls
								+ '"><div><input class="filter-field" type="text" name="'
								+ (col.filter.name || col.dataIndex)
								+ '"><span class="filter-search"></span></div></li>';
						hasFilter = true
					}
				} else {
					strFilter += '<li style="width:' + w + 'px" class="'
							+ expandColumnCls + '"></li>'
				}
			}
			strHeader += '</ol>';
			if (hasFilter) {
				strHeader += '<ol>' + strFilter + '</ol>'
			}
			self.view.grid.thead.html(strHeader);
			if (config.sm) {
				$('input[type=checkbox]', self.view.grid.thead).click(
						function() {
							if (this.checked) {
								$('input[type=checkbox]', self.view.grid.tbody)
										.each(function() {
											this.checked = true
										}).closest('tr').addClass(
												'row_selected')
							} else {
								$('input[type=checkbox]', self.view.grid.tbody)
										.each(function() {
											this.checked = false
										}).closest('tr').removeClass(
												'row_selected')
							}
						});
				self.getCheckedRowRecords = function() {
					var rec = [];
					$('input[type=checkbox]:checked', self.view.grid.tbody)
							.each(function(i, cbRow) {
								rec.push(self.store.data.items[cbRow.value])
							});
					return rec
				}
			}
			if (hasFilter) {
				$('li.zm-grid-column-filter', self.view.grid.thead).keydown(
						function(e) {
							if (e.keyCode == 13) {
								resetConfigParam();
								self.store.load()
							} else if (e.keyCode == 27) {
								$('.filter-field', self.view.grid.thead)
										.val('');
								resetConfigParam();
								self.store.load()
							}
						}).mouseenter(function(e) {
					$('.filter-search', this).show()
				}).mouseleave(function(e) {
					$('.filter-search', this).hide()
				});
				$('.filter-search', self.view.grid.thead).click(function() {
					resetConfigParam();
					self.store.load()
				});
				$('select.filter-field', self.view.grid.thead).change(
						function() {
							resetConfigParam();
							self.store.load()
						})
			}
		}
		;
		function _body() {
			var strBody = '';
			if (self.store.data.totalCount > 0) {
				$
						.each(
								self.store.data.items,
								function(i, el) {
									var rowCls = '';
									if (i % 2 == 0)
										rowCls = ' class="row_odd"';
									strBody += '<ul' + rowCls + '>';
									if (config.sm) {
										strBody += '<li style="width:20px" class="zm-grid-column-sm"><input type="checkbox" name="zm-item'
												+ i
												+ '" value="'
												+ i
												+ '"></input></li>'
									}
									for (var i = 0; i < config.columns.length; i++) {
										var col = config.columns[i];
										var val = col.dataIndex != '' ? eval('(el.'
												+ col.dataIndex + ')')
												: '';
										var w = col.width ? col.width : 100;
										var expandColumnCls = '', clsArr = [], cls = '';
										if (col.autoWidth)
											expandColumnCls = 'zm-grid-column-expand';
										if (expandColumnCls != '') {
											clsArr.push(expandColumnCls)
										}
										;
										if (col.type == 'action') {
											clsArr.push('_gridAction')
										}
										if (col.cls) {
											clsArr.push(col.cls)
										}
										if (clsArr.length > 0) {
											cls = ' class="' + clsArr.join(' ')
													+ '"'
										}
										strBody += '<li style="width:'
												+ w
												+ 'px;" '
												+ cls
												+ '>'
												+ (col.renderer ? col.renderer(
														val, el) : val)
												+ '</li>'
									}
									strBody += '</ul>'
								})
			} else {
				strBody += String.format(self.view.noData,
						config.columns.length + (config.sm ? 1 : 0))
			}
			self.view.grid.tbody.html(strBody);
			if (self.store.data.totalCount > 0) {
				$.each(self.store.data.items, function(i, el) {
					$.each(config.columns, function(index, obj) {
						if (obj.afterrender) {
							var val = obj.dataIndex != '' ? eval('(el.'
									+ obj.dataIndex + ')') : '';
							var tdEl = $('ul:eq(' + index + ')', trEl);
							var trEl = $('ul:eq(' + i + ')',
									self.view.grid.tbody);
							obj.afterrender(val, el, tdEl, trEl,
									self.view.table)
						}
					})
				})
			}
			$('._gridAction', self.view.grid.tbody)
					.click(
							function(e) {
								var t = e.target;
								if (t.tagName === 'A' || t.tagName === 'BUTTON') {
									var rowEl = $(this).closest('ul');
									var rowIndex = $(this).closest('ul')
											.index();
									var r = self.store.data.items[rowIndex];
									var action = t.title
											|| t.href.substring(t.href
													.indexOf('#') + 1);
									self[action]
											&& self[action](r, rowEl, rowIndex)
								}
							});
			$('ul', self.view.grid.tbody).dblclick(function(e) {
				var rowIndex = $(this).index();
				var r = self.store.data.items[rowIndex];
				var action = 'fireDetail';
				self[action] && self[action](r)
			});
			if (config.sm) {
				$('input[type=checkbox]', self.view.grid.tbody)
						.click(
								function() {
									var allChecked = ($(
											'input[type=checkbox]:checked',
											self.view.grid.tbody).length == $(
											'input[type=checkbox]',
											self.view.grid.tbody).length);
									if (this.checked) {
										$(this).closest('ul').addClass(
												'row_selected')
									} else {
										$(this).closest('ul').removeClass(
												'row_selected')
									}
									if (allChecked) {
										$('input[type=checkbox]',
												self.view.grid.thead)[0].checked = true
									} else {
										$('input[type=checkbox]',
												self.view.grid.thead)[0].checked = false
									}
								})
			}
			_expandColumn()
		}
		;
		function updatePagebar() {
			var strPage = '';
			var pagePanel = document.createElement("DIV");
			self.store.data = self.store.data || {};
			self.store.data.items = self.store.data.items || [];
			if (!config.noPageBar) {
				var cp = parseInt(config.curPage);
				var pp = parseInt(config.perPage);
				var tp = parseInt(config.totalPage);
				var strPage = self.view.page.first.replace('XXX', cp > 1 ? ''
						: 'disabled');
				strPage += self.view.page.prev.replace('XXX', cp > 1 ? ''
						: 'disabled');
				var start = ((cp - 2) < 1 || tp <= 5) ? 1
						: ((cp + 2 < tp) ? (cp - 2) : Math.max(1, tp - 5));
				var end = (start + 4) < tp ? (start + 5) : tp;
				var strNumber = '';
				for (var i = start; i <= end; i++) {
					if (i == cp) {
						strNumber += String.format(self.view.page.curNumber, i);
						continue
					}
					strNumber += String.format(self.view.page.number, i)
				}
				strPage += strNumber;
				strPage += self.view.page.next.replace('XXX', cp < tp ? ''
						: 'disabled');
				strPage += self.view.page.last.replace('XXX', cp < tp ? ''
						: 'disabled')
			}
			pagePanel.innerHTML = strPage;
			var _el_b = document.createElement("B");
			_el_b.innerHTML = '&nbsp;';
			pagePanel.appendChild(_el_b);
			_el_b.onclick = function() {
				self.store.load()
			};
			if (!config.noPageBar) {
				var s = document.createElement("SELECT");
				for (var i = 0; i < config.pageOptions.length; i++) {
					var v = config.pageOptions[i];
					if (config.perPage == v) {
						opt = new Option(v, v, true)
					} else {
						opt = new Option(v, v)
					}
					s.options.add(opt)
				}
				s.onchange = function() {
					config.curPage = 1;
					config.perPage = this.value;
					config.params.limit = config.perPage;
					updatePage()
				}
			}
			;
			self.view.pagebar.html('');
			if (s) {
				pagePanel.appendChild(document
						.createTextNode(zm.langs.pagebar.pageSizePrefix));
				pagePanel.appendChild(s);
				var textNode = document
						.createTextNode(zm.langs.pagebar.pageSizeSuffix);
				pagePanel.appendChild(textNode)
			}
			self.view.pagebar.append(pagePanel);
			self.view.pagebar.append(String.format(self.view.page.pageInfo,
					self.store.data.totalCount || 0, config.params.start + 1,
					config.params.start + self.store.data.items.length))
		}
		;
		self.view.pagebar
				.click(function(e) {
					if ($(e.target).hasClass('disabled'))
						return;
					switch (e.target.className.replace(' ', '')) {
					case 'js-page-first':
						config.curPage = 1;
						break;
					case 'js-page-pre':
						config.curPage -= 1;
						break;
					case 'js-page-next':
						config.curPage = config.curPage - 0 + 1;
						break;
					case 'js-page-last':
						config.curPage = (self.store.data.totalCount == 0 ? 1
								: Math.ceil(self.store.data.totalCount
										/ config.perPage));
						break;
					case 'js-page-number':
						config.curPage = e.target.textContent
								|| e.target.outerText - 0;
						break;
					default:
						return false
					}
					updatePage();
					e.preventDefault();
					e.stopPropagation()
				});
		function updatePage() {
			config.params.start = (config.curPage - 1) * config.perPage;
			self.store.load()
		}
		;
		function init() {
			initTopBar();
			_header();
			updatePagebar();
			self.refreshView();
			var _el = self;
			while (_el.width() == 0 || !_el.is(":visible")) {
				_el = _el.parent()
			}
			;
			self.wrapWidth = _el.width();
			var w = Math.max(fixWidth + 120, self.wrapWidth);
			self.view.grid.thead.css('width', w);
			self.view.grid.tbody.css('width', w)
		}
		;
		function _expandColumn() {
			var w = Math.max(100, $('.zm-grid-header', self.view.grid.thead)
					.width()
					- fixWidth - 35);
			$(self).find('.zm-grid-column-expand').width(w)
		}
		$(window).resize(function() {
			_expandColumn()
		});
		self.refreshView = function() {
			var h = self.initConfig.height - self.view.grid.thead.outerHeight()
					- self.view.topbar.outerHeight();
			if (!config.noPageBar) {
				h -= self.view.pagebar.outerHeight()
			}
			self.view.grid.tbody.css({
				'height' : h
			})
		};
		self.store.load = function(cfg) {
			self.view.table.mask(zm.langs.data.loading);
			cfg = cfg || {};
			self.url = cfg.url || self.url || config.url;
			var filterParams = {};
			if (hasFilter) {
				$('.filter-field', self.view.grid.thead).each(function(i, el) {
					if (el.value != '') {
						filterParams[el.name] = el.value
					}
				})
			}
			var params = $.extend({}, config.params, cfg.params,
					self.baseParams, filterParams);
			$.ajax({
				type : "POST",
				url : self.url,
				data : params,
				dataType : 'json',
				success : function(data) {
					self.store.data = data;
					config.totalPage = data.totalCount > 0 ? Math
							.ceil(data.totalCount / config.perPage) : 1;
					_body();
					updatePagebar();
					self.view.table.unmask()
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(zm.langs.data.errorLoading);
					self.view.table.unmask()
				}
			})
		};
		function resetConfigParam() {
			config.curPage = 1;
			config.params.start = 0
		}
		self.config = config;
		init();
		return this
	}
})(jQuery);
$(document).ready(
		function() {
			$('.zm-combo>dt').click(function() {
				$(this).next('dd').toggle()
			});
			$('.zm-combo')
					.delegate(
							'span',
							'click',
							function(e) {
								var text = $(this).text(), val = $(this).attr(
										'value'), p = $(this).parent();
								p.prev('dt').html(text);
								p.children('span').removeClass('selected');
								$(this).addClass('selected');
								p.siblings('input').val(val).trigger('change');
								p.hide()
							});
			$('.zm-combo').hover(function() {
				$(this).attr('working', 1)
			}, function() {
				$(this).attr('working', 0)
			});
			$(document).click(function() {
				$('.zm-combo').each(function() {
					if ($(this).attr('working') != 1) {
						$(this).children('dd').hide()
					}
				})
			})
		});
(function($) {
	$.fn.shuffleLetters = function(prop) {
		var options = $.extend({
			"step" : 8,
			"fps" : 25,
			"text" : "",
			"callback" : function() {
			}
		}, prop);
		return this.each(function() {
			var el = $(this), str = "";
			if (el.data('animated')) {
				return true
			}
			;
			el.data('animated', true);
			if (options.text) {
				str = options.text.split('')
			} else {
				str = el.text().split('')
			}
			;
			var types = [], letters = [];
			for (var i = 0; i < str.length; i++) {
				var ch = str[i];
				if (ch == " ") {
					types[i] = "space";
					continue
				} else if (/[a-z]/.test(ch)) {
					types[i] = "lowerLetter"
				} else if (/[A-Z]/.test(ch)) {
					types[i] = "upperLetter"
				} else if (/[0-9]/.test(ch)) {
					types[i] = "number"
				} else {
					types[i] = "symbol"
				}
				letters.push(i)
			}
			;
			el.html("");
			(function shuffle(start) {
				var i, len = letters.length, strCopy = str.slice(0);
				if (start > len) {
					el.data('animated', false);
					options.callback(el);
					return
				}
				;
				for (i = Math.max(start, 0); i < len; i++) {
					if (i < start + options.step) {
						strCopy[letters[i]] = randomChar(types[letters[i]])
					} else {
						strCopy[letters[i]] = ""
					}
				}
				;
				el.text(strCopy.join(""));
				setTimeout(function() {
					shuffle(start + 1)
				}, 1000 / options.fps)
			})(-options.step)
		})
	};
	function randomChar(type) {
		var pool = "";
		if (type == "lowerLetter") {
			pool = "abcdefghijklmnopqrstuvwxyz0123456789"
		} else if (type == "upperLetter") {
			pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		} else if (type = "number") {
			pool = "0123456789"
		} else if (type == "symbol") {
			pool = ",.?/\\(^)![]{}*&^%$#'\""
		}
		;
		var arr = pool.split('');
		return arr[Math.floor(Math.random() * arr.length)]
	}
})(jQuery);
(function($) {
	$.fn.startLoading = function(config) {
		var me = $(this);
		config = config || {};
		if ($('.loading-anim', me).length == 0) {
			var w = me.outerWidth(), h = me.outerHeight(), margin = config.margin || 5, style = me
					.data('loading-style')
					|| '', cls = me.data('loading-class') || '';
			if (me.css('position') == 'static') {
				me.css({
					'position' : 'relative'
				})
			}
			me.append('<div class="loading-anim ' + cls + '" style="left:'
					+ margin + 'px;top:' + margin + 'px;width:'
					+ (w - 2 * margin) + 'px;height:' + (h - 2 * margin)
					+ 'px;' + style + '"></div>')
		}
	};
	$.fn.stopLoading = function() {
		$('.loading-anim', this).remove()
	}
})(jQuery);
$(function() {
	var zm = window.zm || {};
	var validate = zm.validate || {};
	var rules = $
			.extend(
					{
						equal : /^.*$/,
						required : /^.+$/,
						numeric : /^[0-9]+$/,
						integer : /^\-?[0-9]+$/,
						decimal : /^\-?[0-9]*\.?[0-9]+$/,
						date : /^\-?[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/,
						phone : /^[01]?[-.]?(\([2-9]\d{2}\)|[2-9]\d{2})[-.]?\d{3}[-.]?\d{4}$/,
						email : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						alpha : /^[a-z]+$/i,
						alphaNumeric : /^[a-z0-9]+$/i,
						ip : /^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$/i,
						base64 : /[^a-zA-Z0-9\/\+=]/i,
						url : /^((http|https):\/\/(\w+:{0,1}\w*@)?(\S+)|)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/
					}, validate.rules);
	var messages = $.extend({
		equal : 'The %s and %0s is inconsistent.',
		required : 'The %s is required.',
		email : 'The %s must contain a valid email address.',
		phone : 'The %s must contain a valid phone number.',
		alpha : 'The %s must only contain alphabetical characters.',
		alphaNumeric : 'The %s must only contain alpha-numeric characters.',
		numeric : 'The %s must contain only numbers.',
		integer : 'The %s must contain an integer.',
		decimal : 'The %s must contain a decimal number.',
		ip : 'The %s must contain a valid IP.',
		base64 : 'The %s must contain a base64 string.',
		url : 'The %s must contain a valid URL.',
		date : 'The %s must contain a valid Date.'
	}, validate.messages);
	$.fn.validateForm = function(warningFn, successFn) {
		var success = true;
		var tag = $(this).data('tag') || 'div';
		$(this).find('input,textarea').each(
				function() {
					var el = $(this);
					var validate = $(this).data('validate');
					if (validate) {
						var val = $(this).val();
						var arrValidate = validate.split('|');
						var msg = el.data('message');
						var isValidated = true;
						$.each(arrValidate, function(i, v) {
							if (!rules[v].test(val)) {
								var errorMsg = msg ? msg : messages[v].replace(
										'%s', el.prop('placeholder'));
								if (warningFn) {
									warningFn(el, errorMsg)
								} else {
									el.next(tag).remove().end().after(
											'<' + tag + ' class="field-error">'
													+ errorMsg + '</' + tag
													+ '>')
								}
								success = false;
								isValidated = false;
								return false
							} else if (v == 'equal' && el.data('for')
									&& val != $('#' + el.data('for')).val()) {
								var errorMsg = msg ? msg : messages[v].replace(
										'%s', el.prop('placeholder')).replace(
										'%0s',
										$('#' + el.data('for')).prop(
												'placeholder'));
								if (warningFn) {
									warningFn(el, errorMsg)
								} else {
									el.next(tag).remove().end().after(
											'<' + tag + ' class="field-error">'
													+ errorMsg + '</' + tag
													+ '>')
								}
								success = false;
								isValidated = false;
								return false
							}
						});
						if (isValidated) {
							if (successFn) {
								successFn(el)
							} else {
								el.next('.field-error').remove()
							}
						}
					}
				});
		return success
	};
	$('.frm-submit').submit(function() {
		var success = $(this).validateForm();
		var me = $(this);
		if (success && me.attr('callback')) {
			try {
				var fn = me.attr('callback');
				if (me.data('mask')) {
					me.startLoading()
				}
				$.post(me.attr('action'), me.serialize(), function(r) {
					eval(fn + '(' + r + ')');
					if (me.data('mask')) {
						me.stopLoading()
					}
				})
			} catch (e) {
			}
			return false
		} else {
			return success
		}
	})
});
$(function() {
	$(".numeric").keydown(
			function(event) {
				if ($.inArray(event.keyCode, [ 46, 8, 9, 27, 13, 190 ]) !== -1
						|| (event.keyCode == 65 && event.ctrlKey === true)
						|| (event.keyCode >= 35 && event.keyCode <= 39)) {
					return
				} else {
					if (event.shiftKey
							|| (event.keyCode < 48 || event.keyCode > 57)
							&& (event.keyCode < 96 || event.keyCode > 105)) {
						event.preventDefault()
					}
				}
			})
});