app.controller('summerHousesListController', ['$scope', 'summerHouseService', function ($scope, summerHouseService) {

    summerHouseService.getSummerHouses().then(function(response) {
        console.log(response.data);
    }).catch(function(response) {
       //TODO: errorHandling
    });
    /* $http.get("/frontend/services/resource/persons/4")
     .then(function(response){ $scope.testas = response.data; }) */

}]);
