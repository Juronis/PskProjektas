app.controller('registrationFormController', ['$scope', 'adminService', 'errorService', function ($scope, adminService, errorService) {

    $scope.successMessage = "";
    $scope.messageLog = "";

    var setFields = function () {
        adminService.getSettings('birthday_required').then(function (response) {
            $scope.birthday = response.data.pvalue;
        });
    }

    setFields();

    $scope.edit = function() {
        if($scope.registrationFormForm.$valid) {
            var data = {
                "name" : "BIRTHDAY_REQUIRED",
                "pvalue" : $scope.birthday
            }
            adminService.updateSettings(data).then(function (response) {
                $scope.successMessage = "Informacija atnaujinta";
            }).catch(function(response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'registrationFormController');
            });
        }
    }

}]);