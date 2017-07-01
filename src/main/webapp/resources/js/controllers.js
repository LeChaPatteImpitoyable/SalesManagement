var app = angular.module('app-controllers', []);

app.controller('OfferingsController', function($location, $scope, $rootScope, $http, $modal, Notification) {
	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Ofertas';
    
    // Obtener lista status
    $http.get("getStatuses")
    .success(function(response) {
    	$scope.statuses = response;
    });
	
    // Obtener ofertas
    $scope.getOfferings = function() {
	    $http.get("getOfferings")
	    .success(function(response) {
	    	$scope.offerings = response;
	    	
	    	$scope.canEdit = false;
	        $scope.selectedOffering = {};
	        
	        var now = new Date();
	        $scope.nowUTC = new Date(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(), 0, 0, 0, 0);
	    	
	    	if ($(window).width() > 991) {
	    		var height = $(window).height() - 
	    					    $("#navbar").height() - 
	    					    $("#divStatus").height() - 
	    					    30;
	    		if (((36 * $scope.offerings.length) + 70) > height) {
	    	    	$(".scrollableContainer").css('height', height);
	    		}
	    	}
	    })
	    .error(function() {
	    	Notification.error("Error al obtener ofertas.");
	    });
    };
    
    // Seleccionar oferta
    $scope.setSelected = function (offering) {
       $scope.canEdit = angular.isUndefined($("#canEdit").val()) ? false : true;
       $scope.selectedOffering = offering;
       
       var dueDate = new Date(offering.dueDate);
       $scope.dueDateUTC = new Date(dueDate.getUTCFullYear(), dueDate.getUTCMonth(), dueDate.getUTCDate(), 0, 0, 0, 0);
       
       if ($scope.dueDateUTC < $scope.nowUTC && offering.status.id == 6) {
    	   $scope.cantPublishReason = "Fecha de vigencia de la oferta superior a fecha actual.";
       } else {
    	   $scope.cantPublishReason = null;
       }
    };
    
    // Eliminar oferta
    $scope.deleteOffering = function() {
    	var dataObj = {
    		id : $scope.selectedOffering.id
    	};
    	
    	$http.post('deleteOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta eliminada.");
        })
        .error(function() {
        	Notification.error("Error al eliminar oferta.");
        });
    };
    
    // Retirar oferta
    $scope.retireOffering = function() {
    	var dataObj = {
    			id : $scope.selectedOffering.id
    	};
    	$http.post('retireOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta retirada.");
        })
        .error(function() {
        	Notification.error("Error al retirar oferta.");
        });
    };
    
    // Duplicar oferta
    $scope.duplicateOffering = function() {
    	var dataObj = {
    			id : $scope.selectedOffering.id
    	};
    	$http.post('duplicateOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta duplicada.");
        })
        .error(function() {
        	Notification.error("Error al duplicar oferta.");
        });
    };
    
    // Solicitar publicacion de oferta
    $scope.requestPublishOffering = function() {
    	var dataObj = {
    			id : $scope.selectedOffering.id
    	};
    	$http.post('requestPublishOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta pasada a estado pendiente de publicaci&oacute;n.");
        })
        .error(function() {
        	Notification.error("Error al pasar la oferta a estado pendiente de publicaci&oacute;n.");
        });
    };
    
    // Publicar oferta
    $scope.publishOffering = function() {
    	var dataObj = {
    			id : $scope.selectedOffering.id
    	};
    	$http.post('publishOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta publicada.");
        })
        .error(function() {
        	Notification.error("Error al publicar oferta.");
        });
    };
    
    // Rechazar oferta
    $scope.rejectOffering = function(rejectReason) {
    	var dataObj = {
			id : $scope.selectedOffering.id,
			rejectReason : rejectReason
		};
    	
    	$http.post('rejectOffering', dataObj)
    	.success(function(response) {
        	$location.path('/');
        	Notification.success("Oferta rechazada.");
        })
        .error(function() {
        	Notification.error("Error al rechazar oferta.");
        });
    };
    
    // Modal confirmacion
    $scope.openConfirmation = function (action, message) {
    	var modalInstance = $modal.open({
        	templateUrl: 'modalConfirmation',
        	controller: 'ModalConfirmationController',
        	resolve: {
        		message: function () {
        			return message;
        		},
        		action: function () {
        			return action;
        		}
        	}
    	});
    	
    	modalInstance.result.then(function (action) {
    		if (!angular.isUndefined(action.action) && action.action == 'rejectOffering') {
    			$scope[action.action](rejectReason.value);
    		} else {
    			$scope[action]();
    		}
    	});
	};
    
    // Datepicker
	$scope.open = function($event, opened) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope[opened] = !$scope[opened];
	};
});

