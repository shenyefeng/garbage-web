<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${!empty cookie.lang}">
	 <fmt:setLocale value="${cookie.lang.value}" />
</c:if>
<fmt:setBundle basename="i18n/messages" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><fmt:message key="title.home"/></title>
<link href="css-main/main.css" rel="stylesheet" type="text/css" />
<link href="css-main/popup.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="favicon.ico">
<script>
	if ('${sessionScope.userName}' == '') {
		window.location.href = "login.jsp";
	}

</script>
</head>

<body>
<div class="header cc">
   <div class="logo">
<%-- 		<c:choose>
			<c:when test="${cookie.lang.value == 'zh_CN'}">
				<img src="img-new/main-title-logo.png" />
			</c:when>
			<c:when test="${cookie.lang.value == 'en_US'}">
				<img src="img-new/main-title-logo-en.png" />
			</c:when>
			<c:otherwise>
				<img src="img-new/main-title-logo.png" />
			</c:otherwise>
		</c:choose> --%>
		<span class="logo-text"><fmt:message key="label.logomsg"/></span>
   </div>
   <div class="users-box">
       <span class="drop"><i class="icon-user"></i> ${sessionScope.userName}<b class="caret"></b>
          <div class="sig-out" style="display: none;">
             <ul class="dropdown-menu">
                <li><a href="logout.html"><fmt:message key="LOGOUT_10001"/></a></li>
             </ul>
          </div>
	   </span>
       <span class="text-change">
			<c:choose>
				<c:when test="${cookie.lang.value == 'zh_CN'}">
					<a class="english" href="#">English</a><span class="select"> | 中文</span>
				</c:when>
				<c:when test="${cookie.lang.value == 'en_US'}">
					<span class="select">English | </span><a class="chinese" href="#">中文</a>
				</c:when>
				<c:otherwise>
					 <a class="english" href="#">English</a> | <a class="chinese" href="#">中文</a>
				</c:otherwise>
			</c:choose>
	   </span>
   </div>
</div>

<div class="wrapper">
    <div class="main cc">
        <div class="sidebar" id="sidebar">
        </div>
        <div class="contents">
            <h2 class="h2-man" id="contents-header"></h2>
            <div class="man-search cc" id="contents-search">
            </div>
            <div class="look-chart" id="look-chart" style="display:none;">
            </div>
            <div class="table-box" id="table-box">
           </div>
           <div class="pagination pagination-centered" id="page-line" style="display:none;">
           </div>
        </div>
    </div>

</div>
<object id="ComAxCtrl" classid="clsid:1D82E7E4-CDEE-4894-92C2-A3E605D4F84E" codebase="ocx/ComAxCtrl.ocx"  style="width:1px; height:1px;"></object>

<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jquery.charts.min.js"></script>
<script src="js/jquery.cookie.min.js"></script>
<jsp:include page="dictionary.jsp" />
<script src="js/common.js"></script>
<script src="js/comm.js"></script>
<script src="js/menu.generate.js"></script>
<script src="js/switch-lan.js"></script>
<script src="js/menu-tree.js"></script>
<script src="js/paging.js"></script>
<script src="js/popup.js"></script>
<script src="js/mgr.overview.js"></script>
<script src="js/mgr.systemconfig.js"></script>
<script src="js/mgr.users.js"></script>
<script src="js/mgr.qrcodes.js"></script>
<script src="js/mgr.garbagestations.js"></script>
<script src="js/mgr.garbagecans.js"></script>
<script src="js/mgr.garbagetypes.js"></script>
<script src="js/mgr.gifts.js"></script>
<script src="js/mgr.garbages.js"></script>
<script src="js/mgr.giftgrants.js"></script>
<script src="js/mgr.reports.type.js"></script>
<script src="js/mgr.reports.user.js"></script>

<script>
	$(".caret").mouseover(function(){
		if($(".sig-out").is(':hidden')) {
			$(".sig-out").show(500);
		}
	});
	$(".dropdown-menu").mouseout(function(){
		$(".sig-out").hide(500);
	});
</script>
</body>
</html>
