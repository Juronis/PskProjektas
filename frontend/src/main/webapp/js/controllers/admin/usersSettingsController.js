app.controller('usersSettingsController', ['$scope', 'adminService', 'errorService', function ($scope, adminService, errorService) {

    $scope.successMessage = "";
    $scope.messageLog = "";

    var setFields = function () {
        adminService.getSettings('max_members').then(function (response) {
            $scope.maxUsers = parseInt(response.data.pvalue);
        });
        adminService.getSettings('membership_price').then(function (response) {
           $scope. membershipFee = parseInt(response.data.pvalue);
        });
        adminService.getSettings('minimum_recommendations').then(function (response) {
            $scope. minRecommendations = parseInt(response.data.pvalue);
        });
        adminService.getSettings('max_registration_days').then(function (response) {
            $scope. maxReservation = parseInt(response.data.pvalue);
        });
    }

    setFields();

    $scope.edit = function() {
        if($scope.userSettingsForm.$valid) {
            var data = [{
                    "name" : "MAX_MEMBERS",
                    "pvalue" : $scope.maxUsers
                },
                {
                    "name" : "MEMBERSHIP_PRICE",
                    "pvalue" : $scope.membershipFee
                },
                {
                    "name" : "MINIMUM_RECOMMENDATIONS",
                    "pvalue" : $scope.minRecommendations
                },
                {
                    "name" : "MAX_REGISTRATION_DAYS",
                    "pvalue" : $scope.maxReservation
                }]
            adminService.updateSettings(data).then(function (response) {
                $scope.successMessage = "Informacija atnaujinta";
            }).catch(function(response) {
                $scope.messageLog = errorService.getErrorMsgByCode(response.status, 'usersSettingsController');
            });
        }
    }

}]);