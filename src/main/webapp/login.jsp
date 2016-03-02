<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${!empty cookie.lang}">
	 <fmt:setLocale value="${cookie.lang.value}" /> 
</c:if>
<fmt:setBundle basename="i18n/messages" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><fmt:message key="title.login"/></title>
<link href="css-login/login-style.css" rel="stylesheet" type="text/css" />
<script>     
	if (window != top)
		top.location.href = location.href;
	if('${sessionScope.userName}' != '') {
		window.location.href="home.jsp";
	}
</script>
<link rel="shortcut icon" href="favicon.ico">
</head>

<body>
<div class="login-box">
	<div class="login-logo">
<%-- 		<c:choose>
			<c:when test="${cookie.lang.value == 'zh_CN'}">
				<img class="logo" src="img-new/login-logo.png" />
			</c:when>
			<c:when test="${cookie.lang.value == 'en_US'}">
				<img class="logo" src="img-new/login-logo-en.png" />
			</c:when>
			<c:otherwise>
				<img class="logo" src="img-new/login-logo.png" />
			</c:otherwise>
		</c:choose> --%>
		<span class="logo-text"><fmt:message key="label.logomsg"/> <i><fmt:message key="label.system.name"/></i></span>
	</div>
    <div class="login-input-bg">
        <div class="input-box-form">
            <!-- <form action="login.html" method="post" id="commentForm"> -->
                <input id="username" class="input-block" type="text" name="username" placeholder="<fmt:message key="label.username"/>">
                <input id="password" class="input-block" type="password" name="password" placeholder="<fmt:message key="label.password"/>">
                <img alt="<fmt:message key="label.clickreflash"/>" src="validationCode" style="float:right;" id="resrc" /><input id="validationCode" class="input-block input-width" type="text" name="validationCode" placeholder="<fmt:message key="label.validationcode"/>">
                <label class="remember-box">
                   <input type="checkbox" id="rem"/> <span><fmt:message key="label.remberinfo"/></span>                   
                </label>
                <div class="submit-btn"><button type="submit" id="login"><fmt:message key="label.login"/></button></div>
            <!-- </form> -->
            <div class="font-switch">
			<c:choose>
				<c:when test="${cookie.lang.value == 'zh_CN'}">
					<a class="english" href="#">English</a> | <span>中文</span>
				</c:when>
				<c:when test="${cookie.lang.value == 'en_US'}">
					<span>English | </span> <a class="chinese" href="#">中文</a>
				</c:when>
				<c:otherwise>
					 <a class="english" href="#">English</a> | <a class="chinese" href="#">中文</a>
				</c:otherwise>
			</c:choose>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jquery.cookie.min.js"></script>
<script src="js/login.js"></script>
<script src="js/switch-lan.js"></script>
</body>
</html>