app.controller('ModalConfirmationController', function ($scope, $modalInstance, message, action, Notification) {
	$scope.message = message;
	$scope.action = action;
	
	$scope.ok = function () {
		if (action == 'rejectOffering') {
			if (angular.isUndefined($scope.rejectReason) || $scope.rejectReason == null || $scope.rejectReason.trim() == "") {
				Notification.error("Debe ingresar el motivo del rechazo.");
				$("#rejectReason").focus();
				return;
			} else {
				action = {'action' : action, 'rejectReason' : rejectReason};
				$modalInstance.close(action);
			}
		} else {
		    $modalInstance.close(action);
		}
	};
	
	$scope.close = function () {
		$modalInstance.dismiss('cancel');
	};
});

app.controller('OfferingController', function($location, $scope, $rootScope, $http, $routeParams, $base64, $modal, Notification) {	
	$scope.offering = {};
	$scope.product = {};
    $scope.products = [];
    $scope.productNames = [];
    $scope.product.financialServices = [];
    $scope.newFinancialService = "";
    $scope.prodIndex = -1;
    
    $scope.canEdit = true;
    
    $scope.isCollapsed = false;
    
    if ($(window).width() > 991)
    	$("#divButtonsProduct").css('margin-top', $("#divFinancialServices").height() - $("#divButtonsProduct").height());
    
    angular.element(document).ready(function () {
    	$('#maxAmount').autoNumeric('init', {aSign: '$', aSep: '.', aDec: ',', vMax: '999999999999.99', lZero: 'deny'});
    	$('#maxTerm').autoNumeric('init', {vMax: '999', mDec: '0' ,lZero: 'deny'});
    	$('#teaRateClient').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#teaRateDelayClient').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#teaRate').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#teaRateDelay').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#commissionOpening').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#commissionEarlyCancellation').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    	$('#commissionDownPayment').autoNumeric('init', {aSign: '%', pSign: 's', aSep: '.', aDec: ',', vMax: '100', lZero: 'deny'});
    });
    
    if (angular.isUndefined($routeParams.id)) {
    	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Nueva Oferta';
    	
    	$scope.offering.status = {};
    	$scope.offering.status.name = "Nueva";
    	$scope.offering.creationDate = new Date();
    	
    	$http.get("initOffering");
    	
    	$scope.canEdit = true;
    } else {
    	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Oferta ' + $routeParams.id;
    	
	    $http.get("getOffering", {params : {id : $routeParams.id} })
	    .success(function(response) {
	    	
	    	response.creationDate = new Date(response.creationDate);
	    	response.creationDate.setHours(response.creationDate.getHours() + response.creationDate.getTimezoneOffset() / 60);
	    	
	    	if (!angular.isUndefined(response.publishDate) && response.publishDate != null) {
	    		response.publishDate = new Date(response.publishDate);
	    		response.publishDate.setHours(response.publishDate.getHours() + response.publishDate.getTimezoneOffset() / 60);
	    	}
	    	
	    	response.dueDate = new Date(response.dueDate);
	    	response.dueDate.setHours(response.dueDate.getHours() + response.dueDate.getTimezoneOffset() / 60);
	    	
	    	$scope.offering = response;
	    	$scope.products = response.products;
	    	
	    	// Si el status es guardada puede editar
	    	if ($scope.offering.status.id == 1 || $scope.offering.status.id == 7)
	    		$scope.canEdit = true;
	    	else
	    		$scope.canEdit = false;

	    	// Si el status es rechazada busca el motivo y lo muestra
	    	if(response.status.id == 7) {
	    		getLastOfferingStatus($scope.offering);
	    	}
	    });
    }
    
    $http.get("getCurrencies")
    .success(function(response) {
    	$scope.currencies = response;
    });
    
    $http.get("getProductTypes")
    .success(function(response) {
    	$scope.productTypes = response;
    });
    
    $http.get("getProductNames")
    .success(function(response) {
    	$scope.productNames = response;
    });
    
    function getLastOfferingStatus(offering) {
    	$http.get('getLastOfferingStatus', {params : {id : offering.id} })
    	.success(function(response) {
        	$scope.rejectReason = response.comment;
        })
        .error(function() {
        	Notification.error("Error al obtener &uacute;ltimo estado de la oferta.");
        });
	};
    
    ///// Oferta /////
    
    $scope.createUpdateOffering = function() {
    	if (angular.isUndefined($scope.offering.dueDate) || $scope.offering.dueDate == "") {
    		Notification.error("Debe ingresar Fecha de Vigencia.");
			return;
		}
    	
    	if (angular.isUndefined($routeParams.id)) {
        	// Crear
			var dataObj = {
				dueDate : $scope.offering.dueDate
			};
			
	    	$http.post('createOffering', dataObj)
	    	.success(function(response) {
	        	$location.path('/ofertas');
	        	Notification.success("Oferta creada.");
	        })
	        .error(function() {
	        	Notification.error("Error al crear oferta.");
	        });
    	} else {
    		// Actualizar
    		var dataObj = {
				id : $scope.offering.id,
				bank: $scope.offering.bank,
				status : $scope.offering.status,
				creationDate : $scope.offering.creationDate,
				publishDate : $scope.offering.publishDate,
				dueDate : $scope.offering.dueDate,
				products : $scope.products
			};
	    	
	    	$http.post('updateOffering', dataObj)
	    	.success(function(response) {
	        	$location.path('/ofertas');
	        	if ($scope.offering.status.id == 7)
	        		Notification.success("Oferta modificada y estado cambiado a Guardada.");
	        	else
	        		Notification.success("Oferta modificada.");
	        })
	        .error(function() {
	        	Notification.error("Error al modificar oferta.");
	        });
    	}
    };
    
    ///// Producto /////
    
    $scope.insertUpdateProduct = function() {
		$scope.product.maxAmount = $('#maxAmount').autoNumeric('get');
		$scope.product.maxTerm = $('#maxTerm').autoNumeric('get');
		$scope.product.teaRateClient = $('#teaRateClient').autoNumeric('get');
		$scope.product.teaRateDelayClient = $('#teaRateDelayClient').autoNumeric('get');
		$scope.product.teaRate = $('#teaRate').autoNumeric('get');
		$scope.product.teaRateDelay = $('#teaRateDelay').autoNumeric('get');
		$scope.product.commissionOpening = $('#commissionOpening').autoNumeric('get');
		$scope.product.commissionEarlyCancellation = $('#commissionEarlyCancellation').autoNumeric('get');
		$scope.product.commissionDownPayment = $('#commissionDownPayment').autoNumeric('get');

		$scope.productValidation = "";
		
		if (angular.isUndefined($scope.product.productType) || $scope.product.productType == "") {
			$scope.productValidation = "Tipo de producto";
		}
		
		if (angular.isUndefined($scope.product.name) || $scope.product.name == "") {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "Nombre de producto";
		}
		
		if (angular.isUndefined($scope.product.currency)) {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "Moneda";
		}
		
		if (angular.isUndefined($scope.product.maxAmount) || $scope.product.maxAmount == "") {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "Monto tope";
		}
		
		if (angular.isUndefined($scope.product.maxTerm) || $scope.product.maxTerm == "") {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "Plazo tope";
		}
		
		if (angular.isUndefined($scope.product.teaRate) || $scope.product.teaRate == "") {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "% T.E.A.";
		}
		
		if (angular.isUndefined($scope.product.teaRateDelay) || $scope.product.teaRateDelay == "") {
			if ($scope.productValidation != "")
				$scope.productValidation += ", ";
			
			$scope.productValidation += "% Mora";
		}
		
		if ($scope.productValidation != "") {
			Notification.error("Debe ingresar " + $scope.productValidation + ".");
			return;
		}

		if (!angular.isUndefined($scope.product.name) && !angular.isUndefined($scope.product.name.name))
			$scope.product.name = $scope.product.name.name;
		

		// Si no ingresa intereses cliente toma los de no cliente
		if (angular.isUndefined($scope.product.teaRateClient) || $scope.product.teaRateClient == "") {
			$scope.product.teaRateClient = $scope.product.teaRate;
		}
		if (angular.isUndefined($scope.product.teaRateDelayClient) || $scope.product.teaRateDelayClient == "") {
			$scope.product.teaRateDelayClient = $scope.product.teaRateDelay;
		}
		
    	if ($scope.prodIndex == -1) {
    		angular.forEach($scope.products, function(prod, key) {
    		    if (prod.productType.id == $scope.product.productType.id &&
    		    	prod.name == $scope.product.name) {
    		    	Notification.error("Producto repetido.");
		    		return;
    		    }
    		});
    		
    		$http.post('addProduct', $scope.product)
	    	.success(function(response) {
	        	Notification.info("Producto " + $scope.product.name  + " agregado.");
	    		$scope.product = {};
	    		$scope.product.financialServices = [];
	        })
	        .error(function(response) {
		        Notification.error("Error al agregar producto.");
		    });
    		
			$scope.products.push($scope.product);
    	} else {
    		if ($scope.products[$scope.prodIndex].productType.id != $scope.product.productType.id ||
    				$scope.products[$scope.prodIndex].name != $scope.product.name) {
    			angular.forEach($scope.products, function(prod, key) {
        		    if (prod.productType.id == $scope.product.productType.id &&
        		    	prod.name == $scope.product.name) {
        		    	Notification.error("Producto repetido.");
        		    	return;
        		    }
        		});
    		}
    		
			$http.post('updateProduct', $scope.product)
	    	.success(function(response) {
	    		Notification.info("Producto " + $scope.product.name  + " modificado.");
	    		$scope.product = {};
	    		$scope.product.financialServices = [];
	        })
	        .error(function(response) {
		        Notification.error("Error al modificar producto.");
		    });
			
    		$scope.products[$scope.prodIndex] = angular.extend($scope.product);
    		$scope.prodIndex = -1;
    	}
    	
    };
    
    $scope.editProduct = function(index) {
    	$http.post("initProduct", $scope.products[index]);
    	
        $scope.prodIndex = index;
    	$scope.product = angular.copy($scope.products[index]);
    	
    	$scope.product.maxAmount = undefined;
    	$scope.product.maxTerm = undefined;
    	$scope.product.teaRateClient = undefined;
    	$scope.product.teaRateDelayClient = undefined;
    	$scope.product.teaRate = undefined;
    	$scope.product.teaRateDelay = undefined;
    	$scope.product.commissionOpening = undefined;
    	$scope.product.commissionEarlyCancellation = undefined;
    	$scope.product.commissionDownPayment = undefined;
    	$('#maxAmount').autoNumeric('set', $scope.products[index].maxAmount);
    	$('#maxTerm').autoNumeric('set', $scope.products[index].maxTerm);
    	$('#teaRateClient').autoNumeric('set', $scope.products[index].teaRateClient);
    	$('#teaRateDelayClient').autoNumeric('set', $scope.products[index].teaRateDelayClient);
    	$('#teaRate').autoNumeric('set', $scope.products[index].teaRate);
    	$('#teaRateDelay').autoNumeric('set', $scope.products[index].teaRateDelay);
    	$('#commissionOpening').autoNumeric('set', $scope.products[index].commissionOpening);
    	$('#commissionEarlyCancellation').autoNumeric('set', $scope.products[index].commissionEarlyCancellation);
    	$('#commissionDownPayment').autoNumeric('set', $scope.products[index].commissionDownPayment);
    	
    	if ($(window).width() > 991)
        	$("#divButtonsProduct").css('margin-top', 65 + ($scope.product.financialServices.size != null ? $scope.product.financialServices.size * 34 : 0) - $("#divButtonsProduct").height());
    };
    
    $scope.deleteProduct = function(index) {
    	$http.post('deleteProduct', $scope.products[index]);
    	
    	$scope.products.splice(index, 1);
    	
    	if (index == $scope.prodIndex) {
    		$scope.product = {};
    		$scope.product.financialServices = [];
    		$scope.prodIndex = -1;
    	}
    };
    
    $scope.fileNameChanged = function(file) {
    	$scope.image = new FormData();
    	$scope.image.append("file", file.files[0]);
    	
    	$http.post('fileupload', $scope.image, {
			withCredentials: true,
	        headers: {'Content-Type': undefined },
	        transformRequest: angular.identity
	    })
	    .success(function(response) {
	    	$scope.product.image = $base64.decode(response.image);
        });
    	
		$scope.product.imageName = file.files[0].name;
	}
    
    // Datepicker
	$scope.open = function($event, opened) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope[opened] = !$scope[opened];
	};
	
	// Combo Producto
	$scope.refreshResults = function ($select){
		var search = $select.search, list = angular.copy($select.items), FLAG = -1;
	    
	    list = list.filter(function(item) { 
	    	return item.id !== FLAG; 
	    });
	  
	    if (!search) {
	    	$select.items = list;
	    }
	    else {
	    	var userInputItem = {id: FLAG, name: search};
	    	$select.items = [userInputItem].concat(list);
	    	$select.selected = userInputItem;
	    }
	};
	
	// Servicio financiero
	$scope.addFinancialService = function() {
		console.log("$scope.addFinancialService: " + $scope.newFinancialService + " - " + $scope.product.financialServices);
		if ($scope.newFinancialService != null && $scope.newFinancialService != "") {
	        $scope.product.financialServices.push({
	        	name: $scope.newFinancialService.trim()
	        });
	        
	        $scope.newFinancialService = "";

	    	if ($(window).width() > 991)
	    		$("#divButtonsProduct").css('margin-top', $("#divFinancialServices").height());
		}
	};
	
	$scope.deleteFinancialService = function(index) {
    	$http.post('deleteFinancialService', $scope.product.financialServices[index]);
    	
    	$scope.product.financialServices.splice(index, 1);
    	
    	if ($(window).width() > 991)
    		$("#divButtonsProduct").css('margin-top', $("#divFinancialServices").height() - $("#divButtonsProduct").height() * 2);
    };
    
    // Modal imagen
    $scope.openImage = function (size) {
    	var modalInstance = $modal.open({
        	templateUrl: 'modalImage',
        	controller: 'ModalImageController',
        	resolve: {
        		image: function () {
        			return $scope.product.image;
        		}
        	}
    	});
	};
});

