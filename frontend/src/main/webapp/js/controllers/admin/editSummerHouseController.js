app.controller('editSummerHouseController', ['$scope', '$stateParams', 'summerHouseService', 'errorService', function ($scope, $stateParams, summerHouseService, errorService) {

    var setFields = function() {
        summerHouseService.getSummerHouse($stateParams.id).then(function(response) {
            $scope.summerHouse = response.data;
        });
    }

    setFields();

    $scope.successMessage = "";
    $scope.messageLog = "";

    $('#dateFrom').datepicker({
        format: 'yyyy-mm-dd',
        daysOfWeekDisabled: "2,3,4,5,6",
        weekStart: 1
    });

    $('#dateTo').datepicker({
        format: 'yyyy-mm-dd',
        daysOfWeekDisabled: "2,3,4,5,6",
        weekStart: 1
    });

    $('#selectImage').click(function() {
        $('#imageInput').click();
    });

    $('#imageInput').change(function () {
        $('#selectImage').addClass('btn-info');
    });

    $scope.services = [
        {
            "title": "",
            "price": ""
        }
    ]

    $scope.addService = function() {
        var itemToClone = { "title": "", "price": "" };
        $scope.services.push(itemToClone);
    }

    $scope.removeService = function(index) {
        $scope.services.splice(index, 1);
    }

    $scope.edit = function () {
        // if ($scope.summerHouseForm.$valid) {
        summerHouseService.addImage($scope.image).then(function(response) {
            $scope.summerHouse.imageUrl = response.data.link;

            summerHouseService.editSummerHouse($stateParams.id, $scope.summerHouse).then(function (response) {
                $scope.successMessage = "Vasarnamis sėkmingai atnaujintas";
                /* summerHouseService.addServices(response.data.id, $scope.services).then(function(response) {
                 $scope.successMessage = "Vasarnamis sėkmingai pridėtas";
                 }).catch(function (response) {
                 //TODO: error handling
                 }); */
            }).catch(function (response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'editSummerHouseController');
            });

        }).catch(function (response) {
            $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'editSummerHouseController');
        });
    }

}]);
