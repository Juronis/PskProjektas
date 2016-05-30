app.controller('addSummerHouseController', ['$scope', 'summerHouseService', function ($scope, summerHouseService) {

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

    $scope.add = function () {
       // if ($scope.summerHouseForm.$valid) {
            summerHouseService.addImage($scope.image).then(function(response) {
                $scope.summerHouse.image = response.data.link;

                summerHouseService.addSummerHouse($scope.summerHouse).then(function (response) {
                    $scope.successMessage = "Vasarnamis sėkmingai pridėtas";
                    /* summerHouseService.addServices(response.data.id, $scope.services).then(function(response) {
                        $scope.successMessage = "Vasarnamis sėkmingai pridėtas";
                    }).catch(function (response) {
                        //TODO: error handling
                    }); */
                }).catch(function (response) {
                    //TODO: error handling
                });

            }).catch(function (response) {
               //TODO: error handling
            });
            //insert summerhouse

            //insert services
        //}
    }

}]);