app.controller('ModalImageController', function ($scope, $modalInstance, image) {
	$scope.image = image;
	  
	$scope.close = function () {
		  $modalInstance.dismiss('cancel');
	};
});


app.controller('ChannelConfigController', function($location, $scope, $rootScope, $http, Notification) {
	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Configuraci\u00F3n del Canal';
	
	var configOpeningTime = {};
	var configClosingTime = {};
	
	$http.get("getChannelConfig")
    .success(function(response) {
    	configOpeningTime = response.openingTime;
    	configClosingTime = response.closingTime;
    	$scope.openingTime = new Date(0, 0, 0, response.openingTime.time.substr(0,2), response.openingTime.time.substr(3,2), 0);
    	$scope.closingTime = new Date(0, 0, 0, response.closingTime.time.substr(0,2), response.closingTime.time.substr(3,2), 0);
    	$scope.businessDays = response.businessDays;
    })
    .error(function(data, status, headers, config) {
    	Notification.error("Error al obtener configuraci&oacute;n del canal.");
    });
	
	// Actualizar configuracion
	$scope.updateChannelConfig = function() {
		configOpeningTime.time = $scope.openingTime.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
		configClosingTime.time = $scope.closingTime.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
		
		var dataObj = {
			openingTime : configOpeningTime,
			closingTime : configClosingTime,
			businessDays : $scope.businessDays
		};
		
		$http.post('updateChannelConfig', dataObj)
		.success(function() {
	    	$location.path('/ofertas');
    		Notification.success("Configuraci&oacute;n del canal actualizada correctamente.");
        })
        .error(function(data, status, headers, config) {
        	Notification.error("Error al actualizar configuraci&oacute;n del canal.");
        });
    };
});

