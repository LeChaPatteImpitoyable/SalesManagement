var app = angular.module('app-directives', []);

app.directive("submit",function(){
    return function (scope, element, attrs) {
        var id = attrs["submit"];
        element.on("click",function(){
            document.getElementById(id).click();
        });
    };
});

app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
 
                event.preventDefault();
            }
        });
    };
});

app.directive('ngConfirmClick', [
	function(){
		return {
		    priority: -1,
		    restrict: 'A',
		    link: function(scope, element, attrs){
		    	element.bind('click', function(e){
		    		var message = attrs.ngConfirmClick;
		    		if(message && !confirm(message)){
		    			e.stopImmediatePropagation();
		    			e.preventDefault();
		    		}
		    	});
	        }
		}
	}
]);

app.directive('aDisabled', function() {
    return {
        compile: function(tElement, tAttrs, transclude) {
            //Disable ngClick
            tAttrs["ngClick"] = "!("+tAttrs["aDisabled"]+") && ("+tAttrs["ngClick"]+")";

            //return a link function
            return function (scope, iElement, iAttrs) {

                //Toggle "disabled" to class when aDisabled becomes true
                scope.$watch(iAttrs["aDisabled"], function(newValue) {
                    if (newValue !== undefined) {
                        iElement.toggleClass("disabled", newValue);
                    }
                });

                //Disable href on click
                iElement.on("click", function(e) {
                    if (scope.$eval(iAttrs["aDisabled"])) {
                        e.preventDefault();
                    }
                });
            };
        }
    };
});