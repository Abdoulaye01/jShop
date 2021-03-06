<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
<link href="${bootstrapCSS}" rel="stylesheet" />
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

<spring:url value="/resources/default_theme/css/font-awesome.min.css" var="awesomeCSS" />
<link href="${awesomeCSS}" rel="stylesheet" />

<spring:url value="/resources/default_theme/css/sb-admin.css" var="sbadminCSS" />
<link href="${sbadminCSS}" rel="stylesheet" />

<spring:url value="/resources/default_theme/css/admin.css" var="adminCSS" />
<link href="${adminCSS}" rel="stylesheet" />

<c:url value="/" var="home" />
<script>
var home = "${home}";
</script>