app.controller('RolesController', function($location, $scope, $rootScope, $http, Notification) {
	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Configuraci\u00F3n de Roles';
	
	$scope.selectedActions = [];
	$scope.selectedUsers = [];
	$scope.creatingRole = false;
	
	getRoleNames();
	
	if ($(window).width() > 991) {
		var maxHeight = $(window).height() - 
					  $("#navbar").height() - 
					  $("#divRoleInputs").height() - 
					  $("#divPanelHeadingAvailableActions").innerHeight() - 
					  $("#divButtons").height() - 
					  70;
    	$(".scrolleableDiv").css('max-height', maxHeight);
	}

	// Obtener Roles
	function getRoleNames() {
		$http.get("getRoleNames")
	    .success(function(response) {
	    	$scope.roles = response;
	    })
	    .error(function() {
        	Notification.error("Error al obtener roles.");
        });
	};
    
	// Acciones
	function getActions() {
		$http.get("getActions")
	    .success(function(response) {
	    	$scope.actions = response;
	    	
	    	$scope.availableActions = [];
	    	$scope.selectedAvailableActions = [];
	    	$scope.selectedActions = [];
	    	
	    	for (var i = 0; i < $scope.actions.length; i++) {
	    		var found = false;
	    		
				for (var j = 0; j < $scope.selectedRole.actions.length; j++) {
					if ($scope.actions[i].id == $scope.selectedRole.actions[j].id)
						found = true;
				}
				
				if (!found)
					$scope.availableActions.push($scope.actions[i]);
			};
	    })
	    .error(function() {
        	Notification.error("Error al obtener acciones.");
        });
	};
	
	// Usuarios
	function getUsers() {
		$http.get("getUsers", {params : {showActive : 1}})
	    .success(function(response) {
	    	$scope.users = response;
	    	
	    	$scope.availableUsers = [];
	    	$scope.selectedAvailableUsers = [];
	    	$scope.selectedUsers = [];
	    	
	    	for (var i = 0; i < $scope.users.length; i++) {
	    		var found = false;
	    		
				for (var j = 0; j < $scope.selectedRole.users.length; j++) {
					if ($scope.users[i].id == $scope.selectedRole.users[j].id)
						found = true;
				}
				
				if (!found)
					$scope.availableUsers.push($scope.users[i]);
			};
	    })
	    .error(function() {
        	Notification.error("Error al obtener usuarios.");
        });
	};
	
	// Inicializar rol
	$scope.initRole = function() {
		getRoleNames();
		$scope.creatingRole = false;
		$scope.selectedRole = {};
    };
	
	// Seleccion de rol
    $scope.setSelectedRole = function() {
    	if ($scope.selectedRole != null) {
	    	$http.get("getRole", {params : {id : $scope.selectedRole.id}})
		    .success(function(response) {
		    	$scope.selectedRole = response;
		    	
		    	getActions();
		    	getUsers();
		    })
		    .error(function() {
	        	Notification.error("Error al obtener rol.");
	        });
	    }
    };
	
    // Nuevo rol
	$scope.newRole = function() {
    	getActions();
    	getUsers();
    	
		$scope.selectedRole = {};
		$scope.selectedRole.actions = [];
		$scope.selectedRole.users = [];
    	
    	$scope.availableActions = angular.copy($scope.actions);
    	$scope.selectedAvailableActions = [];
    	$scope.selectedActions = [];
    	
    	$scope.availableUsers = angular.copy($scope.users);
    	$scope.selectedAvailableUsers = [];
    	$scope.selectedUsers = [];
    	
    	$scope.creatingRole = true;
    };
    
    // Agregar o actualizar rol
    $scope.createUpdateRole = function() {
    	if (angular.isUndefined($scope.selectedRole.id)) {
    		$http({
				url: 'createRole', 
				method: 'POST',
				data: $scope.selectedRole,
				transformResponse: function(data, headersGetter, status) {
		            return {message: data};
		        }
			})
	    	.success(function() {
	    		Notification.success("Rol " + $scope.selectedRole.name + " creado correctamente.");
	    		getRoleNames();
	    		$scope.creatingRole = false;
	    		$scope.selectedRole = {};
	        })
	        .error(function(data, status, headers, config) {
	        	if(data.message == "error")
	        		Notification.error("Error al crear rol " + $scope.selectedRole.name + " .");
	        	else
	        		Notification.error(data.message);
	        });
    	} else {
    		$http({
				url: 'updateRole', 
				method: 'POST',
				data: $scope.selectedRole,
				transformResponse: function(data, headersGetter, status) {
		            return {message: data};
		        }
			})
	    	.success(function() {
	    		Notification.success("Rol " + $scope.selectedRole.name + " actualizado correctamente.");
	    		getRoleNames();
	    		$scope.creatingRole = false;
	    		$scope.selectedRole = {};
	        })
	        .error(function(data, status, headers, config) {
	        	if(data.message == "error")
	        		Notification.error("Error al actualizar rol " + $scope.selectedRole.name + ".");
	        	else
	        		Notification.error(data.message);
	        });
    	}
    };
    
    // Eliminar rol
    $scope.deleteRole = function() {
    	$http.post('deleteRole', $scope.selectedRole)
    	.success(function(response) {
    		Notification.success("Rol " + $scope.selectedRole.name + " eliminado correctamente.");
    		getRoleNames();
        })
        .error(function(response) {
        	Notification.error("Error al eliminar rol " + $scope.selectedRole.name + ".");
        });
    };
    
    // Permisos disponibles
    $scope.setSelectedAvailableAction = function(action) {
    	if ($scope.selectedAvailableActions.indexOf(action) == -1)
    		$scope.selectedAvailableActions.push(action);
    	else {
    		for(var i = $scope.selectedAvailableActions.length - 1; i >= 0; i--){
    		    if($scope.selectedAvailableActions[i] == action){
    		        $scope.selectedAvailableActions.splice(i,1);
    		    }
    		}
    	}
    };
    
    $scope.checkSelectedAvailableAction = function (action) {
    	if ($scope.selectedAvailableActions.indexOf(action) == -1)
    		return false;
    	
    	return true;
    };
    
    // Permisos seleccionados
    $scope.setSelectedAction = function (action) {
    	if ($scope.selectedActions.indexOf(action) == -1)
    		$scope.selectedActions.push(action);
    	else {
    		for (var i = $scope.selectedActions.length - 1; i >= 0; i--) {
    		    if ($scope.selectedActions[i] == action) {
    		        $scope.selectedActions.splice(i,1);
    		    }
    		}
    	}
    };
    
    $scope.checkSelectedAction = function (action) {
    	if ($scope.selectedActions.indexOf(action) == -1)
    		return false;
    	
    	return true;
    };
    
    // Cambio permisos
    $scope.availableToSelectedActions = function () {  
		for (var i = 0; i < $scope.selectedAvailableActions.length; i++) { 
	        $scope.selectedRole.actions.push($scope.selectedAvailableActions[i]);
	        
			for (var j = $scope.availableActions.length - 1; j >= 0; j--) {
    		    if ($scope.selectedAvailableActions[i] == $scope.availableActions[j]) {
    		        $scope.availableActions.splice(j,1);
    		        break;
    		    }
			}
		}
    	
    	$scope.selectedAvailableActions = [];
    };
    
    $scope.selectedToAvailableActions = function () {    	
    	for (var i = $scope.selectedRole.actions.length - 1; i >= 0; i--) {
    		for (var j = 0; j < $scope.selectedActions.length; j++) {
    		    if ($scope.selectedRole.actions[i] == $scope.selectedActions[j]) {
    		        $scope.availableActions.push($scope.selectedRole.actions[i]);
    		        $scope.selectedRole.actions.splice(i,1);
    		    }
			}
		}
    	
    	$scope.selectedActions = [];
    };
    
    
    // Usuarios disponibles
    $scope.setSelectedAvailableUser = function (user) {
    	if ($scope.selectedAvailableUsers.indexOf(user) == -1)
    		$scope.selectedAvailableUsers.push(user);
    	else {
    		for(var i = $scope.selectedAvailableUsers.length - 1; i >= 0; i--){
    		    if($scope.selectedAvailableUsers[i] == user){
    		        $scope.selectedAvailableUsers.splice(i,1);
    		    }
    		}
    	}
    };
    
    $scope.checkSelectedAvailableUser = function (user) {
    	if ($scope.selectedAvailableUsers.indexOf(user) == -1)
    		return false;
    	
    	return true;
    };
    
    // Usuarios seleccionados
    $scope.setSelectedUser = function (user) {
    	if ($scope.selectedUsers.indexOf(user) == -1)
    		$scope.selectedUsers.push(user);
    	else {
    		for (var i = $scope.selectedUsers.length - 1; i >= 0; i--) {
    		    if ($scope.selectedUsers[i] == user) {
    		        $scope.selectedUsers.splice(i,1);
    		    }
    		}
    	}
    };
    
    $scope.checkSelectedUser = function (user) {
    	if ($scope.selectedUsers.indexOf(user) == -1)
    		return false;
    	
    	return true;
    };
    
    // Cambio usuarios
    $scope.availableToSelectedUsers = function () {  
		for (var i = 0; i < $scope.selectedAvailableUsers.length; i++) { 
	        $scope.selectedRole.users.push($scope.selectedAvailableUsers[i]);
	        
			for (var j = $scope.availableUsers.length - 1; j >= 0; j--) {
    		    if ($scope.selectedAvailableUsers[i] == $scope.availableUsers[j]) {
    		        $scope.availableUsers.splice(j,1);
    		        break;
    		    }
			}
		}
    	
    	$scope.selectedAvailableUsers = [];
    };
    
    $scope.selectedToAvailableUsers = function () {    	
    	for (var i = $scope.selectedRole.users.length - 1; i >= 0; i--) {
    		for (var j = 0; j < $scope.selectedUsers.length; j++) {
    		    if ($scope.selectedRole.users[i] == $scope.selectedUsers[j]) {
    		        $scope.availableUsers.push($scope.selectedRole.users[i]);
    		        $scope.selectedRole.users.splice(i,1);
    		    }
			}
		}
    	
    	$scope.selectedUsers = [];
    };
});

