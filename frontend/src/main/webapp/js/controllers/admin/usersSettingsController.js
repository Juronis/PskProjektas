app.controller('userSettingsController', ['$scope', 'adminService', function ($scope, adminService) {

    $scope.successMessage = "";
    $scope.messageLog = "";

    var setFields = function () {
        adminService.getUsersSettings().then(function (response) {
            $scope.maxUsers = response.data.maxMembers;
            $scope.membershipFee = response.data.membershipFee;
        });
    }

    setFields();

    $scope.edit = function() {
        if($scope.userSettingsForm.$valid) {
            var data = {
                "maxMembers" : $scope.maxUsers,
                "membershipFee" : $scope.membershipFee
            }
            adminService.editUsersSettings(data).then(function (response) {
                $scope.successMessage = "Nustatymai atnaujinti";
            }).catch(function(response) {
                //TODO: error handler
            });
        }
    }

}]);