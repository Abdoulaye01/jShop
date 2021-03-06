<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<div class="row" id="cart">
			<header class="jumbotron hero-spacer col-xs-12">


				<h2>Shipping details:</h2>
				<form:form action="${pageContext.request.contextPath}/orderPlaced"
					method="post" modelAttribute="orderUserWrapper">

					<div class="form-group col-xs-12 col-md-6">
						<label>First Name</label>
						<form:input class="form-control" path="firstName" id="firstName" />
					</div>

					<div class="form-group col-xs-12 col-md-6">
						<label>Last Name</label>
						<form:input class="form-control" path="lastName" id="lastName" />
					</div>
					<h3 class="col-xs-12">Shiping address</h3>
					<div class="form-group col-xs-12 col-md-6">
						<label>Country*</label>
						<form:select path="country" class="form-control" items="${countryList}">
							
						</form:select>

					</div>

					<div class="form-group col-xs-12 col-md-6">
						<label>State</label>
						<form:input class="form-control" placeholder="John R Smith"
							path="state" id="state" />
					</div>
					<div class="form-group col-xs-12 col-md-6">
						<label>City*</label>
						<form:input class="form-control" placeholder="John R Smith"
							path="city" id="city" />
					</div>

					<div class="form-group col-xs-12 col-md-6">
						<label>Local Address*</label>
						<form:input class="form-control" placeholder="John R Smith"
							path="shipAddress" id="shipAddress" />
					</div>

					<div class="form-group col-xs-12 col-md-6">
						<label>Zip*</label>
						<form:input class="form-control" path="zip" id="zip" />
					</div>

					<h3 class="col-xs-12">Contacts</h3>
					<div class="form-group col-xs-12 col-md-6">
						<label>Phone(international format)*</label>
						<form:input class="form-control" path="phone" id="phone" />
					</div>

					<div class="form-group col-xs-12 col-md-6">
						<label>Email*</label>
						<form:input class="form-control" path="email" id="email" />
					</div>

					<div class="form-group col-xs-12">
					<div class="checkbox checkbox-success">
					<label>
                        <form:checkbox class="form-control" path="createUser" style="width: auto;height: auto;"
							id="createUser" /> Register new user</label>
                    </div>
						
					</div>

					<div id="displayUserData" class="col-xs-12" style="display:none;">

						<core:if test="${not empty errorSummary}">
							<core:forEach var="error" items="${errorSummary}">
								<div class="alert alert-danger">

									<strong>Error!</strong> ${error}

								</div>
							</core:forEach>
						</core:if>

						<div class="form-group  col-xs-12 col-md-6">
							<label>Username/login*</label>
							<form:input class="form-control" placeholder="John R Smith"
								path="user.username" />
						</div>

						<div class="form-group col-xs-12 col-md-6">
							<label>Password*</label>
							<form:input class="form-control" path="user.password"
								id="password" />
						</div>

					</div>

					<div class="form-group text-center col-xs-12">
						<button type="submit" class="btn btn-success">Place order</button>
					</div>
				</form:form>

				<!-- select List shipName;			
			display with AJAX shippingCost; -->



			</header>
		</div>
	</div>
	<%@include file="template_parts/footer.jsp"%>
<script>
$('#createUser').click(function() {
	showBlock();
});

function showBlock(){
	if( $('#createUser').is(':checked')) {
        $("#displayUserData").show();
    } else {
        $("#displayUserData").hide();
    }
}

showBlock();
</script>

</body>
</html>
