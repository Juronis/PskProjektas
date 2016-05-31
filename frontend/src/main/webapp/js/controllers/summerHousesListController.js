app.controller('summerHousesListController', ['$scope', 'summerHouseService', function ($scope, summerHouseService) {

    summerHouseService.getSummerHouses().then(function(response) {
        $scope.summerHouses = response.data;
    }).catch(function(response) {
       //TODO: errorHandling
    });

}]);
