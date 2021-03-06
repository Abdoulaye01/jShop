<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>403-Access denied!</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body> 
<!-- style="background-image:url('http://img1.joyreactor.cc/pics/post/full/Mia-Malkova-Elle-Alexandra-X-Art-magazine-%D0%AD%D1%80%D0%BE%D1%82%D0%B8%D0%BA%D0%B0-690653.jpeg');
background-size:cover;"> -->
<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<!-- Jumbotron Header -->
		<header class="jumbotron hero-spacer" style="text-align:center;">
			<h1 style="color: rgb(228, 88, 162);">Access denied!</h1>
			<br/>
			<img src="${pageContext.request.contextPath}/resources/img/403.png" style="margin:0 auto;max-width: 80%;huerotate {-webkit-filter: hue-rotate(180deg);filter: hue-rotate(130deg);" />
			<br/>
			<br/>
			<p>You are not authorized to access this page.</p>
			<br/>
			<p>
				<a class="btn btn-primary btn-large" href="${home}">Return to main page</a>
			</p>
		</header>
	</div>
	<%@include file="template_parts/footer.jsp"%>
</body>
</html>