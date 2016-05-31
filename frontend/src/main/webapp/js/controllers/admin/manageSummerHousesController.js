app.controller('manageSummerHousesController', ['$scope', 'summerHouseService', 'errorService', function ($scope, summerHouseService, errorService) {

    summerHouseService.getSummerHouses().then(function(response) {
        $scope.summerHouses = response.data;
    }).catch(function(response) {
        //TODO: errorHandling
    });

    $scope.delete = function(id) {
        var answer = prompt("Įveskite slaptažodį", "");
        if(answer) {
            var data = {"password": answer};
            summerHouseService.delete(id,data).then(function (response) {
                $scope.successMessage = "Vasarnamis sėkmingai ištrintas";
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'manageSummerHousesController');
            });
        }
    }

}]);
