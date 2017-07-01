<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<html>
<head>
	<title>Administraci&oacute;n de Ofertas</title>
	
	<link type="text/css" rel="stylesheet" href="resources/css/bootstrap.min.css"/>
	<link type="text/css" rel="stylesheet" href="resources/css/select.min.css"/>
	<link type="text/css" rel="stylesheet" href="resources/css/main.css"/>
</head>
<body onload='document.loginForm.email.focus();'>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<ul class="nav navbar-nav" style="width:100%;">
				<li class="navbar-left">
					<img src="resources/img/logo.png" style="width: 5em;"/>
				</li>
				<li>
					<h3>Administraci&oacute;n de Ofertas</h3>
				</li>
			</ul>
		</div>
	</nav>
	<div>
	
	</div>
	<div class="container">
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="alert alert-info">${msg}</div>
		</c:if>
		
		<form name='loginForm'
			action="<c:url value='/j_spring_security_check' />"
			method='POST'>
			<div class="form-horizontal text-center">
				<div class="form-group">
					<label for="email" class="col-md-4 control-label">Correo electr&oacute;nico</label>
					<div class="col-md-4">
						<input name="email" type="text" class="form-control" required>
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-md-4 control-label">Contrase&ntilde;a</label>
					<div class="col-md-4">
						<input name="password" type="password" class="form-control">
					</div>
				</div>
				<input type="submit" class="btn btn-primary" value="Login"/>
			</div>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>