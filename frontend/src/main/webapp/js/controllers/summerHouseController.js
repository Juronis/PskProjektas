app.controller('summerHouseController', ['$scope', '$stateParams', 'summerHouseService', function ($scope, $stateParams, summerHouseService) {


    var loadSummerHouse = function() {

        summerHouseService.getSummerHouse($stateParams.id).then(function (response) {
            $scope.summerHouse = response.data;
            console.log($scope.summerHouse);
            $('#dateFrom').datepicker({
                format: 'yyyy-mm-dd',
                daysOfWeekDisabled: "2,3,4,5,6",
                weekStart: 1,
                startDate: $scope.summerHouse.dateFrom,
                endDate: $scope.summerHouse.dateTo
            });

            $('#dateTo').datepicker({
                format: 'yyyy-mm-dd',
                daysOfWeekDisabled: "2,3,4,5,6",
                weekStart: 1,
                startDate: $scope.summerHouse.dateFrom,
                endDate: $scope.summerHouse.dateTo
            });
        }).catch(function (response) {
            //TODO: error handling
        });
    }

    loadSummerHouse();

    $scope.price = 0;

    var countPrice = function() {
        if($scope.fromDate && $scope.toDate) {
            var days = Math.floor(( Date.parse($scope.toDate, "yyyy-mm-dd") - Date.parse($scope.fromDate, "yyyy-mm-dd") ) / 86400000);
            days++;
            $scope.price = (days/7) * $scope.summerHouse.price;
            $scope.$apply();

        }
    }

    $('#dateFrom').focusout(function() {
        countPrice();
    });
    $('#dateTo').focusout(function() {
        countPrice();
    });

    $scope.selection = [];

    $scope.toggleSelection = function toggleSelection(name) {
        var idx = $scope.selection.indexOf(name);

        // is currently selected
        if (idx > -1) {
            $scope.selection.splice(idx, 1);

            var result = $.grep($scope.summerHouse.activityList, function(e){ return e.name == name; });
            $scope.price -= result[0].price;
        }

        // is newly selected
        else {
            $scope.selection.push(name);

            var result = $.grep($scope.summerHouse.activityList, function(e){ return e.name == name; });
            $scope.price += result[0].price;
        }

    };

    $scope.reserve = function() {
        if ($scope.reserveForm.$valid) {
            console.log($scope.fromDate+" "+$scope.toDate);
         /*
            transfersService.transfer($stateParams.id,).then(function (response) {

            }).catch(function(response) {
               //TODO: error handling
            });
            */
        }
    }

}]);
