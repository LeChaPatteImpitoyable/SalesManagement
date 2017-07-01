'use strict';

var app = angular.module('salesManagementApp', ['spring-security-csrf-token-interceptor', 'scrollable-table', 'chieffancypants.loadingBar', 'ngCookies', 'ui-notification', 'ngRoute', 'ngAnimate', 'ui.bootstrap', 'ui.select', 'datetime', 'base64', 'app-controllers', 'app-directives', 'app-filters']);

app.config(function($routeProvider, NotificationProvider, cfpLoadingBarProvider, csrfProvider) {
	$routeProvider.when("/ofertas", {
		controller : "OfferingsController",
		cache: false,
		templateUrl : "resources/partials/ofertas.jsp"
	});
	$routeProvider.when("/oferta/:id?", {
		controller : "OfferingController",
		templateUrl : "resources/partials/oferta.jsp"
	});
	$routeProvider.when("/canalConfig", {
		controller : "ChannelConfigController",
		templateUrl : "resources/partials/canalConfig.jsp"
	});
	$routeProvider.when("/roles", {
		controller : "RolesController",
		templateUrl : "resources/partials/roles.jsp"
	});
	$routeProvider.when("/usuarios", {
		controller : "UsersController",
		templateUrl : "resources/partials/usuarios.jsp"
	});
	$routeProvider.when("/userConfig", {
		controller : "UserConfigController",
		templateUrl : "resources/partials/userConfig.jsp"
	});
	$routeProvider.otherwise({
		redirectTo : "/ofertas"
	});
	
	NotificationProvider.setOptions({
        positionX: 'center',
        positionY: 'top'
    });
	
	cfpLoadingBarProvider.includeSpinner = true;
	
	csrfProvider.config({
		url: 'login',
        csrfHttpType: 'get'
    });
});