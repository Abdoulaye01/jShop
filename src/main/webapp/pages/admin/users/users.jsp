<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Users</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Dashboard</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Users</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty flashMessage}">
					<div class="alert alert-success">
						<strong>Request success!</strong> ${flashMessage}
					</div>
				</core:if>
				<div class="row">

					<div class="col-lg-12">
						<a href="${pageContext.request.contextPath}/a/user/add"><button
								type="button" class="btn btn-primary">Add new user</button></a>
					</div>
					<div class="form-group col-xs-3">
						<label>User name</label> <input class="form-control" type="text"
							id="searchName" />
					</div>
					<div class="form-group col-xs-3">
						<label>User email</label> <input class="form-control" type="text"
							id="searchEmail" />
					</div>
					<div class="form-group col-xs-3">
						<label>User status</label> <select class="form-control"
							id="searchStatus">
							<option value="">All</option>
							<option value="true">Active</option>
							<option value="false">Hidden</option>
						</select>
					</div>
					<div class="form-group col-xs-3">
						<label>User role</label> <select class="form-control"
							id="searchRole">
							<option value="">All</option>
							<c:forEach items="${roleList}" var="selectRole">
								<option value="${selectRole.role}">${selectRole.role}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-xs-3">
						<a class="btn btn-default" href="#"
							style="display: block; margin-top: 2.4rem;"
							onclick="searchButtonClick();">Search</a>
					</div>
					<div class="form-group col-xs-12">
						<h2>List of Users</h2>
					</div>
					<div class="form-group col-xs-12">
						<div class="table-responsive" id="table">
							<%@include file="_table.jsp"%>

						</div>
					</div>
				</div>

			</div>


		</div>
	</div>


	<%@include file="../_footer.jsp"%>

	<script>
	function searchButtonClick()
	{
		pageButtonClick('1');
	}
	</script>

	<script>
	function pageButtonClick(targetPage)
	{
		var pageSize = ${pageSize};
		var current = targetPage;
		var status = $('#searchStatus').val();
		var name = $('#searchName').val();
		var email = $('#searchEmail').val();
		var role = $('#searchRole').val();
		$.ajax ({ 
			url: '${pageContext.request.contextPath}${URL_PREFIX}findUsers', 
			type: "POST", 						
			data : {name:name, status:status, email:email, role:role, current:current, pageSize:pageSize},
			complete: function(response){
				$('#table').html(response.responseText);
			}
		}); 
	}
	</script>
</body>
</html>