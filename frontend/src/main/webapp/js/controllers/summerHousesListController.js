app.controller('summerHousesListController', ['$scope', 'summerHouseService', 'errorService', function ($scope, summerHouseService, errorService) {

    summerHouseService.getSummerHouses().then(function(response) {
        $scope.summerHouses = response.data;
    });

}]);
