var app = angular.module('app-filters', []);

app.filter('filterCreationDateFrom', function () {
    return function (offeringList, dateFrom) {
        var filtered = [];
        
        if(dateFrom == null || dateFrom == '') {
        	return offeringList;
        }
        
        var dateFromUTC = new Date(dateFrom.getUTCFullYear(), dateFrom.getUTCMonth(), dateFrom.getUTCDate(),
        						   dateFrom.getUTCHours(), dateFrom.getUTCMinutes(), dateFrom.getUTCSeconds());
        
        for (var i = 0; i < offeringList.length; i++) {
            var offering = offeringList[i];
            
            var date = new Date(offering.creationDate);
            var dateUTC = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
            					   date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
            
            if (dateFromUTC <= dateUTC) {
                filtered.push(offering);
            }
        }
        return filtered;
    };
});

app.filter('filterCreationDateTo', function () {
    return function (offeringList, dateTo) {
        var filtered = [];
        
        if(dateTo == null || dateTo == '') {
        	return offeringList;
        }
        
        var dateToUTC = new Date(dateFrom.getUTCFullYear(), dateFrom.getUTCMonth(), dateFrom.getUTCDate(),
				   				 dateFrom.getUTCHours(), dateFrom.getUTCMinutes(), dateFrom.getUTCSeconds());
        
        for (var i = 0; i < offeringList.length; i++) {
            var offering = offeringList[i];
            
            var date = new Date(offering.creationDate);
            var dateUTC = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
            					   date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
            
            if (dateToUTC >= dateUTC) {
                filtered.push(offering);
            }
        }
        return filtered;
    };
});

app.filter('filterStatus', function () {
    return function (offeringList, status) {
        var filtered = [];
        
        if(status == null || status == 0) {
        	return offeringList;
        }
        
        for (var i = 0; i < offeringList.length; i++) {
            var offering = offeringList[i];            

            if (status == offering.status.id) {
                filtered.push(offering);
            }
        }
        return filtered;
    };
});

app.filter('filterPeriod', function () {
    return function (offeringList, period) {
        var filtered = [];
        
        if(period == null || period == 0) {
        	return offeringList;
        }
        
        var today = new Date();
        
        for (var i = 0; i < offeringList.length; i++) {
            var offering = offeringList[i];
            var offeringDate = new Date(offering.creationDate);
            
            if (period == 'YTD' && offeringDate.getUTCFullYear() == today.getUTCFullYear()) {
                filtered.push(offering);
            } else if (period == 'MTD' && offeringDate.getUTCMonth() == today.getUTCMonth()) {
                filtered.push(offering);
            } else if (period == 'WTD' && offeringDate.getUTCFullYear() == today.getUTCFullYear()
            		&& offeringDate.getUTCWeek() == today.getUTCWeek()) {
                filtered.push(offering);
            }
        }
        return filtered;
    };
});

app.filter('percentage', ['$filter', function ($filter) {
	return function (input, decimals) {
		return $filter('number')(input, decimals) + '%';
	};
}]);

Date.prototype.getUTCWeek = function() {
  var date = new Date(this.getTime());
  date.setHours(0, 0, 0, 0);
  date.setDate(date.getUTCDate() + 3 - (date.getUTCDay() + 6) % 7);
  var week1 = new Date(date.getUTCFullYear(), 0, 4);
  return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getUTCDay() + 6) % 7) / 7);
}