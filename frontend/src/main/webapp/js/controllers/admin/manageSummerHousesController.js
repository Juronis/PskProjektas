app.controller('manageSummerHousesController', ['$scope', 'summerHouseService', 'errorService', function ($scope, summerHouseService, errorService) {

    var loadLists = function() {
        summerHouseService.getSummerHouses().then(function (response) {
            $scope.summerHouses = response.data;
        });
    }

    loadLists();

    $scope.delete = function(id) {
        var answer = prompt("Įveskite slaptažodį", "");
        if(answer) {
            var data = {"password": answer};
            summerHouseService.deleteById(id,data).then(function (response) {
                $scope.summerHouses = $scope.summerHouses.filter(function (el) {
                    return el.id !== id;
                });
                $scope.successMessage = "Vasarnamis sėkmingai ištrintas";
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'manageSummerHousesController');
            });
        }
    }

}]);
