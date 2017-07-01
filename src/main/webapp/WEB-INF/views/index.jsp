<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="true" %>

<!DOCTYPE html>

<html data-ng-app="salesManagementApp">
	<head>
		<title>Administraci&oacute;n de Ofertas</title>
		
		<link type="text/css" rel="stylesheet" href="resources/css/bootstrap.min.css"/>
		<link type="text/css" rel="stylesheet" href="resources/css/main.css"/>
		<link type="text/css" rel="stylesheet" href="resources/css/select.min.css"/>
		<link type="text/css" rel="stylesheet" href="resources/css/angular-ui-notification.min.css"/>
		<link type="text/css" rel="stylesheet" href="resources/css/loading-bar.min.css"/>
		<link type="text/css" rel="stylesheet" href="resources/css/scrollable-table.css"/>
		
		<script type="text/javascript" src="resources/lib/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-route.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-resource.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-animate.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-locale_es-es.js"></script>
		<script type="text/javascript" src="resources/lib/angular-base64.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-cookies.min.js"></script>
		<script type="text/javascript" src="resources/lib/angular-ui-notification.min.js"></script>
		<script type="text/javascript" src="resources/lib/ui-bootstrap-tpls-0.13.3.min.js"></script>
		<script type="text/javascript" src="resources/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/lib/autoNumeric.js"></script>
		<script type="text/javascript" src="resources/lib/select.min.js"></script>
		<script type="text/javascript" src="resources/lib/datetime.js"></script>
		<script type="text/javascript" src="resources/lib/loading-bar.min.js"></script>
		<script type="text/javascript" src="resources/lib/scrollable-table.min.js"></script>
		<script type="text/javascript" src="resources/lib/spring-security-csrf-token-interceptor.min.js"></script>
		
		<script type="text/javascript" src="resources/js/app.js"></script>
		<script type="text/javascript" src="resources/js/controllers.js"></script>
		<script type="text/javascript" src="resources/js/directives.js"></script>
		<script type="text/javascript" src="resources/js/filters.js"></script>
	</head>
	<body>
		<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-header visible-xs pull-left"><img data-ng-src="resources/img/logo.png" style="width: 5em;"/><b>{{$root.pageTitle}}</b></div>
		
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
			    	<span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
			    </button>
			</div>
			
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="navbar-left hidden-xs">
						<a style="padding: 0px;"
							data-popover="Ofertas"
							data-popover-placement="bottom"
							data-popover-trigger="mouseenter"
							href="#/ofertas">
							<img data-ng-src="resources/img/logo.png" style="width: 4.5em;"/>
						</a>
					</li>
					
					<li class="active hidden-xs"><h3>{{$root.pageTitle}}</h3></li>
					
					<li class="navbar-right">
						<form id="formLogout"
							action="<c:url value='/logout'/>"
							method="post">
							<input type="hidden"
								name="${_csrf.parameterName}"
								value="${_csrf.token}"/>
						</form>
						<a style="font-size: 25px;"
							href="javascript:document.getElementById('formLogout').submit();"
							data-popover="Salir"
							data-popover-placement="bottom"
							data-popover-trigger="mouseenter">
							<span class="glyphicon glyphicon-log-out"></span>
						</a>
					</li>
					<li class="navbar-right dropdown">
						<a href=""
				          	class="dropdown-toggle"
				          	style="font-size: 25px;"
				          	data-toggle="dropdown"
				          	role="button"
				          	aria-haspopup="true"
				          	aria-expanded="false">
				          	<span class="glyphicon glyphicon-cog"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#/userConfig">Cambiar contrase&ntilde;a</a></li>
							<sec:accesscontrollist hasPermission="VIEW_CHANNEL_CONF" domainObject="null">
								<li><a href="#/canalConfig">Configuraci&oacute;n del canal</a></li>
							</sec:accesscontrollist>
							<sec:accesscontrollist hasPermission="VIEW_ROLES" domainObject="null">
								<li><a href="#/roles">Roles</a></li>
							</sec:accesscontrollist>
							<sec:accesscontrollist hasPermission="VIEW_USERS" domainObject="null">
								<li><a href="#/usuarios">Usuarios</a></li>
							</sec:accesscontrollist>
						</ul>
			        </li>
					<li class="navbar-right">
						<a style="cursor: normal;">
							Usuario: <sec:authentication property="principal.username"/>
						</a>
					</li>
				</ul>
			</div>
		</nav>
	    <ng-view></ng-view>
	</body>
</html>