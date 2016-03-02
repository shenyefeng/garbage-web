<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${!empty cookie.lang}">
	<fmt:setLocale value="${cookie.lang.value}" />
</c:if>
<fmt:setBundle basename="i18n/messages" />
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title><fmt:message key="title.index" /></title>
<link rel="shortcut icon" href="favicon.ico">
	<!--[if IE]>
<script src="js-index/html5.js"></script>
<link type="text/css" rel="stylesheet" href="css-index/ie.min.css" />
<link type="text/css" rel="stylesheet" href="css-index/ie6.min.css" />
<![endif]-->
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<link type="text/css" rel="stylesheet" href="css-index/style.css">
<script type="text/javascript" src="js-index/index.js"></script>
<script type="text/javascript" src="js-index/jquery.easing.1.3.js"></script>
<link type="text/css" rel="stylesheet" href="css-index/index.css">

</head>
<body class="zm">
	<div class="w-header">
		<header> 
		<a class="icon icon-phone" href="tel:400-123-4567">400-123-4567</a>
		<a class="icon icon-email" href="mailto:service@mljy.com">service@mljy.com</a>
		<div>
			<%-- <span class="icon icon-tree" title="少砍伐树木"><i id="res_tree">10407.98</i> (棵)</span><span class="icon icon-oil" title="节省石油"><i id="res_oil">23186.75</i>
				(桶)</span><span class="icon icon-co2" title="减少CO2排放"><i id="res_co2">11502.13</i> (吨)</span>
			<script type="text/javascript">
				(function(){
					var r=null, ids = ['res_tree','res_oil','res_co2'];	
					function loadData(){
						$.get('<%=basePath%>home/getContribution?_date='+new Date().getTime(),
								function(data,status) {
									var d=data.items;
									r= [d.t,d.o,d.c];
									},
									'json'
								);
						setTimeout(arguments.callee,60000);
						}
					function updateData() {
						var i = Math.floor(Math.random()*5);
						if(i<3&&r!=null)
							$('#'+ids[i]).shuffleLetters({text:''+r[i]});
						setTimeout(arguments.callee,3000);
						}
					loadData();
					updateData();
					})();
				</script> --%>
		</div>
		</header>
		<footer> <a href="<%=basePath%>" class="logo"><img alt="<fmt:message key="label.webname"/>" src="img-index/logo.png" width="142"
			height="50"></a> <a href="<%=basePath%>special" class="slim"><img src="img-index/title-bar.jpg" width="510" height="50"></a> <a
			href="http://wpa.qq.com/msgrd?v=3&uin=656090842&site=qq&menu=yes" class="qq" target="_blank">QQ联系客户</a> <a href="<%=basePath%>activity_schedule"
			class="clander">日常活动安排</a> </footer>
	</div>
	<div class="w-navigation">
		<nav class="clearfix"> 
		<a href="<%=basePath%>" class="selected">首页</a> 
		
		<%-- 
		<a href="<%=basePath%>mall">环保商城</a> 
		<a href="<%=basePath%>show_image">绿色生活Show</a>
		<a href="<%=basePath%>waste_sorting">垃圾分类知识</a> 
		<a href="<%=basePath%>special">环保专题</a> 
		<a href="<%=basePath%>review">留言</a>
		 --%>
		
		<div>
			<form action="<%=basePath%>home/getCustomerByBarcode" class="frm-submit" callback="getBarcodeInfo">
				<lable>二维码校验：<input onfocus="this.style.backgroundColor=&quot;#EEE&quot;;this.placeholder=&quot;&quot;"
					onblur="this.style.backgroundColor=&quot;&quot;;this.placeholder=&quot;请输入您的二维码号&quot;" maxlength="20" autocomplete="off" placeholder="请输入您的二维码号"
					name="barcode_code" data-message=" " data-validate="required">
				<button type="submit">提交</button>
				</lable>
			</form>
		</div>
		</nav>
	</div>
	<script type="text/javascript">function getBarcodeInfo(res) {alert(res.detail);}</script>
	<div id="wrapper">
		<div class="w-home-map">
			<div>
				<div id="hs_container" class="hs_container">
					<div class="hs_area hs_area1">
						<img class="hs_visible" src="img-index/area1/1.jpg" alt=""/>
						<img src="img-index/area1/3.jpg" alt=""/>
					</div>
					<div class="hs_area hs_area2">
						<img class="hs_visible" src="img-index/area2/1.jpg" alt=""/>
						<img src="img-index/area2/3.jpg" alt=""/>
					</div>
					<div class="hs_area hs_area3">
						<img class="hs_visible" src="img-index/area3/1.jpg" alt=""/>
						<img src="img-index/area3/3.jpg" alt=""/>
					</div>
					<div class="hs_area hs_area4">
						<img class="hs_visible" src="img-index/area4/1.jpg" alt=""/>
						<img src="img-index/area4/3.jpg" alt=""/>
					</div>
					<div class="hs_area hs_area5">
						<img class="hs_visible" src="img-index/area5/1.jpg" alt=""/>
						<img src="img-index/area5/3.jpg" alt=""/>
					</div>
				</div>
				<section>
				<form class="frm-submit" method="POST" callback="doLogin" action="<%=basePath%>home/authenticate" data-tag="small">
					<label>
						<span>用户名：</span>
						<input type="text" placeholder="用户名" name="customer_phone" data-validate="phone" data-message="手机号码不能为空"/>
					</label>
					<label class="lbpasswd">
						<span>密 码：</span>
						<input type="password" placeholder="密码" name="customer_password" data-validate="required" data-message="密码不能为空"/>
					</label>
					<label class="clearfix">
						<span>验证码：</span>
						<input id="js_captcha_el" type="text" placeholder="验证码" autocomplete="off" name="captcha" data-validate="required" data-message="验证码不能为空"><img alt="验证码" src="<%=basePath%>validationCode2" id="js_captcha_img" onclick="this.src=&#39;<%=basePath%>validationCode2?&#39;+(new Date().valueOf());"/>
					</label>
					<button type="submit" class="button btn-white">登录</button>
				</form>
				</section>
			</div>
		</div>

		<div class="w-stepbystep clearfix">
			<dl class="clearfix">
				<dt>1</dt>
				<dd>
					<h2>成为会员</h2>
					<p>当前仅可线下注册</p>
					<a href="<%=basePath%>custom_center">查看已开通服务的社区</a>
				</dd>
			</dl>
			<dl class="clearfix step2">
				<dt>2</dt>
				<dd>
					<h2>获取积分</h2>
					<ul>
						<li>参与美丽家园垃圾分类行动，投放可回收物</li>
						<li>上传活动照片并获得一定数量点赞、评论</li>
						<li>对他人照片点赞、评论</li>
						<li>兑换绿色产品</li>
						<li>参加美丽家园的各种活动</li>
					</ul>
					<a href="<%=basePath%>rules">查看积分规则</a>
				</dd>
			</dl>
			<dl class="clearfix">
				<dt>3</dt>
				<dd>
					<h2>积分用途</h2>
					<ul>
						<li>兑换各类商品</li>
						<li>捐赠公益项目</li>
						<li>参与美丽家园游戏</li>
						<li>转赠给美丽家园的朋友</li>
					</ul>
					<a href="<%=basePath%>mall">前往积分商城</a>
				</dd>
			</dl>
		</div>
	</div>
	<div class="bg"></div>
	<div class="w-footer">
		<div class="clearfix">
			<dl>
				<dt>美丽家园</dt>
				<dd>
					<a href="<%=basePath%>custom_center">服务范围</a>
				</dd>
				<dd>
					<a href="<%=basePath%>be_customer">成为会员</a>
				</dd>
				<dd>
					<a href="<%=basePath%>rules">积分规则</a>
				</dd>
				<dd>
					<a href="<%=basePath%>join_us">加入我们</a>
				</dd>
			</dl>
			<dl>
				<dt>关注我们</dt>
				<dd>
					<a href="http://weibo.com/" target="_blank">新浪微博</a>
				</dd>
				<dd>
					<a href="http://t.qq.com/" target="_blank">腾讯微博</a>
				</dd>
				<dd>
					<a class="wechat-pop" href="javascript:void(0)">微信<img src="img-index/QR.png" width="100" height="100"></a>
				</dd>
			</dl>
			<dl style="width: 200px;">
				<dt>联系我们</dt>
				<dd>
					<a href="tel:400-123-4567">电话：400-123-4567</a>
				</dd>
				<dd>
					<a href="mailto:service@mljy.com">邮箱：service@mljy.com</a>
				</dd>
				<dd>地址：平湖市民政局创益园</dd>
				<dd>邮编：314200</dd>
			</dl>
			<dl style="width: 290px;">
				<dt>管理员登录</dt>
				<dd>
					<a target="_blank" href="<%=basePath%>home.jsp">管理员登录</a>
				</dd>
			</dl>
			<section> <img src="img-index/beautyhome-logo.png" width="168" height="43">
			<span>垃圾分类 · 造福地球</span> </section>
		</div>
		<h4>Copyright © 2014 - 2014 mljy.com All Rights Reserved</h4>
	</div>
	<div class="w-back-top" style="display: none;">
		<a href="<%=basePath%>#top" id="backtop"></a>
	</div>
	<script type="text/javascript">
	$(window).scroll(function() {
		if ($(this).scrollTop() > 80) {
			$('.w-back-top').fadeIn(); 
			} else {
				$('.w-back-top').fadeOut();
				}
		});
	$('#backtop').click(function(event) {
		event.preventDefault();
		$("html, body").animate({scrollTop: 0}, 800);
		});
	</script>

</body>
</html>