app.controller('UsersController', function($location, $scope, $rootScope, $http, Notification) {
	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Configuraci\u00F3n de Usuarios';
	
	$scope.showInactive = false;
	
	$scope.genders = [{id: 0, name: 'Masculino'},
	                  {id: 1, name: 'Femenino' }];
	
	initUser();
	getUsers();
	
	if ($(window).width() > 991) {
		var maxHeight = $(window).height() - 
					    $("#navbar").height() - 
					    $("#divPanelHeadingUsers").innerHeight() - 
					    70;
    	$(".scrolleableDiv").css('max-height', maxHeight);
	}
	
	// Inicializar usuario
	function initUser() {
		$scope.selectedUser = {};
	    $scope.gender = {};
	};
	
	// Obtener usuarios
	function getUsers() {
		$http.get("getUsers", {params : {showActive : $scope.showInactive ? 0 : 1}})
	    .success(function(response) {
    		initUser();
	    	$scope.users = response;
	    })
	    .error(function() {
        	Notification.error("Error al obtener usuarios.");
        });
	};
	
	$scope.getUsers = function () {
		getUsers();
	};
	
	// Seleccion
	$scope.setSelectedUser = function (user) {
		initUser();
		$scope.selectedUser = angular.copy(user);
		$scope.gender.id = $scope.selectedUser.sex;
    };
    
    // Usuario nuevo
	$scope.newUser = function () {
		initUser();
    };
    
    // Crear o actualizar usuario
	$scope.createUpdateUser = function () {
		$scope.selectedUser.sex = $scope.gender.id;
		
		if (angular.isUndefined($scope.selectedUser.id)) {
			$http({
				url: 'createUser', 
				method: 'POST',
				data: $scope.selectedUser,
				transformResponse: function(data, headersGetter, status) {
		            return {message: data};
		        }
			})
	    	.success(function() {
	    		Notification.success("Usuario creado correctamente.");
	    		getUsers();
	        })
	        .error(function(data, status, headers, config) {
	        	if(data.message == "error")
	        		Notification.error("Error al crear usuario.");
	        	else
	        		Notification.error(data.message);
	        });
		} else {
			$http({
				url: 'updateUser', 
				method: 'POST',
				data: $scope.selectedUser,
				transformResponse: function(data, headersGetter, status) {
		            return {message: data};
		        }
			})
	    	.success(function() {
	    		Notification.success("Usuario " + $scope.selectedUser.email + " actualizado correctamente.");
	    		getUsers();
	        })
	        .error(function(data, status, headers, config) {
	        	if(data.message == "error")
	        		Notification.error("Error al actualizar al usuario " + $scope.selectedUser.email + ".");
	        	else
	        		Notification.error(data.message);
	        });
		}
    };
    
    // Eliminar usuario
    $scope.deleteUser = function(user) {
    	$http.post('deleteUser', user)
    	.success(function(response) {
    		Notification.success("Usuario " + user.email + " eliminado correctamente.");
    		getUsers();
        })
        .error(function(response) {
        	Notification.error("Error al eliminar usuario " + user.email + ".");
        });
    };
	
	// Datepicker
	$scope.open = function($event, opened) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope[opened] = !$scope[opened];
	};
});

app.controller('UserConfigController', function($location, $scope, $rootScope, $http, Notification) {
	$rootScope.pageTitle = 'Administraci\u00F3n de Ofertas - Cambio de contrase\u00F1a';
	
	// Actualizar configuracion
	$scope.updateUserConfig = function() {
		if(!(angular.isUndefined($scope.newPassword) || $scope.newPassword == "")) {
			if(angular.isUndefined($scope.newPasswordConfirm) || $scope.newPasswordConfirm == "")
				Notification.error("Debe ingresar la confirmaci&oacute;n de la nueva contrase&ntilde;a.");
			else if ($scope.newPassword != $scope.newPasswordConfirm)
				Notification.error("Las contrase&ntilde;as ingresadas no coinciden.");
			else {
				var userConfig = {};
				userConfig.checkPassword = $scope.password;
				userConfig.newPassword = $scope.newPassword;
				
				$http({
					url: 'updateUserConfig', 
					method: 'POST',
					data: userConfig,
					transformResponse: function(data, headersGetter, status) {
			            return {message: data};
			        }
				})
		    	.success(function() {
			    	$location.path('/ofertas');
			    	Notification.success("Configuraci&oacute;n actualizada correctamente.");
		        })
		        .error(function(data, status, headers, config) {
		        	if(data.message == "error")
			    		Notification.error("Error al actualizar configuraci&oacute;n.");
		        	else
		        		Notification.error(data.message);
		        });
			}
		}
    };
});